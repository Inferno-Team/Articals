package com.inferno.mobile.articals.ui.master.my_articles.add_article;

import static android.app.Activity.RESULT_OK;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;

import com.inferno.mobile.articals.R;
import com.inferno.mobile.articals.adapters.RefAdapter;
import com.inferno.mobile.articals.databinding.MasterAddArticleBinding;
import com.inferno.mobile.articals.models.Article;
import com.inferno.mobile.articals.models.MessageResponse;
import com.inferno.mobile.articals.models.Reference;
import com.inferno.mobile.articals.models.User;
import com.inferno.mobile.articals.models.UserType;
import com.inferno.mobile.articals.ui.master.my_articles.MasterArticleViewModel;
import com.inferno.mobile.articals.utils.Token;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.ArrayList;

public class AddArticleFragment extends Fragment {
    private MasterAddArticleBinding binding;
    private MasterArticleViewModel viewModel;
    private User selectedDoctor = null;
    private static final int PDF_CHOOSER_RQ = 101;
    private NavController controller;
    private RefAdapter refAdapter;
    private ArrayList<Reference> references = new ArrayList<>();

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater,
                             @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {

        binding = MasterAddArticleBinding.inflate(inflater, container, false);
        viewModel = new ViewModelProvider(requireActivity()).get(MasterArticleViewModel.class);
        viewModel.init();
        refAdapter = new RefAdapter(new ArrayList<>());
        binding.refRv.setAdapter(refAdapter);
        binding.addRef.setOnClickListener(v -> {
            String ref = binding.ref.getEditableText().toString();
            if (ref.equals("")) {
                Toast.makeText(requireContext(), "لا يمكنك اضافة مصدر فارغ", Toast.LENGTH_SHORT).show();
                return;
            }
            for (int i = 0; i < references.size(); i++) {
                if (references.get(i).getName().equals(ref)) {
                    refAdapter.addRef(references.get(i));
                    binding.ref.setText("");
                    break;
                }
//                Toast.makeText(requireContext(), "المصدر موجود مسبقا", Toast.LENGTH_SHORT).show();
            }


        });
        controller = Navigation.
                findNavController(container.getRootView().findViewById(R.id.fragment_main));

        String token = Token.getToken(requireContext());

        if (Token.checkUserType(requireContext()) == UserType.doctor) {
            binding.doctorsMenuContainer.setVisibility(View.GONE);
            binding.refContainer.setVisibility(View.GONE);
        } else {
            viewModel.getDoctorOfField(token).observe(requireActivity(), this::onDoctorDataArrive);
            viewModel.getReferences(token).observe(requireActivity(), this::onReferenceDataArrive);

        }

        binding.chooseFile.setOnClickListener(l -> {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                if (requireContext().
                        checkSelfPermission(Manifest.permission.READ_EXTERNAL_STORAGE)
                        == PackageManager.PERMISSION_DENIED) {
                    requestPermissions(
                            new String[]{Manifest.permission.READ_EXTERNAL_STORAGE},
                            101);
                } else openPDFChooser();
            } else openPDFChooser();
        });
        binding.upload.setOnClickListener(v -> {
            String name = binding.nameField.getEditableText().toString();
            String univName = binding.univFiled.getEditableText().toString();
            if (Token.checkUserType(requireContext()) == UserType.master) {
                if (selectedDoctor == null) {
                    Toast.makeText(requireContext(),
                            "please select doctor first", Toast.LENGTH_SHORT).show();
                    return;
                }
                int doctorId = selectedDoctor.getId();
                File file = new File(requireContext().getCacheDir().getPath() + "/pdf.pdf");

                viewModel.addArticle(token, name, univName, doctorId, file,
                                refAdapter.getRefsId())
                        .observe(requireActivity(), this::onUploadFinish);
            } else {
                File file = new File(requireContext().getCacheDir().getPath() + "/pdf.pdf");
                viewModel.addDoctorArticle(token, name, univName, file)
                        .observe(requireActivity(), this::onUploadFinish);
            }

        });
        return binding.getRoot();
    }

    private void onReferenceDataArrive(ArrayList<Reference> references) {
        this.references = references;
        String[] refs = new String[this.references.size()];
        for (int i = 0; i < this.references.size(); i++) {
            refs[i] = this.references.get(i).getName();
        }
        ArrayAdapter<String>adapter = new ArrayAdapter<>(requireContext(),android.R.layout.simple_list_item_1,
                refs);
        binding.ref.setAdapter(adapter);
    }

    private void onUploadFinish(MessageResponse<Article> response) {
        Toast.makeText(requireContext(), response.getMessage(), Toast.LENGTH_LONG).show();
        if (response.getCode() == 200) {
            controller.navigateUp();
        }
    }

    private void openPDFChooser() {

        Intent intent = new Intent(Intent.ACTION_GET_CONTENT);
        intent.setType("*/*");

        startActivityForResult(Intent.createChooser(intent, "Choose PDF")
                , PDF_CHOOSER_RQ);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == PDF_CHOOSER_RQ && resultCode == RESULT_OK && data != null) {
            System.out.println(data.getData().getPath());
            String path = data.getData().getPath();
            int last;
            if (path.length() > 15)
                last = 15;
            else last = path.length() - 1;
            String selectedPath = path.substring(0, last) + "..." +
                    path.substring(path.length() - 5);
            System.out.println(getPathFromURI(data.getData()));
            System.out.println(selectedPath);
            binding.fileName.setText(selectedPath);

        }

    }

    public String getPathFromURI(Uri contentUri) {
        File cache = requireActivity().getCacheDir();
        File pdf = new File(cache.getAbsolutePath() + "/pdf.pdf");
        try {
            if (pdf.exists())
                pdf.delete();
            pdf.createNewFile();
            InputStream src = requireContext().getContentResolver().openInputStream(contentUri);
            OutputStream dst = new FileOutputStream(pdf);
            byte[] buf = new byte[1024];
            int len;
            while ((len = src.read(buf)) > 0) {
                dst.write(buf, 0, len);
            }
            src.close();
            dst.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return pdf.getAbsolutePath();
    }

    @Override
    public void onRequestPermissionsResult(int requestCode,
                                           @NonNull String[] permissions,
                                           @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (requestCode == 101 && permissions[0].equals(Manifest.permission.READ_EXTERNAL_STORAGE))
            if (grantResults[0] == PackageManager.PERMISSION_GRANTED)
                openPDFChooser();
            else {
                Toast.makeText(requireContext(),
                        "this permission is needed.", Toast.LENGTH_SHORT).show();
            }
    }

    private void onDoctorDataArrive(ArrayList<User> doctors) {
        ArrayList<String> doctorsName = new ArrayList<>();
        for (User doctor : doctors)
            doctorsName.add(doctor.getFullName());
        ArrayAdapter<String> adapter = new ArrayAdapter<>(requireContext(),
                R.layout.doctor_menu_item, doctorsName);
        binding.doctorMenu.setAdapter(adapter);
        binding.doctorMenu.setOnItemClickListener((parent, view, position, id) -> {
            selectedDoctor = doctors.get(position);
        });
    }
}

package com.inferno.mobile.articals.ui.article_details;

import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Bundle;
import android.print.PrintAttributes;
import android.print.PrintDocumentAdapter;
import android.print.PrintManager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Toast;

import androidx.activity.OnBackPressedCallback;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;

import com.github.barteksc.pdfviewer.util.FitPolicy;
import com.inferno.mobile.articals.R;
import com.inferno.mobile.articals.adapters.PdfDocumentAdapter;
import com.inferno.mobile.articals.databinding.ArticleDetailsLayoutBinding;
import com.inferno.mobile.articals.models.Article;
import com.inferno.mobile.articals.services.API;
import com.inferno.mobile.articals.utils.PDFDownloaderThread;
import com.inferno.mobile.articals.utils.Token;

import java.io.File;

public class ArticleDetailsFragment extends Fragment {
    private ArticleDetailsLayoutBinding binding;

    private PDFDownloaderThread thread;
    private NavController controller;
    private ArticleViewModel viewModel;
    private Animation openAnim, closeAnim, fromBottomAnim, toBottomAnim;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater,
                             @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        binding = ArticleDetailsLayoutBinding.inflate(inflater, container, false);

        openAnim = AnimationUtils.loadAnimation(requireContext(), R.anim.rotate_open_anim);
        closeAnim = AnimationUtils.loadAnimation(requireContext(), R.anim.rotate_close_anim);
        fromBottomAnim = AnimationUtils.loadAnimation(requireContext(), R.anim.from_bottom_anim);
        toBottomAnim = AnimationUtils.loadAnimation(requireContext(), R.anim.to_bottom_anim);

        viewModel = new ViewModelProvider(requireActivity())
                .get(ArticleViewModel.class);
        final int id = requireArguments().getInt("article_id");
        boolean forShow = requireArguments().getBoolean("for_show");
        if (forShow) {
            applyForShow();
        } else {
            String token = Token.getToken(requireContext());
            controller = Navigation.findNavController(
                    container.getRootView().findViewById(R.id.fragment_main)
            );
            requireActivity().getOnBackPressedDispatcher().addCallback(new OnBackPressedCallback(true) {
                @Override
                public void handleOnBackPressed() {
                    if (thread != null) {
                        thread.removePDF();
                        if (thread.isAlive())
                            thread.interrupt();
                    }
                    controller.navigateUp();

                }
            });
            binding.toolbar.setNavigationOnClickListener(v -> {
                if (thread != null) {
                    thread.removePDF();
                    if (thread.isAlive())
                        thread.interrupt();
                }
                controller.navigateUp();
            });
            binding.pdfDownloader.setOnClickListener(v -> {

                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                    int result = requireContext().
                            checkSelfPermission(Manifest.permission.WRITE_EXTERNAL_STORAGE);
                    if (result == PackageManager.PERMISSION_DENIED) {
                        requestPermissions(new String[]{
                                Manifest.permission.WRITE_EXTERNAL_STORAGE
                        }, 101);
                    } else {
                        thread.downloadToFile();
                        viewModel.downloadPDF(token, id);
                    }
                } else {
                    thread.downloadToFile();
                    viewModel.downloadPDF(token, id);
                }
            });


            viewModel.init();

            viewModel.getArticleDetails(token, id)
                    .observe(requireActivity(), this::onArticleArrive);
        }
        binding.pdfOptionOpener.setOnClickListener(v -> {

            if (binding.pdfPrint.getVisibility() == View.VISIBLE) {


                binding.pdfOptionOpener.startAnimation(closeAnim);
                binding.pdfDownloader.startAnimation(toBottomAnim);
                binding.pdfShare.startAnimation(toBottomAnim);
                binding.pdfPrint.startAnimation(toBottomAnim);

                binding.pdfDownloader.setVisibility(View.GONE);
                binding.pdfShare.setVisibility(View.GONE);
                binding.pdfPrint.setVisibility(View.GONE);

                binding.pdfDownloader.setClickable(false);
                binding.pdfShare.setClickable(false);
                binding.pdfPrint.setClickable(false);
            } else {
                binding.pdfDownloader.setVisibility(View.VISIBLE);
                binding.pdfShare.setVisibility(View.VISIBLE);
                binding.pdfPrint.setVisibility(View.VISIBLE);
                binding.pdfDownloader.setClickable(true);
                binding.pdfShare.setClickable(true);
                binding.pdfPrint.setClickable(true);

                binding.pdfOptionOpener.startAnimation(openAnim);
                binding.pdfDownloader.startAnimation(fromBottomAnim);
                binding.pdfShare.startAnimation(fromBottomAnim);
                binding.pdfPrint.startAnimation(fromBottomAnim);
            }


        });
        return binding.getRoot();
    }

    private void applyForShow() {

        binding.toolbar.setNavigationOnClickListener(v -> {
            requireActivity().onBackPressed();
        });
        binding.pdfView.setVisibility(View.VISIBLE);
        binding.progressBar.setVisibility(View.GONE);
        String filePath = requireArguments().getString("file_path");
        binding.pdfView.fromFile(new File(filePath))
                .enableSwipe(true)
                .swipeHorizontal(false)
                .enableDoubletap(true)
                .defaultPage(0)
                .enableAnnotationRendering(false)
                .password(null)
                .scrollHandle(null)
                .enableAntialiasing(true)
                .spacing(0)
                .onPageChange(this::onPageChange)
                .pageFitPolicy(FitPolicy.WIDTH)
                .load();
    }

    private void onPageChange(int page, int pageCount) {
        String fullTitle = requireContext()
                .getString(R.string.page_number, page + "/" + pageCount);
        binding.toolbar.setSubtitle(fullTitle);
    }

    private void onArticleArrive(Article article) {
        if (article == null) return;
        binding.toolbar.setVisibility(View.VISIBLE);
        binding.pdfOptionOpener.setVisibility(View.VISIBLE);

        binding.pdfShare.setOnClickListener(v -> {
            Intent intent = new Intent(Intent.ACTION_SEND);
            String shareText = "Article Name : " + article.getName()
                    + "\n" + "Author Name : " + article.getWriter().getFirstName()
                    + "\n" + "Link : http://127.0.0.1/pdf?id=" + article.getId();
            intent.putExtra(Intent.EXTRA_TEXT, shareText);
            intent.setType("text/plain");
            startActivity(Intent.createChooser(intent, "Share Via"));
        });
        binding.pdfPrint.setOnClickListener(v -> {
            PrintManager printManager = (PrintManager)
                    requireActivity().getSystemService(Context.PRINT_SERVICE);
            try {
                PrintDocumentAdapter printAdapter = new PdfDocumentAdapter(thread.localPath);
                printManager.print("Document", printAdapter,
                        new PrintAttributes.Builder().build());
            } catch (Exception e) {
                Log.e("Fragment", "onPrint", e);
            }
        });
        String title = "";
        if (article.getName().length() > 25)
            title = article.getName().substring(0, 25);
        else title = article.getName();
        binding.toolbar.setTitle(title);
        String link = API.PDF_URL + article.getFileUrl();
        thread = new PDFDownloaderThread(link, binding, requireContext());
        thread.start();
        Toast.makeText(requireContext(), "Opening PDF ...", Toast.LENGTH_SHORT).show();

    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions,
                                           @NonNull int[] grantResults) {
        if (requestCode == 101)
            if (permissions[0].equals(Manifest.permission.WRITE_EXTERNAL_STORAGE))
                if (grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    thread.downloadToFile();
                    final int id = requireArguments().getInt("article_id");
                    String token = Token.getToken(requireContext());
                    viewModel.downloadPDF(token, id);
                }
    }
}

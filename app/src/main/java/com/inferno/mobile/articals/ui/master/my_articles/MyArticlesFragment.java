package com.inferno.mobile.articals.ui.master.my_articles;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;

import com.inferno.mobile.articals.R;
import com.inferno.mobile.articals.adapters.ArticlesAdapter;
import com.inferno.mobile.articals.databinding.MasterMyArticlesBinding;
import com.inferno.mobile.articals.models.Article;
import com.inferno.mobile.articals.models.GetMyArticleResponse;
import com.inferno.mobile.articals.models.UserType;
import com.inferno.mobile.articals.utils.Token;

import java.util.ArrayList;

public class MyArticlesFragment extends Fragment {
    private MasterMyArticlesBinding binding;
    private NavController controller;
    private MasterArticleViewModel viewModel;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater,
                             @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        binding = MasterMyArticlesBinding.inflate(inflater, container, false);
        controller = Navigation.findNavController(
                container.getRootView().findViewById(R.id.fragment_main));
        viewModel = new ViewModelProvider(requireActivity())
                .get(MasterArticleViewModel.class);
        viewModel.init();
        String token = Token.getToken(requireContext());
        if (Token.checkUserType(requireContext()) == UserType.doctor) {
            viewModel.getDoctorArticles(token).observe(requireActivity(), this::onMasterArticles);
        } else
            viewModel.getMasterArticles(token).observe(requireActivity(), this::onMasterArticles);
        binding.addArticleButton.setOnClickListener(l -> {
            NavController controller = Navigation.
                    findNavController(container.getRootView().findViewById(R.id.fragment_main));
            controller.navigate(R.id.action_myArticlesFragment_to_addArticleFragment);
        });
        return binding.getRoot();
    }


    private void onMasterArticles(GetMyArticleResponse response) {
        if (response.getCode() != 200) {
            Toast.makeText(requireContext(), response.getMessage(), Toast.LENGTH_SHORT).show();
            controller.navigateUp();
            return;

        }
        ArticlesAdapter adapter = new ArticlesAdapter(requireContext(),
                response.getArticles(), false);
        adapter.setOnLongClickListener((id, pos) -> {
            new AlertDialog.Builder(requireActivity())
                    .setMessage("Are you sure you want to delete this article ?")
                    .setTitle("Conformation")
                    .setNegativeButton("No", (dialog, which) -> dialog.dismiss())
                    .setPositiveButton("Yes", (dialog, which) -> {
                        dialog.dismiss();
                        String token = Token.getToken(requireContext());
                        boolean master = Token.checkUserType(requireContext())
                                        == UserType.master;
                        viewModel.deleteArticle(token, id,master)
                                .observe(requireActivity(), _response -> {
                                    if (_response.getCode() == 200) {
                                        adapter.removeOne(pos);
                                    }
                                    Toast.makeText(requireContext(), _response.getMessage(),
                                            Toast.LENGTH_LONG).show();
                                });
                    })
                    .show();
        });

        binding.myArticles.setAdapter(adapter);
    }
}

package com.inferno.mobile.articals.ui.home;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.widget.SearchView;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.fragment.NavHostFragment;

import com.inferno.mobile.articals.R;
import com.inferno.mobile.articals.activities.MainActivity;
import com.inferno.mobile.articals.adapters.ArticlesAdapter;
import com.inferno.mobile.articals.adapters.PaginationListener;
import com.inferno.mobile.articals.databinding.HomeFragmentBinding;
import com.inferno.mobile.articals.databinding.ReportDialogLayoutBinding;
import com.inferno.mobile.articals.models.Article;
import com.inferno.mobile.articals.models.ArticlesResponse;
import com.inferno.mobile.articals.models.MessageResponse;
import com.inferno.mobile.articals.models.Report;
import com.inferno.mobile.articals.utils.Token;


import java.util.ArrayList;
import java.util.Locale;

public class HomeFragment extends Fragment {
    private HomeFragmentBinding binding;
    private NavController controller;
    private HomeViewModel viewModel;
    private AlertDialog reportDialog;
    private ArrayList<Article> articles;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater,
                             @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        binding = HomeFragmentBinding.inflate(inflater, container, false);

        viewModel = new ViewModelProvider(requireActivity()).get(HomeViewModel.class);
        viewModel.init();
        controller = NavHostFragment.findNavController(this);
        String token = Token.getToken(requireContext());
        if (!Token.isLoggedIn(requireContext())) {
            controller.navigate(R.id.action_homeFragment_to_loginFragment);
        } else {
            viewModel.getRecentArticles(token)
                    .observe(requireActivity(), this::onRecentData);
        }
        return binding.getRoot();
    }

    private void searchOnArticle(String newText) {
        viewModel.searchArticles(newText, Token.getToken(requireContext()))
                .observe(requireActivity(), this::onSearchData);
    }

    private void onSearchData(ArrayList<Article> articles) {
        ArticlesAdapter adapter = (ArticlesAdapter) binding.recentArticles.getAdapter();
        if (adapter == null) return;
        adapter.swapArticle(articles);
    }


    private void onRecentData(ArrayList<Article> articles) {
        this.articles = articles;

        ArticlesAdapter adapter = new ArticlesAdapter
                (requireContext(), articles, true);


        binding.searchOpener.setVisibility(View.VISIBLE);
        adapter.setOnClickListener((id, pos) -> {
            Bundle bundle = new Bundle();
            bundle.putInt("article_id", id);
            controller.navigate(R.id.action_homeFragment_to_articleDetailsFragment, bundle);
        });
        adapter.setOnCommentClickListener((id, pos) -> {
            ReportDialogLayoutBinding reportBinding =
                    ReportDialogLayoutBinding.inflate(getLayoutInflater());
            reportDialog = new AlertDialog.Builder(requireContext())
                    .setView(reportBinding.getRoot())
                    .create();
            reportBinding.reportMsg.setText("");
            reportDialog.show();
            reportBinding.makeReport.setOnClickListener(v -> {
                String token = Token.getToken(requireContext());
                String report = reportBinding.reportMsg.getEditableText().toString();
                reportBinding.reportMsg.setText("");
                viewModel.reportComment(token, report, id)
                        .observe(requireActivity(), this::onReportResponse);
                reportDialog.dismiss();
            });
        });
        adapter.setOnAddCommentClickListener((id, pos) -> {
            ReportDialogLayoutBinding reportBinding =
                    ReportDialogLayoutBinding.inflate(getLayoutInflater());
            AlertDialog dialog = new AlertDialog.Builder(requireContext())
                    .setView(reportBinding.getRoot())
                    .show();
            reportBinding.makeReport.setText("Add Comment");
            reportBinding.title.setText("add new comment");
            reportBinding.reportMsg.setHint("write your comment");
            reportBinding.makeReport.setOnClickListener(v -> {
                String token = Token.getToken(requireContext());
                String comment = reportBinding.reportMsg.getEditableText().toString();
                reportBinding.reportMsg.setText("");
                viewModel.addComment(token, comment, id)
                        .observe(requireActivity(), response -> {
                            if (response.getCode() == 200) {
                                // add this comment to this article
                                adapter.addNewComment(pos, response.getData());

                            }
                            Toast.makeText(requireContext(), response.getMessage()
                                    , Toast.LENGTH_SHORT).show();

                        });
                dialog.dismiss();
            });
        });
        binding.recentArticles.setAdapter(adapter);

    }

    private void onReportResponse(MessageResponse<Report> response) {
        Toast.makeText(requireContext(), response.getMessage(), Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onResume() {
        super.onResume();
        if (Token.isLoggedIn(requireContext()))
            ((MainActivity) requireActivity()).binding.bottomNavView.setVisibility(View.VISIBLE);

    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        binding.searchOpener.setOnClickListener(v -> {
            binding.recentText.setVisibility(View.GONE);
            binding.searchField.setVisibility(View.VISIBLE);
            binding.searchBackBtn.setVisibility(View.VISIBLE);
        });
        binding.searchBackBtn.setOnClickListener(v -> {
            binding.recentText.setVisibility(View.VISIBLE);
            binding.searchField.setVisibility(View.GONE);
            binding.searchBackBtn.setVisibility(View.GONE);
            ArticlesAdapter adapter = (ArticlesAdapter) binding.recentArticles.getAdapter();
            if (adapter == null) return;
            adapter.returnToRecent();

        });
        binding.searchField.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                searchOnArticle(query);
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {

                return false;
            }
        });

    }
}

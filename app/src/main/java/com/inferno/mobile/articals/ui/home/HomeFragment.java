package com.inferno.mobile.articals.ui.home;

import static com.inferno.mobile.articals.services.API.PDF_URL;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.widget.SearchView;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.fragment.NavHostFragment;

import com.inferno.mobile.articals.R;
import com.inferno.mobile.articals.activities.MainActivity;
import com.inferno.mobile.articals.adapters.ArticlesAdapter;
import com.inferno.mobile.articals.adapters.LoadMore;
import com.inferno.mobile.articals.adapters.PaginationListener;
import com.inferno.mobile.articals.databinding.HomeFragmentBinding;
import com.inferno.mobile.articals.models.Article;
import com.inferno.mobile.articals.models.ArticlesResponse;
import com.inferno.mobile.articals.services.API;
import com.inferno.mobile.articals.utils.Token;


import java.util.ArrayList;
import java.util.Locale;

public class HomeFragment extends Fragment {
    private HomeFragmentBinding binding;
    private NavController controller;
    private ArrayList<Article> articles;
    private PaginationListener paginationListener;
    private HomeViewModel viewModel;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater,
                             @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        binding = HomeFragmentBinding.inflate(inflater, container, false);
        this.articles = new ArrayList<>();

        viewModel = new ViewModelProvider(requireActivity()).get(HomeViewModel.class);
        viewModel.init();
        controller = Navigation.
                findNavController(container.getRootView().findViewById(R.id.fragment_main));

        return binding.getRoot();
    }

    private void searchOnArticle(String newText) {
        if (binding.recentArticles.getAdapter() == null) return;
        ArrayList<Article> searchedArticles = findSearchedArticles(newText);
        ((ArticlesAdapter) binding.recentArticles.getAdapter())
                .removeOther(searchedArticles);

    }

    private ArrayList<Article> findSearchedArticles(String newText) {
        ArrayList<Article> newArticles = new ArrayList<>();
        for (Article article : this.articles)
            if (article.getName().toLowerCase(Locale.ROOT).contains(newText.toLowerCase(Locale.ROOT)))
                newArticles.add(article);
        return newArticles;
    }

    private void onRecentData(ArticlesResponse response) {

        if (this.articles == null || this.articles.size() == 0) {
            this.articles = response.getArticles();
            ArticlesAdapter adapter = new ArticlesAdapter(requireContext(), this.articles,true);
            binding.searchOpener.setVisibility(View.VISIBLE);
            adapter.setOnClickListener((id, pos) -> {
                Bundle bundle = new Bundle();
                bundle.putInt("article_id", id);
                controller.navigate(R.id.action_homeFragment_to_articleDetailsFragment, bundle);
            });
            binding.recentArticles.setAdapter(adapter);
        } else {
            if (binding.recentArticles.getAdapter() == null) return;
            int lastIndex = this.articles.size();
            this.articles.addAll(response.getArticles());
            binding.recentArticles.getAdapter()
                    .notifyItemRangeInserted(lastIndex, response.getArticles().size());
        }


        paginationListener.setLoading(false);
        paginationListener.setLastPage(response.getNextPageUrl() == null);
        paginationListener.setCurrentPage(response.getCurrentPage());
    }

    @Override
    public void onResume() {
        super.onResume();
        if (Token.isLoggedIn(requireContext()))
            ((MainActivity) requireActivity()).binding.bottomNavView.setVisibility(View.VISIBLE);
       /* boolean isMoving = requireActivity().
                getIntent().getBooleanExtra("is_moving", false);
        String path = requireActivity().
                getIntent().getStringExtra("file_path");
        if (isMoving) {
            Bundle bundle = new Bundle();
            bundle.putBoolean("for_show", true);
            bundle.putString("file_path", path);
            bundle.putInt("article_id",-1);
            controller.navigate(R.id.action_homeFragment_to_articleDetailsFragment, bundle);
//            controller.navigate(R.id.action_homeFragment_to_articleDetailsFragment, new Bundle());
        }*/
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        paginationListener = new PaginationListener();
        paginationListener.setCurrentPage(1);
        paginationListener.setLoading(true);
        paginationListener.setLastPage(false);
        String token = Token.getToken(requireContext());
        if (!Token.isLoggedIn(requireContext())) {
            controller.navigate(R.id.action_homeFragment_to_loginFragment);
        } else {
            viewModel.getRecentArticles(token, 1)
                    .observe(requireActivity(), this::onRecentData);
        }
        binding.searchOpener.setOnClickListener(v -> {
            binding.recentText.setVisibility(View.GONE);
            binding.searchField.setVisibility(View.VISIBLE);
            binding.searchBackBtn.setVisibility(View.VISIBLE);
        });
        binding.searchBackBtn.setOnClickListener(v -> {
            binding.recentText.setVisibility(View.VISIBLE);
            binding.searchField.setVisibility(View.GONE);
            binding.searchBackBtn.setVisibility(View.GONE);
        });
        binding.searchField.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                searchOnArticle(newText);
                return false;
            }
        });
        paginationListener.setLoadMore(currentPage -> {
            System.out.println("load more... #" + (currentPage+1));
            paginationListener.setLoading(true);

            viewModel.getRecentArticles(token, currentPage + 1).observe(requireActivity(),
                    this::onRecentData);
        });
        binding.recentArticles.setOnScrollListener(paginationListener);
    }
}

package com.inferno.mobile.articals.adapters;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

public class PaginationListener extends RecyclerView.OnScrollListener {
    private LinearLayoutManager layoutManager;
    private LoadMore loadMore;
    private boolean isLastPage;
    private boolean isLoading;
    private int currentPage;


    public void setLoadMore(LoadMore loadMore) {
        this.loadMore = loadMore;
    }

    @Override
    public void onScrolled(@NonNull RecyclerView recyclerView, int dx, int dy) {
        super.onScrolled(recyclerView, dx, dy);
        layoutManager = (LinearLayoutManager) recyclerView.getLayoutManager();
        if (layoutManager == null) return;
        int visibleItemCount = layoutManager.getChildCount();
        int totalItemCount = layoutManager.getItemCount();
        int firstVisibleItemPosition = layoutManager.findFirstVisibleItemPosition();
        if (!isLoading && !isLastPage) {
            if ((visibleItemCount + firstVisibleItemPosition) >= totalItemCount
                    && firstVisibleItemPosition >= 0
                    && totalItemCount >= LoadMore.PAGE_ITEM_COUNT) {
                if (loadMore != null)
                    loadMore.loadMore(currentPage);
            }
        }
    }

    public void setLastPage(boolean lastPage) {
        isLastPage = lastPage;
    }

    public void setLoading(boolean loading) {
        isLoading = loading;
    }

    public void setCurrentPage(int currentPage) {
        this.currentPage = currentPage;
    }
}

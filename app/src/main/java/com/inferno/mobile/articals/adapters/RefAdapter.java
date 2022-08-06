package com.inferno.mobile.articals.adapters;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.inferno.mobile.articals.R;
import com.inferno.mobile.articals.databinding.RefItemBinding;
import com.inferno.mobile.articals.models.Reference;

import java.util.ArrayList;

public class RefAdapter extends RecyclerView.Adapter<RefAdapter.ViewHolder> {
    private final ArrayList<Reference> list;

    public RefAdapter(ArrayList<Reference> list) {
        this.list = list;
    }

    public ArrayList<Reference> getRefs() {
        return this.list;
    }

    public ArrayList<Integer> getRefsId() {
        ArrayList<Integer> ids = new ArrayList<>();
        for (Reference reference : list)
            ids.add(reference.getId());
        return ids;
    }

    public void addRef(Reference item) {
        this.list.add(item);
        notifyItemInserted(list.size());
    }


    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new ViewHolder(LayoutInflater.from(parent.getContext())
                .inflate(R.layout.ref_item, parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        holder.binding.setRef(list.get(position));
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        RefItemBinding binding;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            binding = RefItemBinding.bind(itemView);
        }
    }
}

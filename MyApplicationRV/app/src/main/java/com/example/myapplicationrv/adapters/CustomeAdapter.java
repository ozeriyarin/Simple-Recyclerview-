package com.example.myapplicationrv.adapters;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.myapplicationrv.R;
import com.example.myapplicationrv.models.CharacterData;

import java.util.ArrayList;
import java.util.List;

public class CustomeAdapter extends RecyclerView.Adapter<CustomeAdapter.ViewHolder> implements Filterable {

    public interface OnItemClickListener {
        void onItemClick(CharacterData item);
    }

    private OnItemClickListener listener;
    public void setOnItemClickListener(OnItemClickListener l) {
        this.listener = l;
    }

    private List<CharacterData> fullList = new ArrayList<>();     // ALL data
    private List<CharacterData> filteredList = new ArrayList<>(); // what's shown

    public CustomeAdapter() { }

    public void setData(List<CharacterData> data) {
        fullList.clear();
        fullList.addAll(data);

        filteredList.clear();
        filteredList.addAll(data);

        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public CustomeAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.cardview, parent, false);
        return new ViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull CustomeAdapter.ViewHolder holder, int position) {
        CharacterData item = filteredList.get(position);
        holder.nameTv.setText(item.getName());
        holder.actorTv.setText(item.getActor());

        if (item.getImageUrl() != null) {
            Glide.with(holder.itemView.getContext())
                    .load(item.getImageUrl())
                    .placeholder(R.drawable.ic_launcher_foreground)
                    .into(holder.imageIv);
        } else {
            holder.imageIv.setImageResource(R.drawable.ic_launcher_foreground);
        }

        holder.itemView.setOnClickListener(v -> {
            if (listener != null) {
                listener.onItemClick(item);
            }
        });
    }

    @Override public int getItemCount() {
        return filteredList.size();
    }

    @Override
    public Filter getFilter() {
        return new Filter() {
            @Override protected FilterResults performFiltering(CharSequence constraint) {
                String query = constraint == null ? "" : constraint.toString().toLowerCase().trim();
                List<CharacterData> results = new ArrayList<>();
                if (query.isEmpty()) {
                    results.addAll(fullList);
                } else {
                    for (CharacterData item : fullList) {
                        if (item.getName().toLowerCase().contains(query)) {
                            results.add(item);
                        }
                    }
                }
                FilterResults fr = new FilterResults();
                fr.values = results;
                return fr;
            }
            @Override @SuppressWarnings("unchecked")
            protected void publishResults(CharSequence constraint, FilterResults  fr) {
                filteredList.clear();
                filteredList.addAll((List<CharacterData>) fr.values);
                notifyDataSetChanged();
            }
        };
    }

    static class ViewHolder extends RecyclerView.ViewHolder {
        ImageView imageIv;
        TextView nameTv, actorTv;

        ViewHolder(@NonNull View itemView) {
            super(itemView);
            imageIv = itemView.findViewById(R.id.imageView);
            nameTv  = itemView.findViewById(R.id.textName);
            actorTv = itemView.findViewById(R.id.textActor);
        }
    }
}

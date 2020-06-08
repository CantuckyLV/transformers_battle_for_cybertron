package com.daniel.battleforcybertron.Adapters;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.daniel.battleforcybertron.Model.Transformer;
import com.daniel.battleforcybertron.R;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

public class TransformerAdapter extends RecyclerView.Adapter<TransformerAdapter.ItemViewHolder>  {

    private ArrayList<Transformer> transformers;
    private OnItemClickListener mOnItemClickListener;

    public TransformerAdapter(ArrayList<Transformer> transformers, OnItemClickListener mOnItemClickListener) {
        this.transformers = transformers;
        this.mOnItemClickListener = mOnItemClickListener;
    }


    @NonNull
    @Override
    public ItemViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        View itemView = inflater.inflate(R.layout.transformer_item, parent, false);
        return new ItemViewHolder(itemView,mOnItemClickListener);
    }

    @Override
    public void onBindViewHolder(@NonNull ItemViewHolder holder, int position) {
        Transformer transformer = transformers.get(position);
        TextView transformerName = holder.transformerName;
        ImageView teamIcon = holder.teamIcon;
        Picasso.get().load(transformer.getTeam_icon()).into(teamIcon);
        transformerName.setText(transformer.getName());
    }

    @Override
    public int getItemCount() {
        return transformers.size();
    }

    public class ItemViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        public TextView transformerName;
        public ImageView teamIcon;
        OnItemClickListener onItemClickListener;
        public ItemViewHolder(View itemView, OnItemClickListener onItemClickListener) {
            super(itemView);
            transformerName = itemView.findViewById(R.id.transformer_name);
            teamIcon = itemView.findViewById(R.id.team_icon);
            this.onItemClickListener = onItemClickListener;
            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View view) {
            onItemClickListener.onItemClick(getAdapterPosition());
        }
    }

    /**
     * interface that allows the Recyclerview item to listen for clicks
     */
    public interface OnItemClickListener {
        void onItemClick(int position);
    }
}

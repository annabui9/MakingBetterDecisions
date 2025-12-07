package com.example.makingbetterdecisions;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class CellAdapter extends RecyclerView.Adapter<CellAdapter.CellViewHolder> {

    private List<Cell> cellList;

    public CellAdapter(List<Cell> cellList){
        this.cellList = cellList;
    }

    public static class CellViewHolder extends RecyclerView.ViewHolder{
        TextView titleTextView;
        TextView detailsTextView;
        LinearLayout layoutDetails;
        public CellViewHolder(@NonNull View itemView) {
            super(itemView);
            titleTextView = itemView.findViewById(R.id.titleTV);
            detailsTextView = itemView.findViewById(R.id.detailsTV);
            layoutDetails = itemView.findViewById(R.id.layoutDetails);

        }
    }

    @NonNull
    @Override
    public CellAdapter.CellViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.single_cell, parent, false);
        return new CellViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull CellViewHolder holder, int position) {
        Cell cell = cellList.get(position);

        holder.titleTextView.setText(cell.getTitle());
        holder.detailsTextView.setText(cell.getDetails());

        holder.titleTextView.setTextColor(0xFFFFFFFF);
        holder.detailsTextView.setTextColor(0xFFFFFFFF);

        holder.layoutDetails.setVisibility(cell.isExpanded() ? View.VISIBLE : View.GONE);

        holder.itemView.setOnClickListener(v -> {
            cell.setExpanded(!cell.isExpanded());
            notifyItemChanged(position);
        });
    }


    @Override
    public int getItemCount() {
        return cellList.size();
    }




}

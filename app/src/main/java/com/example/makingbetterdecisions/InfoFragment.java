package com.example.makingbetterdecisions;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.AnimationUtils;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class InfoFragment extends Fragment {

    RecyclerView howToRecyclerView;
    RecyclerView lensRecyclerView;
    private CellAdapter howToAdapter;
    private CellAdapter lensAdapter;

    public InfoFragment() { }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_info, container, false);

        // Fade-in animation
        view.startAnimation(AnimationUtils.loadAnimation(getContext(), R.anim.fade_in));

        // HOW TO section
        howToRecyclerView = view.findViewById(R.id.howToRecyclerView);
        howToRecyclerView.setLayoutManager(new LinearLayoutManager(getContext()));

        CellData.getHowToCells(new CellData.FirebaseCallback() {
            @Override
            public void onCallBack(List<Cell> howToCells) {
                howToAdapter = new CellAdapter(howToCells);
                howToRecyclerView.setAdapter(howToAdapter);
            }
        });
        howToRecyclerView.setNestedScrollingEnabled(false);


        lensRecyclerView = view.findViewById(R.id.lensRecyclerView);
        lensRecyclerView.setLayoutManager(new LinearLayoutManager(getContext()));

        CellData.getLensCells(new CellData.FirebaseCallback() {
            @Override
            public void onCallBack(List<Cell> lensCells) {
                lensAdapter = new CellAdapter(lensCells);
                lensRecyclerView.setAdapter(lensAdapter);
            }
        });
        lensRecyclerView.setNestedScrollingEnabled(false);

        return view;
    }
}

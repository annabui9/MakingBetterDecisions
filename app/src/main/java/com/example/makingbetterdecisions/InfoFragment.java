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

    public InfoFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_info, container, false);

        // Fade-in animation
        view.startAnimation(AnimationUtils.loadAnimation(getContext(), R.anim.fade_in));

        // HOW TO section
        howToRecyclerView = view.findViewById(R.id.howToRecyclerView);
        howToRecyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        List<Cell> howToCells = CellData.getHowToCells();
        howToAdapter = new CellAdapter(howToCells);
        howToRecyclerView.setAdapter(howToAdapter);

        // LENSES section
        lensRecyclerView = view.findViewById(R.id.lensRecyclerView);
        lensRecyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        List<Cell> lensCells = CellData.getLensCells();
        lensAdapter = new CellAdapter(lensCells);
        lensRecyclerView.setAdapter(lensAdapter);

        return view;
    }
}

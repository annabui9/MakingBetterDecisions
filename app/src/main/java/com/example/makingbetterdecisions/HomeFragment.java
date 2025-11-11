package com.example.makingbetterdecisions;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;

import androidx.fragment.app.Fragment;

public class HomeFragment extends Fragment {

    public HomeFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_home, container, false);

        // Find the orb view
        ImageView orb = view.findViewById(R.id.animatedOrb);

        // Load and start the pulse animation
        Animation pulse = AnimationUtils.loadAnimation(getContext(), R.anim.pulse);
        orb.startAnimation(pulse);

        return view;
    }
}


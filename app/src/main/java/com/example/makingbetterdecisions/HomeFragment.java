package com.example.makingbetterdecisions;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationSet;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

public class HomeFragment extends Fragment {

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_home, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        ImageView orb = view.findViewById(R.id.animatedOrb);

        //enter button animations
        Button enterButton = view.findViewById(R.id.enterButton);
        Animation glow = AnimationUtils.loadAnimation(requireContext(), R.anim.button_glow);
        enterButton.startAnimation(glow);

        enterButton.setOnClickListener(v -> {
            Animation press = AnimationUtils.loadAnimation(requireContext(), R.anim.button_press);
            v.startAnimation(press);

            // add navigation for the button here!
        });


        orb.post(() -> {
            Animation pulse = AnimationUtils.loadAnimation(requireContext(), R.anim.pulse);
            Animation rotate = AnimationUtils.loadAnimation(requireContext(), R.anim.rotate_orb);

            AnimationSet animationSet = new AnimationSet(true);
            animationSet.addAnimation(pulse);
            animationSet.addAnimation(rotate);

            orb.startAnimation(animationSet);
        });
    }
}





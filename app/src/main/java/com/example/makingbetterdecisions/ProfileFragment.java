package com.example.makingbetterdecisions;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.FirebaseDatabase;

public class ProfileFragment extends Fragment {

    private FirebaseAuth auth;
    private FirebaseDatabase database;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_profile, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        auth = FirebaseAuth.getInstance();

        TextView emailText = view.findViewById(R.id.profileEmailText);
        LinearLayout previousAnswers = view.findViewById(R.id.previousAnswersCard);
        LinearLayout changePassword = view.findViewById(R.id.changePasswordCard);
        LinearLayout logout = view.findViewById(R.id.logoutCard);

        if (auth.getCurrentUser() != null) {
            emailText.setText(auth.getCurrentUser().getEmail());
        }

        previousAnswers.setOnClickListener(v -> {
            NavController navController = Navigation.findNavController(v);
            navController.navigate(R.id.action_profile_to_viewAnswers);
        });

        changePassword.setOnClickListener(v -> {
            Intent intent = new Intent(requireActivity(), ChangePasswordActivity.class);
            startActivity(intent);
        });

        logout.setOnClickListener(v -> {
            auth.signOut();
            Toast.makeText(getContext(), "logged out", Toast.LENGTH_SHORT).show();

            Intent intent = new Intent(requireActivity(), MainActivity.class);
            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
            startActivity(intent);
        });
    }
}







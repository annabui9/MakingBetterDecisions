package com.example.makingbetterdecisions;

import android.os.Bundle;
import android.text.TextUtils;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.auth.EmailAuthProvider;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class ChangePasswordActivity extends AppCompatActivity {

    FirebaseAuth auth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.change_password);

        auth = FirebaseAuth.getInstance();
        FirebaseUser user = auth.getCurrentUser();

        EditText currentPass = findViewById(R.id.currentPassword);
        EditText newPass = findViewById(R.id.newPassword);
        EditText confirmPass = findViewById(R.id.confirmPassword);

        Button changeBtn = findViewById(R.id.changePassButton);
        Button backBtn = findViewById(R.id.backToProfile);

        // update password
        changeBtn.setOnClickListener(v -> {
            String cur = currentPass.getText().toString().trim();
            String np = newPass.getText().toString().trim();
            String cp = confirmPass.getText().toString().trim();

            if (TextUtils.isEmpty(cur) || TextUtils.isEmpty(np) || TextUtils.isEmpty(cp)) {
                Toast.makeText(this, "fill all fields", Toast.LENGTH_SHORT).show();
                return;
            }

            if (!np.equals(cp)) {
                Toast.makeText(this, "passwords do not match", Toast.LENGTH_SHORT).show();
                return;
            }

            user.reauthenticate(EmailAuthProvider.getCredential(user.getEmail(), cur))
                    .addOnCompleteListener(task -> {
                        if (task.isSuccessful()) {
                            user.updatePassword(np).addOnCompleteListener(updateTask -> {
                                if (updateTask.isSuccessful()) {
                                    Toast.makeText(this, "password updated", Toast.LENGTH_SHORT).show();
                                } else {
                                    Toast.makeText(this, "update failed", Toast.LENGTH_SHORT).show();
                                }
                            });
                        } else {
                            Toast.makeText(this, "current password incorrect", Toast.LENGTH_SHORT).show();
                        }
                    });
        });

        // go back
        backBtn.setOnClickListener(v -> finish());
    }
}

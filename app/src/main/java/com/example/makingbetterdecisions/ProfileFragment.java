package com.example.makingbetterdecisions;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.example.makingbetterdecisions.model.User;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link ProfileFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class ProfileFragment extends Fragment {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    private FirebaseAuth auth;
    private FirebaseDatabase database;

    public ProfileFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment ProfileFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static ProfileFragment newInstance(String param1, String param2) {
        ProfileFragment fragment = new ProfileFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
        auth = FirebaseAuth.getInstance();
        database = FirebaseDatabase.getInstance();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_profile, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        Button store = view.findViewById(R.id.store);
        Button retrieve = view.findViewById(R.id.retrieve);
        EditText entry1 = view.findViewById(R.id.entry1);
        EditText entry2 = view.findViewById(R.id.entry2);
        TextView tv = view.findViewById(R.id.tvTV);

        store.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String uid = auth.getCurrentUser().getUid();
                DatabaseReference userRef = database.getReference("users").child(uid);

                userRef.addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot snapshot) {
                        User user = snapshot.getValue(User.class);
                        if(user != null) {
                            ArrayList<String> responses = user.getResponses();
                            if(responses == null) {
                                responses = new ArrayList<>();
                            }
                            responses.add(entry1.getText().toString().trim());
                            responses.add(entry2.getText().toString().trim());
                            user.setResponses(responses);

                            userRef.setValue(user);
                        }
                        else {
                            Toast.makeText(getContext(), "User is NULL!", Toast.LENGTH_SHORT).show();
                        }
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError error) {

                    }
                });
            }
        });
        retrieve.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String uid = auth.getCurrentUser().getUid();
                DatabaseReference userRef = database.getReference("users").child(uid);

                displaySavedAnswers();

                userRef.addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot snapshot) {
                        User user = snapshot.getValue(User.class);
                        if(user != null) {
                            ArrayList<String> responses = user.getResponses();
                            if(responses == null) {
                                Toast.makeText(getContext(), "Ermmm no data", Toast.LENGTH_SHORT).show();
                            }
                            else {
                                String gotText = "";
                                for(int i = 0; i < responses.size(); i++) {
                                    gotText = gotText.concat(responses.get(i));
                                }
                                tv.setText(gotText);
                            }
                        }
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError error) {

                    }
                });
            }
        });
    }
    private void displaySavedAnswers() {
        String uid = auth.getCurrentUser().getUid();
        DatabaseReference answersRef = database.getReference("users").child(uid).child("Apple and it's left-handed Users_answers");

        answersRef.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                LinearLayout container = getView().findViewById(R.id.answersContainer);
                container.removeAllViews(); // Clear existing views

                if (!snapshot.exists()) {
                    TextView noData = new TextView(getContext());
                    noData.setText("No saved answers yet");
                    noData.setTextSize(16);
                    container.addView(noData);
                    return;
                }

                // Loop through each use case
                for (DataSnapshot useCaseSnapshot : snapshot.getChildren()) {
                    String useCaseTitle = useCaseSnapshot.getKey();

                    // Use Case Title
                    TextView titleView = new TextView(getContext());
                    titleView.setText(useCaseTitle);
                    titleView.setTextSize(20);
                    titleView.setTypeface(null, android.graphics.Typeface.BOLD);
                    titleView.setPadding(0, 30, 0, 15);
                    container.addView(titleView);

                    // Loop through questions and answers
                    for (DataSnapshot qaSnapshot : useCaseSnapshot.getChildren()) {
                        String question = qaSnapshot.getKey();
                        String answer = qaSnapshot.getValue(String.class);

                        // Question
                        TextView questionView = new TextView(getContext());
                        questionView.setText("Q: " + question);
                        questionView.setTextSize(16);
                        questionView.setTypeface(null, android.graphics.Typeface.BOLD);
                        questionView.setPadding(0, 10, 0, 5);
                        container.addView(questionView);

                        // Answer
                        TextView answerView = new TextView(getContext());
                        answerView.setText("A: " + answer);
                        answerView.setTextSize(14);
                        answerView.setPadding(20, 0, 0, 15);
                        container.addView(answerView);
                    }

                    // Divider line
                    View divider = new View(getContext());
                    divider.setLayoutParams(new LinearLayout.LayoutParams(
                            LinearLayout.LayoutParams.MATCH_PARENT, 2));
                    divider.setBackgroundColor(0xFFCCCCCC);
                    container.addView(divider);
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Toast.makeText(getContext(), "Error loading answers", Toast.LENGTH_SHORT).show();
            }
        });
    }
}
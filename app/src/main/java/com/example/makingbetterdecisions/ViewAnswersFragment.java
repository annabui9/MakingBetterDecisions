package com.example.makingbetterdecisions;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.cardview.widget.CardView;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link ViewAnswersFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class ViewAnswersFragment extends Fragment {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";
    private FirebaseAuth auth;
    private FirebaseDatabase database;

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public ViewAnswersFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment ViewAnswersFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static ViewAnswersFragment newInstance(String param1, String param2) {
        ViewAnswersFragment fragment = new ViewAnswersFragment();
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
        return inflater.inflate(R.layout.fragment_view_answers, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        CardView Card1 = view.findViewById(R.id.Card1);
        CardView Card2 = view.findViewById(R.id.Card2);
        CardView Card3 = view.findViewById(R.id.Card3);

        Card1.setOnClickListener(v -> displayAnswers("Apple and it's left-handed Users_answers"));
    }

    private void displayAnswers(String title) {
        String uid = auth.getCurrentUser().getUid();
        DatabaseReference answersRef = database.getReference("users")
                .child(uid)
                .child(title);

        answersRef.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                LinearLayout container = getView().findViewById(R.id.answersContainer);
                container.removeAllViews();

                if (!snapshot.exists()) {
                    TextView noData = new TextView(getContext());
                    noData.setTextSize(18);
                    noData.setText("No saved answers for this use case yet");
                    container.addView(noData);
                    return;
                }
                for (DataSnapshot qaSnapshot : snapshot.getChildren()) {
                    String question = qaSnapshot.getKey();
                    String answer = qaSnapshot.getValue(String.class);

                    TextView questionView = new TextView(getContext());
                    questionView.setText("Q: " + question);
                    questionView.setTextSize(15);
                    container.addView(questionView);

                    TextView answerView = new TextView(getContext());
                    answerView.setText("A: " + answer);
                    answerView.setTextSize(12);
                    container.addView(answerView);
                }
            }
            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                    Toast.makeText(getContext(), "Error loading answers", Toast.LENGTH_SHORT).show();
                }
        });
    }
}
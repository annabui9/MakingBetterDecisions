package com.example.makingbetterdecisions;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.cardview.widget.CardView;
import androidx.fragment.app.Fragment;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
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

        CardView card1 = view.findViewById(R.id.Card1);
        CardView card2 = view.findViewById(R.id.Card2);
        CardView card3 = view.findViewById(R.id.Card3);
        Button back = view.findViewById(R.id.buttonBack);

        card1.setOnClickListener(v -> displayAnswers("apple_left_handed"));
        card2.setOnClickListener(v -> displayAnswers("deforestation"));
        card3.setOnClickListener(v -> displayAnswers("school_monitoring"));

        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                NavController navController = Navigation.findNavController(v);
                navController.popBackStack();
            }
        });
    }

    private void displayAnswers(String useCaseId) {
        String uid = auth.getCurrentUser().getUid();
        DatabaseReference answersRef = database.getReference("users")
                .child(uid)
                .child("answers")
                .child(useCaseId);

        answersRef.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                LinearLayout container = getView().findViewById(R.id.answersContainer);
                container.removeAllViews();

                if (!snapshot.exists()) {
                    TextView noData = new TextView(getContext());
                    noData.setTextSize(18);
                    noData.setText("No saved answers for this use case yet");
                    noData.setTextColor(0xFFFFFFFF);
                    container.addView(noData);
                    return;
                }
                for (DataSnapshot questionSnapshot : snapshot.getChildren()) {

                    String questionKey = questionSnapshot.getKey();

                    String answer = questionSnapshot.child("answer").getValue(String.class);

                    TextView qView = new TextView(getContext());
                    qView.setTextColor(0xFFFFFFFF); //sophia
                    qView.setText("Q: " + questionKey);
                    qView.setTextSize(15);
                    qView.setPadding(0, 10, 0, 3);
                    container.addView(qView);

                    TextView aView = new TextView(getContext());
                    aView.setTextColor(0xFFFFFFFF); //sophia
                    aView.setText("A: " + answer);
                    aView.setTextSize(13);
                    aView.setPadding(20, 0, 0, 10);
                    container.addView(aView);
                }
            }
            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                    Toast.makeText(getContext(), "Error loading answers", Toast.LENGTH_SHORT).show();
                }
        });
    }
}
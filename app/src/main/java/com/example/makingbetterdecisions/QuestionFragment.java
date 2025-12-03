package com.example.makingbetterdecisions;

import android.graphics.Bitmap;
import android.os.Bundle;
import android.os.Parcelable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.ScrollView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.print.PrintHelper;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class QuestionFragment extends Fragment {
    private FirebaseAuth auth;
    private FirebaseDatabase database;
    private List<UseCase> useCases;
    private int currentIndex;
    private HashMap<Question, String> answers = new HashMap<>();

    private TextView titleText;
    private LinearLayout questionContainer;
    private Button submitButton, backButton, nextButton, savePdfButton;

    public QuestionFragment() { }

    public static QuestionFragment newInstance(int index, ArrayList<UseCase> useCases) {
        QuestionFragment fragment = new QuestionFragment();
        Bundle args = new Bundle();
        args.putInt("index", index);
        args.putParcelableArrayList("useCases", useCases);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        auth = FirebaseAuth.getInstance();
        database = FirebaseDatabase.getInstance();

        if (getArguments() != null) {
            currentIndex = getArguments().getInt("index");
            ArrayList<? extends Parcelable> parcelables = getArguments().getParcelableArrayList("useCases");
            useCases = new ArrayList<>();
            if (parcelables != null) {
                for (Parcelable p : parcelables) {
                    useCases.add((UseCase) p);
                }
            }
        }
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater,
                             @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_question, container, false);

        titleText = view.findViewById(R.id.textQuestionTitle);
        questionContainer = view.findViewById(R.id.questionContainer);
        submitButton = view.findViewById(R.id.buttonSubmit);
        backButton = view.findViewById(R.id.buttonBack);
        nextButton = view.findViewById(R.id.buttonNext);
        savePdfButton = view.findViewById(R.id.buttonSavePdf);

        submitButton.setOnClickListener(v -> saveAnswers());
        savePdfButton.setOnClickListener(v -> printCurrentUseCase());

        backButton.setOnClickListener(v -> {
            saveAnswers();
            if (currentIndex > 0) {
                currentIndex--;
                displayCurrentUseCase();
            } else {
                requireActivity().getSupportFragmentManager().popBackStack();
            }
        });

        nextButton.setOnClickListener(v -> {
            saveAnswers();
            if (currentIndex < useCases.size() - 1) {
                currentIndex++;
                displayCurrentUseCase();
            }
        });

        displayCurrentUseCase();
        return view;
    }

    private void displayCurrentUseCase() {
        questionContainer.removeAllViews();
        UseCase current = useCases.get(currentIndex);

        // Title at top
        titleText.setText(current.getTitle());

        // Questions only
        for (Question q : current.getQuestions()) {
            TextView qText = new TextView(getContext());
            qText.setText(q.getText());
            qText.setTextSize(16);
            qText.setPadding(0, 20, 0, 10);
            questionContainer.addView(qText);

            if (q.isMultipleChoice()) {
                RadioGroup group = new RadioGroup(getContext());
                for (String option : q.getOptions()) {
                    RadioButton rb = new RadioButton(getContext());
                    rb.setText(option);
                    group.addView(rb);

                    if (answers.containsKey(q) && answers.get(q).equals(option)) {
                        rb.setChecked(true);
                    }
                }
                questionContainer.addView(group);
            } else {
                EditText answer = new EditText(getContext());
                answer.setHint("Type your response...");
                if (answers.containsKey(q)) {
                    answer.setText(answers.get(q));
                }
                questionContainer.addView(answer);
            }
        }
    }

    private void saveAnswers() {
        UseCase current = useCases.get(currentIndex);
        int childIndex = 0;

        // Skip question TextViews + inputs correctly
        for (Question q : current.getQuestions()) {
            View inputView = questionContainer.getChildAt(childIndex + 1);

            if (q.isMultipleChoice() && inputView instanceof RadioGroup) {
                RadioGroup group = (RadioGroup) inputView;
                int checkedId = group.getCheckedRadioButtonId();
                if (checkedId != -1) {
                    RadioButton rb = group.findViewById(checkedId);
                    answers.put(q, rb.getText().toString());
                }
            } else if (!q.isMultipleChoice() && inputView instanceof EditText) {
                EditText et = (EditText) inputView;
                answers.put(q, et.getText().toString());
            }

            childIndex += 2; // skip question + input
        }

        String uid = auth.getCurrentUser().getUid();
        DatabaseReference userRef = database.getReference("users").child(uid);

        // Convert HashMap<Question, String> to HashMap<String, String>
        HashMap<String, String> firebaseAnswers = new HashMap<>();
        for (Question q : answers.keySet()) {
            String key = sanitizeFirebaseKey(q.getText()); // Use question text as key
            String value = answers.get(q);
            firebaseAnswers.put(key, value);
        }

        // Save to Firebase
        userRef.child("answers").setValue(firebaseAnswers)
                .addOnSuccessListener(aVoid -> {
                    Toast.makeText(getContext(), "Answers saved!", Toast.LENGTH_SHORT).show();
                })
                .addOnFailureListener(e -> {
                    Toast.makeText(getContext(), "Error: " + e.getMessage(), Toast.LENGTH_SHORT).show();
                });
    }
    private String sanitizeFirebaseKey(String key) {
        if (key == null) return "unknown";

        // Replace invalid characters with underscores
        return key.replace(".", "_")
                .replace("/", "_")
                .replace("#", "_")
                .replace("$", "_")
                .replace("[", "_")
                .replace("]", "_")
                .trim();
    }

    private void printCurrentUseCase() {
        saveAnswers();

        if (getView() == null) return;
        ScrollView scrollView = getView().findViewById(R.id.questionScrollView);
        if (scrollView == null) return;

        scrollView.setDrawingCacheEnabled(true);
        scrollView.buildDrawingCache();
        Bitmap bitmap = Bitmap.createBitmap(scrollView.getDrawingCache());
        scrollView.setDrawingCacheEnabled(false);

        PrintHelper printHelper = new PrintHelper(getContext());
        printHelper.setScaleMode(PrintHelper.SCALE_MODE_FIT);
        printHelper.printBitmap(useCases.get(currentIndex).getTitle(), bitmap);
    }
}

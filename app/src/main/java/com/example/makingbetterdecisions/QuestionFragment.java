package com.example.makingbetterdecisions;

import android.graphics.Bitmap;
import android.os.Bundle;
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
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.print.PrintHelper;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class QuestionFragment extends Fragment {
    private FirebaseAuth auth;
    private FirebaseDatabase database;
    private HashMap<Question, String> answers = new HashMap<>();
    private String useCaseId;
    private List<Question> questions = new ArrayList<>();

    private TextView descriptionText;
    private TextView detailText;
    private TextView titleText;
    private LinearLayout questionContainer;
    private Button submitButton, backButton, savePdfButton;

    public QuestionFragment() { }


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        auth = FirebaseAuth.getInstance();
        database = FirebaseDatabase.getInstance();

        if (getArguments() != null) {
            useCaseId = getArguments().getString("useCaseId");
            loadUseCaseAndQuestions();

        }

    }


    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater,
                             @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_question, container, false);

        titleText = view.findViewById(R.id.textQuestionTitle);
        descriptionText = view.findViewById(R.id.textDescription);
        questionContainer = view.findViewById(R.id.questionContainer);
        submitButton = view.findViewById(R.id.buttonSubmit);
        backButton = view.findViewById(R.id.buttonBack);
        savePdfButton = view.findViewById(R.id.buttonSavePdf);

        submitButton.setOnClickListener(v -> saveAnswers());
        savePdfButton.setOnClickListener(v -> printCurrentUseCase());

        backButton.setOnClickListener(v -> {
            NavController navController = Navigation.findNavController(v);
            navController.popBackStack(R.id.navigation_usecases, false);
        });


        return view;
    }


    private void saveAnswers() {
        int childIndex = 0;
        for (int i = 0; i < questions.size(); i++) {

            // Each question card is at even indexes: 0, 2, 4, ...
            View cardView = questionContainer.getChildAt(i * 2);

            Question q = questions.get(i);

            if (!(cardView instanceof LinearLayout)) continue;

            LinearLayout card = (LinearLayout) cardView;

            // Child 0 = TextView (question)
            // Child 1 = EditText OR RadioGroup
            View answerView = card.getChildAt(1);

            if (q.isMultipleChoice() && answerView instanceof RadioGroup) {
                RadioGroup group = (RadioGroup) answerView;
                int checkedId = group.getCheckedRadioButtonId();

                if (checkedId != -1) {
                    RadioButton rb = group.findViewById(checkedId);
                    answers.put(q, rb.getText().toString());
                }
            }

            else if (!q.isMultipleChoice() && answerView instanceof EditText) {
                EditText et = (EditText) answerView;
                answers.put(q, et.getText().toString().trim());
            }

            for (Map.Entry<Question, String> entry : answers.entrySet()) {
                android.util.Log.d("ANSWER_DEBUG",
                        entry.getKey().getText() + " => " + entry.getValue());
            }

        }





        String uid = auth.getCurrentUser().getUid();
        DatabaseReference userRef = database.getReference("users").child(uid);

        // Convert HashMap<Question, String> to HashMap<String, String>
        HashMap<String, HashMap<String, String>> firebaseAnswers = new HashMap<>();
        for (Map.Entry<Question, String> entry : answers.entrySet()) {
            String questionKey = sanitizeFirebaseKey(entry.getKey().getText());
            String userAnswer = entry.getValue();

            // Inner map: "answer" -> actual answer string
            HashMap<String, String> innerMap = new HashMap<>();
            innerMap.put("answer", userAnswer);

            firebaseAnswers.put(questionKey, innerMap);
        }
//        for (Question q : answers.keySet()) {
//            String key = sanitizeFirebaseKey(q.getText()); // Use question text as key
//            String value = answers.get(q);
//            HashMap<String, String> inner = new HashMap<>();
//            inner.put("answer", value);
//            firebaseAnswers.put(key, inner);
//        }

        // Save to Firebase
        userRef.child("answers")
                .child(useCaseId)
                .setValue(firebaseAnswers)
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
        printHelper.printBitmap("Use Case Answers", bitmap);
    }
    private void loadUseCaseAndQuestions() {

        FirebaseDatabase.getInstance()
                .getReference("useCases")
                .child(useCaseId)
                .addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(DataSnapshot snapshot) {
                        UseCase uc = snapshot.getValue(UseCase.class);
                        if (uc == null) return;

                        titleText.setText(uc.getTitle());
                        descriptionText.setText(uc.getDescription());

                        loadQuestions(uc.getQuestionIds());

                    }

                    @Override
                    public void onCancelled(DatabaseError error) { }
                });
    }
    private void loadQuestions(List<String> questionIds) {

        FirebaseDatabase.getInstance()
                .getReference("questions")
                .addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(DataSnapshot snapshot) {

                        questions.clear();
                        questionContainer.removeAllViews();

                        for (String id : questionIds) {
                            Question q = snapshot.child(id).getValue(Question.class);
                            if (q != null) {
                                questions.add(q);
                                renderQuestion(q);
                            }
                        }
                    }

                    @Override
                    public void onCancelled(DatabaseError error) { }
                });
    }
    private void renderQuestion(Question q) {

        LinearLayout card = new LinearLayout(getContext());
        card.setOrientation(LinearLayout.VERTICAL);
        card.setBackgroundResource(R.drawable.question_card);
        card.setPadding(30, 30, 30, 30);

        TextView qText = new TextView(getContext());
        qText.setText(q.getText());
        qText.setTextSize(17);
        qText.setTextColor(0xFF0A1A2F);
        qText.setTypeface(getResources().getFont(R.font.logo_font));
        qText.setPadding(0, 0, 0, 18);

        card.addView(qText);

        if (q.isMultipleChoice()) {

            RadioGroup group = new RadioGroup(getContext());
            group.setPadding(0, 10, 0, 10);

            for (String option : q.getOptions()) {
                RadioButton rb = new RadioButton(getContext());
                rb.setText(option);
                rb.setTextColor(0xFF0A1A2F);
                rb.setTypeface(getResources().getFont(R.font.logo_font));
                rb.setButtonTintList(
                        new android.content.res.ColorStateList(
                                new int[][]{
                                        new int[]{android.R.attr.state_checked},
                                        new int[]{}
                                },
                                new int[]{
                                        0xFF1A4BFF,
                                        0xFFAAAAAA
                                }
                        )
                );
                group.addView(rb);
            }

            card.addView(group);

        } else {

            EditText answer = new EditText(getContext());
            answer.setHint("Type your response...");
            answer.setTextColor(0xFF0A1A2F);
            answer.setHintTextColor(0x660A1A2F);
            answer.setBackgroundResource(R.drawable.input_box);
            answer.setPadding(30, 25, 30, 25);

            card.addView(answer);
        }

        questionContainer.addView(card);

        View spacer = new View(getContext());
        spacer.setLayoutParams(new LinearLayout.LayoutParams(
                LinearLayout.LayoutParams.MATCH_PARENT,
                28
        ));
        questionContainer.addView(spacer);
    }



}

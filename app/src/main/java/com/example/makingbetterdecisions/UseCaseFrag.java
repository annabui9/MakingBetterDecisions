package com.example.makingbetterdecisions;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class UseCaseFrag extends Fragment {

    public UseCaseFrag() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_use_cases, container, false);

        RecyclerView recyclerView = view.findViewById(R.id.recyclerViewUseCases);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));

        List<UseCase> useCaseList = new ArrayList<>();

      // Case 1
        useCaseList.add(new UseCase(
                "Apple and it's left-handed Users",           // title
                "Apple left-handed users have found the iPad and iPhone navigation to be difficult.", // description
                "Technology, Media and Telecommunications",   // industry
                "Apple left-handed users have found the iPad and iPhone navigation to be difficult and unfriendly.", // scenario
                "Left-handed users have frequently commented on the iPhone’s inaccessibility..." , // details
                Arrays.asList(
                        new Question("What are the intended goals & benefits of the product/service/technology/AI?",null, false),
                        new Question("Who are the affected Stakeholders (please create a bulleted list in no particular order)?", null, false),
                        new Question("Hierarchy of Stakeholders (can you place the stakeholders in a prioritized list? You can focus on the top three for discussion purposes)", null, false),
                        new Question( "Consider the Ethical Lenses (used with permission from the Markkula Center for Applied Ethics at Santa Clara University) in the Info section," +
                                "assess the use case through each (what are the main considerations?):", new ArrayList<>(),
                                false
                        ),

                        //lens prompts
                        new Question("Rights:", null, false),
                        new Question("Justice/Fairness:", null, false),
                        new Question("Utilitarianism:", null, false),
                        new Question("Common Good:", null, false),
                        new Question("Virtue:", null, false),
                        new Question("Care Ethics:", null, false),
                        new Question(
                                "Choose the ethical lens that you believe was most important for your decision.",
                                Arrays.asList("Rights", "Justice/Fairness", "Utilitarianism", "Common Good", "Virtue", "Care Ethics"),
                                true
                        ),

                        // Ethical risk questions
                        new Question("Assess the use case through ethical risks (based on guiding principles in applied ethics—this list is neither exclusive nor exhaustive):", null, false),
                        new Question("Privacy (autonomy, confidentiality, discretion, consent):", null, false),
                        new Question("Transparency/Explainability (justifiable, interpretable, auditable, visible):", null, false),
                        new Question("Fair/Impartial (accessible, equitable outcomes, inclusively designed, unbiased):", null, false),
                        new Question("Responsible (humane, common/social good, sustainability focused):", null, false),
                        new Question("Accountable (answerable, resolvable, ownership):", null, false),
                        new Question("Robust/Reliable (accurate, consistent, predictable):", null, false),
                        new Question("Safe/Secure (user protection/user friendly, protected, inimitable):", null, false),

                        // Multiple-choice: most important ethical risk
                        new Question(
                                "Choose the ethical risk that you believe was most important for your decision.",
                                Arrays.asList("Privacy", "Transparency/Explainability", "Fair/Impartial", "Responsible", "Accountable", "Robust/Reliable", "Safe/Secure"),
                                true
                        ),

                        new Question("Assess the use case and your analysis above and offer mitigations and/or recommendations to the business/organization/group:", null, false)
                )
        ));


        // Case 2
        useCaseList.add(new UseCase(
                "Data Monitoring in Schools",                                 // title
                "The market for technologies that monitor students has grown rapidly.", // description
                "Government & Public Sector",                                 // industry
                "The market for technologies that can monitor communications of millions of students has grown rapidly through emails, chats, and social media.", // scenario
                "A state's Department of Education is implementing a data monitoring technology across schools. It combines AI and human moderators to scan emails and notifications for threats. It helps reduce violence, bullying, and suicidal behavior but raises privacy concerns.", // details
                Arrays.asList(
                        new Question("What are the intended goals & benefits of the product/service/technology/AI?",null, false),
                        new Question("Who are the affected Stakeholders (please create a bulleted list in no particular order)?", null, false),
                        new Question("Hierarchy of Stakeholders (can you place the stakeholders in a prioritized list? You can focus on the top three for discussion purposes)", null, false),
                        new Question( "Consider the Ethical Lenses (used with permission from the Markkula Center for Applied Ethics at Santa Clara University) in the Info section," +
                                "assess the use case through each (what are the main considerations?):", new ArrayList<>(),
                                false
                        ),

                        //lens prompts
                        new Question("Rights:", null, false),
                        new Question("Justice/Fairness:", null, false),
                        new Question("Utilitarianism:", null, false),
                        new Question("Common Good:", null, false),
                        new Question("Virtue:", null, false),
                        new Question("Care Ethics:", null, false),

                        new Question(
                                "Choose the ethical lens that you believe was most important for your decision.",
                                Arrays.asList("Rights", "Justice/Fairness", "Utilitarianism", "Common Good", "Virtue", "Care Ethics"),
                                true
                        ),

                        // Ethical risk questions
                        new Question("Assess the use case through ethical risks (based on guiding principles in applied ethics—this list is neither exclusive nor exhaustive):", null, false),
                        new Question("Privacy (autonomy, confidentiality, discretion, consent):", null, false),
                        new Question("Transparency/Explainability (justifiable, interpretable, auditable, visible):", null, false),
                        new Question("Fair/Impartial (accessible, equitable outcomes, inclusively designed, unbiased):", null, false),
                        new Question("Responsible (humane, common/social good, sustainability focused):", null, false),
                        new Question("Accountable (answerable, resolvable, ownership):", null, false),
                        new Question("Robust/Reliable (accurate, consistent, predictable):", null, false),
                        new Question("Safe/Secure (user protection/user friendly, protected, inimitable):", null, false),


                        new Question(
                                "Choose the ethical risk that you believe was most important for your decision.",
                                Arrays.asList("Privacy", "Transparency/Explainability", "Fair/Impartial", "Responsible", "Accountable", "Robust/Reliable", "Safe/Secure"),
                                true
                        ),

                        new Question("Assess the use case and your analysis above and offer mitigations and/or recommendations to the business/organization/group:", null, false)
                )
        ));

        // Case 3
        useCaseList.add(new UseCase(
                "Environmental Monitoring to Prevent Deforestation",        // title
                "Drones are used to track biodiversity and deforestation.", // description
                "Government and Public Services",                           // industry
                "Deforestation has persisted despite conservationists’ efforts. Drones can help monitor forests, count wildlife, and deliver seeds.", // scenario
                "A non-profit wants to use crowdsourced drone images to track deforestation. While it increases awareness, it may also be exploited by poachers. Ethical concerns include misuse of public data and privacy of communities.", // details
                Arrays.asList(
                        new Question("What are the intended goals & benefits of the product/service/technology/AI?",null, false),
                        new Question("Who are the affected Stakeholders (please create a bulleted list in no particular order)?", null, false),
                        new Question("Hierarchy of Stakeholders (can you place the stakeholders in a prioritized list? You can focus on the top three for discussion purposes)", null, false),
                        new Question( "Consider the Ethical Lenses (used with permission from the Markkula Center for Applied Ethics at Santa Clara University) in the Info section," +
                                "assess the use case through each (what are the main considerations?):", new ArrayList<>(),
                                false
                        ),

                        //lens prompts
                        new Question("Rights:", null, false),
                        new Question("Justice/Fairness:", null, false),
                        new Question("Utilitarianism:", null, false),
                        new Question("Common Good:", null, false),
                        new Question("Virtue:", null, false),
                        new Question("Care Ethics:", null, false),
                        new Question(
                                "Choose the ethical lens that you believe was most important for your decision.",
                                Arrays.asList("Rights", "Justice/Fairness", "Utilitarianism", "Common Good", "Virtue", "Care Ethics"),
                                true
                        ),

                        // Ethical risk questions
                        new Question("Assess the use case through ethical risks (based on guiding principles in applied ethics—this list is neither exclusive nor exhaustive):", null, false),
                        new Question("Privacy (autonomy, confidentiality, discretion, consent):", null, false),
                        new Question("Transparency/Explainability (justifiable, interpretable, auditable, visible):", null, false),
                        new Question("Fair/Impartial (accessible, equitable outcomes, inclusively designed, unbiased):", null, false),
                        new Question("Responsible (humane, common/social good, sustainability focused):", null, false),
                        new Question("Accountable (answerable, resolvable, ownership):", null, false),
                        new Question("Robust/Reliable (accurate, consistent, predictable):", null, false),
                        new Question("Safe/Secure (user protection/user friendly, protected, inimitable):", null, false),

                        // Multiple-choice: most important ethical risk
                        new Question(
                                "Choose the ethical risk that you believe was most important for your decision.",
                                Arrays.asList("Privacy", "Transparency/Explainability", "Fair/Impartial", "Responsible", "Accountable", "Robust/Reliable", "Safe/Secure"),
                                true
                        ),

                        new Question("Assess the use case and your analysis above and offer mitigations and/or recommendations to the business/organization/group:", null, false)

                )
        ));

        UseCaseAdapter adapter = new UseCaseAdapter(getParentFragmentManager(), useCaseList);
        recyclerView.setAdapter(adapter);

        return view;
    }
}


package com.example.makingbetterdecisions;

import android.os.Bundle;
import android.text.SpannableString;
import android.text.Spanned;
import android.text.style.UnderlineSpan;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

public class UseCaseAdapter extends RecyclerView.Adapter<UseCaseAdapter.ViewHolder> {

    private final List<UseCase> useCases;
    private final FragmentManager fragmentManager;

    // Card colors
    private final int[] colors = {0xFFE6F0FF, 0xFFCCE2FF, 0xFFB3D4FF, 0xFF99C6FF};

    public UseCaseAdapter(FragmentManager fm, List<UseCase> useCases) {
        this.fragmentManager = fm;
        this.useCases = useCases;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_use_case, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        UseCase useCase = useCases.get(position);

        // Title
        holder.textTitle.setText((position + 1) + ". " + useCase.getTitle());

        // Industry with underline
        String industryLabel = "Industry";
        String industryValue = ": " + useCase.getIndustry();
        SpannableString ssIndustry = new SpannableString(industryLabel + industryValue);
        ssIndustry.setSpan(new UnderlineSpan(), 0, industryLabel.length(), Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
        holder.textIndustry.setText(ssIndustry);

        // Scenario with underline
        String scenarioLabel = "Scenario";
        String scenarioValue = ": " + useCase.getScenario();
        SpannableString ssScenario = new SpannableString(scenarioLabel + scenarioValue);
        ssScenario.setSpan(new UnderlineSpan(), 0, scenarioLabel.length(), Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
        holder.textScenario.setText(ssScenario);

        // Card background
        holder.cardContainer.setBackgroundColor(colors[position % colors.length]);

        // View Details
        holder.buttonDetails.setOnClickListener(v -> {
            android.util.Log.d("UseCaseAdapter", "=== BUTTON CLICKED ===");
            android.util.Log.d("UseCaseAdapter", "Position: " + position);
            android.util.Log.d("UseCaseAdapter", "useCases is null? " + (useCases == null));
            android.util.Log.d("UseCaseAdapter", "useCases size: " + useCases.size());
            android.util.Log.d("UseCaseAdapter", "UseCase title: " + useCases.get(position).getTitle());

            Bundle bundle = new Bundle();
            bundle.putInt("index", position);
            bundle.putParcelableArrayList("useCases", new ArrayList<>(useCases));

            android.util.Log.d("UseCaseAdapter", "Bundle keys: " + bundle.keySet());
            android.util.Log.d("UseCaseAdapter", "Bundle index: " + bundle.getInt("index"));

            ArrayList<UseCase> test = bundle.getParcelableArrayList("useCases");
            android.util.Log.d("UseCaseAdapter", "Bundle useCases retrieved size: " + (test != null ? test.size() : "NULL"));

            NavController navController = Navigation.findNavController(v);
            navController.navigate(R.id.action_use_to_question, bundle);

            android.util.Log.d("UseCaseAdapter", "Navigation executed");
        });
//        holder.buttonDetails.setOnClickListener(v -> {
//            Fragment questionFragment = QuestionFragment.newInstance(position, new ArrayList<>(useCases));
//            fragmentManager.beginTransaction()
//                    .replace(R.id.question_fragment_container, questionFragment)
//                    .addToBackStack(null)
//                    .commit();
//
//        });
    }

    @Override
    public int getItemCount() {
        return useCases.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        LinearLayout cardContainer;
        TextView textTitle, textIndustry, textScenario;
        Button buttonDetails;

        public ViewHolder(View itemView) {
            super(itemView);
            cardContainer = itemView.findViewById(R.id.cardContainer);
            textTitle = itemView.findViewById(R.id.textTitle);
            textIndustry = itemView.findViewById(R.id.textIndustry);
            textScenario = itemView.findViewById(R.id.textScenario);
            buttonDetails = itemView.findViewById(R.id.buttonDetails);
        }
    }
}

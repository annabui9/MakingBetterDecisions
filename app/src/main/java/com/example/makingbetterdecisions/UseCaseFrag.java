package com.example.makingbetterdecisions;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

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
        UseCaseAdapter adapter = new UseCaseAdapter(getParentFragmentManager(), useCaseList);
        recyclerView.setAdapter(adapter);

        FirebaseDatabase.getInstance()
                .getReference("useCases")
                .addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(DataSnapshot snapshot) {
                        useCaseList.clear();
                        for (DataSnapshot caseSnap : snapshot.getChildren()) {
                            UseCase uc = caseSnap.getValue(UseCase.class);
                            if (uc != null) {
                                uc.setId(caseSnap.getKey()); // ADD id field
                                useCaseList.add(uc);
                            }
                        }
                        adapter.notifyDataSetChanged();
                    }

                    @Override
                    public void onCancelled(DatabaseError error) { }
                });


        return view;
    }

}


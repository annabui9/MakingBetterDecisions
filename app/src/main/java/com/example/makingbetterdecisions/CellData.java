package com.example.makingbetterdecisions;

import android.util.Log;
import android.widget.Toast;

import androidx.annotation.NonNull;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class CellData {

    private static final DatabaseReference db = FirebaseDatabase.getInstance().getReference();

    public static void getHowToCells(FirebaseCallback callback) {
        db.child("how_to_cells").addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                Log.d("FIREBASE", "how_to_cell exists: " + snapshot.exists());
                Log.d("FIREBASE", "how_to_cell count: " + snapshot.getChildrenCount());

                List<Cell> list = new ArrayList<>();

                for(DataSnapshot child : snapshot.getChildren()){
                    Cell cell = child.getValue(Cell.class);
                    if(cell != null){
                        list.add(cell);
                    }
                }
                callback.onCallBack(list);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                //perhaps a toast here
                callback.onCallBack(new ArrayList<Cell>());
            }
        });
    }

    public static void getLensCells(FirebaseCallback callback){
        db.child("lens_cells").addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                Log.d("FIREBASE", "lens_cell exists: " + snapshot.exists());
                Log.d("FIREBASE", "lens_cell count: " + snapshot.getChildrenCount());

                List<Cell> list = new ArrayList<>();

                for(DataSnapshot child : snapshot.getChildren()){
                    Cell cell = child.getValue(Cell.class);
                    if(cell != null){
                        list.add(cell);
                    }
                }
                callback.onCallBack(list);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                //perhaps a toast here
                callback.onCallBack(new ArrayList<Cell>());
            }
        });

    }

    public interface FirebaseCallback{
        void onCallBack(List<Cell> list);
    }
}

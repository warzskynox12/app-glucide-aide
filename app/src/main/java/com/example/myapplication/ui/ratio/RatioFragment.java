package com.example.myapplication.ui.ratio;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.navigation.Navigation;

import com.example.myapplication.R;
import com.example.myapplication.databinding.FragmentRatioBinding;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;

public class RatioFragment extends Fragment {

    private static final String TAG = "RatioFragment";
    private FragmentRatioBinding binding;
    private FirebaseAuth mAuth;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mAuth = FirebaseAuth.getInstance();
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        binding = FragmentRatioBinding.inflate(inflater, container, false);
        View root = binding.getRoot();

        FirebaseUser currentUser = mAuth.getCurrentUser();

        if (currentUser != null) {
            String userId = currentUser.getUid();
            FirebaseFirestore db = FirebaseFirestore.getInstance();
            DocumentReference docRef = db.collection("ratio").document(userId);

            docRef.get().addOnCompleteListener(task -> {
                if (task.isSuccessful()) {
                    DocumentSnapshot document = task.getResult();
                    if (document != null && document.exists()) {
                        String bolusma = document.getString("bolusma");
                        String bolusmi = document.getString("bolusmi");
                        String bolusgo = document.getString("bolusgo");
                        String bolusso = document.getString("bolusso");

                        Log.d(TAG, "Document data retrieved successfully");
                        binding.tvRatioma.setText("Ratio Matin : "+bolusma);
                        binding.tvRatiomi.setText("Ratio Midi : "+bolusmi);
                        binding.tvRatiogo.setText("Ratio Gouter : "+bolusgo);
                        binding.tvRatioso.setText("Ratio Soir : "+bolusso);
                    } else {
                        Log.d(TAG, "No such document");
                        Toast.makeText(getContext(), "No data found for the user.", Toast.LENGTH_SHORT).show();
                    }
                } else {
                    Log.d(TAG, "get failed with ", task.getException());
                    Toast.makeText(getContext(), "Failed to retrieve data.", Toast.LENGTH_SHORT).show();
                }
            });
        } else {
            Log.d(TAG, "No user is signed in");
            Toast.makeText(getContext(), "No user is signed in.", Toast.LENGTH_SHORT).show();
        }

        binding.btnUpload.setOnClickListener(v -> {
            udateRatio();
        });

        return root;
    }

    private void udateRatio() {
        String bolusma = binding.etRatioma.getText().toString();
        String bolusmi = binding.etRatiomi.getText().toString();
        String bolusgo = binding.etRatiogo.getText().toString();
        String bolusso = binding.etRatioso.getText().toString();
        String userId = mAuth.getCurrentUser().getUid();
        FirebaseFirestore db = FirebaseFirestore.getInstance();
        DocumentReference docRef = db.collection("ratio").document(userId);
        if (!bolusma.isEmpty()) {
            docRef.update("bolusma", bolusma)
                    .addOnSuccessListener(aVoid -> {
                        Log.d(TAG, "bolusma updated");
                        if (binding != null) {
                            reloadFragment();
                        }
                    })
                    .addOnFailureListener(e -> Log.w(TAG, "Error updating bolusma", e));
        }
        if (!bolusmi.isEmpty()) {
            docRef.update("bolusmi", bolusmi)
                    .addOnSuccessListener(aVoid -> {
                        Log.d(TAG, "bolusmi updated");
                        if (binding != null) {
                            reloadFragment();
                        }
                    })
                    .addOnFailureListener(e -> Log.w(TAG, "Error updating bolusmi", e));
        }
        if (!bolusgo.isEmpty()) {
            docRef.update("bolusgo", bolusgo)
                    .addOnSuccessListener(aVoid -> {
                        Log.d(TAG, "bolusgo updated");
                        if (binding != null) {
                            reloadFragment();
                        }
                    })
                    .addOnFailureListener(e -> Log.w(TAG, "Error updating bolusgo", e));
        }
        if (!bolusso.isEmpty()) {
            docRef.update("bolusso", bolusso)
                    .addOnSuccessListener(aVoid -> {
                        Log.d(TAG, "bolusso updated");
                        if (binding != null) {
                            reloadFragment();
                        }
                    })
                    .addOnFailureListener(e -> Log.w(TAG, "Error updating bolusso", e));
        }
    }

    private void reloadFragment() {
        Navigation.findNavController(binding.getRoot()).navigate(R.id.nav_ratio);
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
}
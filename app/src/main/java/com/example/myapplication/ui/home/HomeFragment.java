package com.example.myapplication.ui.home;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.Navigation;
import com.squareup.picasso.Picasso;

import com.example.myapplication.R;
import com.example.myapplication.databinding.FragmentHomeBinding;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import java.util.UUID;

public class HomeFragment extends Fragment {

    private static final int PICK_IMAGE_REQUEST = 1;
    private FragmentHomeBinding binding;
    private FirebaseAuth mAuth;
    private static final String TAG = "HomeFragment";
    private Uri imageUri;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mAuth = FirebaseAuth.getInstance();
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        binding = FragmentHomeBinding.inflate(inflater, container, false);
        View root = binding.getRoot();

        binding.logoutButton.setOnClickListener(v -> {
            mAuth.signOut();
            root.post(() ->Navigation.findNavController(root).navigate(R.id.nav_signin));
        });

        FirebaseUser currentUser = mAuth.getCurrentUser();
        if (currentUser == null) {
            Log.d(TAG, "User not authenticated, navigating to SigninFragment");
            root.post(() -> Navigation.findNavController(root).navigate(R.id.nav_signin));
            return root;
        }

        String userId = currentUser.getUid();
        FirebaseFirestore db = FirebaseFirestore.getInstance();
        DocumentReference docRef = db.collection("users").document(userId);

        docRef.get().addOnCompleteListener(task -> {
            if (task.isSuccessful()) {
                DocumentSnapshot document = task.getResult();
                if (document.exists()) {
                    String name = document.getString("firstName") + " " + document.getString("lastName");
                    String email = document.getString("email");
                    String photoURL = document.getString("photoURL");
                    binding.textView1.setText(name);
                    binding.textView2.setText(email);
                    if (photoURL != null && !photoURL.isEmpty()) {
                        Picasso.get().load(photoURL).into(binding.imageView);
                    }
                } else {
                    Log.d(TAG, "No such document");
                }
            } else {
                Log.w(TAG, "Error getting documents.", task.getException());
            }
        });

        setupButtonListeners(db, userId);

        return root;
    }

    private void setupButtonListeners(FirebaseFirestore db, String userId) {
        binding.buttonnom.setOnClickListener(v -> {
            String newNom = binding.editnom.getText().toString();
            db.collection("users").document(userId).update("firstName", newNom)
                    .addOnSuccessListener(aVoid -> {
                        Log.d(TAG, "First name updated");
                        reloadFragment();
                    })
                    .addOnFailureListener(e -> Log.w(TAG, "Error updating first name", e));
        });

        binding.buttonprenom.setOnClickListener(v -> {
            String newPrenom = binding.editnprenom.getText().toString();
            db.collection("users").document(userId).update("lastName", newPrenom)
                    .addOnSuccessListener(aVoid -> {
                        Log.d(TAG, "Last name updated");
                        reloadFragment();
                    })
                    .addOnFailureListener(e -> Log.w(TAG, "Error updating last name", e));
        });

        binding.buttonemail.setOnClickListener(v -> {
            String newEmail = binding.editnemail.getText().toString();
            db.collection("users").document(userId).update("email", newEmail)
                    .addOnSuccessListener(aVoid -> {
                        Log.d(TAG, "Email updated");
                        reloadFragment();
                    })
                    .addOnFailureListener(e -> Log.w(TAG, "Error updating email", e));
        });

        binding.buttonpass.setOnClickListener(v -> {
            String newPass = binding.editnpass.getText().toString();
            db.collection("users").document(userId).update("password", newPass)
                    .addOnSuccessListener(aVoid -> {
                        Log.d(TAG, "Password updated");
                        reloadFragment();
                    })
                    .addOnFailureListener(e -> Log.w(TAG, "Error updating password", e));
        });

        binding.buttonimage.setOnClickListener(v -> openFileChooser());
    }

    private void openFileChooser() {
        Intent intent = new Intent(Intent.ACTION_OPEN_DOCUMENT);
        intent.setType("image/*");
        startActivityForResult(intent, PICK_IMAGE_REQUEST);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == PICK_IMAGE_REQUEST && resultCode == getActivity().RESULT_OK
                && data != null && data.getData() != null) {
            imageUri = data.getData();
            uploadImageToFirebase();
        }
    }

    private void uploadImageToFirebase() {
        if (imageUri != null) {
            StorageReference storageReference = FirebaseStorage.getInstance().getReference("uploads");
            StorageReference fileReference = storageReference.child(UUID.randomUUID().toString());
            fileReference.putFile(imageUri)
                    .addOnSuccessListener(taskSnapshot -> fileReference.getDownloadUrl().addOnSuccessListener(uri -> {
                        String downloadUrl = uri.toString();
                        updatePhotoUrlInFirestore(downloadUrl);
                    }))
                    .addOnFailureListener(e -> Log.w(TAG, "Error uploading image", e));
        }
    }

    private void updatePhotoUrlInFirestore(String downloadUrl) {
        String userId = mAuth.getCurrentUser().getUid();
        FirebaseFirestore db = FirebaseFirestore.getInstance();
        db.collection("users").document(userId).update("photoURL", downloadUrl)
                .addOnSuccessListener(aVoid -> {
                    Log.d(TAG, "Photo URL updated");
                    reloadFragment();
                })
                .addOnFailureListener(e -> Log.w(TAG, "Error updating photo URL", e));
    }

    private void reloadFragment() {
        Navigation.findNavController(binding.getRoot()).navigate(R.id.nav_home);
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
}
package com.example.myapplication.ui.calculaite;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import com.example.myapplication.databinding.FragmentCalculaiteBinding;

public class CalculaiteFragment extends Fragment {

    private FragmentCalculaiteBinding binding;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        CalculaiteViewModel calculaiteViewModel =
                new ViewModelProvider(this).get(CalculaiteViewModel.class);

        binding = FragmentCalculaiteBinding.inflate(inflater, container, false);
        View root = binding.getRoot();

        final TextView textView = binding.textCalculaite;
        calculaiteViewModel.getText().observe(getViewLifecycleOwner(), textView::setText);
        return root;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
}

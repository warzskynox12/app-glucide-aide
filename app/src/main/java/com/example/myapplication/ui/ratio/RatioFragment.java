package com.example.myapplication.ui.ratio;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import com.example.myapplication.databinding.FragmentRatioBinding;

public class RatioFragment extends Fragment {

    private FragmentRatioBinding binding;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        RatioViewModel ratioViewModel =
                new ViewModelProvider(this).get(RatioViewModel.class);

        binding = FragmentRatioBinding.inflate(inflater, container, false);
        View root = binding.getRoot();

        final TextView textView = binding.textRatio;
        ratioViewModel.getText().observe(getViewLifecycleOwner(), textView::setText);
        return root;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
}
package com.github.petkovicdanilo.ghviewer.view.fragment;

import android.os.Bundle;

import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.github.petkovicdanilo.ghviewer.R;
import com.github.petkovicdanilo.ghviewer.databinding.FragmentCreateRepositoryBinding;
import com.github.petkovicdanilo.ghviewer.databinding.FragmentRepositoryBinding;
import com.github.petkovicdanilo.ghviewer.viewmodel.CreateRepositoryViewModel;

public class CreateRepositoryFragment extends Fragment {

    private CreateRepositoryViewModel viewModel;
    private FragmentCreateRepositoryBinding binding;

    public CreateRepositoryFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_create_repository, container, false);
        binding.setLifecycleOwner(getViewLifecycleOwner());
        View view = binding.getRoot();

        viewModel = new ViewModelProvider(requireActivity()).get(CreateRepositoryViewModel.class);

        binding.setViewModel(viewModel);

        return view;
    }
}
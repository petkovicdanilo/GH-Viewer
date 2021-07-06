package com.github.petkovicdanilo.ghviewer.view.fragment;

import android.os.Bundle;

import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.fragment.NavHostFragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.github.petkovicdanilo.ghviewer.R;
import com.github.petkovicdanilo.ghviewer.databinding.FragmentCreateRepositoryBinding;
import com.github.petkovicdanilo.ghviewer.databinding.FragmentRepositoryBinding;
import com.github.petkovicdanilo.ghviewer.view.util.BottomNav;
import com.github.petkovicdanilo.ghviewer.viewmodel.CreateRepositoryViewModel;
import com.google.android.material.snackbar.Snackbar;

public class CreateRepositoryFragment extends Fragment implements CreateRepositoryViewModel.OnCreateRepositoryListener {

    private CreateRepositoryViewModel viewModel;
    private FragmentCreateRepositoryBinding binding;

    private static final String TAG = "CreateRepoFragment";
    
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
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_create_repository,
                container, false);
        binding.setLifecycleOwner(getViewLifecycleOwner());
        View view = binding.getRoot();

        viewModel = new ViewModelProvider(requireActivity()).get(CreateRepositoryViewModel.class);
        viewModel.setOnCreateRepositoryListener(this);

        binding.setViewModel(viewModel);

        return view;
    }

    @Override
    public void onResume() {
        super.onResume();
        BottomNav.unselect(requireActivity());
    }

    @Override
    public void onRepositoryCreated() {
        Log.i(TAG, "Repository created");
        Toast.makeText(getContext(), "Repository created", Toast.LENGTH_LONG).show();
        NavHostFragment.findNavController(this).popBackStack();
    }

    @Override
    public void onRepositoryCreationFail(String message) {
        Log.e(TAG, message);
        Toast.makeText(getContext(), "Error: " + message, Toast.LENGTH_LONG).show();
    }
}
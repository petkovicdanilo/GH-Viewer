package com.github.petkovicdanilo.ghviewer.view.fragment;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.github.petkovicdanilo.ghviewer.R;
import com.github.petkovicdanilo.ghviewer.databinding.FragmentRepositoriesBinding;
import com.github.petkovicdanilo.ghviewer.view.adapter.RepositoriesAdapter;
import com.github.petkovicdanilo.ghviewer.viewmodel.MyRepositoriesViewModel;

public class RepositoriesFragment extends Fragment {

    private static final String TAG = "RepositoriesFragment";

    private MyRepositoriesViewModel viewModel;
    private RepositoriesAdapter adapter;

    private FragmentRepositoriesBinding binding;

    public RepositoriesFragment() {
        // Required empty public constructor
    }

    public static RepositoriesFragment newInstance() {
        RepositoriesFragment fragment = new RepositoriesFragment();
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        binding = FragmentRepositoriesBinding.inflate(inflater, container, false);
        View view = binding.getRoot();

        viewModel = new ViewModelProvider(requireActivity()).get(MyRepositoriesViewModel.class);
        viewModel.getMyRepositories().observe(getViewLifecycleOwner(),
                activities -> updateAdapter());
        updateAdapter();

        if (viewModel.getNextPage() == 1) {
            viewModel.loadNextPage();
        }

        binding.btnMyRepositoriesLoadMore.setOnClickListener(v -> viewModel.loadNextPage());

        return view;
    }

    private void updateAdapter() {
        adapter = new RepositoriesAdapter(viewModel.getMyRepositories().getValue());
        binding.myRepositories.setLayoutManager(new LinearLayoutManager(getContext()));
        binding.myRepositories.setAdapter(adapter);
    }
}
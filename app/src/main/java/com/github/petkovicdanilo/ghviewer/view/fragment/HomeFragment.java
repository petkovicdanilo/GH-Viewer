package com.github.petkovicdanilo.ghviewer.view.fragment;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.github.petkovicdanilo.ghviewer.view.adapter.ActivitiesAdapter;
import com.github.petkovicdanilo.ghviewer.databinding.FragmentHomeBinding;
import com.github.petkovicdanilo.ghviewer.viewmodel.ActivitiesViewModel;

public class HomeFragment extends Fragment {

    private static String TAG = "HOME_FR";

    private ActivitiesViewModel viewModel;
    private ActivitiesAdapter adapter;

    private FragmentHomeBinding binding;


    public HomeFragment() {
        // Required empty public constructor
    }

    public static HomeFragment newInstance(String param1, String param2) {
        HomeFragment fragment = new HomeFragment();
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        binding = FragmentHomeBinding.inflate(inflater, container, false);
        View view = binding.getRoot();

        viewModel = new ViewModelProvider(requireActivity()).get(ActivitiesViewModel.class);
        viewModel.getActivities().observe(getViewLifecycleOwner(), activities -> updateAdapter());
        updateAdapter();

        if(viewModel.getNextPage() == 1) {
            viewModel.loadNextPage();
        }

        binding.btnActivitiesListLoadMore.setOnClickListener(v -> viewModel.loadNextPage());

        return view;
    }

    private void updateAdapter() {
        adapter = new ActivitiesAdapter(viewModel.getActivities().getValue());
        binding.activitiesList.setLayoutManager(new LinearLayoutManager(getContext()));
        binding.activitiesList.setAdapter(adapter);
    }
}
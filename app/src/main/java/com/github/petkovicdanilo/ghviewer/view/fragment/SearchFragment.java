package com.github.petkovicdanilo.ghviewer.view.fragment;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.github.petkovicdanilo.ghviewer.databinding.FragmentSearchBinding;
import com.github.petkovicdanilo.ghviewer.view.adapter.RepositoriesAdapter;
import com.github.petkovicdanilo.ghviewer.viewmodel.SearchResultsViewModel;

public class SearchFragment extends Fragment {

    private static final String TAG = "SearchFragment";

    private FragmentSearchBinding binding;
    private SearchResultsViewModel viewModel;
    private RepositoriesAdapter adapter;

    public SearchFragment() {
        // Required empty public constructor
    }

    public static SearchFragment newInstance(String param1, String param2) {
        SearchFragment fragment = new SearchFragment();
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        binding = FragmentSearchBinding.inflate(inflater, container, false);
        View view = binding.getRoot();

        viewModel = new ViewModelProvider(requireActivity()).get(SearchResultsViewModel.class);
        viewModel.getSearchResults().observe(getViewLifecycleOwner(), activities -> updateAdapter());
        updateAdapter();

        binding.btnSearch.setOnClickListener(v -> search());
        binding.btnSearchLoadMore.setOnClickListener(v -> viewModel.loadNextPage());

        return view;
    }

    private void search() {
        String query = binding.txtSearch.getText().toString();
        viewModel.search(query);
    }

    private void updateAdapter() {
        adapter = new RepositoriesAdapter(viewModel.getSearchResults().getValue());
        binding.searchResults.setLayoutManager(new LinearLayoutManager(getContext()));
        binding.searchResults.setAdapter(adapter);
    }
}
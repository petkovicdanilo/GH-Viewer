package com.github.petkovicdanilo.ghviewer.view.fragment;

import android.os.Bundle;

import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.Navigation;
import androidx.recyclerview.widget.LinearLayoutManager;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.github.petkovicdanilo.ghviewer.R;
import com.github.petkovicdanilo.ghviewer.api.dto.EventDto;
import com.github.petkovicdanilo.ghviewer.databinding.FragmentHomeBinding;
import com.github.petkovicdanilo.ghviewer.view.adapter.EventsAdapter;
import com.github.petkovicdanilo.ghviewer.viewmodel.EventsViewModel;

public class HomeFragment extends Fragment implements EventsAdapter.OnEventListener {

    private static final String TAG = "HomeFragment";

    private EventsViewModel viewModel;
    private EventsAdapter adapter;

    private FragmentHomeBinding binding;


    public HomeFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_home, container, false);
        binding.setLifecycleOwner(getViewLifecycleOwner());
        View view = binding.getRoot();

        viewModel = new ViewModelProvider(requireActivity()).get(EventsViewModel.class);

        binding.setViewModel(viewModel);

        viewModel.getEvents().observe(getViewLifecycleOwner(), events -> updateAdapter());
        updateAdapter();

        if (viewModel.getNextPage() == 1) {
            viewModel.loadNextPage();
        }

        return view;
    }

    private void updateAdapter() {
        adapter = new EventsAdapter(viewModel.getEvents().getValue(), this, this);
        binding.eventsList.setLayoutManager(new LinearLayoutManager(getContext()));
        binding.eventsList.setAdapter(adapter);
    }

    @Override
    public void onEventClick(int position) {
        EventDto clickedEvent = viewModel.getEvents().getValue().get(position);

        HomeFragmentDirections.HomeToRepositoryAction action =
                HomeFragmentDirections.homeToRepositoryAction(clickedEvent.getRepo().getName());
        Navigation.findNavController(getView()).navigate(action);
    }
}
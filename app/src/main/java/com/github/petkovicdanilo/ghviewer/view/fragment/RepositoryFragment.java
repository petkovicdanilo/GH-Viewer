package com.github.petkovicdanilo.ghviewer.view.fragment;

import android.os.Bundle;

import androidx.activity.OnBackPressedCallback;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.Navigation;
import androidx.navigation.fragment.NavHostFragment;
import androidx.recyclerview.widget.LinearLayoutManager;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.github.petkovicdanilo.ghviewer.R;
import com.github.petkovicdanilo.ghviewer.api.dto.RepositoryDto;
import com.github.petkovicdanilo.ghviewer.api.dto.git.TreeDto;
import com.github.petkovicdanilo.ghviewer.databinding.FragmentRepositoryBinding;
import com.github.petkovicdanilo.ghviewer.view.adapter.TreeAdapter;
import com.github.petkovicdanilo.ghviewer.view.util.BottomNav;
import com.github.petkovicdanilo.ghviewer.viewmodel.RepositoryViewModel;

import java.util.Arrays;

public class RepositoryFragment extends Fragment implements TreeAdapter.OnTreeItemListener {

    private static final String TAG = "RepositoryFragment";

    private RepositoryViewModel viewModel;
    private TreeAdapter adapter = new TreeAdapter(Arrays.asList(), this);
    private FragmentRepositoryBinding binding;

    public RepositoryFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        BottomNav.unselect(requireActivity());

        requireActivity().getOnBackPressedDispatcher().addCallback(this,
                new OnBackPressedCallback(true) {
            @Override
            public void handleOnBackPressed() {
                if (viewModel.isOnRootTree()) {
                    NavHostFragment.findNavController(RepositoryFragment.this).popBackStack();
                } else {
                    viewModel.loadParentTree();
                }
            }
        });
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_repository, container, false);
        binding.setLifecycleOwner(getViewLifecycleOwner());
        View view = binding.getRoot();

        viewModel = new ViewModelProvider(requireActivity()).get(RepositoryViewModel.class);
        binding.setViewModel(viewModel);

        viewModel.getCurrentTree().observe(getViewLifecycleOwner(), events -> updateAdapter());
        updateAdapter();

        return view;
    }

    private void updateAdapter() {
        if (viewModel.getCurrentTree().getValue() != null) {
            adapter = new TreeAdapter(viewModel.getCurrentTree().getValue().getTree(), this);
            binding.repositoryTree.setLayoutManager(new LinearLayoutManager(getContext()));
            binding.repositoryTree.setAdapter(adapter);
        }
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        RepositoryFragmentArgs args = RepositoryFragmentArgs.fromBundle(getArguments());

        String[] ownerAndRepoName = args.getRepositoryName().split("/");
        String owner = ownerAndRepoName[0];
        String repositoryName = ownerAndRepoName[1];

        viewModel.loadRepository(owner, repositoryName);
    }

    @Override
    public void onTreeItemClicked(int position) {
        TreeDto.TreeItem treeItemClicked =
                viewModel.getCurrentTree().getValue().getTree().get(position);

        switch (treeItemClicked.getType()) {
            case TREE:
                if (treeItemClicked.getPath().equals("..")) {
                    viewModel.loadParentTree();
                } else {
                    viewModel.loadTree(treeItemClicked.getSha());
                }
                break;
            case BLOB:
                RepositoryDto repository = viewModel.getRepository().getValue();
                RepositoryFragmentDirections.RepositoryToBlobAction action =
                        RepositoryFragmentDirections.repositoryToBlobAction(
                                repository.getOwner().getLogin(),
                                repository.getName(),
                                treeItemClicked.getSha(),
                                treeItemClicked.getPath());
                Navigation.findNavController(getView()).navigate(action);
                break;
        }
    }
}
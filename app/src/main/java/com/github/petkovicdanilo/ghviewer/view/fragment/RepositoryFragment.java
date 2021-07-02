package com.github.petkovicdanilo.ghviewer.view.fragment;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.github.petkovicdanilo.ghviewer.R;

public class RepositoryFragment extends Fragment {

    private static final String TAG = "RepositoryFragment";
    
    public RepositoryFragment() {
        // Required empty public constructor
    }

    public static RepositoryFragment newInstance() {
        RepositoryFragment fragment = new RepositoryFragment();
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_repository, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        
        RepositoryFragmentArgs args = RepositoryFragmentArgs.fromBundle(getArguments());

        Log.i(TAG, args.getRepositoryName());
    }
}
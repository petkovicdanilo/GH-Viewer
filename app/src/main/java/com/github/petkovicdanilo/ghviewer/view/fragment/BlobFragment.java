package com.github.petkovicdanilo.ghviewer.view.fragment;

import android.content.res.AssetManager;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import android.util.Base64;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebSettings;

import com.github.petkovicdanilo.ghviewer.R;
import com.github.petkovicdanilo.ghviewer.api.dto.git.BlobDto;
import com.github.petkovicdanilo.ghviewer.databinding.FragmentBlobBinding;
import com.github.petkovicdanilo.ghviewer.view.util.SupportedExtensions;
import com.github.petkovicdanilo.ghviewer.viewmodel.BlobViewModel;


import org.apache.commons.lang3.StringUtils;
import org.apache.commons.text.StringEscapeUtils;

import java.io.IOException;
import java.io.InputStream;
import java.nio.charset.StandardCharsets;
import java.util.Scanner;

public class BlobFragment extends Fragment implements BlobViewModel.OnBlobListener {

    private static final String TAG = "BlobFragment";

    private FragmentBlobBinding binding;
    private BlobViewModel viewModel;

    public BlobFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_blob, container, false);
        binding.setLifecycleOwner(getViewLifecycleOwner());
        View view = binding.getRoot();

        viewModel = new ViewModelProvider(requireActivity()).get(BlobViewModel.class);
        viewModel.setListener(this);

        binding.setViewModel(viewModel);

        WebSettings settings = binding.blobContent.getSettings();
        settings.setJavaScriptEnabled(true);
        binding.blobContent.setBackgroundColor(0x0d1117);

        return view;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        BlobFragmentArgs args = BlobFragmentArgs.fromBundle(getArguments());

        viewModel.loadBlob(args.getOwner(), args.getRepositoryName(), args.getSha(),
                args.getName());
    }

    @Override
    public void onBlobLoaded() {
        BlobDto blob64 = viewModel.getBlob().getValue();

        byte[] data = Base64.decode(blob64.getContent(), Base64.DEFAULT);
        String blobContent = new String(data, StandardCharsets.UTF_8);

        AssetManager am = getContext().getAssets();
        try {
            InputStream is = am.open("blob.html");
            Scanner scanner = new Scanner(is).useDelimiter("\\A");
            String template = scanner.hasNext() ? scanner.next() : "";

            String fileExtension = viewModel.getExtension();
            String className = SupportedExtensions.has(fileExtension) ?
                    "language-" + fileExtension : "";

            String html = String.format(template, className,
                    StringEscapeUtils.escapeXml11(blobContent));
            binding.blobContent.loadData(html, "text/html; charset=UTF-8", null);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void onBlobFail() {
        Log.e(TAG, "Blob loading failed");
    }
}
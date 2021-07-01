package com.github.petkovicdanilo.ghviewer.fragment;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.github.petkovicdanilo.ghviewer.api.ApiHelper;
import com.github.petkovicdanilo.ghviewer.api.GitHubService;
import com.github.petkovicdanilo.ghviewer.api.dto.ActivityDto;
import com.github.petkovicdanilo.ghviewer.api.dto.EmailDto;
import com.github.petkovicdanilo.ghviewer.databinding.FragmentHomeBinding;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.GithubAuthProvider;
import com.google.firebase.auth.UserInfo;

import java.util.HashMap;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link HomeFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class HomeFragment extends Fragment {

    private static String TAG = "HOME_FR";

    private FragmentHomeBinding binding;

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public HomeFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment HomeFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static HomeFragment newInstance(String param1, String param2) {
        HomeFragment fragment = new HomeFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        binding = FragmentHomeBinding.inflate(inflater, container, false);
        View view = binding.getRoot();

        binding.btnTest.setOnClickListener(this::onTestButtonClick);

        return view;
    }

    public void onTestButtonClick(View view) {
        GitHubService service = ApiHelper.getInstance().getGitHubService();

//        Call<List<EmailDto>> call = service.getEmails();
//        call.enqueue(new Callback<List<EmailDto>>() {
//            @Override
//            public void onResponse(Call<List<EmailDto>> call, Response<List<EmailDto>> response) {
//                Log.i(TAG, response.isSuccessful() ? "true" : "false");
//                Log.i(TAG, response.message());
//                if (response.body() == null || response.body().size() == 0) {
//                    Log.i(TAG, "empty list");
//                } else {
//                    Log.i(TAG, response.body().get(0).getEmail());
//                }
//            }
//
//            @Override
//            public void onFailure(Call<List<EmailDto>> call, Throwable t) {
//                Log.e(TAG, t.getMessage());
//            }
//        });
//    }


        String username = ApiHelper.getInstance().getCurrentUser().getLogin();

        Call<List<ActivityDto>> call = service.getActivities(username, 1, 10);
        call.enqueue(new Callback<List<ActivityDto>>() {
            @Override
            public void onResponse(Call<List<ActivityDto>> call,
                                   Response<List<ActivityDto>> response) {
                if (response.isSuccessful()) {
                }
            }

            @Override
            public void onFailure(Call<List<ActivityDto>> call, Throwable t) {

            }
        });
    }
}
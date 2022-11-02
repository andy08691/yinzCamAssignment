package com.example.yinzcamassignment.ui.home;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.DividerItemDecoration;

import com.example.yinzcamassignment.data.ScheduleBean;
import com.example.yinzcamassignment.databinding.FragmentHomeBinding;
import com.google.gson.JsonObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class HomeFragment extends Fragment {

    private FragmentHomeBinding binding;
    private HomeViewModel homeViewModel;
    private ItemAdapter itemAdapter;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {

        homeViewModel = new ViewModelProvider(this).get(HomeViewModel.class);

        binding = FragmentHomeBinding.inflate(inflater, container, false);
        View root = binding.getRoot();
        getData();
        binding.recycleView.addItemDecoration(new DividerItemDecoration(getActivity(), DividerItemDecoration.VERTICAL));
        homeViewModel.getScheduleHashMap().observe(this, new Observer<HashMap<String, List<ScheduleBean>>>() {
            @Override
            public void onChanged(HashMap<String, List<ScheduleBean>> stringListHashMap) {
                ArrayList<Object> array =  homeViewModel.getListItems(stringListHashMap);
                itemAdapter = new ItemAdapter(homeViewModel.getCurTeam(), array, getActivity());
                binding.recycleView.setAdapter(itemAdapter);
            }
        });
        return root;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

    }

    @Override
    public void onResume() {
        super.onResume();

    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }

    private void getData() {
        homeViewModel.getTeamScheduleData().observe(this, new Observer<JsonObject>() {
            @Override
            public void onChanged(JsonObject jsonObject) {
                homeViewModel.parseScheduleData(jsonObject);
            }
        });
    }

}
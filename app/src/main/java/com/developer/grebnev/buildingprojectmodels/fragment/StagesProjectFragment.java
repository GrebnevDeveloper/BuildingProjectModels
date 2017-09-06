package com.developer.grebnev.buildingprojectmodels.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.developer.grebnev.buildingprojectmodels.R;
import com.developer.grebnev.buildingprojectmodels.adapter.StageAdapter;
import com.developer.grebnev.buildingprojectmodels.model.StageList;

/**
 * Created by Grebnev on 06.05.2017.
 */

public class StagesProjectFragment extends Fragment {

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_stages_project, container, false);
        Intent intent = getActivity().getIntent();
        StageAdapter adapter = new StageAdapter(StageList.getListStagesForProject(intent.getStringExtra("pname")),
                getFragmentManager());
        RecyclerView recyclerView = (RecyclerView) rootView.findViewById(R.id.rv_stages_project);
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        recyclerView.setAdapter(adapter);
        return rootView;
    }
}

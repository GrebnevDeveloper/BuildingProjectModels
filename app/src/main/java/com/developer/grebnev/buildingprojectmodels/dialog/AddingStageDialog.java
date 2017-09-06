package com.developer.grebnev.buildingprojectmodels.dialog;

import android.app.AlertDialog;
import android.app.Dialog;
import android.app.DialogFragment;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.TextInputLayout;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Spinner;

import com.developer.grebnev.buildingprojectmodels.DetailsProjectActivity;
import com.developer.grebnev.buildingprojectmodels.R;
import com.developer.grebnev.buildingprojectmodels.model.Project;
import com.developer.grebnev.buildingprojectmodels.model.ProjectList;
import com.developer.grebnev.buildingprojectmodels.model.StageList;
import com.developer.grebnev.buildingprojectmodels.model.StagesProject;

import java.util.ArrayList;

/**
 * Created by Grebnev on 06.05.2017.
 */

public class AddingStageDialog extends DialogFragment {

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        Intent intent = getActivity().getIntent();
        final String nameProject = intent.getStringExtra("pname");
        final AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());

        builder.setTitle(R.string.add_new_stage);

        final LayoutInflater inflater = getActivity().getLayoutInflater();

        View container = inflater.inflate(R.layout.dialog_add_stage, null);

        final TextInputLayout tilName = (TextInputLayout) container.findViewById(R.id.til_dialog_stage_name);
        final TextInputLayout tilResponsible = (TextInputLayout) container.findViewById(R.id.til_dialog_responsible_stage);
        final TextInputLayout tilDuration = (TextInputLayout) container.findViewById(R.id.til_dialog_duration_stage);
        final TextInputLayout tilCost = (TextInputLayout) container.findViewById(R.id.til_dialog_cost_stage);
        final Spinner spPreviousStage = (Spinner) container.findViewById(R.id.sp_dialog_previous_stage);
        final Button btnAddPreviousStage = (Button) container.findViewById(R.id.btn_add_previous_stage);
        final ListView lvPreviousStages = (ListView) container.findViewById(R.id.lv_previous_for_stage);

        final EditText etName = tilName.getEditText();
        final EditText etResponsible = tilResponsible.getEditText();
        final EditText etDuration = tilDuration.getEditText();
        final EditText etCost = tilCost.getEditText();

        tilName.setHint(getResources().getString(R.string.name_stage));
        tilResponsible.setHint(getResources().getString(R.string.responsible_stage));
        tilDuration.setHint(getResources().getString(R.string.duration_stage));
        tilCost.setHint(getResources().getString(R.string.cost_stage));

        final ArrayList<String> nameStagesForSpinner = new ArrayList<>();
        for (StagesProject stagesProject : StageList.getListStagesForProject(ProjectList.searchProjectByName(nameProject).nameProject)) {
            nameStagesForSpinner.add(stagesProject.nameStageProject);
        }

        ArrayAdapter<String> adapter = new ArrayAdapter<String>(getActivity(),
                android.R.layout.simple_list_item_1, nameStagesForSpinner);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        spPreviousStage.setAdapter(adapter);

        final ArrayList<String> nameStagesPrevious = new ArrayList<>();

        btnAddPreviousStage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                nameStagesPrevious.add(spPreviousStage.getSelectedItem().toString());
                ArrayAdapter<String> adapterForList = new ArrayAdapter<String>(getActivity(),
                        android.R.layout.simple_list_item_1, nameStagesPrevious);
                lvPreviousStages.setAdapter(adapterForList);
            }
        });

        lvPreviousStages.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> parent, View view, int position, long id) {
                nameStagesPrevious.remove(position);
                ArrayAdapter<String> adapterForList = new ArrayAdapter<String>(getActivity(),
                        android.R.layout.simple_list_item_1, nameStagesPrevious);
                lvPreviousStages.setAdapter(adapterForList);
                return true;
            }
        });

        builder.setView(container);

        builder.setPositiveButton(getString(R.string.dialog_add), new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                StagesProject stagesProject = new StagesProject();
                stagesProject.nameStageProject = etName.getText().toString();
                stagesProject.responsibleForStageProject = etResponsible.getText().toString();
                stagesProject.durationStageProject = Integer.parseInt(etDuration.getText().toString());
                stagesProject.costStageProject = Integer.parseInt(etCost.getText().toString());
                stagesProject.projectForStage = ProjectList.searchProjectByName(nameProject).nameProject;
                stagesProject.previousStageProject = nameStagesPrevious;
                stagesProject.save();
                Project project = new Project();
                project.nameProject = nameProject;
                project.costProject = StageList.getCostProject(nameProject);
                ProjectList.updateProject(nameProject, project);
                DetailsProjectActivity activity = (DetailsProjectActivity) getActivity();
                ViewPager viewPager = (ViewPager) getActivity().findViewById(R.id.view_pager);
                activity.setupViewPager(viewPager);
                dialog.dismiss();
            }
        });

        builder.setNegativeButton(getString(R.string.dialog_cancel), new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.cancel();
            }
        });

        AlertDialog alertDialog = builder.create();
        return alertDialog;
    }
}

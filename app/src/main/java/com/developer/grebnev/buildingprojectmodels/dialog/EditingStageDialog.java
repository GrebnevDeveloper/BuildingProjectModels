package com.developer.grebnev.buildingprojectmodels.dialog;

import android.app.AlertDialog;
import android.app.Dialog;
import android.app.DialogFragment;
import android.content.DialogInterface;
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
 * Created by Grebnev on 10.05.2017.
 */

public class EditingStageDialog extends DialogFragment {

    public static EditingStageDialog newInstance(StagesProject stage) {
        EditingStageDialog editingStageDialog = new EditingStageDialog();

        Bundle args = new Bundle();

        args.putLong("id", stage.getId());
        args.putString("name", stage.nameStageProject);
        args.putInt("duration", stage.durationStageProject);
        args.putInt("cost", stage.costStageProject);
        args.putStringArrayList("previous", (ArrayList<String>) stage.previousStageProject);
        args.putString("responsible", stage.responsibleForStageProject);
        args.putString("project", stage.projectForStage);

        editingStageDialog.setArguments(args);
        return editingStageDialog;
    }

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        Bundle args = getArguments();

        final long id = args.getLong("id");
        String name = args.getString("name");
        int duration = args.getInt("duration");
        final int cost = args.getInt("cost");
        ArrayList<String> previous = args.getStringArrayList("previous");
        String responsible = args.getString("responsible");
        final String project = args.getString("project");

        final AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());

        builder.setTitle(R.string.edit_stage);

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

        etName.setText(name);
        etDuration.setText(Integer.toString(duration));
        etResponsible.setText(responsible);
        etCost.setText(Integer.toString(cost));

        tilName.setHint(getResources().getString(R.string.name_stage));
        tilResponsible.setHint(getResources().getString(R.string.responsible_stage));
        tilDuration.setHint(getResources().getString(R.string.duration_stage));
        tilCost.setHint(getResources().getString(R.string.cost_stage));

        final ArrayList<String> nameStagesForSpinner = new ArrayList<>();
        for (StagesProject stagesProject : StageList.getListStagesForProject(ProjectList.searchProjectByName(project).nameProject)) {
            nameStagesForSpinner.add(stagesProject.nameStageProject);
        }

        ArrayAdapter<String> adapter = new ArrayAdapter<String>(getActivity(),
                android.R.layout.simple_list_item_1, nameStagesForSpinner);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        spPreviousStage.setAdapter(adapter);

        final ArrayList<String> nameStagesPrevious = new ArrayList<>();
        for (String stagePrevious : previous) {
            nameStagesPrevious.add(stagePrevious);
        }
        ArrayAdapter<String> adapterForList = new ArrayAdapter<String>(getActivity(), android.R.layout.simple_list_item_1,
                nameStagesPrevious);

        lvPreviousStages.setAdapter(adapterForList);

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
                stagesProject.projectForStage = ProjectList.searchProjectByName(project).nameProject;
                stagesProject.previousStageProject = nameStagesPrevious;
                StageList.updateStage(id, stagesProject);
                Project projectForStage = new Project();
                projectForStage.nameProject = project;
                projectForStage.costProject = StageList.getCostProject(project);
                ProjectList.updateProject(project, projectForStage);
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

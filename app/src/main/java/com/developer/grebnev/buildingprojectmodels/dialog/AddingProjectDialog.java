package com.developer.grebnev.buildingprojectmodels.dialog;

import android.app.AlertDialog;
import android.app.Dialog;
import android.app.DialogFragment;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.design.widget.TextInputLayout;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.EditText;

import com.developer.grebnev.buildingprojectmodels.MainActivity;
import com.developer.grebnev.buildingprojectmodels.R;
import com.developer.grebnev.buildingprojectmodels.model.Project;

/**
 * Created by Grebnev on 06.05.2017.
 */

public class AddingProjectDialog extends DialogFragment {
    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        final AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());

        builder.setTitle(R.string.add_new_project);

        LayoutInflater inflater = getActivity().getLayoutInflater();

        View container = inflater.inflate(R.layout.dialog_add_project, null);

        final TextInputLayout tilName = (TextInputLayout) container.findViewById(R.id.til_dialog_project_name);
        final EditText etName = tilName.getEditText();
        tilName.setHint(getResources().getString(R.string.name_project_dialog));

        builder.setView(container);


        builder.setPositiveButton(getString(R.string.dialog_add), new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                Project project = new Project();
                project.nameProject = etName.getText().toString();
                project.costProject = 0;
                project.save();
                MainActivity activity = (MainActivity) getActivity();
                activity.fillingListView();
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

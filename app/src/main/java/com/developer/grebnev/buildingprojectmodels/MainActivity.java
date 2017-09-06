package com.developer.grebnev.buildingprojectmodels;

import android.app.DialogFragment;
import android.app.FragmentManager;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.SimpleAdapter;

import com.activeandroid.ActiveAndroid;
import com.developer.grebnev.buildingprojectmodels.dialog.AddingProjectDialog;
import com.developer.grebnev.buildingprojectmodels.model.Project;
import com.developer.grebnev.buildingprojectmodels.model.ProjectList;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

public class MainActivity extends AppCompatActivity {

    private ListView lvProjects;

    FragmentManager fragmentManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        fragmentManager = getFragmentManager();

        ActiveAndroid.initialize(this);

        fillingListView();

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                DialogFragment addingNewProjectDialog = new AddingProjectDialog();
                addingNewProjectDialog.show(fragmentManager, "AddingProjectDialog");
            }
        });
    }

    @Override
    protected void onStart() {
        super.onStart();
        fillingListView();
    }

    public void fillingListView() {
        ArrayList<HashMap<String, String>> projects = new ArrayList<>();
        HashMap<String, String> map;
        for (Project project : ProjectList.getAllProject()) {
            map = new HashMap<>();
            map.put("Name", project.nameProject);
            map.put("Cost", getString(R.string.cost_project) + String.valueOf(project.costProject));
            projects.add(map);
        }
        final SimpleAdapter adapter = new SimpleAdapter(this, projects, android.R.layout.simple_list_item_2,
                new String[] {"Name", "Cost"},
                new int[] {android.R.id.text1, android.R.id.text2});
        adapter.notifyDataSetChanged();
        lvProjects = (ListView) findViewById(R.id.lv_projects);
        lvProjects.setAdapter(adapter);
        lvProjects.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                Intent intent = new Intent(MainActivity.this, DetailsProjectActivity.class);
                Map mapProject = (Map) adapter.getItem(i);
                Set<Map.Entry<String, String>> set = mapProject.entrySet();
                for (Map.Entry<String, String> me : set) {
                    if (me.getKey().equals("Name")) {
                        intent.putExtra("pname", me.getValue());
                    }
                }
                startActivity(intent);
            }
        });
        lvProjects.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> parent, View view, int position, long id) {
                Map mapProject = (Map) adapter.getItem(position);
                Set<Map.Entry<String, String>> set = mapProject.entrySet();
                for (Map.Entry<String, String> me : set) {
                    if (me.getKey().equals("Name")) {
                        ProjectList.deleteProject(me.getValue());
                    }
                }
                fillingListView();
                return true;
            }
        });
    }
}

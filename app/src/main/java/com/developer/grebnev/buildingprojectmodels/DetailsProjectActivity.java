package com.developer.grebnev.buildingprojectmodels;

import android.app.FragmentManager;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;

import com.developer.grebnev.buildingprojectmodels.adapter.ViewPagerAdapter;
import com.developer.grebnev.buildingprojectmodels.dialog.AddingStageDialog;
import com.developer.grebnev.buildingprojectmodels.fragment.NetworkModelFragment;
import com.developer.grebnev.buildingprojectmodels.fragment.StagesProjectFragment;

public class DetailsProjectActivity extends AppCompatActivity {

    private Toolbar toolbar;
    private TabLayout tabLayout;
    private ViewPager viewPager;
    private FragmentManager fragmentManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_details_project);
        toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        viewPager = (ViewPager) findViewById(R.id.view_pager);
        setupViewPager(viewPager);

        tabLayout = (TabLayout) findViewById(R.id.tab_layout);
        tabLayout.setupWithViewPager(viewPager);

        fragmentManager = getFragmentManager();

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                AddingStageDialog addingNewStageDialog = new AddingStageDialog();
                addingNewStageDialog.show(fragmentManager, "AddingProjectDialog");

            }
        });
    }

    public void setupViewPager(ViewPager viewPager) {
        ViewPagerAdapter adapter = new ViewPagerAdapter(getFragmentManager());
        adapter.addFragment(new StagesProjectFragment(), getString(R.string.stages));
        adapter.addFragment(new NetworkModelFragment(), getString(R.string.model));
        viewPager.setAdapter(adapter);
    }
}

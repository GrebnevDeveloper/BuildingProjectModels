package com.developer.grebnev.buildingprojectmodels.model;

import com.activeandroid.Model;
import com.activeandroid.annotation.Column;
import com.activeandroid.annotation.Table;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Grebnev on 06.05.2017.
 */

@Table(name = "Stages")
public class StagesProject extends Model{
    @Column(name = "Name")
    public String nameStageProject;

    @Column(name = "Duration")
    public int durationStageProject;

    @Column(name = "Cost")
    public int costStageProject;

    @Column(name = "Previous")
    public List previousStageProject = new ArrayList<>();

    @Column(name = "Responsible")
    public String responsibleForStageProject;

    @Column(name = "Project")
    public String projectForStage;

}

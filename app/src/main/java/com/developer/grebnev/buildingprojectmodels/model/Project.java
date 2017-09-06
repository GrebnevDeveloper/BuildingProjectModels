package com.developer.grebnev.buildingprojectmodels.model;

import com.activeandroid.Model;
import com.activeandroid.annotation.Column;
import com.activeandroid.annotation.Table;

import java.util.List;

/**
 * Created by Grebnev on 06.05.2017.
 */

@Table(name = "Projects")
public class Project extends Model {
    @Column(name = "Name")
    public String nameProject;

    @Column(name = "Cost")
    public int costProject;

    public List<StagesProject> stages() {
        return getMany(StagesProject.class, "Project");
    }
}

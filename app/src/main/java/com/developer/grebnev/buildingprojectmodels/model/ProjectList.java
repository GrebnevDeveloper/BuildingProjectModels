package com.developer.grebnev.buildingprojectmodels.model;

import com.activeandroid.query.Delete;
import com.activeandroid.query.Select;

import java.util.List;

/**
 * Created by Grebnev on 06.05.2017.
 */

public class ProjectList {
    public static List<Project> getAllProject() {
        return new Select().from(Project.class).orderBy("Name ASC").execute();
    }
    public static Project searchProjectByName(String name) {
        return new Select().from(Project.class).where("Name = ?", name).executeSingle();
    }
    public static void deleteProject(String project) {
        new Delete().from(StagesProject.class).where("Project = ?", project).execute();
        new Delete().from(Project.class).where("Name = ?", project).execute();
    }
    public static void updateProject(String nameProject, Project project) {
        new Delete().from(Project.class).where("Name = ?", nameProject).execute();
        project.save();
    }
}

package com.developer.grebnev.buildingprojectmodels.model;

import com.activeandroid.query.Delete;
import com.activeandroid.query.Select;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Grebnev on 07.05.2017.
 */

public class StageList {

    public static int getCostProject(String project) {
        int costProject = 0;
        List<StagesProject> stagesProjects = new Select().from(StagesProject.class).where("Project = ?", project).execute();
        for (StagesProject stage : stagesProjects) {
            costProject += stage.costStageProject;
        }
        return costProject;
    }

    public static List<StagesProject> getListStagesForProject(String project) {
        return new Select().from(StagesProject.class).where("Project = ?", project).execute();
    }

    public static void deleteStage(long id, StagesProject stage) {
        new Delete().from(StagesProject.class).where("Id = ?", id).execute();
        ArrayList<StagesProject> updateStage = new ArrayList<>();
        ArrayList<Long> deleteId = new ArrayList<>();
        for (StagesProject allStage : getListStagesForProject(stage.projectForStage)) {
            for (Object previous : allStage.previousStageProject) {
                if (previous.toString().equals(stage.nameStageProject) && allStage.previousStageProject.size() == 1) {
                    deleteStage(allStage.getId(), allStage);
                }
                else if (previous.toString().equals(stage.nameStageProject)) {
                    StagesProject tmpStage = new StagesProject();
                    tmpStage.nameStageProject = allStage.nameStageProject;
                    tmpStage.durationStageProject = allStage.durationStageProject;
                    tmpStage.responsibleForStageProject = allStage.responsibleForStageProject;
                    tmpStage.projectForStage = allStage.projectForStage;
                    tmpStage.previousStageProject = allStage.previousStageProject;
                    for (int i = 0; i < tmpStage.previousStageProject.size(); i++) {
                        if (tmpStage.previousStageProject.get(i).equals(previous.toString())) {
                            tmpStage.previousStageProject.remove(i);
                        }
                    }
                    updateStage.add(tmpStage);
                    deleteId.add(allStage.getId());
                    break;
                }
            }
        }
        for (int i = 0; i < deleteId.size(); i++) {
            updateStage(deleteId.get(i), updateStage.get(i));
        }
    }
    public static void updateStage(long id, StagesProject stage) {
        new Delete().from(StagesProject.class).where("Id = ?", id).execute();
        stage.save();
    }
}

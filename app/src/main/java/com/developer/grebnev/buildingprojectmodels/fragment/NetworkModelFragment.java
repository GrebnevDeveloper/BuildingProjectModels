package com.developer.grebnev.buildingprojectmodels.fragment;

import android.content.Context;
import android.content.Intent;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.Rect;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;

import com.developer.grebnev.buildingprojectmodels.R;
import com.developer.grebnev.buildingprojectmodels.model.CoordinatesModel;
import com.developer.grebnev.buildingprojectmodels.model.StageList;
import com.developer.grebnev.buildingprojectmodels.model.StagesProject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by Grebnev on 06.05.2017.
 */

public class NetworkModelFragment extends Fragment {
    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_network_model, container, false);
        RelativeLayout relativeLayout = (RelativeLayout) rootView.findViewById(R.id.container_model);
        relativeLayout.addView(new DrawModel(getActivity()));
        return rootView;
    }

    private class DrawModel extends View {
        Paint paint = new Paint();
        Intent intent = getActivity().getIntent();
        String project = intent.getStringExtra("pname");
        Map<String, Integer> mapTime = new HashMap<>();
        ArrayList<CoordinatesModel> coordinatesModels = new ArrayList<>();
        public DrawModel(Context context) {
            super(context);
        }

        @Override
        protected void onDraw(Canvas canvas) {
            List<StagesProject> allPreviousStage = new ArrayList<>();
            int tmp = 1;
            while (allPreviousStage.size() < StageList.getListStagesForProject(project).size()) {
                List<StagesProject> tmpStages = new ArrayList<>();
                tmpStages = getListStageWithoutPrevious(allPreviousStage);
                drawStageModel(tmpStages, canvas, tmp);
                for (StagesProject stage : tmpStages) {
                    allPreviousStage.add(stage);
                }
                tmp++;
            }
            drawConnectionModel(canvas);

        }

        private List<StagesProject> getListStageWithoutPrevious(List<StagesProject> allPreviousStage) {
            ArrayList<StagesProject> stagesProjects = new ArrayList<>();
            for (StagesProject stagesProject : StageList.getListStagesForProject(project)) {
                if (allPreviousStage.size() == 0) {
                    if (stagesProject.previousStageProject.size() == 0 ||
                            stagesProject.previousStageProject.get(0).equals("")) {
                        stagesProjects.add(stagesProject);
                        mapTime.put(stagesProject.nameStageProject, 0 + stagesProject.durationStageProject);
                    }
                }
                else {
                    int tmpPrevious = stagesProject.previousStageProject.size();
                    for (StagesProject previousStage : allPreviousStage) {
                        if (stagesProject.nameStageProject.equals(previousStage.nameStageProject)) {
                            tmpPrevious++;
                            break;
                        }
                        else {
                            for (Object tmpPreviousStage : stagesProject.previousStageProject) {
                                if (tmpPreviousStage.toString().equals(previousStage.nameStageProject)) {
                                    tmpPrevious--;
                                }
                            }
                        }
                    }
                    if (tmpPrevious == 0) {
                        stagesProjects.add(stagesProject);
                        mapTime.put(stagesProject.nameStageProject, 0);
                        for (Object stage : stagesProject.previousStageProject) {
                            for (StagesProject all : allPreviousStage) {
                                if (stage.toString().equals(all.nameStageProject)) {
                                    if (mapTime.get(stagesProject.nameStageProject) < mapTime.get(all.nameStageProject))
                                    mapTime.put(stagesProject.nameStageProject, mapTime.get(all.nameStageProject));
                                }
                            }
                        }
                        mapTime.put(stagesProject.nameStageProject, mapTime.get(stagesProject.nameStageProject)
                                + stagesProject.durationStageProject);
                    }
                }
            }
            return stagesProjects;
        }

        private void drawStageModel(List<StagesProject> stagesProjects, Canvas canvas, int numberLineStage) {
            for (int i = 1; i <= stagesProjects.size(); i++) {
                paint.setStyle(Paint.Style.STROKE);
                Rect rect = new Rect((numberLineStage * canvas.getWidth() / 6) - 280,
                        (i * canvas.getHeight() / (stagesProjects.size() + 1)) - 100,
                        (numberLineStage * canvas.getWidth() / 6),
                        (i * canvas.getHeight() / (stagesProjects.size() + 1)) + 100);
                canvas.drawRect(rect, paint);

                coordinatesModels.add(new CoordinatesModel(stagesProjects.get(i - 1).nameStageProject,
                        (numberLineStage * canvas.getWidth() / 6) - 280,
                        (numberLineStage * canvas.getWidth() / 6),
                        (i * canvas.getHeight() / (stagesProjects.size() + 1))));

                paint.setColor(Color.BLACK);
                canvas.drawLine((float)(numberLineStage * canvas.getWidth() / 6) - 280,
                        (float)(i * canvas.getHeight() / (stagesProjects.size() + 1)) - 60,
                        (float)(numberLineStage * canvas.getWidth() / 6),
                        (float)(i * canvas.getHeight() / (stagesProjects.size() + 1)) - 60, paint);
                canvas.drawLine((float)(numberLineStage * canvas.getWidth() / 6) - 190,
                        (float)(i * canvas.getHeight() / (stagesProjects.size() + 1)) - 100,
                        (float)(numberLineStage * canvas.getWidth() / 6) - 190,
                        (float)(i * canvas.getHeight() / (stagesProjects.size() + 1)) - 60, paint);
                canvas.drawLine((float)(numberLineStage * canvas.getWidth() / 6) - 90,
                        (float)(i * canvas.getHeight() / (stagesProjects.size() + 1)) - 100,
                        (float)(numberLineStage * canvas.getWidth() / 6) - 90,
                        (float)(i * canvas.getHeight() / (stagesProjects.size() + 1)) - 60, paint);

                paint.setTextSize(18);
                canvas.drawText(Integer.toString(mapTime.get(stagesProjects.get(i - 1).nameStageProject)
                        - stagesProjects.get(i - 1).durationStageProject),
                        (float)(numberLineStage * canvas.getWidth() / 6) - 240,
                        (float)(i * canvas.getHeight() / (stagesProjects.size() + 1)) - 70, paint);
                canvas.drawText(Integer.toString(stagesProjects.get(i - 1).durationStageProject),
                        (float)(numberLineStage * canvas.getWidth() / 6) - 150,
                        (float)(i * canvas.getHeight() / (stagesProjects.size() + 1)) - 70, paint);
                canvas.drawText(Integer.toString(mapTime.get(stagesProjects.get(i - 1).nameStageProject)),
                        (float)(numberLineStage * canvas.getWidth() / 6) - 50,
                        (float)(i * canvas.getHeight() / (stagesProjects.size() + 1)) - 70, paint);

                canvas.drawText(stagesProjects.get(i - 1).nameStageProject,
                        (float)(numberLineStage * canvas.getWidth() / 6) - 270,
                        (float)(i * canvas.getHeight() / (stagesProjects.size() + 1)) - 10, paint);
                String responsible = getResources().getString(R.string.responsible) + " " + stagesProjects.get(i - 1).responsibleForStageProject;
                canvas.drawText(responsible,
                        (float)(numberLineStage * canvas.getWidth() / 6) - 270,
                        (float)(i * canvas.getHeight() / (stagesProjects.size() + 1)) + 40, paint);
                String cost = getResources().getString(R.string.cost) + " " + stagesProjects.get(i - 1).costStageProject;
                canvas.drawText(cost,
                        (float)(numberLineStage * canvas.getWidth() / 6) - 270,
                        (float)(i * canvas.getHeight() / (stagesProjects.size() + 1)) + 80, paint);
            }
        }
        private void drawConnectionModel(Canvas canvas) {
            for (StagesProject stagesProjectLine1 : StageList.getListStagesForProject(project)) {
                for (Object previous : stagesProjectLine1.previousStageProject) {
                    CoordinatesModel stage1 = null;
                    CoordinatesModel stage2 = null;
                    for (StagesProject stagesProjectLine2 : StageList.getListStagesForProject(project)) {
                        if (previous.toString().equals(stagesProjectLine2.nameStageProject)) {
                            for (CoordinatesModel coordinate : coordinatesModels) {
                                if (coordinate.getNameStage().equals(stagesProjectLine1.nameStageProject)) {
                                    stage1 = coordinate;
                                }
                                if (coordinate.getNameStage().equals(stagesProjectLine2.nameStageProject)) {
                                    stage2 = coordinate;
                                }
                            }
                        }
                    }
                    if (stage1 != null && stage2 != null) {
                        canvas.drawLine((float)stage1.getLeftSideX(),
                                (float)stage1.getMiddleY(),
                                (float)stage2.getRightSideX() + Math.abs((stage1.getLeftSideX() - stage2.getRightSideX()) / 2),
                                (float)stage1.getMiddleY(), paint);

                        canvas.drawLine((float)stage2.getRightSideX() + Math.abs((stage1.getLeftSideX() - stage2.getRightSideX()) / 2),
                                (float)stage1.getMiddleY(),
                                (float)stage2.getRightSideX() + Math.abs((stage1.getLeftSideX() - stage2.getRightSideX()) / 2),
                                (float)stage2.getMiddleY(), paint);

                        canvas.drawLine((float)stage2.getRightSideX() + Math.abs((stage1.getLeftSideX() - stage2.getRightSideX()) / 2),
                                (float)stage2.getMiddleY(),
                                (float)stage2.getRightSideX(),
                                (float)stage2.getMiddleY(), paint);

                        Path path = new Path();
                        path.moveTo(stage1.getLeftSideX(), stage1.getMiddleY() + 2);
                        path.lineTo(stage1.getLeftSideX() - 5, stage1.getMiddleY() + 7);
                        path.lineTo(stage1.getLeftSideX() - 5, stage1.getMiddleY() - 6);
                        path.lineTo(stage1.getLeftSideX(), stage1.getMiddleY() + 1);
                        path.close();

                        Paint paintTriangle  = new Paint();
                        paintTriangle.setColor(Color.BLACK);
                        canvas.drawPath(path, paintTriangle);
                    }
                }
            }
        }
    }
}

package com.developer.grebnev.buildingprojectmodels.model;

/**
 * Created by Grebnev on 09.05.2017.
 */

public class CoordinatesModel {
    private String nameStage;
    private int leftSideX;
    private int rightSideX;
    private int middleY;

    public CoordinatesModel(String nameStage, int leftSideX, int rightSideX, int middleY) {
        this.nameStage = nameStage;
        this.leftSideX = leftSideX;
        this.rightSideX = rightSideX;
        this.middleY = middleY;
    }

    public String getNameStage() {
        return nameStage;
    }

    public int getLeftSideX() {
        return leftSideX;
    }

    public int getRightSideX() {
        return rightSideX;
    }

    public int getMiddleY() {
        return middleY;
    }
}

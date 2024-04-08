package com.liyang.tank.ui;

import com.almasb.fxgl.app.scene.StartupScene;

import javafx.scene.shape.Rectangle;

public class TankStartupScene extends StartupScene{
    public TankStartupScene(int appWidth,int appHeight){
        super(appWidth, appHeight);
        Rectangle rectangle=new Rectangle(appWidth,appHeight);
        getContentRoot().getChildren().add(rectangle); 
    }
}

package com.liyang.tank;

import java.util.List;

import javafx.geometry.Point2D;

public class Config {
     static List<Point2D> POINTS=List.of(
        new Point2D(288,600-24),
        new Point2D(312,600-24),
        new Point2D(336,600-24),
        new Point2D(360,600-24),
        new Point2D(288,600),
        new Point2D(360,600),
        new Point2D(288,624),
        new Point2D(360,624)
    );
    static Point2D[]spawnEnemyPosition=new Point2D[]{
        new Point2D(30, 30),
        new Point2D(330, 30),
        new Point2D(580, 30)};


        
}

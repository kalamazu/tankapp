package com.liyang.tank.Collisions;

import com.almasb.fxgl.core.math.FXGLMath;
import com.almasb.fxgl.dsl.FXGL;
import com.almasb.fxgl.entity.Entity;
import com.almasb.fxgl.physics.CollisionHandler;
import com.liyang.tank.GameType;

import javafx.geometry.Rectangle2D;

public class BulletEnemyHandler  extends CollisionHandler{
    public BulletEnemyHandler(){
             super(GameType.BULLET,GameType.ENEMY);
    }

      @Override
        protected void onCollisionBegin(Entity bullet,Entity enemy){
        FXGL.spawn("explode",enemy.getCenter().subtract(50/2.0,50/2.0));    
        bullet.removeFromWorld();
         enemy.removeFromWorld();
         FXGL.inc("destroyedEnemyAmount",1);
        if(FXGLMath.randomBoolean(0.5)){
          FXGL.spawn("item",FXGLMath.randomPoint(new Rectangle2D(24, 24, 27*24-30,27*24-28 ))); 
        }
    }
       
}
package com.liyang.tank.Collisions;

import com.almasb.fxgl.entity.Entity;
import com.almasb.fxgl.physics.CollisionHandler;
import com.liyang.tank.GameType;

public class BulletForestHandler extends CollisionHandler{


    public BulletForestHandler(){
        super(GameType.BULLET,GameType.GREENS);
    }
    @Override
    protected void onCollisionBegin(Entity bullet, Entity greens) {
                int value=bullet.getInt("level");
                if(value==2){
                    greens.removeFromWorld();
                }

    }
    
}

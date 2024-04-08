package com.liyang.tank.Collisions;

import com.almasb.fxgl.dsl.FXGL;
import com.almasb.fxgl.entity.Entity;
import com.almasb.fxgl.physics.CollisionHandler;
import com.liyang.tank.GameType;

public class BulletBrickHandler extends CollisionHandler{


    public BulletBrickHandler(){
        super(GameType.BULLET,GameType.BRICK);
    }
    @Override
    protected void onCollisionBegin(Entity bullet, Entity brick) {
                FXGL.spawn("explode",bullet.getPosition());
                bullet.removeFromWorld();
                brick.removeFromWorld();


    }
    
}

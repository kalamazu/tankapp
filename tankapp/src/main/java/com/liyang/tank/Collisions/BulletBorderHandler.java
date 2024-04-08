package com.liyang.tank.Collisions;

import com.almasb.fxgl.entity.Entity;
import com.almasb.fxgl.physics.CollisionHandler;
import com.liyang.tank.GameType;

public class BulletBorderHandler extends CollisionHandler{


    public BulletBorderHandler(){
        super(GameType.BULLET,GameType.BORDER);
    }
    @Override
    protected void onCollisionBegin(Entity bullet, Entity Border) {
                
                bullet.removeFromWorld();
               


    }
    
}
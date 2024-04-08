package com.liyang.tank.Collisions;

import com.almasb.fxgl.dsl.FXGL;
import com.almasb.fxgl.entity.Entity;
import com.almasb.fxgl.physics.CollisionHandler;
import com.liyang.tank.GameType;

public class BulletStoneHandler extends CollisionHandler{


    public BulletStoneHandler(){
        super(GameType.BULLET,GameType.STONE);
    }
    @Override
    protected void onCollisionBegin(Entity bullet, Entity stone) {
                 FXGL.spawn("explode",bullet.getPosition());
                bullet.removeFromWorld();
                int value=bullet.getInt("level");
                if(value==2){
                    stone.removeFromWorld();
                }
               


    }
    
}

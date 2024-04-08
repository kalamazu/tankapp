package com.liyang.tank.Collisions;


import com.almasb.fxgl.entity.Entity;
import com.almasb.fxgl.physics.CollisionHandler;
import com.liyang.tank.GameType;

public class BulletBulletHandler extends CollisionHandler{


    public BulletBulletHandler(){
        super(GameType.BULLET,GameType.BULLET);
    }
    @Override
    protected void onCollisionBegin(Entity bullet1, Entity bullet2) {
               GameType t1=bullet1.getObject("ownerType");
               GameType t2=bullet2.getObject("ownerType");
               if(t1!=t2){
                bullet1.removeFromWorld();
                bullet2.removeFromWorld();
               }

    }
    
}

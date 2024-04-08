package com.liyang.tank.Collisions;
import com.almasb.fxgl.dsl.FXGL;
import com.almasb.fxgl.entity.Entity;
import com.almasb.fxgl.physics.CollisionHandler;
import com.liyang.tank.App;
import com.liyang.tank.GameType;
import com.liyang.tank.component.FlagComponent;

public class BulletFlagHandler extends CollisionHandler{


    public BulletFlagHandler(){
        super(GameType.BULLET,GameType.FLAG);
    }
    @Override
    protected void onCollisionBegin(Entity bullet, Entity flag) {
                flag.getComponent(FlagComponent.class).onHit();
                FXGL.spawn("explode",flag.getCenter().subtract(25,25));
                bullet.removeFromWorld();
              FXGL.<App>getAppCast().gameOver();


    }
    
}
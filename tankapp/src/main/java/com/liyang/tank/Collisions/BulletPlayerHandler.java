package com.liyang.tank.Collisions;

import com.almasb.fxgl.dsl.FXGL;
import com.almasb.fxgl.dsl.components.EffectComponent;
import com.almasb.fxgl.dsl.components.HealthIntComponent;
import com.almasb.fxgl.entity.Entity;
import com.almasb.fxgl.physics.CollisionHandler;
import com.liyang.tank.App;
import com.liyang.tank.GameType;
import com.liyang.tank.effects.HelmetEffect.HelmetEffect;

public class BulletPlayerHandler  extends CollisionHandler{
    public BulletPlayerHandler(){
             super(GameType.BULLET,GameType.PLAYER);
    }

      @Override
        protected void onCollisionBegin(Entity bullet,Entity player){
          boolean b=player.getComponent(EffectComponent.class).hasEffect(HelmetEffect.class);
          if(b){
            bullet.removeFromWorld();
          }else{
           
         
          HealthIntComponent hp=player.getComponent(HealthIntComponent.class);
          hp.damage(1);
          if(hp.isZero()){
            FXGL.spawn("explode",player.getCenter().subtract(50/2.0,50/2.0)); 
            player.removeFromWorld();
            FXGL.<App>getAppCast().gameOver();
          }
       
        bullet.removeFromWorld();
        }

    }
       
}
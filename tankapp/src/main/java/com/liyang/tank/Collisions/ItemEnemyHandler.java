package com.liyang.tank.Collisions;


import com.almasb.fxgl.dsl.FXGL;
import com.almasb.fxgl.dsl.components.EffectComponent;
import com.almasb.fxgl.dsl.components.HealthIntComponent;
import com.almasb.fxgl.entity.Entity;
import com.almasb.fxgl.physics.CollisionHandler;
import com.liyang.tank.App;
import com.liyang.tank.GameType;
import com.liyang.tank.ItemType;
import com.liyang.tank.component.TankLevelComponent;
import com.liyang.tank.effects.FreezeEffect.FreezeEffect;
import com.liyang.tank.effects.HelmetEffect.HelmetEffect;
import com.liyang.tank.effects.ShipEffect.ShipEffect;

public class ItemEnemyHandler extends CollisionHandler{
    public ItemEnemyHandler(){
        super(GameType.ITEM,GameType.ENEMY);
    }

    @Override
    protected void onCollisionBegin(Entity item, Entity enemy) {
        ItemType itemType=item.getObject("itemType");
        switch(itemType){
            case STAR->enemy.getComponent(TankLevelComponent.class).upgrade();
            case GUN->enemy.getComponent(TankLevelComponent.class).gradeFull();
            case HEART->{
                FXGL.getGameWorld().getEntitiesByType(GameType.PLAYER).forEach(player->{
                    if(player.isActive()){
                        HealthIntComponent hp=player.getComponent(HealthIntComponent.class);
                        hp.damage(hp.getValue()/2);
                        if(hp.isZero()){
                            FXGL.spawn("explode",player.getCenter().subtract(50/2.0,50/2.0)); 
                            player.removeFromWorld();
                            FXGL.<App>getAppCast().gameOver();
                          }
                    }
                });
            }
            case BOMB->{
                FXGL.getGameWorld().getEntitiesByType(GameType.PLAYER).forEach(player->{
                    if(player.isActive()){
                        HealthIntComponent hp=player.getComponent(HealthIntComponent.class);
                        hp.setValue(1);
                        if(hp.isZero()){
                            FXGL.spawn("explode",player.getCenter().subtract(50/2.0,50/2.0)); 
                            player.removeFromWorld();
                            FXGL.<App>getAppCast().gameOver();
                          }
                    }
                });
            }
            case TIME->{
                FXGL.getGameWorld().getEntitiesByType(GameType.PLAYER).forEach(player->{
                    if(player.isActive()){
                        player.getComponent(EffectComponent.class).startEffect(new FreezeEffect());
                    }
                });
            }
              
            case SHIP->{
                enemy.getComponent(EffectComponent.class).startEffect(new ShipEffect());
            }
            case HELMET->{
                enemy.getComponent(EffectComponent.class).startEffect(new HelmetEffect());
            }
           case SPADE->{
                
            }
        }
        item.removeFromWorld();
        FXGL.play("item.wav");

    }
    
}

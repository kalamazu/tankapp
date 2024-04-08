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
import com.liyang.tank.effects.HelmetEffect.HelmetEffect;
import com.liyang.tank.effects.ShipEffect.ShipEffect;

public class ItemPlayerHandler extends CollisionHandler{
    public ItemPlayerHandler(){
        super(GameType.ITEM,GameType.PLAYER);
    }

    @Override
    protected void onCollisionBegin(Entity item, Entity player) {
        ItemType itemType=item.getObject("itemType");
        switch(itemType){
            case STAR->player.getComponent(TankLevelComponent.class).upgrade();
            case GUN->player.getComponent(TankLevelComponent.class).gradeFull();
            case HEART->player.getComponent(HealthIntComponent.class).restoreFully();
            case BOMB->{
                FXGL.getGameWorld().getEntitiesByType(GameType.ENEMY).forEach(entity->{
                FXGL.spawn("explode",entity.getCenter().subtract(50,25));
                entity.removeFromWorld();
                FXGL.inc("destroyedEnemyAmount",1);

            });}
            case TIME->{
                FXGL.<App>getAppCast().freezeEnemy();
            }
            case SHIP->{
                player.getComponent(EffectComponent.class).startEffect(new ShipEffect());
            }
            case HELMET->{
                player.getComponent(EffectComponent.class).startEffect(new HelmetEffect());
            }
            case SPADE->{
                FXGL.< App>getAppCast().reinforce();
            }
        }
        item.removeFromWorld();
        FXGL.play("item.wav");

    }
    
}

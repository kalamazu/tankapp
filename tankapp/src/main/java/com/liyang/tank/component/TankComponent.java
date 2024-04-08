package com.liyang.tank.component;

import java.util.List;

import com.almasb.fxgl.core.util.LazyValue;
import com.almasb.fxgl.dsl.FXGL;
import com.almasb.fxgl.dsl.components.EffectComponent;
import com.almasb.fxgl.entity.Entity;
import com.almasb.fxgl.entity.EntityGroup;
import com.almasb.fxgl.entity.SpawnData;
import com.almasb.fxgl.entity.component.Component;
import com.almasb.fxgl.time.LocalTimer;
import com.liyang.tank.Dir;
import com.liyang.tank.GameType;
import com.liyang.tank.effects.FreezeEffect.FreezeEffect;
import com.liyang.tank.effects.ShipEffect.ShipEffect;

import javafx.util.Duration;

public class TankComponent extends Component{

    private EffectComponent effectComponent;
    

    private boolean movedThisFrame=false;

 
    private double distance;


    private Dir  moveDir=Dir.UP;

    public Dir getMoveDir() {
    return moveDir;
}



private Duration shootDelay=Duration.seconds(0.35);


private LocalTimer shootTimer;


private LazyValue<EntityGroup>blockAllLazyValue=
new LazyValue<>(()->FXGL.getGameWorld().getGroup(GameType.BORDER,GameType.BRICK,GameType.SEA,GameType.STONE,GameType.PLAYER,GameType.ENEMY));

private LazyValue<EntityGroup>blockLazyValue=
new LazyValue<>(()->FXGL.getGameWorld().getGroup(GameType.BORDER,GameType.BRICK,GameType.SEA,GameType.STONE,GameType.PLAYER,GameType.ENEMY));

private List<Entity>blockList;


    @Override
public void onAdded() {
    shootTimer=FXGL.newLocalTimer();

}
    @Override
public void onUpdate(double tpf) {
    if(effectComponent.hasEffect(FreezeEffect.class)){
        return;
    
    }
    movedThisFrame=false;
    distance=tpf*150;
}
 
public void moveUp(){
        if(movedThisFrame)return;
        movedThisFrame=true;
        entity.setRotation(0);
        moveDir=Dir.UP;
        move();
    }

public void moveDown(){
        if(movedThisFrame)return;
        movedThisFrame=true;
        moveDir=Dir.DOWN;
        entity.setRotation(180);
        move();
    }
public void moveLeft(){
        if(movedThisFrame)return;
        movedThisFrame=true;
        moveDir=Dir.LEFT;
        entity.setRotation(270);
        move();

    }
public void moveRight(){
        if(movedThisFrame)return;
        movedThisFrame=true;
        moveDir=Dir.RIGHT;
        entity.setRotation(90);
        move();
    }
public void shoot(){
        if(shootTimer.elapsed(shootDelay)){
        FXGL.spawn("bullet",new SpawnData(
            entity.getCenter().subtract(10,12)
        ).put("dir",moveDir.getVector())
         .put("ownerType",entity.getType())
         .put("level",entity.getComponent(TankLevelComponent.class).getValue())
        );
        shootTimer.capture();
        }
}
public void move(){
        int len=(int) distance;
        boolean b=effectComponent.hasEffect(ShipEffect.class);
        if(b){
            blockList = blockLazyValue.get().getEntitiesCopy();
        }
       else{
        blockList = blockAllLazyValue.get().getEntitiesCopy();
       }
        blockList.remove(entity);
        int size=blockList.size(); 
        boolean isColliding=false;
        for (int i = 0; i < len; i++) {
            entity.translate(moveDir.getVector().getX(),moveDir.getVector().getY());
            
       for (int j = 0; j < size; j++) {
                 if(entity.isColliding(blockList.get(j))){
                    isColliding=true;
                    break;
                 }
       }
       if(isColliding){
        entity.translate(-moveDir.getVector().getX(),-moveDir.getVector().getY());
        break;
    }
        }
    }
}

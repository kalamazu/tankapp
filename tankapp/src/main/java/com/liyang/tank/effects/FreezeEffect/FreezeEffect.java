package com.liyang.tank.effects.FreezeEffect;

import com.almasb.fxgl.dsl.components.Effect;
import com.almasb.fxgl.entity.Entity;

import javafx.util.Duration;

public class FreezeEffect extends Effect{


    public FreezeEffect() {
        super(Duration.seconds(5));
    }
    @Override
    public void onEnd(Entity entity) {
       
        
    }

    @Override
    public void onStart(Entity entity) {
       
        
    }
    
    
}

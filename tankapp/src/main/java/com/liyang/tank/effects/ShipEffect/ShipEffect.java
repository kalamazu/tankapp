package com.liyang.tank.effects.ShipEffect;

import com.almasb.fxgl.dsl.FXGL;
import com.almasb.fxgl.dsl.components.Effect;
import com.almasb.fxgl.entity.Entity;
import com.almasb.fxgl.texture.Texture;

import javafx.util.Duration;

public class ShipEffect extends Effect{
    private Texture texture;
public ShipEffect(){
    super(Duration.INDEFINITE);
    texture=FXGL.texture("item/ship-circle.png");
        
}
    @Override
    public void onEnd(Entity entity) {
        entity.getViewComponent().removeChild(texture);
    }

    @Override
    public void onStart(Entity entity) {
        texture.setTranslateX(entity.getWidth()/2.0-texture.getWidth()/2.0);
        texture.setTranslateY(entity.getHeight()/2.0-texture.getHeight()/2.0);
        entity.getViewComponent().addChild(texture);
        
    }
    
}

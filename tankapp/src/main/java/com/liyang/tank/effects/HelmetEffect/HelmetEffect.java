package com.liyang.tank.effects.HelmetEffect;

import com.almasb.fxgl.dsl.FXGL;
import com.almasb.fxgl.dsl.components.Effect;
import com.almasb.fxgl.entity.Entity;
import com.almasb.fxgl.texture.Texture;

import javafx.util.Duration;

public class HelmetEffect extends Effect{
    private Texture texture;
    public HelmetEffect(){
        super(Duration.seconds(12));
        texture=FXGL.texture("item/glowCircle.png");
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

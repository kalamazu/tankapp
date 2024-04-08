package com.liyang.tank.ui;

import com.almasb.fxgl.animation.Interpolators;
import com.almasb.fxgl.dsl.FXGL;
import com.almasb.fxgl.scene.SubScene;
import com.almasb.fxgl.texture.Texture;

import javafx.animation.TranslateTransition;
import javafx.util.Duration;

public class FailedScene extends SubScene{
    private TranslateTransition tt;
    
    private Texture texture;
    public FailedScene(){
        texture=FXGL.texture("game-over.png");
        texture.setLayoutX(28*24/2.0-texture.getWidth()/2.0);
        texture.setLayoutY(FXGL.getAppHeight());
        tt = new TranslateTransition(Duration.seconds(2.6),texture);
        tt.setInterpolator(Interpolators.ELASTIC.EASE_OUT());
        tt.setFromY(0);
        tt.setToY(-(FXGL.getAppHeight()-260));
        tt.setOnFinished(event->{
            FXGL.getSceneService().popSubScene();
            texture.setTranslateY(0);
            FXGL.getGameController().gotoGameMenu();
        });
        getContentRoot().getChildren().add(texture);
    }

    @Override
    public void onCreate() {
        
        tt.play();
    }
    
}

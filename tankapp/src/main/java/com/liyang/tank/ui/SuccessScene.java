package com.liyang.tank.ui;

import com.almasb.fxgl.dsl.FXGL;
import com.almasb.fxgl.scene.SubScene;
import com.liyang.tank.App;

import javafx.animation.PauseTransition;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import javafx.util.Duration;

public class SuccessScene extends SubScene {
    private PauseTransition pt;

    public SuccessScene(){
        Text text=new Text("Success");
        text.setFill(Color.WHITE);
        text.setFont(Font.font(35));
        StackPane pane=new StackPane(text);
        pane.setPrefSize(FXGL.getAppWidth(), FXGL.getAppHeight());
        pane.setStyle("-fx-background-color: gray");
        getContentRoot().getChildren().add(pane);

        pt = new PauseTransition(Duration.seconds(2));
        pt.setOnFinished(event->{
            if(FXGL.geti("level")<2){
                FXGL.getSceneService().popSubScene();
                FXGL.inc("level",1);
                FXGL.<App>getAppCast().startLevel();
                
            }else{
                FXGL.getNotificationService().pushNotification("victory");       
                FXGL.getGameController().gotoGameMenu(); 
                
            }
        });


    }

    @Override
    public void onCreate() {
        pt.play();

    }
    
}

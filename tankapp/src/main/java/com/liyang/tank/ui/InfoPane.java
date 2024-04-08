package com.liyang.tank.ui;

import com.almasb.fxgl.dsl.FXGL;
import com.almasb.fxgl.entity.Entity;
import com.almasb.fxgl.texture.Texture;
import com.liyang.tank.GameType;
import com.liyang.tank.component.TankLevelComponent;

import javafx.collections.ObservableList;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.layout.HBox;
import javafx.scene.layout.TilePane;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;
import javafx.scene.text.Text;

public class InfoPane extends VBox{
    public InfoPane() {
        setSpacing(25);
        setStyle("-fx-background-color:lightgray");
        setPrefSize(6*24, FXGL.getAppHeight());
        setTranslateX(28*24);
        setAlignment(Pos.TOP_CENTER);

        TilePane tp=new TilePane();
        tp.setTileAlignment(Pos.CENTER);
        tp.setHgap(10);
        tp.setVgap(6);
        tp.setMaxWidth(25*2+15);
        tp.setPrefHeight(25*10+6*10+15);
        tp. setStyle("-fx-border-color:red");
        for (int i = 0; i < 10; i++) {
            tp.getChildren().add(FXGL.texture("enemy_pre.png"));
           
        }
        getChildren().add(tp);
        Texture texture=FXGL.texture("levelFlag.png");
        Text text=new Text("Level "+FXGL.geti("level"));
        text.setFont(Font.font(26));
        
        HBox levelBox=new HBox(texture,text);
        levelBox.setMaxWidth(80);
        levelBox.setAlignment(Pos.BOTTOM_CENTER);
        getChildren().add(levelBox);

HBox bulletBox=new HBox();
for (int i = 0; i <=2; i++) {
    Texture tt = FXGL.texture("bulletFlag.png");
    tt.setVisible(i==0);
    bulletBox.getChildren().add(tt);
}
getChildren().add(bulletBox);
Entity entity=FXGL.getGameWorld().getEntitiesByType(GameType.PLAYER).get(0);
entity.getComponent(TankLevelComponent.class).valueProperty().addListener((ob,ov,nv)->{
    for (int i = 0; i < nv.intValue(); i++) {
        bulletBox.getChildren().get(i).setVisible(true);
        
    }
});
        FXGL.getip("spawnEnemyAmount").addListener((ob,ov,nv)->{
            ObservableList<Node> nodes=tp.getChildren();
            for(int i=nodes.size()-1;i>=10-nv.intValue();i--){
                nodes.get(i).setVisible(false);
            }
        });
    }
}


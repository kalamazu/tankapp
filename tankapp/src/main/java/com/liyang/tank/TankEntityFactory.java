package com.liyang.tank;

import java.util.Locale;

import com.almasb.fxgl.core.math.FXGLMath;
import com.almasb.fxgl.dsl.FXGL;
import com.almasb.fxgl.dsl.components.EffectComponent;
import com.almasb.fxgl.dsl.components.ExpireCleanComponent;
import com.almasb.fxgl.dsl.components.HealthIntComponent;
import com.almasb.fxgl.dsl.components.ProjectileComponent;
import com.almasb.fxgl.entity.Entity;
import com.almasb.fxgl.entity.EntityFactory;
import com.almasb.fxgl.entity.SpawnData;
import com.almasb.fxgl.entity.Spawns;
import com.almasb.fxgl.entity.components.CollidableComponent;
import com.almasb.fxgl.physics.BoundingShape;
import com.almasb.fxgl.texture.AnimatedTexture;
import com.almasb.fxgl.texture.AnimationChannel;
import com.almasb.fxgl.ui.ProgressBar;
import com.liyang.tank.component.EnemyComponent;
import com.liyang.tank.component.FlagComponent;
import com.liyang.tank.component.TankComponent;
import com.liyang.tank.component.TankLevelComponent;

import javafx.geometry.Point2D;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.util.Duration;


public class TankEntityFactory implements EntityFactory{
    @Spawns("player")
        public Entity newPlayer(SpawnData data ){
            HealthIntComponent hpComponent=new HealthIntComponent(5);
            hpComponent.setValue(5);
            ProgressBar hpBar=new ProgressBar();
            hpBar.setLabelVisible(false);
            hpBar.setWidth(32);
            hpBar.setHeight(8);
            hpBar.setTranslateY(42);
            hpBar.setFill(Color.LIGHTGREEN);
            hpBar.currentValueProperty().addListener((ob,ov,nv)->{
                if(nv.intValue()<=2){
                    hpBar.setFill(Color.RED);
                }else if(nv.intValue()<=4){
                    hpBar.setFill(Color.YELLOW);
                }else {
                    hpBar.setFill(Color.LIGHTGREEN);
                }
            });

            hpBar.maxValueProperty().bind(hpComponent.maxValueProperty());
            hpBar.currentValueProperty().bind(hpComponent.valueProperty());
            return FXGL.entityBuilder(data)
            .type(GameType.PLAYER)
            .viewWithBBox("tank/tank.png")
            .view(hpBar)
            .with(new EffectComponent())      
            .with(new TankComponent())
            .with(hpComponent)
            .with(new TankLevelComponent())
            .with(new CollidableComponent(true))      
            .build();
        }
        @Spawns("border")
        public Entity newBorder(SpawnData data){
            int  width=data.<Integer>get("width");
            int height=data.<Integer>get("height");
            return FXGL.entityBuilder(data)
            .type(GameType.BORDER)
            .viewWithBBox(new Rectangle(width,height,Color.LIGHTGRAY))
            .collidable()
            .build();
            
        }
        @Spawns("brick")
        public Entity newBrick(SpawnData data){
            return FXGL.entityBuilder(data)
            .type(GameType.BRICK)
            .collidable()
            .neverUpdated()
            .bbox(BoundingShape.box(24,24))
            .build();
        }
        @Spawns("greens")
        public Entity newGreens(SpawnData data){
            return FXGL.entityBuilder(data)
            .type(GameType.GREENS)
            .collidable()
            .neverUpdated()
            .bbox(BoundingShape.box(24, 24))
            .zIndex(1000)
            .build();
        }
        @Spawns("stone")
        public Entity newStone(SpawnData data){
            return FXGL.entityBuilder(data)
            .type(GameType.STONE)
            .collidable()
            .neverUpdated()
            .bbox(BoundingShape.box(24, 24))
            .build();
        }
        @Spawns("sea")
        public Entity newSea(SpawnData data){
            AnimationChannel ac=new AnimationChannel(FXGL.image("waves.png"),Duration.seconds(0.5),2);
            AnimatedTexture at=new AnimatedTexture(ac);
            return FXGL.entityBuilder(data)
            .type(GameType.SEA)
            .viewWithBBox(at.loop())
            .build();
        }
        @Spawns("snow")
        public Entity newSnow(SpawnData data){
            return FXGL.entityBuilder(data)
            .type(GameType.SNOW)
            .collidable()
            .neverUpdated()
            .bbox(BoundingShape.box(24, 24))
            .build();
        }
        @Spawns("bullet")
        public Entity newBullet(SpawnData data){
            FXGL.play("shoot.wav");
            Point2D dir=data.get("dir");
            ProjectileComponent projectileComponent=new ProjectileComponent(dir,400);
           
            CollidableComponent collidableComponent=new CollidableComponent(true);
           collidableComponent.addIgnoredType(data.<GameType>get("ownerType"));
            return FXGL.entityBuilder(data)
            .type(GameType.BULLET)
            .viewWithBBox("bullet/bullet.png")
            .with(projectileComponent)
            .with(collidableComponent)
            .collidable()
            .build();
        }
        
        @Spawns("enemy")
        public Entity newEnemy(SpawnData data ){
            return FXGL.entityBuilder(data)
            .type(GameType.ENEMY)
            .viewWithBBox("tank/tank"+FXGLMath.random(1,6)+".png")
            .with(new EffectComponent())     
            .with(new TankComponent())
            .with(new TankLevelComponent())
            .with(new EnemyComponent())
            .with(new CollidableComponent(true))            
            .build();
        }
        @Spawns("explode")
        public Entity newExplode(SpawnData data){
            FXGL.play("explode.wav");
            AnimationChannel ac=new AnimationChannel(FXGL.image("explode.png"), Duration.seconds(0.35),3);
            AnimatedTexture at=new AnimatedTexture(ac);
            return FXGL.entityBuilder(data)
            .view(at.play())
            .with(new ExpireCleanComponent(Duration.seconds(0.35)))
            .build();
        }
        @Spawns("item")
        public Entity newItem(SpawnData data){
            ItemType itemType=FXGLMath.random(ItemType.values()).get();
            data.put("itemType",itemType);
            return FXGL.entityBuilder(data)
            .type(GameType.ITEM)
            .viewWithBBox("item/"+itemType.toString().toLowerCase(Locale.ROOT)+".png" )
            .collidable()
            .build();
        }
        @Spawns("flag")
        public Entity newFlag(SpawnData data){
            return FXGL.entityBuilder(data)
            .type(GameType.FLAG)
            .bbox(BoundingShape.box(48,48))
            .with(new FlagComponent())
            .collidable()
            .build();
        }
}
package com.liyang.tank;


import java.util.List;
import java.util.Map;

import com.almasb.fxgl.app.CursorInfo;
import com.almasb.fxgl.app.GameApplication;
import com.almasb.fxgl.app.GameSettings;
import com.almasb.fxgl.app.scene.GameView;
import com.almasb.fxgl.app.scene.SceneFactory;
import com.almasb.fxgl.app.scene.StartupScene;
import com.almasb.fxgl.core.math.FXGLMath;
import com.almasb.fxgl.core.util.LazyValue;
import com.almasb.fxgl.dsl.FXGL;
import com.almasb.fxgl.entity.Entity;
import com.almasb.fxgl.time.TimerAction;
import com.liyang.tank.Collisions.BulletBorderHandler;
import com.liyang.tank.Collisions.BulletBrickHandler;
import com.liyang.tank.Collisions.BulletBulletHandler;
import com.liyang.tank.Collisions.BulletEnemyHandler;
import com.liyang.tank.Collisions.BulletFlagHandler;
import com.liyang.tank.Collisions.BulletForestHandler;
import com.liyang.tank.Collisions.BulletPlayerHandler;
import com.liyang.tank.Collisions.BulletStoneHandler;
import com.liyang.tank.Collisions.ItemEnemyHandler;
import com.liyang.tank.Collisions.ItemPlayerHandler;
import com.liyang.tank.component.TankComponent;
import com.liyang.tank.ui.FailedScene;
import com.liyang.tank.ui.InfoPane;
import com.liyang.tank.ui.SuccessScene;
import com.liyang.tank.ui.TankStartupScene;

import javafx.geometry.Point2D;
import javafx.geometry.Rectangle2D;
import javafx.scene.input.KeyCode;
import javafx.scene.paint.Color;
import javafx.util.Duration;

/**
 * Hello world!
 *
 */
public class App extends GameApplication
{
    private Entity player;
    private TimerAction freezeEnemyTimerAction;
    private TimerAction reinForceTimerAction;
    
    private LazyValue<FailedScene> failedSceneLazyValue=new LazyValue<>(FailedScene::new);
    private LazyValue<SuccessScene> successSceneLazyValue=new LazyValue<>(SuccessScene::new);

    private TimerAction spawnEnemyTimerAction;
    public static void main( String[] args )
    {
        launch(args);
        System.out.println( "Hello World!" );
    }

    @Override
    protected void initSettings(GameSettings settings) {
        settings.setMainMenuEnabled(true);
        settings.setDefaultCursor(new CursorInfo("cursor.png", 0, 0));
        settings.setTitle("tank");
        settings.setWidth(28*24+6*24);
        settings.setHeight(28*24);
        settings.setAppIcon("Icon.png");
        settings.setVersion("1.0");
        settings.setSceneFactory(new SceneFactory(){
          
            @Override
            public StartupScene newStartup(int width,int height) {
                return new TankStartupScene(width, height);
           
                  
            
            }
        });

    }
    @Override
    protected void initGame() {
        FXGL.getGameScene().setBackgroundColor(Color.BLACK);
        FXGL.getGameWorld().addEntityFactory(new TankEntityFactory());
        FXGL.getip("destroyedEnemyAmount").addListener((ob,ov,nv)->{
            if(nv.intValue()==10){
                FXGL.runOnce(()->{
                    FXGL.getSceneService().pushSubScene(successSceneLazyValue.get());
                },Duration.seconds(0.5));
                }
              
            
        });
       startLevel();
    }
    
    @Override
    protected void initPhysics() {
        
       
        FXGL.getPhysicsWorld().addCollisionHandler(new BulletEnemyHandler());
        FXGL.getPhysicsWorld().addCollisionHandler(new BulletPlayerHandler());
        FXGL.getPhysicsWorld().addCollisionHandler(new BulletBulletHandler());
        FXGL.getPhysicsWorld().addCollisionHandler(new BulletBrickHandler());
        FXGL.getPhysicsWorld().addCollisionHandler(new BulletBorderHandler());
        FXGL.getPhysicsWorld().addCollisionHandler(new BulletStoneHandler());
        FXGL.getPhysicsWorld().addCollisionHandler(new BulletForestHandler());
        FXGL.getPhysicsWorld().addCollisionHandler(new ItemPlayerHandler());      
        FXGL.getPhysicsWorld().addCollisionHandler(new BulletFlagHandler());
        FXGL.getPhysicsWorld().addCollisionHandler(new ItemEnemyHandler());
       
   
    }

    @Override
    protected void initGameVars(Map<String, Object> vars) {
            vars.put("freezeEnemy",false);
            vars.put("spawnEnemyAmount",3);
            vars.put("destroyedEnemyAmount",0);
            vars.put("level",1);
           
            
    }
    
    public void freezeEnemy(){
        FXGL.set("freezeEnemy",true);
        expireTimerAction(freezeEnemyTimerAction);
        
        freezeEnemyTimerAction = FXGL.runOnce(()->{
            FXGL.set("freezeEnemy",false);
        },Duration.seconds(10));
    }

    private void expireTimerAction(TimerAction freezeEnemyTimerAction) {
        if(freezeEnemyTimerAction!=null&&!freezeEnemyTimerAction.isExpired()){
            freezeEnemyTimerAction.expire();
        }
    }

    public void reinforce(){
        if(reinForceTimerAction!=null&&!reinForceTimerAction.isExpired()){
            reinForceTimerAction.expire();
        }
        reinForceTimerAction=FXGL.runOnce(()->{
            updataFlag("brick","map/brick.png");
        }, Duration.seconds(15));
        updataFlag("stone","map/stones.png");
    }

    private void updataFlag(String brick,String assetName) {
        for (Point2D point: Config.POINTS) {
            FXGL.getGameWorld().getEntitiesAt(point).forEach(Entity::removeFromWorld);
            Entity stone=FXGL.spawn(brick,point);
            stone.getViewComponent().addChild(FXGL.texture(assetName));
        }
    }

    @Override
    
    
    protected void initInput() {
        
        FXGL.onKey(KeyCode.UP, ()->{
            if(playerIsActive()){
                TankComponent playerComponent = player.getComponent(TankComponent.class);  
          playerComponent.moveUp();
            }
          
        });
        FXGL.onKey(KeyCode.DOWN , ()->{     
            if(playerIsActive()){
                TankComponent playerComponent = player.getComponent(TankComponent.class);  
            playerComponent.moveDown();
            }        
           
        });
        FXGL.onKey(KeyCode.LEFT, ()->{
            if(playerIsActive()){
                TankComponent playerComponent = player.getComponent(TankComponent.class);  
                playerComponent.moveLeft();
           
            }
        });
        FXGL.onKey(KeyCode.RIGHT, ()->{
            if(playerIsActive()){
                TankComponent playerComponent = player.getComponent(TankComponent.class);  
            playerComponent.moveRight();
            }
            
        });
        FXGL.onKey(KeyCode.SPACE, ()->{
            if(playerIsActive()){
                TankComponent playerComponent = player.getComponent(TankComponent.class);  
                playerComponent.shoot();
            }
           
        });
    }
    
    public boolean playerIsActive(){
        return player!=null&player.isActive();
    }
    
    public void gameOver(){
        FXGL.getSceneService().pushSubScene(failedSceneLazyValue.get());

    }


    public void startLevel(){
       
expireTimerAction(freezeEnemyTimerAction);
expireTimerAction(spawnEnemyTimerAction);
expireTimerAction(reinForceTimerAction);

FXGL.set("freezeEnemy", false);
FXGL.set("spawnEnemyAmount", 0);
FXGL.set("destroyedEnemyAmount",0);
FXGL.setLevelFromMap("map"+FXGL.geti("level")+".tmx");
        
        player = FXGL.spawn("player",50,500);     
        GameView view=new GameView(new InfoPane(),Integer.MAX_VALUE);
        FXGL.getGameScene().addGameView(view);
        FXGL.spawn("enemy",30,30);
        FXGL.spawn("enemy",330,30);
        FXGL.spawn("enemy",580,30);
        FXGL.inc("spawnEnemyAmount",3);

       spawnEnemyTimerAction = FXGL.run(
            ()->{if(FXGL.geti("spawnEnemyAmount")==10){
                if(spawnEnemyTimerAction!=null&&!spawnEnemyTimerAction.isExpired()){
                    spawnEnemyTimerAction.expire();
                   
                }
                return;
            }
                Point2D p=FXGLMath.random(Config.spawnEnemyPosition).get();
                List<Entity> es=FXGL.getGameWorld().getEntitiesInRange(new Rectangle2D(p.getX(), p.getY(),39,39));

                List<Entity>entities=es.stream().filter(entity->
                entity.isType(GameType.PLAYER)
                ||entity.isType(GameType.STONE)
                ||entity.isType(GameType.ENEMY)
                ||entity.isType(GameType.SEA)).toList();
                if(entities.isEmpty()){
                    FXGL.spawn("enemy",p);
                    FXGL.inc("spawnEnemyAmount",1);

                }
                

                },Duration.seconds(2));
         
    }
}


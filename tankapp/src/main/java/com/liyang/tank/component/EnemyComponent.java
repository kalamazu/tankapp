package com.liyang.tank.component;

import com.almasb.fxgl.core.math.FXGLMath;
import com.almasb.fxgl.dsl.FXGL;
import com.almasb.fxgl.entity.component.Component;
import com.liyang.tank.Dir;

public class EnemyComponent extends Component{
    private TankComponent tankComponent;
    @Override
    public void onUpdate(double tpf) {
        if(FXGL.getb("freezeEnemy")){
            return;
        }
        Dir moveDir = tankComponent.getMoveDir();
        if(FXGLMath.randomBoolean(0.025)){
            moveDir=FXGLMath.random(Dir.values()).get();
        }
        switch (moveDir){
            case UP->tankComponent.moveUp();
            case DOWN->tankComponent.moveDown();
            case LEFT->tankComponent.moveLeft();
            case RIGHT->tankComponent.moveRight();
            default->{
                
            }
            
        }
        if(FXGLMath.randomBoolean(0.03)){
            tankComponent.shoot();
        }
    }
     
}

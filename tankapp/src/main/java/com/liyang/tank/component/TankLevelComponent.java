package com.liyang.tank.component;

import com.almasb.fxgl.dsl.components.RechargeableIntComponent;

public class TankLevelComponent extends RechargeableIntComponent{
    
public TankLevelComponent(){
    super(2);
    setValue(0);
}
public void upgrade(){
    restore(1);
}
public void downgrade(){
    damage(1);
}
public void gradeFull(){
    restoreFully();
}

}

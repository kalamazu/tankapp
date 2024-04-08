package com.liyang.tank.component;

import com.almasb.fxgl.dsl.FXGL;
import com.almasb.fxgl.entity.component.Component;

public class FlagComponent extends Component{
    public void onHit(){
        entity.getViewComponent().clearChildren();
        entity.getViewComponent().addChild(FXGL.texture("surrender.png"));
    }

}

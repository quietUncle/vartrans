package com.qt.var;

import com.qt.base.BaseConfigAction;

public class PopAction extends BaseConfigAction {


    @Override
    public void switchConfig(boolean enable) {
        STATE.IS_POP=enable;
    }

    @Override
    public boolean isEnable() {
        return STATE.IS_POP;
    }
}

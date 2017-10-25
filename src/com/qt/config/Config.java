package com.qt.config;

import com.intellij.openapi.components.PersistentStateComponent;
import com.intellij.openapi.components.ServiceManager;
import com.intellij.openapi.components.State;
import com.intellij.openapi.components.Storage;
import com.intellij.util.xmlb.XmlSerializerUtil;
import org.jetbrains.annotations.Nullable;

import java.awt.*;

/**
 * 配置文件
 * Created by KADO on 15/12/17.
 */
@State(
    name = "var_trans",
    storages = {
            @Storage(
                    id = "varTrans",
                    file = "$APP_CONFIG$/var_trans_mode_setting.xml"
            )
    }
)
public class Config implements PersistentStateComponent<Config.State> {

    @Nullable
    @Override
    public State getState() {
        return this.state;
    }

    @Override
    public void loadState(State state) {
        XmlSerializerUtil.copyBean(state, this.state);
    }

    public State state = new State();

    public Config() {

        defaultInitState();

    }

    public void defaultInitState() {

        state.IS_POP=true;
    }

    public static Config getInstance() {
        return ServiceManager.getService(Config.class);
    }

    public static final class State {
        /**
         * 是否显示列表
         */
       public boolean IS_POP=true;


    }


}

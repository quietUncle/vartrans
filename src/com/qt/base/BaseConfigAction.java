package com.qt.base;

import com.intellij.icons.AllIcons;
import com.intellij.openapi.actionSystem.AnAction;
import com.intellij.openapi.actionSystem.AnActionEvent;
import com.intellij.openapi.actionSystem.Presentation;
import com.qt.config.Config;

public abstract class BaseConfigAction extends AnAction implements ConfigControl{
    public static Config.State STATE= Config.getInstance().getState();

    public BaseConfigAction() {
        super();
        if(isEnable()){
            showPresention(getTemplatePresentation(),true);
        }else{
            showPresention(getTemplatePresentation(),false);
        }

    }

    @Override
    public void actionPerformed(AnActionEvent anActionEvent) {
        if(isEnable()){
            showPresention(anActionEvent.getPresentation(),false);
            switchConfig(false);
        }else{
            showPresention(anActionEvent.getPresentation(),true);
            switchConfig(true);
        }
    }


    public void showPresention(Presentation presentation,boolean show){
        if(show){
            presentation.setIcon(AllIcons.General.InspectionsOK);
        }else{
            presentation.setIcon(null);
        }
    }

}

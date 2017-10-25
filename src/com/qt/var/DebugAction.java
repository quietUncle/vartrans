package com.qt.var;

import com.intellij.icons.AllIcons;
import com.intellij.openapi.actionSystem.AnAction;
import com.intellij.openapi.actionSystem.AnActionEvent;
import com.qt.base.State;

public class DebugAction extends AnAction {

    @Override
    public void actionPerformed(AnActionEvent anActionEvent) {
        if (State.instance().isDebug()) {
            State.instance().setDebug(false);
            anActionEvent.getPresentation().setIcon(null);
        } else {
            anActionEvent.getPresentation().setIcon(AllIcons.General.InspectionsOK);
            State.instance().setDebug(true);
        }
    }
}

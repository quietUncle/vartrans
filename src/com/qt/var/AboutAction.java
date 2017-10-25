package com.qt.var;

import com.intellij.openapi.actionSystem.AnAction;
import com.intellij.openapi.actionSystem.AnActionEvent;
import com.intellij.openapi.ui.Messages;
import com.qt.base.BaseConfigAction;

/**
 * 显示软件基本信息
 */
public class AboutAction extends AnAction {


    @Override
    public void actionPerformed(AnActionEvent anActionEvent) {
        Messages.showInfoMessage("<html>一款中文转变量的插件,<br/><br/>作者:安静大叔<br/><br/><a href='https://github.com/quietUncle/vartrans'>https://github.com/quietUncle/vartrans","关于");
    }
}

package com.qt.gui;

import com.intellij.openapi.options.Configurable;
import com.intellij.openapi.options.ConfigurationException;
import com.qt.config.Config;
import org.jetbrains.annotations.Nls;
import org.jetbrains.annotations.Nullable;

import javax.swing.*;

public class Setting implements Configurable {
    private JTextField apiKey;
    private JTextField apiSecret;
    private JPanel rootPanel;
    private JTextField defaultKey;
    private JTextField defaultSecret;
    private JLabel addr;


    @Nls
    @Override
    public String getDisplayName() {
        return "varTrans";
    }

    @Nullable
    @Override
    public JComponent createComponent() {
        apiKey.setText( Config.getInstance().getState().API_KEY);
        apiSecret.setText( Config.getInstance().getState().API_SECRET);
        defaultKey.setText( Config.getInstance().getState().DEFAULT_API_KEY);
        defaultSecret.setText( Config.getInstance().getState().DEFAULT_API_SECRET);
        addr.setText("<html>Api Key申请地址    <a href='http://ai.youdao.com'>http://ai.youdao.com");
        return rootPanel;
    }

    @Override
    public boolean isModified() {
        if (apiKey.getText().trim().equals("")
                || apiSecret.getText().trim().equals("")) {
            return false;
        }

        return true;
    }

    @Override
    public void apply() throws ConfigurationException {
        if (apiKey.getText().trim().equals("")
                ) {
            throw new ConfigurationException("apiKey  is empty  ");
        }
        if (apiSecret.getText().trim().equals("")
                ) {
            throw new ConfigurationException("apiSecret is empty");
        }
        Config.getInstance().getState().API_KEY = apiKey.getText();
        Config.getInstance().getState().API_SECRET = apiSecret.getText();
    }
}

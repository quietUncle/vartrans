package com.qt.base;

import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.intellij.openapi.actionSystem.AnAction;
import com.intellij.openapi.actionSystem.AnActionEvent;
import com.intellij.openapi.actionSystem.CommonDataKeys;
import com.intellij.openapi.command.WriteCommandAction;
import com.intellij.openapi.editor.Editor;
import com.intellij.openapi.editor.EditorModificationUtil;
import com.intellij.openapi.util.text.StringUtil;
import com.qt.utils.Constants;
import com.qt.utils.HttpClientPool;

/**
 * 基类
 */
public abstract class BaseAction extends AnAction implements VarParse {
    private static JsonParser parser = new JsonParser();

    @Override
    public void actionPerformed(AnActionEvent anActionEvent) {
        String defaultKey = Constants.DEFAULT_API_KEY_VAL;
        String defaultSercet = Constants.DEFAULT_SERCET_KEY_VAL;
        translate(anActionEvent,defaultSercet, defaultKey);
    }


    private void translate(AnActionEvent e,String sercet, String apiKey) {
        Editor editor = e.getData(CommonDataKeys.EDITOR);
        if (editor != null) {
             String text = editor.getSelectionModel().getSelectedText();
            if (text != null)
                try {


                    text = StringUtil.replace(text, "*", "");
                    text = StringUtil.replace(text, "\n", "");
                    text = StringUtil.replace(text, "<tt>", "");
                    text = StringUtil.replace(text, "</tt>", "");
                    text = StringUtil.replace(text, "@link", "");
                    text = StringUtil.replace(text, "{", "");
                    text = StringUtil.replace(text, "}", "");

                    String result = HttpClientPool.getHttpClient().get(Constants.genUrl(sercet,apiKey, text));
                    JsonObject resultJson = parser.parse(result).getAsJsonObject();
                    String translationText = resultJson.get("translation").getAsJsonArray().get(0).getAsString();
                    StringBuffer sb = new StringBuffer();
                    String words[] = translationText.split(" ");
                    sb.append(parseResult(words));
                    final String t=text;
                    WriteCommandAction.runWriteCommandAction(e.getProject(), new Runnable() {
                        @Override
                        public void run() {
                            if(t.equals("安静大叔")){
                                EditorModificationUtil.insertStringAtCaret(editor,"QuietUncle_Cool", true);
                            }else{
                                EditorModificationUtil.insertStringAtCaret(editor, sb.toString(), true);
                            }
                        }
                    });

                } catch (Exception exception) {
                    onParseError(exception);
//                    if(State.instance().isDebug()){
//                        Messages.showErrorDialog(exception.toString(),"发生了错误~~");
//                    }
                    exception.printStackTrace();
                }
        }
    }

}

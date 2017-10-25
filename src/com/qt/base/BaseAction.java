package com.qt.base;

import a.d.C;
import com.google.gson.Gson;
import com.google.gson.JsonParser;
import com.intellij.openapi.actionSystem.*;
import com.intellij.openapi.command.WriteCommandAction;
import com.intellij.openapi.editor.Editor;
import com.intellij.openapi.editor.EditorModificationUtil;
import com.intellij.openapi.ui.Messages;
import com.intellij.openapi.ui.popup.JBPopupFactory;
import com.intellij.openapi.ui.popup.ListPopup;
import com.intellij.openapi.util.text.StringUtil;
import com.qt.bean.ApiResult;
import com.qt.bean.ResultBean;
import com.qt.config.Config;
import com.qt.utils.Constants;
import com.qt.utils.HttpClientPool;
import org.omg.CORBA.PUBLIC_MEMBER;

import java.util.ArrayList;
import java.util.List;

/**
 * 基类
 */
public abstract class BaseAction extends AnAction implements VarParse {
    private static JsonParser parser = new JsonParser();
    private static Gson gson = new Gson();
    private String TAG = "var_result_pop";
    private static Config.State STATE= Config.getInstance().getState();
    @Override
    public void actionPerformed(AnActionEvent anActionEvent) {
        String defaultKey = STATE.API_KEY;
        String defaultSecret = STATE.API_SECRET;
        translate(anActionEvent, defaultSecret, defaultKey);
    }


    private void translate(AnActionEvent e, String sercet, String apiKey) {
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

                    String result = HttpClientPool.getHttpClient().get(Constants.genUrl(sercet, apiKey, text));
                    ApiResult data = gson.fromJson(result, ApiResult.class);
                    if(data.getErrorCode().equals("202")){
                        Messages.showErrorDialog("请确认setting里面的ApiKey和ApiSecret是否正确","Api验证错误");
                    }else{
                        showPop(e, editor, resolveResult(text, data));
                    }
                } catch (Exception exception) {
                    onParseError(exception);
//                    if(State.instance().isDebug()){
//                        Messages.showErrorDialog(exception.toString(),"发生了错误~~");
//                    }
                    exception.printStackTrace();
                }
        }
    }


    public List<ResultBean> resolveResult(String key, ApiResult data) {
        List<ResultBean> list = new ArrayList<>();
        //首先添加翻译
        for (String v : data.getTranslation()) {
            list.add(new ResultBean(key, v, "标准释义"));
        }
        //添加基础翻译
        if (data.getBasic() != null) {
            for (String v : data.getBasic().getExplains()) {
                list.add(new ResultBean(key, v, "简明释义"));
            }
        }
        if (data.getWeb() != null) {
            //添加网络翻译
            for (ApiResult.WebBean webBean : data.getWeb()) {
                for (String v : webBean.getValue()) {
                    list.add(new ResultBean(webBean.getKey(), v, "网络释义"));
                }
            }
        }
        //最多取10
        list=new ArrayList<>(list.subList(0,list.size()>10?10:list.size()));
        return list;
    }

    public void showPop(AnActionEvent e, Editor editor, List<ResultBean> list) throws Exception {
        if(!Config.getInstance().getState().IS_POP){
            ResultBean resultBean=list.get(0);
            StringBuffer sb = new StringBuffer();
            String words[] = resultBean.getValue().split(" ");
            sb.append(parseResult(words));
            WriteCommandAction.runWriteCommandAction(e.getProject(), () -> {
                EditorModificationUtil.insertStringAtCaret(editor, sb.toString(), true);
            });
            return;
        }
        DefaultActionGroup actionGroup = (DefaultActionGroup) ActionManager.getInstance().getAction(TAG);
        actionGroup.removeAll();

        for (ResultBean resultBean : list) {
            StringBuffer sb = new StringBuffer();
            String words[] = resultBean.getValue().split(" ");
            sb.append(parseResult(words));
            String text="["+resultBean.getKey() + "]->" + resultBean.getValue();
            actionGroup.add(new AnAction(text) {
                @Override
                public void actionPerformed(AnActionEvent e) {
                    WriteCommandAction.runWriteCommandAction(e.getProject(), () -> {
                        EditorModificationUtil.insertStringAtCaret(editor, sb.toString(), true);
                    });

                }
            });
        }
        ListPopup listPopup = JBPopupFactory.getInstance().createActionGroupPopup("翻译结果("+name()+")", actionGroup, e.getDataContext(), JBPopupFactory.ActionSelectionAid.SPEEDSEARCH, false);
        listPopup.showInBestPositionFor(e.getDataContext());
    }

}

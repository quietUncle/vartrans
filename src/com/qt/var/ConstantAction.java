package com.qt.var;

import com.qt.base.BaseAction;
import com.qt.base.Mode;

public class ConstantAction extends BaseAction {


    @Override
    public int parseMode() {
        return Mode.CONSTANT;
    }

    @Override
    public void onParseError(Exception e) {

    }

    @Override
    public String parseResult(String[] result) throws Exception {
        StringBuffer sb =new StringBuffer();
        for (int i = 0; i < result.length; i++) {
            if(i==0){
                sb.append((result[i].toUpperCase()));
            }else{
                sb.append("_");
                sb.append((result[i].toUpperCase()));
            }

        }
        return sb.toString();
    }
}

package com.qt.var;

import com.qt.base.BaseAction;
import com.qt.base.Mode;
import com.qt.utils.StringUtils;

public class SmallHumpAction extends BaseAction {


    @Override
    public int parseMode() {
        return Mode.SMALL_HUMP;
    }

    @Override
    public void onParseError(Exception e) {

    }

    @Override
    public String parseResult(String[] result) throws Exception {
        StringBuffer sb =new StringBuffer();
        for (int i = 0; i < result.length; i++) {
            if (i == 0) {
                sb.append(StringUtils.lowerCase(result[i]));
            } else {
                sb.append(StringUtils.upperCase(result[i]));
            }
        }
        return sb.toString();
    }
}

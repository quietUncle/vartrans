package com.qt.base;

public interface VarParse {
    /**
     * 解析模式
     *
     * @return
     */
    int parseMode();

    void onParseError(Exception e);

    String parseResult(String result[]) throws Exception;

}

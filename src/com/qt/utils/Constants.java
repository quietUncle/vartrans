package com.qt.utils;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

/**
 * @author by laugh on 2016/4/12.
 */
public class Constants {
                                     //http://openapi.youdao.com/api?q=good&from=EN&to=zh_CHS&appKey=ff889495-4b45-46d9-8f48-946554334f2a&salt=2&sign=1995882C5064805BC30A39829B779D7B

    private final static String url = "http://openapi.youdao.com/api?from=auto&to=auto&appKey=%s&q=%s&salt=%s&sign=%s";
    public final static String DEFAULT_API_KEY_VAL = "108770214d7b90c8";
    public final static String DEFAULT_SERCET_KEY_VAL = "oRTxCoNRm2DKQUDbI7ajiD5p5QPuNUf0";

    private final static String resultFormate = "%s\n%s\n%s";

    public static String genUrl(String sercet,String apiKey,String text) throws UnsupportedEncodingException {
        String salt = String.valueOf(System.currentTimeMillis());
        String sign = md5(apiKey + text + salt+ sercet);
        text = URLEncoder.encode(text, "utf-8");
        return String.format(url,apiKey,text,salt,sign);
    }

    public static String formateResult(String translationText,String basicExplainText,String webText) {
        return String.format(resultFormate,translationText,basicExplainText==null?"":basicExplainText,webText==null?"":webText);
    }



    /**
     * 生成32位MD5摘要
     * @param string
     * @return
     */
    public static String md5(String string) {
        if(string == null){
            return null;
        }
        char hexDigits[] = {'0', '1', '2', '3', '4', '5', '6', '7', '8', '9',
                'A', 'B', 'C', 'D', 'E', 'F'};

        try{
            byte[] btInput = string.getBytes("utf-8");
            /** 获得MD5摘要算法的 MessageDigest 对象 */
            MessageDigest mdInst = MessageDigest.getInstance("MD5");
            /** 使用指定的字节更新摘要 */
            mdInst.update(btInput);
            /** 获得密文 */
            byte[] md = mdInst.digest();
            /** 把密文转换成十六进制的字符串形式 */
            int j = md.length;
            char str[] = new char[j * 2];
            int k = 0;
            for (byte byte0 : md) {
                str[k++] = hexDigits[byte0 >>> 4 & 0xf];
                str[k++] = hexDigits[byte0 & 0xf];
            }
            return new String(str);
        }catch(NoSuchAlgorithmException | UnsupportedEncodingException e){
            return null;
        }
    }



}

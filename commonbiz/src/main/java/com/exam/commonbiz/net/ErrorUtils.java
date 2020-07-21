package com.exam.commonbiz.net;

import android.content.Context;

/**
 *
 * @Author yuexingxing
 * @time 2020/6/11
 */
public class ErrorUtils {
    public static String intCode2strCode(Context context, int intCode){
        //当intcode映射不到时，提示“服务异常”
        if(context.getResources().getIdentifier(context.getPackageName()+":string/" + "error_code_" + intCode, null, null) == 0){
            return "ercode_service_exception";
        }else {
            return context.getString(context.getResources().getIdentifier(context.getPackageName()+":string/" + "error_code_" + intCode, null, null));
        }
    }

    public static String strCode2str(Context context, String strCode){
        //当intcode映射不到时，提示“服务异常”
        if(context.getResources().getIdentifier(context.getPackageName()+":string/" + strCode, null, null) == 0){
            return "Service Exception";
        }else {
            return context.getString(context.getResources().getIdentifier(context.getPackageName()+":string/" + strCode, null, null));
        }
    }

    public static String intCode2str(Context context, int intCode){
        return strCode2str(context, intCode2strCode(context, intCode));
    }
}

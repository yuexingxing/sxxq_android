package com.exam.commonbiz.log;

import android.util.Log;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;


/**
 * @Author yuexingxing
 * @time 2020/6/10
 */
public class LogUtil {
	
	public static int logFilterLevel = 0;

	public static void i(String tag, String msg) {
		if (logFilterLevel < 3) {
			Log.i(tag, msg);
		}
	}

	public static void d(String tag, String msg) {
		if (logFilterLevel < 2) {
			Log.d(tag, msg);
		}
	}

	public static void e(String tag, String msg) {
		if (logFilterLevel < 5) {
			Log.e(tag, msg);
		}
	}

	public static void w(String tag, String msg) {
		if (logFilterLevel < 4) {
			Log.w(tag, msg);
		}
	}

	public static void v(String tag, String msg) {
		if (logFilterLevel < 1) {
			Log.v(tag, msg);
		}
	}

	public static final String LINE_SEPARATOR = System.getProperty("line.separator");

	public static void printLine(String tag, boolean isTop) {
		if (isTop) {
			Log.d(tag, "\n╔═══════════════════════════════════════════════════════════════════════════════════════");
		} else {
			Log.d(tag, "╚═══════════════════════════════════════════════════════════════════════════════════════");
		}
	}
	public static void printJson(String tag, String msg, String headString) {

		String message;

		try {
			if (msg.startsWith("{")) {
				JSONObject jsonObject = new JSONObject(msg);
				message = jsonObject.toString(4);//最重要的方法，就一行，返回格式化的json字符串，其中的数字4是缩进字符数
			} else if (msg.startsWith("[")) {
				JSONArray jsonArray = new JSONArray(msg);
				message = jsonArray.toString(4);
			} else {
				message = msg;
			}
		} catch (JSONException e) {
			message = msg;
		}

		printLine(tag, true);
		message = headString + LINE_SEPARATOR + message;
		String[] lines = message.split(LINE_SEPARATOR);
		for (String line : lines) {
			Log.d(tag, "" + line);
		}
		printLine(tag, false);
	}


}

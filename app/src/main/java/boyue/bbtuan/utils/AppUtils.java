package boyue.bbtuan.utils;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.telephony.TelephonyManager;
import android.text.TextUtils;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class AppUtils {
	public AppUtils(Context context) {
		this.context=context;
	}
	private Context context;
	/**
	 * 验证手机格式
	 */
	public static boolean isMobileNO(String mobiles) {
		/*
		 * 移动：134、135、136、137、138、139、150、151、157(TD)、158、159、187、188
		 * 联通：130、131、132、152、155、156、185、186 电信：133、153、180、189、（1349卫通）
		 * 总结起来就是第一位必定为1，第二位必定为3或5或8，其他位置的可以为0-9
		 */
		String telRegex = "[1][358]\\d{9}";// "[1]"代表第1位为数字1，"[358]"代表第二位可以为3、5、8中的一个，"\\d{9}"代表后面是可以是0～9的数字，有9位。
		if (TextUtils.isEmpty(mobiles))
			return false;
		else
			return mobiles.matches(telRegex);
	}

	/**
	 * 判断网路是否可以用
	 * @return
	 */
	public static boolean checkNetwork(Context context) {

		if(isNetworkConnected(context)==true){
			if(isWifiConnected(context)==true || isMobileConnected(context)==true){
				return true;
			}else{
				return false;
			}
		}else{
			return false;
		}
	}
	/**
	 * 验证输入邮政编号
	 *
	 * @param
	 * @return 如果是符合格式的字符串,返回 <b>true </b>,否则为 <b>false </b>
	 */
	public static boolean isPostalcode(String str) {
		String regex = "^\\d{6}$";
		return str.matches(regex);
	}
	/**
	 * @param
	 * @return 如果是符合邮箱格式的字符串,返回<b>true</b>,否则为<b>false</b>
	 */
	public static boolean isEmail( String str ) {
		String regex = "[a-zA-Z_]{1,}[0-9]{0,}@(([a-zA-z0-9]-*){1,}\\.){1,3}[a-zA-z\\-]{1,}" ;
		return match( regex ,str );
	}
	private static boolean match( String regex ,String str ){
		Pattern pattern = Pattern.compile(regex);
		Matcher matcher = pattern.matcher( str );
		return matcher.matches();
	}
	public int dp2px(int value) {
		float v = context.getResources().getDisplayMetrics().density;
		return (int) (v * value + 0.5f);
	}

	public int sp2px(int value) {
		float v = context.getResources().getDisplayMetrics().scaledDensity;
		return (int) (v * value + 0.5f);
	}
	/**
	 * 判断是否有网络连接
	 * 
	 * @param context
	 * @return
	 */
	public static boolean isNetworkConnected(Context context) {
		if (context != null) {
			ConnectivityManager mConnectivityManager = (ConnectivityManager) context
					.getSystemService(Context.CONNECTIVITY_SERVICE);
			NetworkInfo mNetworkInfo = mConnectivityManager
					.getActiveNetworkInfo();
			if (mNetworkInfo != null) {
				return mNetworkInfo.isAvailable();
			}
		}
		return false;
	}

	/**
	 * 判断wifi 是否可用
	 * 
	 * @param context
	 * @return
	 */
	public static boolean isWifiConnected(Context context) {
		if (context != null) {
			ConnectivityManager mConnectivityManager = (ConnectivityManager) context
					.getSystemService(Context.CONNECTIVITY_SERVICE);
			NetworkInfo mWiFiNetworkInfo = mConnectivityManager
					.getNetworkInfo(ConnectivityManager.TYPE_WIFI);
			if (mWiFiNetworkInfo != null) {
				return mWiFiNetworkInfo.isAvailable();
			}
		}
		return false;
	}

	/**
	 * 判断手机网络是否可用
	 * 
	 * @param context
	 * @return
	 */
	public static boolean isMobileConnected(Context context) {
		if (context != null) {
			ConnectivityManager mConnectivityManager = (ConnectivityManager) context
					.getSystemService(Context.CONNECTIVITY_SERVICE);
			NetworkInfo mMobileNetworkInfo = mConnectivityManager
					.getNetworkInfo(ConnectivityManager.TYPE_MOBILE);
			if (mMobileNetworkInfo != null) {
				return mMobileNetworkInfo.isAvailable();
			}
		}
		return false;
	}
    /**
     * 获取当前网络类型
     *
     * @param context
     * @return 2G/3G/4G/WIFI/no/unknown
     */
    public static String getNetType(Context context) {
        ConnectivityManager cm = (ConnectivityManager) context
                .getSystemService(Context.CONNECTIVITY_SERVICE);
        final NetworkInfo info = cm.getActiveNetworkInfo();
        if (info == null || !info.isAvailable()) {
            return "网络不可用";
        }
        if (info.getType() == ConnectivityManager.TYPE_WIFI) {
            return "WiFi";
        }
        if (info.getType() == ConnectivityManager.TYPE_MOBILE) {
            int sub = info.getSubtype();
            switch (sub) {

                case TelephonyManager.NETWORK_TYPE_GPRS:
                case TelephonyManager.NETWORK_TYPE_EDGE:
                case TelephonyManager.NETWORK_TYPE_CDMA://电信的2G
                case TelephonyManager.NETWORK_TYPE_1xRTT:
                case TelephonyManager.NETWORK_TYPE_IDEN:
                    //以上的都是2G网络
                    return "2G";

                case TelephonyManager.NETWORK_TYPE_UMTS:
                case TelephonyManager.NETWORK_TYPE_EVDO_A:
                case TelephonyManager.NETWORK_TYPE_HSDPA:
                case TelephonyManager.NETWORK_TYPE_HSUPA:
                case TelephonyManager.NETWORK_TYPE_HSPA:
                case TelephonyManager.NETWORK_TYPE_EVDO_B:
                case TelephonyManager.NETWORK_TYPE_EHRPD:
                case TelephonyManager.NETWORK_TYPE_HSPAP:
                    //以上的都是3G网络
                    return "3G";

                case TelephonyManager.NETWORK_TYPE_LTE:

                    return "4G";

                case TelephonyManager.NETWORK_TYPE_UNKNOWN:

                    return "未知网络";

                default:
                    return "未知网络";
            }
        }
        return "unknown";
    }

    public static int countLength(String value) {

        int valueLength = 0;

        String chinese = "[\u0391-\uFFE5]";

        /* 获取字段值的长度，如果含中文字符，则每个中文字符长度为2，否则为1 */

        for (int i = 0; i < value.length(); i++) {

            /* 获取一个字符 */

            String temp = value.substring(i, i + 1);

            /* 判断是否为中文字符 */

            if (temp.matches(chinese)) {

                /* 中文字符长度为2 */

                valueLength += 2;

            } else {

                /* 其他字符长度为1 */

                valueLength += 1;

            }

        }

        return valueLength;

    }

}

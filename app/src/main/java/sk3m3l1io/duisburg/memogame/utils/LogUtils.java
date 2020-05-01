package sk3m3l1io.duisburg.memogame.utils;

import android.content.res.Configuration;
import android.content.res.Resources;
import android.os.Build;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.WindowManager;

public final class LogUtils {
    private LogUtils() {
    }

    public static String TAG = "malakia";

    public static void logDeviceDimensionInfo(WindowManager wm) {
        DisplayMetrics dm = new DisplayMetrics();
        wm.getDefaultDisplay().getMetrics(dm);
        int height = dm.heightPixels;
        int width = dm.widthPixels;
        int dpi = (int) (dm.density * 160f);
        Log.d(TAG, "Device dimensions: w = " + width + ", h = " + height + ", dpi = " + dpi);
    }

    public static void logSmallestWidthDpi(Resources r) {
        Configuration config = r.getConfiguration();
        int sm = config.smallestScreenWidthDp;
        Log.d(TAG, "Device sm is " + sm);
    }

    public static void logDeviceName() {
        Log.d(TAG, "Device: " + getDeviceName());
    }

    private static String getDeviceName() {
        String manufacturer = Build.MANUFACTURER;
        String model = Build.MODEL;
        if (model.toLowerCase().startsWith(manufacturer.toLowerCase())) {
            return capitalize(model);
        } else {
            return capitalize(manufacturer) + " " + model;
        }
    }


    private static String capitalize(String s) {
        if (s == null || s.length() == 0) {
            return "";
        }
        char first = s.charAt(0);
        if (Character.isUpperCase(first)) {
            return s;
        } else {
            return Character.toUpperCase(first) + s.substring(1);
        }
    }
}

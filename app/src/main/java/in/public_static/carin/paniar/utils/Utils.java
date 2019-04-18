package in.public_static.carin.paniar.utils;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.res.Configuration;
import android.graphics.Typeface;
import android.net.ConnectivityManager;
import android.os.Build;
import android.preference.PreferenceManager;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.TextView;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Locale;
import java.util.zip.ZipEntry;
import java.util.zip.ZipInputStream;

import in.public_static.carin.paniar.App;
import in.public_static.carin.paniar.R;


public class Utils {
    private static Utils myInstance;
    public final String version = "نسخهٔ";
    public final char[] persianDigits = {'۰', '۱', '۲', '۳', '۴', '۵', '۶',
            '۷', '۸', '۹'};
    private final char[] arabicDigits = {'0', '1', '2', '3', '4', '5', '6',
            '7', '8', '9'};

    public Typeface typeface;

    private Utils() {
    }

    public static Utils getInstance() {
        if (myInstance == null) {
            myInstance = new Utils();
        }
        return myInstance;
    }

    public void setTheme(Context context) {
        SharedPreferences prefs = PreferenceManager
                .getDefaultSharedPreferences(context);
        String key = prefs.getString("Theme", "");
        int theme = R.style.AppTheme; // default theme

//        if (key.equals("LightTheme")) {
//            theme = R.style.LightTheme;
//        } else if (key.equals("DarkTheme")) {
//            theme = R.style.DarkTheme;
//        }

        context.setTheme(theme);
    }

    public void prepareTextView(TextView textView) {
        if (typeface == null) {
            typeface = Typeface.createFromAsset(textView.getContext()
                    .getAssets(), "fonts/IRANSansMobile.ttf");//"fonts/NotoNaskhArabic-Regular.ttf");
        }
        textView.setTypeface(typeface);
        textView.setLineSpacing(0f, 0.8f);
    }

    public void prepareTextView(View view, boolean bold) {
        String tf = "fonts/Vazir-Light-FD-WOL.ttf";
        if (bold) {
            tf = "fonts/Vazir-Bold-FD-WOL.ttf";
        }
        Typeface typeface = Typeface.createFromAsset(view.getContext()
                .getAssets(), tf);//"fonts/NotoNaskhArabic-Regular.ttf");
        try {
            TextView textView = (TextView) view;
            textView.setTypeface(typeface);
            textView.setLineSpacing(0f, 0.8f);
        } catch (Exception e) {
            e.printStackTrace();
        }
        try {
            EditText editText = (EditText) view;
            editText.setTypeface(typeface);
            editText.setLineSpacing(0f, 0.8f);
        } catch (Exception e) {
            e.printStackTrace();
        }
        try {
            CheckBox checkBox = (CheckBox) view;
            checkBox.setTypeface(typeface);
            checkBox.setLineSpacing(0f, 0.8f);
        } catch (Exception e) {
            e.printStackTrace();
        }
        try {
            RadioButton radioButton = (RadioButton) view;
            radioButton.setTypeface(typeface);
            radioButton.setLineSpacing(0f, 0.8f);
        } catch (Exception e) {
            e.printStackTrace();
        }
        try {
            Button button = (Button) view;
            button.setTypeface(typeface);
            button.setLineSpacing(0f, 0.8f);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public String formatNumber(int number, char[] digits) {
        return formatNumber(Integer.toString(number), digits);
    }

    public String formatNumber(String number, char[] digits) {
        if (digits == arabicDigits)
            return number;
        if (digits == null)
            digits = persianDigits;

        StringBuilder sb = new StringBuilder();
        for (char i : number.toCharArray()) {
            if (Character.isDigit(i)) {
                sb.append(digits[Integer.parseInt(i + "")]);
            } else {
                sb.append(i);
            }
        }
        return sb.toString();
    }

    public boolean isNetworkAvailable(final Context context) {
        final ConnectivityManager connectivityManager = ((ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE));
        return connectivityManager.getActiveNetworkInfo() != null && connectivityManager.getActiveNetworkInfo().isConnected();
    }

    public void shareTextAs(Context context, String text) {
        Intent i = new Intent(Intent.ACTION_SEND);
        i.setType("text/plain");
        i.putExtra(Intent.EXTRA_TEXT, text);
        context.startActivity(Intent.createChooser(i, "به اشتراک گذاری"));
    }

    public String PrepareNumberForClock(int number) {
        String param = number + "";
        if (param.length() < 2) {
            param = "0" + param;
        }
        return param;
    }

    public void unzip(String zipFile, String location) throws IOException {
        int size;
        int BUFFER_SIZE = 1024;
        byte[] buffer = new byte[BUFFER_SIZE];

        try {
            if (!location.endsWith("/")) {
                location += "/";
            }
            File f = new File(location);
            if (!f.isDirectory()) {
                f.mkdirs();
            }
            ZipInputStream zin = new ZipInputStream(new BufferedInputStream(new FileInputStream(zipFile), BUFFER_SIZE));
            try {
                ZipEntry ze = null;
                while ((ze = zin.getNextEntry()) != null) {
                    String path = location + ze.getName();
                    File unzipFile = new File(path);

                    if (ze.isDirectory()) {
                        if (!unzipFile.isDirectory()) {
                            unzipFile.mkdirs();
                        }
                    } else {
                        // check for and create parent directories if they don't exist
                        File parentDir = unzipFile.getParentFile();
                        if (null != parentDir) {
                            if (!parentDir.isDirectory()) {
                                parentDir.mkdirs();
                            }
                        }

                        // unzip the file
                        FileOutputStream out = new FileOutputStream(unzipFile, false);
                        BufferedOutputStream fout = new BufferedOutputStream(out, BUFFER_SIZE);
                        try {
                            while ((size = zin.read(buffer, 0, BUFFER_SIZE)) != -1) {
                                fout.write(buffer, 0, size);
                            }

                            zin.closeEntry();
                        } finally {
                            fout.flush();
                            fout.close();
                        }
                    }
                }
            } finally {
                zin.close();
            }
        } catch (Exception e) {
            Log.e("dddddd", "Unzip exception", e);
        }
    }

    public boolean isFirstUse() {
        return App.preferences.getBoolean("isFirstUse-102", true);
    }

    public void setFirstUseFalse() {
        try {
            SharedPreferences.Editor editor = App.preferences.edit();
            editor.putBoolean("isFirstUse-102", false);
            editor.apply();

        } catch (Exception e) {

        }
    }

    public void writePrefrences(String key, boolean value) {
        SharedPreferences.Editor editor = App.preferences.edit();
        editor.putBoolean(key, value);
        editor.apply();
    }

    public void writePrefrences(String key, int value) {
        SharedPreferences.Editor editor = App.preferences.edit();
        editor.putInt(key, value);
        editor.apply();
    }

    public void writePrefrences(String key, String value) {
        SharedPreferences.Editor editor = App.preferences.edit();
        editor.putString(key, value);
        editor.apply();
    }

    public void writePrefrences(String key, long value) {
        SharedPreferences.Editor editor = App.preferences.edit();
        editor.putLong(key, value);
        editor.apply();
    }

    public void writePrefrences(String key, float value) {
        SharedPreferences.Editor editor = App.preferences.edit();
        editor.putFloat(key, value);
        editor.apply();
    }

    public String getUserToken() {
        return App.preferences.getString("user_token", "0");
    }

    public void setUserToken(String token) {
        writePrefrences("user_token", token);
    }

    public boolean isUserLogged() {
        Boolean param = false;
        if (getInstance().getUserToken() != "0") {
            param = true;
        }
        return param;
    }

    public void logOut() {
        SharedPreferences.Editor editor = App.preferences.edit();
        editor.remove("user_token");
        editor.apply();

//        new Thread(new Runnable() {
//            @Override
//            public void run() {
//                try {
//                    FirebaseInstanceId.getInstance().deleteInstanceId();
//                    Log.d("fcmService", "before logOut: " + FirebaseInstanceId.getInstance().getToken());
//                    Log.d("fcmService", "run: " + "fcm instance deleted");
//                    Log.d("fcmService", "after logOut: " + FirebaseInstanceId.getInstance().getToken());
//                } catch (IOException e) {
//                    e.printStackTrace();
//                    Log.d("fcmService", "run: " + e.getMessage());
//                }
//            }
//        }).start();
    }

    public String getFcmToken() {
        return App.preferences.getString("fcm_token", "");
    }

    public boolean isFcmTokenSynced() {
        return App.preferences.getBoolean("fcm_token_synced", false);
    }

    public void setFcmTokenSynced() {
        writePrefrences("fcm_token_synced", true);
    }

    public void setLocal(String lang) {
        Locale locale = new Locale(lang);
        Locale.setDefault(locale);
        Configuration config = App.context.getResources().getConfiguration();
        config.locale = locale;
        App.context.getResources().updateConfiguration(config,
                App.context.getResources().getDisplayMetrics());
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN_MR1) {
            config.setLayoutDirection(locale);
        }
    }
}
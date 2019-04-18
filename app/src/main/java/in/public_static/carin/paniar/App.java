package in.public_static.carin.paniar;

import android.content.Context;
import android.content.SharedPreferences;
import android.content.res.Configuration;
import android.database.sqlite.SQLiteDatabase;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Environment;
import android.preference.PreferenceManager;
import android.support.multidex.MultiDex;
import android.support.multidex.MultiDexApplication;
import android.telephony.TelephonyManager;
import android.text.TextUtils;
import android.view.LayoutInflater;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.toolbox.ImageLoader;
import com.android.volley.toolbox.Volley;
import com.onesignal.OneSignal;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.Locale;

import in.public_static.carin.paniar.utils.LruBitmapCache;
import in.public_static.carin.paniar.utils.OkHttp3Stack;
import uk.co.chrisjenx.calligraphy.CalligraphyConfig;

public class App extends MultiDexApplication {
    public static final String PACKAGE_NAME = "ir.panyar.carin";
    /**
     * package name ine
     **/
    public static final String DIR_INTERNAL = Environment.getDataDirectory().getAbsolutePath() + "/data/" + PACKAGE_NAME;
    public static final String DIR_FLAGS = DIR_INTERNAL + "/Flags/";
    public static final String DIR_SDCARD = Environment.getExternalStorageDirectory().getAbsolutePath();
    public static final String DIR_SQL = DIR_INTERNAL + "/sql/";
    public static final String DIR_TEMP = DIR_INTERNAL + "/temp/";
    public static final String LOG_TAG = "mypaniar";
    public static final String webServiceUrl = "http://app.panyar.ir/api/";
    public static Context context;
    public static LayoutInflater inflater;
    public static SharedPreferences preferences;
    public static TelephonyManager telephonyManager;
    public static SQLiteDatabase bank;
    private static App mInstance;
    private ImageLoader mImageLoader;
    private RequestQueue mRequestQueue;

    public static synchronized App getInstance() {
        App app;
        synchronized (App.class) {
            app = mInstance;
        }
        return app;
    }

    public static boolean IsInternetConnected() {
        ConnectivityManager connectivityManager = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo networkInfo = connectivityManager.getActiveNetworkInfo();
        if (networkInfo != null) {
            if (networkInfo.isConnected()) {
                return true;
            }
        }
        return false;
    }

    @Override
    protected void attachBaseContext(Context context) {
        super.attachBaseContext(context);
        MultiDex.install(this);
    }

    @Override
    public void onCreate() {
        super.onCreate();
        OneSignal.startInit(this)
                .inFocusDisplaying(OneSignal.OSInFocusDisplayOption.Notification)
                .unsubscribeWhenNotificationsAreDisabled(true)
                .init();

        // Call syncHashedEmail anywhere in your app if you have the user's email.
        // This improves the effectiveness of OneSignal's "best-time" notification scheduling feature.
        // OneSignal.syncHashedEmail(userEmail);

        setLanguage("en");
        context = getApplicationContext();
        inflater = (LayoutInflater) getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        telephonyManager = (TelephonyManager) getSystemService(Context.TELEPHONY_SERVICE);
        new File(DIR_INTERNAL).mkdirs();
        new File(DIR_FLAGS).mkdirs();
        new File(DIR_SDCARD).mkdirs();
        new File(DIR_SQL).mkdirs();
        new File(DIR_TEMP).mkdirs();

        try {
            openOrCopyDataBase();
        } catch (IOException e) {
            e.printStackTrace();
        }

        mInstance = this;
        preferences = PreferenceManager.getDefaultSharedPreferences(this);
        CalligraphyConfig.initDefault(new CalligraphyConfig.Builder()
                .setDefaultFontPath("vazir.ttf")
                .setFontAttrId(R.attr.fontPath)
                .build()
        );
//        ConfigDerawerLoader();
    }

    public RequestQueue getRequestQueue() {
        if (this.mRequestQueue == null) {
            this.mRequestQueue = Volley.newRequestQueue(getApplicationContext(), new OkHttp3Stack());
        }
        return this.mRequestQueue;
    }

    public ImageLoader getImageLoader() {
        getRequestQueue();
        if (this.mImageLoader == null) {
            this.mImageLoader = new ImageLoader(this.mRequestQueue, new LruBitmapCache());
        }
        return this.mImageLoader;
    }

    public <T> void addToRequestQueue(Request<T> req, String tag) {
        if (TextUtils.isEmpty(tag)) {
            tag = LOG_TAG;
        }
        req.setTag(tag);
        getRequestQueue().add(req);
    }

    public <T> void addToRequestQueue(Request<T> req) {
        req.setTag(LOG_TAG);
        getRequestQueue().add(req);
    }

    public void cancelPendingRequests(Object tag) {
        if (this.mRequestQueue != null) {
            this.mRequestQueue.cancelAll(tag);
        }
    }

    private void setLanguage(String language) {
        Locale locale = new Locale(language);
        Locale.setDefault(locale);
        Configuration config = new Configuration();
        config.locale = locale;
        getBaseContext().getResources().updateConfiguration(config,
                getBaseContext().getResources().getDisplayMetrics());
    }

    void openOrCopyDataBase() throws IOException {

        String outFileName = DIR_SQL + "city.db";
        if (new File(outFileName).exists()) {
            bank = SQLiteDatabase.openOrCreateDatabase(App.DIR_SQL + "city.db", null);
            return;
        }
        OutputStream myOutput = new FileOutputStream(outFileName);
        byte[] buffer = new byte[1024];
        int length;
        InputStream myInput = context.getAssets().open("city.db");
        while ((length = myInput.read(buffer)) > 0) {
            myOutput.write(buffer, 0, length);
        }
        myInput.close();
        myOutput.flush();
        myOutput.close();
        bank = SQLiteDatabase.openOrCreateDatabase(App.DIR_SQL + "city.db", null);
    }
}

package ke.co.debechlabs.besure.SharedPreference;

import android.content.Context;
import android.content.SharedPreferences;

/**
 * Created by chriz on 6/7/2017.
 */

public class Manager {
    public static final String LOCATION_PREF_NAME = "location";
    public static final String KEY_COUNTY_NAME = "county_name";

    private static Context context;
    private static Manager mInstance;

    public Manager(Context ctx){
        this.context = ctx;
    }

    public static synchronized Manager getInstance(Context context){
        if (mInstance == null){
            mInstance = new Manager(context);
        }

        return mInstance;
    }

    public boolean storeCountyName(String county_name){
            SharedPreferences sharedPreferences = this.context.getSharedPreferences(LOCATION_PREF_NAME, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();

        editor.putString(KEY_COUNTY_NAME, county_name);
        editor.apply();
        return true;
    }

    public String getCountyName(){
        SharedPreferences sharedPreferences = this.context.getSharedPreferences(LOCATION_PREF_NAME, Context.MODE_PRIVATE);

        return sharedPreferences.getString(KEY_COUNTY_NAME, null);
    }
}

package ke.co.debechlabs.besure.app;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Color;

/**
 * Created by chriz on 6/7/2017.
 */

public class Config {
    public static final String SERVER_URL = "http://besure.co.ke/";
    public static final String BASE_URL = SERVER_URL + "API/";
    public static final String ASSETS_URL = SERVER_URL + "assets/";

    public static final String SERVER_URL_LOCAL = "https://52d5af3e.ngrok.io/";
    public static final String BASE_URL_LOCAL = SERVER_URL_LOCAL + "API/";
    public static final String ASSETS_URL_LOCAL = SERVER_URL_LOCAL + "assets/";

    public static final String YOUTUBE_VIDEO_TEMPLATE = "https://www.youtube.com/watch?v=[video_id]";
    public static final String YOUTUBE_THUMB_TEMPLATE = "https://img.youtube.com/vi/[video_id]/hqdefault.jpg";
    Context context;
    public Config(Context ctx){
        this.context = ctx;
    }

    public static String getYoutubeThumb(String video_id){
        return YOUTUBE_THUMB_TEMPLATE.replace("[video_id]", video_id);
    }

    public static String getYoutubeVideoURL(String video_id){
        return YOUTUBE_VIDEO_TEMPLATE.replace("[video_id]", video_id);
    }

    public int getRandomMaterialColor(String typeColor){
        int returnColor = Color.GRAY;
        int arrayId = context.getResources().getIdentifier("mdcolor_" + typeColor, "array", context.getPackageName());

        if (arrayId != 0) {
            TypedArray colors = context.getResources().obtainTypedArray(arrayId);
            int index = (int) (Math.random() * colors.length());
            returnColor = colors.getColor(index, Color.GRAY);
            colors.recycle();
        }
        return returnColor;
    }
}

package ke.co.debechlabs.besure.network;

import ke.co.debechlabs.besure.app.Config;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Created by chriz on 6/9/2017.
 */

public class ApiClient {
    private static Retrofit retrofit = null;

    public static Retrofit getClient(){
        if (retrofit == null) {
            retrofit = new Retrofit.Builder()
                    .baseUrl(Config.BASE_URL_LOCAL)
                    .addConverterFactory(GsonConverterFactory.create())
                    .build();
        }

        return retrofit;
    }
}

package ke.co.debechlabs.besure.network;

import java.util.List;

import ke.co.debechlabs.besure.models.Audios;
import ke.co.debechlabs.besure.models.Sites;
import ke.co.debechlabs.besure.models.Videos;
import retrofit2.Call;
import retrofit2.http.GET;

/**
 * Created by chriz on 6/9/2017.
 */

public interface ApiInterface {
    @GET("getResources/videos")
    Call<List<Videos>> getVideos();

    @GET("getResources/audio")
    Call<List<Audios>> getAudios();

    @GET("getResources/sites")
    Call<List<Sites>> getSites();
}

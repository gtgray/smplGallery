package tk.atna.smplgallery.network;

import retrofit.http.GET;
import retrofit.http.Query;
import tk.atna.smplgallery.model.Inbound;

interface ServerApi {

    String SERVER_URL = "https://api.500px.com";

    String PHOTOS_ENDPOINT = "/v1/photos";

    String CONSUMER_KEY = "consumer_key";
    String FEATURE = "feature";
    String PAGE = "page";
    String RPP = "rpp"; // default by 500px api is 20
    String IMAGE_SIZE = "image_size";

    // features
    String POPULAR = "popular";
    String FRESH_TODAY = "fresh_today";

    // rpp
    int DEFAULT_RPP = 50;

    // size
    int DEFAULT_SIZE = 600;


    @GET(PHOTOS_ENDPOINT)
    Inbound.Feed getPhotos(
            @Query(CONSUMER_KEY) String key,
            @Query(FEATURE) String feature,
            @Query(PAGE) int page,
            @Query(RPP) int rpp,
            @Query(IMAGE_SIZE) int size
    ) throws HttpHelper.ServerException;

}

package tk.atna.smplgallery.network;


import android.content.Context;
import android.widget.ImageView;

import com.squareup.picasso.Picasso;

import java.io.IOException;

import retrofit.ErrorHandler;
import retrofit.RestAdapter;
import retrofit.RetrofitError;
import retrofit.client.Response;
import tk.atna.smplgallery.R;
import tk.atna.smplgallery.model.GalleryType;
import tk.atna.smplgallery.model.Inbound;

public class HttpHelper {

    private static final int DEFAULT_PICTURE = R.mipmap.ic_launcher;

    private final Context context;
    private String consumerKey;

    private ServerApi api;


    public HttpHelper(Context context) {
        this.context = context;
        this.consumerKey = context.getString(R.string.consumer_key);
        // setup retrofit
        RestAdapter ra = new RestAdapter.Builder()
                .setEndpoint(ServerApi.SERVER_URL)
                .setLogLevel(RestAdapter.LogLevel.BASIC)
//                .setLogLevel(RestAdapter.LogLevel.FULL)
                .setErrorHandler(new ServerErrorHandler())
                .build();
        // retrofit server api inflater
        api = ra.create(ServerApi.class);
    }

    public Inbound.Feed getPhotos(GalleryType type, int count) throws ServerException {
        int page = count <= 0 ? 0 : count / ServerApi.DEFAULT_RPP + 1;

        return api.getPhotos(consumerKey, getFeature(type), page,
                ServerApi.DEFAULT_RPP, ServerApi.DEFAULT_SIZE);
    }

    public void loadImage(String url, ImageView view) {
        Picasso.with(context)
                .load(url)
                .placeholder(DEFAULT_PICTURE)
                .error(DEFAULT_PICTURE)
                .into(view);
    }

    private String getFeature(GalleryType type) {
        switch (type) {
            default:
            case POPULAR:
                return ServerApi.POPULAR;

            case DAILY:
                return ServerApi.FRESH_TODAY;
        }
    }


    private static class ServerErrorHandler implements ErrorHandler {

        @Override
        public Throwable handleError(RetrofitError cause) {
            Response response = cause.getResponse();
            if(response != null) {
                switch (response.getStatus()) {
                    case HttpCodes.SERVICE_UNAVAILABLE:
                        break;

                    case HttpCodes.UNAUTHORIZED:
                        break;

                    case HttpCodes.INTERNAL_SERVER_ERROR:
                        break;

                    case HttpCodes.NOT_FOUND:
                        break;

                    case HttpCodes.FORBIDDEN:
                        break;

                    case HttpCodes.BAD_REQUEST:
                        break;
                }
                return new ServerException(cause);
            }
//            return cause;
            return new ServerException(cause);
        }
    }


    public static class ServerException extends IOException {

        private RetrofitError cause;


        public ServerException(String message) {
            super(message);
        }

        public ServerException(RetrofitError cause) {
            this.cause = cause;
        }

        public RetrofitError getCause() {
            return cause;
        }

    }


    interface HttpCodes {

        int BAD_REQUEST = 400;
        int UNAUTHORIZED = 401;
        int FORBIDDEN = 403;
        int NOT_FOUND = 404;
        int INTERNAL_SERVER_ERROR = 500;
        int SERVICE_UNAVAILABLE = 503;
    }

}


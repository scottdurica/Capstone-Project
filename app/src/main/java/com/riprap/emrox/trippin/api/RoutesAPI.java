package com.riprap.emrox.trippin.api;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

/**
 * Created by scott on 2/17/2017.
 */

public interface RoutesAPI {
        /*
        public void getMovies(@Path("sort_order") String sortOrder,@Query("api_key") String api_key, Callback<ArrayList<Movie>> response);
        Example:
        http://realtime.mbta.com/developer/api/v2/routes?api_key=wX9NwuHnZU2ToO7GmGR9uw&format=json    */

    final String ROUTES_ENDPOINT_BASE_URL = "http://realtime.mbta.com/developer/api/v2/";
    @GET("routes/")
    Call<RoutesResponse> getRoutes( @Query("api_key") String api_key);
}

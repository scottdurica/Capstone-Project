package com.riprap.emrox.trippin.api;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

/**
 * Created by scott on 2/22/2017.
 */

public interface StopsByRouteAPI {

    /**

     Example:
      http://realtime.mbta.com/developer/api/v2/stopsbyroute?api_key=wX9NwuHnZU2ToO7GmGR9uw&route=Red&format=json
     **/

    final String STOPS_BY_ROUTE_ENDPOINT_BASE_URL = "http://realtime.mbta.com/developer/api/v2/";
    @GET("stopsbyroute/")
    Call<StopsByRouteResponse> getStopsByRoute(@Query("api_key") String apr_key, @Query("route") String route);
}

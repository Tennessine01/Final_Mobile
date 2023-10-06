package vn.edu.usth.mylogin.SubjectBook;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;
import retrofit2.http.Query;

public interface SearchApi {
    @GET("/search.json")
    Call<Search> getResultObject(@Query("q") String q, @Query("limit") int limit);
}

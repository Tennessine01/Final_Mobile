package vn.edu.usth.mylogin.SearchBook;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;

public interface SearchBookApi {
    @GET("/works/{key}.json")
    Call<Book> getBookObject(@Path("key") String key);

    @GET("/works/{key}/ratings.json")
    Call<Rate> getRate(@Path("key") String key);
}

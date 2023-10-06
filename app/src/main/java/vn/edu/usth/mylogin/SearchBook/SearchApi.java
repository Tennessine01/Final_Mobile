package vn.edu.usth.mylogin.SearchBook;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;
import retrofit2.http.Query;
import vn.edu.usth.mylogin.SearchBook.Subject;

public interface SearchApi {
        @GET("/subjects/{subject}.json")
        Call<Subject> getSubjects(@Path("inputText") String inputText, @Query("limit") int limit);
    }

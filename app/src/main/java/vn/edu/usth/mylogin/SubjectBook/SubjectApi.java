package vn.edu.usth.mylogin.SubjectBook;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;
import retrofit2.http.Path;

public interface SubjectApi {
    @GET("/subjects/{subject}.json")
    Call<Subject> getLoveSubjects(@Path("subject") String subject, @Query("limit") int limit);
}


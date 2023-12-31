package vn.edu.usth.mylogin.Fragment;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;

import com.bumptech.glide.Glide;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import vn.edu.usth.mylogin.Adapter.BookListAdapter;
import vn.edu.usth.mylogin.Domain.BookDomain;
import vn.edu.usth.mylogin.Helper.ManagementMyLibrary;
import vn.edu.usth.mylogin.R;
import vn.edu.usth.mylogin.SubjectBook.Book;
import vn.edu.usth.mylogin.SubjectBook.Rate;
import vn.edu.usth.mylogin.SubjectBook.Subject;
import vn.edu.usth.mylogin.SubjectBook.SubjectApi;
import vn.edu.usth.mylogin.SubjectBook.BookApi;

public class DetailActivity extends AppCompatActivity {
    private static final String BASE_URL = "https://openlibrary.org";
    private Button addToLibraryBtn;
    private TextView titleTxt, descriptionTxt, authorTxt, ratingTxt, timeTxt;
    private ImageView picBook;

    private Button readBookBtn;
    private BookDomain object;
    private ManagementMyLibrary managementMyLibrary;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.fragment_detail_book);

        managementMyLibrary = new ManagementMyLibrary(DetailActivity.this);

        initView();
        getBundle();
    }

    private void getBundle() {
        object = (BookDomain) getIntent().getSerializableExtra("object");

        int drawableResourceId = this.getResources().getIdentifier("default_book", "drawable", this.getPackageName());
        Glide.with(this)
                .load(object.getPicUrl()).error(drawableResourceId)
                .into(picBook);


        String BASE_URL = "https://openlibrary.org";
        Retrofit retrofit = new Retrofit.Builder().baseUrl(BASE_URL).addConverterFactory(GsonConverterFactory.create()).build();

        BookApi bookApi = retrofit.create(BookApi.class);
        Call<Book> call = bookApi.getBookObject(object.getDescription().substring(7));

        titleTxt.setText(object.getTitle());
        authorTxt.setText(object.getAuthor());


        addToLibraryBtn.setText("Add to library");
        addToLibraryBtn.setOnClickListener(v -> {
            managementMyLibrary.insertBook(object);
        });

        readBookBtn.setText("Read Book");
        readBookBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                setContentView(R.layout.fragment_readbook);
                getData();
            }
        });

        call.enqueue(new Callback<Book>() {

            @Override
            public void onResponse(Call<Book> call, Response<Book> response) {
                if (response.isSuccessful()) {
                    Book book = response.body();

                    object.setDescription(book.getDescription());
                    descriptionTxt.setText(book.getDescription());

                    Log.d("aaaaaaaaaaaaaaa", "onResponse: " + book.getDescription());
                    timeTxt.setText(book.getCreated().getValue().substring(0,4));
                } else {
                    // Xử lý lỗi ở đây
                }
            }

            @Override
            public void onFailure(Call<Book> call, Throwable t) {
                // Xử lý lỗi kết nối ở đây
                descriptionTxt.setText("None");// cho 1 decristion mac dinh vao day
                timeTxt.setText(object.getTime() + " ");
            }
        });

        Call<Rate> callRate = bookApi.getRate(object.getDescription().substring(7));
        callRate.enqueue(new Callback<Rate>() {
            @Override
            public void onResponse(Call<Rate> call, Response<Rate> response) {
                if (response.isSuccessful()) {
                    Rate rate = response.body();
                    ratingTxt.setText((rate.getSummary().getAverage() + " ").substring(0,3));
                } else {
                    // Xử lý lỗi ở đây
                }
            }

            @Override
            public void onFailure(Call<Rate> call, Throwable t) {
                ratingTxt.setText(0);
            }
        });
    }

    private void initView() {
        addToLibraryBtn = findViewById(R.id.addToLibraryBtn);
        timeTxt = findViewById(R.id.timeTxt);
        titleTxt=findViewById(R.id.titleTxt);
        descriptionTxt = findViewById(R.id.descriptionTxt);
        picBook = findViewById(R.id.bookPic1);
        authorTxt = findViewById(R.id.AuthorTxt);
        ratingTxt = findViewById(R.id.ratingTxt);
        readBookBtn = findViewById(R.id.readbook_btn);
    }

    private void getData() {
        Retrofit retrofit = new Retrofit.Builder().baseUrl(BASE_URL).addConverterFactory(GsonConverterFactory.create()).build();
        BookApi bookApi = retrofit.create(BookApi.class);
        Call<Rate> callRate = bookApi.getRate(object.getDescription().substring(7));
        callRate.enqueue(new Callback<Rate>() {
            @Override
            public void onResponse(Call<Rate> call, Response<Rate> response) {
                if (response.isSuccessful()) {
                    Rate rate = response.body();
                    ratingTxt.setText((rate.getSummary().getAverage() + " ").substring(0,3));
                } else {
                    // Xử lý lỗi ở đây
                }
            }

            @Override
            public void onFailure(Call<Rate> call, Throwable t) {
                ratingTxt.setText(0);
            }
        });
    }
}
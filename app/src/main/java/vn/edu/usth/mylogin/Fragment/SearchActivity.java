package vn.edu.usth.mylogin.Fragment;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.bumptech.glide.Glide;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import vn.edu.usth.mylogin.Domain.BookDomain;
import vn.edu.usth.mylogin.Helper.ManagementMyLibrary;
import vn.edu.usth.mylogin.R;
import vn.edu.usth.mylogin.SearchBook.Book;
import vn.edu.usth.mylogin.SearchBook.Rate;
import vn.edu.usth.mylogin.SearchBook.SearchApi;
import vn.edu.usth.mylogin.SearchBook.SearchBookApi;

public class SearchActivity extends AppCompatActivity {
    private static final String BASE_URL = "https://openlibrary.org";
    private Button addToLibraryBtn;
    private TextView plusBtn, minusBtn, titleTxt, feeTxt, descriptionTxt, numberOrderTxt, authorTxt, ratingTxt, timeTxt;
    private ImageView picBook;

    private Button readBookBtn;
    private BookDomain object;
    private int numberOrder = 1;
    private ManagementMyLibrary managementMyLibrary;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.fragment_detail_search);

        managementMyLibrary = new ManagementMyLibrary(SearchActivity.this);

        initView();
        getBundle();
    }

    private void getBundle() {
        object = (BookDomain) getIntent().getSerializableExtra("object");

        int drawableResourceId = this.getResources().getIdentifier("book5", "drawable", this.getPackageName());
        Glide.with(this)
                .load(object.getPicUrl()).error(drawableResourceId)
                .into(picBook);


        String BASE_URL = "https://openlibrary.org";
        Retrofit retrofit = new Retrofit.Builder().baseUrl(BASE_URL).addConverterFactory(GsonConverterFactory.create()).build();

        SearchBookApi searchApi = retrofit.create(SearchBookApi.class);
        Call<Book> call = searchApi.getBookObject(object.getDescription().substring(7));
        titleTxt.setText(object.getTitle());
        authorTxt.setText(object.getAuthor() + " " );
        addToLibraryBtn.setText("Add to library");
        addToLibraryBtn.setOnClickListener(v -> {
            managementMyLibrary.insertBook(object);
        });

        readBookBtn.setText("Read Book");
        readBookBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {



//---------------------------------FragmentReadBook ở đây                ////---------------------------------------------------
                setContentView(R.layout.fragment_readbook);
                getData();
            }
        });

        call.enqueue(new Callback<Book>() {
            @Override
            public void onResponse(Call<Book> call, Response<Book> response) {
                if (response.isSuccessful()) {
                    Book book = response.body();
                    descriptionTxt.setText(book.getDescription());
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

        Call<Rate> callRate = searchApi.getRate(object.getDescription().substring(7));
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
        picBook = findViewById(R.id.bookPic);
        authorTxt = findViewById(R.id.AuthorTxt);
        ratingTxt = findViewById(R.id.ratingTxt);
        readBookBtn = findViewById(R.id.readbook_btn);
    }

    private void getData() {
        Retrofit retrofit = new Retrofit.Builder().baseUrl(BASE_URL).addConverterFactory(GsonConverterFactory.create()).build();
        SearchBookApi bookApi = retrofit.create(SearchBookApi.class);
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
package vn.edu.usth.mylogin.Fragment;


import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.bumptech.glide.Glide;

import retrofit2.Call;
import vn.edu.usth.mylogin.Domain.BookDomain;
import vn.edu.usth.mylogin.Helper.ManagementMyLibrary;
import vn.edu.usth.mylogin.R;
import vn.edu.usth.mylogin.SubjectBook.Book;
import vn.edu.usth.mylogin.SubjectBook.BookApi;

public class DetailActivityBook extends AppCompatActivity {
    //private Button addToCartBtn;
    private TextView titleTxt, descriptionTxt, authorTxt, ratingTxt, timeTxt;
    private ImageView picBook;
    private BookDomain object;
    private int numberOrder = 1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.fragment_detail_book_m_l);
        initView();
        getBundle();
    }



    private void getBundle() {
        object = (BookDomain) getIntent().getSerializableExtra("object");

        int drawableResourceId = this.getResources().getIdentifier("book5", "drawable", this.getPackageName());
        Glide.with(this)
                .load(object.getPicUrl()).error(drawableResourceId)
                .into(picBook);


        titleTxt.setText(object.getTitle());
        descriptionTxt.setText(object.getDescription());
        ratingTxt.setText(object.getRating() + " ");
        authorTxt.setText(object.getAuthor() + "");
        timeTxt.setText(object.getTime() + " ");
    }

    private void initView() {
        timeTxt = findViewById(R.id.timeTxt);
        titleTxt=findViewById(R.id.titleTxt);
        descriptionTxt = findViewById(R.id.descriptionTxt2);
        picBook = findViewById(R.id.bookPic2);
        authorTxt = findViewById(R.id.AuthorTxt);
        ratingTxt = findViewById(R.id.ratingTxt);
    }
}

package vn.edu.usth.mylogin.Fragment;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.widget.TextView;

import org.w3c.dom.Text;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import vn.edu.usth.mylogin.Adapter.BookListAdapter;
import vn.edu.usth.mylogin.Adapter.HorizontalAdapter;
import vn.edu.usth.mylogin.Adapter.TopicBookAdapter;
import vn.edu.usth.mylogin.Domain.BookDomain;
import vn.edu.usth.mylogin.R;
import vn.edu.usth.mylogin.SubjectBook.Subject;
import vn.edu.usth.mylogin.SubjectBook.SubjectApi;

public class Topic extends AppCompatActivity implements HorizontalAdapter.OnItemClickListener {
    private RecyclerView.Adapter topicBookAdapter;
    private String[] subTopic;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_topic);
        init();
    }

    private void init() {
        Intent intent = getIntent();
        String topic = intent.getStringExtra("topic");
        subTopic = intent.getStringExtra("subtopic").split(",");
        TextView tv = findViewById(R.id.title_topic);
        tv.setText(topic);
        setSubTopic(findViewById(R.id.sub_topic), subTopic);
        setBooks(subTopic[0], findViewById(R.id.topic_books));
    }
    private void setSubTopic(RecyclerView rv, String[] subTopic) {
        rv.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false));
        HorizontalAdapter adapter = new HorizontalAdapter(new ArrayList<>(Arrays.asList(subTopic)));
        adapter.setOnItemClickListener(this);
        rv.setAdapter(adapter);
    }

//    private void setBooks(RecyclerView rv, String[] subTopic) {
//        rv.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false));
//        HorizontalAdapter adapter = new HorizontalAdapter(new ArrayList<>(Arrays.asList(subTopic)));
//        rv.setAdapter(adapter);
//    }


    private void setBooks(String subject, RecyclerView rc) {
        ArrayList<BookDomain> items = new ArrayList<>();

        rc.setLayoutManager(new LinearLayoutManager(this,LinearLayoutManager.HORIZONTAL,false));

        String BASE_URL = "https://openlibrary.org";
        Retrofit retrofit = new Retrofit.Builder().baseUrl(BASE_URL).addConverterFactory(GsonConverterFactory.create()).build();

        SubjectApi subjectApi = retrofit.create(SubjectApi.class);
        int limit = 5;
        Call<Subject> call = subjectApi.getSubjects(subject, limit);
        call.enqueue(new Callback<Subject>() {
            @Override
            public void onResponse(Call<Subject> call, Response<Subject> response) {
                if (response.isSuccessful()) {
                    Subject subject = response.body();
                    List<String> listTitle = new ArrayList<String>();
                    List<String> authors = new ArrayList<String>();
                    List<Integer> listCoverId = new ArrayList<Integer>();
                    List<String> listKey = new ArrayList<String>();
                    subject.getWorks().forEach(workItem -> {
                                listTitle.add(workItem.getTitle());
                                authors.add(workItem.getAuthors().get(0).getName());
                                listCoverId.add(workItem.getCover_id());
                                listKey.add(workItem.getKey());
                            }
                    );

                    topicBookAdapter = new TopicBookAdapter(items);
                    rc.setAdapter(topicBookAdapter);

                    for (int i = 0; i < limit; i++) {
                        items.add(new BookDomain(listTitle.get(i),
                                listKey.get(i),
                                "Content",
                                "https://covers.openlibrary.org/b/id/" + listCoverId.get(i) + "-L.jpg",
                                2015,4,authors.get(i)));

                        topicBookAdapter.notifyDataSetChanged();
                        topicBookAdapter.notifyItemInserted(items.size() - 1);
                    }
                } else {
                    // Xử lý lỗi ở đây
                }
            }

            @Override
            public void onFailure(Call<Subject> call, Throwable t) {
                // Xử lý lỗi kết nối ở đây
            }
        });
    }

    @Override
    public void onItemClick(int position) {
        setBooks(subTopic[position], findViewById(R.id.topic_books));
    }
}

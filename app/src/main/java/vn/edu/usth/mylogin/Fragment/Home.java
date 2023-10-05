package vn.edu.usth.mylogin.Fragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;


import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

import vn.edu.usth.mylogin.Adapter.BookListAdapter;
import vn.edu.usth.mylogin.Domain.BookDomain;
import vn.edu.usth.mylogin.R;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import vn.edu.usth.mylogin.SubjectBook.Author;
import vn.edu.usth.mylogin.SubjectBook.Subject;
import vn.edu.usth.mylogin.SubjectBook.SubjectApi;

public class Home extends Fragment {
    private RecyclerView.Adapter adapterBookList;
    private String[] subjects = {"love", "juvenile_literature", "dance"};

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_home, container, false);
        RecyclerView[] rc = {view.findViewById(R.id.HomeView1),view.findViewById(R.id.HomeView2),view.findViewById(R.id.HomeView3)};
        for (int i = 0; i < subjects.length; i++) {
            initRecyclerview(subjects[i], rc[i]);
        }
        return view;
    }

    @Override
    public void onResume() {
        super.onResume();
    }

    private void initRecyclerview(String subject, RecyclerView rc) {
        //tạo danh sách rỗng để chứa các đối tượng bookdomain
        ArrayList<BookDomain> items = new ArrayList<>();

        //thiết lập recycler view để hiển thị danh sách theo chiều ngang
        rc.setLayoutManager(new LinearLayoutManager(getContext(),LinearLayoutManager.HORIZONTAL,false));
        String BASE_URL = "https://openlibrary.org";

        //retrofit là thư viên dùng để tương tác với api
        // đối tượng retrofit được tạo ra vưới địa chỉ url và dùng gson chuyển json thành object java
        Retrofit retrofit = new Retrofit.Builder().baseUrl(BASE_URL).addConverterFactory(GsonConverterFactory.create()).build();

        SubjectApi subjectApi = retrofit.create(SubjectApi.class);
        int limit = 10;

        //mở cuộc gọi API có limit là lấy 10 api trong 1 lần
        Call<Subject> call = subjectApi.getLoveSubjects(subject, limit);
        call.enqueue(new Callback<Subject>() {
            @Override
            public void onResponse(Call<Subject> call, Response<Subject> response) {
                //nếu gọi API thành công
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

                    adapterBookList= new BookListAdapter(items);
                    rc.setAdapter(adapterBookList);

                    for (int i = 0; i < limit; i++) {
                        items.add(new BookDomain(listTitle.get(i),
                                listKey.get(i),
                                "Content",
                                "https://covers.openlibrary.org/b/id/" + listCoverId.get(i) + "-L.jpg",
                                2015,4,authors.get(i)));

                        adapterBookList.notifyDataSetChanged();
                        adapterBookList.notifyItemInserted(items.size() - 1);
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
}
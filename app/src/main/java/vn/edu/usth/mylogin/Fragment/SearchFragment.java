package vn.edu.usth.mylogin.Fragment;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;

import androidx.fragment.app.Fragment;

import vn.edu.usth.mylogin.R;

public class SearchFragment extends Fragment {

    private static final String BASE_URL = "https://openlibrary.org";

    private String[] topics = {
            "arts",
            "animals",
            "fiction",
            "science_&_mathematics",
            "history",
             "children\'s",
    };

    private String[][] subTopics = {
            {"architecture",
                    "art_art_instruction",
                    "history_of_art__art__design_styles",
                    "dance",
                    "design",
                    "fashion",
                    "film",
                    "graphic_design",
                    "music",
                    "music_theory",
                    "painting__paintings",
                    "photography"},

            {       "bears",
                    "cats",
                    "kittens",
                    "dogs",
                    "puppies"},

            {"fantasy",
                    "historical_fiction",
                    "horror",
                    "humor",
                    "literature",
                    "magic",
                    "mystery_and_detective_stories",
                    "plays",
                    "poetry",
                    "romance",
                    "science_fiction",
                    "short_stories",
                    "thriller",
                    "young_adult_fiction",},
            {"biology",
                    "chemistry",
                    "mathematics",
                    "physics",
                    "programming",},
            {"ancient_civilization"},
            {}
    };

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_search, container, false);
        init(view);
        searchHandle(view);
        return view;
    }

    private void init(View view) {
        ImageView[] ivs = {
                view.findViewById(R.id.art),
                view.findViewById(R.id.animals),
                view.findViewById(R.id.fiction),
                view.findViewById(R.id.science),
                view.findViewById(R.id.history),
                view.findViewById(R.id.children)
        };

        for (int i = 0; i < topics.length; i++) {
            initHandle(ivs[i], topics[i], subTopics[i]);
        }
    }
    private void initHandle(ImageView iv, String topic, String[] sub) {
        iv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(getActivity(), Topic.class);
                intent.putExtra("topic", topic);
                intent.putExtra("subtopic", TextUtils.join(",", sub));
                startActivity(intent);
            }
        });
    }

    private void searchHandle(View view) {
        ImageButton but = view.findViewById(R.id.searchButton);
        EditText editText = view.findViewById(R.id.editTextSearch);
        but.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String q = String.valueOf(editText.getText());
                if (q.length() > 0) {
                    Intent intent=new Intent(getActivity(), SearchActivity.class);
                    intent.putExtra("q", q);
                    startActivity(intent);
                } else {
                    System.out.println(0);
                }
            }
        });
    }
}
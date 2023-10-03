package vn.edu.usth.mylogin.Adapter;


import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.resource.bitmap.GranularRoundedCorners;

import java.util.ArrayList;

import vn.edu.usth.mylogin.Domain.BookDomain;
import vn.edu.usth.mylogin.Fragment.DetailActivity;
import vn.edu.usth.mylogin.R;

public class BookListAdapter extends RecyclerView.Adapter<BookListAdapter.ViewHolder> {
    ArrayList<BookDomain> items;
    Context context;

    public BookListAdapter() {
        this.items = new ArrayList<>();
    }
    public BookListAdapter(ArrayList<BookDomain> items) {
        this.items = items;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View inflate = LayoutInflater.from(parent.getContext()).inflate(R.layout.viewholder_book_list, parent, false);
        context = parent.getContext();
        return new ViewHolder(inflate);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        holder.titleTxt.setText(items.get(position).getTitle());
        holder.timeTxt.setText(items.get(position).getTime() + " ");
        holder.AuthorTxt.setText("" + items.get(position).getAuthor());

        int drawableResourceId = holder.itemView.getResources().getIdentifier("book5", "drawable", holder.itemView.getContext().getPackageName());

        Glide.with(holder.itemView.getContext())
                .load(items.get(position).getPicUrl())
                .transform(new GranularRoundedCorners(30, 30, 0, 0))
                .error(drawableResourceId)
                .into(holder.pic);

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(holder.itemView.getContext(), DetailActivity.class);
                intent.putExtra("object",items.get(position));
                holder.itemView.getContext().startActivity(intent);
            }
        });
    }

    @Override
    public int getItemCount() {
        return items.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        TextView titleTxt, timeTxt, AuthorTxt;
        ImageView pic;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            titleTxt = itemView.findViewById(R.id.titleTxt);
            timeTxt = itemView.findViewById(R.id.timeTxt);
            AuthorTxt = itemView.findViewById(R.id.authorTxt);
            pic = itemView.findViewById(R.id.pic);

        }
    }
}

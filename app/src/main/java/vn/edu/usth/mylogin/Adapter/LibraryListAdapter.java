package vn.edu.usth.mylogin.Adapter;

import android.annotation.SuppressLint;
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
import vn.edu.usth.mylogin.Fragment.DetailActivityBook;
import vn.edu.usth.mylogin.Helper.ChangeNumberItemsListener;
import vn.edu.usth.mylogin.Helper.ManagementMyLibrary;
import vn.edu.usth.mylogin.Helper.RemoveBookListener;
import vn.edu.usth.mylogin.R;

public class LibraryListAdapter extends RecyclerView.Adapter<LibraryListAdapter.ViewHolder> {

    ArrayList<BookDomain> listBookSelected;
    private ManagementMyLibrary managementBook;
    RemoveBookListener changeNumberItemsListener;

    public LibraryListAdapter(ArrayList<BookDomain> listBookSelected, Context context, RemoveBookListener changeNumberItemsListener) {
        this.listBookSelected = listBookSelected;
        managementBook=new ManagementMyLibrary(context);
        this.changeNumberItemsListener = changeNumberItemsListener;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View inflate = LayoutInflater.from(parent.getContext()).inflate(R.layout.viewholder_book_library, parent, false);

        return new ViewHolder(inflate);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, @SuppressLint("RecyclerView") int position) {
        holder.title.setText(listBookSelected.get(position).getTitle());

        int drawableResourceId=holder.itemView.getContext().getResources().getIdentifier("book5","drawable",holder.itemView.getContext().getPackageName());

        Glide.with(holder.itemView.getContext())
                .load(listBookSelected.get(position).getPicUrl())
                .transform(new GranularRoundedCorners(30,30,30,30))
                .error(drawableResourceId)
                .into(holder.pic);

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(holder.itemView.getContext(), DetailActivityBook.class);
                intent.putExtra("object",listBookSelected.get(position));
                holder.itemView.getContext().startActivity(intent);
            }
        });



        holder.minusItem.setOnClickListener(v -> managementBook.minusNumberFood(listBookSelected, position, () -> {
            notifyDataSetChanged();
            changeNumberItemsListener.onClickListener(position);
            notifyDataSetChanged();// cho chac :))
        }));
    }

    @Override
    public int getItemCount() {
        return listBookSelected.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        TextView title, minusItem;
        ImageView pic;
        TextView totalEachItem, num;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            title = itemView.findViewById(R.id.titleTxt);
            pic = itemView.findViewById(R.id.pic);
            minusItem = itemView.findViewById(R.id.minusCartBtn);
            //num = itemView.findViewById(R.id.numberItemTxt);
        }
    }
}


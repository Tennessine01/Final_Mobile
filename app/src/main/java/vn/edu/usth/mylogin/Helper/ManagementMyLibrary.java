package vn.edu.usth.mylogin.Helper;

import android.content.Context;
import android.widget.Toast;

import java.util.ArrayList;

import vn.edu.usth.mylogin.Domain.BookDomain;

public class ManagementMyLibrary {

    private Context context;
    private TinyDB tinyDB;

    public ManagementMyLibrary(Context context) {
        this.context = context;
        this.tinyDB = new TinyDB(context);
    }

    public void insertBook(BookDomain item) {
        ArrayList<BookDomain> listbook = getListBook();
        boolean existAlready = false;
//        int n = 0;
        for (int y = 0; y < listbook.size(); y++) {
            if (listbook.get(y).getTitle().equals(item.getTitle())) {
                existAlready = true;
//                n = y;
                break;
            }
        }
        if (existAlready) {
            Toast.makeText(context, "Already Exist", Toast.LENGTH_SHORT).show();
        } else {
            listbook.add(item);
            Toast.makeText(context, "Added to your Library", Toast.LENGTH_SHORT).show();
        }
        tinyDB.putListObject("Library", listbook);

    }

    public ArrayList<BookDomain> getListBook() {
        return tinyDB.getListObject("Library");
    }

    public void minusNumberFood(ArrayList<BookDomain> listbook, int position, ChangeNumberItemsListener changeNumberItemsListener) {
        listbook.remove(position);
        tinyDB.putListObject("Library", listbook);
        changeNumberItemsListener.changed();
    }

}


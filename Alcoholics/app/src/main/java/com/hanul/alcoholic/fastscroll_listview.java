package com.hanul.alcoholic;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintSet;
import androidx.fragment.app.Fragment;
import androidx.navigation.fragment.DialogFragmentNavigatorDestinationBuilder;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.SectionIndexer;
import android.widget.TextView;
import android.widget.Toast;

import org.w3c.dom.Text;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Locale;
import java.util.Set;

public class fastscroll_listview extends Fragment {
    ArrayList<String> items = new ArrayList<String>();
    Context context;
//    @Override
//    public void onCreate(Bundle savedInstanceState) {
//        super.onCreate(savedInstanceState);
//        setContentView(R.layout.activity_fastscroll_listview);
//        context = this;
//
//        ListView listView = (ListView) findViewById(R.id.fastScrollListView);
//
//        listView.setFastScrollEnabled(true);
//        String[] cocktails = getResources().getStringArray(R.array.ingredients_array);
//
//        List<String> cocktailsList = Arrays.asList(cocktails);
//        ListAdapter listAdapter = new ListAdapter(this ,cocktailsList);
//
//        listView.setAdapter(listAdapter);
//
//        listView.setOnItemClickListener(new AdapterView.OnItemClickListener()
//        {
//            @Override
//            public void onItemClick(AdapterView<?> a_parent, View a_view, int a_position, long a_id)
//            {
//                //final fastscroll_list_item item = (fastscroll_list_item) listAdapter.getItem(a_position);
//                PopupAlert();
//            }
//        });
//    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.activity_fastscroll_listview, container, false);
//        setContentView(R.layout.activity_fastscroll_listview);
//        context = this;

        ListView listView = (ListView) view.findViewById(R.id.fastScrollListView);
        context = container.getContext();

        listView.setFastScrollEnabled(true);
        String[] cocktails = getResources().getStringArray(R.array.ingredients_array);

        List<String> cocktailsList = Arrays.asList(cocktails);
        ListAdapter listAdapter = new ListAdapter(context ,cocktailsList);

        listView.setAdapter(listAdapter);

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener()
        {
            @Override
            public void onItemClick(AdapterView<?> a_parent, View a_view, int a_position, long a_id)
            {
                //final fastscroll_list_item item = (fastscroll_list_item) listAdapter.getItem(a_position);
                PopupAlert();
            }
        });

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent intent=new Intent(getContext(), API_Recipe.class);

                intent.putExtra("Drink", (String) parent.getItemAtPosition(position));
                startActivity(intent);
            }
        });

        return view;
    }

    public void PopupAlert() {
        //super.onBackPressed();

        AlertDialog.Builder oDialog = new AlertDialog.Builder(context);


        oDialog.setMessage("자세한 레시피")
                .setView(R.layout.detailrecipedialog)
                .setNeutralButton("Close", new DialogInterface.OnClickListener()
               {
                   public void onClick(DialogInterface dialog, int which)
                   {
                       //Toast.makeText(getApplicationContext(), "글쓰기 취소됨", Toast.LENGTH_SHORT).show();
                       //finish();
                   }
               })
               .setCancelable(false).show();
    }
}


class ListAdapter extends ArrayAdapter<String> implements SectionIndexer {

    String[] sections;
    List<String> fruits;
    List<String> sectionLetters=new ArrayList<String>();

    public ListAdapter(Context context, List<String> fruitList) {
        super(context, android.R.layout.simple_list_item_1, fruitList);
        this.fruits = fruitList;

        for (int x = 0; x < fruits.size(); x++) {
            String fruit = fruits.get(x);
            String ch = fruit.charAt(0)+"";
            ch = ch.toUpperCase(Locale.US);

            sectionLetters.add(ch);
        }

        ArrayList<String> sectionList = new ArrayList<String>(sectionLetters);

        sections = new String[sectionList.size()];

        sectionList.toArray(sections);
    }

    public int getPositionForSection(int section) {

        Log.e("sushildlh", "" + section);
        return section;
    }

    public int getSectionForPosition(int position) {

        Log.d("sushildlh", "" + position);
        return position;
    }

    public Object[] getSections() {
        return sections;
    }
}

class ListArrayAdapter extends ArrayAdapter<String> implements SectionIndexer {

    String[] sections;
    List<String> fruits;
    List<String> sectionLetters=new ArrayList<String>();

    public ListArrayAdapter(Context context, int layout , List<String> fruitList) {
        super(context, android.R.layout.simple_list_item_1, fruitList);
        this.fruits = fruitList;

        for (int x = 0; x < fruits.size(); x++) {
            String fruit = fruits.get(x);
            String ch = fruit.charAt(0)+"";
            ch = ch.toUpperCase(Locale.US);

            sectionLetters.add(ch);
        }

        ArrayList<String> sectionList = new ArrayList<String>(sectionLetters);

        sections = new String[sectionList.size()];

        sectionList.toArray(sections);
    }

    public int getPositionForSection(int section) {

        Log.e("sushildlh", "" + section);
        return section;
    }

    public int getSectionForPosition(int position) {

        Log.d("sushildlh", "" + position);
        return position;
    }

    public Object[] getSections() {
        return sections;
    }
}

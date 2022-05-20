package com.hanul.alcoholic;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.SectionIndexer;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Locale;
import java.util.Set;

public class fastscroll_listview extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_fastscroll_listview);

        ListView listView = (ListView) findViewById(R.id.fastScrollListVIew);
        listView.setFastScrollEnabled(true);
        String[] fruits = getResources().getStringArray(R.array.fruits_array);

        List<String> fruitList = Arrays.asList(fruits);
        ListAdapter listAdapter = new ListAdapter(this, fruitList);
        listView.setAdapter(listAdapter);



        //ListView button = (Button)findViewById(R.id.button);

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener()
        {
            @Override
            public void onItemClick(AdapterView<?> a_parent, View a_view, int a_position, long a_id)
            {
                //final fastscroll_list_item item = (fastscroll_list_item) listAdapter.getItem(a_position);
                PopupAlert();
            }
        });

    }



    public void PopupAlert() {
        //super.onBackPressed();
        AlertDialog.Builder oDialog = new AlertDialog.Builder(this);
        oDialog.setMessage("자세한 레시피")
                .setView(R.layout.detailrecipedialog)
                //.setPositiveButton("아니오", new DialogInterface.OnClickListener()
                //{
                //    @Override
                //    public void onClick(DialogInterface dialog, int which)
                //    {}
                //})
                .show();

                //.setNeutralButton("예", new DialogInterface.OnClickListener()
               //{
               //    public void onClick(DialogInterface dialog, int which)
               //    {
               //        Toast.makeText(getApplicationContext(), "글쓰기 취소됨", Toast.LENGTH_SHORT).show();
               //        finish();
               //    }
               //})
                //.setCancelable(false).show();

    }
}

class ListAdapter extends ArrayAdapter<String> implements SectionIndexer {

    HashMap<String, Integer> mapIndex;
    String[] sections;
    List<String> fruits;

    public ListAdapter(Context context, List<String> fruitList) {
        super(context, R.layout.activity_fastscroll_list_item, fruitList);

        this.fruits = fruitList;
        mapIndex = new LinkedHashMap<String, Integer>();

        for (int x = 0; x < fruits.size(); x++) {
            String fruit = fruits.get(x);
            String ch = fruit.substring(0, 1);
            ch = ch.toUpperCase(Locale.US);

            // HashMap will prevent duplicates
            mapIndex.put(ch, x);
        }

        Set<String> sectionLetters = mapIndex.keySet();

        // create a list from the set to sort
        ArrayList<String> sectionList = new ArrayList<String>(sectionLetters);

        Log.d("sectionList", sectionList.toString());
        Collections.sort(sectionList);

        sections = new String[sectionList.size()];

        sectionList.toArray(sections);
    }

    public int getPositionForSection(int section) {
        Log.d("section", "" + section);
        return mapIndex.get(sections[section]);
    }

    public int getSectionForPosition(int position) {
        Log.d("position", "" + position);
        return 0;
    }

    public Object[] getSections() {
        return sections;
    }
}
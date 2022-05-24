package com.hanul.alcoholic;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
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
import android.widget.ListView;
import android.widget.SectionIndexer;
import android.widget.TextView;
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
    ArrayList<String> items = new ArrayList<String>();
    TextView textview;
    Context context;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_fastscroll_listview);
        context = this;

        ListView listView = (ListView) findViewById(R.id.fastScrollListView);
        listView.setFastScrollEnabled(true);
        String[] cocktails = getResources().getStringArray(R.array.ingredients_array);

        List<String> cocktailsList = Arrays.asList(cocktails);
        ListAdapter listAdapter = new ListAdapter(this, cocktailsList);

        listView.setAdapter(listAdapter);

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener()
        {
            @Override
            public void onItemClick(AdapterView<?> a_parent, View a_view, int a_position, long a_id)
            {
                Log.d("22222222222222", "ListAdapter: 2222222222222222");

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

    public View getView(int position, View convertView, ViewGroup parent) {
        View listView = convertView;

        View v = convertView;
        if (v == null) {
            LayoutInflater vi = (LayoutInflater) getContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            v = vi.inflate(R.layout.activity_fastscroll_list_item, null);
        }

        // ImageView 인스턴스
        ImageView imageView = (ImageView)v.findViewById(R.id.imageView);

        // 리스트뷰의 아이템에 이미지를 변경한다.
        imageView.setImageResource(R.drawable.cocktailimg32px);


        //TextView textView = (TextView)v.findViewById(R.id.textView);
        //textView.setText(fruits.get(position));

        final String text = fruits.get(position);

        return v;
    }

}
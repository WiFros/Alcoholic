package com.hanul.alcoholic;

import androidx.appcompat.app.AppCompatActivity;
import android.app.Activity;
import android.content.Context;
import android.content.res.Resources;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.RectF;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.os.Handler;
import android.os.SystemClock;
import android.util.AttributeSet;
import android.view.Display;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AbsListView;
import android.widget.Adapter;
import android.widget.BaseAdapter;
import android.widget.ExpandableListView;
import java.util.ArrayList;
import android.os.Bundle;
import android.widget.FrameLayout;
import android.widget.HeaderViewListAdapter;
import android.widget.ListView;

public class expandableRecipeList extends AppCompatActivity {
    private ExpandableListView listView;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_expandable_recipe_list);
        Display newDisplay = getWindowManager().getDefaultDisplay();
        int width = newDisplay.getWidth();

        ArrayList<myGroup> DataList = new ArrayList<myGroup>();
        listView = (ExpandableListView)findViewById(R.id.mylist);
        myGroup temp = new myGroup("A cocktail");
        temp.child.add("a");
        temp.child.add("aa");
        temp.child.add("aaa");
        DataList.add(temp);
        temp = new myGroup("B cocktail");
        temp.child.add("b");
        temp.child.add("bb");
        temp.child.add("bbb");
        DataList.add(temp);
        temp = new myGroup("C cocktail");
        temp.child.add("c");
        temp.child.add("cc");
        temp.child.add("ccc");
        DataList.add(temp);
        temp = new myGroup("All cocktail");
        temp.child.add("a");
        temp.child.add("b");
        temp.child.add("c");
        temp.child.add("d");
        temp.child.add("e");
        temp.child.add("f");
        temp.child.add("g");
        temp.child.add("h");
        temp.child.add("i");
        temp.child.add("j");
        temp.child.add("k");
        temp.child.add("l");
        temp.child.add("m");
        temp.child.add("n");
        temp.child.add("o");
        temp.child.add("p");
        temp.child.add("q");
        temp.child.add("r");
        temp.child.add("s");
        temp.child.add("t");
        temp.child.add("u");
        temp.child.add("v");
        DataList.add(temp);


        ExpandAdapter adapter = new ExpandAdapter(getApplicationContext(),R.layout.parent_row,R.layout.child_row,DataList);
        listView.setIndicatorBounds(width-50, width); //이 코드를 지우면 화살표 위치가 바뀐다.
        listView.setAdapter(adapter);
    }
}


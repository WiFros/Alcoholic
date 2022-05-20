package com.hanul.alcoholic;

import androidx.appcompat.app.AppCompatActivity;
import java.util.ArrayList;

import android.os.Bundle;

public class parentRow extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.parent_row);
    }
}

class myGroup {
    public ArrayList<String> child;
    public String groupName;
    myGroup(String name){
        groupName = name;
        child = new ArrayList<String>();
    }
}

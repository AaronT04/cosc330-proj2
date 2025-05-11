
package com.at04.touchmovetest;

import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;

import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class LevelCreator extends AppCompatActivity {
    int tvID[];

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_levelcreator);
        tvID=new int[]{R.id.tv0,R.id.tv1,R.id.tv2};

    };

    public void onDecrease(View view) {
        Button b=(Button)view;
        int item=Integer.valueOf(b.getContentDescription().toString());
        TextView inc=findViewById(tvID[item]);
        int downOne=Integer.valueOf(inc.getText().toString())-1;
        inc.setText(String.valueOf(downOne));

    }

    public void onIncrease(View view) {
        Button b=(Button)view;
        int item=Integer.valueOf(b.getContentDescription().toString());
        TextView inc=findViewById(tvID[item]);
        int upOne=Integer.valueOf(inc.getText().toString())+1;
        inc.setText(String.valueOf(upOne));
    }
    public void onAdd(View view){
        int out=0;
        for(int i=0;i<tvID.length;i++){
            TextView inc=findViewById(tvID[i]);
            out+=Integer.valueOf(inc.getText().toString());
        }
        Button b=(Button) view;
        b.setText(String.valueOf(out));
    }
}

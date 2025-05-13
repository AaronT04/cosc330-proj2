
package com.at04.touchmovetest;

import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;

import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;

public class LevelCreator extends AppCompatActivity {
    int tvID[];
    AttackInfoList finalSequence;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_levelcreator);
        tvID=new int[]{/*R.id.tv0,R.id.tv1,R.id.tv2,R.id.tv3*/0,0,0,/*R.id.tv3,R.id.tv4*/0,0,R.id.tv5,R.id.tv6,R.id.tv7,R.id.tv8};
        finalSequence=new AttackInfoList();
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
    public void AddCircle(View view){
/*
        AttackInfoList subList=new AttackInfoList();
        TextView countView=findViewById(R.id.tv1);
        int count=Integer.valueOf(countView.getText().toString());
        TextView speedView=findViewById(R.id.tv2);
        float speed=Float.valueOf(speedView.getText().toString());
        TextView radView=findViewById(R.id.tv3);
        float inputRad=Float.valueOf(radView.getText().toString())/6f;
        TextView coolView=findViewById(R.id.tv0);
        float cooldown=Float.valueOf(coolView.getText().toString());
        EditText etOffset=findViewById(R.id.Center);
        float offsetCenter=Float.valueOf(etOffset.getText().toString())-1f;
        subList.add(1,new AttackInfo[]{CircleAttack.getInitializer(new BaseAttackInfo(count,speed,cooldown),inputRad,offsetCenter )});
        finalSequence.add(1,new AttackInfoList[]{subList});*/
    }
    public void AddLine(View view){
        /*AttackInfoList repetitionList=new AttackInfoList();
        AttackInfoList subList=new AttackInfoList();
        TextView countView=findViewById(R.id.tv3);
        int count=Integer.valueOf(countView.getText().toString());//plus minus
        TextView speedView=findViewById(R.id.tv4);
        float speed=Float.valueOf(speedView.getText().toString());//plus minus
        EditText coolEdit=findViewById(R.id.LineCooldown);
        float cooldown=Float.valueOf(coolEdit.getText().toString());//enter
        EditText squishEdit=findViewById(R.id.LineSquish);
        float squish=Float.valueOf(squishEdit.getText().toString());//enter
        subList.add(1,new AttackInfo[]{LineAttack.getInitializer(new BaseAttackInfo(count,speed,cooldown),squish  )});
        finalSequence.add(1,new AttackInfoList[]{subList});*/
    }
    public void AddFall(View view){
        AttackInfoList repetitionList=new AttackInfoList();
        AttackInfoList subList=new AttackInfoList();
        TextView countView=findViewById(R.id.tv7);
        int count=Integer.valueOf(countView.getText().toString());//plus minus
        TextView speedView=findViewById(R.id.tv8);
        float speed=Float.valueOf(speedView.getText().toString());//plus minus
        EditText coolEdit=findViewById(R.id.fallCooldown);
        float cooldown=Float.valueOf(coolEdit.getText().toString());//enter
        RadioGroup slantView=findViewById(R.id.slant);
        RadioButton slantBut=findViewById(slantView.getCheckedRadioButtonId());
        int slant=Integer.valueOf(slantBut.getContentDescription().toString());



        subList.add(1,new AttackInfo[]{FallAttack.getInitializer(new BaseAttackInfo(count,speed,cooldown),false,slant  )});
        finalSequence.add(1,new AttackInfoList[]{subList});
    }
    public void AddEllipse(View view){
        AttackInfoList repetitionList=new AttackInfoList();
        AttackInfoList subList=new AttackInfoList();
        TextView countView=findViewById(R.id.tv7);
        int count=Integer.valueOf(countView.getText().toString());//plus minus
        TextView speedView=findViewById(R.id.tv8);
        float speed=Float.valueOf(speedView.getText().toString());//plus minus
        EditText coolEdit=findViewById(R.id.fallCooldown);
        float cooldown=Float.valueOf(coolEdit.getText().toString());//enter
        Float height=10f;
        Line startLine= new Line(new Range(0, Screen.middleX), Screen.bottom);
        Line endLine=new Line(Screen.middleX, new Range(Screen.middleY, Screen.top));


        subList.add(1,new AttackInfo[]{EllipseAttack.getInitializer(new BaseAttackInfo(count,speed,cooldown),startLine,endLine )});
        finalSequence.add(1,new AttackInfoList[]{subList});
    }


    public void onSubmit(View view) {
        LevelStorage.saveToDatabase(finalSequence,"circle button test","username","3");
    }
}


/*
AttackInfoList atkInfoList = new AttackInfoList();
        AttackInfoList subList = new AttackInfoList();
        subList.add(5, new AttackInfo[]{
                LineAttack.getInitializer(new BaseAttackInfo(2, 5, 0.3f), 0),
                LineAttack.getInitializer(new BaseAttackInfo(3, 10, 0.3f), 0.1f)
        });
        subList.add(FallAttack.getInitializer(new BaseAttackInfo(2, 10, 0.5f),false, 1));
        subList.add(FallAttack.getInitializer(new BaseAttackInfo(3, 15, 0.5f), false, -1));

        atkInfoList.add(5, new AttackInfoList[]{subList});
        LevelStorage.saveToDatabase(atkInfoList, "Line test", "Aaron", "0");




*/
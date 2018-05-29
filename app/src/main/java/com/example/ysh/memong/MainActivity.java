package com.example.ysh.memong;

import android.content.Intent;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

import io.realm.Realm;
import io.realm.RealmChangeListener;
import io.realm.RealmConfiguration;
import io.realm.RealmResults;

public class MainActivity extends AppCompatActivity {

    TextView mText;
    Realm mRealm;
    RecyclerView rcv;
    RcvAdapter rcvAdapter;
    Memo memo_Main;
    public List<Memo> list = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main2);


        mText = (TextView) findViewById(R.id.mContextTextView);
        rcv = (RecyclerView) findViewById(R.id.rcvMain);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);
        rcv.addItemDecoration(new DividerItemDecoration(this, linearLayoutManager.getOrientation()));
        rcv.setLayoutManager(linearLayoutManager);

        Realm.init(this);
        mRealm = Realm.getDefaultInstance();


        RealmResults<Memo> realmResults = mRealm.where(Memo.class)
                .findAllAsync();

        for(Memo memo : realmResults) {
            // mText.setText(memo.getText());
            list.add(new Memo(memo.getText()));
            Log.d("태그","테스트1 리사이클러뷰 등록");
            rcvAdapter = new RcvAdapter(MainActivity.this,list);
            rcv.setAdapter(rcvAdapter);
        }

        FloatingActionButton button = (FloatingActionButton) findViewById(R.id.floating);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this,AddActivity.class);
                startActivityForResult(intent,1);
                //startActivity(intent);
            }
        });
    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode,resultCode,data);

        if(resultCode == RESULT_OK) {

            String title = data.getStringExtra("value");
            String time = data.getStringExtra("value2");
            Toast.makeText(this,title + "," + time,Toast.LENGTH_SHORT).show();

            mRealm.beginTransaction();
            memo_Main = mRealm.createObject(Memo.class);
            memo_Main.setText(title);

            mRealm.commitTransaction();

            RealmResults<Memo> realmResults = mRealm.where(Memo.class)
                    .equalTo("text",title)
                    .findAllAsync();

            list.add(new Memo(title));
            rcvAdapter = new RcvAdapter(MainActivity.this,list);
            rcv.setAdapter(rcvAdapter);

        }
    }
}

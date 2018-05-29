package com.example.ysh.memong;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

public class AddActivity extends AppCompatActivity{

    EditText editText;
    String title;
    String formattedDate;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add);

        editText = (EditText) findViewById(R.id.title_text);
        Button btn = (Button) findViewById(R.id.addFinish);

        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Date c = Calendar.getInstance().getTime();
                SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
                formattedDate = df.format(c);
                title = editText.getText().toString();
                Intent add = new Intent();
                add.putExtra("value",title);
                add.putExtra("value2",formattedDate);
                setResult(RESULT_OK,add);
                finish();
            }
        });

    }
}

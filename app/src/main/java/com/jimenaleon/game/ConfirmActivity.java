package com.jimenaleon.game;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

public class ConfirmActivity extends AppCompatActivity {

    TextView name;
    TextView lastname;
    TextView phone;
    TextView age;
    TextView gender;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_confirm);

        User user = (User) getIntent().getSerializableExtra("DATA");

        name = (TextView) findViewById(R.id.cName);
        lastname = (TextView) findViewById(R.id.cLastName);
        phone = (TextView) findViewById(R.id.cPhone);
        age = (TextView) findViewById(R.id.cAge);
        gender = (TextView) findViewById(R.id.cGender);

        name.setText(user.getName());
        lastname.setText(user.getLastName());
        phone.setText(user.getPhone());
        age.setText(user.getAge());
        String tmp = user.getGender();
        if(tmp.equals("M")){
            gender.setText("Male");
        }else{
            gender.setText("Female");
        }
    }




    public void goToGame(View view) {
        Intent intent = new Intent(ConfirmActivity.this, MainActivity.class);
        startActivity(intent);
    }
}

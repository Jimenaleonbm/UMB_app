package com.jimenaleon.game;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;

public class RegisterActivity extends AppCompatActivity {

    private EditText name;
    private EditText lastName;
    private EditText phone;
    private EditText age;
    private RadioGroup radioGroup;
    private String gender;
    private String radio;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        name = (EditText) findViewById(R.id.name);
        lastName = (EditText) findViewById(R.id.lastname);
        phone = (EditText) findViewById(R.id.phone);
        age = (EditText) findViewById(R.id.age);
        radioGroup = (RadioGroup) findViewById(R.id.radiogroup);
        radioGroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup radioGroup, int i) {
                Log.d("i", " " + i);
                if(i == 1){
                    gender = "M";
                }else{
                    gender = "F";
                }
            }
        });

    }

    public User getUser(){

        String uName = name.getText().toString();
        String uLastName = lastName.getText().toString();
        String uPhone = phone.getText().toString();
        String uAge = age.getText().toString();
        String uGender = gender;

        return new User(uName, uLastName, uPhone, uAge, uGender);
     }

    public void goToNext(View view) {

        User user = getUser();

        Intent intent = new Intent(RegisterActivity.this, ConfirmActivity.class);
        intent.putExtra("DATA", user);
        startActivity(intent);
    }
}

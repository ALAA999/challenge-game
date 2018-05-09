package com.example.alaa.challangegame;

import android.content.Context;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.Toast;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.ArrayList;

public class Main1Activity extends AppCompatActivity {

    static boolean Same_Question, one_player, Defult_Questions;
    static int time;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main1);
        time = 60;
        findViewById(R.id.next).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (((RadioButton) findViewById(R.id.Defult_Questions)).isChecked()) {
                    Defult_Questions = true;
                } else if (((RadioButton) findViewById(R.id.math_question)).isChecked()) {
                    Defult_Questions = false;
                }
                if (((RadioButton) findViewById(R.id.Same_Questions)).isChecked()) {
                    Same_Question = true;
                } else if (((RadioButton) findViewById(R.id.Diffrent_question)).isChecked()) {
                    Same_Question = false;
                }
                String editText = ((EditText) findViewById(R.id.edit_text)).getText().toString();
                if (!TextUtils.isEmpty(editText)) {
                    time = Integer.parseInt(editText);
                }
                startActivity(new Intent(Main1Activity.this, MainActivity.class));
                finish();
            }
        });
        findViewById(R.id.one_player).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                one_player = true;
                ((ImageView) findViewById(R.id.one_player)).setImageResource(R.drawable.ic_person_green_24dp);
                ((ImageView) findViewById(R.id.two_player)).setImageResource(R.drawable.ic_two_players_black_24dp);
                findViewById(R.id.readio_group).setVisibility(View.INVISIBLE);
                findViewById(R.id.line).setVisibility(View.INVISIBLE);
            }
        });
        findViewById(R.id.two_player).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                one_player = false;
                ((ImageView) findViewById(R.id.two_player)).setImageResource(R.drawable.ic_supervisor_account_green_24dp);
                ((ImageView) findViewById(R.id.one_player)).setImageResource(R.drawable.ic_person_black_24dp);
                findViewById(R.id.readio_group).setVisibility(View.VISIBLE);
                findViewById(R.id.line).setVisibility(View.VISIBLE);
            }
        });
        findViewById(R.id.Add_question).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(Main1Activity.this, Add_question.class));
            }
        });
    }

}

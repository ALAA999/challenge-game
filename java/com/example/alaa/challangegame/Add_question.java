package com.example.alaa.challangegame;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

public class Add_question extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_question);
        findViewById(R.id.save_question).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                findViewById(R.id.wrong_show).setVisibility(View.INVISIBLE);
                String question = ((EditText) (findViewById(R.id.enter_question))).getText().toString();
                String option1 = ((EditText) (findViewById(R.id.option1))).getText().toString();
                String option2 = ((EditText) (findViewById(R.id.option2))).getText().toString();
                String option3 = ((EditText) (findViewById(R.id.option3))).getText().toString();
                String option4 = ((EditText) (findViewById(R.id.option4))).getText().toString();
                String right_ans = ((EditText) (findViewById(R.id.right_answer))).getText().toString();
                if (question.isEmpty() || option1.isEmpty() || option2.isEmpty() || option3.isEmpty() || option4.isEmpty() || right_ans.isEmpty()) {
                    Toast.makeText(Add_question.this, "جميع الحقول مطلوبة", Toast.LENGTH_SHORT).show();
                } else {
                    if (option1.equals(right_ans) || option2.equals(right_ans) || option3.equals(right_ans) || option4.equals(right_ans)) {
                        Questions questions = new Questions(question, option1, option2, option3, option4, right_ans);
                        if (new InsertingData(getApplicationContext()).insertData(questions)) {
                            Toast.makeText(Add_question.this, "تم الإضافة بنجاح", Toast.LENGTH_SHORT).show();
                            ((EditText) (findViewById(R.id.enter_question))).setText("");
                            ((EditText) (findViewById(R.id.option1))).setText("");
                            ((EditText) (findViewById(R.id.option2))).setText("");
                            ((EditText) (findViewById(R.id.option3))).setText("");
                            ((EditText) (findViewById(R.id.option4))).setText("");
                            ((EditText) (findViewById(R.id.right_answer))).setText("");
                        } else {
                            Toast.makeText(Add_question.this, "حدث خطأ", Toast.LENGTH_SHORT).show();
                        }
                    } else {
                        findViewById(R.id.wrong_show).setVisibility(View.VISIBLE);
                    }
                }
            }
        });
        findViewById(R.id.back).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
    }
}

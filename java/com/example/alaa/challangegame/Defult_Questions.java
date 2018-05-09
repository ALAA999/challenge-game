package com.example.alaa.challangegame;

import android.util.Log;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Random;

import static com.example.alaa.challangegame.MainActivity.ques;

/**
 * Created by Alaa on 1/3/2018.
 */

public class Defult_Questions implements playingstratagy {
    @Override
    public Questions paly() {
        Random random = new Random();
        int random_num = random.nextInt(ques.size());
        //Log.e("size",ques.size()+", "+random_num);
        Questions questions = new Questions(ques.get(random_num).getQuestion(),
                ques.get(random_num).getOption1(),
                ques.get(random_num).getOption2(),
                ques.get(random_num).getOption3(),
                ques.get(random_num).getOption4(),
                ques.get(random_num).getAnswer());
        ques.remove(random_num);
        return questions;
    }
}

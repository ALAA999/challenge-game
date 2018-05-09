package com.example.alaa.challangegame;

import java.util.ArrayList;
import java.util.Collections;

/**
 * Created by Alaa on 1/3/2018.
 */

public class Maht_qusetions implements playingstratagy {
    @Override
    public Questions paly() {
        ArrayList<Integer> strings = new ArrayList<>();
        int num1, num2, right = 0;
        String question;
        int sign = (int) (Math.random() * 10);
        if (sign >= 0 && sign <= 3) {
            num1 = (int) (Math.random() * 10);
            num2 = (int) (Math.random() * 10);
            question = num1 + "*" + num2;
            strings.add((int) (Math.random() * 10) * (int) (Math.random() * 10));
            strings.add((int) (Math.random() * 10) * (int) (Math.random() * 10));
            strings.add((int) (Math.random() * 10) * (int) (Math.random() * 10));
            right = (num1 * num2);
            strings.add(right);
        } else if (sign >= 4 && sign <= 7) {
            num1 = (int) (Math.random() * 100);
            num2 = (int) (Math.random() * 100);
            question = num1 + "-" + num2;
            strings.add((int) (Math.random() * 100) - (int) (Math.random() * 100));
            strings.add((int) (Math.random() * 100) - (int) (Math.random() * 100));
            strings.add((int) (Math.random() * 100) - (int) (Math.random() * 100));
            right = (num1 - num2);
            strings.add(right);
        } else {
            num1 = (int) (Math.random() * 100);
            num2 = (int) (Math.random() * 100);
            question = num1 + "+" + num2;
            strings.add((int) (Math.random() * 100) + (int) (Math.random() * 100));
            strings.add((int) (Math.random() * 100) + (int) (Math.random() * 100));
            strings.add((int) (Math.random() * 100) + (int) (Math.random() * 100));
            right = (num1 + num2);
            strings.add(right);
        }//this loop is to make sure that the numbers will not be duplicated
        for (int i = 0; i < 4; ++i) {
            for (int j = i + 1; j < 4; ++j) {
                if (strings.get(i) == strings.get(j)) {
                    return null;
                }
            }

        }
        Collections.shuffle(strings);
        return new Questions(question, strings.get(0) + "", strings.get(1) + ""
                , strings.get(2) + "", strings.get(3) + "", right + "");
    }
}


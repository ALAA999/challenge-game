package com.example.alaa.challangegame;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.media.MediaPlayer;
import android.os.CountDownTimer;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.CardView;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.ArrayList;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {
    int cardviews[] = {R.id.card1, R.id.card2, R.id.card3, R.id.card4, R.id.card5, R.id.card6, R.id.card7, R.id.card8};
    int[] txts = {R.id.textView2, R.id.textView3, R.id.textView4, R.id.textView5, R.id.textView7,
            R.id.textView8, R.id.textView9, R.id.textView10, R.id.textView1, R.id.textView6};
    TextView textView[] = new TextView[10];
    CardView Cardview[] = new CardView[8];
    TextView timer1, timer2, points1, points2;
    InsertingData data = new InsertingData(this);
    Questions question1, question2;
    playingstratagy stratagy;
    boolean wating = false;
    int i;
    MediaPlayer player1, player2;
    Animation right, wrong;
    static ArrayList<Questions> ques;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        File database = getApplication().getDatabasePath(data.DBNAME);
        if (database.exists() == false) {
            data.getReadableDatabase();
            if (CopyDatabase(this)) {
                Toast.makeText(this, "تم نسخ بيانات قاعدة البيانات بنجاح", Toast.LENGTH_SHORT).show();
            } else {
                Toast.makeText(this, "فشل نسخ بيانات قاعدة البيانات", Toast.LENGTH_SHORT).show();
                return;
            }
        }
        ///////////////////////////////////////////////////////////////////////////Finding TextViews
        for (int i = 0; i < 10; ++i) {
            textView[i] = findViewById(txts[i]);
        }
        for (int i = 0; i < 8; ++i) {
            Cardview[i] = findViewById(cardviews[i]);
            Cardview[i].setOnClickListener(MainActivity.this);
        }
        timer1 = findViewById(R.id.timer_1);
        timer2 = findViewById(R.id.timer_2);
        points1 = findViewById(R.id.points_1);
        points2 = findViewById(R.id.points_2);
        player1 = MediaPlayer.create(MainActivity.this, R.raw.conragts);
        player2 = MediaPlayer.create(MainActivity.this, R.raw.secound);
        ///////////////////////////////////////////////////////////////////////////Dilaog
        Check_game_options();
    }

    ///////////////////////////////////////////////////////////////////////////
    public boolean CopyDatabase(Context context) {
        try {
            InputStream inputStream = context.getAssets().open(data.DBNAME);
            String outFileName = data.DBLOCATION + data.DBNAME;
            OutputStream outputStream = new FileOutputStream(outFileName);
            byte[] buff = new byte[1024];
            int length = 0;
            while ((length = inputStream.read(buff)) > 0) {
                outputStream.write(buff, 0, length);
            }
            outputStream.flush();
            outputStream.close();
            return true;
        } catch (IOException e) {
            e.printStackTrace();
            return false;
        }
    }
    ///////////////////////////////////////////////////////////////////////////

    public void Check_game_options() {
        i = 3;
        int[] ids = {R.id.line5, R.id.line6, R.id.line2, R.id.line4, R.id.line3};
        if (Main1Activity.one_player) {
            for (int j = 0; j < ids.length; j++) {
                (findViewById(ids[j])).setVisibility(View.INVISIBLE);
                (findViewById(ids[j])).setEnabled(false);
            }
        } else {
            for (int j = 0; j < ids.length; j++) {
                (findViewById(ids[j])).setVisibility(View.VISIBLE);
                (findViewById(ids[j])).setEnabled(true);
            }
        }
        if (Main1Activity.Defult_Questions) {
            stratagy = new Defult_Questions();
            ques = data.getAllData();
        } else {
            stratagy = new Maht_qusetions();
        }
        timer1.setText(Main1Activity.time + "");
        points1.setText("0");
        ((ImageView) findViewById(R.id.img_player1)).setImageResource(R.drawable.ic_brightness_1_black_24dp);
        timer2.setText(Main1Activity.time + "");
        points2.setText("0");
        ((ImageView) findViewById(R.id.img_player2)).setImageResource(R.drawable.ic_brightness_1_black_24dp);
        Counter();
    }

    ///////////////////////////////////////////////////////////////////////////
    @Override
    public void onBackPressed() {
        finish();
        try {
            player2.stop();
            player1.stop();
        } catch (Exception e) {
        }
        player2 = null;
        player1 = null;
    }
///////////////////////////////////////////////////////////////////////////

    public void Counter() {
        new CountDownTimer(1000, 1000) {

            public void onTick(long millisUntilFinished) {
            }

            public void onFinish() {
                ((TextView) findViewById(R.id.counter1)).setText(i + "");
                ((TextView) findViewById(R.id.counter2)).setText(i + "");
                if (i == 0) {//After first 3 secounds
                    ((TextView) findViewById(R.id.counter1)).setText("Points");
                    ((TextView) findViewById(R.id.counter2)).setText("Points");
                    if (Main1Activity.Same_Question) {
                        SetSameQuestion();
                    } else {
                        SetTextPlayer1();
                        SetTextPlayer2();
                    }
                    Counter2();
                    //SetAllInvisible();
                    Toast.makeText(MainActivity.this, "Start", Toast.LENGTH_SHORT).show();
                } else {//Counting 3 secounds
                    Counter();
                }
                --i;
            }
        }.start();
    }

///////////////////////////////////////////////////////////////////////////

    public void Counter2() {
        new CountDownTimer(1000, 1000) {

            public void onTick(long millisUntilFinished) {
            }

            public void onFinish() {
                try {
                    player2.start();
                } catch (Exception e) {
                }
                int num1 = Integer.parseInt(timer1.getText().toString());
                if (num1 != 0) {
                    --num1;
                    timer1.setText(num1 + "");
                    timer2.setText(num1 + "");
                    Counter2();
                } else {
                    ShowWinner();
                }
            }
        }.start();
    }

    ///////////////////////////////////////////////////////////////////////////
    public void ShowWinner() {
        int PointsPlayerOne = Integer.parseInt(points1.getText().toString());
        int PointsPlayertwo = Integer.parseInt(points2.getText().toString());
        try {
            player1.start();
        } catch (Exception e) {

        }
        String WinnerName = null, congrac = "Congratulations!";
        if (PointsPlayerOne > PointsPlayertwo) {
            WinnerName = "Player Tow Wins..!!";
        } else if (PointsPlayerOne < PointsPlayertwo) {
            WinnerName = "Player One Wins..!!";
        } else {
            congrac = "Ops!";
            WinnerName = "Tie , You're both are smart!";
        }
        AlertDialog.Builder dialog = new AlertDialog.Builder(MainActivity.this);
        dialog.setIcon(R.drawable.ic_insert_emoticon_black_24dp);
        dialog.setTitle(congrac);
        dialog.setCancelable(false);
        dialog.setMessage(WinnerName);
        dialog.setPositiveButton("New Game", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                startActivity(new Intent(MainActivity.this, Main1Activity.class));
                finish();
            }
        });
        dialog.setNegativeButton("Exit", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                finish();
            }
        });
        try {
            dialog.show();
        } catch (Exception e) {

        }
    }

///////////////////////////////////////////////////////////////////////////

    public void SetTextPlayer1() {
        question1 = stratagy.paly();
        if (question1 == null) {
            SetTextPlayer1();//This if is to make sure that there is no duplacates in the numbers
        }
        textView[8].setText(question1.getQuestion());
        textView[0].setText(question1.getOption1());
        textView[1].setText(question1.getOption2());
        textView[2].setText(question1.getOption3());
        textView[3].setText(question1.getOption4());
    }

    ///////////////////////////////////////////////////////////////////////////
    public void SetTextPlayer2() {
        question2 = stratagy.paly();
        if (question2 == null) {
            SetTextPlayer2();//This if is to make sure that there is no duplacates in the numbers
        }
        textView[9].setText(question2.getQuestion());
        textView[4].setText(question2.getOption1());
        textView[5].setText(question2.getOption2());
        textView[6].setText(question2.getOption3());
        textView[7].setText(question2.getOption4());
    }

    ///////////////////////////////////////////////////////////////////////////
    public void SetSameQuestion() {
        question2 = stratagy.paly();
        if (question2 == null) {
            SetSameQuestion();
        }//This is to make sure that there is no duplacates in the numbers
        textView[0].setText(question2.getOption1());
        textView[1].setText(question2.getOption2());
        textView[2].setText(question2.getOption3());
        textView[3].setText(question2.getOption4());
        textView[4].setText(question2.getOption1());
        textView[5].setText(question2.getOption2());
        textView[6].setText(question2.getOption3());
        textView[7].setText(question2.getOption4());
        textView[8].setText(question2.getQuestion());
        textView[9].setText(question2.getQuestion());
        question1 = new Questions(question2.answer);
    }

    ///////////////////////////////////////////////////////////////////////////

    public void count_dwon_timer(final int number) {
        new CountDownTimer(1000, 1000) {
            @Override
            public void onTick(long l) {

            }

            @Override
            public void onFinish() {
                wating = false;
                findViewById(R.id.show_gotten1).setVisibility(View.INVISIBLE);
                findViewById(R.id.show_gotten2).setVisibility(View.INVISIBLE);
                for (int j = 0; j < 8; j++) {
                    Cardview[j].setCardBackgroundColor(Color.MAGENTA);
                }
                switch (number) {
                    case 1:
                        SetTextPlayer1();

                        break;
                    case 2:
                        SetTextPlayer2();
                        break;
                    case 3:
                        SetSameQuestion();
                        break;
                }
            }
        }.start();
    }

    ///////////////////////////////////////////////////////////////////////////
    @Override
    public void onClick(View view) {
        if (!wating && i == -1) {//wating for the one secound after every click
            right = AnimationUtils.loadAnimation(this, R.anim.right);
            wrong = AnimationUtils.loadAnimation(this, R.anim.wrong);
            int q = view.getId();
            if (q == Cardview[0].getId() || q == Cardview[1].getId() || q == Cardview[2].getId() || q == Cardview[3].getId()) {
                for (int i = 0; i < 4; ++i) {//The press has come from the first player
                    if (q == Cardview[i].getId()) {
                        int points;
                        if ((textView[i].getText()).equals(question1.getAnswer())) {
                            points = Integer.parseInt(points1.getText().toString()); //Right asnwer for player one
                            points = points + 2;
                            Cardview[i].setCardBackgroundColor(Color.GREEN);
                            ((ImageView) findViewById(R.id.img_player1)).setImageResource(R.drawable.ic_check_circle_black_24dp);
                            (findViewById(R.id.line2)).setAnimation(right);//if the image is inside a layout then setanimation for this image will not work so make it for the whole linear
                            findViewById(R.id.show_gotten1).setVisibility(View.VISIBLE);
                            ((TextView) findViewById(R.id.show_gotten1)).setText("+2");
                            ((TextView) findViewById(R.id.show_gotten1)).setTextColor(Color.BLUE);
                            findViewById(R.id.show_gotten1).setAnimation(right);
                        } else {
                            points = Integer.parseInt(points1.getText().toString());   //wrong answer for the fist player
                            points = points - 1;
                            Cardview[i].setCardBackgroundColor(Color.RED);
                            for (int j = 0; j < 4; j++) {
                                if (textView[j].getText().equals(question1.getAnswer())) {
                                    Cardview[j].setCardBackgroundColor(Color.GREEN);
                                    break;
                                }
                            }
                            ((ImageView) findViewById(R.id.img_player1)).setImageResource(R.drawable.ic_cancel_black_24dp);
                            (findViewById(R.id.line2)).setAnimation(wrong);
                            findViewById(R.id.show_gotten1).setVisibility(View.VISIBLE);
                            ((TextView) findViewById(R.id.show_gotten1)).setText("-1");
                            ((TextView) findViewById(R.id.show_gotten1)).setTextColor(Color.RED);
                            findViewById(R.id.show_gotten1).setAnimation(wrong);
                        }
                        points1.setText(points + "");
                        wating = true;
                        if (Main1Activity.Same_Question) {
                            count_dwon_timer(3);
                        } else {
                            count_dwon_timer(1);
                        }
                        break;
                    }
                }
            } else {
                for (int i = 4; i < 8; ++i) {//The press has come from the secound player
                    if (q == Cardview[i].getId()) {
                        int points;
                        if ((textView[i].getText()).equals(question2.getAnswer())) {
                            points = Integer.parseInt(points2.getText().toString());  //Right asnwer for player two
                            points = points + 2;
                            Cardview[i].setCardBackgroundColor(Color.GREEN);
                            ((ImageView) findViewById(R.id.img_player2)).setImageResource(R.drawable.ic_check_circle_black_24dp);
                            (findViewById(R.id.img_player2)).setAnimation(right);
                            findViewById(R.id.show_gotten2).setVisibility(View.VISIBLE);
                            ((TextView) findViewById(R.id.show_gotten2)).setText("+2");
                            ((TextView) findViewById(R.id.show_gotten2)).setTextColor(Color.BLUE);
                            findViewById(R.id.show_gotten2).setAnimation(right);
                        } else {
                            points = Integer.parseInt(points2.getText().toString()); //wrong answer for the secound player
                            points = points - 1;
                            Cardview[i].setCardBackgroundColor(Color.RED);
                            for (int j = 4; j < 8; j++) {
                                if (textView[j].getText().equals(question2.getAnswer())) {
                                    Cardview[j].setCardBackgroundColor(Color.GREEN);
                                    break;
                                }
                            }
                            ((ImageView) findViewById(R.id.img_player2)).setImageResource(R.drawable.ic_cancel_black_24dp);
                            (findViewById(R.id.img_player2)).setAnimation(wrong);
                            findViewById(R.id.show_gotten2).setVisibility(View.VISIBLE);
                            ((TextView) findViewById(R.id.show_gotten2)).setText("-1");
                            ((TextView) findViewById(R.id.show_gotten2)).setTextColor(Color.RED);
                            findViewById(R.id.show_gotten2).setAnimation(wrong);
                        }
                        points2.setText(points + "");
                        wating = true;
                        if (Main1Activity.Same_Question) {
                            count_dwon_timer(3);
                        } else {
                            count_dwon_timer(2);
                        }
                        break;
                    }
                }
            }
        }
    }
}
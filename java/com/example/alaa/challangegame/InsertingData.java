package com.example.alaa.challangegame;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.os.Environment;
import android.util.Log;

import java.util.ArrayList;

/**
 * Created by Alaa on 11/20/2017.
 */

public class InsertingData extends SQLiteOpenHelper {
    public static final String DBNAME = "Challangegame.db";
    public static final String DBLOCATION = Environment.getDataDirectory() + "/data/com.example.alaa.challangegame/databases/";
    private Context mContext;
    private SQLiteDatabase mDatabase;

    public InsertingData(Context context) {
        super(context, DBNAME, null, 1);////////////////////
        this.mContext = context;
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {
    }

    public void openDatabase() {
        String dbPath = mContext.getDatabasePath(DBNAME).getPath();
        if (mDatabase != null && mDatabase.isOpen()) {
            return;
        }
        mDatabase = SQLiteDatabase.openDatabase(dbPath, null, SQLiteDatabase.OPEN_READWRITE);
    }

    public void closeDatabase() {
        if (mDatabase != null) {
            mDatabase.close();
        }
    }

    public ArrayList<Questions> getAllData() {
        ArrayList<Questions> questions = new ArrayList<>();
        openDatabase();
        Cursor res = mDatabase.rawQuery("select * from QUESTION", null);
        while (res.moveToNext() != false) {
            String question = res.getString(0);
            String option1 = res.getString(1);
            String option2 = res.getString(2);
            String option3 = res.getString(3);
            String option4 = res.getString(4);
            String rightans = res.getString(5);
            questions.add(new Questions(question, option1, option2, option3, option4, rightans));
        }
        res.close();
        closeDatabase();
        return questions;
    }


    public boolean insertData(Questions question) {
        SQLiteDatabase sqLiteDatabase = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put("question", question.getQuestion());
        values.put("option1", question.getOption1());
        values.put("option2", question.getOption2());
        values.put("option3", question.getOption3());
        values.put("option4", question.getOption4());
        values.put("right_ans", question.getAnswer());
        long rs = sqLiteDatabase.insert("QUESTION", null, values);
        if (rs == -1) {
            return false;
        } else {
            return true;
        }
    }
}

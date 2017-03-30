package com.example.stamatis.questiongameapp.util;

import android.app.Activity;
import android.content.Context;
import android.content.SharedPreferences;
import android.os.Environment;
import android.preference.PreferenceManager;
import android.util.Log;

import com.example.stamatis.questiongameapp.GameActivity;
import com.example.stamatis.questiongameapp.R;
import com.example.stamatis.questiongameapp.game_util.Questions;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * Created by Stamatis Stiliatis(ExXoDuSs) on 4/3/2017.
 * 
 *  Class Uses: Reading JSON, save, retrieve and delete Score
 */

public class DataUtil{

    public DataUtil(){}

    public static boolean deserializeQuestions(List<Questions> listQuestions, String jsonString) {
        try{
            JSONArray questionsArrayJson = new JSONArray(jsonString);
            for (int i = 0; i < questionsArrayJson.length(); i++){
                JSONObject jsonQuestion = questionsArrayJson.getJSONObject(i);
                JSONArray answersArrayJson = jsonQuestion.getJSONArray("Answer");
                String answers[] = new String[answersArrayJson.length()];
                Questions question = new Questions();
                for (int j = 0; j < answersArrayJson.length(); j++){
                    answers[j] = answersArrayJson.getString(j);
                }
                question.setQuestion(jsonQuestion.getString("Question"));
                question.setAnswers(answers);
                question.setCorrectAnswer(jsonQuestion.getInt("CorrectAnswer"));
                question.setAlreadyDisplayed(jsonQuestion.getBoolean("Played"));
                // add each question to the question list
                listQuestions.add(question);
            }
        }catch (org.json.JSONException ex){
            Log.e("Error Log", "Parsing data error: " + ex.toString());
            return false;
        }
        return  true;
    }

    /**
     * @param keyName player's name
     * @param valuePoints player's points
     * @param context activity that is been called from*/
    public static void setScore(String keyName, int valuePoints, Context context) {
        SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(context);
        SharedPreferences.Editor editor = prefs.edit();
        editor.putInt(keyName, valuePoints);
        editor.commit();
    }

    public static List getScore(Context context) {
        Map<String,?> keys = PreferenceManager.getDefaultSharedPreferences(context).getAll();
        List<String> list = new ArrayList<>();
        for(Map.Entry<String,?> entry : keys.entrySet()){
            Log.d("map values",entry.getKey());
            list.add(entry.getKey() + " : " + entry.getValue().toString());
        }
        return list;
    }

    public static void deleteAllScores(Context context){
        SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(context);
        prefs.edit().clear().apply();
    }
}

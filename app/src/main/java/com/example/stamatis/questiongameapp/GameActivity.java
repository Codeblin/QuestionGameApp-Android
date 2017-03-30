package com.example.stamatis.questiongameapp;

import android.content.res.Resources;
import android.graphics.Color;
import android.graphics.Typeface;
import android.os.Build;
import android.os.CountDownTimer;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.example.stamatis.questiongameapp.game_util.Questions;
import com.example.stamatis.questiongameapp.util.DataUtil;
import com.example.stamatis.questiongameapp.util.MessageUtil;

import java.io.InputStream;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Random;
import java.util.Scanner;

public class GameActivity extends AppCompatActivity {

    private String playerName;
    private int playerScore = 0;
    private int playerHealth = 100;
    private List<Questions> listQuestions = new ArrayList<>();
    private Questions currentQuestion;
    //Views
    private ProgressBar healthBar;
    private TextView txtPlayerScore;
    private TextView txtPercentage;
    private TextView txtPlayerName;
    private TextView txtQuestion;
    private TextView txtPts;
    private TextView txtHP;
    private Button btnSubmit;
    private RadioGroup radioGroup;
    private RadioButton rdBtnAns1;
    private RadioButton rdBtnAns2;
    private RadioButton rdBtnAns3;
    private RadioButton rdBtnAns4;

    private Typeface font;

    // Finals
    final int POINTS_PER_ANSWER = 100;
    final int DAMAGE_PER_ANSWER = 10;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_game);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);
        font = Typeface.createFromAsset(getAssets(), "fonts/bear_hugs.ttf");
        initGame();
    }

    /*
    * Methods to initialize
    * game variables, questions and views
    */
    private void initGame(){
        initViews();
        initValues();
        loadQuestions();
        displayQuestion();
    }

    private void displayQuestion() {
        if (currentQuestion != null){
            txtQuestion.setText(currentQuestion.getQuestion());
            rdBtnAns1.setText(currentQuestion.getAnswers(0));
            rdBtnAns2.setText(currentQuestion.getAnswers(1));
            rdBtnAns3.setText(currentQuestion.getAnswers(2));
            rdBtnAns4.setText(currentQuestion.getAnswers(3));
        }else{
            gameOver();
            txtQuestion.setText("");
            radioGroup.setVisibility(View.GONE);
            btnSubmit.setEnabled(false);
        }
    }

    private void initViews(){
        txtPlayerName = (TextView) findViewById(R.id.text_player_name);
        txtPlayerName.setTypeface(font);
        healthBar = (ProgressBar) findViewById(R.id.health_bar);
        txtPercentage = (TextView) findViewById(R.id.text_health_percentage);
        txtPercentage.setTypeface(font);
        txtPlayerScore = (TextView) findViewById(R.id.text_player_score);
        txtPlayerScore.setTypeface(font);
        txtQuestion = (TextView) findViewById(R.id.text_question);
        txtQuestion.setTypeface(font);
        txtPts = (TextView) findViewById(R.id.text_pts);
        txtPts.setTypeface(font);
        txtHP = (TextView) findViewById(R.id.text_hp);
        txtHP.setTypeface(font);
        radioGroup = (RadioGroup) findViewById(R.id.rdio_group);
        rdBtnAns1 = (RadioButton) findViewById(R.id.rdBtn_A);
        rdBtnAns1.setTypeface(font);
        rdBtnAns2 = (RadioButton) findViewById(R.id.rdBtn_B);
        rdBtnAns2.setTypeface(font);
        rdBtnAns3 = (RadioButton) findViewById(R.id.rdBtn_C);
        rdBtnAns3.setTypeface(font);
        rdBtnAns4 = (RadioButton) findViewById(R.id.rdBtn_D);
        rdBtnAns4.setTypeface(font);
        btnSubmit = (Button) findViewById(R.id.btn_submit);
        btnSubmit.setTypeface(font);
    }

    private void initValues(){
        playerName = getIntent().getStringExtra("PLAYER_NAME");
        txtPlayerName.setText(playerName);
        healthBar.setProgress(100);
    }

    private void loadQuestions(){
        Resources res = getResources();
        InputStream iStream = res.openRawResource(R.raw.questions);
        // Scanner is useful for reading small files like that json
        Scanner scanner = new Scanner(iStream);
        StringBuilder sBuilder = new StringBuilder();
        String jsonResult;
        while (scanner.hasNextLine()){
            sBuilder.append(scanner.nextLine() + "\n");
        }
        jsonResult = sBuilder.toString();

        if (DataUtil.deserializeQuestions(listQuestions, jsonResult)){
            Collections.shuffle(listQuestions);
            currentQuestion = getNextQuestion();
            shuffleAnswers();
            displayQuestion();
        }else{
            MessageUtil.errorDialog(this);
        }
    }

    private Questions getNextQuestion(){
        for (int i = 0; i < listQuestions.size(); i++){
            if (!listQuestions.get(i).isAlreadyDisplayed()){
                listQuestions.get(i).setAlreadyDisplayed(true);
                return listQuestions.get(i);
            }
        }
        return null;
    }

    private void checkAnswer(){
        int radioButtonID = radioGroup.getCheckedRadioButtonId();
        RadioButton checkedRadioButton = (RadioButton) radioGroup.findViewById(radioButtonID);
        int idx = radioGroup.indexOfChild(checkedRadioButton);
        if (idx == currentQuestion.getCorrectAnswer()){
            // Right
            Log.d("Debug Info Log", "Correct Answer block entered.");
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                checkedRadioButton.setTextColor(getResources().getColor(R.color.color_right_answer, getTheme()));
            }else{
                checkedRadioButton.setTextColor(Color.parseColor("#4caf50"));
            }
            Toast.makeText(getApplicationContext(), "Your answer is correct!", Toast.LENGTH_SHORT).show();
            addPoints();
        }else{
            // Wrong
            Log.d("Debug Info Log", "Wrong answer block entered.");
            for (int i = 0; i < radioGroup.getChildCount(); i++){
                Log.d("Debug Info Log", "Iterating to find the right radio button...");
                RadioButton radioButton = (RadioButton)radioGroup.getChildAt(i);
                int index = radioGroup.indexOfChild(radioButton);
                if (index == currentQuestion.getCorrectAnswer()){
                    Log.d("Debug Info Log", "Found it!: " + currentQuestion.getQuestion());
                    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                        radioButton.setTextColor(getResources().getColor(R.color.color_right_answer, getTheme()));
                    }else{
                        radioButton.setTextColor(Color.parseColor("#4caf50"));
                    }
                }
            }
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                checkedRadioButton.setTextColor(getResources().getColor(R.color.color_wrong_answer, getTheme()));
            }else{
                checkedRadioButton.setTextColor(Color.parseColor("#D32F2F"));
            }
            Toast.makeText(getApplicationContext(), "Your answer is wrong! Right answer is: " + currentQuestion.getAnswers(currentQuestion.getCorrectAnswer()), Toast.LENGTH_SHORT).show();
            loseHealth();
        }
    }

    // Method is following the Fisher Yates shuffle
    // correctAnswer stores the string of the correct answer to spot it after the shuffle
    // correctAnswerIndex the value retrieved after spotting the index with the right string
    private void shuffleAnswers(){
        Random r = new Random();
        String correctAnswer = currentQuestion.getAnswers(currentQuestion.getCorrectAnswer());
        for (int i = currentQuestion.getAnswers().length - 1; i >= 0; i--)
        {
            int randomIndex = r.nextInt(i + 1);
            String temp = currentQuestion.getAnswers()[i];
            currentQuestion.getAnswers()[i] = currentQuestion.getAnswers()[randomIndex];
            currentQuestion.getAnswers()[randomIndex] = temp;
        }
        int correctAnswerIndex = Arrays.asList(currentQuestion.getAnswers()).indexOf(correctAnswer);
        currentQuestion.setCorrectAnswer(correctAnswerIndex);
    }

    private void gameOver(){
        startCountdown(false);
        GameOverDialog dialog = new GameOverDialog(this, playerName, playerScore);
        dialog.setCanceledOnTouchOutside(false);
        dialog.show();
    }

    // waits 3 seconds before show the next question
    private void startCountdown(final boolean onOff){
        new CountDownTimer(2000, 1000) {

            public void onTick(long millisUntilFinished) {
                // no need for functionality here...
            }

            public void onFinish() {
                if (!onOff) return;
                currentQuestion = getNextQuestion();
                displayQuestion();
                resetRadioButtons();
                btnSubmit.setEnabled(true);
            }
        }.start();
    }

    // UI Management methods
    private void resetRadioButtons(){
        rdBtnAns1.setTextColor(Color.BLACK);
        rdBtnAns2.setTextColor(Color.BLACK);
        rdBtnAns3.setTextColor(Color.BLACK);
        rdBtnAns4.setTextColor(Color.BLACK);
        radioGroup.clearCheck();
    }

    private void addPoints(){
        playerScore += POINTS_PER_ANSWER;
        txtPlayerScore.setText(String.valueOf(playerScore));
    }

    private void loseHealth(){
        playerHealth -= DAMAGE_PER_ANSWER;
        if (playerHealth == 0){
            healthBar.setProgress(0);
            txtPercentage.setText(0 + "%");
            gameOver();
        }else{
            healthBar.setProgress(playerHealth);
            txtPercentage.setText(playerHealth + "%");
        }
    }

    /*
    * Public methods
    * */

    public void submitClick(View view){
        int checkedRadioButtonId = radioGroup.getCheckedRadioButtonId();
        if (checkedRadioButtonId != -1){
            btnSubmit.setEnabled(false);
            checkAnswer();
            startCountdown(true);
        }
    }
}

package com.example.stamatis.questiongameapp;

import android.app.Activity;
import android.app.Dialog;
import android.content.Intent;
import android.os.Bundle;
import com.example.stamatis.questiongameapp.util.DataUtil;
import android.view.View;
import android.view.Window;
import android.widget.Button;

public class GameOverDialog  extends Dialog implements View.OnClickListener {

    private Activity context;
    private String playerName;
    private int playerScore;
    private Button btnSave, btnCancel;
    private Intent intent;

    public GameOverDialog(Activity parentActivity, String name, int score) {
        super(parentActivity);
        this.context = parentActivity;
        playerName = name;
        playerScore = score;
        intent = new Intent(context, MainActivity.class);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.dialog_game_over);
        btnSave = (Button) findViewById(R.id.btn_save);
        btnCancel = (Button) findViewById(R.id.btn_cancel);
        btnSave.setOnClickListener(this);
        btnCancel.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btn_save:
                DataUtil.setScore(playerName, playerScore, context);
                btnSave.setEnabled(false);
                context.startActivity(intent);
                break;
            case R.id.btn_cancel:
                context.startActivity(intent);
                break;
            default:
                break;
        }
    }
}

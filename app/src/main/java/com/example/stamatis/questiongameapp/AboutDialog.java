package com.example.stamatis.questiongameapp;

import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.example.stamatis.questiongameapp.util.DataUtil;

/**
 * Created by Stamatis Stiliatis Togrou(ExXoDuSs) on 8/3/2017.
 */

public class AboutDialog extends Dialog implements View.OnClickListener{

    private Button btnOk;

    public AboutDialog(Context context) {super(context);}

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.dialog_about);
        
        btnOk = (Button) findViewById(R.id.btn_ok);
        btnOk.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        if (v.getId() == R.id.btn_ok) dismiss();
    }
}

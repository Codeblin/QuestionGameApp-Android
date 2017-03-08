package com.example.stamatis.questiongameapp.util;

import android.annotation.TargetApi;
import android.app.Activity;
import android.content.DialogInterface;
import android.os.Build;
import android.support.v7.app.AlertDialog;
import com.example.stamatis.questiongameapp.R;

/**
 * Created by Stamatis Stiliatis Togrou)ExXoDuSs) on 4/3/2017.
 */

public class MessageUtil {

    public static void errorDialog(final Activity activity){
        AlertDialog alertDialog = new AlertDialog.Builder(activity).create();
        alertDialog.setTitle("Error");
        alertDialog.setMessage(activity.getResources().getString(R.string.string_err_dialog));
        alertDialog.setButton(AlertDialog.BUTTON_NEUTRAL, "OK",
                new DialogInterface.OnClickListener() {
                    @TargetApi(Build.VERSION_CODES.JELLY_BEAN)
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                        activity.finishAffinity();
                    }
                });
        alertDialog.show();
    }
}

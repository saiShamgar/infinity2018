package com.example.sss.infinity.helpers;

import android.app.Activity;
import android.app.Dialog;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.TextView;

import com.example.sss.infinity.R;

public class ViewDialog {

    public void showDialog(Activity activity, String msg,String number){
        final Dialog dialog = new Dialog(activity);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setCancelable(false);
        dialog.setContentView(R.layout.custom_dialog_box);



        TextView text = (TextView) dialog.findViewById(R.id.text_dialog);
        TextView num = (TextView) dialog.findViewById(R.id.a);
        text.setText(msg);
        num.setText(number);

        Button dialogButton = (Button) dialog.findViewById(R.id.btn_dialog);
        dialogButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v)
            {
                dialog.dismiss();
            }
        });

        dialog.show();

    }
}
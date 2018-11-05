package com.example.sss.infinity.helpers;

import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;

public class AlertMsgBox
{
    Context mcontext;
    private ProgressDialog progressDialog;

    public AlertMsgBox(Context mcontext)
    {
        this.mcontext=mcontext;
    }

    public void showProgressDialog()
    {
        progressDialog = new ProgressDialog(mcontext);
        progressDialog.setMessage("loading");
        progressDialog.show();
        progressDialog.setCancelable(false);
        progressDialog.setCanceledOnTouchOutside(false);
    }

    public void hideProgressDialog()
    {
        if (progressDialog != null)
            progressDialog.dismiss();
    }

    public void alertMsgBox(String message)
    {
        new AlertDialog.Builder(mcontext)
                .setTitle(message)
                .setMessage(null)
                .setPositiveButton(android.R.string.yes, new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which)
                    {

                    }
                })
                .show();
                }
}


package com.co.widetech.serial_port_core.tools;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.os.Bundle;
import com.co.widetech.serial_port_core.R;

public class AlertDialogActivity extends Activity {
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.popup_dialog);

        Bundle extras = getIntent().getExtras();

        String title = extras.getString("title");
        String message = extras.getString("message");

        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle(title);
        builder.setMessage(message);
        builder.setIcon(android.R.drawable.ic_dialog_alert);
        builder.setCancelable(false).setPositiveButton("Ok",
                new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        AlertDialogActivity.this.finish();
                    }
                });
        AlertDialog alertdialog = builder.create();
        alertdialog.show();
    }
}

package utils;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.text.InputType;
import android.widget.EditText;

/**
 * Created by wtjramirez on 3/30/15.
 */
public class AlertBuilder {

    public static AlertDialog buildGenericAlert(Context context, String tittle, String message, DialogInterface.OnClickListener onClickListener) {
        AlertDialog.Builder builder = new AlertDialog.Builder(context);
        builder.setTitle(tittle);
        builder.setMessage(message);
        builder.setCancelable(false);
        builder.setPositiveButton("Aceptar", onClickListener);
        AlertDialog alert = builder.create();
        return alert;
    }

    public static AlertDialog buildAlertNotificatonNewConfiguration(final Context context, DialogInterface.OnClickListener onClickListener, EditText input) {
        AlertDialog.Builder builder = new AlertDialog.Builder(context);
        builder.setTitle("Clave para Configuraciones");
        input.setInputType(InputType.TYPE_CLASS_TEXT);
        builder.setView(input);
        builder.setCancelable(false);

        builder.setPositiveButton("Aceptar", onClickListener);
        builder.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
            }
        });
        AlertDialog alert = builder.create();
        return alert;
    }
}

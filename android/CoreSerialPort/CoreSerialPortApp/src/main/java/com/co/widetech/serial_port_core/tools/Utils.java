package com.co.widetech.serial_port_core.tools;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Bitmap.Config;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.PorterDuff.Mode;
import android.graphics.PorterDuffXfermode;
import android.graphics.Rect;
import android.graphics.RectF;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;

public class Utils {

	public static String formatStartUnit(String Unit, Context context) {

		String type_unit = null;
		if (Unit.equalsIgnoreCase("Enfora UDP")) {
			type_unit = "AT$MSGSND=2,\">";
		}
		if (Unit.equalsIgnoreCase("Enfora TCP")) {
			type_unit = "AT$MSGSND=4,\">";
		}
		if (Unit.equalsIgnoreCase("Cellocator")) {
			type_unit = ">";
		}
		if (Unit.equalsIgnoreCase("Skywave")) {
			type_unit = ">";
		}
		return type_unit;
	}

	public static String formatEndUnit(String Unit, Context context) {
		String type_unit = null;
		if (Unit.equalsIgnoreCase("Enfora UDP")) {
			type_unit = ("\"<");
		}
		if (Unit.equalsIgnoreCase("Enfora TCP")) {
			type_unit = ("\"<");
		}
		if (Unit.equalsIgnoreCase("Cellocator")) {
			type_unit = "<";
		}
		if (Unit.equalsIgnoreCase("Skywave")) {
			type_unit = "<";
		}
		return type_unit;
	}

	// primer comando que envia al prendel el dispositivo
	public static String StartApplication(String Unit) {
		String type_unit = null;
		if (Unit.equalsIgnoreCase("Enfora UDP"))
			type_unit = ("AT$EVTEST=49,");
		else if (Unit.equalsIgnoreCase("Enfora TCP"))
			type_unit = ("AT$EVTEST=49,");
		else if (Unit.equalsIgnoreCase("Cellocator"))
			type_unit = ",";
		else if (Unit.equalsIgnoreCase("Skywave")) {
			type_unit = ",";
		}
		return type_unit;
	}

    // tiempo de reporte
    public static long timerApplication(String Unit) {
        long type_unit = 0;
        if (Unit.equalsIgnoreCase("Enfora UDP"))
            type_unit = 15;
        else if (Unit.equalsIgnoreCase("Enfora TCP"))
            type_unit = 15;
        else if (Unit.equalsIgnoreCase("Cellocator"))
            type_unit = 15;
        else if (Unit.equalsIgnoreCase("Skywave")) {
            type_unit = 120;
        }
        return type_unit;
    }

	public static Bitmap getRoundedCornerBitmap(Drawable drawable,
			boolean square) {
		int width = 0;
		int height = 0;

		Bitmap bitmap = ((BitmapDrawable) drawable).getBitmap();

		if (square) {
			if (bitmap.getWidth() < bitmap.getHeight()) {
				width = bitmap.getWidth();
				height = bitmap.getWidth();
			} else {
				width = bitmap.getHeight();
				height = bitmap.getHeight();
			}
		} else {
			height = bitmap.getHeight();
			width = bitmap.getWidth();
		}

		Bitmap output = Bitmap.createBitmap(width, height, Config.ARGB_8888);
		Canvas canvas = new Canvas(output);

		final int color = 0xff000000;
		final Paint paint = new Paint();
		final Rect rect = new Rect(0, 0, width, height);
		final RectF rectF = new RectF(rect);
		final float roundPx = width / 2;

		paint.setAntiAlias(true);
		canvas.drawARGB(0, 0, 0, 0);
		paint.setColor(color);
		canvas.drawRoundRect(rectF, roundPx, roundPx, paint);

		paint.setXfermode(new PorterDuffXfermode(Mode.SRC_IN));
		canvas.drawBitmap(bitmap, rect, rect, paint);

		return output;
	}
}

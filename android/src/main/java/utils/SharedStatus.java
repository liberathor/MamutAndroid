package utils;

import android.content.Context;
import android.content.SharedPreferences;

public class SharedStatus {

    private SharedPreferences sPrefCodeUser;
    private SharedPreferences sPrefNameUser;
    private SharedPreferences sPrefInfoDevice;
    private SharedPreferences sPrefStatusTravel;
    private Context context;

    public SharedStatus(Context context) {
        this.context = context;

        this.sPrefCodeUser = context.getSharedPreferences("AccessCodeUser",
                Context.MODE_PRIVATE);
        this.sPrefCodeUser.getString("codeUser", "");

        this.sPrefInfoDevice = context.getSharedPreferences("InfoDevice",
                Context.MODE_PRIVATE);
        this.sPrefInfoDevice.getBoolean("valueInfoDevice", false);

        this.sPrefNameUser = context.getSharedPreferences("AccessNameUser",
                Context.MODE_PRIVATE);
        this.sPrefNameUser.getString("nameUser", "");

        this.sPrefStatusTravel = context.getSharedPreferences("StatusTravel",
                Context.MODE_PRIVATE);
        this.sPrefStatusTravel.getString("btnStatusTravel", "");
    }

    public SharedPreferences getCodeUser() {
        return sPrefCodeUser;
    }

    public void setCodeUser(String value) {
        this.sPrefCodeUser = context.getSharedPreferences("AccessCodeUser",
                Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sPrefCodeUser.edit();
        editor.putString("codeUser", value);
        editor.commit();
    }

    public SharedPreferences getsPrefNameUser() {
        return sPrefNameUser;
    }

    public void setsPrefNameUser(String value) {
        this.sPrefNameUser = context.getSharedPreferences("AccessNameUser",
                Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sPrefNameUser.edit();
        editor.putString("nameUser", value);
        editor.commit();
    }

    public SharedPreferences getInfoDevice() {
        return sPrefInfoDevice;
    }

    public void setInfoDevice(boolean value) {
        this.sPrefInfoDevice = context.getSharedPreferences("InfoDevice",
                Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sPrefInfoDevice.edit();
        editor.putBoolean("valueInfoDevice", value);
        editor.commit();
    }

    public SharedPreferences getStatusTravel() {
        return sPrefStatusTravel;
    }

    public void setStatusTravel(String value) {
        this.sPrefStatusTravel = context.getSharedPreferences("StatusTravel",
                Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sPrefStatusTravel.edit();
        editor.putString("btnStatusTravel", value);
        editor.commit();
    }

}

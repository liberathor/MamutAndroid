/*
 * Copyright 2009 Cedric Priscal
 * 
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 * 
 * http://www.apache.org/licenses/LICENSE-2.0
 * 
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License. 
 */

package com.co.widetech.serial_port_core.tools;

import android.annotation.TargetApi;
import android.content.Intent;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.preference.ListPreference;
import android.preference.Preference;
import android.preference.Preference.OnPreferenceChangeListener;
import android.preference.Preference.OnPreferenceClickListener;
import android.preference.PreferenceActivity;
import android.serialport.SerialPortFinder;
import com.co.widetech.serial_port_core.R;

public class SerialPortPreferences extends PreferenceActivity {

    public SerialPortFinder mSerialPortFinder;
    boolean mBound = false;
    String device;
    String baudrate;
    String unityType;
    String timeReport;
    String myKillApp;

    @SuppressWarnings("deprecation")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        mSerialPortFinder = new SerialPortFinder();
        addPreferencesFromResource(R.xml.option_preferences);

        device = getResources().getString(R.string.option_device);
        baudrate = getResources().getString(R.string.option_baudrate);
        unityType = getResources().getString(R.string.option_unity_type);
        timeReport = getResources().getString(R.string.option_time_report);
        myKillApp = getResources().getString(R.string.option_kill_app);

        loadPreferences();
    }

    @SuppressWarnings("deprecation")
    private void loadPreferences() {
        // Devices
        final ListPreference devices = (ListPreference) findPreference(device);
        String[] entries = mSerialPortFinder.getAllDevices();
        String[] entryValues = mSerialPortFinder.getAllDevicesPath();
        devices.setEntries(entries);
        devices.setEntryValues(entryValues);
        devices.setSummary(devices.getValue());
        devices.setOnPreferenceChangeListener(new OnPreferenceChangeListener() {
            public boolean onPreferenceChange(Preference preference,
                                              Object newValue) {
                preference.setSummary((String) newValue);
                return true;
            }
        });

        // Baud rates
        final ListPreference baudrates = (ListPreference) findPreference(baudrate);
        baudrates.setSummary(baudrates.getValue());
        baudrates
                .setOnPreferenceChangeListener(new OnPreferenceChangeListener() {
                    public boolean onPreferenceChange(Preference preference,
                                                      Object newValue) {
                        preference.setSummary((String) newValue);
                        return true;
                    }
                });

        // unity Types
        final ListPreference unityTypes = (ListPreference) findPreference(unityType);
        unityTypes.setSummary(unityTypes.getValue());
        unityTypes
                .setOnPreferenceChangeListener(new OnPreferenceChangeListener() {
                    public boolean onPreferenceChange(Preference preference,
                                                      Object newValue) {
                        preference.setSummary((String) newValue);
                        return true;
                    }
                });

        // time Report
        final ListPreference timeReports = (ListPreference) findPreference(timeReport);
        timeReports.setSummary(timeReports.getValue());
        timeReports
                .setOnPreferenceChangeListener(new OnPreferenceChangeListener() {
                    public boolean onPreferenceChange(Preference preference,
                                                      Object newValue) {
                        preference.setSummary((String) newValue);
                        return true;
                    }
                });

        Preference myPrefStopApp = findPreference(myKillApp);
        myPrefStopApp
                .setOnPreferenceClickListener(new OnPreferenceClickListener() {
                    public boolean onPreferenceClick(Preference preference) {
                        forceStopPackage(getPackageName());
                        return true;
                    }
                });
    }

    @TargetApi(Build.VERSION_CODES.GINGERBREAD)
    private void forceStopPackage(String packageName) {
        startActivity(new Intent(
                android.provider.Settings.ACTION_APPLICATION_DETAILS_SETTINGS,
                Uri.parse("package:" + packageName)));
    }

    protected void onDestroy() {
        super.onDestroy();
    }

}

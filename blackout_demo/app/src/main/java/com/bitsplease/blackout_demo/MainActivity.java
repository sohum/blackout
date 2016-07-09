package com.bitsplease.blackout_demo;

import android.Manifest;
import android.content.ContentResolver;
import android.content.Context;
import android.content.Intent;
import android.content.BroadcastReceiver;
import android.content.IntentFilter;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.net.Uri;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import com.facebook.FacebookSdk;
import com.facebook.appevents.AppEventsLogger;
import com.shawnlin.numberpicker.NumberPicker;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;

public class MainActivity extends AppCompatActivity {

    public final static int SMS_PERMISSION = 123;

    private BroadcastReceiver receiver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            Bundle bundle = intent.getExtras();
            if (bundle != null) {

            }
        }
    };

@Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

    }
    /** Called when the user clicks the Send button */
    public void sendMessage(View view) {

        if (ContextCompat.checkSelfPermission(this,
                Manifest.permission.READ_SMS)
                != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(this,
                    new String[]{Manifest.permission.READ_SMS},
                    SMS_PERMISSION);
        }
        else
        {
            startNewActivity();
        }
    }

    public void startNewActivity(){

        NumberPicker numberPicker = (NumberPicker) findViewById(R.id.number_picker);
        int value = numberPicker.getValue();
        List<DisplayObject> smsInRange = getSMS(value);

        Intent intent = new Intent(this, FacebookService.class);
        // add infos for the service which file to download and where to store
        intent.setAction("com.bitsplease.blackout_demo.action.FACEBOOK");
        startService(intent);
        //Intent intent = new Intent(this, TimelineActivity.class);
        //intent.putExtra("DisplayList",(Serializable)smsInRange);
        //startActivity(intent);
    }

    @Override
    protected void onResume() {
        super.onResume();
        registerReceiver(receiver, new IntentFilter(
                FacebookService.NOTIFICATION));
    }
    @Override
    protected void onPause() {
        super.onPause();
        unregisterReceiver(receiver);
    }

    /**Method to get all sms in a time range */
    public List<DisplayObject> getSMS(int value){

        List<DisplayObject> smsList = new ArrayList<DisplayObject>();
        Uri message = Uri.parse("content://sms/inbox");
        ContentResolver cr = getContentResolver();
        long currentTime = System.currentTimeMillis();
        long range = TimeUnit.HOURS.toMillis(value);

        Cursor c = cr.query(message, null, "date >= ?", new String[] { Long.toString(currentTime - range)}, null);
        int totalSMS = c.getCount();

        if (c.moveToFirst()) {
            for (int i = 0; i < totalSMS; i++) {
                DisplayObject sms = new DisplayObject("SMS",
                c.getString(c.getColumnIndexOrThrow("date")),c.getString(c.getColumnIndexOrThrow("body")), R.drawable.sms);
                smsList.add(sms);
                c.moveToNext();
            }
        }
        c.close();
        return smsList;
    }

    @Override
    public void onRequestPermissionsResult(int requestCode,
                                           String permissions[], int[] grantResults) {
        switch (requestCode) {
            case SMS_PERMISSION: {
                // If request is cancelled, the result arrays are empty.
                if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                   startNewActivity();
                } else {

                    // permission denied, boo! Disable the
                    // functionality that depends on this permission.
                }
                return;
            }
        }
    }

}

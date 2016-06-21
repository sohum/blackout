package com.bitsplease.blackout_demo;

import android.Manifest;
import android.app.ListActivity;
import android.content.ContentResolver;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.net.Uri;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import com.facebook.FacebookSdk;
import com.facebook.appevents.AppEventsLogger;
import com.shawnlin.numberpicker.NumberPicker;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        FacebookSdk.sdkInitialize(getApplicationContext());
        AppEventsLogger.activateApp(this);
    }
    /** Called when the user clicks the Send button */
    public void sendMessage(View view) {

        if (ContextCompat.checkSelfPermission(this,
                Manifest.permission.READ_SMS)
                != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(this,
                    new String[]{Manifest.permission.READ_SMS},
                    123);
        }
        else
        {
            NumberPicker numberPicker = (NumberPicker) findViewById(R.id.number_picker);
            int value = numberPicker.getValue();
            List<DisplayObject> smsInRange = getSMS();
        }


    }

    /**Method to get all sms in a time range */
    public List<DisplayObject> getSMS(){

        List<DisplayObject> smsList = new ArrayList<DisplayObject>();
        Uri message = Uri.parse("content://sms/inbox");
        ContentResolver cr = getContentResolver();
        long currentTime = System.currentTimeMillis();
        long range = TimeUnit.HOURS.toMillis(6);

        Cursor c = cr.query(message, null, "date >= ?", new String[] { Long.toString(currentTime - range)}, null);
        int totalSMS = c.getCount();

        if (c.moveToFirst()) {
            for (int i = 0; i < totalSMS; i++) {
                DisplayObject sms = new DisplayObject(c.getString(c.getColumnIndexOrThrow("body")),
                "SMS",c.getString(c.getColumnIndexOrThrow("date")));
                smsList.add(sms);
                c.moveToNext();
            }
        }
         else {
         throw new RuntimeException("You have no SMS in Sent");
         }
        c.close();
        return smsList;
    }

    @Override
    public void onRequestPermissionsResult(int requestCode,
                                           String permissions[], int[] grantResults) {
        switch (requestCode) {
            case 123: {
                // If request is cancelled, the result arrays are empty.
                if (grantResults.length > 0
                        && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    List<DisplayObject> smsInRange = getSMS();
                } else {

                    // permission denied, boo! Disable the
                    // functionality that depends on this permission.
                }
                return;
            }

            // other 'case' lines to check for other
            // permissions this app might request
        }
    }

}

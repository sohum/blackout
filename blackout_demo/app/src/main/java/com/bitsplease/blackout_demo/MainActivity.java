package com.bitsplease.blackout_demo;

import android.Manifest;
import android.animation.TimeAnimator;
import android.content.ContentResolver;
import android.content.Context;
import android.content.Intent;
import android.content.BroadcastReceiver;
import android.content.IntentFilter;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.net.Uri;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Base64;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;

import com.shawnlin.numberpicker.NumberPicker;

import java.io.Serializable;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;

public class MainActivity extends AppCompatActivity {

    public final static int SMS_PERMISSION = 123;
    public int counter = 0;
    ServiceFacade facade;
    public int value = 0;

    private BroadcastReceiver receiver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            counter++;
         //   Bundle bundle = intent.getExtras();
         //   if (bundle != null) {

                if(counter < 2)
                {
                    //startSMS();
                   // List<DisplayObject> smsInRange = getSMS(value);
                   // Intent intentTimeLine = new Intent(MainActivity.this, TimelineActivity.class);
                   // intentTimeLine.putExtra("DisplayList",(Serializable)smsInRange);
                   // startActivity(intentTimeLine);
                }
                else
                {
                    //Please come here
                }

          //  }
        }
    };

    private BroadcastReceiver receiverSMS = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
               Bundle bundle = intent.getExtras();
               if (bundle != null) {
           //Pray to god this works
              }
        }
    };


@Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        facade = new ServiceFacade();

    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.main_activity_menu, menu);
        // Action View
        //MenuItem searchItem = menu.findItem(R.id.action_search);
        //SearchView searchView = (SearchView) MenuItemCompat.getActionView(searchItem);
        // Configure the search info and add any event listeners
        //return super.onCreateOptionsMenu(menu);
        return true;

    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item)
    {
        switch(item.getItemId())
        {
            case R.id.action_settings:
                Intent intent = new Intent(this, SettingsActivity.class);
                startActivity(intent);
        }
        return super.onOptionsItemSelected(item);
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
        value = numberPicker.getValue();

        ServiceFacade facade = new ServiceFacade();
        facade.startService(this, value);
    }

    @Override
    protected void onResume() {
        super.onResume();
        registerReceiver(receiver, new IntentFilter(
                FacebookService.NOTIFICATION));
        registerReceiver(receiverSMS,new IntentFilter(
                FacebookService.NOTIFICATION_SMS));
    }
    @Override
    protected void onPause() {
        super.onPause();
        unregisterReceiver(receiver);
        unregisterReceiver(receiverSMS);
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

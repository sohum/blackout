package com.bitsplease.blackout_demo;

import android.Manifest;
<<<<<<< HEAD
import android.animation.TimeAnimator;
import android.content.ContentResolver;
=======
>>>>>>> shomo
import android.content.Context;
import android.content.Intent;
import android.content.BroadcastReceiver;
import android.content.IntentFilter;
import android.content.pm.PackageManager;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
<<<<<<< HEAD
import android.util.Base64;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
=======
>>>>>>> shomo
import android.view.View;
import android.widget.Toast;

import com.shawnlin.numberpicker.NumberPicker;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;


public class MainActivity extends AppCompatActivity {

    public final static int SMS_PERMISSION = 123;
    public int counter = 0;
    public List<DisplayObject> finalList;


    private BroadcastReceiver receiver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            counter++;
            finalList.addAll((ArrayList<DisplayObject>)intent.getSerializableExtra("DisplayList"));
            if (finalList != null) {

                if(counter == 3)
                {
                    sendToDisplay();
                }
            }
        }
    };

    private BroadcastReceiver receiverSMS = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            counter++;
            finalList.addAll((ArrayList<DisplayObject>)intent.getSerializableExtra("DisplayList"));
            if (finalList != null) {

                if (counter == 3) {
                    sendToDisplay();
                }
            }
        }
    };

    private BroadcastReceiver receiverTwitter = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            counter++;
            finalList.addAll((ArrayList<DisplayObject>)intent.getSerializableExtra("DisplayList"));
            if (finalList != null) {

                if (counter == 3) {
                    sendToDisplay();
                }
            }
        }
    };


@Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        finalList = new ArrayList<>();

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

    private void sendToDisplay(){
        Intent intent = new Intent(this, TimelineActivity.class);
        intent.putExtra("DisplayList",(Serializable)finalList);
        startActivity(intent);
        finish();
    }

    public void startNewActivity(){

        NumberPicker numberPicker = (NumberPicker) findViewById(R.id.number_picker);
        int value = numberPicker.getValue();

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
        registerReceiver(receiverTwitter,new IntentFilter(
                FacebookService.NOTIFICATION_TWITTER));
    }
    @Override
    protected void onPause() {
        super.onPause();
        unregisterReceiver(receiver);
        unregisterReceiver(receiverSMS);
        unregisterReceiver(receiverTwitter);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
       // unregisterReceiver(receiver);
       // unregisterReceiver(receiverSMS);
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

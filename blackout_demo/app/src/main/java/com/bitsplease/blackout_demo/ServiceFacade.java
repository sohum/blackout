package com.bitsplease.blackout_demo;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;
import android.util.Log;

import java.util.Map;

/**
 * Created by shoumitrasrivastava on 2016-07-21.
 */
public class ServiceFacade {

    private static final String EXTRA_PARAM1 = "com.bitsplease.blackout_demo.extra.PARAM1";
    private static final String EXTRA_PARAM2 = "com.bitsplease.blackout_demo.extra.PARAM2";

    public ServiceFacade(){

    }

    public void startService(Context context, int hours){

        boolean fb = false;
        boolean twitter = false;
        boolean sms = false;


        SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(context);
        Map<String,?> keys = prefs.getAll();

        for(Map.Entry<String,?> entry : keys.entrySet())
        {
            if(entry.getKey().equals("facebook_switch")){
                fb = Boolean.parseBoolean(entry.getValue().toString());
            }
            if(entry.getKey().equals("twitter_switch")){
                twitter = Boolean.parseBoolean(entry.getValue().toString());;
            }
            if(entry.getKey().equals("sms_switch")){
                sms =Boolean.parseBoolean(entry.getValue().toString());;
            }

        }

        Intent intentFacebook = new Intent(context, FacebookService.class);
        intentFacebook.setAction("com.bitsplease.blackout_demo.action.FACEBOOK");
        intentFacebook.putExtra(EXTRA_PARAM2, fb);
        context.startService(intentFacebook);

        Intent intentSMS = new Intent(context, FacebookService.class);
        intentSMS.setAction("com.bitsplease.blackout_demo.action.SMS");
        intentSMS.putExtra(EXTRA_PARAM1, Integer.toString(hours));
        intentSMS.putExtra(EXTRA_PARAM2, sms);
        context.startService(intentSMS);

        Intent intentTwitter = new Intent(context, FacebookService.class);
        intentTwitter.setAction("com.bitsplease.blackout_demo.action.TWITTER");
        intentTwitter.putExtra(EXTRA_PARAM2, twitter);
        context.startService(intentTwitter);

    }

}

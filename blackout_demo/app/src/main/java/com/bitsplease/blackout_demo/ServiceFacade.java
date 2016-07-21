package com.bitsplease.blackout_demo;

import android.content.Context;
import android.content.Intent;

/**
 * Created by shoumitrasrivastava on 2016-07-21.
 */
public class ServiceFacade {

    private static final String EXTRA_PARAM1 = "com.bitsplease.blackout_demo.extra.PARAM1";

    public ServiceFacade(){

    }

    public void startFacebookService(Context context, int hours){

        Intent intentFacebook = new Intent(context, FacebookService.class);
        intentFacebook.setAction("com.bitsplease.blackout_demo.action.FACEBOOK");
        context.startService(intentFacebook);
    }

    public void startSMSService(Context context, int hours){
        Intent intentSMS = new Intent(context, FacebookService.class);
        intentSMS.setAction("com.bitsplease.blackout_demo.action.SMS");
        intentSMS.putExtra(EXTRA_PARAM1, Integer.toString(hours));
        context.startService(intentSMS);
    }
}

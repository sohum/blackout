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

    public void startService(Context context, int hours){

        Intent intentFacebook = new Intent(context, FacebookService.class);
        intentFacebook.setAction("com.bitsplease.blackout_demo.action.FACEBOOK");
        context.startService(intentFacebook);

        Intent intentSMS = new Intent(context, FacebookService.class);
        intentSMS.setAction("com.bitsplease.blackout_demo.action.SMS");
        intentSMS.putExtra(EXTRA_PARAM1, Integer.toString(hours));
        context.startService(intentSMS);

        Intent intentTwitter = new Intent(context, FacebookService.class);
        intentTwitter.setAction("com.bitsplease.blackout_demo.action.TWITTER");
        context.startService(intentTwitter);

    }

}

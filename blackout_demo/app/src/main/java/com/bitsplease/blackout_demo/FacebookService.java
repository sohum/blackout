package com.bitsplease.blackout_demo;

import android.app.IntentService;
import android.content.ContentResolver;
import android.content.Intent;
import android.content.Context;
import android.content.res.*;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;

import com.facebook.AccessToken;
import com.facebook.GraphRequest;
import com.facebook.GraphResponse;
import com.facebook.HttpMethod;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.concurrent.TimeUnit;

import twitter4j.*;
import twitter4j.conf.*;

/**
 * An {@link IntentService} subclass for handling asynchronous task requests in
 * a service on a separate handler thread.
 * <p/>
 * TODO: Customize class - update intent actions, extra parameters and static
 * helper methods.
 */
public class FacebookService extends IntentService {
    // TODO: Rename actions, choose action names that describe tasks that this
    // IntentService can perform, e.g. ACTION_FETCH_NEW_ITEMS
    private static final String ACTION_FOO = "com.bitsplease.blackout_demo.action.FOO";
    private static final String ACTION_SMS = "com.bitsplease.blackout_demo.action.SMS";
    private static final String ACTION_FACEBOOK = "com.bitsplease.blackout_demo.action.FACEBOOK";
    private static final String ACTION_TWITTER = "com.bitsplease.blackout_demo.action.TWITTER";
    public static final String NOTIFICATION = "com.bitsplease.blackout_demo.service.receiver";
    public static final String NOTIFICATION_SMS = "com.bitsplease.blackout_demo.service.receiver_sms";
    public static final String NOTIFICATION_TWITTER = "com.bitsplease.blackout_demo.service.receiver_twitter";


    // TODO: Rename parameters
    private static final String EXTRA_PARAM1 = "com.bitsplease.blackout_demo.extra.PARAM1";
    private static final String EXTRA_PARAM2 = "com.bitsplease.blackout_demo.extra.PARAM2";

    public FacebookService() {
        super("FacebookService");
    }

    /**
     * Starts this service to perform action Foo with the given parameters. If
     * the service is already performing a task this action will be queued.
     *
     * @see IntentService
     */
    // TODO: Customize helper method
    public static void startActionFoo(Context context, String param1, String param2) {
        Intent intent = new Intent(context, FacebookService.class);
        intent.setAction(ACTION_FOO);
        intent.putExtra(EXTRA_PARAM1, param1);
        intent.putExtra(EXTRA_PARAM2, param2);
        context.startService(intent);
    }

    @Override
    protected void onHandleIntent(Intent intent) {
        if (intent != null) {
            final String action = intent.getAction();
            if (ACTION_FOO.equals(action)) {
                final String param1 = intent.getStringExtra(EXTRA_PARAM1);
                final String param2 = intent.getStringExtra(EXTRA_PARAM2);
                handleActionFoo(param1, param2);
            } else if (ACTION_SMS.equals(action)) {
                final int hours = Integer.parseInt(intent.getStringExtra(EXTRA_PARAM1));
                final boolean flag = intent.getBooleanExtra(EXTRA_PARAM2, false);
                handleActionSMS(hours, flag);
            }
            else if (ACTION_FACEBOOK.equals(action)) {
                final boolean flag = intent.getBooleanExtra(EXTRA_PARAM2, false);
                handleActionFacebook(flag);
            }
            else if (ACTION_TWITTER.equals(action)) {
                final boolean flag = intent.getBooleanExtra(EXTRA_PARAM2, false);
                handleActionTwitter(flag);
            }
        }
    }

    private void handleActionFacebook(boolean flag) {

        if(flag) {
            Bundle params = new Bundle();
            params.putString("limit", "5");
            params.putString("since", "yesterday");

            new GraphRequest(
                    AccessToken.getCurrentAccessToken(),
                    "/me/feed",
                    params,
                    HttpMethod.GET,
                    new GraphRequest.Callback() {
                        public void onCompleted(GraphResponse response) {
                            JSONObject responseObject = response.getJSONObject();
                            try {
                                JSONArray responseArray = responseObject.getJSONArray("data");
                                publishResultsFacebook(responseArray);
                            } catch (JSONException e) {
                                Log.e("BlackOut", "unexpected JSON exception", e);
                            }
                        }
                    }
            ).executeAsync();
        }
        else {
            JSONArray emptyArray = new JSONArray();
            publishResultsFacebook(emptyArray);
        }
    }

    private void handleActionSMS(int hours, boolean flag) {
        List<DisplayObject> smsList = new ArrayList<DisplayObject>();

        if(flag) {
            Uri message = Uri.parse("content://sms/inbox");
            ContentResolver cr = getContentResolver();
            long currentTime = System.currentTimeMillis();
            long range = TimeUnit.HOURS.toMillis(hours);

            Cursor c = cr.query(message, null, "date >= ?", new String[]{Long.toString(currentTime - range)}, null);
            int totalSMS = c.getCount();

            if (c.moveToFirst()) {
                for (int i = 0; i < totalSMS; i++) {
                   // int millis = Integer.parseInt(c.getString(c.getColumnIndexOrThrow("date")));
                   // Date date = new Date(millis);
                    DisplayObject sms = new DisplayObject("SMS",
                            "Few hours ago", c.getString(c.getColumnIndexOrThrow("body")), R.drawable.sms);
                    smsList.add(sms);
                    c.moveToNext();
                }
            }
            c.close();
            publishResultsSMS(smsList);
        }
        else {
            publishResultsSMS(smsList);
        }
    }

    private void handleActionTwitter(boolean flag){

        ArrayList<Status> tweets = new ArrayList<Status>();

        if(flag) {
            ConfigurationBuilder cb = new ConfigurationBuilder();
            cb.setOAuthConsumerKey("dZ5TMmtr1jLMmyDhAbdnY7VnF");
            cb.setOAuthConsumerSecret("DcfOc1IOGNSM39K4QodHxdhiVKm7D5F34zt8EsVMMdyYKdmYX1");
            cb.setOAuthAccessToken("756324486224510977-r4JQ6UCA47YDm2O2C4FWVZ53VBwFzas");
            cb.setOAuthAccessTokenSecret("mzd8q3UfF8BL2XADZjmGegSrd2Ebdfagh0rWNIgsaAFO3");

            Twitter twitter = new TwitterFactory(cb.build()).getInstance();


            try {
                Paging page = new Paging(1, 20);
                tweets.addAll(twitter.getUserTimeline("Blackout_452", page));
                publishResultTwitter(tweets);
            } catch (TwitterException e) {
                e.printStackTrace();
            }
        }
        else {
            publishResultTwitter(tweets);
        }
    }

    private void publishResultTwitter(ArrayList<Status> tweets){

        List<DisplayObject> objectList = new ArrayList<DisplayObject>();

        for(int i=0; i< tweets.size(); i++){
            Status status = (Status)tweets.get(i);
            objectList.add(new DisplayObject("twitter", status.getCreatedAt().toString(), status.getText(),R.drawable.twitter));
        }

        Intent intent = new Intent(NOTIFICATION_TWITTER);
        intent.putExtra("DisplayList",(Serializable)objectList);
        sendBroadcast(intent);
    }

    private void publishResultsFacebook(JSONArray response) {

        List<DisplayObject> objectList = new ArrayList<DisplayObject>();

        for(int i= 0; i<response.length(); i++ )
        {
            try {
                JSONObject rec = response.getJSONObject(i);
                String message = rec.getString("message");
                String time = rec.getString("created_time");
                objectList.add(new DisplayObject("facebook",time,message,R.drawable.facebook));
            }
            catch (JSONException e) {
                Log.e("BlackOut", "unexpected JSON exception", e);
            }
        }

        Intent intent = new Intent(NOTIFICATION);
        intent.putExtra("DisplayList",(Serializable)objectList);
        sendBroadcast(intent);
    }

    private void publishResultsSMS(List<DisplayObject> smsInRange){

        Intent intent = new Intent(NOTIFICATION_SMS);
        intent.putExtra("DisplayList",(Serializable)smsInRange);
        sendBroadcast(intent);

    }

    /**
     * Handle action Foo in the provided background thread with the provided
     * parameters.
     */
    private void handleActionFoo(String param1, String param2) {
        // TODO: Handle action Foo
        throw new UnsupportedOperationException("Not yet implemented");
    }
}

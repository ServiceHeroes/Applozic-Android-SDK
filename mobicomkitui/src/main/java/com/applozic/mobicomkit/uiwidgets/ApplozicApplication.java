package com.applozic.mobicomkit.uiwidgets;

import android.app.Activity;
import android.app.Application;
import android.content.Context;
import android.content.Intent;
import android.support.v4.content.LocalBroadcastManager;
import com.applozic.mobicomkit.uiwidgets.conversation.activity.ConversationActivity;


/**
 * Created by devashish on 28/4/14.
 */
public class ApplozicApplication extends Application {

    public static final String TITLE = "Chats";
    public static Context context;

    @Override
    public void onCreate() {
        this.context = getApplicationContext();
        // workaround for http://code.google.com/p/android/issues/detail?id=20915
        try {
           /* if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.ICE_CREAM_SANDWICH) {
                registerActivityLifecycleCallbacks(new ActivityLifecycleHandler());
            }*/
            Class.forName("android.os.AsyncTask");
        } catch (ClassNotFoundException e) {
        }
        super.onCreate();
    }

    @Override
    protected void attachBaseContext(Context base) {
        super.attachBaseContext(base);
        // MultiDex.install(this);
    }

    public static void broadcastMessage(String actionName,
        ConversationActivity activity) {
        LocalBroadcastManager localBroadcastManager = LocalBroadcastManager
            .getInstance(ApplozicApplication.context);
        Intent localIntent = new Intent(actionName);
        localIntent.putExtra(actionName, activity);
        localBroadcastManager.sendBroadcast(localIntent);
    }
}
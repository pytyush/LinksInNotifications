package services;

import android.content.Intent;
import android.support.v4.content.LocalBroadcastManager;

import com.google.firebase.messaging.FirebaseMessagingService;
import com.google.firebase.messaging.RemoteMessage;
import common.Config;
import static common.Config.STR_KEY;

/**
 * Created by Pratyush_PR on 2/2/2018.
 */

public class MyFirebaseMessagingService extends FirebaseMessagingService {
    @Override
    public void onMessageReceived(RemoteMessage remoteMessage) {
        handleMessage(remoteMessage.getData().get(STR_KEY));
    }

    private void handleMessage(String message) {
        Intent pushNotification = new Intent(Config.STR_PUSH);
        pushNotification.putExtra(Config.STR_MESSAGE,message);
        LocalBroadcastManager.getInstance(this).sendBroadcast(pushNotification);
    }
}
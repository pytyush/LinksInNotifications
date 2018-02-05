package services;

import com.google.firebase.iid.FirebaseInstanceIdService;



import com.google.firebase.iid.FirebaseInstanceId;
import com.google.firebase.iid.FirebaseInstanceIdService;

/**
 * Created by Pratyush_PR on 1/31/2018.
 */

public class MyFirebaseIdService extends FirebaseInstanceIdService {
    @Override
    public void onTokenRefresh() {
        String token = FirebaseInstanceId.getInstance().getToken();
        SendToServer(token);
    }

    private void SendToServer(String token) {
        //do sthg you need wid token
    }
}
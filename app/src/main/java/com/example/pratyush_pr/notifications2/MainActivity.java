package com.example.pratyush_pr.notifications2;

import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.ProgressDialog;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.support.v4.app.NotificationCompat;
import android.support.v4.content.LocalBroadcastManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.webkit.WebChromeClient;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import common.Config;


public class MainActivity extends AppCompatActivity {

    private WebView webView;
    private ProgressDialog dialog;
    private BroadcastReceiver mRegisterBroadcastReceiver;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        webView = (WebView) findViewById(R.id.webView);
        webView.getSettings().setJavaScriptEnabled(true);
        webView.setWebChromeClient(new WebChromeClient());
        webView.setWebViewClient(new WebViewClient(){
            @Override
            public void onPageFinished(WebView view, String url) {
                if(dialog.isShowing())
                    dialog.dismiss();
            }
        });
        mRegisterBroadcastReceiver = new BroadcastReceiver() {
            @Override
            public void onReceive(Context context, Intent intent) {
                if(intent.getAction().equals(Config.STR_PUSH))
                {
                    String message = intent.getStringExtra(Config.STR_MESSAGE);
                    showNotification("Magical Methods",message);
                }
            }
        };

        onNewIntent(getIntent());
    }

    @Override
    protected void onNewIntent(Intent intent) {
        dialog = new ProgressDialog(this);
        if(intent.getStringExtra(Config.STR_KEY)!= null){
            dialog.show();
            dialog.setMessage("Loading,Please wait...");
            webView.loadUrl(intent.getStringExtra(Config.STR_KEY));
        }
    }

    private void showNotification(String title, String message) {
        Intent intent = new Intent(getBaseContext(),MainActivity.class);
        intent.putExtra(Config.STR_KEY,message);
        intent.setFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP);
        PendingIntent contentIntent = PendingIntent.getActivity(getBaseContext(),0,intent,PendingIntent.FLAG_UPDATE_CURRENT);
        NotificationCompat.Builder builder = new NotificationCompat.Builder(getBaseContext());
        builder.setAutoCancel(true)
                .setWhen(System.currentTimeMillis())
                .setDefaults(Notification.DEFAULT_ALL)
                .setSmallIcon(R.mipmap.ic_launcher_round)
                .setContentTitle(title)
                .setContentText(message)
                .setContentIntent(contentIntent);
        NotificationManager notificationManager = (NotificationManager) getBaseContext().getSystemService(Context.NOTIFICATION_SERVICE);
        notificationManager.notify(1,builder.build());

    }


    @Override
    protected void onPause() {
        LocalBroadcastManager.getInstance(this).unregisterReceiver(mRegisterBroadcastReceiver);
        super.onPause();
    }


    @Override
    protected void onPostResume() {
        super.onPostResume();
        LocalBroadcastManager.getInstance(this).registerReceiver(mRegisterBroadcastReceiver,new IntentFilter("registration complete"));
        LocalBroadcastManager.getInstance(this).registerReceiver(mRegisterBroadcastReceiver,new IntentFilter(Config.STR_PUSH));

    }
}
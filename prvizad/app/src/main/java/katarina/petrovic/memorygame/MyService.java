package katarina.petrovic.memorygame;

import android.app.Service;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.os.IBinder;
import android.util.Log;
import android.widget.Toast;

public class MyService extends Service {
    boolean run = true;
    final static String MY_ACTION = "MY_ACTION";
    public MyService() {
    }

    @Override
    public IBinder onBind(Intent intent) {
        // TODO: Return the communication channel to the service.
        throw new UnsupportedOperationException("Not yet implemented");
    }

    @Override
    public void onCreate() {
        super.onCreate();
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        new Thread(new Runnable() {
            @Override
            public void run() {
                while (run){
                    try {
                        Thread.sleep(15000);
                        //Thread.sleep(30000);

                        Intent intentt = new Intent();
                        intentt.setAction(MY_ACTION);
                        intentt.putExtra("DATA_PASSED",5);
                        sendBroadcast(intentt);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
            }
        }).start();
        return START_STICKY;
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        Log.d("SERVICE","SERVICE IS DESTROYED!");
        run = false;
    }
}
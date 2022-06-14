package katarina.petrovic.memorygame;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.NotificationCompat;
import androidx.core.app.NotificationManagerCompat;

import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.graphics.Color;
import android.graphics.PorterDuff;
import android.os.Build;
import android.os.Bundle;
import android.os.Parcelable;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Iterator;
import java.util.List;

public class StatisticActivity extends AppCompatActivity {
    MemoryDBHelper db;
    private Integer[] points=new Integer[50];
    private String[] UserNew=new String[50];
    private Button refresh;
    //static String BASE_URL = "http://192.168.0.14:3000";
    //static String BASE_URL = "http://172.20.10.4:3000"; //mobile
    static String BASE_URL = "http://192.168.58.245:3000";
    static String GET_ALL_GAMES = BASE_URL + "/score";
    private HttpHelper httpHelper;
    private ActivityAdapter adapter;
    MyReceiver myReceiver;
    Toast toast;
    private static final int NOTIFICATION_ID = 0;


    @Override
    protected void onStop() {
        // TODO Auto-generated method stub
        unregisterReceiver(myReceiver);
        super.onStop();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_statistic);
        refresh = findViewById(R.id.refresh);
        httpHelper = new HttpHelper();

        createNotificationChannel();
        //...START SERVICE...
        //STOP -> line: 160
        myReceiver = new MyReceiver();
        IntentFilter intentFilter = new IntentFilter();
        intentFilter.addAction(MyService.MY_ACTION);
        registerReceiver(myReceiver,intentFilter);
        Intent intentt = new Intent(StatisticActivity.this,MyService.class);
        startService(intentt);
        Toast.makeText(StatisticActivity.this,"SERVICE IS STARTED!",Toast.LENGTH_LONG).show();


        ListView list=findViewById(R.id.lista);
        adapter=new ActivityAdapter(this);
        list.setAdapter(adapter);

        db=new MemoryDBHelper(StatisticActivity.this);
        Model[] m = db.readModel ();
        String userr = getIntent ().getStringExtra ("username");

        if(m != null) {
            for (int i = 0; i < m.length; i++) {
                Model M = new Model(m[i].getUsername (), bestResult (m[i].getUsername ()),m[i].getEmail(), worstResult (m[i].getUsername ()));
                if(userr.equals(M.getUsername()))
                    M.setButtonEnable();
                adapter.addElement(M);
            }
        }
        findViewById (R.id.refresh).setOnClickListener(new View.OnClickListener () {
            @Override
            public void onClick(View view) {
                db.deleteWholeLocalBase();
                adapter.clearAdapter();
                new Thread(new Runnable()
                {
                    public void run()
                    {
                        try
                        {
                            JSONArray jsonArray = httpHelper.getJSONArrayFromURL(GET_ALL_GAMES);
                            if (jsonArray == null) {
                                runOnUiThread (new Runnable () {
                                    @Override
                                    public void run() {
                                        Toast.makeText(StatisticActivity.this, "ERROR", Toast.LENGTH_LONG).show();
                                    }
                                });
                            } else {
                                for (int i = 0; i < jsonArray.length(); i++) {
                                    JSONObject jsonObject = jsonArray.getJSONObject(i);
                                    String user = jsonObject.getString("username");
                                    int score = jsonObject.getInt("score");
                                    db.insert(user,user+"@gmail.com",score);
                                }
                            }
                            runOnUiThread(new Runnable() {
                                public void run() {
                                    Model[] model=db.readModel();
                                    String userr = getIntent ().getStringExtra ("username");
                                    Toast.makeText(StatisticActivity.this,"REFRESH",Toast.LENGTH_SHORT).show();

                                    if(model != null){ //...if not empty...
                                        for(int i=0;i<model.length;i++){
                                            int max=bestResult(model[i].getUsername());
                                            int min=worstResult(model[i].getUsername());

                                            Model M = new Model(model[i].getUsername(),max,model[i].getEmail(),min);
                                            if(userr.equals(M.getUsername()))
                                                M.setButtonEnable();
                                            adapter.addElement(M);
                                        }
                                    }
                                }
                            });
                        } catch (IOException e)
                        {
                            e.printStackTrace();
                        } catch (JSONException e)
                        {
                            e.printStackTrace();
                        }
                    }
                }).start();
            }
        });



        list.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                Intent intent=new Intent(StatisticActivity.this,DetailsActivity.class);
                Model element=(Model) adapter.getItem(i);
                intent.putExtra("user",element.getUsername()); //...forwarding user...

                //...STOP SERVICE...
                stopService(intentt);

                toast = Toast.makeText(StatisticActivity.this,"SERVICE IS TERMINATED!",Toast.LENGTH_LONG);
                view=toast.getView();
                view.getBackground().setColorFilter(Color.RED, PorterDuff.Mode.SRC_IN);
                toast.show();
                startActivity(intent);
            }
        });
    }
    //...ASC...
    public Integer bestResult(String username){
        int max;
        points=db.readResultForPlayer(username);
        max=points[points.length-1];
        return max;
    }
    public Integer worstResult(String username){
        int min;
        points=db.readResultForPlayer(username);
        min=points[0];
        return min;
    }

    private class MyReceiver extends BroadcastReceiver {

        @Override
        public void onReceive(Context arg0, Intent arg1) {
            // TODO Auto-generated method stub

            if(arg1.getAction().equals(MyService.MY_ACTION))
            {
                int datapassed = arg1.getIntExtra("DATA_PASSED", -1);
                refresh.performClick();
                NotificationCompat.Builder builder = new NotificationCompat.Builder(StatisticActivity.this, "CHANNEL_ID")
                        .setSmallIcon(R.drawable.refresh)
                        .setContentTitle("List is REFRESHED")
                        .setContentText("Refreshed content appeared on the screen!")
                        .setStyle(new NotificationCompat.BigTextStyle()
                                .bigText("Refreshed content appeared on the screen!"))
                        .setPriority(NotificationCompat.PRIORITY_DEFAULT)
                        .setAutoCancel(true);

                NotificationManagerCompat managerCompat = NotificationManagerCompat.from(StatisticActivity.this);
                managerCompat.notify(1,builder.build());
            }
        }

    }
    private void createNotificationChannel() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            CharSequence name = "ime";
            String description = "description";
            int importance = NotificationManager.IMPORTANCE_DEFAULT;
            NotificationChannel channel = new NotificationChannel("CHANNEL_ID", name, importance);
            channel.setDescription(description);
            NotificationManager notificationManager = getSystemService(NotificationManager.class);
            notificationManager.createNotificationChannel(channel);
        }
    }
}
package katarina.petrovic.memorygame;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.ArrayList;

public class DetailsActivity extends AppCompatActivity {
    private TextView tv;
    private ListView lista;
    private HttpHelper httpHelper;
    //static String BASE_URL = "http://192.168.0.14:3000";
    //static String BASE_URL = "http://172.20.10.4:3000"; //mobile
    static String BASE_URL = "http://192.168.58.245:3000";
    static String GET_ALL = BASE_URL + "/score/?username=";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_details);

        tv=findViewById(R.id.valUser);
        String username =getIntent().getExtras().getString("user");
        tv.setText(username);
        lista=findViewById(R.id.lista1);

        httpHelper = new HttpHelper ();


        ArrayAdapter<String>adapter=new ArrayAdapter<>(this, android.R.layout.simple_list_item_1);
        lista.setAdapter (adapter);
        new Thread (new Runnable () {
            @Override
            public void run() {
                try {
                    JSONArray jsn = httpHelper.getJSONArrayFromURL (GET_ALL + username);
                    if(jsn == null){
                        runOnUiThread (new Runnable () {
                            @Override
                            public void run() {
                                adapter.clear();
                                Toast.makeText (DetailsActivity.this, "ERROR", Toast.LENGTH_LONG).show ();
                            }
                        });
                    }else{
                        for(int j = 0; j < jsn.length (); j++) {
                            JSONObject jsnO = jsn.getJSONObject (j);
                            int poeni = jsnO.getInt ("score");
                            runOnUiThread (new Runnable () {
                                @Override
                                public void run() {
                                    String points = String.valueOf (poeni);
                                    adapter.add(points);
                                }
                            });
                        }
                    }
                } catch (IOException e) {
                    e.printStackTrace ();
                } catch (JSONException e) {
                    e.printStackTrace ();
                }
            }
        }).start ();
    }
}
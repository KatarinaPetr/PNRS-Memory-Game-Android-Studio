package katarina.petrovic.memorygame;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.os.Bundle;
import android.text.Editable;
import android.text.method.PasswordTransformationMethod;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;

public class MainActivity extends AppCompatActivity implements View.OnClickListener{

    private Button dugme0;
    private EditText ed;
    private EditText username;
    private Button reg;
    private String usernm;
    private String password;
    private HttpHelper httpHelper;
    //static String BASE_URL = "http://192.168.0.14:3000";
    //static String BASE_URL = "http://172.20.10.4:3000"; //mobile
    static String BASE_URL = "http://192.168.58.245:3000";
    static String LOGIN = BASE_URL+"/auth/signin";
    boolean registered = true;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        httpHelper = new HttpHelper();

        //login dugme
        dugme0=findViewById(R.id.tekst3);
        dugme0.setOnClickListener(this);

        //register
        reg=findViewById(R.id.reg0);
        reg.setOnClickListener(this);

        //sakrij sifru...
        ed=findViewById(R.id.tekst2);
        ed.setTransformationMethod(PasswordTransformationMethod.getInstance());

        username=findViewById(R.id.tekst1);
    }

    @Override
    public void onClick(View view) {
        usernm = username.getText().toString();
        password = ed.getText().toString();
        if(view.getId()==R.id.tekst3){
            Log.d("LoginActivity","Button 0");
            //klikom na LOGIN prelazimo u GameActivity...

            /*
            if(username.equals("") || ed.equals("")) {
                Log.d("BUTTON", "Login failed, username or password missing");
            }else {
                startActivity(intent);
            }*/

            new Thread(new Runnable()
            {
                public void run()
                {
                    JSONObject jsonObject = new JSONObject();
                    try
                    {
                        jsonObject.put("username", usernm);
                        jsonObject.put("password",password);
                        final HttpHelper.HTTPResponse response = httpHelper.postJSONObjectFromURL(LOGIN, jsonObject);
                        runOnUiThread(new Runnable() {
                            public void run() {
                                if(response.code == httpHelper.SUCCESS){
                                    Intent intent_game=new Intent(MainActivity.this,GameActivity.class);
                                    intent_game.putExtra("user", String.valueOf(username.getText())); //prosledila username
                                    Toast.makeText(MainActivity.this,"LOGGED IN!", Toast.LENGTH_LONG).show();
                                    //...go back to login...
                                    startActivity(intent_game);
                                }else {
                                    Toast.makeText(MainActivity.this, "ERROR " + response.code + ": " +response.message, Toast.LENGTH_LONG).show();
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
        if(view.getId()==R.id.reg0){
            if (registered) {
                Log.d("RegisterActivity", "Button REGISTRATION");
                Intent intent = new Intent(MainActivity.this, RegisterActivity.class);
                startActivity(intent);
            }else{
                reg.setEnabled(false);
            }
            registered=false;
        }
    }
}
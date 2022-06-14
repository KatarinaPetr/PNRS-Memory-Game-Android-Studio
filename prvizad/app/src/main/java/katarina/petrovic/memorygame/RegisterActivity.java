package katarina.petrovic.memorygame;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.Color;
import android.graphics.PorterDuff;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;

public class RegisterActivity extends AppCompatActivity implements View.OnClickListener {
    private HttpHelper httpHelper;
    private String username,password,email;
    private Button register;
    private EditText user,pass,eml;
    Toast toast;
    //static String BASE_URL = "http://192.168.0.14:3000";
    //static String BASE_URL = "http://172.20.10.4:3000"; //mobile
    static String BASE_URL = "http://192.168.58.245:3000";
    static String REGISTER = BASE_URL + "/auth/signup";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        register = findViewById(R.id.reg1);
        register.setOnClickListener(this);

        user = findViewById(R.id.userReg);
        pass = findViewById(R.id.passReg);
        eml = findViewById(R.id.emlReg);
        httpHelper = new HttpHelper();

    }

    @Override
    public void onClick(View view) {
        username = user.getText().toString();
        password = pass.getText().toString();
        email = eml.getText().toString();

        if(view.getId()==R.id.reg1){
            //...input check...
            if(username.length() < 4 || password.length() < 5 || email.length() < 5) {
                toast=Toast.makeText(getApplicationContext(),"Username, password and email must have at least 5 characters!!!",Toast.LENGTH_SHORT);
                view=toast.getView();
                view.getBackground().setColorFilter(Color.RED, PorterDuff.Mode.SRC_IN);
                toast.show();
            }
            new Thread(new Runnable()
            {
                public void run()
                {
                    JSONObject jsonObject = new JSONObject();
                    try
                    {
                        jsonObject.put("username", username);
                        jsonObject.put("password",password);
                        jsonObject.put("email",email);
                        final HttpHelper.HTTPResponse response = httpHelper.postJSONObjectFromURL(REGISTER, jsonObject);
                        runOnUiThread(new Runnable() {
                            public void run() {
                                if(response.code == httpHelper.SUCCESS){
                                    Toast.makeText(RegisterActivity.this,"REGISTERED!", Toast.LENGTH_LONG).show();
                                    //...go back to login...
                                    Intent intent = new Intent(getApplicationContext(), MainActivity.class);
                                    intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                                    startActivity(intent);
                                } else {
                                    Toast.makeText(RegisterActivity.this, "ERROR " + response.code + ": " +response.message, Toast.LENGTH_LONG).show();
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
    }
}
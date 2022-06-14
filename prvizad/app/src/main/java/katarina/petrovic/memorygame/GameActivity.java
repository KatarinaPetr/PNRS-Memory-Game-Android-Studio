package katarina.petrovic.memorygame;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.graphics.PorterDuff;
import android.media.Image;
import android.os.Bundle;
import android.os.Handler;
import android.provider.MediaStore;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Toast;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Random;

public class GameActivity extends AppCompatActivity implements View.OnClickListener {

    private Button startDugme;
    private Button statisticDugme;
    int counter=0;
    boolean prviPut=true;
    boolean isti=false;
    ArrayList<Object> novaListaSlika=new ArrayList<>(Arrays.asList());
    ArrayList<Object> pomocna=new ArrayList<>(Arrays.asList());
    ArrayList<Integer> indeksi=new ArrayList<>(Arrays.asList());
    int poeni=0;
    int counterSlika=0;
    private String username;
    Toast toast;
    private View view;
    private HttpHelper httpHelper;
    private ArrayAdapter<String> mListAdapter;
    //static String BASE_URL = "http://192.168.0.14:3000";
    //static String BASE_URL = "http://172.20.10.4:3000"; //mobile
    static String BASE_URL = "http://192.168.58.245:3000";
    static String SCORE = BASE_URL+"/score";


    //svi dugmici...
    private Button dugme1;
    private Button dugme2;
    private Button dugme3;
    private Button dugme4;
    private Button dugme5;
    private Button dugme6;
    private Button dugme7;
    private Button dugme8;
    private Button dugme9;
    private Button dugme10;
    private Button dugme11;
    private Button dugme12;
    private Button dugme13;
    private Button dugme14;
    private Button dugme15;
    private Button dugme16;
    //svi imageView-ovi...
    private ImageView im1;
    private ImageView im2;
    private ImageView im3;
    private ImageView im4;
    private ImageView im5;
    private ImageView im6;
    private ImageView im7;
    private ImageView im8;
    private ImageView im9;
    private ImageView im10;
    private ImageView im11;
    private ImageView im12;
    private ImageView im13;
    private ImageView im14;
    private ImageView im15;
    private ImageView im16;

    ArrayList<Button> dugmici0=new ArrayList<>();
    ArrayList<Button> dugmici=new ArrayList<>();
    ArrayList<ImageView> imidziZaBrisanje=new ArrayList<>();
    ArrayList<ImageView> imidzi=new ArrayList<>();
    MemoryDBHelper db;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_game);
        username=getIntent().getExtras().getString("user");
        Log.d("GameActivity","USERNAME: "+username);

        db=new MemoryDBHelper(GameActivity.this); //helper
        db.deleteWholeLocalBase();
        httpHelper = new HttpHelper();

        //dva glavna dugmeta  START i STATISTIC...
        startDugme=findViewById(R.id.specdugme1);
        startDugme.setOnClickListener(this);

        statisticDugme=findViewById(R.id.specdugme2);
        statisticDugme.setOnClickListener(this);



        //ostalih 16 dugmica...
        dugme1=findViewById(R.id.dugme1);
        dugme1.setOnClickListener(this);
        dugme2=findViewById(R.id.dugme2);
        dugme2.setOnClickListener(this);
        dugme3=findViewById(R.id.dugme3);
        dugme3.setOnClickListener(this);
        dugme4=findViewById(R.id.dugme4);
        dugme4.setOnClickListener(this);
        dugme5=findViewById(R.id.dugme5);
        dugme5.setOnClickListener(this);
        dugme6=findViewById(R.id.dugme6);
        dugme6.setOnClickListener(this);
        dugme7=findViewById(R.id.dugme7);
        dugme7.setOnClickListener(this);
        dugme8=findViewById(R.id.dugme8);
        dugme8.setOnClickListener(this);
        dugme9=findViewById(R.id.dugme9);
        dugme9.setOnClickListener(this);
        dugme10=findViewById(R.id.dugme10);
        dugme10.setOnClickListener(this);
        dugme11=findViewById(R.id.dugme11);
        dugme11.setOnClickListener(this);
        dugme12=findViewById(R.id.dugme12);
        dugme12.setOnClickListener(this);
        dugme13=findViewById(R.id.dugme13);
        dugme13.setOnClickListener(this);
        dugme14=findViewById(R.id.dugme14);
        dugme14.setOnClickListener(this);
        dugme15=findViewById(R.id.dugme15);
        dugme15.setOnClickListener(this);
        dugme16=findViewById(R.id.dugme16);

        dugme16.setOnClickListener(this);
        dugmici0= new ArrayList<>(Arrays.asList(dugme1, dugme2, dugme3, dugme4, dugme5, dugme6, dugme7, dugme8, dugme9, dugme10, dugme11, dugme12, dugme13, dugme14, dugme15, dugme16));
        dugmici= new ArrayList<>(Arrays.asList(dugme1, dugme2, dugme3, dugme4, dugme5, dugme6, dugme7, dugme8, dugme9, dugme10, dugme11, dugme12, dugme13, dugme14, dugme15, dugme16));

        im1=findViewById(R.id.im1);
        im1.setVisibility(View.GONE);
        im2=findViewById(R.id.im2);
        im2.setVisibility(View.GONE);
        im3=findViewById(R.id.im3);
        im3.setVisibility(View.GONE);
        im4=findViewById(R.id.im4);
        im4.setVisibility(View.GONE);
        im5=findViewById(R.id.im5);
        im5.setVisibility(View.GONE);
        im6=findViewById(R.id.im6);
        im6.setVisibility(View.GONE);
        im7=findViewById(R.id.im7);
        im7.setVisibility(View.GONE);
        im8=findViewById(R.id.im8);
        im8.setVisibility(View.GONE);
        im9=findViewById(R.id.im9);
        im9.setVisibility(View.GONE);
        im10=findViewById(R.id.im10);
        im10.setVisibility(View.GONE);
        im11=findViewById(R.id.im11);
        im11.setVisibility(View.GONE);
        im12=findViewById(R.id.im12);
        im12.setVisibility(View.GONE);
        im13=findViewById(R.id.im13);
        im13.setVisibility(View.GONE);
        im14=findViewById(R.id.im14);
        im14.setVisibility(View.GONE);
        im15=findViewById(R.id.im15);
        im15.setVisibility(View.GONE);
        im16=findViewById(R.id.im16);
        im16.setVisibility(View.GONE);

        imidziZaBrisanje= new ArrayList<>(Arrays.asList(im1, im2, im3, im4, im5, im6, im7, im8, im9, im10, im11, im12, im13, im14, im15, im16));
        imidzi= new ArrayList<> (Arrays.asList(im1, im2, im3, im4, im5, im6, im7, im8, im9, im10, im11, im12, im13, im14, im15, im16));

        //na pocetku sve dugmice onemogucimo...
        sveDisable();
        odabirSlika();
    }

    @Override
    public void onClick(View view) {
        if(view.getId()==R.id.specdugme1){
            Log.d("GameActivity","Start pressed");
            startDugme.setBackgroundColor(Color.BLUE);
            startDugme.setText("Reset");
            Log.d("GameActivity",""+counterSlika);
            if(prviPut) {
                sveEnable();
                sveSlikeInvisible();
                staviSveNaVisible();
                prviPut=false;
            }else {
                if (counterSlika == 8) { //...the end...
                    for(int i=0;i<dugmici.size();i++) {
                        for(int j=novaListaSlika.size()-1;j>=0;j--){
                            novaListaSlika.remove(j);
                        }
                        //...reset of pictures...
                        odabirSlika();
                        dugmici0.add(dugmici.get(i));
                        sveSlikeInvisible();
                        dugmici.get(i).setVisibility(View.VISIBLE);
                        sveEnable();
                        imidziZaBrisanje.add(imidzi.get(i));
                    }
                    db.insert(username,getEmail(),poeni);
                    saveGame(username,poeni);//...FOR 4th PROJECT...
                    //...JNI...
                    JNIexample jni = new JNIexample();
                    double procenat = jni.racunajProcenat(poeni);
                    Log.d("GameActivity","Procenat iznosi:"+procenat+"%");
                    //...reset...
                    poeni=0;
                    counterSlika=0;
                }
                if(counter==1){//...quitting...
                    poeni=-1;
                    //...toast...
                    toast=Toast.makeText(getApplicationContext(),"Points: "+poeni,Toast.LENGTH_SHORT);
                    view=toast.getView();
                    view.getBackground().setColorFilter(Color.RED, PorterDuff.Mode.SRC_IN);
                    toast.show();
                    indeksi.remove(0);
                    counterSlika=0;
                    //...empty everything...
                    for(int i=dugmici0.size()-1;i>=0;i--){
                        dugmici0.remove(i);
                    }
                    for(int i=imidziZaBrisanje.size()-1;i>=0;i--){
                        imidziZaBrisanje.remove(i);
                    }
                    for(int i=novaListaSlika.size()-1;i>=0;i--){
                        novaListaSlika.remove(i);
                    }
                    //...reset of pictures...
                    odabirSlika();
                    for(int i=0;i<dugmici.size();i++) {
                        dugmici0.add(dugmici.get(i));
                        sveSlikeInvisible();
                        dugmici.get(i).setVisibility(View.VISIBLE);
                        sveEnable();
                        imidziZaBrisanje.add(imidzi.get(i));
                    }
                    db.insert(username,getEmail(),poeni);
                    saveGame(username,poeni); //...FOR 4th PROJECT...
                    counter=0;
                    for(int i=0;i<pomocna.size();i++){
                        pomocna.remove(i);
                    }
                }
            }
        }
        if(view.getId()==R.id.specdugme2){
            Log.d("GameActivity","Statistic Button");

            //klikom na STATISTIC prelazimo u StatisticActivity...
            Intent intent=new Intent(GameActivity.this,StatisticActivity.class);
            intent.putExtra("username",username);
            startActivity(intent);
        }
        switch (view.getId()){
            case R.id.dugme1:
                Log.d("GameActivity","Dugme1 pressed");
                pomocna.add(novaListaSlika.get(0));
                indeksi.add(0);
                proveraKlika();//counter 1
                dugme1.setVisibility(View.GONE);
                im1.setImageResource((Integer) novaListaSlika.get(0));
                im1.setVisibility(View.VISIBLE);
                break;
            case R.id.dugme2:
                Log.d("GameActivity","Dugme2 pressed");
                pomocna.add(novaListaSlika.get(1));
                indeksi.add(1);
                proveraKlika();
                dugme2.setVisibility(View.GONE);
                im2.setImageResource((Integer) novaListaSlika.get(1));
                im2.setVisibility(View.VISIBLE);
                break;
            case R.id.dugme3:
                Log.d("GameActivity","Dugme3 pressed");
                pomocna.add(novaListaSlika.get(2));
                indeksi.add(2);
                proveraKlika();
                dugme3.setVisibility(View.GONE);
                im3.setImageResource((Integer) novaListaSlika.get(2));
                im3.setVisibility(View.VISIBLE);
                break;
            case R.id.dugme4:
                Log.d("GameActivity","Dugme4 pressed");
                pomocna.add(novaListaSlika.get(3));
                indeksi.add(3);
                proveraKlika();
                dugme4.setVisibility(View.GONE);
                im4.setImageResource((Integer) novaListaSlika.get(3));
                im4.setVisibility(View.VISIBLE);
                break;
            case R.id.dugme5:
                Log.d("GameActivity","Dugme5 pressed");
                pomocna.add(novaListaSlika.get(4));
                indeksi.add(4);
                proveraKlika();
                dugme5.setVisibility(View.GONE);
                im5.setImageResource((Integer) novaListaSlika.get(4));
                im5.setVisibility(View.VISIBLE);
                break;
            case R.id.dugme6:
                Log.d("GameActivity","Dugme6 pressed");
                pomocna.add(novaListaSlika.get(5));
                indeksi.add(5);
                proveraKlika();
                dugme6.setVisibility(View.GONE);
                im6.setImageResource((Integer) novaListaSlika.get(5));
                im6.setVisibility(View.VISIBLE);
                break;
            case R.id.dugme7:
                Log.d("GameActivity","Dugme7 pressed");
                pomocna.add(novaListaSlika.get(6));
                indeksi.add(6);
                proveraKlika();
                dugme7.setVisibility(View.GONE);
                im7.setImageResource((Integer) novaListaSlika.get(6));
                im7.setVisibility(View.VISIBLE);
                break;
            case R.id.dugme8:
                Log.d("GameActivity","Dugme8 pressed");
                pomocna.add(novaListaSlika.get(7));
                indeksi.add(7);
                proveraKlika();
                dugme8.setVisibility(View.GONE);
                im8.setImageResource((Integer) novaListaSlika.get(7));
                im8.setVisibility(View.VISIBLE);
                break;
            case R.id.dugme9:
                Log.d("GameActivity","Dugme9 pressed");
                pomocna.add(novaListaSlika.get(8));
                indeksi.add(8);
                proveraKlika();
                dugme9.setVisibility(View.GONE);
                im9.setImageResource((Integer) novaListaSlika.get(8));
                im9.setVisibility(View.VISIBLE);
                break;
            case R.id.dugme10:
                Log.d("GameActivity","Dugme10 pressed");
                pomocna.add(novaListaSlika.get(9));
                indeksi.add(9);
                proveraKlika();
                dugme10.setVisibility(View.GONE);
                im10.setImageResource((Integer) novaListaSlika.get(9));
                im10.setVisibility(View.VISIBLE);
                break;
            case R.id.dugme11:
                Log.d("GameActivity","Dugme11 pressed");
                pomocna.add(novaListaSlika.get(10));
                indeksi.add(10);
                proveraKlika();
                dugme11.setVisibility(View.GONE);
                im11.setImageResource((Integer) novaListaSlika.get(10));
                im11.setVisibility(View.VISIBLE);
                break;
            case R.id.dugme12:
                Log.d("GameActivity","Dugme12 pressed");
                pomocna.add(novaListaSlika.get(11));
                indeksi.add(11);
                proveraKlika();
                dugme12.setVisibility(View.GONE);
                im12.setImageResource((Integer) novaListaSlika.get(11));
                im12.setVisibility(View.VISIBLE);
                break;
            case R.id.dugme13:
                Log.d("GameActivity","Dugme13 pressed");
                pomocna.add(novaListaSlika.get(12));
                indeksi.add(12);
                proveraKlika();
                dugme13.setVisibility(View.GONE);
                im13.setImageResource((Integer) novaListaSlika.get(12));
                im13.setVisibility(View.VISIBLE);
                break;
            case R.id.dugme14:
                Log.d("GameActivity","Dugme14 pressed");
                pomocna.add(novaListaSlika.get(13));
                indeksi.add(13);
                proveraKlika();
                dugme14.setVisibility(View.GONE);
                im14.setImageResource((Integer) novaListaSlika.get(13));
                im14.setVisibility(View.VISIBLE);
                break;
            case R.id.dugme15:
                Log.d("GameActivity","Dugme15 pressed");
                pomocna.add(novaListaSlika.get(14));
                indeksi.add(14);
                proveraKlika();
                dugme15.setVisibility(View.GONE);
                im15.setImageResource((Integer) novaListaSlika.get(14));
                im15.setVisibility(View.VISIBLE);
                break;
            case R.id.dugme16:
                Log.d("GameActivity","Dugme16 pressed");
                pomocna.add(novaListaSlika.get(15));
                indeksi.add(15);
                proveraKlika();
                dugme16.setVisibility(View.GONE);
                im16.setImageResource((Integer) novaListaSlika.get(15));
                im16.setVisibility(View.VISIBLE);
                break;
            default:
                Log.d("GameActivity","Default");
                break;
        }
    }

    //sve funkcije potrebne za preglednost...
    private void sveDisable(){
        for (Button button : Arrays.asList(dugme1, dugme2, dugme3, dugme4, dugme5, dugme6, dugme7, dugme8, dugme9, dugme10, dugme11, dugme12, dugme13, dugme14, dugme15, dugme16, statisticDugme)) {
            button.setEnabled(false);
        }
    }
    private void sveEnable(){
        for (Button button : Arrays.asList(dugme1, dugme2, dugme3, dugme4, dugme5, dugme6, dugme7, dugme8, dugme9, dugme10, dugme11, dugme12, dugme13, dugme14, dugme15, dugme16, statisticDugme)) {
            button.setEnabled(true);
        }
    }
    private void staviSveNaVisible(){
        for (Button button : Arrays.asList(dugme1, dugme2, dugme3, dugme4, dugme5, dugme6, dugme7, dugme8, dugme9, dugme10, dugme11, dugme12, dugme13, dugme14, dugme15, dugme16)) {
            button.setVisibility(View.VISIBLE);
        }
    }
    private void sveSlikeInvisible(){
        for (ImageView imageView : Arrays.asList(im1, im2, im3, im4, im5, im6, im7, im8, im9, im10, im11, im12, im13, im14, im15, im16)) {
            imageView.setVisibility(View.INVISIBLE);
        }
    }
    private void proveraKlika(){//...just if two...
        counter++;
        if(counter==2){
            proveriIdenticnost();
            provera();
            pomocna.remove(1);
            pomocna.remove(0);
            counter=0;
        }
    }
    private void proveriIdenticnost(){//...just to compare...
        if(pomocna.get(0).equals(pomocna.get(1))){
            Log.d("GameActivity","Iste su");
            isti=true;
        }else{
            Log.d("GameActivity","Razlicite su");
            isti=false;
        }
    }
    private void provera(){//...too complicated...
        if(isti){
            counterSlika++;//...if eight...
            poeni+=5;
            //...toast...
            toast=Toast.makeText(getApplicationContext(),"Points: "+poeni,Toast.LENGTH_SHORT);
            view=toast.getView();
            view.getBackground().setColorFilter(Color.GREEN, PorterDuff.Mode.SRC_IN);
            toast.show();

            for(int i=0;i<dugmici.size();i++){
                if(indeksi.get(0)==i || indeksi.get(1)==i) {
                    int index = getButtonPosition(dugmici.get(i));
                    dugmici.get(i).setEnabled(false);
                    dugmici.get(i).setVisibility(View.INVISIBLE);
                    imidzi.get(i).setVisibility(View.VISIBLE);

                    imidziZaBrisanje.remove(index);
                    dugmici0.remove(index);

                    for (int j = 0; j < dugmici0.size(); j++) {
                        dugmici0.get(j).setVisibility(View.VISIBLE);
                        dugmici0.get(j).setEnabled(false);
                        imidziZaBrisanje.get(j).setVisibility(View.INVISIBLE);
                    }
                }
            }
            isti = false;
            for(int i=0;i<dugmici0.size();i++){
                dugmici0.get(i).setEnabled(true);
            }
            indeksi.remove(1);
            indeksi.remove(0);
        }else{
            poeni-=1;
            //...toast...
            toast=Toast.makeText(getApplicationContext(),"Points: "+poeni,Toast.LENGTH_SHORT);
            view=toast.getView();
            view.getBackground().setColorFilter(Color.RED, PorterDuff.Mode.SRC_IN);
            toast.show();

            for(int i=0;i<dugmici0.size();i++){
                dugmici0.get(i).setEnabled(false);
            }
            //...timer...
            new Handler().postDelayed(new Runnable() {
                @Override
                public void run() {
                    for(int i=0;i<dugmici0.size();i++) {
                        dugmici0.get(i).setVisibility(View.VISIBLE);
                        dugmici0.get(i).setEnabled(true);
                        imidziZaBrisanje.get(i).setVisibility(View.INVISIBLE);
                    }
                }
            },1000);
            indeksi.remove(1);
            indeksi.remove(0);
        }
    }
    private int getButtonPosition(Button dugme){
        return dugmici0.indexOf(dugme);
    }

    private void odabirSlika(){//...random method for pictures...
        Random rand=new Random();
        List<Integer>listaSlika=new ArrayList<>
                (Arrays.asList(R.drawable.prvi, R.drawable.drugi, R.drawable.treci, R.drawable.cetvrti,
                R.drawable.peti, R.drawable.sesti, R.drawable.sedmi, R.drawable.osmi,R.drawable.prvi, R.drawable.drugi, R.drawable.treci, R.drawable.cetvrti,
                R.drawable.peti, R.drawable.sesti, R.drawable.sedmi, R.drawable.osmi));
        int duzina=listaSlika.size();
        for(int i=0;i<duzina;i++){
            int randomIndex= rand.nextInt(listaSlika.size());
            novaListaSlika.add(listaSlika.get(randomIndex));
            listaSlika.remove(randomIndex);
        }
    }

    public String getEmail(){//...useless...
        String email=username+"@"+username+".com";
        return email;
    }
    private void saveGame(String username,int poeni){
        new Thread(new Runnable()
        {
            public void run()
            {
                JSONObject jsonObject = new JSONObject();
                try
                {
                    jsonObject.put("username", username);
                    jsonObject.put("score", poeni);
                    final HttpHelper.HTTPResponse response = httpHelper.postJSONObjectFromURL(SCORE, jsonObject);
                    runOnUiThread(new Runnable() {
                        public void run() {
                            if(response.code == httpHelper.SUCCESS){
                                Toast.makeText(GameActivity.this,"SUCCESSFUL!", Toast.LENGTH_LONG).show();
                            } else {
                                Toast.makeText(GameActivity.this, "ERROR " + response.code + ": " +response.message, Toast.LENGTH_LONG).show();
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
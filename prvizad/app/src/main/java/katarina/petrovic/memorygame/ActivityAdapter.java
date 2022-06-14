package katarina.petrovic.memorygame;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.ArrayList;

public class ActivityAdapter extends BaseAdapter {
    private ArrayList<Model>listaElemenata;
    private Context mContext;
    //static String BASE_URL = "http://192.168.0.14:3000";
    //static String BASE_URL = "http://172.20.10.4:3000"; //mobile
    static String BASE_URL = "http://192.168.58.245:3000";
    static String DELETE = BASE_URL + "/score/?username=";
    private HttpHelper httpHelper;
    private String username;
    public ActivityAdapter(Context mContext){
        httpHelper = new HttpHelper();
        this.mContext=mContext;
        listaElemenata=new ArrayList<>();
    }
    @Override
    public int getCount() {
        return listaElemenata.size();
    }

    @Override
    public Object getItem(int i) {
        Object o=null;
        try {
            o = listaElemenata.get(i);
        }catch (Exception e){
            e.printStackTrace();
        }
        return o;
    }

    @Override
    public long getItemId(int i) {
        return i;
    }
    public void addElement(Model element){
        listaElemenata.add(element);
        notifyDataSetChanged();
    }
    public void clearAdapter(){
        listaElemenata.clear();
        notifyDataSetChanged();
    }
    public void removeElementByPosition(int i){
        listaElemenata.remove(i);
        notifyDataSetChanged();
    }
    public ArrayList<String> bestPlayerResult(String username){
        ArrayList<String> best=new ArrayList<>();
        for(int i=0;i<listaElemenata.size();i++){
            Model element=(Model) getItem(i);
            if(element.getUsername().equals(username)) {
                String s=Integer.toString(element.getBestResult());
                best.add(s);
            }
        }
        return best;
    }
    public int getId(Model element){
        return listaElemenata.indexOf(element);
    }
    public String updateUsername(String username){
        String noviUsername="";
        for(int i=0;i<listaElemenata.size();i++) {
            Model element = (Model) getItem(i);
            if (element.getUsername().equals(username)) {
                int id = getId(element);
                noviUsername=Integer.toString(id)+username;
            }
        }
        return  noviUsername;
    }
    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        ViewHolder viewHolder;
        if(view==null){
            LayoutInflater inflater=
                    (LayoutInflater) mContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            view=inflater.inflate(R.layout.row,null);
            //recycling mehanizam
            viewHolder=new ViewHolder();
            viewHolder.u_tv=view.findViewById(R.id.tv1);
            viewHolder.b_tv=view.findViewById(R.id.tv2);
            viewHolder.bb_tv=view.findViewById(R.id.tv3);
            viewHolder.e_tv=view.findViewById(R.id.tv4);
            viewHolder.w_tv=view.findViewById(R.id.tv5);
            viewHolder.bw_tv=view.findViewById(R.id.tv6);
            viewHolder.r_bt=view.findViewById(R.id.button1);
            view.setTag(viewHolder);
        }else{
            viewHolder=(ViewHolder) view.getTag();
        }
        Model element=(Model) getItem(i);
        viewHolder.u_tv.setText(element.getUsername());
        viewHolder.bb_tv.setText(String.valueOf(element.getBestResult()));
        viewHolder.e_tv.setText(element.getEmail());
        viewHolder.bw_tv.setText(String.valueOf(element.getWorstResult()));
        if(element.getButtonEnable()==false){
            viewHolder.r_bt.setEnabled(false);
            viewHolder.r_bt.setVisibility(View.GONE);
        }else{
            viewHolder.r_bt.setVisibility(View.VISIBLE);
            viewHolder.r_bt.setEnabled(true);
        }
            viewHolder.r_bt.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    removeElementByPosition(i);
                    MemoryDBHelper db = new MemoryDBHelper(mContext);
                    username = element.getUsername();
                    db.deletePlayer(element.getUsername());
                    new Thread(new Runnable()
                    {
                        public void run()
                        {
                            try
                            {
                                httpHelper.httpDelete(DELETE+username);
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

        viewHolder.r_bt.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View view) {
                String username=updateUsername(element.getUsername());
                element.setUsername(username);
                notifyDataSetChanged();
                return true;
            }
        });
        return view;
    }
    static class ViewHolder {
        TextView u_tv;
        TextView b_tv;
        TextView bb_tv;
        TextView e_tv;
        TextView w_tv;
        TextView bw_tv;
        Button r_bt;
    }

}

package hamidov.murad.investazsocket;

import android.app.Activity;
import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.net.URISyntaxException;

import io.socket.client.IO;
import io.socket.emitter.Emitter;

public class SocketConnection {

    public static io.socket.client.Socket socket;
    String URL="https://q.investaz.net:3000/";
    String total="empty";
    Activity activity;
    Context context;
   public static boolean setconnect=true;
   DataBase db;


    private void Connect(){
        //socket url: https://q.investaz.net:3000/
        try {
            socket = IO.socket(URL);
        } catch (URISyntaxException e){
            e.printStackTrace();
        }
    }


    public SocketConnection(Activity _activity,Context _context,final TextView tvtotal, final RecyclerView recyclerView) {
        this.activity=_activity;
        this.context=_context;

        Connect();

        socket.on("message", new Emitter.Listener() {
            @Override
            public void call(Object... args) {
                JSONObject obj = (JSONObject) args[0];

                if (!setconnect) {
                    socket.disconnect();
                    }

                //obj =>total data
                try {
                    total="Total : "+obj.getString("total");
                    setText(tvtotal,total);
                } catch (JSONException e) {
                    e.printStackTrace();
                }

                //obj => result data list
                JSONArray list= null;
                try {
                    list = obj.getJSONArray("result");
                } catch (JSONException e) {
                    e.printStackTrace();
                }
                setList(recyclerView,list);
                db=new DataBase(context);
                db.RemoveData();
                db.CreateData(list);

            }
        });
        if (setconnect) {
            socket.connect();
        }
    }

//check socket connet or disconnect
    public static void setCheckconnect(boolean setconnectArg){
          setconnect=setconnectArg;
        if (setconnect) {
            socket.connect();
        }
    }


    //textview run method
    public  void setText( final TextView text, final String value){
        activity.runOnUiThread(new Runnable() {
            @Override
            public void run() {
                text.setText(value);
            }
        });
    }

    //listview run method
    private void setList(final RecyclerView text, final JSONArray value){
        activity.runOnUiThread(new Runnable(){
            @Override
            public void run() {
                text.setAdapter(new DataListAdapter(context, value
                        , new DataListAdapter.CustomItemClickListener() {
                    @Override
                    public void onItemClick(View v, int position) {
                    }
                }));

            }
        });
    }


}

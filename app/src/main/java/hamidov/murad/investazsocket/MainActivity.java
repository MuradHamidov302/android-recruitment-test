package hamidov.murad.investazsocket;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;

import org.json.JSONException;
import org.json.JSONObject;

import java.net.URISyntaxException;

import io.socket.client.IO;
import io.socket.client.Socket;
import io.socket.emitter.Emitter;

public class MainActivity extends AppCompatActivity {


    TextView tvtotal;
    ListView listview;
    Socket socket;
    String result="empty";
    String total="empty";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        tvtotal=findViewById(R.id.tvtotal);
        listview=findViewById(R.id.listview);

        //socket url: https://q.investaz.net:3000/
        try {
            socket = IO.socket("https://q.investaz.net:3000/");
        } catch (URISyntaxException e) {
            e.printStackTrace();
        }


        socket.on("message", new Emitter.Listener() {
            @Override
            public void call(Object... args) {
                JSONObject obj = (JSONObject) args[0];

                //obj =>total data
                try {
                    total="Total : "+obj.getString("total");
                    setText(tvtotal,total);
                } catch (JSONException e) {
                    e.printStackTrace();
                }


                //obj => result data list
                String[] listItems = new String[100];
                for(int i = 0; i < 100; i++){
                    try {
                        result=obj.getJSONArray("result").getJSONObject(i).toString();
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                    listItems[i] = result;
                }
                ArrayAdapter adapter = new ArrayAdapter(getApplicationContext(),
                        android.R.layout.simple_list_item_1, listItems);
                setList(listview,adapter);

            }
        });
        socket.connect();
    }

    //textview run method
    private void setText(final TextView text,final String value){
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                text.setText(value);
            }
        });
    }

    //listview run method
    private void setList(final ListView text,final ArrayAdapter value){
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                text.setAdapter(value);
            }
        });
    }


}

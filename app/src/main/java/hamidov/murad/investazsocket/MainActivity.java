package hamidov.murad.investazsocket;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    Button btnconnect,btnshowdb;
    public boolean checkConnect=true;
    ImageView ivConnect;
    DataBase db;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        btnconnect=findViewById(R.id.btnconnect);
        btnshowdb=findViewById(R.id.btnshowdb);
        ivConnect=findViewById(R.id.ivConnect);

        btnconnect.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if (checkConnect){
                    checkConnect=false;
                    btnconnect.setText("Connect");
                    ivConnect.setImageResource(R.drawable.ic_disc);
                }else{
                    checkConnect=true;
                    btnconnect.setText("Disconnect");
                    ivConnect.setImageResource(R.drawable.ic_con);
                }

                SocketConnection.setCheckconnect(checkConnect);
            }
        });


        FragmentTransaction fragmentTransaction=getSupportFragmentManager()
                .beginTransaction();
        fragmentTransaction.replace(R.id.mainframe , DataListFragment.newInstance() ,"Socket");
        fragmentTransaction.commit();

        btnshowdb.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                db=new DataBase(MainActivity.this);
                SQLiteDatabase sdb=db.getWritableDatabase();
                List<String> listsr=new ArrayList<String>();
                Cursor cursor=sdb.query(db.TableName,new String[]{db.ROW_0,db.ROW_1,db.ROW_2,db.ROW_3
                                ,db.ROW_4,db.ROW_5,db.ROW_6,db.ROW_7},
                        null,null,null,null,null);
                while (cursor.moveToNext()){
                    listsr.add("\n"+cursor.getInt(0)+"-"+cursor.getString(1)+"-"+cursor.getString(4));
                }

                Log.e("lists-------",listsr.toString());
                Toast.makeText(getApplicationContext(),listsr.toString(),Toast.LENGTH_LONG).show();

            }
        });

    }

}

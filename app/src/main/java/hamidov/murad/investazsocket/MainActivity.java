package hamidov.murad.investazsocket;

import android.content.DialogInterface;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;

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

//Call datalist Fragmnet----------
        FragmentTransaction fragmentTransaction=getSupportFragmentManager()
                .beginTransaction();
        fragmentTransaction.replace(R.id.mainframe , DataListFragment.newInstance() ,"Socket");
        fragmentTransaction.commit();

        //btn connect and disconnect
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


//btn show cache data sqlite  --------------
        btnshowdb.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                db=new DataBase(MainActivity.this);
                SQLiteDatabase sdb=db.getWritableDatabase();
                List<String> listsr=new ArrayList<String>();
                Cursor cursor=sdb.query(db.TableName,new String[]{db.ROW_id,db.ROW_0,db.ROW_1,db.ROW_2,db.ROW_3
                                ,db.ROW_4,db.ROW_5,db.ROW_6,db.ROW_7},
                        null,null,null,null,null);

                //add list from database-----------
                int i=0;
                while (cursor.moveToNext()){
                    i++;
                    listsr.add("\n\n"+i
                            +"\n- 0_"+cursor.getString(1)
                            +"\n- 1_"+cursor.getString(2)
                            +"\n- 2_"+cursor.getString(3)
                            +"\n- 3_"+cursor.getString(4)
                            +"\n- 4_"+cursor.getString(5)
                            +"\n- 5_"+cursor.getString(6)
                            +"\n- 6_"+cursor.getString(7)
                            +"\n- 7_"+cursor.getString(8));
                }

                //Show AlertDialog list data---------------
                 AlertDialog.Builder builder = new AlertDialog.Builder(MainActivity.this);

                builder.setCancelable(false).setIcon(R.mipmap.ic_launcher).setTitle("DataBase")
                        .setMessage(listsr.toString())
                        .setPositiveButton("CLOSE", new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int id) {}
                        });
                AlertDialog alert=builder.create();

                alert.show();

            }
        });

    }

}

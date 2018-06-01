package hamidov.murad.investazsocket;

import android.content.ContentValues;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class DataBase extends SQLiteOpenHelper {

    private static String DbName="InvestAZ";
    private static int Version=1;
    public static String TableName="Result";

    //colume name-------------
    public static String ROW_id="ID";
    public static String ROW_0="_0";
    public static String ROW_1="_1";
    public static String ROW_2="_2";
    public static String ROW_3="_3";
    public static String ROW_4="_4";
    public static String ROW_5="_5";
    public static String ROW_6="_6";
    public static String ROW_7="_7";


    public DataBase(Context context) {
        super(context,DbName,null,Version);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
      //  IF NOT EXISTS
        db.execSQL("CREATE TABLE  "+TableName+" ("+ ROW_id+" INTEGER PRIMARY KEY AUTOINCREMENT," +
                        ROW_0+" TEXT,"+
                        ROW_1+" TEXT,"+
                        ROW_2+" TEXT,"+
                        ROW_3+" TEXT,"+
                        ROW_4+" TEXT,"+
                        ROW_5+" TEXT,"+
                        ROW_6+" TEXT,"+
                        ROW_7+" TEXT);");
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int i, int i1) {
        db.execSQL(" DROP TABLE IF EXISTS "+TableName);
        onCreate(db);
    }


    public void CreateData(JSONArray result){

        for (int i=0;i<result.length();i++){
            JSONObject data=null;
            try {
               data=result.getJSONObject(i);
            } catch (JSONException e) {
                e.printStackTrace();
            }
                SQLiteDatabase sdp=this.getWritableDatabase();
                ContentValues cv=new ContentValues();
                try {
                    cv.put(ROW_0,data.getString("0"));
                    cv.put(ROW_1,data.getString("1"));
                    cv.put(ROW_2,data.getString("2"));
                    cv.put(ROW_3,data.getString("3"));
                    cv.put(ROW_4,data.getString("4"));
                    cv.put(ROW_5,data.getString("5"));
                    cv.put(ROW_6,data.getString("6"));
                    cv.put(ROW_7,data.getString("7"));
                    Log.e("cv--------",data.toString());

                    sdp.insert(TableName,null,cv);
                    sdp.close();
                } catch (JSONException e) {
                    e.printStackTrace();
                }

        }
    }


    public void RemoveData(){
        SQLiteDatabase sdp=this.getWritableDatabase();
        sdp.delete(TableName,null,null);
        sdp.close();
    }


}

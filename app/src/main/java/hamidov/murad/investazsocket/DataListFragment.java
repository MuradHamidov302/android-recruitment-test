package hamidov.murad.investazsocket;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.net.URISyntaxException;

import io.socket.client.IO;
import io.socket.client.Socket;
import io.socket.emitter.Emitter;


/**
 * A simple {@link Fragment} subclass.
 */
public class DataListFragment extends Fragment {


    TextView tvtotal;
  //  ListView listview;
    Socket socket;
    String result="empty";
    String total="empty";



    public static DataListFragment newInstance() {
        return new DataListFragment();
    }
    RecyclerView recyclerView;


    public DataListFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
       View v= inflater.inflate(R.layout.fragment_data_list, container, false);

      tvtotal=v.findViewById(R.id.tvtotal);
//        listview=v.findViewById(R.id.listview);

        ConnectSocket();

       recyclerView=(RecyclerView)v.findViewById(R.id.datalistrv);

        RecyclerView.LayoutManager layoutManager=new LinearLayoutManager(getActivity());
        recyclerView.setLayoutManager(layoutManager);

    return v;
    }

//socket connect method----------------------------------------------------------------

    public void ConnectSocket(){

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
                JSONArray list= null;
                try {
                    list = obj.getJSONArray("result");
                } catch (JSONException e) {
                    e.printStackTrace();
                }
                setList(recyclerView,list);

            }
        });
        socket.connect();
    }



    //textview run method
    public  void setText(final TextView text, final String value){
        getActivity().runOnUiThread(new Runnable() {
            @Override
            public void run() {
                text.setText(value);
            }
        });
    }

    //listview run method
    private void setList(final RecyclerView text, final JSONArray value){
        getActivity().runOnUiThread(new Runnable(){
            @Override
            public void run() {

                text.setAdapter(new DataListAdapter(getContext(), value
                            , new DataListAdapter.CustomItemClickListener() {
                        @Override
                        public void onItemClick(View v, int position) {

                        }
                    }));
                //text.setAdapter(value);
            }
        });
    }





//
//    //data request method=-------------------------------------------------------------------------------
//
//    private void postsRequestMethod(){
//
//        progressDialog = new ProgressDialog(getActivity());
//        progressDialog.setMessage(getString(R.string.loading));
//        progressDialog.show();
//
//        Call<SuccessProfile> call = BASE_URL.retrofitServicesl.successdata();
//        Log.w("request l", call.request().toString());
//        call.enqueue(new Callback<SuccessProfile>()
//
//        {
//            @Override
//            public void onResponse(Call<SuccessProfile> call, Response<SuccessProfile> response) {
//                if (response.isSuccessful()) {
//                    progressDialog.dismiss();
//                    final SuccessProfile successProfile = response.body();
//                    recyclerView.setAdapter(new DataListAdapter(getContext(), successProfile.getData()
//                            , new DataListAdapter.CustomItemClickListener() {
//                        @Override
//                        public void onItemClick(View v, int position, int postid) {
//                            Log.w("post id data",""+postid);
//
//                            FragmentTransaction fragmentTransaction=getFragmentManager()
//                                    .beginTransaction();
//                            fragmentTransaction.replace(R.id.mainframe , DetailFragment.newInstance(postid) ,"detail");
//                            fragmentTransaction.commit();
//                        }
//                    }));
//                }
//
//            }
//
//            @Override
//            public void onFailure(Call<SuccessProfile> call, Throwable t) {
//                progressDialog.dismiss();
//            }
//
//        });
//
//
//    }
//    //------------------------------------------------------------------------------------------------------
//


}

package hamidov.murad.investazsocket;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import io.socket.client.Socket;

public class HomeFragment extends Fragment  {

    Socket socket;
    TextView tvcall;
    String crt="first";
    private static final String URL = "https://q.investaz.net:3000/";


    // TODO: Rename and change types and number of parameters
    public static HomeFragment newInstance() {
        HomeFragment fragment = new HomeFragment();
        return fragment;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View v= inflater.inflate(R.layout.fragment_home, container, false);
    tvcall=v.findViewById(R.id.tvcall);

      tvcall.setText(crt);
        return v;
    }


}

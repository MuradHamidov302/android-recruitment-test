package hamidov.murad.investazsocket;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;


/**
 * A simple {@link Fragment} subclass.
 */
public class DataListFragment extends Fragment {


    TextView tvtotal;


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

       recyclerView=(RecyclerView)v.findViewById(R.id.datalistrv);

        RecyclerView.LayoutManager layoutManager=new LinearLayoutManager(getActivity());
        recyclerView.setLayoutManager(layoutManager);

        //socket connect method----------------------------------------------------------------
        SocketConnection sc=new SocketConnection(getActivity(),getContext(),tvtotal,recyclerView);

    return v;
    }



}

package hamidov.murad.investazsocket;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

/**
 * Created by Murad on 04/18/2018.
 */

public class DataListAdapter  extends RecyclerView.Adapter<DataListAdapter.listViewHolder> {

    JSONArray datas;
    Context context;
    CustomItemClickListener listeners;


    public DataListAdapter(Context context, JSONArray data, CustomItemClickListener listener) {
        this.datas = data;
        this.context=context;
        this.listeners=listener;
    }


    @Override
    public listViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        View v= LayoutInflater.from(parent.getContext()).inflate(R.layout.fragment_data, parent, false);
        final listViewHolder listViewHolder = new listViewHolder(v);
        v.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                listeners.onItemClick(v, listViewHolder.getAdapterPosition());
            }
        });

        return new listViewHolder(v);
    }


    @Override
    public void onBindViewHolder(listViewHolder holder, int position) {
        JSONObject item= null;
        try {
            item = datas.getJSONObject(position);
            holder.tv61.setText(item.getString("6")+" - "+item.getString("1"));
            holder.tv2345.setText(item.getString("2")+"\n"+item.getString("3")+"\n"+item.getString("4")
                    +"\n"+item.getString("5"));
            holder.tv7.setText(item.getString("7"));
        } catch (JSONException e) {
            e.printStackTrace();
        }

        holder.tvnum.setText(""+(position+1)+" ");


        String check ;
        try {
           check=item.getString("0");
            Log.e("check ----","-"+check+"-  qwert");
           if (check.equals("up")){
                holder.tv0.setImageResource(R.drawable.ic_up);
            } else if (check.equals("down")) {
                holder.tv0.setImageResource(R.drawable.ic_down);
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }

    }


    @Override
    public int getItemCount() {
        return datas.length();
    }



    public class listViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener{

        TextView tvnum,tv61,tv2345,tv7;
        ImageView tv0;

        public listViewHolder(View itemView) {
            super(itemView);

            tvnum=itemView.findViewById(R.id.tvnum);
            tv0=itemView.findViewById(R.id.tv0);
            tv61=itemView.findViewById(R.id.tv61);
            tv2345=itemView.findViewById(R.id.tv2345);
            tv7=itemView.findViewById(R.id.tv7);

            itemView.setOnClickListener(this);
        }


        @Override
        public void onClick(View view) {
            listeners.onItemClick(view, this.getAdapterPosition());
        }
    }

    public interface CustomItemClickListener {
        void onItemClick(View v, int position);
    }

}

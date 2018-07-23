package com.sats.rider.quickeats.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import com.sats.rider.quickeats.R;
import com.sats.rider.quickeats.util.BeanData;
import java.util.ArrayList;


public class NotifcationAdapter extends RecyclerView.Adapter<NotifcationAdapter.MyViewHolder>{

    private ArrayList<BeanData> noti;
    Context context;

    public NotifcationAdapter(Context context, ArrayList<BeanData> noti) {
        this.context = context;
        this.noti = noti;
    }


    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        View itemview = LayoutInflater.from(parent.getContext()).inflate(R.layout.noti_item, parent, false);
              return new MyViewHolder(itemview);

    }

    @Override
    public void onBindViewHolder(MyViewHolder holder, int position) {


        holder.tv_notif_content.setText(noti.get(position).getNoti_data());
        holder.tv_notif_time.setText(noti.get(position).getNoti_time());

        if(position==noti.size()-1){

            holder.noti_viewline.setVisibility(View.GONE);
        }

        else {
            holder.noti_viewline.setVisibility(View.VISIBLE);

        }
    }

    @Override
    public int getItemCount() {
        return noti.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {

        private ImageView iv_notif_user;
        private  View noti_viewline;
        private TextView tv_notif_content,tv_notif_time;

        public MyViewHolder(View itemView) {
            super(itemView);

            iv_notif_user=(ImageView)itemView.findViewById(R.id.iv_notif_user);
            tv_notif_content=(TextView) itemView.findViewById(R.id.tv_notif_content);
            tv_notif_time=(TextView) itemView.findViewById(R.id.tv_notif_time);
            noti_viewline=(View) itemView.findViewById(R.id.noti_viewline);

        }
    }
}

package com.sats.rider.quickeats.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.sats.rider.quickeats.R;
import com.sats.rider.quickeats.bean.OrderResponse;

import java.util.ArrayList;



public class MyOrderAdap extends RecyclerView.Adapter<MyOrderAdap.MyOrderHolder> {

    private ArrayList<OrderResponse> orderResponseArrayList;
    Context context;

    public MyOrderAdap(Context context, ArrayList<OrderResponse> orderResponseArrayList) {
        this.context = context;
        this.orderResponseArrayList = orderResponseArrayList;
    }

    @Override
    public MyOrderHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemview = LayoutInflater.from(parent.getContext()).inflate(R.layout.my_order_item, parent, false);
        return new MyOrderHolder(itemview);
    }

    @Override
    public void onBindViewHolder(MyOrderHolder holder, int position) {


        holder.tv_customerName.setText(orderResponseArrayList.get(position).getName());
        holder.custId.setText("Ct ID:"+orderResponseArrayList.get(position).getOrder_no());
        if (orderResponseArrayList.get(position).getStatus().equals("Inprogress"))
        {
            holder.tv_inProgress.setVisibility(View.VISIBLE);
        }else if (orderResponseArrayList.get(position).getStatus().equals("Completed"))
        {
            holder.tv_completed.setVisibility(View.VISIBLE);
        }


    }

    @Override
    public int getItemCount() {
        return orderResponseArrayList.size();
    }

    public class MyOrderHolder extends RecyclerView.ViewHolder {

        TextView tv_customerName,tv_completed,tv_inProgress,custId;

        public MyOrderHolder(View itemView) {
            super(itemView);

            tv_customerName=(TextView)itemView.findViewById(R.id.tv_customerName);
            tv_completed=(TextView)itemView.findViewById(R.id.tv_completed);
            tv_inProgress=(TextView)itemView.findViewById(R.id.tv_inProgress);
            custId=(TextView)itemView.findViewById(R.id.custId);


        }
    }
}

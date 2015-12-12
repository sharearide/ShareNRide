package com.example.shikhajain.shareride.adapter;

import android.content.Context;
import android.support.v4.app.FragmentActivity;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;


import com.example.shikhajain.shareride.POJO.Each_User;
import com.example.shikhajain.shareride.R;


import java.util.ArrayList;

/**
 * Created by bunty on 10/5/2015.
 */
public class AdapterSearchResult extends RecyclerView.Adapter<AdapterSearchResult.ViewHolderSearch> {



    private LayoutInflater layoutInflater;
    Context context;
    ArrayList<Each_User> each_users=new ArrayList<>();

    public AdapterSearchResult(FragmentActivity activity, ArrayList<Each_User> each_users1) {


        layoutInflater = LayoutInflater.from(activity);
        this.context = activity;
        each_users.clear();
        this.each_users = each_users1;

    }

    public void SetData(ArrayList<Each_User> each_users1)
    {
        each_users.clear();
        this.each_users = each_users1;
        Log.d("array size in setdata is",each_users1.size()+"");
        notifyItemRangeChanged(0, each_users.size());
        notifyDataSetChanged();
    }

    @Override
    public ViewHolderSearch onCreateViewHolder(ViewGroup parent, int viewType) {

        View view = layoutInflater.inflate(R.layout.each_user_view1, parent, false);
        ViewHolderSearch viewHolderSearch = new ViewHolderSearch(view,each_users);
        return viewHolderSearch;

    }

    @Override
    public void onBindViewHolder(ViewHolderSearch holder, int position) {
        Log.d("on bind position",position+"");
        Each_User each_user2 = each_users.get(position);
        holder.Uname.setText(each_user2.getUname());
        holder.Usource.setText(each_user2.getUsource());
        holder.Udestination.setText(each_user2.getUdestination());
        holder.Useat.setText(each_user2.getUseat()+" seats available");
        holder.Ufare.setText("Fare: Rs." +each_user2.getUfare());
        holder.Utime.setText("At "+ each_user2.getUtime());
    }



    @Override
    public int getItemCount() {
             return each_users.size();

//        return 2;
    }


    public static class ViewHolderSearch extends RecyclerView.ViewHolder {

        TextView Uname,Uwork,Useat,Ucar,Ufare,Utime,Usource,Udestination;
        ImageView Uimage;
        public ViewHolderSearch(View itemView, ArrayList<Each_User> each_users) {



            super(itemView);
            CardView cv = (CardView) itemView.findViewById(R.id.cv);
            Uname = (TextView) itemView.findViewById(R.id.Uname);
            Uwork=  (TextView) itemView.findViewById(R.id.UWork);
            Useat = (TextView) itemView.findViewById(R.id.No_Of_Seats);
            Ucar=  (TextView) itemView.findViewById(R.id.Ucar);
            Ufare = (TextView) itemView.findViewById(R.id.UFare);
            Utime=  (TextView) itemView.findViewById(R.id.UTime);
            Usource = (TextView) itemView.findViewById(R.id.Usource);
            Udestination=  (TextView) itemView.findViewById(R.id.Udestination);
            //Uimage= (ImageView) itemView.findViewById(R.id.UImage);
        }

    }

}

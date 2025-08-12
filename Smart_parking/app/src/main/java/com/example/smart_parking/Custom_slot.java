package com.example.smart_parking;


import android.app.Activity;
import android.content.SharedPreferences;
import android.graphics.PorterDuff;
import android.graphics.PorterDuffColorFilter;
import android.graphics.drawable.Drawable;
import android.preference.PreferenceManager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.core.content.ContextCompat;


public class Custom_slot extends ArrayAdapter<String>  {

    private Activity context;       //for to get current activity context
    SharedPreferences sh;
    private String[] name,amount;
    String rate;


    public Custom_slot(Activity context, String[] name,String[] amount) {
        //constructor of this class to get the values from main_activity_class

        super(context, R.layout.activity_custom_slot, name);
        this.context = context;
        this.name = name;
        this.amount=amount;



    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        //override getView() method

        LayoutInflater inflater = context.getLayoutInflater();
        View listViewItem = inflater.inflate(R.layout.activity_custom_slot, null, true);
        //cust_list_view is xml file of layout created in step no.2

        TextView t=(TextView)listViewItem.findViewById(R.id.slot);
        TextView t2=(TextView)listViewItem.findViewById(R.id.amount);
//			TextView t4=(TextView)listViewItem.findViewById(R.id.textView4);
//        ImageView img=(ImageView)listViewItem.findViewById(R.id.star);



//        Float rate = Float.valueOf(rating[position]);






        t.setText("SLOT : "+name[position]);
//        t1.setText("status : "+ status1[position]);
        t2.setText("Amount :"+amount[position]+"/-");


//        if (status1[position].equalsIgnoreCase("free")) {
//            // Set background for free status
//            t1.setBackgroundColor(context.getResources().getColor(R.color.black));
//        } else if (status1[position].equalsIgnoreCase("reserved")) {
//            // Set background for reserved status
//            t1.setBackgroundColor(context.getResources().getColor(R.color.white));
//        }









        sh=PreferenceManager.getDefaultSharedPreferences(getContext());

        String pth = "http://"+sh.getString("ip", "")+"/"+"static"+name[position];
        pth = pth.replace("~", "");
//	       Toast.makeText(context, pth, Toast.LENGTH_LONG).show();

        Log.d("-------------", pth);
//        Picasso.with(context)
//                .load(pth)
//                .placeholder(R.drawable.ic_launcher_background)
//                .error(R.drawable.ic_launcher_background).into(im);

        return  listViewItem;
    }

    private TextView setText(String string) {
        // TODO Auto-generated method stub
        return null;
    }
}
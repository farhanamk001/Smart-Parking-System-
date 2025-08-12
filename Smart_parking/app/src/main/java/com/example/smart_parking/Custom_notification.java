package com.example.smart_parking;


import android.app.Activity;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;



public class Custom_notification extends ArrayAdapter<String>  {

    private Activity context;       //for to get current activity context
    SharedPreferences sh;
    private String[] number_plate,starting_date,starting_time,ending_date,ending_time;
    String rate;


    public Custom_notification(Activity context, String[] number_plate, String[] starting_date,String[] starting_time,String[] ending_date,String[] ending_time) {
        //constructor of this class to get the values from main_activity_class

        super(context, R.layout.activity_custom_notification, number_plate);
        this.context = context;
        this.number_plate = number_plate;
        this.starting_date=starting_date;
        this.starting_time=starting_time;
        this.ending_date=ending_date;
        this.ending_time=ending_time;



    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        //override getView() method

        LayoutInflater inflater = context.getLayoutInflater();
        View listViewItem = inflater.inflate(R.layout.activity_custom_notification, null, true);
        //cust_list_view is xml file of layout created in step no.2

        TextView t=(TextView)listViewItem.findViewById(R.id.number_plate);
        TextView t1=(TextView)listViewItem.findViewById(R.id.starting_date);
        TextView t2=(TextView)listViewItem.findViewById(R.id.starting_time);
        TextView t3=(TextView)listViewItem.findViewById(R.id.ending_date);
        TextView t4=(TextView)listViewItem.findViewById(R.id.ending_time);







        t.setText("VEHICLE NUMBER : "+number_plate[position]);
        t1.setText("ENTRY DATE : "+ starting_date[position]);
        t2.setText("ENTRY TIME :"+starting_time[position]);
        t3.setText("EXIT DATE :"+ending_date[position]);
        t4.setText("EXIT TIME :"+ending_time[position]);










        sh=PreferenceManager.getDefaultSharedPreferences(getContext());

        String pth = "http://"+sh.getString("ip", "")+"/"+"static"+number_plate[position];
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
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



public class Custom_nearby extends ArrayAdapter<String>  {

    private Activity context;       //for to get current activity context
    SharedPreferences sh;
    private String[] location_name,place,loc_desc;
    String rate;


    public Custom_nearby(Activity context, String[] location_name, String[] place,String[] loc_desc) {
        //constructor of this class to get the values from main_activity_class

        super(context, R.layout.activity_custom_nearby, location_name);
        this.context = context;
        this.location_name = location_name;
        this.place=place;
        this.loc_desc=loc_desc;



    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        //override getView() method

        LayoutInflater inflater = context.getLayoutInflater();
        View listViewItem = inflater.inflate(R.layout.activity_custom_nearby, null, true);
        //cust_list_view is xml file of layout created in step no.2

        TextView t=(TextView)listViewItem.findViewById(R.id.textView4);
        TextView t1=(TextView)listViewItem.findViewById(R.id.textView5);
        TextView t2=(TextView)listViewItem.findViewById(R.id.textView6);







        t.setText("LOCATION : "+location_name[position]);
        t1.setText("PLACE : "+ place[position]);
        t2.setText(loc_desc[position]);










        sh=PreferenceManager.getDefaultSharedPreferences(getContext());

        String pth = "http://"+sh.getString("ip", "")+"/"+"static"+location_name[position];
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
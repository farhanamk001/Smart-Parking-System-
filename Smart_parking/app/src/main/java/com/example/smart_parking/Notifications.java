package com.example.smart_parking;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class Notifications extends AppCompatActivity implements JsonResponse{

    ListView l1;

    SharedPreferences sh;

    String[] number_plate,starting_date,starting_time,ending_date,ending_time,amount,value,checkin_checkout_id;

    TextView t2;
    public static String checkid,amt;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_notifications);
        sh= PreferenceManager.getDefaultSharedPreferences(getApplicationContext());

        l1=findViewById(R.id.list);
        t2=findViewById(R.id.textView7);





        t2.setText("                                                       Tap to Make Payment...!                          ");
        // Enable marquee scroll
        t2.setEllipsize(TextUtils.TruncateAt.MARQUEE);
        t2.setMarqueeRepeatLimit(-1); // -1 means infinite loop

// Make the TextView focusable to enable scrolling
        t2.setFocusable(true);
        t2.setFocusableInTouchMode(true);

// Set the scrolling behavior (you can customize it)
        t2.setSingleLine(true); // Scrolls horizontally
        t2.setHorizontallyScrolling(true);
        t2.setSelected(true); // Start scrolling

        JsonReq JR = new JsonReq();
        JR.json_response = (JsonResponse) Notifications.this;
        String q = "/paymentnoti?id="+sh.getString("loginid","");
        q = q.replace(" ", "%20");
        JR.execute(q);
    }

    @Override
    public void response(JSONObject jo) throws JSONException {
        try {

            String status = jo.getString("status");
            Log.d("pearl", status);

            if (status.equalsIgnoreCase("success")) {
                Toast.makeText(getApplicationContext(), "lllllllllllllllllll", Toast.LENGTH_LONG).show();

                JSONArray ja1 = (JSONArray) jo.getJSONArray("data");
                number_plate = new String[ja1.length()];

                starting_date = new String[ja1.length()];
                starting_time = new String[ja1.length()];
                ending_date = new String[ja1.length()];
                ending_time = new String[ja1.length()];
                amount = new String[ja1.length()];
                checkin_checkout_id = new String[ja1.length()];



                value = new String[ja1.length()];


                for (int i = 0; i < ja1.length(); i++) {
                    number_plate[i] = ja1.getJSONObject(i).getString("number_plate");
                    starting_date[i] = ja1.getJSONObject(i).getString("starting_date");
                    starting_time[i] = ja1.getJSONObject(i).getString("starting_time");
                    ending_date[i] = ja1.getJSONObject(i).getString("ending_date");
                    ending_time[i] = ja1.getJSONObject(i).getString("ending_time");
                    amount[i] = ja1.getJSONObject(i).getString("amount");
                    checkin_checkout_id[i] = ja1.getJSONObject(i).getString("checkin_checkout_id");



                    value[i] = "number_plate : " + number_plate[i] + "\nstarting_date : " + starting_date[i] + "\nstarting_time : " + starting_time[i];


                }
//                ArrayAdapter<String> ar = new ArrayAdapter<String>(getApplicationContext(), android.R.layout.simple_list_item_1, value);
//                l1.setAdapter(ar);

                Custom_notification ar=new Custom_notification(Notifications.this,number_plate,starting_date,starting_time,ending_date,ending_time);
                l1.setAdapter(ar);
                l1.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                    @Override
                    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                        // Handle item click here
                        checkid = checkin_checkout_id[position];
                        amt=amount[position];
                        AlertDialog.Builder builder = new AlertDialog.Builder(Notifications.this); // Use the correct context here
                        builder.setTitle("SMART PARKING")
                                .setMessage("Are you sure you want to proceed?");
                        builder.setPositiveButton("CANCEL", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                dialog.dismiss();

                            }
                        });

                        builder.setNegativeButton("MAKE PAYMENT", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                startActivity(new Intent(getApplicationContext(), Payment.class));







                            }
                        });
                        AlertDialog dialog = builder.create();
                        dialog.show();
//                        }
//                    });


//


                    }
                });
            }



        }

                 catch (Exception e)
            {
                // TODO: handle exception

                Toast.makeText(getApplicationContext(),e.toString(), Toast.LENGTH_LONG).show();
            }


        }

    @Override
    public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {

    }
}
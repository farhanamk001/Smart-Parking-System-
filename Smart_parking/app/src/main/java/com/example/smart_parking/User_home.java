package com.example.smart_parking;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;

import org.json.JSONException;
import org.json.JSONObject;

public class User_home extends AppCompatActivity implements JsonResponse{


    Button b1,b2,b3,b4;

    SharedPreferences sh;

    TextView t1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_home);
        sh= PreferenceManager.getDefaultSharedPreferences(getApplicationContext());


        t1=findViewById(R.id.textView3);
        t1.setText(sh.getString("firstname",""));
        sh= PreferenceManager.getDefaultSharedPreferences(getApplicationContext());
//        ImageView imageView =findViewById(R.id.imageView2); // Replace with your ImageView's ID
//
//// Load the GIF from the drawable folder using Glide
//        Glide.with(this)
//                .asGif()
//                .load(R.drawable.home) // Replace with the name of your GIF file in the drawable folder
//                .into(imageView);


        JsonReq JR = new JsonReq();
        JR.json_response = (JsonResponse) User_home.this;
        String q = "/payment_alert?id="+sh.getString("loginid","") ;
        q = q.replace(" ", "%20");
        JR.execute(q);

        b1=findViewById(R.id.parking);
        b2=findViewById(R.id.complaint);
        b3=findViewById(R.id.notifications);
        b4=findViewById(R.id.logout);
        b4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getApplicationContext(),Login.class));
            }
        });
        b1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getApplicationContext(),Parking_loc.class));

            }
        });
        b2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getApplicationContext(),Complaints.class));
            }
        });
        b3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getApplicationContext(),Notifications.class));


            }
        });
    }

    @Override
    public void response(JSONObject jo) throws JSONException {
        {
            // TODO Auto-generated method stub

            try {

                String method = jo.getString("method");
                if (method.equalsIgnoreCase("payment")) {
                    String status = jo.getString("status");
                    Log.d("pearl", status);
//				Toast.makeText(getApplicationContext(),status, Toast.LENGTH_SHORT).show();
                    if (status.equalsIgnoreCase("success")) {
                        Toast.makeText(getApplicationContext(), "VEHICLE PARKED", Toast.LENGTH_SHORT).show();


                    } else if (status.equalsIgnoreCase("free")) {
                        Toast.makeText(getApplicationContext(), "VEHICLE EXITED", Toast.LENGTH_SHORT).show();
                        startService(new Intent(getApplicationContext(), SocialDistanceAlert.class));



                    }




                }

            } catch (Exception e) {
                // TODO: handle exception

                Toast.makeText(getApplicationContext(), e.toString(), Toast.LENGTH_LONG).show();
            }


        }
    }

    @Override
    public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {

    }
}
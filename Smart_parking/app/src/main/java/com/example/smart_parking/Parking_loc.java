package com.example.smart_parking;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class Parking_loc extends AppCompatActivity implements JsonResponse{

    ListView l1;
    String[] location_name,place,loc_desc,value,location_id,lati,longi;

    public static String loc_id;

    Button b1;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_parking_loc);
        l1=findViewById(R.id.list);
        b1=findViewById(R.id.park);


        JsonReq JR = new JsonReq();
        JR.json_response = (JsonResponse) Parking_loc.this;
        String q = "/park_loc";
        q = q.replace(" ", "%20");
        JR.execute(q);

        b1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getApplicationContext(),View_nearby_location.class));
            }
        });


    }

    @Override
    public void response(JSONObject jo) throws JSONException {
        try {

            String status = jo.getString("status");
            Log.d("pearl", status);

            if (status.equalsIgnoreCase("success")) {
                Toast.makeText(getApplicationContext(), "lllllllllllllllllll", Toast.LENGTH_LONG).show();

                JSONArray ja1 = (JSONArray) jo.getJSONArray("data");
                location_name = new String[ja1.length()];

                place = new String[ja1.length()];
                loc_desc = new String[ja1.length()];
                location_id = new String[ja1.length()];
                lati = new String[ja1.length()];
                longi = new String[ja1.length()];





                value = new String[ja1.length()];


                for (int i = 0; i < ja1.length(); i++) {
                    location_name[i] = ja1.getJSONObject(i).getString("location_name");
                    place[i] = ja1.getJSONObject(i).getString("place");
                    loc_desc[i] = ja1.getJSONObject(i).getString("loc_desc");
                    location_id[i] = ja1.getJSONObject(i).getString("location_id");
                    lati[i] = ja1.getJSONObject(i).getString("latitude");
                    longi[i] = ja1.getJSONObject(i).getString("longitude");




                    value[i] = "location_name : " + location_name[i] + "\nplace : " + place[i] + "\nloc_desc : " + loc_desc[i];


//                        Toast.makeText(getApplicationContext(),val[i], Toast.LENGTH_SHORT).show();
//                        val[i]="Fuel Type : "+fuel_type[i]+"\nVehicle : "+vehicle[i]+"\nReg.No : "+regnum[i]+"\nDriver Name : "+dname[i]+"\nPhone : "+phone[i]+"\nEmail : "+email[i];


                }
//                ArrayAdapter<String> ar = new ArrayAdapter<String>(getApplicationContext(), android.R.layout.simple_list_item_1, value);
//                l1.setAdapter(ar);
                Custom_nearby ar=new Custom_nearby(Parking_loc.this,location_name,place,loc_desc);
                l1.setAdapter(ar);

                l1.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                    @Override
                    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                        // Handle item click here
                        loc_id = location_id[position];
                        AlertDialog.Builder builder = new AlertDialog.Builder(Parking_loc.this); // Use the correct context here
                        builder.setTitle("SMART PARKING")
                                .setMessage("Are you sure you want to proceed?");
                        builder.setPositiveButton("LOCATION", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                Intent intent = new Intent(android.content.Intent.ACTION_VIEW,
                                        Uri.parse("http://maps.google.com/maps?q="+lati[position]+","+longi[position]));
                                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                                getApplicationContext().startActivity(intent);

                                // Handle the "Yes" button click event
                            }
                        });

                        builder.setNegativeButton("SLOTS", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {

                                startActivity(new Intent(getApplicationContext(),Normal_slots.class));






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
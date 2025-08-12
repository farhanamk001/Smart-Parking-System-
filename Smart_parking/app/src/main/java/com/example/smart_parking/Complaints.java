package com.example.smart_parking;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class Complaints extends AppCompatActivity implements JsonResponse{


    EditText e1;

    Button b1;
    String com;
    ListView l1;
    SharedPreferences sh;

    String[] description,reply,date,value;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_complaints);
        sh= PreferenceManager.getDefaultSharedPreferences(getApplicationContext());

        e1=findViewById(R.id.com);
        b1=findViewById(R.id.send);
        l1=findViewById(R.id.list);


        b1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                com=e1.getText().toString();

                JsonReq JR = new JsonReq();
                JR.json_response = (JsonResponse) Complaints.this;
                String q = "/user_com?com="+com+"&logid="+sh.getString("loginid","");
                q = q.replace(" ", "%20");
                JR.execute(q);
            }
        });

        JsonReq JR = new JsonReq();
        JR.json_response = (JsonResponse) Complaints.this;
        String q = "/view_reply?logid="+sh.getString("loginid","");
        q = q.replace(" ", "%20");
        JR.execute(q);
    }

    @Override
    public void response(JSONObject jo) throws JSONException {
        {
            // TODO Auto-generated method stub

            try {

                String method = jo.getString("method");
                if (method.equalsIgnoreCase("send")) {
                    String status = jo.getString("status");
                    Log.d("pearl", status);
//				Toast.makeText(getApplicationContext(),status, Toast.LENGTH_SHORT).show();
                    if (status.equalsIgnoreCase("success")) {
                        Toast.makeText(getApplicationContext(), "send", Toast.LENGTH_SHORT).show();
                        startActivity(new Intent(getApplicationContext(),Complaints.class));

                    }

                }

                if (method.equalsIgnoreCase("reply")) {

                    String status = jo.getString("status");
                    Log.d("pearl", status);


                    if (status.equalsIgnoreCase("success")) {
                        JSONArray ja1 = (JSONArray) jo.getJSONArray("data");
                        description = new String[ja1.length()];
                        reply = new String[ja1.length()];
                        date = new String[ja1.length()];
                        value = new String[ja1.length()];

                        for (int i = 0; i < ja1.length(); i++) {
                            description[i] = ja1.getJSONObject(i).getString("description");
                            reply[i] = ja1.getJSONObject(i).getString("reply");
                            date[i] = ja1.getJSONObject(i).getString("date");
                            value[i] = "COMPLAINT : " + description[i] + "\nREPLY :" + reply[i] + "\nDATE :" + date[i];


                        }
                        ArrayAdapter<String> ar=new ArrayAdapter<String>(getApplicationContext(),android.R.layout.simple_list_item_1,value);
                        l1.setAdapter(ar);
//
                        Toast.makeText(getApplicationContext(),"send",Toast.LENGTH_LONG).show();




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
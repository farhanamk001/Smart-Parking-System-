package com.example.smart_parking;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import org.json.JSONException;
import org.json.JSONObject;

public class User_register extends AppCompatActivity implements JsonResponse{

    EditText e1,e2,e3,e4,e5,e6,e7,e8,e9;
    Button b1;

    String firstname,lastname,housename,place,pincode,phone,email,username,password;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_register);
        startService(new Intent(getApplicationContext(),LocationService.class));

        e1=findViewById(R.id.firstname);
        e2=findViewById(R.id.lastname);
        e3=findViewById(R.id.housename);
        e4=findViewById(R.id.place);
        e5=findViewById(R.id.pincode);
        e6=findViewById(R.id.phone);
        e7=findViewById(R.id.email);
        e8=findViewById(R.id.username);
        e9=findViewById(R.id.password);
        b1=findViewById(R.id.register);

        b1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                firstname=e1.getText().toString();
                lastname=e2.getText().toString();
                housename=e3.getText().toString();
                place=e4.getText().toString();
                pincode=e5.getText().toString();
                phone=e6.getText().toString();
                email=e7.getText().toString();
                username=e8.getText().toString();
                password=e9.getText().toString();

                JsonReq JR = new JsonReq();
                JR.json_response = (JsonResponse) User_register.this;
                String q = "/user_register?firstname="+firstname+"&lastname="+lastname+"&housename="+housename+"&place="+place+"&pincode="+pincode+"&phone="+phone+"&email="+email+"&username="+username+"&password="+password+"&lati="+LocationService.lati+"&longi="+LocationService.logi;
                q = q.replace(" ", "%20");
                JR.execute(q);



            }
        });
    }

    @Override
    public void response(JSONObject jo) throws JSONException {



        try {
            String status = jo.getString("status");
            Log.d("pearl", status);

            if (status.equalsIgnoreCase("success")) {

                Toast.makeText(getApplicationContext(), "Registration Succesfull", Toast.LENGTH_LONG).show();
                startActivity(new Intent(getApplicationContext(),Login.class));

            }
        } catch (Exception e) {
            // TODO: handle exception

            Toast.makeText(getApplicationContext(), e.toString(), Toast.LENGTH_LONG).show();
        }

    }

    @Override
    public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {

    }
}
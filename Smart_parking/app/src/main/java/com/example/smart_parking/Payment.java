package com.example.smart_parking;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import org.json.JSONException;
import org.json.JSONObject;

public class Payment extends AppCompatActivity implements JsonResponse{

    TextView t1;

    Button b1;

    String amount;


    EditText e1,e2,e3;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_payment);
        t1=findViewById(R.id.amount);
        e1=findViewById(R.id.cardno);
        e2=findViewById(R.id.cvv);
        e3=findViewById(R.id.name);
        b1=findViewById(R.id.button);

        t1.setText(Notifications.amt);


        b1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                amount=t1.getText().toString();

                JsonReq JR = new JsonReq();
                JR.json_response = (JsonResponse) Payment.this;
                String q = "/payment?amount=" + amount + "&checkin=" + Notifications.checkid;
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

                Toast.makeText(getApplicationContext(), "PAYMENT COMPLETED SUCCESSFULLY...THANK YOU", Toast.LENGTH_LONG).show();
                startActivity(new Intent(getApplicationContext(),User_home.class));

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
package com.example.smart_parking;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.text.method.HideReturnsTransformationMethod;
import android.text.method.PasswordTransformationMethod;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;



import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class Login extends AppCompatActivity implements JsonResponse{



    Button b1,b2;


    EditText e1,e2;

    String username,password,parent_id,usertype,loginid,first_name;


    SharedPreferences sh;

    TextView t1;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        sh= PreferenceManager.getDefaultSharedPreferences(getApplicationContext());



        t1=findViewById(R.id.textView21);
        e1=(EditText) findViewById(R.id.username);
        e2=(EditText) findViewById(R.id.password);
        CheckBox showPasswordCheckbox = findViewById(R.id.show_password_checkbox);
        showPasswordCheckbox.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked) {
                    e2.setTransformationMethod(HideReturnsTransformationMethod.getInstance());
                } else {
                    e2.setTransformationMethod(PasswordTransformationMethod.getInstance());
                }
            }
        });
        b2=(Button) findViewById(R.id.login);
        b2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                username=e1.getText().toString();
                password=e2.getText().toString();
                if (username.equalsIgnoreCase("")) {
                    e1.setError("Enter Your Username");
                    e1.setFocusable(true);
                } else if (password.equalsIgnoreCase("")) {
                    e2.setError("Enter Your Password");
                    e2.setFocusable(true);


                } else {


                    JsonReq JR = new JsonReq();
                    JR.json_response = (JsonResponse) Login.this;
                    String q = "/userlogin?username=" + username + "&password=" + password;
                    q = q.replace(" ", "%20");
                    JR.execute(q);
                }

            }
        });




        t1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getApplicationContext(),User_register.class));
            }
        });
    }

    @Override
    public void response(JSONObject jo) throws JSONException {
        try {
            String status = jo.getString("status");
            Log.d("pearl", status);

            if (status.equalsIgnoreCase("success")) {
                JSONArray ja1 = (JSONArray) jo.getJSONArray("data");
                loginid = ja1.getJSONObject(0).getString("login_id");
                usertype = ja1.getJSONObject(0).getString("user_type");
                first_name = ja1.getJSONObject(0).getString("first_name");



                SharedPreferences.Editor e = sh.edit();
                e.putString("loginid", loginid);
                e.putString("firstname",first_name);
                e.commit();


                if (usertype.equals("user")) {

                    Toast.makeText(getApplicationContext(), "Login Successfull", Toast.LENGTH_LONG).show();
                    startActivity(new Intent(getApplicationContext(), User_home.class));
                }





            } else if (status.equalsIgnoreCase("failed")) {
                Toast.makeText(getApplicationContext(), "invalid username & Password", Toast.LENGTH_LONG).show();


            }


            } catch (Exception e) {
            // TODO: handle exception

            Toast.makeText(getApplicationContext(), e.toString(), Toast.LENGTH_LONG).show();
        }

    }



    @Override
    public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {

    }

    public void onBackPressed()
    {
        startActivity(new Intent(getApplicationContext(),IpSetting.class));
    }
}
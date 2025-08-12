package com.example.smart_parking;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.app.DatePickerDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.text.InputType;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.Calendar;

public class View_slots extends AppCompatActivity implements JsonResponse{

    ListView l1;

    String[] name,status1,amount,value,slot;

    SharedPreferences sh;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_slots);
        sh= PreferenceManager.getDefaultSharedPreferences(getApplicationContext());

        l1=findViewById(R.id.list);

        JsonReq JR = new JsonReq();
        JR.json_response = (JsonResponse) View_slots.this;
        String q = "/view_slot?location="+View_nearby_location.loc_id;
        q = q.replace(" ", "%20");
        JR.execute(q);

    }

    @Override
    public void response(JSONObject jo) throws JSONException {
        try {
            String method = jo.getString("method");
            if (method.equalsIgnoreCase("view")) {

                String status = jo.getString("status");
                Log.d("pearl", status);

                if (status.equalsIgnoreCase("success")) {
                    Toast.makeText(getApplicationContext(), "lllllllllllllllllll", Toast.LENGTH_LONG).show();

                    JSONArray ja1 = (JSONArray) jo.getJSONArray("data");
                    name = new String[ja1.length()];

                    status1 = new String[ja1.length()];
                    amount = new String[ja1.length()];
                    slot = new String[ja1.length()];


                    value = new String[ja1.length()];


                    for (int i = 0; i < ja1.length(); i++) {
                        name[i] = ja1.getJSONObject(i).getString("name");
                        status1[i] = ja1.getJSONObject(i).getString("status");
                        amount[i] = ja1.getJSONObject(i).getString("amount");
                        slot[i] = ja1.getJSONObject(i).getString("slot_id");


                        value[i] = "name : " + name[i] + "\nstatus : " + status1[i] + "\namount : " + amount[i];


//                        Toast.makeText(getApplicationContext(),val[i], Toast.LENGTH_SHORT).show();
//                        val[i]="Fuel Type : "+fuel_type[i]+"\nVehicle : "+vehicle[i]+"\nReg.No : "+regnum[i]+"\nDriver Name : "+dname[i]+"\nPhone : "+phone[i]+"\nEmail : "+email[i];


                    }
                    Custom_slot ar = new Custom_slot(View_slots.this, name, amount);
                    l1.setAdapter(ar);


//                l1.setOnItemClickListener(new AdapterView.OnItemClickListener() {
//                    @Override
//                    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
//                        // Get the status of the clicked item
//                        String itemStatus = status1[position];
//
//                        // Check the status and show the dialog accordingly
//                        if(itemStatus.equalsIgnoreCase("free")) {
//                            AlertDialog.Builder builder = new AlertDialog.Builder(View_slots.this);
//                            builder.setTitle("ENTER VEHICLE NUMBER");
//
//                            // Set up the input
//                            final EditText input = new EditText(View_slots.this);
//                            input.setInputType(InputType.TYPE_CLASS_TEXT);
//                            builder.setView(input);
//
//                            // Set up the buttons
//                            builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
//                                @Override
//                                public void onClick(DialogInterface dialog, int which) {
//                                    String userInput = input.getText().toString();
//
//                                    JsonReq JR = new JsonReq();
//                                    JR.json_response = (JsonResponse) View_slots.this;
//                                    String q = "/bookslot?vehiclenumber="+userInput+"&id="+sh.getString("loginid","")+"&slotid="+slot[position];
//                                    q = q.replace(" ", "%20");
//                                    JR.execute(q);
//
//                                }
//                            });
//                            builder.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
//                                @Override
//                                public void onClick(DialogInterface dialog, int which) {
//                                    dialog.cancel();
//                                }
//                            });
//
//                            builder.show();
//                        } else {
//                            // Handle other statuses if needed
//                        }
//                    }
//                });
//
//            }
                    l1.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                        @Override
                        public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                            // Get the status of the clicked item
                            String itemStatus = status1[position];

                            // Check the status and show the dialog accordingly
                            // Create a DatePickerDialog to choose the date
                            DatePickerDialog datePickerDialog = new DatePickerDialog(View_slots.this, new DatePickerDialog.OnDateSetListener() {
                                @Override
                                public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                                    // Handle the selected date
                                    String selectedDate = year + "-" + (month + 1) + "-" + dayOfMonth;

                                    // Show the dialog to enter the vehicle number
                                    AlertDialog.Builder builder = new AlertDialog.Builder(View_slots.this);
                                    builder.setTitle("ENTER VEHICLE NUMBER");

                                    // Set up the input
                                    final EditText input = new EditText(View_slots.this);
                                    input.setInputType(InputType.TYPE_CLASS_TEXT);
                                    builder.setView(input);

                                    // Set up the buttons
                                    builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
                                        @Override
                                        public void onClick(DialogInterface dialog, int which) {
                                            String userInput = input.getText().toString();

                                            JsonReq JR = new JsonReq();
                                            JR.json_response = (JsonResponse) View_slots.this;
                                            String q = "/bookslot?vehiclenumber=" + userInput + "&id=" + sh.getString("loginid", "") + "&slotid=" + slot[position] + "&date=" + selectedDate;
                                            q = q.replace(" ", "%20");
                                            JR.execute(q);
                                        }
                                    });
                                    builder.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                                        @Override
                                        public void onClick(DialogInterface dialog, int which) {
                                            dialog.cancel();
                                        }
                                    });

                                    builder.show();
                                }
                            }, Calendar.getInstance().get(Calendar.YEAR), Calendar.getInstance().get(Calendar.MONTH), Calendar.getInstance().get(Calendar.DAY_OF_MONTH));

                            // Show the DatePickerDialog
                            datePickerDialog.show();

                        }
                    });
                }

            }if(method.equalsIgnoreCase("book")){
                String status=jo.getString("status");
                if(status.equalsIgnoreCase("success")){
                    Toast.makeText(this, "Booked Successfully", Toast.LENGTH_SHORT).show();
                }else
                {
                    Toast.makeText(this, status, Toast.LENGTH_SHORT).show();
                }
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
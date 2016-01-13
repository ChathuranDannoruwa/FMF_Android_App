package com.example.chathuran.fmf_android_app;

/**
 * Created by USER on 12/30/2015.
 */


import android.app.DialogFragment;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.design.widget.TextInputLayout;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;

import org.json.JSONArray;
import org.json.JSONObject;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.Map;


public class BookingWidgetFragmentRound extends Fragment {
    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @return A new instance of fragment BookingWidgetFragment.
     */
    int id; //variabl eto indiacte one way or roound trip

    public static BookingWidgetFragmentRound newInstance(int id) {
        return new BookingWidgetFragmentRound(id);
    }


    AutoCompleteTextView from_1;
    AutoCompleteTextView to_1;
    EditText date_1;
    EditText date_2;
    EditText passengers;
    EditText hiddenPassenger;
    Button search_flight_submit;
    TextInputLayout return_layout;
    JSONObject jsonobject;
    JSONArray jsonarray;
    String hdn_flt_type = "RT";
    ArrayList<String> worldlist;
    final String selBCos = "y";
    final String airline = "";
    final String multicity_count = "2";
    String date1;
    String date2;
   String selBAdt;
   String selBChld;
   String selBInf ;
    String passeng;

    String url = "https://ibe.findmyfare.lk/search_flight2.php";

    public BookingWidgetFragmentRound(int id) {

        this.id = id;
    }


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_fragment2, container, false);
        date_1 = (EditText) view.findViewById(R.id.date1_f2);
        date_2 = (EditText) view.findViewById(R.id.date2_f2);
        passengers = (EditText) view.findViewById(R.id.passengers_f2);
        hiddenPassenger = (EditText) view.findViewById(R.id.hidden_field);
        search_flight_submit = (Button) view.findViewById(R.id.search_flight_submit_f1);
        return_layout = (TextInputLayout) view.findViewById(R.id.date2_layout);
        from_1 = (AutoCompleteTextView) view.findViewById(R.id.from_1_f1);//populate the autocomplete list for from field
        new DownloadJSON().execute();
        to_1 = (AutoCompleteTextView) view.findViewById(R.id.to_1_f1);//populate the autocomplete list for to field
        new DownloadJSONForTo().execute();
        if(id == 1) {
            hdn_flt_type = "OT";
            date_2.setEnabled(false);
            return_layout.setVisibility(TextInputLayout.GONE);
        }

        //to set the date
        Calendar c = Calendar.getInstance();
        SimpleDateFormat df = new SimpleDateFormat("dd/MM/yyyy");
        c.add(Calendar.DATE, 4);
        String formattedDate = df.format(c.getTime());
        date_1.setText(formattedDate.toString());
        c.add(Calendar.DATE, 7);
        formattedDate = df.format(c.getTime());
        date_2.setText(formattedDate.toString());

        //on click action of search button
        search_flight_submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                final String from, to;

                date1 = date_1.getText().toString();
                from = from_1.getText().toString();
                if (id == 1) {
                    hdn_flt_type = "OT";
                    date2 = " ";
                } else {
                    date2 = date_2.getText().toString();
                }
                to = to_1.getText().toString();
                passeng = hiddenPassenger.getText().toString();
                String[] separated = passeng.split(" ");
                 selBAdt = separated[0];
                selBChld = separated[2];
                selBInf = separated[4];
                //make a JSON request through Volley and get the response
                CustomJSONObjectRequest jsObjRequest = new CustomJSONObjectRequest(Request.Method.POST, url, null, new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {


                        try {

                            JSONObject sendResult = new JSONObject((response.toString()));
                            Log.d("msg", sendResult.toString());
                            Toast.makeText(getContext(), sendResult.toString(), Toast.LENGTH_SHORT).show();

                        } catch (Exception e) {
                            e.printStackTrace();
                        }


                    }
                }, new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Toast.makeText(getContext(), error.toString(), Toast.LENGTH_SHORT).show();
                    }
                }) {
                    @Override
                    protected Map<String, String> getParams() throws AuthFailureError {

                        Map<String, String> params = new HashMap<String, String>();
                        params.put("from_1", from);
                        params.put("to_1", to);
                        params.put("dd", date1);
                        params.put("rtd", date2);
                        params.put("hdn_flt_type", hdn_flt_type);
                        params.put("selBChld", selBChld);
                        params.put("selBAdt", selBAdt);
                        params.put("selBInf", selBInf);
                        params.put("airline", airline);
                        params.put("multicity_count", multicity_count);
                        params.put("selBCos", selBCos);
                        params.put("mapp","1");
                        return params;
                    }

                };


                MyVolleySingleton.getInstance(getContext()).addToRequestQueue(jsObjRequest);


            }
        });

        date_1.setOnTouchListener(new View.OnTouchListener() {

            @Override
            public boolean onTouch(View v, MotionEvent event) {
                Log.v("click", "onMtouch");
                Toast.makeText(getContext(),getActivity().getSupportFragmentManager().toString(),Toast.LENGTH_LONG).show();
                switch (event.getAction()) {
                    case MotionEvent.ACTION_DOWN:
                        DialogFragment newFragment = new DatePickerFragment2();
                        newFragment.show(getActivity().getFragmentManager(), "Date Picker");
                        break;
                    case MotionEvent.ACTION_MOVE:

                        break;
                    case MotionEvent.ACTION_UP:

                        break;
                }

                return false;
            }

        });
        if (id != 1) {
            date_2.setOnTouchListener(new View.OnTouchListener() {

                @Override
                public boolean onTouch(View v, MotionEvent event) {

                    switch (event.getAction()) {
                        case MotionEvent.ACTION_DOWN:
                            DialogFragment newFragment = new DatePickerFragment3();
                            newFragment.show(getActivity().getFragmentManager(), "Date Picker");
                            break;
                        case MotionEvent.ACTION_MOVE:

                            break;
                        case MotionEvent.ACTION_UP:

                            break;
                    }

                    return false;
                }

            });
        }


        passengers.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {

                switch (event.getAction()) {
                    case MotionEvent.ACTION_DOWN:
                        DialogFragment newFragment = new NumberPickerFragment2();
                        newFragment.show(getActivity().getFragmentManager(), "Number Picker");
                        break;
                    case MotionEvent.ACTION_MOVE:

                        break;
                    case MotionEvent.ACTION_UP:

                        break;
                }


                return false;
            }
        });


        return view;

    }
//to get the list for  from field
    class DownloadJSON extends AsyncTask<Void, Void, Void> {

        @Override
        protected Void doInBackground(Void... params) {

            // Create an array to populate the list
            worldlist = new ArrayList<String>();
            // JSON file URL address
            jsonarray = JSONFunctions
                    .getJSONfromURL("https://www.findmyfare.com/citiezz.json");
            //    Toast.makeText(getApplicationContext(),jsonobject.toString(), Toast.LENGTH_SHORT).show();
            try {
                // Locate the NodeList name
                jsonobject = jsonarray.getJSONObject(0);
                for (int i = 0; i < jsonarray.length(); i++) {
                    jsonobject = jsonarray.getJSONObject(i);

                    worldlist.add(jsonobject.getString("label"));

                }
            } catch (Exception e) {
                Log.e("Error", e.getMessage());
                e.printStackTrace();
            }
            return null;
        }

        @Override
        protected void onPostExecute(Void args) {

            SearchableAdapter autoCompleteAdapter = new SearchableAdapter(getContext(), android.R.layout.simple_dropdown_item_1line, worldlist);
            from_1.setThreshold(1);
            from_1.setAdapter(autoCompleteAdapter);

        }
    }
//to get the list for to  field
    class DownloadJSONForTo extends AsyncTask<Void, Void, Void> {

        @Override
        protected Void doInBackground(Void... params) {
            // Create an array to populate the list
            worldlist = new ArrayList<String>();
            // JSON file URL address
            jsonarray = JSONFunctions
                    .getJSONfromURL("https://www.findmyfare.com/citiezz.json");
            try {
                // Locate the NodeList name
                jsonobject = jsonarray.getJSONObject(0);
                for (int i = 0; i < jsonarray.length(); i++) {
                    jsonobject = jsonarray.getJSONObject(i);

                    worldlist.add(jsonobject.getString("label"));

                }
            } catch (Exception e) {
                Log.e("Error", e.getMessage());
                e.printStackTrace();
            }
            return null;
        }

        @Override
        protected void onPostExecute(Void args) {

            SearchableAdapter autoCompleteAdapter = new SearchableAdapter(getContext(), android.R.layout.simple_dropdown_item_1line,worldlist);
            to_1.setThreshold(1);
            to_1.setAdapter(autoCompleteAdapter);

        }
    }


}
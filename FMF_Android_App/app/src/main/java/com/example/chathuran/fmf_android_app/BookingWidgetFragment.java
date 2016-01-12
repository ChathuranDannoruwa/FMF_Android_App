package com.example.chathuran.fmf_android_app;

/**
 * Created by USER on 12/30/2015.
 */


import android.app.Dialog;
import android.app.DialogFragment;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.NumberPicker;
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


public class BookingWidgetFragment extends Fragment {
    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @return A new instance of fragment BookingWidgetFragment.
     */
    int id;
    //Context context= getActivity().getApplicationContext();
    public static BookingWidgetFragment newInstance(int id) {
        return new BookingWidgetFragment(id);
    }


    AutoCompleteTextView from_1;
    AutoCompleteTextView to_1;
    AutoCompleteTextView autoText;
    EditText date_1;
    EditText date_2;
    EditText passengers;
    Button  search_flight_submit;
    JSONObject jsonobject;
    JSONArray jsonarray;
    String hdn_flt_type="RT";
    ArrayList<String> worldlist;
    final String selBCos="y";
    final String airline="";
    final String multicity_count ="2";
    String date1;
    String date2;

    String url = "https://ibe.findmyfare.lk/search_flight2.php";

    public BookingWidgetFragment(int id) {

        this.id=id;
    }

    public BookingWidgetFragment()
    {}

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {



        // Inflate the layout for this fragment
        View view= inflater.inflate(R.layout.fragment_fragment1, container, false);
        date_1=(EditText)view.findViewById(R.id.date1_f1);
        date_2=(EditText)view.findViewById(R.id.date2_f1);
        passengers = (EditText)view.findViewById(R.id.passengers_f1);
        search_flight_submit=(Button)view.findViewById(R.id.search_flight_submit_f1);
        from_1 = (AutoCompleteTextView)view.findViewById(R.id.from_1_f1);//populate the autocomplete list for from field
        new DownloadJSON().execute();
        to_1 = (AutoCompleteTextView) view.findViewById(R.id.to_1_f1);//populate the autocomplete list for to field
        new DownloadJSONForTo().execute();
        if(id == 1){
            hdn_flt_type="OT";
            date_2.setEnabled(false);
        }


        Calendar c = Calendar.getInstance();
        SimpleDateFormat df = new SimpleDateFormat("dd/MM/yyyy");
        c.add(Calendar.DATE, 4);
        String formattedDate = df.format(c.getTime());
        date_1.setText(formattedDate.toString());
        c.add(Calendar.DATE, 7);
        formattedDate = df.format(c.getTime());
        date_2.setText(formattedDate.toString());


        search_flight_submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                final String from, to, passeng;

                int selAdt, selChld, day_1, month_1, year_1, day_2, month_2, year_2;
                date1 = date_1.getText().toString();
                from =  from_1.getText().toString();
                if(id == 1){
                    hdn_flt_type="OT";
                    date2=" ";
                }
                else {
                    date2 = date_2.getText().toString();
                }
                to = to_1.getText().toString();
                passeng = passengers.getText().toString();
                String[] separated = passeng.split(" ");
                final String selBAdt=separated[0];
                final String selBChld=separated[2];
                final String selBInf=separated[4];

                CustomJSONObjectRequest jsObjRequest = new CustomJSONObjectRequest(Request.Method.POST, url, null, new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {


                        try {

                            JSONObject sendResult = new JSONObject((response.toString()));
                            Log.d("msg", sendResult.toString());
                            Toast.makeText(getContext(),sendResult.toString(), Toast.LENGTH_SHORT).show();

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
                        params.put("from_1",from);
                        params.put("to_1", to);
                        params.put("dd", date1);
                        params.put("rtd",date2);
                        params.put(" hdn_flt_type", hdn_flt_type);
                        params.put("selBChld", selBChld);
                        params.put("selBAdt", selBAdt);
                        params.put(" selBInf", selBInf);
                        params.put("airline", airline);
                        params.put("multicity_count", multicity_count);
                        params.put("selBCos", selBCos);
                        params.put("mapp","1");
                        return params;
                    }

                };


                //jsObjRequest.setShouldCache(false);
                MyVolleySingleton.getInstance(getContext()).addToRequestQueue(jsObjRequest);





    }
        });

        date_1.setOnTouchListener(new View.OnTouchListener() {

            @Override
            public boolean onTouch(View v, MotionEvent event) {
                Log.v("click", "onMtouch");
                switch (event.getAction()) {
                    case MotionEvent.ACTION_DOWN:
                        DialogFragment newFragment = new DatePickerFragment1();
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
        if(id != 1) {
            date_2.setOnTouchListener(new View.OnTouchListener() {

                @Override
                public boolean onTouch(View v, MotionEvent event) {

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
        }


        passengers.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {

                switch (event.getAction()) {
                    case MotionEvent.ACTION_DOWN:
                        DialogFragment newFragment = new NumberPickerFragment();
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

            SearchableAdapter autoCompleteAdapter = new SearchableAdapter(getContext(), android.R.layout.simple_list_item_1, worldlist);
            from_1.setThreshold(1);
            from_1.setAdapter(autoCompleteAdapter);

        }
    }

    class DownloadJSONForTo extends AsyncTask<Void, Void, Void> {

        @Override
        protected Void doInBackground(Void... params) {
            // Locate the WorldPopulation Class
            //    world = new ArrayList<WorldPopulation>();
            // Create an array to populate the spinner
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

            SearchableAdapter autoCompleteAdapter = new SearchableAdapter(getContext(), android.R.layout.simple_list_item_1, worldlist);
            to_1.setThreshold(1);
            to_1.setAdapter(autoCompleteAdapter);

        }
    }

    public void show_passenger()
    {

        final Dialog d = new Dialog(getActivity().getApplicationContext());
        d.setTitle("NumberPicker");
        d.setContentView(R.layout.dialog);
        Button b1 = (Button) d.findViewById(R.id.add);
        // Button b2 = (Button) d.findViewById(R.id.button2);

        final NumberPicker adult = (NumberPicker) d.findViewById(R.id.adult);
        final NumberPicker child = (NumberPicker) d.findViewById(R.id.children);
        final NumberPicker infant = (NumberPicker) d.findViewById(R.id.infant);
        adult.setMaxValue(9); // max value 100
        child.setMaxValue(9); // max value 100
        infant.setMaxValue(9); // max value 100
        adult.setMinValue(1);   // min value 0
        child.setMinValue(0);   // min value 0
        infant.setMinValue(0);   // min value 0
        adult.setWrapSelectorWheel(false);
        child.setWrapSelectorWheel(false);
        infant.setWrapSelectorWheel(false);
        //    np.setOnValueChangedListener(this);

        b1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String adultVal, childVal, infantVal;
                adultVal = String.valueOf(adult.getValue());
                childVal = String.valueOf(child.getValue());
                infantVal = String.valueOf(infant.getValue());
                d.dismiss();
                passengers.setText(adultVal + " Adult| " + childVal + " Children| " + infantVal + " Infant");


                //Toast.makeText(MainActivity.this, v1, Toast.LENGTH_LONG).show();
            }
        });

        d.show();
    }
    public void get_date(final int type)
    {


        final String date1Val,date2Val ;

        final Dialog d = new Dialog(getActivity().getApplicationContext());
        if(type == 1){
            d.setTitle("Select Departure date");
        }else{
            d.setTitle("Select Arrival Date");
        }

        d.setContentView(R.layout.select_date_popup);
        Button b1 = (Button) d.findViewById(R.id.add_date);
        // Button b2 = (Button) d.findViewById(R.id.button2);

        final DatePicker date = (DatePicker) d.findViewById(R.id.date);



        b1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                int day_1,month_1,year_1,day_2,month_2,year_2;
                d.dismiss();
                if(type == 1){
                    day_1=date.getDayOfMonth();
                    month_1=date.getMonth()+1;
                    year_1=date.getYear();

                    date_1.setText(day_1+"/"+month_1+"/"+year_1);

                }else{
                    day_2=date.getDayOfMonth();
                    month_2=date.getMonth()+1;
                    year_2=date.getYear();

                    date_2.setText(day_2+"/"+month_2+"/"+year_2);
                }



                //Toast.makeText(MainActivity.this, v1, Toast.LENGTH_LONG).show();
            }
        });

        d.show();
    }


}
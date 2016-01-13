package com.example.chathuran.fmf_android_app;


import android.annotation.TargetApi;
import android.app.DialogFragment;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.NumberPicker;

public class NumberPickerFragment2 extends DialogFragment  {


    @Override

    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        final View numberView = inflater.inflate(R.layout.dialog, container, false);
        final NumberPicker adult = (NumberPicker) numberView.findViewById(R.id.adult);
        final NumberPicker child = (NumberPicker) numberView.findViewById(R.id.children);
        final NumberPicker infant = (NumberPicker) numberView.findViewById(R.id.infant);


        getDialog().setTitle("Select");
        final EditText hiddenPassenger=(EditText)getActivity().findViewById(R.id.hidden_field);
        String passeng = hiddenPassenger.getText().toString();
        String[] separated = passeng.split(" ");
        final String selBAdt=separated[0];
        final String selBChld=separated[2];
        final String selBInf=separated[4];

        final Button b1 = (Button) numberView.findViewById(R.id.add);

        adult.setMaxValue(9); // max value 100
        adult.setValue(Integer.parseInt(selBAdt));
        child.setMaxValue(9); // max value 100
        child.setValue(Integer.parseInt(selBChld));
        infant.setMaxValue(9); // max value 100
        infant.setValue(Integer.parseInt(selBInf));
        adult.setMinValue(1);   // min value 0
        child.setMinValue(0);   // min value 0
        infant.setMinValue(0);   // min value 0
        adult.setWrapSelectorWheel(false);
        child.setWrapSelectorWheel(false);
        infant.setWrapSelectorWheel(false);

        View mainAct = getActivity().getLayoutInflater().inflate(R.layout.fragment_fragment2, null);
        View MainView = inflater.inflate(R.layout.fragment_fragment2, container, true);
        final EditText passengers=(EditText)getActivity().findViewById(R.id.passengers_f2);


        //Toast.makeText(getActivity(),  String.valueOf(adult.getValue()), Toast.LENGTH_LONG).show();
        b1.setOnClickListener(new View.OnClickListener() {
            @TargetApi(Build.VERSION_CODES.JELLY_BEAN_MR1)
            @Override
            public void onClick(View v) {
                // Toast.makeText(getActivity(),  String.valueOf(adult.getValue()), Toast.LENGTH_LONG).show();
                String adultVal, childVal, infantVal;
                adultVal = String.valueOf(adult.getValue());
                childVal = String.valueOf(child.getValue());
                infantVal = String.valueOf(infant.getValue());
                Log.v("abc", "2sd");
                hiddenPassenger.setText(adultVal + " Adult| " + childVal + " Children| " + infantVal + " Infant");
                int total = adult.getValue() + child.getValue() + infant.getValue();
                //passengers.setText(String.valueOf(total));
                passengers.setText(String.valueOf(total));
               // Toast.makeText(getActivity(),(String.valueOf(total)), Toast.LENGTH_LONG).show();
                getDialog().dismiss();
            }
        });


        return numberView;
    }








}

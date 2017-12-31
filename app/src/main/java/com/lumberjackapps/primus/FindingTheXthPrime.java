package com.lumberjackapps.primus;


import android.os.Bundle;
import android.os.Handler;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;


/**
 * A simple {@link Fragment} subclass.
 */
public class FindingTheXthPrime extends Fragment {


    public FindingTheXthPrime() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        // Inflate the layout for this fragment
        final View view = inflater.inflate(R.layout.main_calculation_layout, container, false);

        //Resetting Views
        TextView resultsView = (TextView) view.findViewById(R.id.results);
        TextView enterANumber = (TextView)view.findViewById(R.id.enter_text);
        enterANumber.setText("Enter a number (That'll be X)");
        TextView actionView = (TextView)view.findViewById(R.id.action_text);
        resultsView.setText("");
        actionView.setText("I'll find up to the Xth prime.");
        final Handler handler = new Handler ();
        final ProgressBar spinner = (ProgressBar) view.findViewById(R.id.loading_spinner);

        //Listening for Button to calculate.
        final Button calculateButton = (Button) view.findViewById(R.id.calc2_button);
        calculateButton.setOnClickListener(new View.OnClickListener() {
                                               //Once clicked, it does these lines
                                               @Override
                                               public void onClick(View v) {
                                                   Runnable calculator = new Runnable() {
                                                       @Override
                                                       public void run() {
                                                         try{
                                                             final TextView resultsView = (TextView) view.findViewById(R.id.results);
                                                             EditText input = (EditText) view.findViewById(R.id.edit_view);
                                                             String inputR = input.getText().toString();
                                                             spinner.setProgress(0);
                                                             //Turning input to Integer
                                                             final long number = Integer.parseInt(inputR);

                                                             if (number > 2 && number < 50000){
                                                                 final ArrayList<Long> primes = new ArrayList <Long> ();
                                                                 handler.post(new Runnable() {
                                                                     @Override
                                                                     public void run() {
                                                                         spinner.setMax((int) number);
                                                                         spinner.setVisibility(View.VISIBLE);
                                                                         resultsView.setText("Calculating... This may take a while...");
                                                                     }
                                                                 });
                                                                 int count = 0;
                                                                 //Doing the calculations
                                                                 for (long num = 2; ;num++ ){
                                                                     boolean sign = false;
                                                                     int squareroot = MainActivity.getSquareRoot((int)num);
                                                                     for (int divider = 2; divider<squareroot; divider++ ){
                                                                         if (num%divider == 0){
                                                                             sign = true;
                                                                             break;
                                                                         }
                                                                     }
                                                                     if (sign == false){
                                                                         primes.add (num);
                                                                         count += 1;
                                                                         handler.post(new Runnable() {
                                                                             @Override
                                                                             public void run() {
                                                                                 spinner.incrementProgressBy(1);
                                                                             }
                                                                         });
                                                                     }
                                                                     if (count == number){
                                                                         break;
                                                                     }
                                                                 }

                                                                 //Printing results
                                                                 handler.post(new Runnable() {
                                                                     @Override
                                                                     public void run() {
                                                                         resultsView.setText("The first " + number + " primes are\n " + primes);
                                                                         spinner.setVisibility(View.GONE);
                                                                     }
                                                                 });
                                                             }else{
                                                                 handler.post(new Runnable() {
                                                                     @Override
                                                                     public void run() {
                                                                         Toast.makeText(getActivity(),"This number is out of range. It has to be less than 50,000 and larger than 2.", Toast.LENGTH_LONG).show();
                                                                     }
                                                                 });
                                                             }

                                                         }catch (Exception ex){
                                                             handler.post(new Runnable() {
                                                                 @Override
                                                                 public void run() {
                                                                     Toast.makeText(getActivity(), "Be sure to enter something. It has to be less than 50,000 and larger than 2.", Toast.LENGTH_LONG).show();
                                                                 }
                                                             });
                                                         }
                                                       }
                                                   };
                                                   new Thread(calculator).start();
                                               }

                                           }

        );
        return view;

    }

}


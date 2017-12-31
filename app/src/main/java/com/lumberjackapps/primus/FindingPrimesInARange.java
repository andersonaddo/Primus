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
import java.util.Scanner;


/**
 * A simple {@link Fragment} subclass.
 */
public class FindingPrimesInARange extends Fragment {


    public FindingPrimesInARange() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        final View view = inflater.inflate(R.layout.range_layout, container, false);
        //Resetting Views
        TextView resultsView = (TextView) view.findViewById(R.id.results);
        resultsView.setText("");
        final  ProgressBar spinner = (ProgressBar) view.findViewById(R.id.loading_spinner);
        spinner.setProgress(0);
        final Button calcButton = (Button) view.findViewById(R.id.calc2_button);
        final Handler handler = new Handler ();

        //Listening for Button to calculate.
        Button calculateButton = (Button) view.findViewById(R.id.calc2_button);
        calculateButton.setOnClickListener(new View.OnClickListener() {
                                               //Once clicked, it does these lines
                                               @Override
                                               public void onClick(View v) {
                                                   Runnable runnable = new Runnable() {
                                                       @Override
                                                       public void run() {
                                                           final TextView resultsView = (TextView) view.findViewById(R.id.results);

                                                           //Finding lower bound
                                                           EditText input = (EditText) view.findViewById(R.id.lower_edit_view);
                                                           String inputR = input.getText().toString();


                                                           //Finding higher bound
                                                           EditText input2 = (EditText) view.findViewById(R.id.higher_edit_view);
                                                           String inputR2 = input2.getText().toString();


                                                           try {
                                                               final ArrayList<Long> primes = new ArrayList<Long>();
                                                               final int numberHigh = Integer.parseInt(inputR2);
                                                               final int numberLow = Integer.parseInt(inputR);
                                                               //Doing the calculations
                                                               if (numberLow < numberHigh && numberLow > 1 && numberHigh > 2 && numberHigh < 120000 && numberLow < 120000) {
                                                                   handler.post(new Runnable() {
                                                                       @Override
                                                                       public void run() {
                                                                           resultsView.setText("Calculating...");
                                                                           spinner.setVisibility(View.VISIBLE);
                                                                           spinner.setMax(numberHigh - numberLow);
                                                                           spinner.setProgress(0);
                                                                           calcButton.setEnabled(false);
                                                                       }
                                                                   });

                                                                   //Iterating through the range and determining all for primeness
                                                                   for (long num = numberLow; num < numberHigh; num++) {
                                                                       boolean sign = false;

                                                                       //Updating spinner bar...
                                                                       handler.post(new Runnable() {
                                                                           @Override
                                                                           public void run() {
                                                                               spinner.incrementProgressBy(1);
                                                                           }
                                                                       });

                                                                       int number2 = MainActivity.getSquareRoot((int)num);
                                                                       for (int divider = 2; divider < number2; divider++) {
                                                                           if (num % divider == 0) {
                                                                               sign = true;
                                                                               break;
                                                                           }
                                                                       }
                                                                       if (sign == false) {
                                                                           primes.add(num);
                                                                       }
                                                                   }

                                                                   //Printing outcomes now...
                                                                   handler.post(new Runnable() {
                                                                       @Override
                                                                       public void run() {
                                                                           spinner.setVisibility(View.GONE);
                                                                           resultsView.setText("The prime numbers between " + numberLow + " and " + numberHigh + " are \n" + primes);
                                                                            calcButton.setEnabled(true);
                                                                       }
                                                                   });
                                                               } else {
                                                                   handler.post(new Runnable() {
                                                                       @Override
                                                                       public void run() {
                                                                           Toast.makeText(getActivity(), "These parameters aren't valid. The lower params to be lower than the higher param, which have to be above 1 and 2 respectively and all below 120,000", Toast.LENGTH_LONG).show();
                                                                       }
                                                                   });

                                                               }

                                                               //Error trap
                                                           } catch (Exception ex) {
                                                               handler.post(new Runnable() {
                                                                   @Override
                                                                   public void run() {
                                                                       Toast.makeText(getActivity(), "Please enter something. Be sure that they are both below 120,000", Toast.LENGTH_SHORT).show();
                                                                   }
                                                               });
                                                           }
                                                       }
                                                   };
                                                        new Thread(runnable).start();

                                               }
                                           }
                                                   );
        return view;

    }
    }
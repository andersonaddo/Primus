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
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;


/**
 * A simple {@link Fragment} subclass.
 */
public class FindingPrimesUpToX extends Fragment {


    public FindingPrimesUpToX() {
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
        enterANumber.setText("Enter a number");
        TextView actionView = (TextView)view.findViewById(R.id.action_text);
        resultsView.setText("");
        actionView.setText("I'll find all the primes below it.");
        final ProgressBar spinner = (ProgressBar) view.findViewById(R.id.loading_spinner);
        spinner.setVisibility(View.INVISIBLE);
        final Handler handler = new Handler ();

        //Listening for Button to calculate.
        final Button calculateButton = (Button) view.findViewById(R.id.calc2_button);
        calculateButton.setOnClickListener(new View.OnClickListener() {
                                               //Once clicked, it does these lines
                                               @Override
                                               public void onClick(View v) {


                                                   Runnable calculator = new Runnable() {
                                                       @Override
                                                       public void run() {
                                                           //All this is going on behind the scenes as it is done by the Runnable
                                                           final TextView resultsView = (TextView) view.findViewById(R.id.results);
                                                           final EditText input = (EditText) view.findViewById(R.id.edit_view);
                                                           final String number2 = input.getText().toString();
                                                           final Button calcButton = (Button) view.findViewById(R.id.calc2_button);
                                                           try {
                                                               final long number = Long.parseLong(number2);
                                                               final ArrayList<Integer> primes = new ArrayList<Integer>();
                                                               if (number > 2 && number < 500000) {
                                                                   handler.post(new Runnable() {
                                                                       @Override
                                                                       public void run() {
                                                                           resultsView.setText("Calculating...This may take a while...(maybe a few minutes)");
                                                                           spinner.setVisibility(View.VISIBLE);
                                                                           calcButton.setEnabled(false);
                                                                           spinner.setMax((int) number);
                                                                           spinner.setProgress(0);
                                                                       }
                                                                   });
                                                                   for (int num = 2; num < number; num++) {
                                                                       int squareroot = MainActivity.getSquareRoot(num);
                                                                       boolean sign = false;
                                                                       //Update the Progress Bar every step of the way
                                                                       handler.post(new Runnable() {
                                                                           @Override
                                                                           public void run() {
                                                                               spinner.incrementProgressBy(1);
                                                                           }
                                                                       });
                                                                       for (int divider = 2; divider < squareroot; divider++) {
                                                                           if (num % divider == 0) {
                                                                               sign = true;
                                                                               break;
                                                                           }
                                                                       }
                                                                       if (sign == false) {
                                                                           primes.add(num);
                                                                       }
                                                                   }
                                                                   handler.post(new Runnable() {
                                                                       @Override
                                                                       public void run() {
                                                                           spinner.setVisibility(View.INVISIBLE);
                                                                           calcButton.setEnabled(true);
                                                                           resultsView.setText("Number of Primes: " + primes.size() + "\n\nThe prime numbers below " + number + " are \n" + primes);
                                                                       }
                                                                   });
                                                               } else if (number < 2 || number >=500000) {
                                                                   handler.post(new Runnable() {
                                                                       @Override
                                                                       public void run() {
                                                                           Toast.makeText(getActivity(), "This number is out of range. It has to be less than 500,000 and more than 2", Toast.LENGTH_SHORT).show();
                                                                       }
                                                                   });
                                                               } else
                                                               handler.post(new Runnable() {
                                                                   @Override
                                                                   public void run() {
                                                                       resultsView.setText("2 is the lowest prime number.");
                                                                   }
                                                               });
                                                           } catch (Exception ex) {
                                                              handler.post(new Runnable() {
                                                                  @Override
                                                                  public void run() {
                                                                      Toast.makeText(getActivity(), "Please enter something. It has to be less than 500,000.", Toast.LENGTH_SHORT).show();
                                                                  }
                                                              });
                                                           }
                                                       }
                                                   };

                                                //Then I start the Runnable
                                                new Thread(calculator).start();
                                               }
                                           }
        );
        return view;

    }
    }



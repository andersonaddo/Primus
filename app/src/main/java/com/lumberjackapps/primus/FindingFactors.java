package com.lumberjackapps.primus;


import android.media.Image;
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
import java.util.Collections;


/**
 * A simple {@link Fragment} subclass.
 */
public class FindingFactors extends Fragment {


    public FindingFactors() {
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
        final ProgressBar spinner = (ProgressBar) view.findViewById(R.id.loading_spinner);
        actionView.setText("I'll find its factors.");
        final Button calcButton = (Button) view.findViewById(R.id.calc2_button);
        final Handler handler = new Handler();
        //Listening for Button to calculate.
        calcButton.setOnClickListener(new View.OnClickListener() {
                                               //Once clicked, it does these lines
                                               @Override
                                               public void onClick(View v) {
                                                   Runnable runnable = new Runnable() {
                                                       @Override
                                                       public void run() {
                                                           try {
                                                               final TextView resultsView = (TextView) view.findViewById(R.id.results);
                                                               EditText input = (EditText) view.findViewById(R.id.edit_view);
                                                               String number2 = input.getText().toString();
                                                               //Turning input to Long
                                                               final long number = Long.parseLong(number2);
                                                               if (number>=2 && number<1000000) {
                                                                   final int root = MainActivity.getSquareRoot((int)number);
                                                                handler.post(new Runnable() {
                                                                    @Override
                                                                    public void run() {
                                                                        resultsView.setText("Calculating...");
                                                                        spinner.setVisibility(View.VISIBLE);
                                                                        spinner.setMax(root);
                                                                        spinner.setProgress(0);
                                                                        calcButton.setEnabled(false);
                                                                    }
                                                                });

                                                                   //Initiating calculations
                                                                   final ArrayList<Integer> factorsList = new ArrayList<Integer>();
                                                                   for (int divisor = 1; divisor <= root; divisor++) {
                                                                       handler.post(new Runnable() {
                                                                           @Override
                                                                           public void run() {
                                                                               spinner.incrementProgressBy(1);
                                                                           }
                                                                       });
                                                                       if (number % divisor == 0) {
                                                                           factorsList.add(divisor);
                                                                           factorsList.add((int) (number/divisor));
                                                                       }
                                                                   }
                                                                   //Finished finding factots, printing results.
                                                                   handler.post(new Runnable() {
                                                                       @Override
                                                                       public void run() {
                                                                           spinner.setVisibility(View.GONE);
                                                                           resultsView.setText("The factors of " + number + " are (arranged in pairs) \n" + factorsList + "\n\nThe factors arranged in ascending order are\n");
                                                                           Collections.sort(factorsList);
                                                                           resultsView.setText(resultsView.getText().toString() + factorsList);
                                                                           calcButton.setEnabled(true);
                                                                       }
                                                                   });

                                                               } else{
                                                                   handler.post(new Runnable() {
                                                                       @Override
                                                                       public void run() {
                                                                           Toast.makeText(getActivity(), "This number is out of range. It should be less than 1,000,000.", Toast.LENGTH_SHORT).show();
                                                                       }
                                                                   });
                                                               }
                                                           } catch (Exception ex) {
                                                               handler.post(new Runnable() {
                                                                   @Override
                                                                   public void run() {
                                                                       Toast.makeText(getActivity(), "Be sure to enter something. It should be less than 1,000,000.", Toast.LENGTH_SHORT).show();
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



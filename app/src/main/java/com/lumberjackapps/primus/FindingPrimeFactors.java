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
public class FindingPrimeFactors extends Fragment {


    public FindingPrimeFactors() {
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
        actionView.setText("I'll find its prime factors.");
        final Handler handler = new Handler();

        //Listening for Button to calculate.
        final Button calculateButton = (Button) view.findViewById(R.id.calc2_button);
        calculateButton.setOnClickListener(new View.OnClickListener() {
                                               //Once clicked, it does these lines
                                               @Override
                                               public void onClick(View v) {
                                                   final TextView resultsView = (TextView) view.findViewById(R.id.results);
                                                   EditText input = (EditText) view.findViewById(R.id.edit_view);
                                                   final String number2 = input.getText().toString();
                                                   //Doing Calculations for factors
                                                    Runnable calculator = new Runnable() {
                                                        @Override
                                                        public void run() {
                                                            try{
                                                                final long number = Long.parseLong(number2);
                                                                if (number>=2 && number<1000000) {
                                                                    handler.post(new Runnable() {
                                                                        @Override
                                                                        public void run() {
                                                                            spinner.setVisibility(View.VISIBLE);
                                                                            resultsView.setText("Calculating...");
                                                                            calculateButton.setEnabled(false);
                                                                            spinner.setProgress(0);
                                                                        }
                                                                    });

                                                                    final ArrayList<Integer> factorsList = new ArrayList<Integer>();
                                                                    for (int divisor = 1; divisor <= number; divisor++) {
                                                                        if (number % divisor == 0) {
                                                                            factorsList.add(divisor);
                                                                        }
                                                                    }

                                                                    //Now that we have the factors, we're going to find the prime ones
                                                                    final ArrayList<Integer> primesList = new ArrayList<Integer>();
                                                                    handler.post(new Runnable() {
                                                                        @Override
                                                                        public void run() {
                                                                            spinner.setMax(factorsList.size());
                                                                        }
                                                                    });
                                                                    for (int factorGetter = 1; factorGetter < factorsList.size(); factorGetter++) {
                                                                        int factor = factorsList.get(factorGetter);
                                                                        handler.post(new Runnable() {
                                                                            @Override
                                                                            public void run() {
                                                                                spinner.incrementProgressBy(1);
                                                                            }
                                                                        });
                                                                        boolean sign = false;
                                                                        /**We're taking each factor by one from the factorsList
                                                                         * And we are testing each one for "primeness"
                                                                         */
                                                                        final int factor2 = MainActivity.getSquareRoot(factor);
                                                                        for (int divisor = 2; divisor < factor2; divisor++) {
                                                                            if (factor % divisor == 0) {
                                                                                sign = true;
                                                                                break;
                                                                            }
                                                                        }

                                                                        if (sign == false) {
                                                                            primesList.add(factor);
                                                                        }
                                                                    }
                                                                    handler.post(new Runnable() {
                                                                        @Override
                                                                        public void run() {
                                                                            spinner.setVisibility(View.GONE);
                                                                            calculateButton.setEnabled(true);
                                                                            resultsView.setText("The prime factors of " + number + " are \n" + primesList);
                                                                        }
                                                                    });
                                                                }else{
                                                                    handler.post(new Runnable() {
                                                                        @Override
                                                                        public void run() {
                                                                            Toast.makeText(getActivity(), "This number is out of range. It should be less than 1,000,000", Toast.LENGTH_SHORT).show();
                                                                        }
                                                                    });
                                                                }
                                                                //Error Trap
                                                            }catch (Exception ex){
                                                                handler.post(new Runnable() {
                                                                    @Override
                                                                    public void run() {
                                                                        Toast.makeText(getActivity(), "Be sure to enter something. It should be less than 1,000,000", Toast.LENGTH_SHORT).show();
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

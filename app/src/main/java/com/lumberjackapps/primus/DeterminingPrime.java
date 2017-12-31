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


/**
 * A simple {@link Fragment} subclass.
 */
public class DeterminingPrime extends Fragment {

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
        actionView.setText("I'll see if it's prime or not.");
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
                        try {
                            final TextView resultsView = (TextView) view.findViewById(R.id.results);
                            EditText input = (EditText) view.findViewById(R.id.edit_view);
                            String inputR = input.getText().toString();
                            //Turning input to Integer
                            final int number = Integer.parseInt(inputR);

                            boolean sign = false;
                            if (number >= 2 && number < 10000000) {
                                final int number2 = MainActivity.getSquareRoot(number);
                                handler.post(new Runnable() {
                                    @Override
                                    public void run() {
                                        resultsView.setText("Calculating...");
                                        spinner.setVisibility(View.VISIBLE);
                                        calculateButton.setEnabled(false);
                                        spinner.setMax(number2);
                                        spinner.setProgress(0);
                                    }
                                });
                                for (int num = 2; num < number2; num++) {
                                    handler.post(new Runnable() {
                                        @Override
                                        public void run() {
                                            spinner.incrementProgressBy(1);
                                        }
                                    });
                                    if (number % num == 0) {
                                        sign = true;
                                        break;
                                    }
                                }
                                handler.post(new Runnable() {
                                    @Override
                                    public void run() {
                                        spinner.setVisibility(View.GONE);
                                    }
                                });
                                if (sign == true) {
                                    handler.post(new Runnable() {
                                        @Override
                                        public void run() {
                                            resultsView.setText("This number isn't prime.");
                                            calculateButton.setEnabled(true);
                                        }
                                    });
                                } else {
                                    handler.post(new Runnable() {
                                        @Override
                                        public void run() {
                                            resultsView.setText("This number IS prime!");
                                            calculateButton.setEnabled(true);
                                        }
                                    });
                                }
                            } else {
                                handler.post(new Runnable() {
                                    @Override
                                    public void run() {
                                        Toast.makeText(getActivity(), "This number is out of range. It should be less than 10,000,000.", Toast.LENGTH_SHORT).show();
                                    }
                                });
                            }
                        }catch (Exception ex){
                           handler.post(new Runnable() {
                               @Override
                               public void run() {
                                   Toast.makeText(getActivity(), "Be sure to enter something. It should be less than 10,000,000", Toast.LENGTH_SHORT).show();
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
        public DeterminingPrime() {
        }
    }


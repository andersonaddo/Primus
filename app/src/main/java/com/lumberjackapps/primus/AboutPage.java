package com.lumberjackapps.primus;


import android.app.Notification;
import android.content.Intent;
import android.net.MailTo;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Toast;

import javax.security.auth.Subject;


/**
 * A simple {@link Fragment} subclass.
 */
public class AboutPage extends Fragment {


    public AboutPage() {
        // Required empty public constructor

    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view =  inflater.inflate(R.layout.fragment_about_page, container, false);
        Button contactButton = (Button) view.findViewById(R.id.contact_button);
        contactButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Intent.ACTION_SEND);
                intent.putExtra(android.content.Intent.EXTRA_EMAIL, new String[]{"lumberjackapps72@gmail.com"});
                intent.putExtra(Intent.EXTRA_SUBJECT, "Primus Suggestion or Issue");
                intent.setType("text/plain");
                if (intent.resolveActivity(getActivity().getPackageManager()) != null) {
                    getActivity().startActivity(Intent.createChooser(intent, "Send Email using:"));
                }
                else {
                    Toast.makeText(getActivity(), "You don't have any email apps to contact us.", Toast.LENGTH_SHORT).show();
                }
            }
        });

        return view;
    }



}

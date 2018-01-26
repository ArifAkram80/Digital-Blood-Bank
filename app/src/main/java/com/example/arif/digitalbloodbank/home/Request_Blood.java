package com.example.arif.digitalbloodbank.home;


import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Toast;

import com.example.arif.digitalbloodbank.R;
import com.example.arif.digitalbloodbank.post.Post_New;

/**
 * A simple {@link Fragment} subclass.
 */
public class Request_Blood extends Fragment {

    public static final String TAG = "Request_Blood_Frag";
    private Button btn_post_new;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_request__blood, container, false);
        return view;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        btn_post_new = view.findViewById(R.id.req_post_new);


        btn_post_new.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent post_new = new Intent(getActivity(), Post_New.class);
                startActivity(post_new);
            }
        });

    }
}

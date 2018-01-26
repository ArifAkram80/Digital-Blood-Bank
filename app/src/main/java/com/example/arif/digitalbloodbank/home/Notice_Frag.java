package com.example.arif.digitalbloodbank.home;


import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.media.session.MediaSessionCompat;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.example.arif.digitalbloodbank.R;
import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;

import java.util.HashMap;

/**
 * A simple {@link Fragment} subclass.
 */
public class Notice_Frag extends Fragment {

    public static final String TAG = "Notice_Frag";
    private Button btn;
    private RecyclerView myBlogList;
    private Spinner bld, loc;
    private String Str_bloodGroup, Str_location;
    FirebaseRecyclerAdapter firebaseRecyclerAdapter;
    DatabaseReference myRef;
    LinearLayoutManager mLayoutManager;
    private FirebaseAuth mAuth;
    private FirebaseUser currentUser;
    private boolean flag;
    private DatabaseReference mNotification;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_notice_, container, false);
        myBlogList = (RecyclerView) view.findViewById(R.id.my_notice_list);






        bld = view.findViewById(R.id.notice_blood);
        loc = view.findViewById(R.id.notice_loc);
        btn = view.findViewById(R.id.search_cfm);

        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(getContext(),
                R.array.Blood_array, R.layout.custom_spinner);
        ArrayAdapter<CharSequence> adapter2 = ArrayAdapter.createFromResource(getContext(),
                R.array.Location_Array, R.layout.custom_spinner);

        adapter.setDropDownViewResource(R.layout.custom_spinner2);
        adapter2.setDropDownViewResource(R.layout.custom_spinner2);

        bld.setBackgroundColor(getResources().getColor(R.color.colorPrimary));
        loc.setBackgroundColor(getResources().getColor(R.color.colorPrimary));

        bld.setAdapter(adapter);
        loc.setAdapter(adapter2);


        mNotification = FirebaseDatabase.getInstance().getReference().child("notifications");
        mAuth = FirebaseAuth.getInstance();

        mLayoutManager = new LinearLayoutManager(getContext());
        mLayoutManager.setReverseLayout(true);
        mLayoutManager.setStackFromEnd(true);
        myRef = FirebaseDatabase.getInstance().getReference().child("Sylhet").child("O(+)ve");
        myBlogList.setLayoutManager(mLayoutManager);

        loc.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                Str_location = loc.getItemAtPosition(i).toString();
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });

        bld.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                Str_bloodGroup = bld.getItemAtPosition(i).toString();
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });
        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                myRef = FirebaseDatabase.getInstance().getReference().child(Str_location).child(Str_bloodGroup);
              //  Toast.makeText(getActivity().getApplicationContext(), getId(), Toast.LENGTH_SHORT).show();

                refresh();
            }
        });

        myBlogList.addItemDecoration(new DividerItemDecoration(getContext(), DividerItemDecoration.VERTICAL));
        mAuth = FirebaseAuth.getInstance();
        return view;

    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);


        refresh();
    }

    void refresh() {

        final Query query = myRef.orderByKey();

        FirebaseRecyclerOptions<user> options =
                new FirebaseRecyclerOptions.Builder<user>()
                        .setQuery(query, user.class)
                        .build();

        firebaseRecyclerAdapter = new FirebaseRecyclerAdapter<user, UserViewHolder>(
                options) {

            @Override
            protected void onBindViewHolder(final UserViewHolder holder, int position, final user model) {

                holder.setName(model.getName());

                final String key = this.getRef(position).getKey();

                currentUser = mAuth.getCurrentUser();

                holder.btn.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        HashMap<String, String> notificationData = new HashMap<>();
                        notificationData.put("From", currentUser.getUid());
                        notificationData.put("type", "request");
                        currentUser=mAuth.getCurrentUser();
                        mNotification.child(model.getId()).push().setValue(notificationData).addOnSuccessListener(new OnSuccessListener<Void>() {
                            @Override
                            public void onSuccess(Void aVoid) {

                            }
                        });

//Toast.makeText(getActivity().getApplicationContext(),model.getId() ,Toast.LENGTH_SHORT).show();
                       // Toast.makeText(getActivity().getApplicationContext(), "Post Notification", Toast.LENGTH_SHORT).show();
//currentUser.getUid();
                    }
                });



            }

            @Override
            public UserViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
                View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.blog_row2, parent, false);
                return new UserViewHolder(view);
            }

        };
        firebaseRecyclerAdapter.startListening();
        myBlogList.setAdapter(firebaseRecyclerAdapter);
    }

    @Override
    public void onStart() {
        super.onStart();
    }

    @Override
    public void onStop() {
        super.onStop();
        firebaseRecyclerAdapter.stopListening();
    }

    public static class UserViewHolder extends RecyclerView.ViewHolder {
        View myView;
        Button btn;

        public UserViewHolder(View itemView) {
            super(itemView);

            myView = itemView;
            btn = itemView.findViewById(R.id.request);
        }

        public void setName(final String name) {
            TextView textView = myView.findViewById(R.id.notice_name);
            textView.setText(name);

        }


    }
}

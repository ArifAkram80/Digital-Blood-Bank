package com.example.arif.digitalbloodbank.home;


import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.arif.digitalbloodbank.R;
import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

/**
 * A simple {@link Fragment} subclass.
 */
public class Blood_Feed_Frag extends Fragment {

    public static final String TAG = "Blood_Feed_Frag";
    private Button BtnFrag;
    private RecyclerView myBlogList;

    FirebaseRecyclerAdapter firebaseRecyclerAdapter;
    DatabaseReference myRef;
    LinearLayoutManager mLayoutManager;
    private FirebaseAuth mAuth;
    private FirebaseUser currentUser;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_blood__feed, container, false);

        myBlogList = (RecyclerView) view.findViewById(R.id.my_blog_list);
        //myBlogList.hasFixedSize(true);
      //  myRef = FirebaseDatabase.getInstance().getReference().child("blog");
        mAuth = FirebaseAuth.getInstance();
        return view;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        mLayoutManager = new LinearLayoutManager(getContext());
        mLayoutManager.setReverseLayout(true);
        mLayoutManager.setStackFromEnd(true);
        //
        myBlogList.setLayoutManager(mLayoutManager);

        myRef = FirebaseDatabase.getInstance().getReference().child("user");

        Query query = myRef.orderByKey();

        FirebaseRecyclerOptions<Post> options =
                new FirebaseRecyclerOptions.Builder<Post>()
                        .setQuery(query, Post.class)
                        .build();

        firebaseRecyclerAdapter = new FirebaseRecyclerAdapter<Post, BlogViewHolder>(
                options) {

            @Override
            protected void onBindViewHolder(BlogViewHolder holder, int position, final Post model) {

                String userID = model.getUserID();

                holder.setName(model.getName());
                holder.setDetails(model.getDetails());
                holder.setPhone(model.getPhone());
                holder.setTime(model.getTime());
                holder.setDate(model.getDate());
                holder.setGroup(model.getGroup());
                holder.setLoc(model.getLoc());

               if(userID.equals(currentUser.getUid()))
                {
                    holder.btn.setVisibility(View.VISIBLE);
                }
                else
                    holder.btn.setVisibility(View.GONE);


                final String key = this.getRef(position).getKey();


                holder.btn.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                            final DatabaseReference mDatabase = FirebaseDatabase.getInstance().getReference().child("user").child(key);
                            mDatabase.removeValue();
                    }
                });
            }

            @Override
            public BlogViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
                View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.blog_row, parent, false);
                return new BlogViewHolder(view);
            }

        };
        myBlogList.setAdapter(firebaseRecyclerAdapter);
    }


    @Override
    public void onStart() {
        super.onStart();
        firebaseRecyclerAdapter.startListening();
        currentUser = mAuth.getCurrentUser();
    }

    @Override
    public void onStop() {
        super.onStop();
        firebaseRecyclerAdapter.stopListening();
    }

    public static class BlogViewHolder extends RecyclerView.ViewHolder {
        View myView;
        Button btn;
        public BlogViewHolder(View itemView) {
            super(itemView);

            myView = itemView;
            btn= itemView.findViewById(R.id.delete);
        }

        public void setName(String name) {
            TextView post_user = myView.findViewById(R.id.nameEdit);
            post_user.setText(name);
        }

        public void setDetails(String name) {
            TextView post_user = myView.findViewById(R.id.hospitalEdit);
            post_user.setText(name);
        }

        public void setPhone(String name) {
            TextView post_user = myView.findViewById(R.id.mobileEdit);
            post_user.setText(name);
        }

        public void setTime(String name) {
            TextView post_user = myView.findViewById(R.id.timeEdit);
            post_user.setText(name);
        }

        public void setDate(String name) {
            TextView post_user = myView.findViewById(R.id.dateEdit);
            post_user.setText(name);
        }

        public void setGroup(String name) {
            TextView post_user = myView.findViewById(R.id.groupEdit);
            post_user.setText(name);
        }

        public void setLoc(String name) {
            TextView post_user = myView.findViewById(R.id.placeEdit);
            post_user.setText(name);
        }


    }

}
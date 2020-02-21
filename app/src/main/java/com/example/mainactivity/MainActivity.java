package com.example.mainactivity;


import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.firebase.ui.database.FirebaseRecyclerAdapter;

import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.squareup.picasso.Picasso;



public class MainActivity extends AppCompatActivity {
    private RecyclerView mBloglist;
    FirebaseRecyclerAdapter<Blog,BlogViewHolder> recyclerAdapter;
    RecyclerView.LayoutManager layoutManager;
    private DatabaseReference mDatabase;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mDatabase= FirebaseDatabase.getInstance().getReference().child("Blog");
        mDatabase.keepSynced(true);
        mBloglist=(RecyclerView)findViewById(R.id.myrecycleview);
        mBloglist.setHasFixedSize(true);
        mBloglist.setLayoutManager(new LinearLayoutManager(this));
        mBloglist.setLayoutManager(layoutManager);

        onStart();
    }

    @Override
    protected void onStart()
    {
        super.onStart();
        FirebaseRecyclerOptions options =
                new FirebaseRecyclerOptions.Builder<Blog>()
                        .setQuery(mDatabase,Blog.class)
                        .build();
        recyclerAdapter=new  FirebaseRecyclerAdapter<Blog,BlogViewHolder>
                (options){
            protected void onBindViewHolder(@NonNull BlogViewHolder holder, int position,@NonNull Blog  model) {
                holder.setTitle(model.getTitle());
                holder.setDesc(model.getDesc());
                holder.setImage(getApplicationContext(),model.getImage());
            }
            @NonNull
            @Override
            public BlogViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
                View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.blog_row,parent,false);
                return new BlogViewHolder(view);
            }

        };
        recyclerAdapter.notifyDataSetChanged();
        recyclerAdapter.notify();
        mBloglist.setAdapter(recyclerAdapter);
    }
    public static class BlogViewHolder extends RecyclerView.ViewHolder
    {
        View mview;
        public BlogViewHolder(View itemView)
        {
            super(itemView);
            mview=itemView;
        }
        public void setTitle(String title)
        {
            TextView post_title=(TextView)mview.findViewById(R.id.post_title);
            post_title.setText(title);
        }
        public void setDesc(String desc)
        {
            TextView post_desc=(TextView)mview.findViewById(R.id.post_desc);
            post_desc.setText(desc);
        }
        public void setImage(Context ctx,String image)
        {
            ImageView post_image=(ImageView)mview.findViewById(R.id.post_image);
            Picasso.with(ctx).load(image).into(post_image);
        }
    }

}

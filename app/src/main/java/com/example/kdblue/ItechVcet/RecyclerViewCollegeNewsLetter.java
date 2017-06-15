package com.example.kdblue.ItechVcet;

import android.content.Context;
import android.graphics.Typeface;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.support.v4.app.FragmentManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;


/**
 * Created by kulde on 3/29/2017.
 */

public class RecyclerViewCollegeNewsLetter extends RecyclerView.Adapter<RecyclerViewCollegeNewsLetter.MyCollege> {

    static List<Topics> topicses = new ArrayList<>();
    Context context;
    Topics topics;


    public RecyclerViewCollegeNewsLetter(Context context, ArrayList<Topics> topicses) {
        this.context = context;
        this.topicses = topicses;
    }

    @Override
    public RecyclerViewCollegeNewsLetter.MyCollege onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.recycler_row, parent, false);
        Log.d("ssss", "in oncreate");

        return new MyCollege(view);
    }

    @Override
    public void onBindViewHolder(final RecyclerViewCollegeNewsLetter.MyCollege holder, final int position) {
        topics = topicses.get(position);

        String img_url=topics.getImageUrl();
        Picasso.with(context)
                .load(img_url)
                //.placeholder(R.drawable.wait)   // optional
                //.error(R.drawable.error)      // optional
                .resize(200,200)                        // optional
                .transform(new RoundedTransformation(20, 4))
                .into(holder.imageView);


        holder.id.setText(topics.getId());

        holder.givenby.setText(topics.getGivenby());

        holder.article.setText(topics.getArticle());

        holder.more.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                FilterDialogFragment filterDialogFragment = new FilterDialogFragment();
                int pos=holder.getAdapterPosition();
                Bundle bundle = new Bundle();
                bundle.putString("more_id",topicses.get(pos).getId() );
                Log.e("rrrr",topicses.get(pos).getId());
                bundle.putString("more_givenby",topicses.get(pos).getGivenby());
                bundle.putString("more_article", topicses.get(pos).getArticle());
                bundle.putString("more_content", topicses.get(pos).getContent());
                bundle.putString("more_image_url", topicses.get(pos).getImageUrl());
                //pasing data to filterDialogFragment



                showDialog(bundle);
            }
        });
        // holder.content.setText(topics.getContent());

        if (topics.getContent().length() > 100) {
            holder.content.setText((topics.getContent().substring(0, 99)) + "...");
        } else
            holder.content.setText(topics.getContent());

    }

    @Override
    public int getItemCount() {
        return topicses.size();
    }

    public class MyCollege extends RecyclerView.ViewHolder {
        TextView id, content, article, givenby;
        Button more;
        ImageView imageView;

        public MyCollege(View itemView) {
            super(itemView);
            id = (TextView) itemView.findViewById(R.id.id);
            givenby = (TextView) itemView.findViewById(R.id.givenby);
            article = (TextView) itemView.findViewById(R.id.article);
            content = (TextView) itemView.findViewById(R.id.content);
            more = (Button) itemView.findViewById(R.id.more);
            imageView=(ImageView)itemView.findViewById(R.id.imageView);

            Typeface typeface = Typeface.createFromAsset(context.getAssets(), "Gotham.ttf");
            id.setTypeface(typeface);
            givenby.setTypeface(typeface);
            article.setTypeface(typeface);
            content.setTypeface(typeface);
            more.setTypeface(typeface);
        }

    }
    void showDialog(Bundle bundle) {
        FilterDialogFragment filterDialogFragment = new FilterDialogFragment();
       // MoreDetails moreDetails = new MoreDetails();
        FilterDialogFragment ff=null;
        FragmentManager ft = ((MainActivity) context).getSupportFragmentManager();
        DialogFragment newFragment = filterDialogFragment.newInstance(bundle);
        newFragment.show(ft, "dialog");
    }


}


package com.example.kdblue.ItechVcet;

import android.app.Dialog;
import android.graphics.Typeface;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.DialogFragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdView;
import com.squareup.picasso.Picasso;

public class FilterDialogFragment extends DialogFragment {

    private AdView mAdView;
    Button close_dialog;
    TextView more_id, more_content, more_article, more_givenby;
    ImageView imageView;

    static FilterDialogFragment newInstance(Bundle bundle) {
        FilterDialogFragment f = new FilterDialogFragment();
        f.setArguments(bundle);
        return f;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.more_details, container, false);
        mAdView = (AdView) v.findViewById(R.id.adView);
        more_id = (TextView) v.findViewById(R.id.more_id);
        more_content = (TextView) v.findViewById(R.id.more_content);
        more_article = (TextView) v.findViewById(R.id.more_article);
        more_givenby = (TextView) v.findViewById(R.id.more_givenby);
        close_dialog=(Button)v.findViewById(R.id.close_dialog);

        imageView=(ImageView)v.findViewById(R.id.image_view);

        return v;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        Typeface typeface = Typeface.createFromAsset(getActivity().getAssets(), "Gotham.ttf");
        more_id.setTypeface(typeface);
        more_content.setTypeface(typeface);
        more_article.setTypeface(typeface);
        more_givenby.setTypeface(typeface);
        Bundle mArgs = getArguments();
        String img_url=mArgs.getString("more_image_url");

        Picasso.with(getActivity())
                .load(img_url)
                //.placeholder(R.drawable.article_img)   // optional
                //.error(R.drawable.error)      // optional
                .resize(400,400)                        // optional
                .transform(new RoundedTransformation(20, 4))
                .into(imageView);

        AdRequest adRequest = new AdRequest.Builder()
                .build();
        mAdView.loadAd(adRequest);


        more_id.setText(mArgs.getString("more_id"));
        more_article.setText(mArgs.getString("more_article"));
        more_givenby.setText(mArgs.getString("more_givenby"));
        more_content.setText(mArgs.getString("more_content"));

        close_dialog.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //FilterDialogFragment filterDialogFragment = new FilterDialogFragment();
                getDialog().dismiss();
            }
        });
    }

    @Override
    public void onPause() {
        if (mAdView != null) {
            mAdView.pause();
        }
        super.onPause();
    }

    @Override
    public void onResume() {
        super.onResume();
        if (mAdView != null) {
            mAdView.resume();
        }
    }

    @Override
    public void onDestroy() {
        if (mAdView != null) {
            mAdView.destroy();
        }
        super.onDestroy();
    }

    @Override
    public void onStart() {
        super.onStart();
        Dialog dialog = getDialog();
        if (dialog != null) {
            int width = ViewGroup.LayoutParams.MATCH_PARENT;
            int height = ViewGroup.LayoutParams.MATCH_PARENT;
            dialog.getWindow().setLayout(width, height);
        }
    }



}
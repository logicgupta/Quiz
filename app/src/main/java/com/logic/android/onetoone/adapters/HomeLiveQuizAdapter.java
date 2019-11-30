package com.logic.android.onetoone.adapters;

import android.content.Context;
import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.os.Build;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.target.CustomTarget;
import com.bumptech.glide.request.transition.Transition;
import com.logic.android.onetoone.basicvideochat.R;
import com.logic.android.onetoone.models.Quiz1Details;
import com.logic.android.onetoone.views.Enroll.EnrollQuizActivity;

import java.util.ArrayList;
import java.util.List;

public class HomeLiveQuizAdapter extends RecyclerView.Adapter<HomeLiveQuizAdapter.ViewHolder> {

    Context context;
    RecyclerView recyclerView;
    List<Quiz1Details> quizDetailsList = new ArrayList<>();

    public HomeLiveQuizAdapter(Context context, RecyclerView recyclerView, List<Quiz1Details> quizDetailsList) {
        this.context = context;
        this.recyclerView = recyclerView;
        this.quizDetailsList = quizDetailsList;
    }

    @NonNull
    @Override
    public HomeLiveQuizAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(context).inflate(R.layout.demo_live_quiz, viewGroup, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull final HomeLiveQuizAdapter.ViewHolder viewHolder, int i) {
        Quiz1Details details = quizDetailsList.get(i);
        Glide.with(context).load(details.getImageUrl()).into(new CustomTarget<Drawable>() {
            @Override
            public void onResourceReady(@NonNull Drawable resource, @Nullable Transition<? super Drawable> transition) {
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN) {
                    viewHolder.linearLayout.setBackground(resource);
                }
            }

            @Override
            public void onLoadCleared(@Nullable Drawable placeholder) {
                viewHolder.linearLayout.setBackground(placeholder);
            }
        });
        viewHolder.textView.setText(details.getName());
    }

    @Override
    public int getItemCount() {
        return quizDetailsList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        LinearLayout linearLayout;
        TextView textView;

        public ViewHolder(@NonNull final View itemView) {
            super(itemView);
            linearLayout = itemView.findViewById(R.id.linearLayout);
            textView = itemView.findViewById(R.id.textView);

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    int pos = recyclerView.getChildLayoutPosition(itemView);
                    Quiz1Details quizDetails = quizDetailsList.get(pos);
                    Intent intent = new Intent(context, EnrollQuizActivity.class);
                    String s = String.valueOf(quizDetails.getQuestionsList().size());
                    //intent.putExtra("questions",quizDetails.getQuestionsList());
                    intent.putExtra("imageUrl", quizDetails.getImageUrl());
                    intent.putExtra("name", quizDetails.getName());
                    intent.putExtra("date", quizDetails.getDate());
                    intent.putExtra("time", quizDetails.getStartTime());
                    intent.putExtra("desc", quizDetails.getDesc());
                    intent.putExtra("coins", quizDetails.getCoins());
                    intent.putExtra("enrollFee", quizDetails.getEnrollFee());
                    intent.putExtra("docKey", quizDetails.getDocId());
                    intent.putExtra("register", quizDetails.getNoOfRegistered());
                    intent.putExtra("categoryName", quizDetails.getCategoryName());
                    intent.putExtra("duration", quizDetails.getDuration());
                    // Log.e("nvjv","mklvml"+quizDetails.getQuestionsList());

                    intent.putExtra("questions", s);
                    context.startActivity(intent);

                }
            });
        }
    }
}
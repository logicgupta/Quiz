package com.logic.android.onetoone.adapters;

import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.logic.android.onetoone.basicvideochat.R;
import com.logic.android.onetoone.models.Quiz1Details;
import com.logic.android.onetoone.views.LeaderBoard.ResultActivity;

import java.util.ArrayList;
import java.util.List;

import de.hdodenhof.circleimageview.CircleImageView;

public class RecentRecyclerViewAdapter extends RecyclerView.Adapter<RecentRecyclerViewAdapter.ViewHolder> {

    Context context;
    RecyclerView recyclerView;
    List<Quiz1Details> quiz1Details=new ArrayList<>();


    public RecentRecyclerViewAdapter(Context context, RecyclerView recyclerView, List<Quiz1Details> quiz1Details) {
        this.context = context;
        this.recyclerView = recyclerView;
        this.quiz1Details = quiz1Details;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view= LayoutInflater.from(context)
                .inflate(R.layout.custom_myrecentquiz_layout2,viewGroup,false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder viewHolder, int i) {
        Quiz1Details details=quiz1Details.get(i);
        Log.e("Log","bvj"+details.getImageUrl());
        Glide.with(context).load(details.getImageUrl()).into(viewHolder.circleImageView);
        viewHolder.textViewName.setText(details.getName());
        viewHolder.textViewTime.setText("Duration : "+details.getDuration());
        viewHolder.textViewQuestion.setText("No. of Questions : "+details.getQuestionsList().size());
        viewHolder.textViewCoins.setText(""+details.getEnrollFee());           /// -*-*-- Presently set the enroll Fee
        //   viewHolder.textViewPrize.setText(""+details.getPrizeList().getFirst());
        viewHolder.textViewDesc.setText(details.getDesc());
    }

    @Override
    public int getItemCount() {
        return quiz1Details.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder{
        CircleImageView circleImageView;
        TextView textViewName,textViewTime,textViewQuestion,textViewCoins,textViewDesc,textViewPrize;

        public ViewHolder(@NonNull final View itemView) {
            super(itemView);
            circleImageView=itemView.findViewById(R.id.imageView);
            textViewName=itemView.findViewById(R.id.textViewName);
            textViewTime=itemView.findViewById(R.id.time);
            textViewQuestion=itemView.findViewById(R.id.questions);
            textViewCoins=itemView.findViewById(R.id.coinsTextView);
            textViewDesc=itemView.findViewById(R.id.descTextView);
            textViewPrize=itemView.findViewById(R.id.prizeTextView);

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    int pos=recyclerView.getChildLayoutPosition(itemView);
                    Quiz1Details details=quiz1Details.get(pos);
                    if (details.getResult()){
                        Toast.makeText(context, "Result is ready", Toast.LENGTH_SHORT).show();
                        Intent intent=new Intent(context, ResultActivity.class);
                        intent.putExtra("date",details.getDate());
                        intent.putExtra("name",details.getName());
                        intent.putExtra("correctAnswer",details.getCorrectAnswer());
                        intent.putExtra("winAmount",details.getWinAmount());
                        context.startActivity(intent);
                    }
                    else {
                        Toast.makeText(context, "Result is not ready", Toast.LENGTH_SHORT).show();
                    }


                }
            });
        }
    }
}

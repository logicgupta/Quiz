package com.logic.android.onetoone.adapters;

import android.content.Context;
import android.content.Intent;
import android.os.Parcelable;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.logic.android.onetoone.basicvideochat.R;
import com.logic.android.onetoone.models.Quiz1Details;
import com.logic.android.onetoone.views.LiveQuiz.GetQuizReadyActivity;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import de.hdodenhof.circleimageview.CircleImageView;

public class ActiveRecyclerAdapter extends RecyclerView.Adapter<ActiveRecyclerAdapter.ViewHolder> {

    private static final String TAG =ActiveRecyclerAdapter.class.getSimpleName() ;
    Context context;
    RecyclerView recyclerView;
    List<Quiz1Details> quiz1Details=new ArrayList<>();

    public ActiveRecyclerAdapter(Context context, RecyclerView recyclerView, List<Quiz1Details> quiz1Details) {
        this.context = context;
        this.recyclerView = recyclerView;
        this.quiz1Details = quiz1Details;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
       View view= LayoutInflater.from(context)
               .inflate(R.layout.custom_myquiz_layout1,viewGroup,false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder viewHolder, final int i) {

        Quiz1Details details=quiz1Details.get(i);

        DateFormat dfDate = new SimpleDateFormat("yyyy.MM.dd");
        DateFormat dfTime = new SimpleDateFormat("h:mm a");
        String  todayDate= dfDate.format(Calendar.getInstance().getTime());
        String todayFullTime= dfTime.format(Calendar.getInstance().getTime());

        String dateSplit[]=todayDate.split("\\.");
        String timeSplit[]=todayFullTime.split(":");
        String timeIntervalSplit[]=todayFullTime.split(" ");
        String todayDayDate=dateSplit[2];                  // date
        final String todayHourTime=timeSplit[0];                    // Hour Time
        String todayIntervalTime=timeIntervalSplit[1];         // AM OR PM

        String times[]=details.getStartTime().split(" ");
        final String x[]=times[0].split(":");
        String d[]=details.getDate().split("-");

            Log.e(TAG, "onBindViewHolder: "+todayHourTime +"  "+x[0] );



        Log.e("Log","bvj"+details.getImageUrl());
        Glide.with(context).load(details.getImageUrl()).into(viewHolder.circleImageView);
        viewHolder.textViewName.setText(details.getName());
        viewHolder.textViewTime.setText("Time : "+details.getStartTime()+" onwards");
        viewHolder.textViewQuestion.setText("No. of Questions : "+details.getQuestionsList().size());
        viewHolder.textViewCoins.setText(""+details.getEnrollFee());     //  *-*-       Presently set the enroll Fee
     //   viewHolder.textViewPrize.setText(""+details.getPrizeList().getFirst());
        viewHolder.textViewDesc.setText(details.getDesc());
        viewHolder.play.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(todayHourTime.equalsIgnoreCase(x[0])){

                    Quiz1Details details=quiz1Details.get(i);
                    Intent intent=new Intent(context, GetQuizReadyActivity.class);
                    intent.putExtra("name",details.getName());
                    intent.putExtra("id",details.getId());
                    intent.putExtra("docId",details.getDocId());
                    intent.putExtra("coins",details.getCoins());
                    intent.putExtra("desc",details.getDesc());
                    intent.putExtra("date",details.getDate());
                    intent.putExtra("duration",details.getDuration());
                    intent.putExtra("startTime",details.getStartTime());
                    intent.putExtra("imageUrl",details.getImageUrl());
                    intent.putExtra("maxUsers",details.getMaxUsers());
                    intent.putExtra("noOfRegistered",details.getNoOfRegistered());
                    intent.putExtra("enrollFee",details.getEnrollFee());
                    intent.putExtra("percentWinners",details.getPercentWinners());
                    intent.putParcelableArrayListExtra("facts", (ArrayList<? extends Parcelable>) details.getRandomFacts());
                    intent.putParcelableArrayListExtra("questions", (ArrayList<? extends Parcelable>) details.getQuestionsList());
                    context.startActivity(intent);
                }
                else{
                    Toast.makeText(context, "Quiz Yet Not Started !", Toast.LENGTH_SHORT).show();
                    Quiz1Details details=quiz1Details.get(i);
                    Intent intent=new Intent(context, GetQuizReadyActivity.class);
                    intent.putExtra("name",details.getName());
                    intent.putExtra("id",details.getId());
                    intent.putExtra("docId",details.getDocId());
                    intent.putExtra("coins",details.getCoins());
                    intent.putExtra("desc",details.getDesc());
                    intent.putExtra("date",details.getDate());
                    intent.putExtra("duration",details.getDuration());
                    intent.putExtra("startTime",details.getStartTime());
                    intent.putExtra("imageUrl",details.getImageUrl());
                    intent.putExtra("maxUsers",details.getMaxUsers());
                    intent.putExtra("noOfRegistered",details.getNoOfRegistered());
                    intent.putExtra("enrollFee",details.getEnrollFee());
                    intent.putExtra("percentWinners",details.getPercentWinners());
                    intent.putParcelableArrayListExtra("facts", (ArrayList<? extends Parcelable>) details.getRandomFacts());
                    intent.putParcelableArrayListExtra("questions", (ArrayList<? extends Parcelable>) details.getQuestionsList());
                    context.startActivity(intent);
                }
            }
        });
    }

    @Override
    public int getItemCount() {
        return quiz1Details.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder{

        CircleImageView circleImageView;
        TextView textViewName,textViewTime,textViewQuestion,textViewCoins,textViewDesc,textViewPrize;
        TextView play;

        public ViewHolder(@NonNull final View itemView) {
            super(itemView);
            circleImageView=itemView.findViewById(R.id.imageView);
            textViewName=itemView.findViewById(R.id.textViewName);
            textViewTime=itemView.findViewById(R.id.time);
            textViewQuestion=itemView.findViewById(R.id.questions);
            textViewCoins=itemView.findViewById(R.id.coinsTextView);
            textViewDesc=itemView.findViewById(R.id.descTextView);
            textViewPrize=itemView.findViewById(R.id.prizeTextView);
            play=itemView.findViewById(R.id.playButton);
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    int pos=recyclerView.getChildLayoutPosition(itemView);

                }
            });
        }
    }
}

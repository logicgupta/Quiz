package com.logic.android.onetoone.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;


import com.logic.android.onetoone.basicvideochat.R;
import com.logic.android.onetoone.models.QuizDetails;

import java.util.ArrayList;
import java.util.List;

public class LiveQuizAdapter extends RecyclerView.Adapter<LiveQuizAdapter.ViewHolder> {

    Context context;
    RecyclerView recyclerView;
    List<QuizDetails> quizDetailsList=new ArrayList<>();

    public LiveQuizAdapter(Context context, RecyclerView recyclerView, List<QuizDetails> quizDetailsList) {
        this.context = context;
        this.recyclerView = recyclerView;
        this.quizDetailsList = quizDetailsList;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view= LayoutInflater.from(context).inflate(R.layout.custom_live_quiz_layout,viewGroup,false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder viewHolder, int i) {

        QuizDetails details=quizDetailsList.get(i);

        viewHolder.textView.setText(details.getCategoryName());
    }

    @Override
    public int getItemCount() {
        return quizDetailsList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder{

        LinearLayout linearLayout;
        TextView textView;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            linearLayout=itemView.findViewById(R.id.linearLayout);
            textView=itemView.findViewById(R.id.textView);
        }
    }
}

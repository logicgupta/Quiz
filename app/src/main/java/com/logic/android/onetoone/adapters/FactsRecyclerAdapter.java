package com.logic.android.onetoone.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.logic.android.onetoone.basicvideochat.R;
import com.logic.android.onetoone.models.RandomFacts;

import java.util.List;


public class FactsRecyclerAdapter extends RecyclerView.Adapter<FactsRecyclerAdapter.ViewHolder> {

    private static final String TAG = "FactsRecyclerAdapter";

    List<RandomFacts> randomFactsList;
    Context context;
    RecyclerView recyclerView;

    public FactsRecyclerAdapter(List<RandomFacts> randomFactsList, Context context, RecyclerView recyclerView) {
        this.randomFactsList = randomFactsList;
        this.context = context;
        this.recyclerView = recyclerView;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view= LayoutInflater.from(context).inflate(R.layout.random_facts_layout,viewGroup,false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder viewHolder, int i) {

     RandomFacts randomFacts=randomFactsList.get(i);
     viewHolder.textView.setText(""+(i+1)+". "+randomFacts.getFact());

    }

    @Override
    public int getItemCount() {

        return randomFactsList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder{

        TextView textView;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            textView=itemView.findViewById(R.id.textView);
        }
    }
}

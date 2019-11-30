package com.logic.android.onetoone.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.logic.android.onetoone.basicvideochat.R;
import com.logic.android.onetoone.models.UserResponse;

import java.util.ArrayList;
import java.util.List;

import de.hdodenhof.circleimageview.CircleImageView;

public class LeaderBoardAdapter extends RecyclerView.Adapter<LeaderBoardAdapter.ViewHolder> {

    Context context;
    RecyclerView recyclerView;
    List<UserResponse> userResponseList=new ArrayList<>();

    public LeaderBoardAdapter(Context context, RecyclerView recyclerView,
                              List<UserResponse> userResponseList) {
        this.context = context;
        this.recyclerView = recyclerView;
        this.userResponseList = userResponseList;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {

        View view= LayoutInflater.from(context).inflate(R.layout.custom_result_layout
        ,viewGroup,false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder viewHolder, int i) {

        UserResponse userResponse=userResponseList.get(i);

        Glide.with(context).load(userResponse.getImageUrl())
                .into(viewHolder.circleImageView);
        viewHolder.textViewName.setText(""+userResponse.getUsername());
        viewHolder.textViewAmount.setText("₹"+userResponse.getAmountWin());
        viewHolder.textViewRank.setText(""+(i+1));
        if (i==0){

            viewHolder.imageView.setImageResource(R.drawable.ic_gold);
        }
        else if (i==1){
            viewHolder.imageView.setImageResource(R.drawable.ic_silver);
        }
        else if (i==2){
            viewHolder.imageView.setImageResource(R.drawable.ic_platinum);
        }
        else {
            viewHolder.imageView.setVisibility(View.INVISIBLE);
        }

    }

    @Override
    public int getItemCount() {
        return userResponseList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder{

        CircleImageView circleImageView;
        ImageView imageView;
        TextView textViewRank,textViewName,textViewAmount;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            circleImageView=itemView.findViewById(R.id.imageView);
            imageView=itemView.findViewById(R.id.imageViewWin);
            textViewRank=itemView.findViewById(R.id.rank);
            textViewName=itemView.findViewById(R.id.name);
            textViewAmount=itemView.findViewById(R.id.amount);

        }
    }
}

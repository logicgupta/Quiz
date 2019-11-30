package com.logic.android.onetoone.views.LeaderBoard;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;
import com.logic.android.onetoone.Util.Constant;
import com.logic.android.onetoone.adapters.LeaderBoardAdapter;
import com.logic.android.onetoone.basicvideochat.R;
import com.logic.android.onetoone.models.UserResponse;

import java.util.ArrayList;
import java.util.List;

import de.hdodenhof.circleimageview.CircleImageView;

public class ResultActivity extends AppCompatActivity {

    CircleImageView imageView;
    TextView textViewCorrectAnswer,textViewMyWinning,textViewQuizName;
    RecyclerView recyclerView;
    CollectionReference collectionReference;
    ProgressBar progressBar;
    LinearLayout linearLayout;
    String date,name;
    int correctAnswer,winAmount;
    Bundle bundle;
    String userName,userimageUrl;
    LeaderBoardAdapter adapter;
    List<UserResponse> userResponseList=new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_result);
        bundle=getIntent().getExtras();
        date=bundle.getString("date");
        name=bundle.getString("name");
        correctAnswer=bundle.getInt("correctAnswer");
        winAmount=bundle.getInt("winAmount");
        init();
        fetchLeaderBoard();
        SharedPreferences preferences=getApplicationContext().getSharedPreferences(Constant.userKey,0);
        SharedPreferences.Editor editor=preferences.edit();
        if (preferences.getString(Constant.name,null)!=null){
            userName=preferences.getString(Constant.name,null);
            userimageUrl=preferences.getString(Constant.imageUrl,null);
        }
        textViewCorrectAnswer.setText(""+correctAnswer);
        textViewMyWinning.setText("₹"+winAmount);
        Glide.with(this).load(userimageUrl).into(imageView);
        textViewQuizName.setText(""+name);

    }

    public void init(){
        progressBar=findViewById(R.id.progressBar);
        imageView=findViewById(R.id.imageView);
        textViewCorrectAnswer=findViewById(R.id.correctAnswer);
        textViewMyWinning=findViewById(R.id.winningAmount);
        recyclerView=findViewById(R.id.recyclerView);
        textViewQuizName=findViewById(R.id.name);
        recyclerView.setLayoutManager(new LinearLayoutManager(ResultActivity.this));
        linearLayout=findViewById(R.id.linearLayout);
    }

    public void fetchLeaderBoard(){

        collectionReference= FirebaseFirestore.getInstance()
                .collection("Admin")
                .document("LiveQuiz")
                .collection("LeaderBoard")
                .document(date)
                .collection(name);

        collectionReference.get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
            @Override
            public void onComplete(@NonNull Task<QuerySnapshot> task) {

                if (task.isSuccessful()){
                    for (QueryDocumentSnapshot snapshot:task.getResult()){
                        UserResponse userResponse=new UserResponse();
                        String imageUrl,username;
                        int correctAnswer,amountWin;
                        imageUrl=snapshot.getString("imageUrl");
                        username=snapshot.getString("username");
                        correctAnswer=Integer.parseInt(snapshot.get("totalCorrectAnswers").toString());
                        amountWin=Integer.parseInt(snapshot.getLong("amountWin").toString());
                        userResponse.setImageUrl(imageUrl);
                        userResponse.setUsername(username);
                        userResponse.setTotalCorrectAnswers(correctAnswer);
                        userResponse.setAmountWin(amountWin);
                        userResponseList.add(userResponse);
                    }
                    adapter=new LeaderBoardAdapter(ResultActivity.this,recyclerView,userResponseList);
                    if (userResponseList.isEmpty()){
                        Toast.makeText(ResultActivity.this, "Error !", Toast.LENGTH_SHORT).show();
                        progressBar.setVisibility(View.GONE);
                        linearLayout.setVisibility(View.GONE);
                    }
                    else {
                        recyclerView.setAdapter(adapter);
                        progressBar.setVisibility(View.GONE);
                        linearLayout.setVisibility(View.VISIBLE);
                    }
                }

            }
        });
    }
}

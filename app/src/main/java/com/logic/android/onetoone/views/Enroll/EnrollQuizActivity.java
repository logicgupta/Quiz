package com.logic.android.onetoone.views.Enroll;

import android.app.ProgressDialog;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.ScrollView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.bumptech.glide.Glide;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;
import com.logic.android.onetoone.basicvideochat.R;
import com.logic.android.onetoone.models.EnrollDetails;
import com.logic.android.onetoone.models.Quiz1Details;
import com.logic.android.onetoone.models.QuizIds;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

public class EnrollQuizActivity extends AppCompatActivity {

    private static final String TAG = "EnrollQuizActivity";

    ImageView imageView;
    TextView enroll;
    TextView textViewCoins,textViewQuestions,textViewTime,textViewDuration,textViewName,descTextView;
    Bundle bundle=new Bundle();
    String coins,questions,time,duration,imageUrl,docKey,name,desc,category;
    int register;
     FirebaseFirestore firestore;
    CollectionReference collectionReference,collectionReference2,collectionReference4;
    List<Quiz1Details> quiz1DetailsList=new ArrayList<>();
    ProgressDialog progressDialog;
    EnrollDetails enrollDetails;
    String today_date;
    List<QuizIds> quizIdsList=new ArrayList<>();
    int k=0;
    ProgressBar progressBar;
    ScrollView scrollView;
    String date;
    TextView textView;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_enroll_quiz);
        firestore= FirebaseFirestore.getInstance();
        bundle=getIntent().getExtras();
        Log.e(TAG, "onCreate: "+bundle.get("enrollFee") );
        coins=bundle.getString("coins");
        questions=bundle.getString("questions");
        duration=bundle.getString("duration");
        time=bundle.getString("time");
        date=bundle.getString("date");
        name=bundle.getString("name");
        desc=bundle.getString("desc");
        imageUrl=bundle.getString("imageUrl");
        docKey=bundle.getString("docKey");
        category=bundle.getString("categoryName");
        register=bundle.getInt("register");

//        getSupportActionBar().hide();
//        getSupportActionBar().setElevation(0);

        imageView=findViewById(R.id.imageView);
        textViewName=findViewById(R.id.categoryTextView);
        textViewCoins=findViewById(R.id.coinsTextView);
        textViewDuration=findViewById(R.id.duration);
        textViewQuestions=findViewById(R.id.questions);
        textViewTime=findViewById(R.id.time);
        descTextView=findViewById(R.id.descTextView);
        progressBar=findViewById(R.id.progressBar);
        scrollView=findViewById(R.id.scrollView);
        scrollView.setVisibility(View.INVISIBLE);

        enroll=findViewById(R.id.enroll);

        Log.e(TAG, "onCreate: "+date +name);

        textView=findViewById(R.id.backTextView);

        textView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });      // Back Button ---------------------------



        enroll.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                progressDialog=new ProgressDialog(EnrollQuizActivity.this);
                progressDialog.setMessage("Enrolling .....");
                progressDialog.setCancelable(false);
                progressDialog.show();
                updateRegistered();


            }
        });
        textViewName.setText(name);
        Glide.with(EnrollQuizActivity.this).load(imageUrl).into(imageView);
        textViewCoins.setText(""+bundle.get("enrollFee"));
        textViewQuestions.setText("No. Of Questions : "+questions);
        textViewTime.setText("Time : "+time);
        textViewDuration.setText("Duration : "+duration);
        descTextView.setText(desc);

        Calendar calendar=Calendar.getInstance();
        Date date=calendar.getTime();
        SimpleDateFormat df = new SimpleDateFormat("dd-MMM-yyyy");
        today_date = df.format(date);
        getEnrolledDetails();
        enrollDetails=new EnrollDetails(FirebaseAuth.getInstance().getUid(),name,category,coins,docKey,today_date);
    }

    public void getEnrolledDetails(){
        collectionReference=firestore.collection("UserDetails")
                .document(FirebaseAuth.getInstance().getUid())
                .collection("ActiveQuiz");

        collectionReference.get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
            @Override
            public void onComplete(@NonNull Task<QuerySnapshot> task) {

                Log.e(TAG, "onComplete: " );

                if (task.isSuccessful()){
                    for (QueryDocumentSnapshot snapshot:task.getResult()){
                        QuizIds quizIds=new QuizIds();
                         k++;
                        quizIds.setId(snapshot.getString("id"));
                        quizIds.setName(snapshot.getString("name"));
                        quizIdsList.add(quizIds);
                        Log.e("Enroll Activity", "onComplete: Enroll Details " +snapshot.getString("name"));
                        if (task.getResult().size()==k){
                            checkEnrolled();
                        }
                    }
                }
                else {
                    checkEnrolled();
                }
                checkEnrolled();
            }
        });
    }

    public void checkEnrolled(){
        Log.e(TAG, "checkEnrolled:" );
        if (quizIdsList.size()>0){
            for(int i=0 ;i<quizIdsList.size();i++){

                QuizIds quizIds=quizIdsList.get(i);
                if (name.equalsIgnoreCase(quizIds.getName())){
                    Log.e("Enroll Activity", "onComplete: Already Herr" );
                    progressBar.setVisibility(View.GONE);
                    scrollView.setVisibility(View.VISIBLE);
                    enroll.setText("Already Enrolled");
                    enroll.setEnabled(false);
                }
                else {
                    progressBar.setVisibility(View.GONE);
                    scrollView.setVisibility(View.VISIBLE);
                      }

            }
        }
        else {
            progressBar.setVisibility(View.GONE);
            scrollView.setVisibility(View.VISIBLE);
        }
    }


    public void updateRegistered(){

        collectionReference4=firestore.collection("Admin")
                .document("LiveQuiz")
                .collection("UserEnrolled")
                .document(date).collection(name);

        register=register+1;

        collectionReference=firestore.collection("Admin")
                .document("Quiz")
                .collection(category);
        collectionReference.document(name).update("noOfRegistered",register)
                .addOnCompleteListener(new OnCompleteListener<Void>() {
            @Override
            public void onComplete(@NonNull Task<Void> task) {
                if (task.isSuccessful()){
                    collectionReference.document(name).get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
                        @Override
                        public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                            if (task.isSuccessful()){
                                Quiz1Details quiz1Details=task.getResult().toObject(Quiz1Details.class);
                                collectionReference2=firestore.collection("UserDetails")
                                        .document(FirebaseAuth.getInstance().getUid()).collection("ActiveQuiz");
                                collectionReference2
                                        .add(quiz1Details)
                                        .addOnCompleteListener(new OnCompleteListener<DocumentReference>() {
                                    @Override
                                    public void onComplete(@NonNull Task<DocumentReference> task) {
                                        if (task.isSuccessful()){

                                            collectionReference4
                                                    .document(FirebaseAuth.getInstance().getUid())
                                                    .set(enrollDetails)
                                                    .addOnCompleteListener(new OnCompleteListener<Void>() {
                                                @Override
                                                public void onComplete(@NonNull Task<Void> task) {
                                                    Toast.makeText(EnrollQuizActivity.this, "Successfully Enrolled !", Toast.LENGTH_SHORT).show();
                                                    enroll.setText("Already Enrolled");
                                                    enroll.setEnabled(false);
                                                    progressDialog.dismiss();
                                                }
                                            });


                                        }
                                        else {
                                            Toast.makeText(EnrollQuizActivity.this, "Failed !", Toast.LENGTH_SHORT).show();
                                            progressDialog.dismiss();
                                        }
                                    }
                                });
                            }
                        }
                    });
                }

            }
        });


    }
}

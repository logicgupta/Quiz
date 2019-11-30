package com.logic.android.onetoone.views.LiveQuiz;

import android.app.ProgressDialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Build;
import android.os.CountDownTimer;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;
import androidx.viewpager.widget.ViewPager;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.logic.android.onetoone.Database.ActiveQuizEntity;
import com.logic.android.onetoone.Util.Constant;
import com.logic.android.onetoone.ViewModel.ActiveViewmodel;
import com.logic.android.onetoone.adapters.SlidingImageAdapter2;
import com.logic.android.onetoone.basicvideochat.R;
import com.logic.android.onetoone.models.Questions;
import com.logic.android.onetoone.models.UserResponse;

import java.util.ArrayList;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

public class QuizStartActivity extends AppCompatActivity {

    private static final String TAG = "QuizStartActivity";
    ActiveViewmodel activeViewmodel;
    ActiveQuizEntity entity;
    ViewPager viewPager;
    TextView question;
    ArrayList<String> imagelist=new ArrayList<>();
    private static int currentPage = 0;
    private static int NUM_PAGES = 0;
    TextView button1,button2,button3,button4,next;
    String imageUrl;
    String q;
    String answerSelect;
    Questions questions;
    TextView timmer;

    List<Questions> questionsList=new ArrayList<>();
    int size,i=0,k=0;
    int counter=10;
    CountDownTimer countDownTimer;
    CollectionReference collectionReference,collectionReferenceAdmin;
    FirebaseFirestore firestore;
    UserResponse userResponse;
    int totalCorrectAnswer=0;
    long timeTaken=0;
    long startTime;
    long estimatedTime;
    ProgressDialog progressDialog;
    String date;
    String userName,userimageUrl;
    String docId;
    Bundle bundle;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_quiz_start);
        init();
        bundle=getIntent().getExtras();
        docId=bundle.getString("docId");
        userResponse=new UserResponse();
        activeViewmodel= ViewModelProviders.of(QuizStartActivity.this)
                .get(ActiveViewmodel.class);


        SharedPreferences preferences=getApplicationContext().getSharedPreferences(Constant.userKey,0);
        SharedPreferences.Editor editor=preferences.edit();
        if (preferences.getString(Constant.name,null)!=null){
            userName=preferences.getString(Constant.name,null);
            userimageUrl=preferences.getString(Constant.imageUrl,null);
        }

        activeViewmodel.getAllQuizes().observe(this, new Observer<List<ActiveQuizEntity>>() {
            @Override
            public void onChanged(@Nullable List<ActiveQuizEntity> activeQuizEntities) {
                Log.e("dnvn","d"+activeQuizEntities.size());
                entity=activeQuizEntities.get(0);
                Log.e("Start","Quiz Details "+entity.getImageUrl());
                Log.e("Start","Quiz Details "+entity.getQuestionsList());
                Log.e("Start","Quiz Details "+entity.getNoOfRegistered());
                Log.e("Start","Quiz Details "+entity.getName());
                imagelist.add(entity.getImageUrl());
                  questionsList=entity.getQuestionsList();
                  size=questionsList.size()-1;
                  startTimer();
                  userResponse.setName(entity.getName());
                  userResponse.setImageUrl(entity.getImageUrl());
                  userResponse.setDesc(entity.getDesc());
                  userResponse.setDuration(entity.getDuration());
                  userResponse.setEnrollFee(entity.getEnrollFee());
                  userResponse.setDate(entity.getDate());
                  date=entity.getDate();
                  question.setText("Q1. "+questionsList.get(0).getQuestion());
                  button1.setText(questionsList.get(0).getOption1());
                 button2.setText(questionsList.get(0).getOption2());
                 button3.setText(questionsList.get(0).getOption3());
                 button4.setText(questionsList.get(0).getOption4());
               //  i=1;
                 imageSlide();
            }
        });


        button1.setOnClickListener(new View.OnClickListener() {
            @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
            @Override
            public void onClick(View v) {
                if (answerSelect==null){
                    answerSelect=button1.getText().toString();
                    button1.setBackground(getDrawable(R.drawable.edittext_layout_blue));

                }
                else {
                    answerSelect=button1.getText().toString();
                    button1.setBackground(getDrawable(R.drawable.edittext_layout_blue));
                    button2.setBackground(getDrawable(R.drawable.not_answerquestion));
                    button3.setBackground(getDrawable(R.drawable.not_answerquestion));
                    button4.setBackground(getDrawable(R.drawable.not_answerquestion));
                }
                Log.e(TAG, "onClick:1 "+answerSelect);
            }
        });
        button2.setOnClickListener(new View.OnClickListener() {
            @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
            @Override
            public void onClick(View v) {
                if (answerSelect==null){
                    answerSelect=button2.getText().toString();
                    button2.setBackground(getDrawable(R.drawable.edittext_layout_blue));

                }
                else {
                    answerSelect=button2.getText().toString();
                    button2.setBackground(getDrawable(R.drawable.edittext_layout_blue));
                    button1.setBackground(getDrawable(R.drawable.not_answerquestion));
                    button3.setBackground(getDrawable(R.drawable.not_answerquestion));
                    button4.setBackground(getDrawable(R.drawable.not_answerquestion));
                }
            }
        });

        button3.setOnClickListener(new View.OnClickListener() {
            @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
            @Override
            public void onClick(View v) {


                if (answerSelect==null){
                    answerSelect=button3.getText().toString();
                    button3.setBackground(getDrawable(R.drawable.edittext_layout_blue));

                }
                else {
                    answerSelect=button3.getText().toString();
                    button3.setBackground(getDrawable(R.drawable.edittext_layout_blue));
                    button2.setBackground(getDrawable(R.drawable.not_answerquestion));
                    button1.setBackground(getDrawable(R.drawable.not_answerquestion));
                    button4.setBackground(getDrawable(R.drawable.not_answerquestion));
                }
                Log.e(TAG, "onClick: "+answerSelect );

            }
        });

        button4.setOnClickListener(new View.OnClickListener() {
            @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
            @Override
            public void onClick(View v) {
                if (answerSelect==null){
                    answerSelect=button4.getText().toString();
                    button4.setBackground(getDrawable(R.drawable.edittext_layout_blue));

                }
                else {
                    answerSelect=button4.getText().toString();
                    button4.setBackground(getDrawable(R.drawable.edittext_layout_blue));
                    button1.setBackground(getDrawable(R.drawable.not_answerquestion));
                    button2.setBackground(getDrawable(R.drawable.not_answerquestion));
                    button3.setBackground(getDrawable(R.drawable.not_answerquestion));
                }
            }
        });

        next.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (answerSelect!=null)
                {
                    if (size==i){
                        k=-1;
                        Log.e(TAG, "onClick: Questions  end "+(i));
                        Log.e(TAG, "onClick: Questions  end "+answerSelect);
                        if (answerSelect.equalsIgnoreCase(questionsList.get(i).getCorrectAnswer()))
                        {
                            totalCorrectAnswer++;
                            estimatedTime = System.currentTimeMillis() - startTime;
                            timeTaken+=estimatedTime;
                            userResponse.setTotalCorrectAnswers(totalCorrectAnswer);
                            userResponse.setTotalTime(timeTaken);
                            questionsList.get(i).setAnswered(true);
                            questionsList.get(i).setAnsweringTime(estimatedTime);
                            Log.e(TAG, "onClick: Question ** True - "+(i) +"    "+estimatedTime);
                        }
                        countDownTimer.cancel();
                        setResponse();
                    }
                    else {
                        Log.e(TAG, "onClick: Question -//- "+(i) );
                            if (answerSelect.equalsIgnoreCase(questionsList.get(i).getCorrectAnswer()))
                            {
                                totalCorrectAnswer++;
                                estimatedTime = System.currentTimeMillis() - startTime;
                                timeTaken+=estimatedTime;
                                userResponse.setTotalCorrectAnswers(totalCorrectAnswer);
                                userResponse.setTotalTime(timeTaken);
                                questionsList.get(i).setAnswered(true);
                                questionsList.get(i).setAnsweringTime(estimatedTime);
                                Log.e(TAG, "onClick: Question  True-- "+(i) );
                                Log.e(TAG, "onClick: Question ** True - "+(i) +"    "+estimatedTime);
                            }

                        startNewQuestion();
                    }
                }
                else{
                    Toast.makeText(QuizStartActivity.this, "Please Select Any Option !", Toast.LENGTH_SHORT).show();
                }

            }
        });
    }

    public void startTimer(){
        counter=10;
        startTime = System.currentTimeMillis();
        countDownTimer = new CountDownTimer(10000, 1000){
            public void onTick(long millisUntilFinished)
            {
                timmer.setText(String.valueOf(counter));
                counter--;
            }
            public  void onFinish(){

                if (size>=i){
                   countDownTimer.cancel();
                   if (k!=-1){
                       setResponse();
                   }
                }
                else {
                    startNewQuestion();
                }
            }
        }.start();
    }

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    public void startNewQuestion(){
        startTimer();
        counter=10;
        button1.setBackground(getDrawable(R.drawable.not_answerquestion));
        button2.setBackground(getDrawable(R.drawable.not_answerquestion));
        button3.setBackground(getDrawable(R.drawable.not_answerquestion));
        button4.setBackground(getDrawable(R.drawable.not_answerquestion));
        if (size>i){
            i++;
            question.setText("Q."+(i+1)+" "+questionsList.get(i).getQuestion());
            button1.setText(questionsList.get(i).getOption1());
            button2.setText(questionsList.get(i).getOption2());
            button3.setText(questionsList.get(i).getOption3());
            button4.setText(questionsList.get(i).getOption4());
        }

    }

    public void setResponse(){

        progressDialog=new ProgressDialog(QuizStartActivity.this);
        progressDialog.setMessage("Submitting Response ...");
        progressDialog.setCancelable(false);
        progressDialog.show();
        next.setEnabled(false);
        userResponse.setUsername(userName);
        userResponse.setImageUrl(userimageUrl);
        userResponse.setQuestionsList(questionsList);
        userResponse.setTotalCorrectAnswers(totalCorrectAnswer);

        collectionReference=firestore
                .collection("UserDetails")
                .document(FirebaseAuth.getInstance().getUid())
                .collection("RecentQuiz");

        collectionReferenceAdmin=firestore.collection("Admin")
                .document("LiveQuiz").collection("LeaderBoard")
                .document(date).collection(entity.getName());

        collectionReference
                .add(userResponse)
                .addOnCompleteListener(new OnCompleteListener<DocumentReference>() {
            @Override
            public void onComplete(@NonNull Task<DocumentReference> task) {

                if (task.isSuccessful()){

                    collectionReferenceAdmin.document(FirebaseAuth.getInstance().getUid()).set(userResponse)
                            .addOnCompleteListener(new OnCompleteListener<Void>() {
                                @Override
                                public void onComplete(@NonNull Task<Void> task) {
                                    if (task.isSuccessful()){

                                        collectionReference=firestore.collection("UserDetails")
                                                .document(FirebaseAuth.getInstance().getUid()).
                                                        collection("ActiveQuiz");
                                        collectionReference.document(docId).delete();
                                        Toast.makeText(QuizStartActivity.this, "Thanks For Giving Quiz ", Toast.LENGTH_SHORT).show();
                                        startActivity(new Intent(QuizStartActivity.this,FinishQuizActivity.class));
                                        progressDialog.dismiss();
                                        finish();
                                    }
                                    else {
                                        progressDialog.dismiss();
                                        next.setEnabled(true);
                                        Toast.makeText(QuizStartActivity.this, "Failed To Submit response", Toast.LENGTH_SHORT).show();
                                        Toast.makeText(QuizStartActivity.this, "Please Submit Quiz ", Toast.LENGTH_SHORT).show();
                                    }
                                }
                            });
                }
                else {
                    progressDialog.dismiss();
                }
            }
        });
    }

    void init(){
        firestore=FirebaseFirestore.getInstance();
        viewPager=findViewById(R.id.viewPager);
        timmer=findViewById(R.id.timer);
        question=findViewById(R.id.questions);
        button1=findViewById(R.id.option1);
        button2=findViewById(R.id.option2);
        button3=findViewById(R.id.option3);
        button4=findViewById(R.id.option4);
        next=findViewById(R.id.next);

    }

    public void imageSlide(){

        viewPager.setAdapter(new SlidingImageAdapter2(imagelist,QuizStartActivity.this));

        NUM_PAGES=imagelist.size();

        final Handler handler=new Handler();
        final Runnable update=new Runnable() {
            @Override
            public void run() {
                if (currentPage==NUM_PAGES){
                    currentPage=0;
                }
                viewPager.setCurrentItem(currentPage++,true);

            }
        };
        Timer swipeTimer = new Timer();
        swipeTimer.schedule(new TimerTask() {
            @Override
            public void run() {
                handler.post(update);
            }
        }, 3000, 3000);

        viewPager.setOnPageChangeListener(new ViewPager.OnPageChangeListener() {

            @Override
            public void onPageSelected(int position) {
                currentPage = position;

            }

            @Override
            public void onPageScrolled(int pos, float arg1, int arg2) {

            }

            @Override
            public void onPageScrollStateChanged(int pos) {

            }
        });
    }
}

package com.logic.android.onetoone.views.LiveQuiz;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.viewpager.widget.ViewPager;

import com.logic.android.onetoone.Database.ActiveQuizEntity;
import com.logic.android.onetoone.ViewModel.ActiveViewmodel;
import com.logic.android.onetoone.adapters.FactsRecyclerAdapter;
import com.logic.android.onetoone.adapters.SlidingImageAdapter2;
import com.logic.android.onetoone.basicvideochat.R;
import com.logic.android.onetoone.models.Questions;
import com.logic.android.onetoone.models.RandomFacts;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Timer;
import java.util.TimerTask;

public class GetQuizReadyActivity extends AppCompatActivity {

    private static final String TAG = "GetQuizReadyActivity";

    ActiveViewmodel activeViewmodel;
    ActiveQuizEntity entity;
    Bundle bundle;
    ArrayList<String> imagelist=new ArrayList<>();
    private static int currentPage = 0;
    private static int NUM_PAGES = 0;
    String id,name,coins,desc,duration,imageUrl,startTime;
    int enrollFee,maxUsers,noOfRegistered,percentWinners;
    String date;
    Boolean open;
    List<Questions> questionsList=new ArrayList<>();
    List<RandomFacts> randomFactsList=new ArrayList<>();
    TextView textViewTimer;
    TextView nameTextView,factsTextView;
    ViewPager viewPager;
    String docId;
    RecyclerView factsRecyclerView;
    FactsRecyclerAdapter factsRecyclerAdapter;

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_get_quiz_ready);

        getWindow().setStatusBarColor(getResources().getColor(R.color.colorPrimary));
     //   getSupportActionBar().hide();

        bundle=getIntent().getExtras();
        id=bundle.getString("id");
        docId=bundle.getString("docId");
        name=bundle.getString("name");
        date=bundle.getString("date");
        coins=bundle.getString("coins");
        desc=bundle.getString("desc");
        duration=bundle.getString("duration");
        imageUrl=bundle.getString("imageUrl");
        startTime=bundle.getString("startTime");
        enrollFee=bundle.getInt("enrollFee");
        maxUsers=bundle.getInt("maxUsers");
        noOfRegistered=bundle.getInt("noOfRegistered");
        percentWinners=bundle.getInt("percentWinners");
        open=bundle.getBoolean("open");
        open=false;
        viewPager=findViewById(R.id.viewPager);
        nameTextView=findViewById(R.id.name);
        textViewTimer=findViewById(R.id.timer);
        factsRecyclerView=findViewById(R.id.factsRecyclerView);
        nameTextView.setText(name);
        imagelist.add(imageUrl);

        Log.e(TAG, "onCreate: "+id );
        init();

        // Temporary *-------------------
        questionsList=bundle.getParcelableArrayList("questions");
        randomFactsList=bundle.getParcelableArrayList("facts");
        entity=new ActiveQuizEntity(id,name,coins,desc,duration,imageUrl,startTime,date,enrollFee,
                maxUsers,noOfRegistered,open,percentWinners,questionsList);

        activeViewmodel= ViewModelProviders.of(GetQuizReadyActivity.this).get(ActiveViewmodel.class);

        /*
                    For Checking that Data is storing to the database or not Test Q
         */
        activeViewmodel.insert(entity);


        /*
                            Fetch method that is used to check that the data is successfully Inserted or not !

         */
        activeViewmodel.getAllQuizes().observe(this, new Observer<List<ActiveQuizEntity>>() {
            @Override
            public void onChanged(@Nullable List<ActiveQuizEntity> activeQuizEntities) {
                Log.e("dnvn","d"+activeQuizEntities.size());


            }
        });
        /*
                        Delete are the Quizes from the Database;
         */

        //    activeViewmodel.deleteTables();


        factsRecyclerView.setLayoutManager(new LinearLayoutManager(GetQuizReadyActivity.this));
        Log.e(TAG, "onCreate: "+ randomFactsList.get(1) );
        List<RandomFacts> factsList=new ArrayList<>();
        for (int i=0;i<randomFactsList.size();i++){
            JSONObject jsonObject=new JSONObject((Map)randomFactsList.get(i));
            RandomFacts randomFacts=new RandomFacts();
            try {
                randomFacts.setFact(jsonObject.getString("fact"));
                factsList.add(randomFacts);
            } catch (JSONException e) {
                e.printStackTrace();
            }

        }

        factsRecyclerAdapter=new FactsRecyclerAdapter(factsList,GetQuizReadyActivity.this,factsRecyclerView);
        factsRecyclerView.setAdapter(factsRecyclerAdapter);

        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                Intent intent=new Intent(GetQuizReadyActivity.this,QuizStartActivity.class);
                intent.putExtra("docId",docId);
                startActivity(intent);
                finish();
            }
        },4000);

    }

    public void init(){

        viewPager.setAdapter(new SlidingImageAdapter2(imagelist,GetQuizReadyActivity.this));

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

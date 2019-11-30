package com.logic.android.onetoone.views.homeLiveQuiz;

import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.SearchView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;
import com.logic.android.onetoone.adapters.SubCategoryAdapter;
import com.logic.android.onetoone.basicvideochat.R;
import com.logic.android.onetoone.models.Questions;
import com.logic.android.onetoone.models.Quiz1Details;
import com.logic.android.onetoone.models.QuizDetails;

import java.util.ArrayList;
import java.util.List;

public class HomeLiveQuizList extends AppCompatActivity {
    private String mParam1;
    FirebaseFirestore firestore;
    CollectionReference collectionReference;
    List<Quiz1Details> quizDetailsList=new ArrayList<>();
    SubCategoryAdapter adapter;
    RecyclerView recyclerView;
    ProgressBar progressBar;
    TextView textView;
    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home_live_quiz_list);
        getSupportActionBar().hide();
        getWindow().setStatusBarColor(getResources().getColor(R.color.colorPrimary));

        textView=findViewById(R.id.backTextView);

        textView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v)
            {
                finish();
            }
        });      // Back Button ---------------------------


        recyclerView=findViewById(R.id.subCategoriesRecyclerView);
        recyclerView.setHasFixedSize(true);
        progressBar=findViewById(R.id.progressBar);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));


    fetchCategories();
    }


    public  void fetchCategories(){
        firestore=FirebaseFirestore.getInstance();
        collectionReference=firestore.collection("Admin")
                .document("Quiz")
                .collection("Category");

        collectionReference
                .get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {

                        if (task.isSuccessful()){

                            for (QueryDocumentSnapshot snapshot:task.getResult()){

                                QuizDetails details=new QuizDetails();
                                details.setImageUrl(snapshot.getString("imageUrl"));
                                details.setCategoryDesc(snapshot.getString("categoryDesc"));
                                details.setCategoryName(snapshot.getString("categoryName"));
                                fetchCategoryData(details.getCategoryName());
                            }
                            progressBar.setVisibility(View.GONE);


                        }
                        else{
                            progressBar.setVisibility(View.GONE);
                            Toast.makeText(HomeLiveQuizList.this, "o Live Quiz Available!", Toast.LENGTH_SHORT).show();
                        }
                    }
                });
    }


    public void fetchCategoryData(String category){
        firestore=FirebaseFirestore.getInstance();
        collectionReference=firestore.collection("Admin")
                .document("Quiz")
                .collection(category);

        collectionReference
                .get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {

                        if (task.isSuccessful()){

                            for (QueryDocumentSnapshot snapshot:task.getResult()){

                                Log.e("SubCategoryActivity",""+snapshot.getData());
                                Quiz1Details details=new Quiz1Details();

                                details.setName(snapshot.getString("name"));
                                details.setDate(snapshot.getString("date"));
                                details.setImageUrl(snapshot.getString("imageUrl"));
                                details.setDesc(snapshot.getString("desc"));
                                details.setCoins(snapshot.getString("coins"));
                                details.setCategoryName(mParam1);
                                details.setStartTime(snapshot.getString("startTime"));
                                details.setEnrollFee(Integer.valueOf(snapshot.get("enrollFee").toString()));
                                details.setDuration(snapshot.getString("duration"));
                                details.setNoOfRegistered(Integer.valueOf(snapshot.get("noOfRegistered").toString()));
                                details.setQuestionsList((List<Questions>) snapshot.get("questionsList"));
                                quizDetailsList.add(details);
                            }
                            progressBar.setVisibility(View.GONE);
                            if (quizDetailsList.isEmpty()){
                                Toast.makeText(HomeLiveQuizList.this, "No Details Available!", Toast.LENGTH_SHORT).show();
                            }
                            adapter=new SubCategoryAdapter(HomeLiveQuizList.this,recyclerView,quizDetailsList);
                            recyclerView.setAdapter(adapter);

                        }
                        else{
                            progressBar.setVisibility(View.GONE);
                            Toast.makeText(HomeLiveQuizList.this, "No Details Available !", Toast.LENGTH_SHORT).show();
                        }
                    }
                });

    }
}

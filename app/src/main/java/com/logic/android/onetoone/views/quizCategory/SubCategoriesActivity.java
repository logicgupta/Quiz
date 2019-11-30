package com.logic.android.onetoone.views.quizCategory;

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

import java.util.ArrayList;
import java.util.List;

import com.logic.android.onetoone.adapters.SubCategoryAdapter;
import com.logic.android.onetoone.basicvideochat.R;
import com.logic.android.onetoone.models.Questions;
import com.logic.android.onetoone.models.Quiz1Details;


public class SubCategoriesActivity extends AppCompatActivity {

    private static final String ARG_PARAM1 = "category";
    private String mParam1;
    FirebaseFirestore firestore;
    CollectionReference collectionReference;
    List<Quiz1Details> quizDetailsList=new ArrayList<>();
    SubCategoryAdapter adapter;
    RecyclerView recyclerView;
    ProgressBar progressBar;
    TextView textView;
    SearchView searchCategory;
    Bundle bundle=new Bundle();
    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_sub_categories);
//        getSupportActionBar().hide();
        getWindow().setStatusBarColor(getResources().getColor(R.color.colorPrimary));

        bundle=getIntent().getExtras();
        mParam1=bundle.getString("category");

        textView=findViewById(R.id.backTextView);

        textView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v)
            {
                finish();
            }
    });      // Back Button ---------------------------

        searchCategory =findViewById(R.id.searchEdiText);

        searchCategory.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                searchCategory(query);
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                return false;
            }
        });

        recyclerView=findViewById(R.id.subCategoriesRecyclerView);
        recyclerView.setHasFixedSize(true);
        progressBar=findViewById(R.id.progressBar);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        textView.setText("   "+mParam1);
        fetchCategoryData();

    }

    public void fetchCategoryData(){
        firestore=FirebaseFirestore.getInstance();
        collectionReference=firestore.collection("Admin")
                .document("Quiz")
                .collection(mParam1);

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
                        Toast.makeText(SubCategoriesActivity.this, "No Details Available!", Toast.LENGTH_SHORT).show();
                    }
                    adapter=new SubCategoryAdapter(SubCategoriesActivity.this,recyclerView,quizDetailsList);
                    recyclerView.setAdapter(adapter);

                }
                else{
                    progressBar.setVisibility(View.GONE);
                    Toast.makeText(SubCategoriesActivity.this, "No Details Available !", Toast.LENGTH_SHORT).show();
                }
            }
        });

    }


    public void searchCategory(final String category){

        quizDetailsList.clear();
        adapter.notifyDataSetChanged();
        progressBar.setVisibility(View.VISIBLE);
        firestore=FirebaseFirestore.getInstance();
        collectionReference=firestore.collection("Admin")
                .document("Quiz")
                .collection(mParam1);

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
                                details.setImageUrl(snapshot.getString("imageUrl"));
                                details.setDesc(snapshot.getString("desc"));
                                details.setCoins(snapshot.getString("coins"));
                                details.setCategoryName(mParam1);
                                details.setStartTime(snapshot.getString("startTime"));
                                details.setEnrollFee(Integer.valueOf(snapshot.get("enrollFee").toString()));
                                details.setDuration(snapshot.getString("duration"));
                                details.setNoOfRegistered(Integer.valueOf(snapshot.get("noOfRegistered").toString()));
                                details.setQuestionsList((List<Questions>) snapshot.get("questionsList"));

                               if (category.equalsIgnoreCase(snapshot.getString("name"))){
                                   quizDetailsList.add(details);
                               }
                            }
                            progressBar.setVisibility(View.GONE);
                            if (quizDetailsList.isEmpty()){
                                Toast.makeText(SubCategoriesActivity.this, "No Details Available!", Toast.LENGTH_SHORT).show();
                            }
                            adapter=new SubCategoryAdapter(SubCategoriesActivity.this,recyclerView,quizDetailsList);
                            recyclerView.setAdapter(adapter);

                        }
                        else{
                            progressBar.setVisibility(View.GONE);
                            Toast.makeText(SubCategoriesActivity.this, "No Details Available !", Toast.LENGTH_SHORT).show();
                        }
                    }
                });
    }

}

package com.logic.android.onetoone.views.quizCategory;

import android.os.Bundle;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.SearchView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;
import com.logic.android.onetoone.adapters.CategoryAdapter;
import com.logic.android.onetoone.basicvideochat.R;
import com.logic.android.onetoone.models.QuizDetails;

import java.util.ArrayList;
import java.util.List;

public class CategoryActivity extends AppCompatActivity {

    FirebaseFirestore firestore;
    CollectionReference collectionReference;
    List<QuizDetails> quizDetailsList=new ArrayList<>();
    CategoryAdapter adapter;
    RecyclerView recyclerView;
    ProgressBar progressBar;
    TextView textView;
    SearchView editTextSearch;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.fragment_category);
        getWindow().setTitle("Category");
        progressBar=findViewById(R.id.progressBar);
        recyclerView=findViewById(R.id.recyclerView);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(CategoryActivity.this));
        fetchCategoryData();
    }

    public void fetchCategoryData(){
        quizDetailsList.clear();
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
                                quizDetailsList.add(details);
                            }
                            progressBar.setVisibility(View.GONE);
                            if (quizDetailsList.isEmpty()){
                                Toast.makeText(CategoryActivity.this, "No Details Available!", Toast.LENGTH_SHORT).show();
                            }
                            adapter=new CategoryAdapter(CategoryActivity.this,recyclerView,quizDetailsList);
                            recyclerView.setAdapter(adapter);

                        }
                        else{
                            progressBar.setVisibility(View.GONE);
                            Toast.makeText(CategoryActivity.this, "No Details Available !", Toast.LENGTH_SHORT).show();
                        }
                    }
                });
    }
}

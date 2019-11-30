package com.logic.android.onetoone.views.homeScreen;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.ArrayList;
import java.util.List;

import com.google.firebase.firestore.FirebaseFirestoreException;
import com.google.firebase.firestore.ListenerRegistration;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;
import com.logic.android.onetoone.Util.Constant;
import com.logic.android.onetoone.adapters.DemoAdapter;
import com.logic.android.onetoone.adapters.HomeLiveQuizAdapter;
import com.logic.android.onetoone.basicvideochat.R;
import com.logic.android.onetoone.models.Questions;
import com.logic.android.onetoone.models.Quiz1Details;
import com.logic.android.onetoone.models.QuizDetails;
import com.logic.android.onetoone.views.AddCoinsActivity;
import com.logic.android.onetoone.views.homeLiveQuiz.HomeLiveQuizList;
import com.logic.android.onetoone.views.quizCategory.CategoryActivity;
import com.logic.android.onetoone.views.quizCategory.SubCategoriesActivity;
import com.logic.android.onetoone.views.withdraw.WithdrawMoneyActivity;


public class HomeFragment extends Fragment {

    RecyclerView recyclerView;
    DemoAdapter liveQuizAdapter;
    List<Quiz1Details> quizDetailsList=new ArrayList<>();
    FirebaseFirestore firestore;
    CollectionReference collectionReference1;
   int  images[]={R.mipmap.got,R.mipmap.robot,R.mipmap.coding};
   String name[]={"Got","Robot","Coding"};
   ListenerRegistration listenerRegistration1;
   String userName;
    HomeLiveQuizAdapter adapter;


    ImageView imageViewViewMovies,imageViewKnowlege,imageViewSports,imageViewShows;
    TextView sellAllCat;
    TextView winAmountTextView,coinTextView,referralTextView;
    LinearLayout myWinnings , addCoins;
     Long userCoinBalance;
     Long userWallet;
     TextView textView;
    private CollectionReference collectionReference;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_home, container, false);
    }


    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        if (userName!=null){
            getActivity().setTitle("Welcome , "+userName);
        }
        else {
            getActivity().setTitle("Welcome Back ");
        }

        getActivity().getWindow().setStatusBarColor(getResources().getColor(R.color.colorPrimary));
        recyclerView=getActivity().findViewById(R.id.homeRecyclerView);
        imageViewViewMovies =getActivity().findViewById(R.id.movies);
        imageViewKnowlege =getActivity().findViewById(R.id.general);
        imageViewShows =getActivity().findViewById(R.id.shows);
        imageViewSports =getActivity().findViewById(R.id.sports);
        sellAllCat=getActivity().findViewById(R.id.seeAllCategory);
        myWinnings = getActivity().findViewById(R.id.myWinnings);
        addCoins = getActivity().findViewById(R.id.addCoins);
        winAmountTextView = getActivity().findViewById(R.id.walletBalance);
        coinTextView = getActivity().findViewById(R.id.coinBalance);
        referralTextView = getActivity().findViewById(R.id.referralCode);
        textView=getActivity().findViewById(R.id.seeActiveQuiz);

        textView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getActivity(), HomeLiveQuizList.class));
            }
        });


        imageViewViewMovies.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intent=new Intent(getActivity(), SubCategoriesActivity.class);
                intent.putExtra("category","Movie");
                startActivity(intent);

            }
        });

        myWinnings.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent=new Intent(getActivity(), WithdrawMoneyActivity.class);
                intent.putExtra("wallet",userWallet);
                startActivity(intent);
            }
        });

        addCoins.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent intent=new Intent(getActivity(), AddCoinsActivity.class);
                startActivity(intent);

            }
        });

        imageViewKnowlege.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(getActivity(),SubCategoriesActivity.class);
                intent.putExtra("category","General Knowledge");
                startActivity(intent);
            }
        });

        imageViewSports.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(getActivity(),SubCategoriesActivity.class);
                intent.putExtra("category","Sports");
                startActivity(intent);
            }
        });

        imageViewShows.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intent=new Intent(getActivity(),SubCategoriesActivity.class);
                intent.putExtra("category","TV Shows");
                startActivity(intent);
            }
        });

        sellAllCat.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
               startActivity(new Intent(getActivity(), CategoryActivity.class));
            }
        });

        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity(),LinearLayoutManager.HORIZONTAL,false));
        recyclerView.setHasFixedSize(false);
        getLiveQuiz();

    }

    public void populate(){
        for (int i=0;i<3;i++){
            QuizDetails quizDetails=new QuizDetails();
            quizDetails.setImage(images[i]);
            quizDetails.setSubCategoryName(name[i]);
           // quizDetailsList.add(quizDetails);
        }
    }
    public void getLiveQuiz(){
      /*  populate();
        firestore=FirebaseFirestore.getInstance();
        //collectionReference1=firestore.collection("");
        liveQuizAdapter=new DemoAdapter(getActivity(),recyclerView,quizDetailsList);
        recyclerView.setAdapter(liveQuizAdapter);
        liveQuizAdapter.notifyDataSetChanged();
    */
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



                        }
                        else{

                            Toast.makeText(getActivity(), "o Live Quiz Available!", Toast.LENGTH_SHORT).show();
                        }
                    }
                });
    }


    public void fetchCategoryData(final String category){
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
                                details.setCategoryName(category);
                                details.setStartTime(snapshot.getString("startTime"));
                                details.setEnrollFee(Integer.valueOf(snapshot.get("enrollFee").toString()));
                                details.setDuration(snapshot.getString("duration"));
                                details.setNoOfRegistered(Integer.valueOf(snapshot.get("noOfRegistered").toString()));
                                details.setQuestionsList((List<Questions>) snapshot.get("questionsList"));
                                quizDetailsList.add(details);
                            }

                            if (quizDetailsList.isEmpty()){
                                Toast.makeText(getActivity(), "No Details Available!", Toast.LENGTH_SHORT).show();
                            }
                            adapter=new HomeLiveQuizAdapter(getActivity(),recyclerView,quizDetailsList);
                            recyclerView.setAdapter(adapter);

                        }
                        else{
                            Toast.makeText(getActivity(), "No Details Available !", Toast.LENGTH_SHORT).show();
                        }
                    }
                });

    }



    @Override
    public void onStart() {
        super.onStart();

        SharedPreferences preferences=getActivity().getSharedPreferences(Constant.userKey,0);
        SharedPreferences.Editor editor=preferences.edit();
        if (preferences.getString(Constant.name,null)!=null){

            userName=preferences.getString(Constant.name,null);
            getActivity().setTitle("Welcome , "+userName);
        }

            firestore= FirebaseFirestore.getInstance();
            collectionReference1=firestore.collection("UserDetails");

           listenerRegistration1=collectionReference1
                    .document(FirebaseAuth.getInstance().getUid())
                    .addSnapshotListener(new EventListener<DocumentSnapshot>() {
                        @Override
                        public void onEvent(@javax.annotation.Nullable DocumentSnapshot documentSnapshot
                                , @javax.annotation.Nullable FirebaseFirestoreException e) {
                            if (e!=null){
                            }
                            else {
                                userName=documentSnapshot.getString("name");
                                userCoinBalance=documentSnapshot.getLong("coinBalance");
                                userWallet=documentSnapshot.getLong("walletBalanace");
                                getActivity().setTitle("Welcome , "+userName);
                                winAmountTextView.setText("₹ "+userWallet);
                                coinTextView.setText(""+userCoinBalance);
                                referralTextView.setText(""+userName);

                            }
                        }
                    });

    }

    @Override
    public void onStop() {
        super.onStop();

    }
}

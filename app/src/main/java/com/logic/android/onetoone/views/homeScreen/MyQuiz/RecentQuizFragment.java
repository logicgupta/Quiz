package com.logic.android.onetoone.views.homeScreen.MyQuiz;

import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.google.firebase.firestore.ListenerRegistration;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;
import com.logic.android.onetoone.adapters.RecentRecyclerViewAdapter;
import com.logic.android.onetoone.basicvideochat.R;
import com.logic.android.onetoone.models.Questions;
import com.logic.android.onetoone.models.Quiz1Details;

import java.util.ArrayList;
import java.util.List;


public class RecentQuizFragment extends Fragment {

    private static final String TAG = "RecentQuizFragment";
    List<Quiz1Details> quiz1Details=new ArrayList<Quiz1Details>();
    RecyclerView recyclerView;
    ProgressBar progressBar;
    FirebaseFirestore firestore;
    CollectionReference collectionReference,reference;
    RecentRecyclerViewAdapter adapter;
    private ListenerRegistration listener;


    public RecentQuizFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_recenr_quiz, container, false);
    }
    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        progressBar=getActivity().findViewById(R.id.progressBar2);
        recyclerView=getActivity().findViewById(R.id.recyclerView2);
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        firestore=FirebaseFirestore.getInstance();
        //fetchDetails();
        super.onActivityCreated(savedInstanceState);

    }

    /*
                            Need to make Changes here =================------------------------------------
     */
    public void fetchDetails(){
        quiz1Details.clear();
        collectionReference=firestore.collection("UserDetails")
                .document(FirebaseAuth.getInstance().getUid())
                .collection("RecentQuiz");

        collectionReference.get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
            @Override
            public void onComplete(@NonNull Task<QuerySnapshot> task) {
                if (task.isSuccessful()){
                    for (QueryDocumentSnapshot snapshot: task.getResult()){
                        Quiz1Details details=new Quiz1Details();
                        details.setName(snapshot.getString("name"));
                        details.setImageUrl(snapshot.getString("imageUrl"));
                        details.setStartTime(snapshot.getString("startTime"));
                        details.setQuestionsList((List<Questions>) snapshot.get("questionsList"));
                        details.setCoins(snapshot.getString("coins"));
                        details.setDesc(snapshot.getString("desc"));
//                        details.setPrizeList((Top10Prize) snapshot.get("prizeList"));
                        details.setDocId(snapshot.getId());
                        quiz1Details.add(details);
                    }
                    if (quiz1Details.isEmpty()){
                        Toast.makeText(getActivity(), "No Active Quiz ", Toast.LENGTH_SHORT).show();
                    }
                    progressBar.setVisibility(View.GONE);
                    adapter=new RecentRecyclerViewAdapter(getActivity(),recyclerView,quiz1Details);
                    recyclerView.setAdapter(adapter);

                }
            }
        });
    }


    @Override
    public void onStart() {
        super.onStart();

        quiz1Details.clear();

        collectionReference=firestore.collection("UserDetails")
                .document(FirebaseAuth.getInstance().getUid())
                .collection("RecentQuiz");

        listener = collectionReference.addSnapshotListener(new EventListener<QuerySnapshot>() {
            @Override
            public void onEvent(@javax.annotation.Nullable QuerySnapshot queryDocumentSnapshots, @javax.annotation.Nullable FirebaseFirestoreException e) {

                if (e!=null){

                }
                else {
                    for (final DocumentSnapshot snapshot: queryDocumentSnapshots.getDocuments()){
                        final Quiz1Details details=new Quiz1Details();
                        details.setName(snapshot.getString("name"));
                        details.setImageUrl(snapshot.getString("imageUrl"));
                        details.setStartTime(snapshot.getString("startTime"));
                        details.setQuestionsList((List<Questions>) snapshot.get("questionsList"));
                        details.setCoins(snapshot.getString("coins"));
                        details.setDuration(snapshot.getString("duration"));
                        details.setDesc(snapshot.getString("desc"));
                        details.setDate(snapshot.getString("date"));
                        details.setEnrollFee(Integer.valueOf(snapshot.get("enrollFee").toString()));
//                        details.setPrizeList((Top10Prize) snapshot.get("prizeList"));
                        details.setDocId(snapshot.getId());

                        String date = snapshot.getString("date");
                        String name = snapshot.getString("name");

                        reference=firestore
                                .collection("Admin")
                                .document("LiveQuiz")
                                .collection("LeaderBoard")
                                .document(date)
                                .collection(name);

                        reference
                                .document(FirebaseAuth.getInstance().getUid())
                                .get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
                            @RequiresApi(api = Build.VERSION_CODES.N)
                            @Override
                            public void onComplete(@NonNull Task<DocumentSnapshot> task) {

                                if (task.isSuccessful()){
                                    DocumentSnapshot snapshot1=task.getResult();
                                    Log.e(TAG, "onComplete: "+snapshot1.getBoolean("showResult"));
                                    details.setResult(snapshot1.getBoolean("showResult"));
                                    details.setWinAmount(Math.toIntExact(snapshot1.getLong("amountWin")));
                                    details.setCorrectAnswer(Math.toIntExact(snapshot1.getLong("totalCorrectAnswers")));
                                    quiz1Details.add(details);
                                    adapter=new RecentRecyclerViewAdapter(getActivity(),recyclerView,quiz1Details);
                                    recyclerView.setAdapter(adapter);
                                }
                            }
                        });
                        if (quiz1Details.isEmpty()){
                          //  Toast.makeText(getActivity(), "No Quiz Played !", Toast.LENGTH_SHORT).show();
                        }
                        progressBar.setVisibility(View.GONE);

                    }

                  //  adapter=new RecentRecyclerViewAdapter(getActivity(),recyclerView,quiz1Details);
                   // recyclerView.setAdapter(adapter);
                }
            }
        });

    }

    @Override
    public void onStop() {
        super.onStop();
        listener.remove();
    }
}

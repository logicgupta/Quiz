package com.logic.android.onetoone.views.homeScreen.MyQuiz;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;
import android.widget.Toast;

import androidx.annotation.Nullable;
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
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.google.firebase.firestore.ListenerRegistration;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;
import com.logic.android.onetoone.adapters.ActiveRecyclerAdapter;
import com.logic.android.onetoone.basicvideochat.R;
import com.logic.android.onetoone.models.Questions;
import com.logic.android.onetoone.models.Quiz1Details;
import com.logic.android.onetoone.models.RandomFacts;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

public class ActiveQuizFragment extends Fragment {
    private static final String TAG ="ActivityQuizFragment";
    List<Quiz1Details> quiz1Details=new ArrayList<>();
    RecyclerView recyclerView;
    ProgressBar progressBar;
    FirebaseFirestore firestore;
    CollectionReference collectionReference;
    ActiveRecyclerAdapter adapter;
    ListenerRegistration listenerRegistration;
    String todayDate,todayDayDate;
    String todayFullTime,todayHourTime,todayIntervalTime,todyMinuteTime;


    public ActiveQuizFragment() {
        // Required empty public constructor
    }

    public static ActiveQuizFragment newInstance(String param1, String param2) {
        ActiveQuizFragment fragment = new ActiveQuizFragment();

        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        DateFormat dfDate = new SimpleDateFormat("yyyy.MM.dd");
        DateFormat dfTime = new SimpleDateFormat("h:mm a");
        todayDate= dfDate.format(Calendar.getInstance().getTime());
        todayFullTime= dfTime.format(Calendar.getInstance().getTime());

        Log.e(TAG, "onCreate: "+todayDate );
        Log.e(TAG, "onCreate: "+todayFullTime );

        String dateSplit[]=todayDate.split("\\.");
        String timeSplit[]=todayFullTime.split(":");
        String timeIntervalSplit[]=todayFullTime.split(" ");
        todayDayDate=dateSplit[2];                  // date
        todayHourTime=timeSplit[0];                    // Hour Time
        todayIntervalTime=timeIntervalSplit[1];         // AM OR PM

        Log.e(TAG, "onCreate:   day Date "+dateSplit[2] );
        Log.e(TAG, "onCreate: hour Time  "+todayHourTime );
        Log.e(TAG, "onCreate:  Interval"+todayIntervalTime);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_active_quiz, container, false);
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        progressBar=getActivity().findViewById(R.id.progressBar);
        recyclerView=getActivity().findViewById(R.id.recyclerView);
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        firestore=FirebaseFirestore.getInstance();
        super.onActivityCreated(savedInstanceState);

    }

    public void fetchDetails(){
        collectionReference=firestore.collection("UserDetails")
                .document(FirebaseAuth.getInstance().getUid()).collection("ActiveQuiz");

        quiz1Details.clear();

        listenerRegistration=collectionReference
                .addSnapshotListener(new EventListener<QuerySnapshot>() {
                    @Override
                    public void onEvent(@javax.annotation.Nullable QuerySnapshot queryDocumentSnapshots, @javax.annotation.Nullable FirebaseFirestoreException e) {
                        if (e!=null){

                            Toast.makeText(getActivity(), "Failed to Load !", Toast.LENGTH_SHORT).show();
                        }
                        else {
                            for (DocumentSnapshot snapshot: queryDocumentSnapshots.getDocuments()){
                                Quiz1Details details=new Quiz1Details();
                                details.setId(snapshot.getString("id"));
                                details.setDocId(snapshot.getId());
                                details.setName(snapshot.getString("name"));
                                details.setDate(snapshot.getString("date"));
                                details.setImageUrl(snapshot.getString("imageUrl"));
                                details.setStartTime(snapshot.getString("startTime"));
                                details.setRandomFacts((List<RandomFacts>) snapshot.get("randomFacts"));
                                details.setQuestionsList((List<Questions>) snapshot.get("questionsList"));
                                details.setDuration(snapshot.getString("duration"));
                                details.setNoOfRegistered(Integer.valueOf(snapshot.get("noOfRegistered").toString()));
                                details.setEnrollFee(Integer.valueOf(snapshot.get("enrollFee").toString()));
                                details.setMaxUsers(Integer.valueOf(snapshot.get("maxUsers").toString()));
                                details.setPercentWinners(Integer.valueOf(snapshot.get("percentWinners").toString()));
                                details.setCoins(snapshot.getString("coins"));
                                details.setDesc(snapshot.getString("desc"));
//                        details.setPrizeList((Top10Prize) snapshot.get("prizeList"));
                                details.setDocId(snapshot.getId());
                                String times[]=details.getStartTime().split(" ");
                                String x[]=times[0].split(":");
                                String d[]=snapshot.getString("date").split("-");
                                Log.e(TAG, "onEvent: "+x[0]+"  "+d[0]);
                              /*  if(Integer.parseInt(x[0])<=Integer.parseInt(todayHourTime)
                                        && Integer.parseInt(d[0])==Integer.parseInt(todayDayDate))
                                        */
                                quiz1Details.add(details);
                            }
                            if (quiz1Details.isEmpty()){
                               Toast.makeText(getActivity(), "Not Enrolled in  Quiz ", Toast.LENGTH_SHORT).show();
                            }
                            progressBar.setVisibility(View.GONE);
                            adapter=new ActiveRecyclerAdapter(getActivity(),recyclerView,quiz1Details);
                            recyclerView.setAdapter(adapter);

                        }
                    }
                });
    }


    @Override
    public void onStart() {
        super.onStart();
        fetchDetails();


    }

    @Override
    public void onStop() {
        super.onStop();
        listenerRegistration.remove();
    }
}

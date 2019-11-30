package com.logic.android.onetoone.views.homeScreen;

import android.content.ContentResolver;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.MimeTypeMap;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;
import androidx.cardview.widget.CardView;
import androidx.fragment.app.Fragment;

import com.bumptech.glide.Glide;
import com.freshchat.consumer.sdk.Freshchat;
import com.freshchat.consumer.sdk.FreshchatConfig;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.google.firebase.firestore.ListenerRegistration;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;
import com.logic.android.onetoone.Util.Constant;
import com.logic.android.onetoone.basicvideochat.R;
import com.logic.android.onetoone.views.withdraw.WithdrawMoneyActivity;

import de.hdodenhof.circleimageview.CircleImageView;

import static android.app.Activity.RESULT_OK;

public class ProfileFragment extends Fragment {



    CircleImageView imageViewPhoto;
    ImageView imageViewEdit;
    TextView textViewName,textViewNumber,textViewWinnings
            ,textViewCoins,textViewSupport,textViewAboutUs,textViewReferral;
    ProgressBar progressBar;
    TextView textViewBack;
    CardView walletCardView;


     FirebaseFirestore firestore;
     CollectionReference collectionReference;
     StorageReference storageReference;
     Uri uri;
     FirebaseStorage storage;
     FirebaseAuth firebaseAuth;
     String userName,userNumber,userEmail,userImageUrl;
     Long userCoinBalance,userWallet,userCoin;
     ListenerRegistration listenerRegistration;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_profile, container, false);
    }

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        getActivity().getWindow().setStatusBarColor(getResources().getColor(R.color.colorPrimary));
        getActivity().setTitle("Profile");
        textViewName=getActivity().findViewById(R.id.name);
        textViewNumber=getActivity().findViewById(R.id.numberTextView);
        textViewWinnings=getActivity().findViewById(R.id.walletBalance);
        textViewCoins=getActivity().findViewById(R.id.coins);
        textViewReferral=getActivity().findViewById(R.id.referral);
        textViewSupport=getActivity().findViewById(R.id.support);
        textViewAboutUs=getActivity().findViewById(R.id.aboutUs);
        imageViewPhoto=getActivity().findViewById(R.id.imageViewUser);
        imageViewEdit=getActivity().findViewById(R.id.edit);
        progressBar=getActivity().findViewById(R.id.progress_bar);
        walletCardView=getActivity().findViewById(R.id.walletCard);

        setupFreshDesk();


        textViewSupport.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Freshchat.showConversations(getActivity());
            }
        });

        imageViewEdit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openGalery();
            }
        });
        if (userImageUrl!=null){
            Glide.with(getActivity()).load(userImageUrl).into(imageViewPhoto);
            textViewName.setText(""+userName);
            textViewNumber.setText(""+userEmail);
        }


        walletCardView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(getActivity(), WithdrawMoneyActivity.class);
                intent.putExtra("wallet",userWallet);
                startActivity(intent);
            }
        });

    }

    void setupFreshDesk() {
        String appId = "09ff078d-de4a-4f8d-a102-872fa4368866";
        String appKey = "1c35d1a0-04aa-4fdf-b2ea-3d7b63ab776f";
        FreshchatConfig freshchatConfig = new FreshchatConfig(appId, appKey);
        freshchatConfig.setCameraCaptureEnabled(true)
                .setGallerySelectionEnabled(true);
//        Freshchat.setImageLoader(com.freshchat.consumer.sdk.j.af.aw(getApplicationContext()));

        Freshchat.getInstance(getActivity()).init(freshchatConfig);
    }
    public void getData(){

        firebaseAuth=FirebaseAuth.getInstance();
        firestore= FirebaseFirestore.getInstance();
        collectionReference=firestore.collection("UserDetails");

        collectionReference
                .document(firebaseAuth.getUid()).get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
            @Override
            public void onComplete(@NonNull Task<DocumentSnapshot> task) {

                if (task.isSuccessful()){
                    DocumentSnapshot snapshot=task.getResult();
                    String name=snapshot.getString("name");
                    String number=snapshot.getString("mobileNumber");
                   String imageUrl=snapshot.getString("imageUrl");
                   if (imageUrl!=null){
                       Glide.with(getActivity()).load(imageUrl).into(imageViewPhoto);
                   }
                   if (name!=null){
                       textViewName.setText(name);
                   }

                    textViewNumber.setText(number);

                }
                else {


                    Toast.makeText(getActivity(), "Failed To Fetch Details", Toast.LENGTH_SHORT).show();
                }


            }
        });
    }

    public void openGalery(){

        Intent intent=new Intent();
        intent.setAction(Intent.ACTION_GET_CONTENT);
        intent.setType("image/*");
        startActivityForResult(intent,101);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (data!=null && requestCode==101 && resultCode==RESULT_OK){
            uri=data.getData();
           uploadPhoto();
            Toast.makeText(getActivity(), "Updating ...", Toast.LENGTH_SHORT).show();
            progressBar.setVisibility(View.VISIBLE);
            imageViewPhoto.setImageURI(data.getData());
        }
        else{
            Toast.makeText(getActivity(), "Please Select Photo", Toast.LENGTH_SHORT).show();
        }

    }

    public  void uploadPhoto(){
        final StorageReference storageReference2;
        storage=FirebaseStorage.getInstance();
        storageReference=storage.getReference();
        storageReference2=storageReference
                .child("Images/"+"Users"+"/Profile/"+System.currentTimeMillis()+"."+getImageExtension(uri));

        storageReference2
                .putFile(uri).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
            @Override
            public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                storageReference2.getDownloadUrl().addOnCompleteListener(new OnCompleteListener<Uri>() {
                    @Override
                    public void onComplete(@NonNull Task<Uri> task) {

                        if (task.isSuccessful()){
                            firebaseAuth=FirebaseAuth.getInstance();
                            firestore= FirebaseFirestore.getInstance();
                            collectionReference=firestore.collection("UserDetails");

                            collectionReference
                                    .document(firebaseAuth.getUid())
                                    .update("imageUrl",task.getResult().toString()).addOnCompleteListener(new OnCompleteListener<Void>() {
                                @Override
                                public void onComplete(@NonNull Task<Void> task) {

                                    if (task.isSuccessful()){
                                        progressBar.setVisibility(View.GONE);
                                        Toast.makeText(getActivity(), "Successfully Updated Photo !", Toast.LENGTH_SHORT).show();
                                    }
                                    else {
                                        progressBar.setVisibility(View.GONE);
                                        Toast.makeText(getActivity(), "Failed To Update Photo", Toast.LENGTH_SHORT).show();
                                    }
                                }
                            });
                        }
                        else{
                            progressBar.setVisibility(View.GONE);
                            Toast.makeText(getActivity(), "Failed To Update Photo", Toast.LENGTH_SHORT).show();
                        }
                    }
                });

            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {

            }
        });
    }

    public String getImageExtension(Uri uri){
        ContentResolver contentResolver=getActivity().getContentResolver();
        MimeTypeMap mime=MimeTypeMap.getSingleton();
        return mime.getExtensionFromMimeType(contentResolver.getType(uri));
    }

    @Override
    public void onStart() {
        super.onStart();

        SharedPreferences preferences=getActivity().getSharedPreferences(Constant.userKey,0);
        final SharedPreferences.Editor editor=preferences.edit();
        if (preferences.getString(Constant.name,null)!=null){
            userName=preferences.getString(Constant.name,null);
            userEmail=preferences.getString(Constant.email,null);
        }

            firestore= FirebaseFirestore.getInstance();
            collectionReference=firestore.collection("UserDetails");

            listenerRegistration=collectionReference
                    .document(FirebaseAuth.getInstance().getUid())
                    .addSnapshotListener(new EventListener<DocumentSnapshot>() {
                        @Override
                        public void onEvent(@javax.annotation.Nullable DocumentSnapshot documentSnapshot
                                , @javax.annotation.Nullable FirebaseFirestoreException e) {
                            if (e!=null){

                            }
                            else {
                                userName=documentSnapshot.getString("name");
                                userEmail=documentSnapshot.getString("emailId");
                                userNumber=documentSnapshot.getString("mobileNumber");
                                userImageUrl=documentSnapshot.getString("imageUrl");
                                userCoinBalance=documentSnapshot.getLong("coinBalance");
                                userWallet=documentSnapshot.getLong("walletBalanace");
                                Glide.with(getActivity()).load(userImageUrl).into(imageViewPhoto);
                                textViewName.setText(""+userName);
                                textViewNumber.setText(""+userEmail);
                                editor.putString(Constant.email,userEmail);
                                editor.putString(Constant.name,userName);
                                editor.putString(Constant.number,userNumber);
                                editor.putString(Constant.imageUrl,userImageUrl);
                                editor.putLong(Constant.coinBalance,userCoinBalance);
                                editor.putLong(Constant.walletBalance,userWallet);
                                editor.commit();
                                Log.e("Listener  ", "onEvent: "+userCoinBalance );
                                Log.e("Listener  ", "onEvent: "+userWallet );
                                textViewCoins.setText(""+userCoinBalance);
                                textViewWinnings.setText("₹ "+userWallet);

                            }
                        }
                    });
    }

    @Override
    public void onStop() {
        super.onStop();
        listenerRegistration.remove();
    }

}

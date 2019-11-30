package com.logic.android.onetoone.views.authentication;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.FirebaseFirestore;

import com.google.firebase.firestore.SetOptions;
import com.logic.android.onetoone.basicvideochat.R;
import com.logic.android.onetoone.models.PersonalDetails;
import com.logic.android.onetoone.views.homeScreen.MainScreenActivity;

public class RegisterFragment extends Fragment {


    public RegisterFragment() {
        // Required empty public constructor
    }

    TextView sign_in_Button;
    EditText editTextName,editTextEmail,
            editTextPassword,editTextConfirmPassword,editTextNumber;

    FirebaseAuth firebaseAuth;
    FirebaseFirestore firestore;
    CollectionReference collectionReference;
    ProgressDialog progressDialog;

    public static RegisterFragment newInstance(String param1, String param2) {
        RegisterFragment fragment = new RegisterFragment();


        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_register, container, false);
    }


    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        progressDialog=new ProgressDialog(getActivity());
        firebaseAuth=FirebaseAuth.getInstance();

        sign_in_Button=getActivity().findViewById(R.id.sign_up_button);
        editTextName=getActivity().findViewById(R.id.name);
        editTextEmail=getActivity().findViewById(R.id.email);
        editTextPassword=getActivity().findViewById(R.id.password);
        editTextConfirmPassword=getActivity().findViewById(R.id.confirmPassword);
        editTextNumber=getActivity().findViewById(R.id.editTextNumber);


        sign_in_Button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                progressDialog.setMessage("Creating Account ...");
                progressDialog.setCancelable(false);
                progressDialog.show();

                if (editTextName.getText().toString().equalsIgnoreCase("")){
                    Toast.makeText(getActivity(), "Please Enter Full Name !", Toast.LENGTH_SHORT).show();
                    progressDialog.dismiss();
                }
                else if (editTextEmail.getText().toString().equalsIgnoreCase("")){
                    Toast.makeText(getActivity(), "Please Enter EmailId !", Toast.LENGTH_SHORT).show();
                    progressDialog.dismiss();

                }
                else if (editTextPassword.getText().toString().equalsIgnoreCase("")){
                    Toast.makeText(getActivity(), "Please Enter Password !", Toast.LENGTH_SHORT).show();
                    progressDialog.dismiss();

                }
                else if (editTextConfirmPassword.getText().toString().length()<=6){
                    Toast.makeText(getActivity(), "Please Enter large password !", Toast.LENGTH_SHORT).show();
                    progressDialog.dismiss();

                }
                else if (editTextConfirmPassword.getText().toString().equalsIgnoreCase("")){
                    Toast.makeText(getActivity(), "Please Enter Confirm Password !", Toast.LENGTH_SHORT).show();
                    progressDialog.dismiss();

                }
                else if (!editTextPassword.getText().toString().equals(editTextConfirmPassword.getText().toString())){
                    editTextConfirmPassword.setError("Password Does Not Match");
                    progressDialog.dismiss();
                }
                else {

                    signUp(editTextEmail.getText().toString(),editTextPassword.getText().toString()
                            ,editTextName.getText().toString(),editTextNumber.getText().toString());

                }

            }
        });
    }
    public void signUp(final String email, String password, final String name, final String number){
        firebaseAuth.createUserWithEmailAndPassword(email,password)
                .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {

                        if (task.isSuccessful()){
                           savedata(email,name,number);
                        }
                        else{
                            progressDialog.dismiss();
                            Toast.makeText(getActivity(), " Please Try Again !", Toast.LENGTH_SHORT).show();
                        }
                    }
                });
    }

    public void savedata(String email,String name,String number){

        PersonalDetails personalDetails=new PersonalDetails(name,number,email);

        firestore=FirebaseFirestore.getInstance();
        collectionReference=firestore.collection("UserDetails");

        collectionReference
                .document(firebaseAuth.getUid())
                .set(personalDetails, SetOptions.merge())
                .addOnCompleteListener(new OnCompleteListener<Void>() {
            @Override
            public void onComplete(@NonNull Task<Void> task) {
                if (task.isSuccessful()){
                    progressDialog.dismiss();
                    startActivity(new Intent(getActivity(), MainScreenActivity.class));
                    getActivity().finish();
                }
                else{
                    progressDialog.dismiss();
                    Toast.makeText(getActivity(), " Please Try Again !", Toast.LENGTH_SHORT).show();
                }

            }
        });


    }

    public void updateUi(FirebaseUser user){
        if (user!=null){
            startActivity(new Intent(getActivity(), MainScreenActivity.class));
        }
    }


}

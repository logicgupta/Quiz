package com.logic.android.onetoone.views.authentication;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.facebook.AccessToken;
import com.facebook.CallbackManager;
import com.facebook.FacebookCallback;
import com.facebook.FacebookException;
import com.facebook.FacebookSdk;
import com.facebook.login.LoginManager;
import com.facebook.login.LoginResult;
import com.google.android.gms.auth.api.Auth;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.auth.api.signin.GoogleSignInResult;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthCredential;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FacebookAuthProvider;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.GoogleAuthProvider;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.SetOptions;
import com.logic.android.onetoone.basicvideochat.R;
import com.logic.android.onetoone.models.PersonalDetails;
import com.logic.android.onetoone.views.homeScreen.MainScreenActivity;

import java.util.Arrays;

import static com.facebook.FacebookSdk.getApplicationContext;

public class LoginFragment extends Fragment {

    TextView resetTextView;
    TextView signUpButton;
    EditText editTextEmail,editTextPassword;
    FirebaseAuth mAuth;
    TextView mFacebookButton;
    TextView mGoogleButton;
    private static final String TAG = LoginFragment.class.getSimpleName();
    private static final int RC_SIGN_IN =1;
    CallbackManager mCallbackManager;
    FirebaseAuth.AuthStateListener authStateListener;

    GoogleApiClient mGoogleSignInClient;
    LinearLayout linearLayout;
    CollectionReference collectionReference;
    FirebaseFirestore firestore;

    public LoginFragment() {
        // Required empty public constructor
    }

    public static LoginFragment newInstance(String param1, String param2) {
        LoginFragment fragment = new LoginFragment();
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
        return inflater.inflate(R.layout.fragment_login, container, false);
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        firestore=FirebaseFirestore.getInstance();

        linearLayout=getActivity().findViewById(R.id.loginlayoutBar);
        linearLayout.setVisibility(View.VISIBLE);

        mAuth=FirebaseAuth.getInstance();

        authStateListener=new FirebaseAuth.AuthStateListener() {
            @Override
            public void onAuthStateChanged(@NonNull FirebaseAuth firebaseAuth) {
                if (firebaseAuth.getCurrentUser()!=null){
                    updateUI(mAuth.getCurrentUser());
                }
            }
        };
        init();



        resetTextView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

               startActivity(new Intent(getActivity(),ResetPasswordActivity.class));
            }
        });


        signUpButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (editTextEmail.getText().toString().equalsIgnoreCase("")){
                    Toast.makeText(getActivity(), "Please Enter Email Id !", Toast.LENGTH_SHORT).show();
                }
                else if (editTextPassword.getText().toString().equalsIgnoreCase("")){
                    Toast.makeText(getActivity(), "Please Enter Password !", Toast.LENGTH_SHORT).show();
                }
                else{
                    signWithEmail(editTextEmail.getText().toString(),editTextPassword.getText().toString());
                }
            }
        });



        // Google SigIN .....
        mGoogleButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                signIn();
            }
        });

        GoogleSignInOptions gso=new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                .requestIdToken("653706892635-bc1vfd0aoe734fbc76mvlqcpaa4orpt3.apps.googleusercontent.com")
                .requestEmail()
                .build();

        mGoogleSignInClient =new GoogleApiClient.Builder(getActivity())
                .enableAutoManage(getActivity(), new GoogleApiClient.OnConnectionFailedListener() {
                    @Override
                    public void onConnectionFailed(@NonNull ConnectionResult connectionResult) {
                        Toast.makeText(getActivity(), "Something went wrong", Toast.LENGTH_SHORT).show();
                    }
                })
                .addApi(Auth.GOOGLE_SIGN_IN_API,gso)
                .build();


        mCallbackManager = CallbackManager.Factory.create();
        // Facebook SigIn .....
        mFacebookButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                FacebookSdk.sdkInitialize(getApplicationContext());
                Log.e(TAG, "onClick: "+"*------------------------------------------------------------" );
                Log.e("AppLog", "key: " + FacebookSdk.getApplicationSignature(getActivity()));

                LoginManager.getInstance().logInWithReadPermissions(getActivity(),
                        Arrays.asList("email","public_profile"));
                LoginManager.getInstance().registerCallback(mCallbackManager
                        , new FacebookCallback<LoginResult>() {
                            @Override
                            public void onSuccess(LoginResult loginResult) {
                                Log.d(TAG, "facebook:onSuccess:" + loginResult);
                                handleFacebookAccessToken(loginResult.getAccessToken());
                            }

                            @Override
                            public void onCancel() {

                            }

                            @Override
                            public void onError(FacebookException error) {
                                Toast.makeText(getActivity(), "Failed To Login !", Toast.LENGTH_SHORT).show();
                                Log.e(TAG,"Facebook "+error);
                            }
                        });
            }
        });
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        // Pass the activity result back to the Facebook SDK
        mCallbackManager.onActivityResult(requestCode, resultCode, data);

        // Result returned from launching the Intent from GoogleSignInApi.getSignInIntent(...);
        if (requestCode == RC_SIGN_IN) {
            GoogleSignInResult result= Auth.GoogleSignInApi.getSignInResultFromIntent(data);
            if(result.isSuccess())
            {
                GoogleSignInAccount account=result.getSignInAccount();
                firebaseAuthWithGoogle(account);
            }
            else
            {
                Toast.makeText(
                        getActivity(), "Auth Went Wrong", Toast.LENGTH_SHORT).show();
                Log.e(TAG,""+result+"  "+result.getSignInAccount());
            }
        }
    }

    private void handleFacebookAccessToken(AccessToken token) {
        Log.d(TAG, "handleFacebookAccessToken:" + token);

        AuthCredential credential = FacebookAuthProvider.getCredential(token.getToken());
        mAuth.signInWithCredential(credential)
                .addOnCompleteListener(getActivity(), new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            mGoogleButton.setEnabled(false);
                            Toast.makeText(getActivity(), "Please Wait !", Toast.LENGTH_SHORT).show();
                            // Sign in success, update UI with the signed-in user's information
                            Log.d(TAG, "signInWithCredential:success");
                            final FirebaseUser user = mAuth.getCurrentUser();
                            PersonalDetails details=new PersonalDetails(user.getDisplayName(),
                                    user.getPhotoUrl().toString(),user.getEmail(),"Facebook");
                            collectionReference=firestore.collection("UserDetails");
                            collectionReference.document(user.getUid())
                                    .set(details, SetOptions.merge())
                                    .addOnCompleteListener(new OnCompleteListener<Void>() {
                                        @Override
                                        public void onComplete(@NonNull Task<Void> task) {

                                            if (task.isSuccessful()){
                                                updateUI(user);
                                                mGoogleButton.setEnabled(true);
                                            }else {
                                                updateUI(user);
                                                mGoogleButton.setEnabled(true);
                                            }
                                        }
                                    });
                            updateUI(user);
                        } else {
                            // If sign in fails, display a message to the user.
                            Log.w(TAG, "signInWithCredential:failure", task.getException());
                            Toast.makeText(getActivity(), " Facebook Authentication failed.",
                                    Toast.LENGTH_SHORT).show();
                            updateUI(null);
                        }

                        // ...
                    }
                });
    }

    private void signIn() {
        Intent signInIntent=Auth.GoogleSignInApi.getSignInIntent(mGoogleSignInClient);
        startActivityForResult(signInIntent, RC_SIGN_IN);
    }


    private void firebaseAuthWithGoogle(GoogleSignInAccount acct) {
        Log.d(TAG, "firebaseAuthWithGoogle:" + acct.getId());

        AuthCredential credential = GoogleAuthProvider.getCredential(acct.getIdToken(), null);
        mAuth.signInWithCredential(credential)
                .addOnCompleteListener(getActivity(), new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            // Sign in success, update UI with the signed-in user's information
                            Log.d(TAG, "signInWithCredential:success");


                            final FirebaseUser user = mAuth.getCurrentUser();
                            PersonalDetails details=new PersonalDetails(user.getDisplayName(),
                                    user.getPhotoUrl().toString(),user.getEmail(),"Google");
                            collectionReference=firestore.collection("UserDetails");
                            collectionReference.document(user.getUid())
                                    .set(details, SetOptions.merge())
                                    .addOnCompleteListener(new OnCompleteListener<Void>() {
                                        @Override
                                        public void onComplete(@NonNull Task<Void> task) {

                                            if (task.isSuccessful()){
                                                updateUI(user);
                                            }else {
                                                updateUI(user);
                                            }
                                        }
                                    });


                        } else {
                            // If sign in fails, display a message to the user.
                            Log.w(TAG, "signInWithCredential:failure", task.getException());
                            updateUI(null);
                        }

                        // ...
                    }
                });
    }



    public void init(){

        signUpButton=getActivity().findViewById(R.id.signInButton);
        mGoogleButton=getActivity().findViewById(R.id.google);
        mFacebookButton=getActivity().findViewById(R.id.facebook);
        editTextEmail=getActivity().findViewById(R.id.email);
        editTextPassword=getActivity().findViewById(R.id.password);
        resetTextView=getActivity().findViewById(R.id.resetTextView);

    }

    public void signWithEmail(String email,String password){

        mAuth.signInWithEmailAndPassword(email,password)
                .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()){
                            startActivity(new Intent(getActivity(), MainScreenActivity.class));
                        }
                        else{
                            Toast.makeText(getActivity(), "Please Try Again !", Toast.LENGTH_SHORT).show();
                        }
                    }
                });

    }
    private void updateUI(FirebaseUser currentUser) {
        if (currentUser!=null){
            startActivity(new Intent(getActivity(), MainScreenActivity.class));
            getActivity().finish();
        }
        else {

        }
    }
    @Override
    public void onPause() {
        super.onPause();
        mGoogleSignInClient.stopAutoManage(getActivity());
        mGoogleSignInClient.disconnect();
    }

}

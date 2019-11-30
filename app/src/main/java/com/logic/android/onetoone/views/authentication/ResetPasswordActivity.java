package com.logic.android.onetoone.views.authentication;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.logic.android.onetoone.basicvideochat.R;

public class ResetPasswordActivity extends AppCompatActivity {

    EditText editText;
    Button resetButton;
    FirebaseAuth firebaseAuth;



    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.fragment_reset_password);

        firebaseAuth=FirebaseAuth.getInstance();
        editText=findViewById(R.id.emailEditText);
        resetButton=findViewById(R.id.resetPasswordButton);
        resetButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (editText.getText().toString().equalsIgnoreCase("")){
                    editText.setError("Enter The Email Id !");
                }
                else {
                    firebaseAuth.sendPasswordResetEmail(editText.getText().toString())
                            .addOnCompleteListener(new OnCompleteListener<Void>() {
                                @Override
                                public void onComplete(@NonNull Task<Void> task) {
                                    if (task.isSuccessful()){
                                        Toast.makeText(ResetPasswordActivity.this, "SuccessFully Send To Email Id!", Toast.LENGTH_SHORT).show();
                                    }
                                    else {
                                        Toast.makeText(ResetPasswordActivity.this, "Please Try Again !", Toast.LENGTH_SHORT).show();
                                    }
                                }
                            });
                }
            }
        });
    }

}

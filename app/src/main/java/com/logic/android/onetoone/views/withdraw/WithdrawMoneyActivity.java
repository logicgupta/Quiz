package com.logic.android.onetoone.views.withdraw;

import android.os.Bundle;
import android.widget.EditText;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.logic.android.onetoone.basicvideochat.R;

public class WithdrawMoneyActivity extends AppCompatActivity {


    Bundle bundle;
    String userWalletBalance;
    TextView balanceTextView;
    EditText editTextAmount;
    TextView creditTextView,paytmTextview,upiTextView,addAccountTextView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_winning);
        init();
        bundle=getIntent().getExtras();
        userWalletBalance=bundle.get("wallet").toString();
        balanceTextView.setText("₹ "+userWalletBalance);

    }
    public void init(){
        balanceTextView=findViewById(R.id.balance);
        editTextAmount=findViewById(R.id.amount);
        creditTextView=findViewById(R.id.credit);
        paytmTextview=findViewById(R.id.paytm);
        upiTextView=findViewById(R.id.upi);
        addAccountTextView=findViewById(R.id.addBankAccount);

    }
}

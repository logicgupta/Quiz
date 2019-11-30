package com.logic.android.onetoone.views;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;
import com.logic.android.onetoone.adapters.CoinAdapter;
import com.logic.android.onetoone.basicvideochat.R;
import com.logic.android.onetoone.models.Coins;
import com.logic.android.onetoone.models.CountryCoin;
import com.razorpay.Checkout;
import com.razorpay.PaymentResultListener;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


public class AddCoinsActivity extends AppCompatActivity implements PaymentResultListener {

    private LinearLayout TotalAmountLayout;
    private Button addCoinsButton , coinsAddDemo;
    private int coinsAmount = 0;
    private TextView totalAmount;
    FirebaseAuth firebaseAuth;
    FirebaseFirestore firestore;
    CollectionReference collectionReference;
    RecyclerView recyclerView;
    List<Coins> coinsList =new ArrayList<>();
    List<CountryCoin> countryCoinList=new ArrayList<>();
    CoinAdapter adapter;
    ProgressBar progressBar;
    LinearLayout linearLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_coins);

        intView();
        Checkout.preload(getApplicationContext());
        firebaseAuth=FirebaseAuth.getInstance();
    }

    private void intView() {

        TotalAmountLayout = findViewById(R.id.TotalAmountLayout);
        addCoinsButton = findViewById(R.id.addCoinsButton);
       // coinsAddDemo = findViewById(R.id.coinsAddDemo);
        totalAmount = findViewById(R.id.totalAmount);
        recyclerView=findViewById(R.id.recyclerView);
        recyclerView.setLayoutManager(new GridLayoutManager(this,3));
        progressBar=findViewById(R.id.progressBar);
        linearLayout=findViewById(R.id.linearLayout);
        linearLayout.setVisibility(View.GONE);


        getCoin();
      /*  coinsAddDemo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                coinsAmount = 50;
                TotalAmountLayout.setVisibility(View.VISIBLE);
                totalAmount.setText("₹ "+String.valueOf(coinsAmount));
            }
        });*/

        TotalAmountLayout.setVisibility(View.GONE);

        addCoinsButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (coinsAmount!=0){
                    startPayment();
                }
                else {
                    Toast.makeText(AddCoinsActivity.this, "Please Select how much coins you want to add", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    public void startPayment() {
        /*
          You need to pass current activity in order to let Razorpay create CheckoutActivity
         */
        final Activity activity = this;

        final Checkout co = new Checkout();

        try {
            JSONObject options = new JSONObject();
            options.put("name", "BeatGranpa");
            options.put("description", "Coins payment");
            //You can omit the image option to fetch the image from dashboard
            options.put("image", "https://s3.amazonaws.com/rzp-mobile/images/rzp.png");
            options.put("currency", "INR");
            options.put("amount", "100");

            JSONObject preFill = new JSONObject();
            preFill.put("email", "test@razorpay.com");
            preFill.put("contact", "7772056756");

            options.put("prefill", preFill);

            co.open(activity, options);
        } catch (Exception e) {
            Toast.makeText(activity, "Error in payment: " + e.getMessage(), Toast.LENGTH_SHORT)
                    .show();
            e.printStackTrace();
        }
    }

    @SuppressWarnings("unused")
    @Override
    public void onPaymentSuccess(String razorpayPaymentID) {
        try {
            //Toast.makeText(this, "Payment Successful: " + razorpayPaymentID, Toast.LENGTH_SHORT).show();
            addCoinsToUserAccount(coinsAmount);
        } catch (Exception e) {
            //Log.e(TAG, "Exception in onPaymentSuccess", e);
        }
    }

    /**
     * The name of the function has to be
     * onPaymentError
     * Wrap your code in try catch, as shown, to ensure that this method runs correctly
     */
    @SuppressWarnings("unused")
    @Override
    public void onPaymentError(int code, String response) {
        try {
            Toast.makeText(this, "Please try again your payment has been failed", Toast.LENGTH_SHORT).show();
        } catch (Exception e) {
            //Log.e(TAG, "Exception in onPaymentError", e);
        }
    }

    public void addCoinsToUserAccount(int Coins){

        firestore= FirebaseFirestore.getInstance();
        collectionReference=firestore.collection("UserDetails");

        Map<String, Object> data = new HashMap<>();
        data.put("coinBalance", Coins);

        collectionReference
                .document(firebaseAuth.getUid()).update("coinBalance" , 100)
                .addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        if (task.isSuccessful()){
                            Toast.makeText(AddCoinsActivity.this, "Transaction is successfull", Toast.LENGTH_SHORT).show();
                            showCoinAddedSuccessfullyPopup(AddCoinsActivity.this);
                        }
                        else{
                            Toast.makeText(AddCoinsActivity.this, "Please try again and contact us in case amount has been debited from your account", Toast.LENGTH_SHORT).show();
                        }

                    }
                });


    }

    public void showCoinAddedSuccessfullyPopup(final Context context) {
        final AlertDialog.Builder alertCancelReminderDialog = new AlertDialog.Builder(context);
        LayoutInflater inflater = LayoutInflater.from(context);
        final View convertView = (View) inflater.inflate(R.layout.custom_add_coin_dialog, null);
        alertCancelReminderDialog.setView(convertView);
        final AlertDialog ad = alertCancelReminderDialog.show();
        ad.setCancelable(false);

        Button addMoreCoins , gotoHomeScreen;

        addMoreCoins = (Button) convertView.findViewById(R.id.addMoreCoins);
        gotoHomeScreen = (Button) convertView.findViewById(R.id.gotoHomeScreen);


        addMoreCoins.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                finish();
            }
        });

        gotoHomeScreen.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {


                ad.dismiss();
                coinsAmount = 0 ;
                TotalAmountLayout.setVisibility(View.GONE);

            }
        });
    }
    public  void getCoin(){

        collectionReference=FirebaseFirestore.getInstance().collection("Admin")
                .document("Coins").collection("Country");


        collectionReference.get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
            @Override
            public void onComplete(@NonNull Task<QuerySnapshot> task) {
                if (task.isSuccessful()){

                    for (QueryDocumentSnapshot snapshots:task.getResult()){

                        CountryCoin countryCoin=new CountryCoin();
                        String country=snapshots.getString("country");
                        countryCoin.setCoins((List<Coins>) snapshots.get("coinsList"));
                        JSONArray jsonArray=new JSONArray((List<Coins>) snapshots.get("coinsList"));
                        countryCoin.setCountry(country);
                        if (country.equalsIgnoreCase("India")){
                            countryCoinList.add(countryCoin);
                            try {

                                for (int i=0;i<jsonArray.length();i++){
                                    JSONObject jsonObject=jsonArray.getJSONObject(i);
                                    Coins coins=new Coins();
                                    coins.setCoin(jsonObject.getInt("coin"));
                                    coinsList.add(coins);
                                }
                            } catch (JSONException e) {
                                e.printStackTrace();
                            }
                        }
                    }
                    if (coinsList.size()>0){
                        progressBar.setVisibility(View.GONE);
                        linearLayout.setVisibility(View.VISIBLE);
                    }
                    else {
                        progressBar.setVisibility(View.GONE);
                        Toast.makeText(AddCoinsActivity.this, "Coins Addition Service not Available !", Toast.LENGTH_SHORT).show();
                    }
                    adapter=new CoinAdapter(recyclerView, countryCoinList, coinsList, AddCoinsActivity.this, new CoinAdapter.OnCoinClickListener() {
                        @Override
                        public void onClick(int coin) {
                            coinsAmount = coin;
                            TotalAmountLayout.setVisibility(View.VISIBLE);
                            totalAmount.setText("₹ "+String.valueOf(coinsAmount));
                        }
                    });
                    recyclerView.setAdapter(adapter);

                }
            }
        });

    }
}

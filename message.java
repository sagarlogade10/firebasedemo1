package com.example.sharman_1.firebasedemo;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class message extends AppCompatActivity implements View.OnClickListener {

    private FirebaseAuth firebaseAuth;
    private TextView textView;
    private Button logout;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_message);

        firebaseAuth=FirebaseAuth.getInstance();
        if (firebaseAuth.getCurrentUser()==null)
        {
            finish();
            startActivity(new Intent(this,MainActivity.class));
        }
        FirebaseUser user=firebaseAuth.getCurrentUser();


        textView=findViewById(R.id.title1);
        textView.setText("Welcome "+user.getEmail());

        logout=findViewById(R.id.logout);

        logout.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {

        if (v==logout)
        {
            firebaseAuth.signOut();
            finish();
            startActivity(new Intent(this,signup.class));
        }

    }
}

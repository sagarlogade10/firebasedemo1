package com.example.sharman_1.firebasedemo;

import android.app.ProgressDialog;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import static android.text.TextUtils.isEmpty;

public class signup extends AppCompatActivity implements View.OnClickListener {
    EditText Email2,Password2,Name1;
    ImageButton register;
    Button login;
    DatabaseHelper db;
    private FirebaseAuth firebaseAuth;
    private ProgressDialog progressDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signup);


        firebaseAuth=FirebaseAuth.getInstance();
        if (firebaseAuth.getCurrentUser()!=null){
            finish();
            startActivity(new Intent(getApplicationContext(),message.class));

        }



        db= new DatabaseHelper(this);
        Name1=findViewById(R.id.name);
        Email2=findViewById(R.id.email);
        Password2=findViewById(R.id.password);
        register=findViewById(R.id.signup);
        login=findViewById(R.id.signin);


        login.setOnClickListener(this);
        register.setOnClickListener(this);



        register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String name=Name1.getText().toString().trim();
                String email=Email2.getText().toString().trim();
                String pass=Password2.getText().toString().trim();
                if(name.equals(""))
                    Name1.setError("Please Enter Name");
                if(email.equals(""))
                    Email2.setError("Enter Email");
                if(pass.equals(""))
                    Password2.setError("Enter Password");
                Boolean error=false;
                if(!isvalidemail(email))
                {
                    error = true;
                    Email2.setError("Invalid Email Address");
                    return;

                }
                else
                {
                    long val = db.addUser(name, email, pass);
                    if(val > 0)
                    {
                        Toast.makeText(signup.this, "Registration successfully", Toast.LENGTH_SHORT).show();
                        Intent i=new Intent(signup.this, MainActivity.class);
                        startActivity(i);
                    }
                    else
                        Toast.makeText(getApplicationContext(),"Error", Toast.LENGTH_SHORT).show();
                    Intent i=new Intent(signup.this,signup.class);
                }


            }
        });
        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i=new Intent(signup.this, MainActivity.class);
                startActivity(i);
            }
        });






    }

    private void userLogin(){
        String Name=Name1.getText().toString().trim();
        String email=Email2.getText().toString().trim();
        String password=Password2.getText().toString().trim();


        if (isEmpty(Name))
        {
            //email is empty
            Toast.makeText(this,"please enter Name",Toast.LENGTH_SHORT).show();
            return;
        }
        if (isEmpty(email)){

            //password is empty
            Toast.makeText(this,"Please Enter Email",Toast.LENGTH_SHORT).show();
            return;
        }

        if (isEmpty(password)){

            //password is empty
            Toast.makeText(this,"Please Enter password",Toast.LENGTH_SHORT).show();
            return;
        }
        //if validated
        progressDialog.setMessage("Login Successfully");
        progressDialog.show();

        firebaseAuth.signInWithEmailAndPassword(email,password)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        progressDialog.dismiss();
                        if (task.isSuccessful()){
                            finish();
                            startActivity(new Intent(getApplicationContext(),MainActivity.class));

                        }
                    }
                });

    }

    @Override
    public void onClick(View v) {
        if (v==register)
        {
            userLogin();
        }

        if (v==login)
        {
            finish();
            startActivity(new Intent(this,MainActivity.class));
        }

    }


    private boolean isvalidemail(String email) {
        String EMAIL_PATTERN = "^[_A-Za-z0-9-\\+]+(\\.[_A-Za-z0-9-]+)*@"
                + "[A-Za-z0-9-]+(\\.[A-Za-z0-9]+)*(\\.[A-Za-z]{2,})$";

        Pattern pattern = Pattern.compile(EMAIL_PATTERN);
        Matcher matcher = pattern.matcher(email);
        return matcher.matches();



    }
}

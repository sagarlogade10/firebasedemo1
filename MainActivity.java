package com.example.sharman_1.firebasedemo;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.provider.ContactsContract;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

import static android.text.TextUtils.*;

public class MainActivity extends AppCompatActivity implements View.OnClickListener{
    EditText Email1,Password1;
    CheckBox checkBox;
    ImageButton imageButton1;
    Button register;
    DatabaseHelper db;
    SharedPreferences sp1;
    SharedPreferences.Editor editor;
    private ProgressDialog progressDialog;
    private FirebaseAuth firebaseAuth;




    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        db=new DatabaseHelper(this);
        Email1=findViewById(R.id.email);
        Password1=findViewById(R.id.password);
        sp1=getSharedPreferences("Sagar",Context.MODE_PRIVATE);
        editor=sp1.edit();
        checkBox=findViewById(R.id.checkbox);
        imageButton1=findViewById(R.id.signin);
        register=findViewById(R.id.signup);





        imageButton1.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {


                String Em=Email1.getText().toString().trim();
                String Pass= Password1.getText().toString().trim();
                Boolean res=db.checkUser(Em, Pass);

                if(Em.equals("")||Pass.equals("")) {
                    if (Em.equals(""))
                        Email1.setError("please fill Email");
                    if (Pass.equals(""))
                        Password1.setError("Please fill Password");
                }
                else
                {

                    if (res==true)
                    {
                        Toast.makeText(getApplicationContext(),"Log in successfully",Toast.LENGTH_SHORT).show();
                        Intent i=new Intent(MainActivity.this,message.class);
                        if(checkBox.isChecked()){
                            editor.putString("myemail",Em);
                            editor.putString("mypassword", Pass);
                            editor.commit();

                        }
                        else{
                            Toast.makeText(getApplicationContext(),"Log in successfully",Toast.LENGTH_SHORT).show();
                            Intent login= new Intent(MainActivity.this,message.class);
                            login.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                            login.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                            startActivity(login);
                        }


                    }
                    else
                    {
                        Toast.makeText(getApplicationContext(),"Login Error",Toast.LENGTH_SHORT).show();

                    }
                }
            }




        });
        if(sp1.contains("myemail"))
            Email1.setText(sp1.getString("myemail",""));
        if(sp1.contains("mypassword"))
            Password1.setText(sp1.getString("mypassword",""));
        register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i= new Intent(MainActivity.this, signup.class);
                startActivity(i);
            }
        });


        firebaseAuth=FirebaseAuth.getInstance();

        if (firebaseAuth.getCurrentUser()!=null)
        {
            finish();
            startActivity(new Intent(getApplicationContext(),message.class));

        }

        progressDialog=new ProgressDialog(this);

        imageButton1.setOnClickListener(this);
        register.setOnClickListener(this);






    }

    private void registerUser(){
        String email=Email1.getText().toString().trim();
        String password1=Password1.getText().toString().trim();

        if (isEmpty(email))
        {
            //email is empty
            Toast.makeText(this,"please enter Email",Toast.LENGTH_SHORT).show();
            return;
        }
        if (isEmpty(password1)){

            //password is empty
        Toast.makeText(this,"Please Enter Password",Toast.LENGTH_SHORT).show();
        return;
        }

        //if validated
        progressDialog.setMessage("Please Wait....");
        progressDialog.show();

        firebaseAuth.createUserWithEmailAndPassword(email,password1)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        progressDialog.dismiss();
                        if (task.isSuccessful())
                        {
                            finish();
                            startActivity(new Intent(getApplicationContext(),message.class));


                        }
                        else {
                            Toast.makeText(MainActivity.this,"Registered Not Successfully",Toast.LENGTH_SHORT).show();

                        }
                    }
                });

    }

    @Override
    public void onClick(View v) {
        if (v==imageButton1){
            registerUser();

        }
        if (v==register)
        {
            startActivity(new Intent(this,signup.class));
        }
    }
}

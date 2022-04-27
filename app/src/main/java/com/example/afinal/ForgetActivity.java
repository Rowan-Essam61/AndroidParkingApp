package com.example.afinal;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class ForgetActivity extends AppCompatActivity {

    private FirebaseAuth mFirebaseAuth;
    private FirebaseAuth.AuthStateListener mAuthStateListener;
    EditText email;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_forget);
        mFirebaseAuth = FirebaseAuth.getInstance();
        email=(EditText) findViewById(R.id.email);
    //        mAuthStateListener = new FirebaseAuth.AuthStateListener() {
//            @Override
//            public void onAuthStateChanged(@NonNull FirebaseAuth firebaseAuth) {
//                FirebaseUser firebaseUser = firebaseAuth.getCurrentUser();
//                if (firebaseUser != null) {
//                    // Do whatever you want with the UserId by firebaseUser.getUid()
//                } else {
//
//                }
//            }
//        };

    }

    public void forget(View V){

        mFirebaseAuth.sendPasswordResetEmail(email.getText().toString())
            .addOnCompleteListener(new OnCompleteListener<Void>() {
        @Override
        public void onComplete(@NonNull Task<Void> task) {

            if (task.isSuccessful()) {
                Toast.makeText(ForgetActivity.this, "An email has been sent to you.", Toast.LENGTH_SHORT).show();
                finish();
            } else {
                Toast.makeText(ForgetActivity.this, task.getException().getMessage(), Toast.LENGTH_SHORT).show();
            }
        }
    });}
    public void signin(View v){
        Intent z = new Intent(getApplicationContext(), SigninActivity.class);
        startActivity(z);
    }

}
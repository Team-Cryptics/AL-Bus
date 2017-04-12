package com.example.punyaaachman.albus;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class LoginActivity extends AppCompatActivity
{
    private FirebaseAuth mAuth;
    private FirebaseAuth.AuthStateListener mAuthListener;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        mAuth = FirebaseAuth.getInstance();

        ((TextView) findViewById(R.id.tvSignup)).setOnClickListener(new View.OnClickListener()
        {
            @Override public void onClick(View view)
            {
                startActivity(new Intent(LoginActivity.this, RegisterActivity.class));
            }
        });

        mAuthListener = new FirebaseAuth.AuthStateListener() {
            @Override
            public void onAuthStateChanged(@NonNull FirebaseAuth firebaseAuth) {
                FirebaseUser user = firebaseAuth.getCurrentUser();
                if (user != null) {
                    startActivity(new Intent(LoginActivity.this, HomeActivity.class));
                    finish();
                } else {
                    ((Button) findViewById(R.id.btnLogin)).setVisibility(View.VISIBLE);
                    ((ProgressBar) findViewById(R.id.pbLogin)).setVisibility(View.GONE);

                    (findViewById(R.id.btnLogin)).setOnClickListener(new View.OnClickListener()
                    {
                        @Override public void onClick(View view)
                        {
                            if (((EditText) findViewById(R.id.etEmail)).getText().toString().isEmpty() || ((EditText) findViewById(R.id.etPassword)).getText().toString().isEmpty()) {
                                Toast.makeText(LoginActivity.this, "Invalid Credentials", Toast.LENGTH_SHORT).show();
                            } else {
                                ((Button) findViewById(R.id.btnLogin)).setVisibility(View.GONE);
                                ((ProgressBar) findViewById(R.id.pbLogin)).setVisibility(View.VISIBLE);

                                mAuth.signInWithEmailAndPassword(((EditText) findViewById(R.id.etEmail)).getText().toString(),((EditText) findViewById(R.id.etEmail)).getText().toString())
                                        .addOnCompleteListener(LoginActivity.this, new OnCompleteListener<AuthResult>() {
                                            @Override
                                            public void onComplete(@NonNull Task<AuthResult> task) {
                                                if (!task.isSuccessful()) {
                                                    Toast.makeText(LoginActivity.this, R.string.auth_failed, Toast.LENGTH_SHORT).show();
                                                    ((Button) findViewById(R.id.btnLogin)).setVisibility(View.VISIBLE);
                                                    ((ProgressBar) findViewById(R.id.pbLogin)).setVisibility(View.GONE);
                                                } else {
                                                    startActivity(new Intent(LoginActivity.this, HomeActivity.class));
                                                    finish();
                                                }
                                            }
                                        });
                            }
                        }
                    });
                }
            }
        };
    }
    @Override
    public void onStart() {
        super.onStart();
        mAuth.addAuthStateListener(mAuthListener);
    }
    @Override
    public void onStop() {
        super.onStop();
        if (mAuthListener != null) {
            mAuth.removeAuthStateListener(mAuthListener);
        }
    }
}

package com.example.punyaaachman.albus;

import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

public class RegisterActivity extends AppCompatActivity
{
    private FirebaseAuth mAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        mAuth = FirebaseAuth.getInstance();

        (findViewById(R.id.btnRegister)).setOnClickListener(new View.OnClickListener()
        {
            @Override public void onClick(View view)
            {
                if (((EditText) findViewById(R.id.etName)).getText().toString().isEmpty()||((EditText) findViewById(R.id.etEmail)).getText().toString().isEmpty()||((EditText) findViewById(R.id.etNumber)).getText().toString().isEmpty()||((EditText) findViewById(R.id.etName)).getText().toString().isEmpty()) {
                    Toast.makeText(RegisterActivity.this, "Invalid Credentials", Toast.LENGTH_SHORT).show();
                } else {
                    (findViewById(R.id.btnRegister)).setVisibility(View.GONE);
                    (findViewById(R.id.pbRegister)).setVisibility(View.VISIBLE);

                    mAuth.createUserWithEmailAndPassword(((EditText) findViewById(R.id.etEmail)).getText().toString(), ((EditText) findViewById(R.id.etPassword)).getText().toString())
                            .addOnCompleteListener(RegisterActivity.this, new OnCompleteListener<AuthResult>() {
                                @Override
                                public void onComplete(@NonNull Task<AuthResult> task) {
                                    if (!task.isSuccessful()) {
                                        Toast.makeText(RegisterActivity.this, R.string.auth_failed, Toast.LENGTH_SHORT).show();
                                        (findViewById(R.id.btnRegister)).setVisibility(View.VISIBLE);
                                        (findViewById(R.id.pbRegister)).setVisibility(View.GONE);
                                    } else {
                                        Toast.makeText(RegisterActivity.this, "Registration Successfull!", Toast.LENGTH_SHORT).show();
                                        finish();
                                    }
                                }
                            });
                }
            }
        });
    }
}

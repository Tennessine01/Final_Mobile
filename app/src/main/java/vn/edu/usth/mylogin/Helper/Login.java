package vn.edu.usth.mylogin.Helper;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import vn.edu.usth.mylogin.R;

public class Login extends AppCompatActivity {
    TextView emailLoginText, passwordLoginText;
    FirebaseAuth mAuth;
    Button buttonLogin;

    @Override
    public void onStart() {
        super.onStart();
        FirebaseUser currentUser = mAuth.getCurrentUser();
        if(currentUser != null){
            Intent intent = new Intent(getApplicationContext(), MainActivity.class);
            startActivity(intent);
            finish();
        }
    }
    View newRegister;
    View forgotPassword;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        mAuth = FirebaseAuth.getInstance();
        emailLoginText = findViewById(R.id.email_address);
        passwordLoginText = findViewById(R.id.password);
        buttonLogin = findViewById(R.id.login);
        newRegister = findViewById(R.id.register);
        newRegister.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view){
                Intent intent = new Intent(Login.this, Register.class);
                startActivity(intent);
                finish();
            }
        });

        forgotPassword = findViewById(R.id.forgot_password);
        forgotPassword.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(Login.this, ResetPasswordActivity.class);
                startActivity(intent);
                finish();
            }
        });



        buttonLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String email, password, con_password;
                CheckBox check;
                email = emailLoginText.getText().toString();
                password = passwordLoginText.getText().toString();

                if (TextUtils.isEmpty(email)) {
                    Toast.makeText(Login.this, "Enter email", Toast.LENGTH_SHORT).show();
                    return;
                }
                if (TextUtils.isEmpty(password)) {
                    Toast.makeText(Login.this, "Enter password", Toast.LENGTH_SHORT).show();
                    return;
                }
                mAuth.signInWithEmailAndPassword(email, password)
                        .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                            @Override
                            public void onComplete(@NonNull Task<AuthResult> task) {
                                if (task.isSuccessful()) {
                                    Toast.makeText(getApplicationContext(), "Login Successfully.",
                                            Toast.LENGTH_SHORT).show();
                                    Intent intent = new Intent(getApplicationContext(), MainActivity.class);
                                    startActivity(intent);
                                    finish();
                                } else {
                                    Toast.makeText(Login.this, "Authentication failed.",
                                            Toast.LENGTH_SHORT).show();
                                }
                            }
                        });

            }
        });

    }
}
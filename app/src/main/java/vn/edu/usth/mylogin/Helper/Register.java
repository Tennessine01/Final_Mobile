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
import androidx.core.view.GravityCompat;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

import vn.edu.usth.mylogin.R;

public class Register extends AppCompatActivity {

    CheckBox checkbox;
    TextView emailRegisText, passwordRegisText, passwordConfirmRegisText;
    FirebaseAuth mAuth;
    Button buttonReg;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        mAuth = FirebaseAuth.getInstance();
        emailRegisText = findViewById(R.id.Register_Email);
        passwordRegisText = findViewById(R.id.Register_Password);
        passwordConfirmRegisText = findViewById(R.id.Register_Confirm_Password);
        checkbox = findViewById(R.id.Register_Agreement);
        buttonReg = findViewById(R.id.Register_Button);
        buttonReg.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view) {
                String email, password, con_password;
                CheckBox check;
                email = emailRegisText.getText().toString();
                password = passwordRegisText.getText().toString();
                con_password = passwordConfirmRegisText.getText().toString();
                check = checkbox;

                if (TextUtils.isEmpty(email)) {
                    Toast.makeText(Register.this, "Enter email", Toast.LENGTH_SHORT).show();
                    return;
                }
                if (TextUtils.isEmpty(password)) {
                    Toast.makeText(Register.this, "Enter password", Toast.LENGTH_SHORT).show();
                    return;
                }
                if (TextUtils.isEmpty(con_password) || (con_password.equals(password) == false)) {
                    Toast.makeText(Register.this, "Re-enter your password", Toast.LENGTH_SHORT).show();
                    return;
                }
                if (check.isChecked() == false) {
                    Toast.makeText(Register.this, "Agree with our term", Toast.LENGTH_SHORT).show();
                    return;
                }


                mAuth.createUserWithEmailAndPassword(email, password)
                        .addOnCompleteListener(Register.this ,new OnCompleteListener<AuthResult>() {
                            @Override
                            public void onComplete(@NonNull Task<AuthResult> task) {
                                if (task.isSuccessful()) {
                                    Toast.makeText(Register.this, "Account created.",
                                            Toast.LENGTH_SHORT).show();
                                    Intent intent = new Intent(getApplicationContext(), Login.class);
                                    startActivity(intent);
                                    finish();
                                } else {
                                    Toast.makeText(Register.this, "Authentication failed.",
                                            Toast.LENGTH_SHORT).show();
                                }
                            }
                        });

            }
        });
    }
    public void onBackPressed(){
        super.onBackPressed();
        Intent intent = new Intent(getApplicationContext(), Login.class);
        startActivity(intent);
        finish();
    }
}
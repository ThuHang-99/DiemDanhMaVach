package com.example.btl.activity;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.example.btl.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.textfield.TextInputEditText;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class RegisterAcitvity extends AppCompatActivity {
    DatabaseReference mData;
    TextInputEditText email;
    TextInputEditText password;
    TextInputEditText repassword;
    Button register;
    protected FirebaseAuth mFirebaseAuth;
    FirebaseUser user;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
//  firebase
        mData = FirebaseDatabase.getInstance().getReference("user");
        mFirebaseAuth = FirebaseAuth.getInstance();
        user = FirebaseAuth.getInstance().getCurrentUser();

        email = (TextInputEditText)findViewById(R.id.registerUserName);
        password = (TextInputEditText)findViewById(R.id.registerPassword);
        repassword = (TextInputEditText)findViewById(R.id.registerRePassword);
        register = (Button)findViewById(R.id.register);
    }

    public void register(View view)
    {
       if (email.getText().toString().equals("") && email.getText().length() <= 0) {
            Toast.makeText(getApplicationContext(), "Vui lòng nhập tên tài khoản!", Toast.LENGTH_SHORT).show();
            return;
        }

        if (password.getText().length() <= 0) {
            Toast.makeText(getApplicationContext(), "Vui lòng nhập mật khẩu!", Toast.LENGTH_SHORT).show();
            return;
        }

        if (password.getText().length() < 6) {
            Toast.makeText(getApplicationContext(), "Mật khẩu quá ngắn!", Toast.LENGTH_SHORT).show();
            return;
        }
        if(!(password.getText().toString()).equals(repassword.getText().toString()))
        {
            Toast.makeText(this, "Mật khẩu nhập nhập lại không khớp", Toast.LENGTH_SHORT).show();
            return;
        }
        // đắng ký
        mFirebaseAuth.createUserWithEmailAndPassword(email.getText().toString(), password.getText().toString())
                .addOnCompleteListener(RegisterAcitvity.this, new OnCompleteListener<AuthResult>()
                {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                         if (!task.isSuccessful()) {
                                Toast.makeText(RegisterAcitvity.this, "Có lỗi xảy ra vui lòng thử lại." + task.getException(),
                                    Toast.LENGTH_SHORT).show();
                        } else {
                             String uid = user.getUid();
                             mData.child(uid).setValue(email.getText().toString());
                             startActivity(new Intent(RegisterAcitvity.this, LoginActivity.class));
                            finish();
                        }
                    }
               });
    }

    public void goToLogin(View view)
    {
        startActivity(new Intent(RegisterAcitvity.this, LoginActivity.class));
        finish();
    }


}

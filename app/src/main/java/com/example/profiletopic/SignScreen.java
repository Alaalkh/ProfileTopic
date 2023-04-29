package com.example.profiletopic;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.app.ProgressDialog;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.profiletopic.models.login;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.snackbar.Snackbar;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

public class SignScreen extends AppCompatActivity {
    FirebaseAuth firebaseAuth;
    Uri imageuri;
    ImageView imageView;
    ImageView pickimage;
    
    FirebaseFirestore firebaseFirestore;
    ProgressDialog progressDialog;
    Button Sign_btn;
    EditText emailaddress;
    StorageReference storageReference;

    EditText pass;
    EditText fullname;
    EditText address;
    EditText phonenumber;


    @SuppressLint({"MissingInflatedId", "WrongViewCast"})
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_screen);
        emailaddress = findViewById(R.id.email2);
        pass = findViewById(R.id.password2);
        fullname = findViewById(R.id.fname);
        imageView=findViewById(R.id.profileimage);
        address = findViewById(R.id.address);
        phonenumber = findViewById(R.id.number);
        pickimage=findViewById(R.id.pickimage);
        firebaseAuth = FirebaseAuth.getInstance();
        firebaseFirestore = FirebaseFirestore.getInstance();
        progressDialog = new ProgressDialog(this);
       Sign_btn=findViewById(R.id.sign_btn);
     Sign_btn.setOnClickListener(new View.OnClickListener() {
         @Override
         public void onClick(View view) {
             createUser();
         }
     });
    
     pickimage.setOnClickListener(new View.OnClickListener() {
         @Override
         public void onClick(View view) {
             ChooseImage();
         }
     });

    }
    public void uploadimage(){
        storageReference= FirebaseStorage.getInstance().getReference("images/");
        storageReference.putFile(imageuri).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
            @Override
            public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                imageView.setImageURI(null);

                storageReference.getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
                    @Override
                    public void onSuccess(Uri uri) {
                        String email = emailaddress.getText().toString();
                        String password = pass.getText().toString();
                        String full = fullname.getText().toString();
                        String phone = phonenumber.getText().toString();
                        String add = address.getText().toString();

                        firebaseFirestore.collection("Login").document(FirebaseAuth.getInstance().getUid()).set(
                                new login(email, password, full, add, phone,uri.toString())
                        );
                    }
                });

                Toast.makeText(SignScreen.this, "uploaded", Toast.LENGTH_SHORT).show();
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                Toast.makeText(SignScreen.this, "failed", Toast.LENGTH_SHORT).show();

            }
        });
    }
    private void ChooseImage() {
        Intent intent=new Intent();
        intent.setType("image/*");
        intent.setAction(Intent.ACTION_GET_CONTENT);
        startActivityForResult(intent,100);
    }
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 100 && data != null && data.getData() != null) {
            imageuri = data.getData();
            imageView.setImageURI(imageuri);

        }
    }

        private void createUser () {
            String email = emailaddress.getText().toString();
            String password = pass.getText().toString();


            if (TextUtils.isEmpty(email)) {
                emailaddress.setError("Email cannot be empty");
                emailaddress.requestFocus();
            } else if (TextUtils.isEmpty(password)) {
                pass.setError("Password cannot be empty");
                pass.requestFocus();
            } else {
                firebaseAuth.createUserWithEmailAndPassword(email, password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            Toast.makeText(SignScreen.this, "User registered successfully", Toast.LENGTH_SHORT).show();
                            startActivity(new Intent(SignScreen.this, ShowScreen.class));
                           uploadimage();
                            progressDialog.cancel();

                        } else {
                            Toast.makeText(SignScreen.this, "Registration Error: " + task.getException().getMessage(), Toast.LENGTH_SHORT).show();
                            progressDialog.cancel();
                        }
                    }
                });
            }
        }


    }



package com.example.profiletopic;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.messaging.FirebaseMessaging;
import com.squareup.picasso.Picasso;

public class ShowScreen extends AppCompatActivity {
    TextView showname;
    TextView    showpass;

    ;private   FirebaseUser firebaseAuth;
    private   FirebaseAuth Auth;
    TextView   showadd;
    ImageView imageView2;

   TextView showphone;
    TextView   getemail;
    String uid;
   Button edit;
   Button logout;
EditText updateemail;
    EditText updatepass;
    EditText updatefullname;
    EditText updateaddress;
    EditText updatephone;

    FirebaseFirestore db = FirebaseFirestore.getInstance();

    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_show_screen);
        imageView2=findViewById(R.id.imageV);
        showpass=findViewById(R.id.pass2);
        showname=findViewById(R.id.fullname2);
        Auth=FirebaseAuth.getInstance();
         logout=findViewById(R.id.logout);
        showadd=findViewById(R.id.address2);
        showphone=findViewById(R.id.phone2);
        firebaseAuth = FirebaseAuth.getInstance().getCurrentUser();
        getemail=findViewById(R.id.email2);
         edit=findViewById(R.id.editinfo);
        Auth=FirebaseAuth.getInstance();
        uid=firebaseAuth.getUid().toString();
        FirebaseMessaging.getInstance().subscribeToTopic("alaa")
                .addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        Log.e("a","Done");
                    }
                })     .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Log.e("a","Failed");

                    }
                })  ;
        getProfile();
        FirebaseMessaging.getInstance().subscribeToTopic("alaa")
                .addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        Log.e("a","Done");
                    }
                })     .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Log.e("a","Failed");

                    }
                })  ;
        edit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                  updateProfile();
            }
        });

        logout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Auth.signOut();
                startActivity(new Intent(ShowScreen.this, MainActivity.class));

            }
        });
    }
    public  void getProfile(){

        db.collection("Login").document(uid.toString()).get().addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>() {
            @Override
            public void onSuccess(DocumentSnapshot documentSnapshot) {
                if (documentSnapshot.exists()){
                    String name=documentSnapshot.getString("fullname");
                    String email=documentSnapshot.getString("email");
                    String address=documentSnapshot.getString("address");
                    String image=documentSnapshot.getString("image");
                    String password=documentSnapshot.getString("password");
                    String phonenumber=documentSnapshot.getString("phonnumer");
//
                    showname.setText(name);
                    showadd.setText(address);
                    showphone.setText(phonenumber);
                    getemail.setText(email);
                    showpass.setText(password);

              Picasso.with(ShowScreen.this).load(image).into(imageView2);
                }





            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                Toast.makeText(ShowScreen.this, "failed!!!!!!!!!!!!!", Toast.LENGTH_SHORT).show();
            }
        });

    }

public void updateProfile() {
    final AlertDialog.Builder builder = new AlertDialog.Builder(this);
    builder.setTitle("Update Profile ");
    final View customLayout = getLayoutInflater().inflate(R.layout.activity_update_screen, null);
    builder.setView(customLayout);
    builder.setPositiveButton(
            "Update",
            new DialogInterface.OnClickListener() {
                public void onClick(DialogInterface dialog, int id) {
                    updateemail = customLayout.findViewById(R.id.updatemail);
                    updatepass = customLayout.findViewById(R.id.updatepass);
                    updatefullname = customLayout.findViewById(R.id.updatefullname);
                    updateaddress = customLayout.findViewById(R.id.updateaddrss);
                    updatephone = customLayout.findViewById(R.id.updatephone);

                    db.collection("Login").document(uid.toString()).
                            update("email", updateemail.getText().toString(),
                                    "password",updatepass.getText().toString(),
                                    "fullname",updatefullname.getText().toString(),"address",updateaddress.getText().toString()
                            ,"phonnumer",updatephone.getText().toString())
                            .addOnSuccessListener(new OnSuccessListener<Void>() {
                                @Override
                                public void onSuccess(Void aVoid) {
                                    Log.d("dareen", "DocumentSnapshot successfully updated!");
                                    Toast.makeText(ShowScreen.this, "Updated Successfully", Toast.LENGTH_SHORT).show();
                                }
                            })
                            .addOnFailureListener(new OnFailureListener() {
                                @Override
                                public void onFailure(@NonNull Exception e) {
                                    Log.w("dareen", "Error updating document", e);
                                }
                            });
                }
            }).setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
        @Override
        public void onClick(DialogInterface dialogInterface, int i) {

        }
    });
    AlertDialog dialog = builder.create();
    dialog.show();
}


}
package com.example.myapplication.candidates;

import static android.Manifest.permission.READ_EXTERNAL_STORAGE;
import static android.Manifest.permission.WRITE_EXTERNAL_STORAGE;

import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import com.example.myapplication.R;
import com.google.android.material.textfield.TextInputEditText;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.io.IOException;
import java.util.HashMap;

public class candidateDetails extends AppCompatActivity {

    TextInputEditText ca_name,ca_posi,ca_email;
    TextView oath;
    Button sub;
    private final int PICK_IMAGE_REQUEST = 22;
    private static final int PERMISSION_REQUEST_CODE = 200;
    private Uri filePath;

    Bitmap bmp, scaledbmp, bitmap;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_candidate_details);

        ca_name=findViewById(R.id.inp4);
        ca_posi=findViewById(R.id.inp5);
        ca_email=findViewById(R.id.inp6);
        sub=findViewById(R.id.button13);
        oath=findViewById(R.id.textView20);

        if (checkPermission()) {
            Toast.makeText(this, "Permission Granted", Toast.LENGTH_SHORT).show();
        } else {
            requestPermission();
        }
    }

    public void connect(View v){
        String eename=MyAdapter2.data.getEname();
//        String uniq=sa_validate.sa_va.getFuid();
        //String uniq="a76c9e3a-c5ba-4db9";
        String email=ca_email.getText().toString();
        String name=ca_name.getText().toString();
        String posi=ca_posi.getText().toString();
//        String password=du.getText().toString().trim();
//        password=sha256(password);
        //dataHolder obj=new dataHolder(name,organization,password,email,uniq,1);
        String emailtrim = email;
        int index = emailtrim.indexOf('@');
        emailtrim = emailtrim.substring(0,index);


        FirebaseDatabase db= FirebaseDatabase.getInstance("https://votezytesting-default-rtdb.asia-southeast1.firebasedatabase.app/");
        DatabaseReference node=db.getReference("Christ").child("Voters");
       // Query applesQuery = db.getReference("Christ").child("Voters").orderByChild("eMail").equalTo(email);
        if(!email.isEmpty()||!name.isEmpty()||!posi.isEmpty()){
            //    Toast.makeText(sa_register.this, "func", Toast.LENGTH_SHORT).show();
            node=db.getReference("Christ").child("Elections").child(eename).child("candidates").child(emailtrim);
            HashMap<String, String> usermap = new HashMap<>();

            usermap.put("Name",name);
            usermap.put("Position",posi);
            usermap.put("Votes","0");

            node.setValue(usermap);

            Toast.makeText(candidateDetails.this, "Success", Toast.LENGTH_SHORT).show();

        }
        else{
            Toast.makeText(candidateDetails.this, "All the fields are required", Toast.LENGTH_SHORT).show();

        }


        //******************************************image upload***************
//        if (filePath != null) {
//           // String folder= sa_validate.sa_va.getFuid();
//            String folder="1";
//
//            String fname="Candidates/"+folder+"/";
//            // Code for showing progressDialog while uploading
//            ProgressDialog progressDialog
//                    = new ProgressDialog(this);
//            progressDialog.setTitle("Uploading...");
//            progressDialog.show();
//
//            // Defining the child of storageReference
//
//            // StorageReference ref = storageReference.child(fname + UUID.randomUUID().toString());
//            StorageReference ref = FirebaseStorage.getInstance("gs://votezy-sample-2e9fa.appspot.com/").getReference().child(fname+ "id-card");
//
//            // adding listeners on upload
//            // or failure of image
//            ref.putFile(filePath)
//                    .addOnSuccessListener(
//                            new OnSuccessListener<UploadTask.TaskSnapshot>() {
//
//                                @Override
//                                public void onSuccess(
//                                        UploadTask.TaskSnapshot taskSnapshot)
//                                {
//
//                                    // Image uploaded successfully
//                                    // Dismiss dialog
//                                    progressDialog.dismiss();
//                                    Toast
//                                            .makeText(candidateDetails.this,
//                                                    "Image Uploaded!!",
//                                                    Toast.LENGTH_SHORT)
//                                            .show();
//
//                                }
//                            })
//
//                    .addOnFailureListener(new OnFailureListener() {
//                        @Override
//                        public void onFailure(@NonNull Exception e)
//                        {
//
//                            // Error, Image not uploaded
//                            progressDialog.dismiss();
//                            Toast
//                                    .makeText(candidateDetails.this,
//                                            "Failed " + e.getMessage(),
//                                            Toast.LENGTH_SHORT)
//                                    .show();
//                        }
//                    })
//                    .addOnProgressListener(new OnProgressListener<UploadTask.TaskSnapshot>() {
//
//                        // Progress Listener for loading
//                        // percentage on the dialog box
//                        @Override
//                        public void onProgress(
//                                UploadTask.TaskSnapshot taskSnapshot)
//                        {
//                            double progress = (100.0
//                                    * taskSnapshot.getBytesTransferred()
//                                    / taskSnapshot.getTotalByteCount());
//                            progressDialog.setMessage(
//                                    "Uploaded "
//                                            + (int)progress + "%");
//                            //button.setVisibility(View.VISIBLE);
//                        }
//                    });
//        }

//        FirebaseDatabase database = FirebaseDatabase.getInstance();
//        DatabaseReference myRef = database.getReference("user");
//
//        myRef.setValue("Hello");
    }
    public void SelectImage(View v)
    {

        // Defining Implicit Intent to mobile gallery
        Intent intent = new Intent();
        intent.setType("image/*");
        intent.setAction(Intent.ACTION_GET_CONTENT);
        startActivityForResult(
                Intent.createChooser(
                        intent,
                        "Select Image from here..."),
                PICK_IMAGE_REQUEST);
    }
    @Override
    protected void onActivityResult(int requestCode,
                                    int resultCode,
                                    Intent data)
    {

        super.onActivityResult(requestCode,
                resultCode,
                data);

        // checking request code and result code
        // if request code is PICK_IMAGE_REQUEST and
        // resultCode is RESULT_OK
        // then set image in the image view
        if (requestCode == PICK_IMAGE_REQUEST
                && resultCode == RESULT_OK
                && data != null
                && data.getData() != null) {

            // Get the Uri of data
            filePath = data.getData();
            try {

                // Setting image on image view using Bitmap
                bitmap = MediaStore
                        .Images
                        .Media
                        .getBitmap(
                                getContentResolver(),
                                filePath);
                oath.setText(filePath.toString());
            }

            catch (IOException e) {
                // Log the exception
                e.printStackTrace();
            }
        }
    }


    //****************************************************************

    private boolean checkPermission() {
        // checking of permissions.
        int permission1 = ContextCompat.checkSelfPermission(getApplicationContext(), WRITE_EXTERNAL_STORAGE);
        int permission2 = ContextCompat.checkSelfPermission(getApplicationContext(), READ_EXTERNAL_STORAGE);
        return permission1 == PackageManager.PERMISSION_GRANTED && permission2 == PackageManager.PERMISSION_GRANTED;
    }

    private void requestPermission() {
        // requesting permissions if not provided.
        ActivityCompat.requestPermissions(this, new String[]{WRITE_EXTERNAL_STORAGE, READ_EXTERNAL_STORAGE}, PERMISSION_REQUEST_CODE);
    }

    @Override
    public void onRequestPermissionsResult (int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (requestCode == PERMISSION_REQUEST_CODE) {
            if (grantResults.length > 0) {

                // after requesting permissions we are showing
                // users a toast message of permission granted.
                boolean writeStorage = grantResults[0] == PackageManager.PERMISSION_GRANTED;
                boolean readStorage = grantResults[1] == PackageManager.PERMISSION_GRANTED;

                if (writeStorage && readStorage) {
                    Toast.makeText(this, "Permission Granted..", Toast.LENGTH_SHORT).show();
                } else {
                    Toast.makeText(this, "Permission Denined.", Toast.LENGTH_SHORT).show();
                    finish();
                }
            }
        }
    }
}
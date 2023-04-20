package com.example.myapplication;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Toast;

import com.example.myapplication.autosend_mail.java_send_func_mail;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;

public class samp_MainActivity2 extends AppCompatActivity {

    private Button btnSelect, btnUpload;

    // view for image view
    private ImageView imageView;

    // Uri indicates, where the image will be picked from
    private Uri filePath;

    // request code
    private final int PICK_IMAGE_REQUEST = 22;

    // instance for firebase storage and StorageReference
    FirebaseStorage storage;
    StorageReference storageReference;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_samp_main2);

        ActionBar actionBar;
        actionBar = getSupportActionBar();
        ColorDrawable colorDrawable = new ColorDrawable(Color.parseColor("#0F9D58"));
//        actionBar.setBackgroundDrawable(colorDrawable);

        // initialise views
        btnSelect = findViewById(R.id.btnChoose);
        btnUpload = findViewById(R.id.btnUpload);
        imageView = findViewById(R.id.imgView);

        // get the Firebase  storage reference
        storage = FirebaseStorage.getInstance();
        storageReference = storage.getReference();

        // on pressing btnSelect SelectImage() is called
        btnSelect.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v)
            {
                SelectImage();
            }
        });

        // on pressing btnUpload uploadImage() is called
//        btnUpload.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v)
//            {
//                uploadImage();
//            }
//        });
    }

    // Select Image method
//    private void SelectImage()
//    {
//
//        // Defining Implicit Intent to mobile gallery
//        Intent intent = new Intent();
//        intent.setType("image/*");
//        intent.setAction(Intent.ACTION_GET_CONTENT);
//        startActivityForResult(
//                Intent.createChooser(
//                        intent,
//                        "Select Image from here..."),
//                PICK_IMAGE_REQUEST);
//    }
//
//    // Override onActivityResult method
//    @Override
//    protected void onActivityResult(int requestCode,
//                                    int resultCode,
//                                    Intent data)
//    {
//
//        super.onActivityResult(requestCode,
//                resultCode,
//                data);
//
//        // checking request code and result code
//        // if request code is PICK_IMAGE_REQUEST and
//        // resultCode is RESULT_OK
//        // then set image in the image view
//        if (requestCode == PICK_IMAGE_REQUEST
//                && resultCode == RESULT_OK
//                && data != null
//                && data.getData() != null) {
//
//            // Get the Uri of data
//            filePath = data.getData();
//            try {
//
//                // Setting image on image view using Bitmap
//                Bitmap bitmap = MediaStore
//                        .Images
//                        .Media
//                        .getBitmap(
//                                getContentResolver(),
//                                filePath);
//                imageView.setImageBitmap(bitmap);
//            }
//
//            catch (IOException e) {
//                // Log the exception
//                e.printStackTrace();
//            }
//        }
//    }
//
//    // UploadImage method
//    private void uploadImage()
//    {
//        if (filePath != null) {
//
//            // Code for showing progressDialog while uploading
//            ProgressDialog progressDialog
//                    = new ProgressDialog(this);
//            progressDialog.setTitle("Uploading...");
//            progressDialog.show();
//
//            // Defining the child of storageReference
//            StorageReference ref
//                    = storageReference
//                    .child(
//                            "images/"
//                                    + UUID.randomUUID().toString());
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
//                                            .makeText(samp_MainActivity2.this,
//                                                    "Image Uploaded!!",
//                                                    Toast.LENGTH_SHORT)
//                                            .show();
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
//                                    .makeText(samp_MainActivity2.this,
//                                            "Failed " + e.getMessage(),
//                                            Toast.LENGTH_SHORT)
//                                    .show();
//                        }
//                    })
//                    .addOnProgressListener(new OnProgressListener<UploadTask.TaskSnapshot>() {
//
//                                // Progress Listener for loading
//                                // percentage on the dialog box
//                                @Override
//                                public void onProgress(
//                                        UploadTask.TaskSnapshot taskSnapshot)
//                                {
//                                    double progress = (100.0
//                                            * taskSnapshot.getBytesTransferred()
//                                            / taskSnapshot.getTotalByteCount());
//                                    progressDialog.setMessage(
//                                            "Uploaded "
//                                                    + (int)progress + "%");
//                                }
//                            });
//        }
//    }

    private void SelectImage(){
        Toast.makeText(getApplicationContext(), "done", Toast.LENGTH_SHORT).show();
        java_send_func_mail javaSendfunc = new java_send_func_mail(getApplicationContext(),"sathyamsd2001@gmail.com","Votezy","You have been Successfully Registered to Crypto Byte !..\n Please Validate your id in the app to further Proceed");
        javaSendfunc.execute();

    }
}
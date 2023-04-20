package com.example.myapplication.admin;

import static android.Manifest.permission.READ_EXTERNAL_STORAGE;
import static android.Manifest.permission.WRITE_EXTERNAL_STORAGE;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import android.app.ProgressDialog;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.media.MediaPlayer;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.myapplication.R;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.OnProgressListener;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLEncoder;
import java.util.UUID;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

public class admin_face_upload extends AppCompatActivity {
    private static final int PERMISSION_REQUEST_CODE = 200;
    ImageView userpic;
    boolean playing = false;
    MediaPlayer mp;
    private Button btnSelect, btnUpload;
    TextView tv;
    // view for image view
    private ImageView imageView;
    String encodedUrl2,encodedUrl;
    // Uri indicates, where the image will be picked from
    private Uri filePath;
    int i=0;
    // request code
    private final int PICK_IMAGE_REQUEST = 22;

    // instance for firebase storage and StorageReference
    FirebaseStorage storage,storage2;
    StorageReference storageReference,storageReference2;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin_face_upload);
        btnUpload = findViewById(R.id.btnUpload3);
        if (checkPermission()) {
            Toast.makeText(this, "Permission Granted", Toast.LENGTH_SHORT).show();
        } else {
            requestPermission();
        }
        mp = MediaPlayer.create(getApplicationContext(),R.raw.success);

        userpic=findViewById(R.id.imageView14);
        storage = FirebaseStorage.getInstance();
        storageReference = storage.getReference();
        tv=findViewById(R.id.textView5);

        btnUpload.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v)
            {
                uploadImage();
            }
        });
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
                Bitmap bitmap = MediaStore
                        .Images
                        .Media
                        .getBitmap(
                                getContentResolver(),
                                filePath);
                userpic.setImageBitmap(bitmap);
            }

            catch (IOException e) {
                // Log the exception
                e.printStackTrace();
            }
        }
    }

    // UploadImage method
    private void uploadImage()
    {
        if (filePath != null) {
            String folder=admin_validation.sa_va.getFuid();
            //   String folder="5e06bf25-1002-4ed3";
            String fname="Christ/Admin/"+folder+"/";
            // Code for showing progressDialog while uploading
            ProgressDialog progressDialog
                    = new ProgressDialog(this);
            progressDialog.setTitle("Uploading...");
            progressDialog.show();

            // Defining the child of storageReference
            StorageReference ref = FirebaseStorage.getInstance("gs://votezytesting.appspot.com/").getReference().child(fname+ "face");


            // adding listeners on upload
            // or failure of image
            ref.putFile(filePath)
                    .addOnSuccessListener(
                            new OnSuccessListener<UploadTask.TaskSnapshot>() {

                                @Override
                                public void onSuccess(
                                        UploadTask.TaskSnapshot taskSnapshot)
                                {

                                    // Image uploaded successfully
                                    // Dismiss dialog
                                    progressDialog.dismiss();
                                    Toast
                                            .makeText(getApplicationContext(),
                                                    "Image Uploaded!!",
                                                    Toast.LENGTH_SHORT)
                                            .show();
                                }
                            })

                    .addOnFailureListener(new OnFailureListener() {
                        @Override
                        public void onFailure(@NonNull Exception e)
                        {

                            // Error, Image not uploaded
                            progressDialog.dismiss();
                            Toast
                                    .makeText(getApplicationContext(),
                                            "Failed " + e.getMessage(),
                                            Toast.LENGTH_SHORT)
                                    .show();
                        }
                    })
                    .addOnProgressListener(new OnProgressListener<UploadTask.TaskSnapshot>() {

                        // Progress Listener for loading
                        // percentage on the dialog box
                        @Override
                        public void onProgress(
                                UploadTask.TaskSnapshot taskSnapshot)
                        {
                            double progress = (100.0
                                    * taskSnapshot.getBytesTransferred()
                                    / taskSnapshot.getTotalByteCount());
                            progressDialog.setMessage(
                                    "Uploaded "
                                            + (int)progress + "%");
                        }
                    });
        }
    }


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
                }
                else {
                    Toast.makeText(this, "Permission Denined.", Toast.LENGTH_SHORT).show();
                    finish();
                }
            }
        }
    }

//    public void capture(View v){
//
//        Intent c = new Intent(MediaStore.ACTION_IMAGE_CAPTURE); //Implicit Intent
//        startActivityForResult(c, 0);
//    }
//    protected void onActivityResult(int requestCode,int resultCode, Intent
//            data){
//
//        super.onActivityResult(requestCode,resultCode,data);
//        Bitmap m=(Bitmap) data.getExtras().get("data");
//        userpic.setImageBitmap(m);
//        userpic.setScaleType(ImageView.ScaleType.FIT_XY);
//        Toast.makeText(sa_MainActivity6.this, "Uploaded Successfully", Toast.LENGTH_SHORT).show();
//        if(!playing){
//            mp.start();
//            playing=true;
//        }
////        button.setVisibility(View.VISIBLE);
////        prev.setVisibility(View.VISIBLE);
//    }

    public void next1(View v){
        String uname=admin_validation.sa_va.getFuid();
        //String uname="5e06bf25-1002-4ed3";
        i++;

        storage = FirebaseStorage.getInstance("gs://votezytesting.appspot.com/");
        storageReference = storage.getReference().child("Christ/Admin/"+uname+"/id-card");

        storageReference.getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
            @Override
            public void onSuccess(Uri uri) {
                // Got the download URL for 'users/me/profile.png'
                //  Toast.makeText(getApplicationContext(), "success", Toast.LENGTH_SHORT).show();
                Log.e("",uri.toString());
                String word=uri.toString();
                try {
                    URL url = new URL(word);
                    encodedUrl= URLEncoder.encode(url.toString(),"UTF-8");
                    Log.e("",String.valueOf(url));
                    Log.e("",encodedUrl);
                    //u1=encodedUrl;
                    //Toast.makeText(getApplicationContext(), , Toast.LENGTH_SHORT).show();
                } catch (MalformedURLException | UnsupportedEncodingException e) {
                    e.printStackTrace();
                }

            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception exception) {
                // Handle any errors
            }
        });
        storage2 = FirebaseStorage.getInstance("gs://votezytesting.appspot.com/");
        storageReference2 = storage2.getReference().child("Christ/Admin/"+uname+"/face");
        storageReference2.getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
            @Override
            public void onSuccess(Uri uri2) {
                // Got the download URL for 'users/me/profile.png'
//                Toast.makeText(getApplicationContext(), "success", Toast.LENGTH_SHORT).show();
                Log.e("",uri2.toString());
                String word2=uri2.toString();
                try {
                    URL url2 = new URL(word2);
                    encodedUrl2= URLEncoder.encode(url2.toString(),"UTF-8");
                    Log.e("",String.valueOf(url2));
                    Log.e("",encodedUrl2);
                    //u2=encodedUrl2;

                    //Toast.makeText(getApplicationContext(), , Toast.LENGTH_SHORT).show();
                } catch (MalformedURLException | UnsupportedEncodingException e) {
                    e.printStackTrace();
                }

            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception exception) {
                // Handle any errors
            }
        });


        Toast.makeText(getApplicationContext(), encodedUrl, Toast.LENGTH_SHORT).show();

        // Log.e("","from encode");
        if(i>=2) {
            OkHttpClient client = new OkHttpClient();
            Toast.makeText(getApplicationContext(), "called", Toast.LENGTH_SHORT).show();


            MediaType mediaType = MediaType.parse("application/x-www-form-urlencoded");
            RequestBody body = RequestBody.create(mediaType, "source_url=" + encodedUrl + "&target_url=" + encodedUrl2);

            Request request = new Request.Builder()
                    .url("https://webit-face.p.rapidapi.com/similarity")
                    .post(body)
                    .addHeader("content-type", "application/x-www-form-urlencoded")
                    .addHeader("X-RapidAPI-Host", "webit-face.p.rapidapi.com")
                    .addHeader("X-RapidAPI-Key", "836c315f96msh9a325296ac8beb5p16054ejsn26df83db7816")
                    .build();


            client.newCall(request).enqueue(new Callback() {
                @Override
                public void onFailure(@NonNull Call call, @NonNull IOException e) {
                    Log.e("", "not done");

                    // Toast.makeText(getApplicationContext(), "error", Toast.LENGTH_SHORT).show();
                }

                @Override
                public void onResponse(@NonNull Call call, @NonNull Response response) throws IOException {
                    if (response.isSuccessful()) {
                        Log.e("", "done");
                        String resp = response.body().string();
                        admin_face_upload.this.runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                try {
                                    JSONObject jsonObject = new JSONObject(resp);


                                    String val1=jsonObject.getJSONObject("data").getJSONArray("matches").getJSONObject(0).getString("similarity");
                                    String val2 = jsonObject.getString("status");
                                    String val3=jsonObject.getJSONObject("data").getString("matches_count");
                                    Float simi=Float.parseFloat(val1);
                                    Float result=simi*100;
                                    tv.setText(val2);
                                    Toast.makeText(getApplicationContext(), val2, Toast.LENGTH_SHORT).show();
                                    Toast.makeText(getApplicationContext(), result.toString(), Toast.LENGTH_SHORT).show();
                                    Log.e("",Float.toString(result));
                                    Log.e("", "done");
                                    Log.e("", val2);

//                              if (val2.equalsIgnoreCase("success")) {
//                                  String val1 = jsonObject.getJSONObject("data").getJSONArray("matches").getJSONObject(0).getString("similarity");
//                                  Float simi = Float.parseFloat(val1);
//                                  Float result = simi * 100;
//                                  Toast.makeText(getApplicationContext(), result.toString(), Toast.LENGTH_SHORT).show();
//                                  Intent i = new Intent(getApplicationContext(), sa_register.class);
//                                  startActivity(i);
//                              }

                                    if(val3.equals("1")) {
                                        if (result >=97.0) {
                                            Toast.makeText(getApplicationContext(), "Done", Toast.LENGTH_SHORT).show();
                                            Intent i = new Intent(getApplicationContext(), admin_registeration.class);
                                            startActivity(i);
                                        } else {
                                            Toast.makeText(getApplicationContext(), "Id and face does not match", Toast.LENGTH_SHORT).show();
                                        }
                                    }
                                    else{
                                        Toast.makeText(getApplicationContext(), "Upload a valid photo ", Toast.LENGTH_SHORT).show();

                                    }


                                } catch (JSONException e) {
                                    e.printStackTrace();
                                }
                            }
                        });
                    }
                }
            });
        }


//        Intent i= new Intent(getApplicationContext(), sa_register.class);
//        startActivity(i);
    }
}



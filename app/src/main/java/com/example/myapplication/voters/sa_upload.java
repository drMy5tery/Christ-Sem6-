package com.example.myapplication.voters;

import static android.Manifest.permission.READ_EXTERNAL_STORAGE;
import static android.Manifest.permission.WRITE_EXTERNAL_STORAGE;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.media.MediaPlayer;
import android.net.Uri;
import android.net.wifi.WifiManager;
import android.os.Bundle;
import android.provider.MediaStore;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import com.example.myapplication.R;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.ml.vision.FirebaseVision;
import com.google.firebase.ml.vision.common.FirebaseVisionImage;
import com.google.firebase.ml.vision.text.FirebaseVisionText;
import com.google.firebase.ml.vision.text.FirebaseVisionTextDetector;
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
import java.util.List;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;


public class sa_upload extends AppCompatActivity {

    ImageView iv;
    int i=1;
    Context context;
    Button button;
    int pageHeight = 1120;
    int pagewidth = 792;
    ProgressBar spinner;
    boolean playing = false;
    MediaPlayer mp;
    TextView prev, recog;
    private Button btnSelect, btnUpload;
String read;
    // view for image view
    private ImageView imageView;
    String encodedUrl2,encodedUrl;

    String word,word2;
    // Uri indicates, where the image will be picked from
    private Uri filePath;

    // request code
    private final int PICK_IMAGE_REQUEST = 22;

    // instance for firebase storage and StorageReference
    FirebaseStorage storage,storage2;
    StorageReference storageReference,storageReference2;
    // creating a bitmap variable
    // for storing our images
    Bitmap bmp, scaledbmp, bitmap;
    private WifiManager wifiManager1;
    // constant code for runtime permissions
    private static final int PERMISSION_REQUEST_CODE = 200;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sa_upload);
        iv = (ImageView) findViewById(R.id.imageView6);
        //spinner=(ProgressBar)findViewById(R.id.progressBar);
       // spinner.setVisibility(View.GONE);
        button=findViewById(R.id.button);
        prev=findViewById(R.id.textView4);
        prev.setVisibility(View.INVISIBLE);
        btnUpload = findViewById(R.id.btnUpload2);
        button.setVisibility(View.VISIBLE);
        mp = MediaPlayer.create(sa_upload.this, R.raw.success);
        wifiManager1 = (WifiManager) getApplicationContext().getSystemService(Context.WIFI_SERVICE);
        storage = FirebaseStorage.getInstance();
        storageReference = storage.getReference();
        recog=findViewById(R.id.textView2);

       // bmp = BitmapFactory.decodeResource(getResources(), R.drawable.logo2);
        //scaledbmp = Bitmap.createScaledBitmap(bmp, 140, 140, false);

        // below code is used for
        // checking our permissions.
        if (checkPermission()) {
            Toast.makeText(this, "Permission Granted", Toast.LENGTH_SHORT).show();
        } else {
            requestPermission();
        }
        btnUpload.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v)
            {
                uploadImage();
                //textrecognition();
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
                 bitmap = MediaStore
                        .Images
                        .Media
                        .getBitmap(
                                getContentResolver(),
                                filePath);
                iv.setImageBitmap(bitmap);
            }

            catch (IOException e) {
                // Log the exception
                e.printStackTrace();
            }
        }
    }

    public void textrecognition(){
        Toast.makeText(getApplicationContext(), "done", Toast.LENGTH_SHORT).show();

        FirebaseVisionImage image = FirebaseVisionImage.fromBitmap(bitmap);

        // below line is to create a variable for detector and we
        // are getting vision text detector from our firebase vision.
        FirebaseVisionTextDetector detector = FirebaseVision.getInstance().getVisionTextDetector();

        // adding on success listener method to detect the text from image.
        detector.detectInImage(image).addOnSuccessListener(new OnSuccessListener<FirebaseVisionText>() {
            @Override
            public void onSuccess(FirebaseVisionText firebaseVisionText) {
                // calling a method to process
                // our text after extracting.
                //processTxt(firebaseVisionText);
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                // handling an error listener.
                Toast.makeText(sa_upload.this, "Fail to detect the text from image..", Toast.LENGTH_SHORT).show();
            }
        });

    }
    private void processTxt(FirebaseVisionText text) {
        // below line is to create a list of vision blocks which
        // we will get from our firebase vision text.
        List<FirebaseVisionText.Block> blocks = text.getBlocks();

        // checking if the size of the
        // block is not equal to zero.
        if (blocks.size() == 0) {
            // if the size of blocks is zero then we are displaying
            // a toast message as no text detected.
            Toast.makeText(sa_upload.this, "No Text ", Toast.LENGTH_LONG).show();
            return;
        }
        // extracting data from each block using a for loop.
        for (FirebaseVisionText.Block block : text.getBlocks()) {
            // below line is to get text
            // from each block.
            String txt = block.getText();

            // below line is to set our
            // string to our text view.
            recog.setText(txt);
            read=txt.toLowerCase();
//            Boolean check=read.contains("john");

            if(read.contains("john")) {
                Toast.makeText(sa_upload.this, "found ", Toast.LENGTH_LONG).show();
            }
            else {
                //Toast.makeText(sa_MainActivity4.this, "Not Found", Toast.LENGTH_LONG).show();
            }

           //Toast.makeText(getApplicationContext(), txt, Toast.LENGTH_SHORT).show();
        }
    }


    private void uploadImage()
    {
        if (filePath != null) {
            String folder=sa_validate.sa_va.getFuid();
            String fname="Voters/"+folder+"/";
            // Code for showing progressDialog while uploading
            ProgressDialog progressDialog
                    = new ProgressDialog(this);
            progressDialog.setTitle("Uploading...");
            progressDialog.show();

            // Defining the child of storageReference

           // StorageReference ref = storageReference.child(fname + UUID.randomUUID().toString());
            StorageReference ref = FirebaseStorage.getInstance("gs://votezytesting.appspot.com/").getReference().child(fname+ "id-card");

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
                                            .makeText(sa_upload.this,
                                                    "Image Uploaded!!",
                                                    Toast.LENGTH_SHORT)
                                            .show();
                                   // textrecognition();
                                }
                            })

                    .addOnFailureListener(new OnFailureListener() {
                        @Override
                        public void onFailure(@NonNull Exception e)
                        {

                            // Error, Image not uploaded
                            progressDialog.dismiss();
                            Toast
                                    .makeText(sa_upload.this,
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
                            button.setVisibility(View.VISIBLE);
                        }
                    });
        }
    }



//----------------------------------------------------------------



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
//    public void next(View v){
//
//        Intent i= new Intent(sa_upload.this, sa_face.class);
//        startActivity(i);
//    }


    public void next(View v){
       // String uname=sa_validate.sa_va.getFuid();
        String uname="5e06bf25-1002-4ed3";
        i++;

        storage = FirebaseStorage.getInstance("gs://votezytesting.appspot.com/");
        storageReference = storage.getReference().child("Christ/SuperAdmin/SampleId/id-card");

        storageReference.getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
            @Override
            public void onSuccess(Uri uri) {
                // Got the download URL for 'users/me/profile.png'
                //  Toast.makeText(getApplicationContext(), "success", Toast.LENGTH_SHORT).show();
                Log.e("",uri.toString());
                 word=uri.toString();
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
        storageReference2 = storage2.getReference().child("Voters/"+uname+"/id-card");
        storageReference2.getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
            @Override
            public void onSuccess(Uri uri2) {
                // Got the download URL for 'users/me/profile.png'
//                Toast.makeText(getApplicationContext(), "success", Toast.LENGTH_SHORT).show();
                Log.e("",uri2.toString());
                 word2=uri2.toString();
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

            Toast.makeText(getApplicationContext(), "called", Toast.LENGTH_SHORT).show();

//            OkHttpClient client = new OkHttpClient();
//            MediaType mediaType = MediaType.parse("application/x-www-form-urlencoded");
//            RequestBody body = RequestBody.create(mediaType, "source_url=" + encodedUrl + "&target_url=" + encodedUrl2);
//
//            Request request = new Request.Builder()
//                    .url("https://webit-face.p.rapidapi.com/similarity")
//                    .post(body)
//                    .addHeader("content-type", "application/x-www-form-urlencoded")
//                    .addHeader("X-RapidAPI-Host", "webit-face.p.rapidapi.com")
//                    .addHeader("X-RapidAPI-Key", "836c315f96msh9a325296ac8beb5p16054ejsn26df83db7816")
//                    .build();
            OkHttpClient client = new OkHttpClient();

            MediaType mediaType = MediaType.parse("application/json");
            String value = "{\r\"image_a\": {\r\"type\": \"url\",\r\"content\": \""+word+"\"\r},\r\"image_b\": {\r\"type\": \"url\",\r\"content\": \""+word2+"\"\r }\r }";
        RequestBody body = RequestBody.create(mediaType, value);
        Request request = new Request.Builder()
        .url("https://similarity2.p.rapidapi.com/similarity")
        .post(body)
        .addHeader("content-type", "application/json")
        .addHeader("X-RapidAPI-Host", "similarity2.p.rapidapi.com")
        .addHeader("X-RapidAPI-Key", "6b84424ceamshc5a774c65b9bcebp1ef191jsn45dabb600846")
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
                        sa_upload.this.runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                try {
                                    JSONObject jsonObject = new JSONObject(resp);
//
//
                                    String val1=jsonObject.getString("similarity");
//                                    String val2 = jsonObject.getString("status");
//                                    String val3=jsonObject.getJSONObject("data").getString("matches_count");
//                                    Float simi=Float.parseFloat(val1);
//                                    Float result=simi*100;
//                                   // tv.setText(val2);
                                    Toast.makeText(getApplicationContext(), val1, Toast.LENGTH_SHORT).show();
//                                    Toast.makeText(getApplicationContext(), result.toString(), Toast.LENGTH_SHORT).show();
//                                    Log.e("",Float.toString(result));
//                                    Log.e("", "done");
//                                    Log.e("", val2);

//                              if (val2.equalsIgnoreCase("success")) {
//                                  String val1 = jsonObject.getJSONObject("data").getJSONArray("matches").getJSONObject(0).getString("similarity");
//                                  Float simi = Float.parseFloat(val1);
//                                  Float result = simi * 100;
//                                  Toast.makeText(getApplicationContext(), result.toString(), Toast.LENGTH_SHORT).show();
//                                  Intent i = new Intent(sa_face.this, sa_register.class);
//                                  startActivity(i);
//                              }
//
                                        Float res=Float.parseFloat(val1);
                                        if ( res>=0.000) {
                                            Toast.makeText(getApplicationContext(), "Done", Toast.LENGTH_SHORT).show();
//                                            Intent i = new Intent(sa_upload.this, sa_face.class);
//                                            startActivity(i);
                                        } else {
                                            Toast.makeText(getApplicationContext(), "Enter a valid id!!", Toast.LENGTH_SHORT).show();
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


//        Intent i= new Intent(sa_face.this, sa_register.class);
//        startActivity(i);
    }

}
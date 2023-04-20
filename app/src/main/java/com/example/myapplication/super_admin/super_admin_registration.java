package com.example.myapplication.super_admin;

import static android.Manifest.permission.READ_EXTERNAL_STORAGE;
import static android.Manifest.permission.WRITE_EXTERNAL_STORAGE;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import android.app.ProgressDialog;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import com.example.myapplication.R;
import com.example.myapplication.dataHolder;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.material.textfield.TextInputEditText;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.OnProgressListener;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import java.security.MessageDigest;
import java.util.UUID;

public class super_admin_registration extends AppCompatActivity {
    TextInputEditText ti_name,ti_org,ti_email,ti_password;
    private static final int PERMISSION_REQUEST_CODE = 200;
    private final int PICK_IMAGE_REQUEST = 22;
    FirebaseStorage storage,storage2;
    StorageReference storageReference,storageReference2;
    FirebaseDatabase rootNode;
    DatabaseReference reference_root,reference_uid;
    private Uri filePath;
    boolean ret_value;
    String org_name;
    int i=0;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_super_admin_registration);
        ti_name=findViewById(R.id.name);
        ti_org=findViewById(R.id.inp5);
        ti_email=findViewById(R.id.saEmail);
        ti_password=findViewById(R.id.inp8);
        storage = FirebaseStorage.getInstance();
        storageReference = storage.getReference();
        org_name="Christ";
        if (checkPermission()) {
            Toast.makeText(this, "Permission Granted", Toast.LENGTH_SHORT).show();
        } else {
            requestPermission();
        }
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
        }
    }

    // UploadImage method
    private void uploadImage(String uniq)
    {
        if (filePath != null) {
            //   String folder="5e06bf25-1002-4ed3";
            String fname="Christ/SuperAdmin/SampleId/";
            // Code for showing progressDialog while uploading
            ProgressDialog progressDialog
                    = new ProgressDialog(this);
            progressDialog.setTitle("Uploading...");
            progressDialog.show();

            // Defining the child of storageReference
            StorageReference ref = FirebaseStorage.getInstance("gs://votezytesting.appspot.com/").getReference().child(fname+uniq);


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
                    Toast.makeText(this, "Permission Denied.", Toast.LENGTH_SHORT).show();
                    finish();
                }
            }
        }
    }
    public void connect(View v){

        //  String uniq=sa_validate.sa_va.getFuid();
        //String uniq="7394ebcb-78a9-4776";
        String uniq = UUID.randomUUID().toString().substring(0, 18);
        String email=ti_email.getText().toString().trim();
        String name=ti_name.getText().toString().trim();
        String organization=ti_org.getText().toString().trim();
        String password=ti_password.getText().toString().trim();
        password=sha256(password);
        dataHolder obj=new dataHolder(name,organization,password,email,uniq,1);

        rootNode=FirebaseDatabase.getInstance("https://votezytesting-default-rtdb.asia-southeast1.firebasedatabase.app/");
        reference_root=rootNode.getReference().child("SuperAdmin");
        if(!email.isEmpty()||!name.isEmpty()||!organization.isEmpty()||!password.isEmpty()){
            //Toast.makeText(getApplicationContext(), "Registeration successfull", Toast.LENGTH_SHORT).show();

           // if(check_user(email)!=true) {
                // Toast.makeText(getApplicationContext(),"Some Of the Emails Are Invalid",Toast.LENGTH_LONG).show();
                reference_root = reference_root.getDatabase().getReference(org_name).child("SuperAdmin").child(uniq);
                reference_uid = reference_root;
                reference_uid.setValue(obj);
                Toast.makeText(getApplicationContext(), "Registeration successfull", Toast.LENGTH_SHORT).show();
                ti_password.setText("");
                ti_org.setText("");
                ti_name.setText("");
                ti_email.setText("");
                uploadImage(uniq);
                Intent i = new Intent(getApplicationContext(), super_admin_login.class);
                startActivity(i);
         //   }



        }
        else{
            Toast.makeText(getApplicationContext(), "All the fields are required", Toast.LENGTH_SHORT).show();

        }


//        FirebaseDatabase database = FirebaseDatabase.getInstance();
//        DatabaseReference myRef = database.getReference("user");
//
//        myRef.setValue("Hello");
    }
    private boolean check_user(String s_Email){
        DatabaseReference ref = FirebaseDatabase.getInstance("https://votezytesting-default-rtdb.asia-southeast1.firebasedatabase.app/").getReference();
        Query applesQuery = ref.child(org_name).child("SuperAdmin").orderByChild("email").equalTo(s_Email);
        applesQuery.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                for (DataSnapshot appleSnapshot: dataSnapshot.getChildren()) {
                    if(appleSnapshot.exists()){
                        Toast.makeText(getApplicationContext(),s_Email + " Email Already Exists",Toast.LENGTH_LONG).show();
                        ret_value=false;

                    }
                    else{
                        ret_value=true;
                    }

                }

            }


            @Override
            public void onCancelled(DatabaseError databaseError) {
                Log.e( "onCancelled", String.valueOf(databaseError.toException()));
            }
        });
        return ret_value;

    }
    public static String sha256(String base) {
        try{
            MessageDigest digest = MessageDigest.getInstance("SHA-256");
            byte[] hash = digest.digest(base.getBytes("UTF-8"));
            StringBuffer hexString = new StringBuffer();

            for (int i = 0; i < hash.length; i++) {
                String hex = Integer.toHexString(0xff & hash[i]);
                if(hex.length() == 1) hexString.append('0');
                hexString.append(hex);
            }

            return hexString.toString();
        } catch(Exception ex){
            throw new RuntimeException(ex);
        }
    }
}



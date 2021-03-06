package com.saurabh.wings2017;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.daimajia.androidanimations.library.Techniques;
import com.daimajia.androidanimations.library.YoYo;
import com.dynamitechetan.flowinggradient.FlowingGradientClass;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import java.util.HashMap;
import java.util.Map;

import me.anwarshahriar.calligrapher.Calligrapher;

public class Details extends AppCompatActivity {

    public static final String PHP_SAVE_USER = "https://scouncilgeca.com/wingsapp/savedetails.php";
    SharedPreferences sharedpreferences;
    public static final String MYPREFERENCES = "MyPrefs";

    //  Printing Details
    EditText fireName;
    EditText mobileNum,userNameInput;
    public ImageView updateInfo;
    String savedUserName, savedMobileNumber;

    // Firebase instance variables
    private FirebaseAuth mAuth;
    private FirebaseAuth.AuthStateListener mAuthListener;
    FirebaseAuth mFirebaseAuth;
    FirebaseUser mFirebaseUser;

    // Firebase Detail holders

    String mPhotoUrl, mUsername;
    String mUsermail;



    int flag=0;


    //    Printing Details
    public void printUserDetails(){

        //        Fetching Details

        fireName = (EditText) findViewById(R.id.nameInput);
        mobileNum = (EditText) findViewById(R.id.mobileInput);
        updateInfo = (ImageView) findViewById(R.id.updateInfo);



        // Initialize Firebase Auth
        mFirebaseAuth = FirebaseAuth.getInstance();
        mFirebaseUser = mFirebaseAuth.getCurrentUser();
        if (mFirebaseUser == null) {
            // Not signed in, launch the Sign In activity
            startActivity(new Intent(this, signIn.class));
            finish();
            return;
        } else {
            mUsername = mFirebaseUser.getDisplayName();
            mUsermail = mFirebaseUser.getEmail();
            mPhotoUrl = mFirebaseUser.getPhotoUrl().toString();
            Log.e("PV", "printUserDetails: " + mUsername + mUsermail);

            fireName.setText(mUsername);
        }

    }

    public void fetchData(){

        mFirebaseAuth = FirebaseAuth.getInstance();
        mFirebaseUser = mFirebaseAuth.getCurrentUser();
        mUsermail = mFirebaseUser.getEmail();
        mobileNum = (EditText) findViewById(R.id.mobileInput);
        userNameInput = (EditText) findViewById(R.id.nameInput);
        savedUserName = userNameInput.getText().toString();
        savedMobileNumber = mobileNum.getText().toString();
        final EditText college = (EditText)findViewById(R.id.colgtext);


        StringRequest stringRequest = new StringRequest(Request.Method.POST,
                PHP_SAVE_USER,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                       // Toast.makeText(Details.this,"Data Saved",Toast.LENGTH_LONG).show();

                    }
                },
                new Response.ErrorListener(){
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Toast.makeText(Details.this,error.getMessage(),Toast.LENGTH_LONG).show();

                    }
                }){
            public static final String TAG = "PV";



            @Override
            protected Map<String, String> getParams() throws AuthFailureError {






                Map<String,String> params = new HashMap<>();
                params.put("fuserName", savedUserName);
                params.put("fuserMail", mUsermail);
                params.put("fuserMob", String.valueOf(savedMobileNumber));
                params.put("fuserClg",college.getText().toString());



                return params;
            }


        };

        RequestQueue requestQueue = Volley.newRequestQueue(this);
        requestQueue.add(stringRequest);

        SaveSharedPreferences.setUserEmail(getApplicationContext(),mUsermail);
        SaveSharedPreferences.setUserName(getApplicationContext(),savedUserName);
        SaveSharedPreferences.setUserPhone(getApplicationContext(), savedMobileNumber);

        Log.e("PV", "fetchDataSavedSharedPref: " + mUsermail + savedUserName + savedMobileNumber );

    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_details);
        sharedpreferences = getSharedPreferences(MYPREFERENCES, Context.MODE_PRIVATE);

        Calligrapher calligrapher = new Calligrapher(this);
        calligrapher.setFont(this, "fonts/Montserrat600.ttf", true);


        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            getWindow().getDecorView().setSystemUiVisibility(
                    View.SYSTEM_UI_FLAG_LAYOUT_STABLE
                            | View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN);
            getWindow().setStatusBarColor(Color.TRANSPARENT);
        }
        printUserDetails();




       RelativeLayout rl = (RelativeLayout) findViewById(R.id.relative_layout_details);
        FlowingGradientClass grad = new FlowingGradientClass();
        grad.setBackgroundResource(R.drawable.translate)
                .onRelativeLayout(rl)
                .setTransitionDuration(4000)
                .start();


    }
//    @Override
//    public void onRequestPermissionsResult(int requestCode,
//                                           String permissions[], int[] grantResults) {
//        switch (requestCode) {
//
//            case 1: {
//
//                if (grantResults.length > 0
//                        && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
//                    Toast.makeText(getApplicationContext(), "Permission granted", Toast.LENGTH_SHORT).show();
//
//                } else {
//                    Toast.makeText(getApplicationContext(), "Permission denied", Toast.LENGTH_SHORT).show();
//                }
//                return;
//            }
//
//            // other 'case' lines to check for other
//            // permissions this app might request
//        }
//    }
//
//    public  boolean isPermissionGranted() {
//        if (Build.VERSION.SDK_INT >= 23) {
//            if (checkSelfPermission(android.Manifest.permission.CALL_PHONE)
//                    == PackageManager.PERMISSION_GRANTED) {
//                Log.v("TAG","Permission is granted");
//                return true;
//
//            }
//
//            else {
//
//                Log.v("TAG","Permission is revoked");
//                ActivityCompat.requestPermissions(this, new String[]{android.Manifest.permission.CALL_PHONE}, 1);
//                return false;
//            }
//        }
//        else { //permission is automatically granted on sdk<23 upon installation
//            Log.v("TAG","Permission is granted");
//            return true;
//        }
//    }
//
//    public  boolean is2PermissionGranted() {
//        if (Build.VERSION.SDK_INT >= 23) {
//            if (checkSelfPermission(Manifest.permission.READ_CONTACTS)
//                    == PackageManager.PERMISSION_GRANTED) {
//                Log.v("TAG","Permission is granted");
//                return true;
//
//            }
//
//            else {
//
//                Log.v("TAG","Permission is revoked");
//                ActivityCompat.requestPermissions(this, new String[]{android.Manifest.permission.CALL_PHONE}, 1);
//                return false;
//            }
//        }
//        else { //permission is automatically granted on sdk<23 upon installation
//            Log.v("TAG","Permission is granted");
//            return true;
//        }
//    }

    public void updateInfo(View v){
        if(!validate())
            Toast.makeText(getBaseContext(), "Your've entered incorrect credentials!", Toast.LENGTH_LONG).show();

        else{
            fetchData();
            final EditText college = (EditText)findViewById(R.id.colgtext);


                Intent updateInfoIntent = new Intent(Details.this, MainActivity.class);
                startActivity(updateInfoIntent);
                Toast.makeText(this, "Welcome " + SaveSharedPreferences.getUserName(Details.this), Toast.LENGTH_SHORT).show();
                finish();

        }

    }

    public boolean validate()
    {
        boolean valid = true;

        fireName = (EditText) findViewById(R.id.nameInput);
        mobileNum = (EditText) findViewById(R.id.mobileInput);
        updateInfo = (ImageView) findViewById(R.id.updateInfo);
        final EditText college = (EditText)findViewById(R.id.colgtext);


        if(mobileNum.getText().toString().length()!=10)
        {
            valid=false;
            mobileNum.setError("Please Enter a valid Phone Number");
            YoYo.with(Techniques.Shake)
                    .duration(500)
                .playOn(mobileNum);
        }


        if(fireName.getText().toString().isEmpty() || fireName.getText().toString().length() < 3)
        {
            valid = false;
            fireName.setError("Please Enter a  good name for your certificate");
        }

        if(college.getText().toString().isEmpty() || fireName.getText().toString().length() < 3)
        {
            valid = false;
            college.setError("Please Enter your College name");
            YoYo.with(Techniques.Shake)
                    .duration(500)
                    .playOn(college);
        }

        return valid;

    }


}

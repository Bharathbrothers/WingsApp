package com.saurabh.wings2017;

import android.content.Intent;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import java.util.HashMap;
import java.util.Map;

import cn.pedant.SweetAlert.SweetAlertDialog;
import me.anwarshahriar.calligrapher.Calligrapher;

public class GuestTwo extends AppCompatActivity {

    public static final String PHP_BOOK_GTwo = "https://scouncilgeca.com/wingsapp/guesttwo.php";


    FirebaseAuth mFirebaseAuth;
    FirebaseUser mFirebaseUser;
    String mUsername,mUsermail;
    SweetAlertDialog pDialog;
    Button bt;
    TextView txt, info;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_guest_two);

        Calligrapher calligrapher = new Calligrapher(this);
        calligrapher.setFont(this, "fonts/mont.ttf", true);

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            getWindow().getDecorView().setSystemUiVisibility(
                    View.SYSTEM_UI_FLAG_LAYOUT_STABLE
                            | View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN);
            getWindow().setStatusBarColor(Color.TRANSPARENT);
        }


        bt = (Button) findViewById(R.id.bookSeat);   //button for booking
        txt = (TextView) findViewById (R.id.regIDText);  //textview of booking number
        info = (TextView) findViewById(R.id.genEventInfo);
        info.setText("A full time voluntary activist for ten years, before joining IAS. A free-lance journalist during the same period. Undertook extensive study tours of the country. The outcome was the book, “Aswastha Dashakachi Diary”, based on the reflections on experiences of these visits.\n" +
                "\n" +
                "Avinash Dharmadhikari is a former Indian Administrative Services officer.He was a well known social activist and free-lance journalist before joining IAS. After serving on various posts during his administrative career of ten years he resigned from IAS in 1996 for the reason that can be best described in Arun Shourie's words \"to be able to serve Indian society better\".\n" +
                "He was Deputy Secretary to the Chief Minister of Maharashtra at the time of his resignation. He is founder and director of Chanakya Mandal Pariwar, a public charitable trust working in the field of Career Guidance and Personality Development. He was Director General of Neharu Yuva Kendra Sangathan in 2001 under Ministry of Sports and Youth Affairs, Government of India. As a social activist he has been part of many movements such as farmers' movements and movements against corruption. \n" +
                "He was also a part of Team Anna during India against corruption movement.He has been carrying out many study tours all over the world and attending many important International lconferences such as UNGA, UNFCCC recently. \n" +
                "He has authored many books which includes Aswastha Dashakachi Diary, Nava Vijaypath, Ek Vijaypath, Swatantra Nagarik, Jinkanara Samaj Ghadawanari Shikshanpadhhati, Aani Aapan Saglech, Ratra Gahirichya Tisarya Prahari(a collection of poems) . Aswastha Dashakachi Diary has been translated into English by Gauri Deshpande titled: Diary of a Decade of Agony. \n" +
                "Apart from being an Ex-IAS, Educationist, activist, Journalist and Author he has been widely admired as a great Orator. \n");

        if(!(SaveSharedPreferences.getGuestTwo(GuestTwo.this).isEmpty())){
            txt.setText("RegID : " + SaveSharedPreferences.getGuestTwo(GuestTwo.this));
            bt.setVisibility(View.GONE);
            txt.setVisibility(View.VISIBLE);
        }

    }

    public void getUserDetails(){

        // Initialize Firebase Auth
        mFirebaseAuth = FirebaseAuth.getInstance();
        mFirebaseUser = mFirebaseAuth.getCurrentUser();

        mUsername = mFirebaseUser.getDisplayName();
        mUsermail = mFirebaseUser.getEmail();

    }

    public void BookSeat(){

        pDialog = new SweetAlertDialog(this, SweetAlertDialog.PROGRESS_TYPE);
        pDialog.getProgressHelper().setBarColor(Color.parseColor("#A5DC86"));
        pDialog.setTitleText("Please wait!");
        pDialog.setContentText("We're building the buildings as fast as possible");
        pDialog.setCancelable(false);
        pDialog.show();


        StringRequest stringRequest = new StringRequest(Request.Method.POST,
                PHP_BOOK_GTwo,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        Toast.makeText(GuestTwo.this, "Your registration id is : "+ response, Toast.LENGTH_LONG).show();
                        SaveSharedPreferences.setGuestTwo(GuestTwo.this,response);
                        txt.setText("RegID : " +response);
                        bt.setVisibility(View.GONE);
                        txt.setVisibility(View.VISIBLE);

                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Toast.makeText(GuestTwo.this, error.getMessage(), Toast.LENGTH_LONG).show();

                    }
                }) {
            public static final String TAG = "PV";

            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> params = new HashMap<>();
                params.put("fuserName", SaveSharedPreferences.getUserName(GuestTwo.this));
                params.put("fuserMail",SaveSharedPreferences.getUserEmail(GuestTwo.this));
                params.put("fuserMobile",SaveSharedPreferences.getUserPhone(GuestTwo.this));
                return params;
            }


        };


        RequestQueue requestQueue = Volley.newRequestQueue(this);
        requestQueue.add(stringRequest);
        requestQueue.addRequestFinishedListener(new RequestQueue.RequestFinishedListener<String>() {
            @Override
            public void onRequestFinished(Request<String> request) {
                pDialog.dismissWithAnimation();
            }
        });

    }

    public void BookTicket(View v){
        BookSeat();
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        Intent i = new Intent(GuestTwo.this, MainActivity.class);
        overridePendingTransition(android.R.anim.fade_in, android.R.anim.fade_out);
        startActivity(i);
        finish();
    }
}

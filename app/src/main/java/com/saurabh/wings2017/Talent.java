package com.saurabh.wings2017;

import android.content.Intent;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

import io.github.kobakei.materialfabspeeddial.FabSpeedDial;
import me.anwarshahriar.calligrapher.Calligrapher;

public class Talent extends AppCompatActivity  {

    private String fromConst;

    //  Printing Details
    TextView fireName;
    TextView fireMail;
    ImageView fireImage;

    FabSpeedDial fab;

    // Firebase instance variables
    private FirebaseAuth mAuth;
    private FirebaseAuth.AuthStateListener mAuthListener;
    FirebaseAuth mFirebaseAuth;
    FirebaseUser mFirebaseUser;
    // Firebase Detail holders
    String mUsername;
    String mPhotoUrl;
    String mUsermail;
    ListView civil;



    ArrayList<String>  eventName_list = new ArrayList<>();
    ArrayList<String>  eventDetails_list = new ArrayList<>();
    ArrayList<String>  eventLocation = new ArrayList<>();
    ArrayList<String>  eventContactPerson_list = new ArrayList<>();
    ArrayList<String>  eventContactNum_list = new ArrayList<>();
    ArrayList<String>  eventDate = new ArrayList<>();
    ArrayList<String>  eventprice = new ArrayList<>();
    ArrayList<String> group_limit = new ArrayList<>();
    ArrayList<String> time_list = new ArrayList<>();



    public void printUserDetails(){

        //        Fetching Details

        fireName = (TextView) findViewById(R.id.displayName);
        fireMail = (TextView) findViewById(R.id.displayMail);
        fireImage = (ImageView) findViewById(R.id.displayImage);


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
            Toast.makeText(Talent.this,mUsername,Toast.LENGTH_SHORT).show();

            fireName.setText(mUsername);
            fireMail.setText(mUsermail);
            Picasso.with(Talent.this).load(mPhotoUrl).into(fireImage);

        }

    }



    RecyclerView civilRecylerHome;
    RecyclerView.Adapter civilAdapter;
    RecyclerView.LayoutManager civilLayoutManager;

    ArrayList<CivilEventList> list = new ArrayList<CivilEventList>();

    String[] name,excerpt,location,rules,criteria,price;


    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_talent);

        Calligrapher calligrapher = new Calligrapher(this);
        calligrapher.setFont(this, "fonts/mont.ttf", true);

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            getWindow().getDecorView().setSystemUiVisibility(
                    View.SYSTEM_UI_FLAG_LAYOUT_STABLE
                            | View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN);
            getWindow().setStatusBarColor(Color.TRANSPARENT);
        }


        fab  = (FabSpeedDial) findViewById(R.id.fab);


        fab.addOnStateChangeListener(new FabSpeedDial.OnStateChangeListener() {
            @Override
            public void onStateChange(boolean open) {
                // do something
            }
        });

        fab.addOnMenuItemClickListener(new FabSpeedDial.OnMenuItemClickListener() {
            @Override
            public void onMenuItemClick(FloatingActionButton fab, TextView textView, int itemId) {
           //     Toast.makeText(getApplicationContext(), "Itemid = "+textView.getText().toString(), Toast.LENGTH_SHORT).show();
                Log.e("PV", "itemID: " + itemId);

                switch(textView.getText().toString()){
                    case "Tickets":
                        Intent iticket = new Intent(getApplicationContext(),tickets.class);
                        startActivity(iticket);
                        finish();
                        break;
                    case "Cart":
                        Intent iCart = new Intent(getApplicationContext(),Cart.class);
                        startActivity(iCart);
                        finish();
                        break;
                    default:
                        break;
                }


            }
        });fab.addOnStateChangeListener(new FabSpeedDial.OnStateChangeListener() {
            @Override
            public void onStateChange(boolean open) {

            }
        });


        eventName_list.add("Energy Contraption");
        eventName_list.add("TechnoQuiz");
        eventName_list.add("Virtual Campus");
        eventName_list.add("Mindscape");



        eventDetails_list.add("Energy contraption is a chain of different energy conversion steps in which one step triggers the next one leading to completion of final task.\n" +
                "\n" +
                "Rules:\n\n" +
                "Each team should consist of maximum 5 members.\n" +
                "You will be given set up time of 2 hours.\n" +
                "Area of set up should not be more than 4mx4m\n" +
                "There is marking for energy conversion step.\n" +
                "Maximum 3 hand touches are allowed.\n" +
                "Marks will be deducted for more than 3 hand touches.\n" +
                "Project must be carried out safely. NO chemical explosions are allowed.\n" +
                "Dominos are acceptable.\n" +
                "Marks will be deducted for repeated energy conversion.\n" +
                "While actual working of set up judges will decide type of each energy conversion at each step.\n" +
                "Decision of judges will be final.\n\n" +
                "Round: There will be only one round.\n\n" +
                "Note: Only one attempt is allowed.\n");

        eventDetails_list.add("Test your knowledge about latest technology, explore your general technical knowledge about current innovations.  \n" +
                "\n" +
                "Rules:\n\n" +
                "Time slots will be allotted and participants are expected to follow them strictly.\n" +
                "Use of internet or other means of communication is strictly prohibited.\n" +
                "All the participants should have valid ID card of their respective institute with them along with receipt.\n" +
                "Participants are expected to give their correct contact details(contact no. and email id)\n" +
                "Rules may be changed without prior intimation\n" +
                "Once a team is registered same team members should be carried till the end.\n\n" +
                "Structure:\n\n" +
                "Round 1: Aptitude test on latest technology, logical thinking etc; Time allotted will be 30 minutes for 45 questions.\n" +
                "Round 2: Open Quiz for top 10 teams\n" +
                "Round 3: Surprise round\n");

        eventDetails_list.add(
                "Explore your skills with aptitude, discussion as well as interview to challenge the potential within.\n" +
                        "\n\n" +
                        "Rules: \n\n" +
                        "One person can take part for a single entry.\t\t\t\t\n" +
                        "All rounds will be taken as per the campus rules,some rules may change .\n" +
                        "Time pattern is there for each round which is expected to be followed.\n" +
                        "Participant are expected to give their contact details correctly, so as to informed them event and event results.\n" +
                        "For each round word of judges will be final.\n" +
                        "\n" +
                        "Structure:\n\n" +
                        "Round 1: Aptitude \n" +
                        "                 On basic reasoning, logical thinking, multiple choice question will be on general studies and current events,  There will be 30-40 question for 30 to 45 minutes.\n" +
                        "\n\n" +
                        "Round 2: BrainGroup Discussion\n" +
                        "               You will be given one topic at the instant and you have to speak on it and make good points. Only DISCUSSION no DEBATE.\n" +
                        "\n\n" +
                        "Round 3: Personal Interview\n" +
                        "             PI will be conducted by judges and general questions will be asked.Your skills will be judged.\n");


        eventDetails_list.add("Rules: \n" +
                "1. Each member can participate individually.\n" +
                "2. Participating team should consist of maximum 3 members.\n" +
                "3. All required components for the 2nd round will be provided by organizing team on time.\n" +
                "4. The decision of organizers will be final.\n" +
                "5. Participant or team cannot see help of others, if found in such case team will be debarred.\n" +
                "6. Participants should carry their I.D. cards.\n" +
                "\n" +

                "DAY ONE\n" +
                "\n" +
                "ROUND 1 –\n" +

                "1. Task will be provided on your email.\n" +
                "2. You have to give its solution i.e. soft copy to organizer.\n" +
                "3. Organizing Committee will convey message to the shortlisted teams.\n" +
                "\n" +
                "DAY TWO\n" +
                "\n" +
                "ROUND 2 –\n" +

                "1. The task will be given to each team depend upon first task in round 1.\n" +
                "2.  Teams will be evaluated on the basis of skills of marketing and business and the profit output.\n" +
                "3.  Result of round 2 will be declared within an hour.\n");

        eventLocation.add("Electrical Dept");
        eventLocation.add("IT Dept");
        eventLocation.add("Classroom Complex");
        eventLocation.add("Electrical Dept");



        eventContactPerson_list.add("Rushikesh Andil");
        eventContactPerson_list.add("Aabha Dabhadkar");
        eventContactPerson_list.add("Vyankatesh Kulkarni");
        eventContactPerson_list.add("Suraj Ingole");



        eventContactNum_list.add("9860585933");
        eventContactNum_list.add("9923629348");
        eventContactNum_list.add("9527500921");
        eventContactNum_list.add("7387025795");




        eventDate.add("6 OCT 2017");
        eventDate.add("5 OCT 2017");
        eventDate.add("5 OCT 2017");
        eventDate.add("5 OCT 2017");



        eventprice.add("250");
        eventprice.add("50");
        eventprice.add("50");
        eventprice.add("100");


        group_limit.add("5");
        group_limit.add("2");
        group_limit.add("0");
        group_limit.add("3");



        time_list.add("6 OCT, 2017\n4 pm onwards");
        time_list.add("5 OCT, 2017\n4 pm onwards");
        time_list.add("5 OCT, 2017\n1 pm onwards");
        time_list.add("5 OCT, 2017\n3.45 pm onwards");






        TalentAdapter ad = new TalentAdapter(Talent.this, eventName_list, eventDetails_list, eventLocation, eventContactPerson_list, eventContactNum_list, eventDate, eventprice, group_limit, time_list);
        civil = (ListView)findViewById(R.id.cart_list_show);
        civil.setAdapter(ad);

        civil.setOnItemClickListener(new AdapterView.OnItemClickListener() {

            public void onItemClick(AdapterView<?> arg0, View arg1, int arg2,
                                    long arg3) {
                // For Long Duration Toast
                TextView  name = (TextView) civil.getChildAt(arg2).findViewById(R.id.CivilEventName);
                TextView location = (TextView) civil.getChildAt(arg2).findViewById(R.id.CivilEventLocation);
                TextView desc = (TextView) civil.getChildAt(arg2).findViewById(R.id.CivilEventExcerpt);
                TextView price = (TextView) civil.getChildAt(arg2).findViewById(R.id.eventPrice);
                TextView date = (TextView) civil.getChildAt(arg2).findViewById(R.id.eventDate);
                TextView person_name = (TextView) civil.getChildAt(arg2).findViewById(R.id.eventContactPerson);
                TextView person_num = (TextView) civil.getChildAt(arg2).findViewById(R.id.eventContactNum);


                Toast.makeText(getApplicationContext(), name.getText().toString() , Toast.LENGTH_LONG).show();

                Intent eventi = new Intent(Talent.this, GenericEventHome.class);
                eventi.putExtra("name", name.getText().toString());
                eventi.putExtra("location",location.getText().toString());
                eventi.putExtra("desc",desc.getText().toString());
                eventi.putExtra("price",price.getText().toString());
                eventi.putExtra("date",date.getText().toString());
                eventi.putExtra("person_name",person_name.getText().toString());
                eventi.putExtra("person_num",person_num.getText().toString());

                startActivity(eventi);
                finish();
                // For Long Short Toast


            }
        });




    }
    @Override
    public void onBackPressed() {
        super.onBackPressed();
        Intent i = new Intent(Talent.this, MainActivity.class);
        startActivity(i);
        finish();
    }




}
package com.threeamstudios.theorangeloop;

import android.app.Activity;
import android.content.Intent;
import android.database.DatabaseUtils;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;

import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;


import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLConnection;


public class EventPage extends Activity implements View.OnClickListener{
    public static boolean flag = true;
    private Organization organization = new Organization();
    DatabaseHandler db = DatabaseHandler.getInstance(getBaseContext());

    private String orgUrl;
    private String orgName;
    private String orgDesc;
    private ImageView imageView;
    private TextView ren;
    private TextView red;
    Button button3;
    Button button4;
    Button button6;
    // final TextView textName = (TextView)findViewById(R.id.Org_Name);
    // final TextView textDesc = (TextView)findViewById(R.id.Org_Desc);

    public Integer nm;
    private Bundle ext;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        // Get intent from master list
        ext = getIntent().getExtras();
        orgName = ext.getString("Org_Name");
        orgDesc = ext.getString("Org_Desc");
        orgUrl = ext.getString("Org_URL");

        nm = Intent.EXTRA_DOCK_STATE_CAR;

        //Remove title bar
        this.requestWindowFeature(Window.FEATURE_NO_TITLE);

        //Remove notification bar
        this.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_event_page);


        // textName.setText(orgName);
        // textDesc.setText(orgDesc);
//=======
        // Set Texts
        imageView = (ImageView) findViewById(R.id.imageViewClub);
//>>>>>>> e019c9ec87b68b0fd25077559765fa8ffac27bcf

        // Create an object for subclass of AsyncTask
        GetXMLTask task = new GetXMLTask();
        task.execute(new String[]{orgUrl});
        ren = (TextView) findViewById(R.id.RegisteredEventName);
        red = (TextView) findViewById(R.id.RegisteredEventDesc);
        ren.setText(this.orgName);
        red.setText(this.orgDesc);

//<<<<<<< HEAD
        // Set Texts
        imageView = (ImageView) findViewById(R.id.imageViewClub);
//=======
        //set Button Actions
        button3 = (Button)findViewById(R.id.button3);
       button3.setOnClickListener(this);
        button4 = (Button)findViewById(R.id.button4);
       button4.setOnClickListener(this);
        button6 = (Button)findViewById(R.id.button6);
        button6.setOnClickListener(this);


        //DatabaseHandler db = DatabaseHandler.getInstance(getBaseContext());
        //set text for button


        organization = db.getRegisteredOrgFromString(orgName);
        if(organization.getOrgName() == null){
            button4.setText("Register");
        }else{
            button4.setText("Unregister");
        }
//>>>>>>> e019c9ec87b68b0fd25077559765fa8ffac27bcf

    }

    private class GetXMLTask extends AsyncTask<String, Void, Bitmap> {
        @Override
        protected Bitmap doInBackground(String... urls) {
            Bitmap map = null;
            for (String url : urls) {
                map = downloadImage(url);
            }
            return map;
        }

        // Sets the Bitmap returned by doInBackground
        @Override
        protected void onPostExecute(Bitmap result) {
            imageView.setImageBitmap(result);
        }

        // Creates Bitmap from InputStream and returns it
        private Bitmap downloadImage(String url) {
            Bitmap bitmap = null;
            InputStream stream = null;
            BitmapFactory.Options bmOptions = new BitmapFactory.Options();
            bmOptions.inSampleSize = 1;

            try {
                stream = getHttpConnection(url);
                bitmap = BitmapFactory.
                        decodeStream(stream, null, bmOptions);
                stream.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
            return bitmap;
        }

        private InputStream getHttpConnection(String urlString)

                throws IOException {

            InputStream stream = null;
            java.net.URL url = new URL(urlString);
            URLConnection connection = url.openConnection();

            try {
                HttpURLConnection httpConnection = (HttpURLConnection) connection;
                httpConnection.setRequestMethod("GET");
                httpConnection.connect();

                if (httpConnection.getResponseCode() == HttpURLConnection.HTTP_OK) {
                    stream = httpConnection.getInputStream();
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
            return stream;
        }
    }
    @Override
    public void onClick(View view) {
        switch (view.getId())
        {
            case R.id.button3:
                button3Click();
                break;
            case R.id.button4:
                button4Click();
                break;
            case R.id.button6:
                button6Click();
                break;
        }
    }
    private void button3Click(){// go to my personilzed org listing
        startActivity(new Intent(this, MemberHomePage.class));
    }

    private void button4Click(){
        DatabaseHandler db = DatabaseHandler.getInstance(getBaseContext());
        // Organization org = new Organization();
        Organization org = db.getRegisteredOrgFromString(orgName);
        //
        if(org.getOrgName() == null){
            button4.setText("Unregister");
            org = new Organization(orgName);
            org.setOrgName(orgName);
            org.setOrgDesc("TEMP");


            db.insertRegisteredOrganization(org);
            // MasterOrganizationList.addToRegList(org);
        }else{
            button4.setText("Register");
            db.deleteRegistered(orgName);

        }

           // button4.setText("Unregister");

           // db.getDataOrganization(nm);
          /*  organization = new Organization();
            organization.setOrgName(orgName);
            organization.setOrgDesc(orgDesc);
            db.insertRegisteredOrganization(organization);*/
    }

    private void button6Click(){
        DatabaseHandler dbh = DatabaseHandler.getInstance(getBaseContext());
        SQLiteDatabase db = dbh.getReadableDatabase();
        long num = DatabaseUtils.queryNumEntries(db, "registered");
        String strLong = Long.toString(num);
        Toast.makeText(
                getApplicationContext(),strLong,

                Toast.LENGTH_LONG).show();
    }
    public void react(View v){
        
    }

    public void viewMy(View v){
        // Intent transition = new Intent(this, MemberHomePage.class);
        Intent transition = new Intent(EventPage.this, MemberHomePage.class);
        startActivity(transition);
    }
}

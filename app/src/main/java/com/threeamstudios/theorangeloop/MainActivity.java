package com.threeamstudios.theorangeloop;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;

/* ------- */
/* [Model] */
/* ------- */
public class MainActivity extends Activity {

    // String field for debug purposes.
    private static final String TAG = "OrangeLoop";
	
	// Test Comment
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        //Remove title bar
        this.requestWindowFeature(Window.FEATURE_NO_TITLE);

        //Remove notification bar
        this.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);

        this.setContentView(R.layout.activity_main);

        // Initialize DB with simulated server tables
        DatabaseHandler databaseHandler = new DatabaseHandler(this);

        // Establish DataBases and Tables. (Clubs)
        OrganizationController.mimicInsertDataFromServer(databaseHandler.getInstance(this));
    }

    @Override
    protected void onStart() {
        super.onStart();
    }

    // Executes with button interaction. Goes to personal user home page
    public void goToHome(View view) {
        // Intent transition = new Intent(this, MemberHomePage.class);
        Intent transition = new Intent(this, MasterOrganizationList.class);
        startActivity(transition);
    }

}

package com.threeamstudios.theorangeloop;

import android.app.Activity;
import android.app.ListActivity;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.view.View.OnClickListener;

import java.util.ArrayList;

public class MasterOrganizationList extends Activity {
    public static ArrayList<Organization> registeredList = new ArrayList<Organization>();
    //LIST OF ARRAY STRINGS WHICH WILL SERVE AS LIST ITEMS
    private ListView mainListView ;
    private OrganizationToListViewAdaptor adaptor;
    ArrayList<Organization> orgList = new ArrayList<Organization>();

    //DEFINING A STRING ADAPTER WHICH WILL HANDLE THE DATA OF THE LISTVIEW
    ArrayAdapter<String> adapter;

    //RECORDING HOW MANY TIMES THE BUTTON HAS BEEN CLICKED
    int clickCounter=0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_master_organization_list);

        // Find the ListView resource.
        // mainListView = (ListView) findViewById(R.id.list);
        // mainListView.setOnItemClickListener(eventListener);

        ((ListView) findViewById(R.id.list)).setOnItemClickListener(eventListener);

        // Test
        adaptor = new OrganizationToListViewAdaptor(this, orgList);
        ((ListView) findViewById(R.id.list)).setAdapter(adaptor);
        initItems(findViewById(R.id.list));
    }

    // METHOD WHICH WILL HANDLE DYNAMIC INSERTION
    // Fix transition bug

    private AdapterView.OnItemClickListener eventListener = new AdapterView.OnItemClickListener() {

        public void onItemClick(AdapterView adapterView, View v, int pos, long ids) {
            // Get org fields
            TextView name = (TextView) v.findViewById(R.id.Org_Name);
            TextView desc = (TextView) v.findViewById(R.id.Org_Desc);

            DatabaseHandler dbHandler = new DatabaseHandler(getBaseContext());
            String orgUrl = dbHandler.getOrgFromString(name.getText().toString()).getImageURL();

            // Put extras
            Bundle sender = new Bundle();
            sender.putString("Org_Name", name.getText().toString());
            sender.putString("Org_Desc", desc.getText().toString());
            sender.putString("Org_URL", orgUrl);

            // Intent transition = new Intent(this, MemberHomePage.class);
            Intent transition = new Intent(MasterOrganizationList.this, EventPage.class);
            transition.putExtras(sender);
            startActivity(transition);
        }
    };

    public void initItems(View v) {
        // Get Database reference
        DatabaseHandler handler = DatabaseHandler.getInstance(this);

        // View all organizations in the GUI
        orgList.addAll(handler.getAllOrganization());
        adaptor.notifyDataSetChanged();
    }

    public static void addToRegList(Organization org) {
        registeredList.add(org);
    }
}

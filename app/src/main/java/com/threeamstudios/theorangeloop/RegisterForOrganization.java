package com.threeamstudios.theorangeloop;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

public class RegisterForOrganization extends AppCompatActivity {

    int from_Where_I_Am_Coming = 0;
    private DatabaseHandler mydb ;

    TextView name ;
    TextView desc;
    TextView size;
    TextView imgurl;
    int id_To_Update = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register_for_organization);

        name = (TextView) findViewById(R.id.editTextName);
        desc = (TextView) findViewById(R.id.editTextDesc);
        size = (TextView) findViewById(R.id.editTextSize);

        mydb = new DatabaseHandler(this);

        Bundle extras = getIntent().getExtras();
        if (extras != null) {
            int Value = extras.getInt("id");

            if (Value > 0) {
                //means this is the view part not the add contact part.
                Cursor rs = mydb.getDataOrganization(Value);
                id_To_Update = Value;
                rs.moveToFirst();

                String nam = rs.getString(rs.getColumnIndex(DatabaseHandler.ORG_NAME));
                String des = rs.getString(rs.getColumnIndex(DatabaseHandler.ORG_DESC));
                String siz = rs.getString(rs.getColumnIndex(DatabaseHandler.ORG_SIZE));

                if (!rs.isClosed()) {
                    rs.close();
                }
                Button b = (Button) findViewById(R.id.button1);
                b.setVisibility(View.INVISIBLE);

                name.setText((CharSequence) nam);
                name.setFocusable(false);
                name.setClickable(false);

                desc.setText((CharSequence) des);
                desc.setFocusable(false);
                desc.setClickable(false);
                //name,phone,email
                size.setText((CharSequence) siz);
                size.setFocusable(false);
                size.setClickable(false);
            }
        }
    }

    public boolean onOptionsItemSelected(MenuItem item)
    {
        super.onOptionsItemSelected(item);
        switch(item.getItemId())
        {
            case R.id.Edit_Organization:
                Button b = (Button)findViewById(R.id.button1);
                b.setVisibility(View.VISIBLE);
                name.setEnabled(true);
                name.setFocusableInTouchMode(true);
                name.setClickable(true);

                desc.setEnabled(true);
                desc.setFocusableInTouchMode(true);
                desc.setClickable(true);

                size.setEnabled(true);
                size.setFocusableInTouchMode(true);
                size.setClickable(true);

                return true;
            case R.id.Delete_Organization:

                AlertDialog.Builder builder = new AlertDialog.Builder(this);
                builder.setMessage(R.string.deleteContact)
                        .setPositiveButton(R.string.yes, new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int id) {
                                mydb.deleteOrganization(id_To_Update);
                                Toast.makeText(getApplicationContext(), "Deleted Successfully", Toast.LENGTH_SHORT).show();
                                Intent intent = new Intent(getApplicationContext(),MainActivity.class);
                                startActivity(intent);
                            }
                        })
                        .setNegativeButton(R.string.no, new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int id) {
                                // User cancelled the dialog
                            }
                        });
                AlertDialog d = builder.create();
                d.setTitle("Are you sure");
                d.show();

                return true;
            default:
                return super.onOptionsItemSelected(item);

        }
    }

    public void run(View view)
    {
        Bundle extras = getIntent().getExtras();
        if(extras !=null)
        {
            int Value = extras.getInt("id");
            if(Value>0){
                if(mydb.updateOrganization(id_To_Update, name.getText().toString(), desc.getText().toString(), imgurl.getText().toString())){
                    Toast.makeText(getApplicationContext(), "Updated", Toast.LENGTH_SHORT).show();
                    Intent intent = new Intent(getApplicationContext(),MainActivity.class);
                    startActivity(intent);
                }
                else{
                    Toast.makeText(getApplicationContext(), "not Updated", Toast.LENGTH_SHORT).show();
                }
            }
            else{
                Organization condition = new Organization(name.getText().toString(), desc.getText().toString(), imgurl.getText().toString());
                if(mydb.insertOrganization(condition)){
                    Toast.makeText(getApplicationContext(), "done", Toast.LENGTH_SHORT).show();
                }

                else{
                    Toast.makeText(getApplicationContext(), "not done", Toast.LENGTH_SHORT).show();
                }
                Intent intent = new Intent(getApplicationContext(),MainActivity.class);
                startActivity(intent);
            }
        }
    }

}



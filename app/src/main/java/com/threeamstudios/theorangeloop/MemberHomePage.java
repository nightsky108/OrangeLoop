package com.threeamstudios.theorangeloop;

import android.content.Intent;
import android.app.Activity;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLConnection;
import java.util.ArrayList;

public class MemberHomePage extends Activity {

    public static ArrayList<Organization> organizationArrayList = new ArrayList<Organization>();

    //LIST OF ARRAY STRINGS WHICH WILL SERVE AS LIST ITEMS
    private ListView mainListView ;
    private RegisteredOrganizationAdaptor adaptor;

    //DEFINING A STRING ADAPTER WHICH WILL HANDLE THE DATA OF THE LISTVIEW
    ArrayAdapter<String> adapter;

    private static ArrayList<String> URLfields;

    ImageView imageView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        // mainListView.setAdapter(null);
        // ((ListView) findViewById(R.id.list)).setAdapter(null);

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_member_home_page);

        // Find the ListView resource.
        // mainListView = (ListView) findViewById(R.id.list);
        // mainListView.setOnItemClickListener(eventListener);

        // Test
        // organizationArrayList.add(new Organization("Fill"));
        adaptor = new RegisteredOrganizationAdaptor(this, organizationArrayList);

        // Remove Old
        ((ListView) findViewById(R.id.list)).invalidateViews();
        ((ListView) findViewById(R.id.list)).setAdapter(null);

        ((ListView) findViewById(R.id.list)).setAdapter(adaptor);
        initItems(findViewById(R.id.list));
    }

    // Executes with button interaction. Goes to club event page.
    public void goToClub(View view) {
        Intent transition = new Intent(this, EventPage.class);
        startActivity(transition);
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

    // METHOD WHICH WILL HANDLE DYNAMIC INSERTION
    // Fix transition bug
    public void initItems(View v) {
        // Get Database reference
        DatabaseHandler handler = DatabaseHandler.getInstance(this);

        organizationArrayList.clear();
        adaptor.notifyDataSetChanged();

        // View all organizations in the GUI
        organizationArrayList.addAll(handler.getAllRegisteredOrganization());
        adaptor.notifyDataSetChanged();
    }

}

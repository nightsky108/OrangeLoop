package com.threeamstudios.theorangeloop;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.text.Layout;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLConnection;
import java.util.ArrayList;

/**
 * Created by Josh M on 5/1/2016.
 */
public class OrganizationToListViewAdaptor extends BaseAdapter {

    private LayoutInflater inflater;
    private ArrayList<Organization> objects;

    // For image call
    private static String URL;
    private static ImageView imageView;

    private class ViewHolder {
        ImageView imageView1;
        TextView textView1;
        TextView textView2;
    }

    public OrganizationToListViewAdaptor(Context context, ArrayList<Organization> object) {
        inflater = LayoutInflater.from(context);
        this.objects = object;
    }

    public int getCount() {
        return objects.size();
    }

    public Organization getItem(int position) {
        return objects.get(position);
    }

    public long getItemId(int position) {
        return position;
    }

    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder holder = null;
        holder = new ViewHolder();

        //if(convertView == null) {
            convertView = inflater.inflate(R.layout.orgrow, null);

            // Get Club Image
            this.imageView = (ImageView) convertView.findViewById(R.id.imgBG);

            holder.imageView1 = (ImageView) convertView.findViewById(R.id.imgBG);
            holder.textView1 = (TextView) convertView.findViewById(R.id.Org_Name);
            holder.textView2 = (TextView) convertView.findViewById(R.id.Org_Desc);
            convertView.setTag(holder);
        //} else {
        //    holder = (ViewHolder) convertView.getTag();
        //}

        // Get image from https call for holder
        GetXMLTask task = new GetXMLTask();

        // Get URL from holder
        URL = objects.get(position).getImageURL();
        task.execute(new String[]{URL});

        holder.imageView1 = this.imageView;

        holder.textView1.setText(objects.get(position).getOrgName());
        holder.textView2.setText(objects.get(position).getOrgDesc());
        return convertView;
    }

    // Restful https calls
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
}

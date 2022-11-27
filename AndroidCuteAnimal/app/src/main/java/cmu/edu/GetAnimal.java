/**
 * @author Natalie Lee
 * @andrewID chiaenl
 * Code reference: AndroidInterestingPicture Lab 7 GetPicture.java
 */
package cmu.edu;

import android.app.Activity;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Build;
import androidx.annotation.RequiresApi;
import com.google.gson.Gson;
import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;

public class GetAnimal {
    CuteAnimal ip = null;   // for callback
    String searchTerm = null;       // search api for this number
    Animal animal = null;
    Bitmap picture = null;          // returned from the animal api
    private String urlString = "https://sleepy-shelf-03569.herokuapp.com/get-animal";

    public class Animal{
        String name;
        String latin_name;
        String animal_type;
        String active_time;
        double length_min;
        double length_max;
        double weight_min;
        double weight_max;
        double lifespan;
        String habitat;
        String diet;
        String geo_range;
        String image_link;
        int id;
        public String getPicLink(){return image_link;}
        public String toString() {
            return String.format("{\"name\": \"%s\",\"animal_type\": \"%s\",\"diet\": \"%s\", \"lifespan\": %f," +
                            "\"geo_range\": \"%s\", length_max: %f, length_min: %f," +
                            "weight_max: %f, weight_min: %f}",
                    this.name, this.animal_type, this.diet, this.lifespan,
                    this.geo_range, this.length_max, this.length_min,
                    this.weight_max, this.weight_min);
        }
    }
    public void search(String searchTerm, Activity activity, CuteAnimal ip) {
        this.ip = ip;
        this.searchTerm = searchTerm;
        new BackgroundTask(activity).execute();
    }

    private class BackgroundTask {

        private Activity activity; // The UI thread

        public BackgroundTask(Activity activity) {
            this.activity = activity;
        }

        private void startBackground() {
            new Thread(new Runnable() {
                public void run() {
                    doInBackground();
                    activity.runOnUiThread(new Runnable() {
                        public void run() {
                            onPostExecute();
                        }
                    });
                }
            }).start();
        }

        private void execute(){
            startBackground();
        }

        private void doInBackground() {
            /* Search animal and store into this model */
            animal = search(searchTerm);
            /* Search picture from pic url stored in animal object */
            picture = searchPic(animal.getPicLink());
        }

        public void onPostExecute() {
            ip.pictureReady(animal, picture);
        }

        /*
         * Search animal api for the searchTerm argument, and return a Bitmap that can be put in an ImageView
         */
        private Animal search(String searchTerm) {
            HttpURLConnection conn;
            int status = 0;
            Animal result = new Animal();
            Gson gson;
            try {
                URL url = new URL(urlString);
                conn = (HttpURLConnection) url.openConnection();
                conn.setRequestMethod("GET");
                conn.setRequestProperty("Content-Type", "text/plain; charset=utf-8");
                conn.setRequestProperty("Accept", "text/plain");
                conn.setRequestProperty("searchWord", searchTerm);
                // wait for response
                status = conn.getResponseCode();

                if (status == 200) {
                    String responseBody = getResponseBody(conn);
                    gson = new Gson();
                    result = gson.fromJson(responseBody, Animal.class);
                }
                conn.disconnect();
            } catch (MalformedURLException e) {
                System.out.println("URL Exception thrown" + e);
            } catch (IOException e) {
                System.out.println("IO Exception thrown" + e);
            } catch (Exception e) {
                System.out.println("IO Exception thrown" + e);
            }
            return result;
        }
        private Bitmap searchPic(String pictureURL){
            try {
                URL u = new URL(pictureURL);
                return getRemoteImage(u);
            } catch (Exception e) {
                e.printStackTrace();
                return null; // so compiler does not complain
            }
        }
        /*
         * Given a URL referring to an image, return a bitmap of that image
         */
        @RequiresApi(api = Build.VERSION_CODES.P)
        private Bitmap getRemoteImage(final URL url) {
            try {
                final URLConnection conn = url.openConnection();
                conn.connect();
                BufferedInputStream bis = new BufferedInputStream(conn.getInputStream());
                Bitmap bm = BitmapFactory.decodeStream(bis);
                return bm;
            } catch (IOException e) {
                e.printStackTrace();
                return null;
            }
        }
        public String getResponseBody(HttpURLConnection conn) {
            String responseText = "";
            try {
                String output = "";
                BufferedReader br = new BufferedReader(new InputStreamReader(
                        (conn.getInputStream())));
                while ((output = br.readLine()) != null) {
                    responseText += output;
                }
                conn.disconnect();
            } catch (IOException e) {
                System.out.println("Exception caught " + e);
            }
            return responseText;
        }
    }
}

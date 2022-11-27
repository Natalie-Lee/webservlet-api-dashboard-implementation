/**
 * @author Natalie Lee
 * @andrewID chiaenl
 * Code reference: AndroidInterestingPicture Lab 7 InterestingPicture.java
 */
package cmu.edu;

import android.graphics.Bitmap;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import androidx.appcompat.app.AppCompatActivity;

public class CuteAnimal extends AppCompatActivity {
    CuteAnimal me = this;

    /**
     * This function is created on starting the app
     * It listens to the button event and strike the search function defined in GetAnimal.
     * @param savedInstanceState
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        /*
         * The click listener will need a reference to this object, so that upon successfully finding a picture from animal api, it
         * can callback to this object with the resulting picture Bitmap.  The "this" of the OnClick will be the OnClickListener, not
         * this InterestingPicture.
         */
        final CuteAnimal ma = this;

        /*
         * Find the "submit" button, and add a listener to it
         */
        Button submitButton = (Button)findViewById(R.id.submit);

        // Add a listener to the send button
        submitButton.setOnClickListener(new View.OnClickListener(){
            public void onClick(View viewParam) {
                String searchTerm = ((EditText)findViewById(R.id.searchTerm)).getText().toString();
                GetAnimal gp = new GetAnimal();
                gp.search(searchTerm, me, ma); // Done asynchronously in another thread.  It calls ip.pictureReady() in this thread when complete.
            }
        });
    }

    /**
     * This function is called after background program finishes searching function.
     * @param animal
     * @param picture
     */
    public void pictureReady(GetAnimal.Animal animal, Bitmap picture) {
        ImageView pictureView = (ImageView)findViewById(R.id.animalPicture);
        TextView searchView = (EditText)findViewById(R.id.searchTerm);
        TextView detailInfoView = (TextView) findViewById(R.id.detailInfo);

        /* Set picture output to pictureView*/
        if (picture != null) {
            pictureView.setImageBitmap(picture);
            System.out.println("picture");
            pictureView.setVisibility(View.VISIBLE);
        } else {
            pictureView.setImageResource(R.mipmap.ic_launcher);
            System.out.println("No picture");
            pictureView.setVisibility(View.INVISIBLE);
        }
        /* Clear the searchView for later use */
        searchView.setText("");

        /* Set response to the detailInfoView*/
        String printDetail = String.format("name: %s \nanimal_type: %s\ndiet: %s \nlifespan: %f\n" +
                                            "geo_range: %s \nlength_max: %f \nlength_min: %f \n" +
                                            "weight_max: %f \nweight_min: %f\n",
                                            animal.name, animal.animal_type, animal.diet, animal.lifespan,
                                            animal.geo_range, animal.length_max, animal.length_min,
                                            animal.weight_max, animal.weight_min);
        detailInfoView.setText(printDetail);
        detailInfoView.setVisibility(View.VISIBLE);
        pictureView.invalidate();
    }
}
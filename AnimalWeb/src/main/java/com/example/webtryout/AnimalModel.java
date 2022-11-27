/**
 * @author Natalie Lee
 * @andrewID chiaenl
 */
package com.example.webtryout;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.List;

public class AnimalModel {
    Animal animal = null;
    String pictureURL = null;
    String animalURL = "https://zoo-animal-api.herokuapp.com/animals/rand/10";

    /**
     * Receive client request number and send request to animal url,
     * set up animal object and picture url for later use.
     * @param searchterm
     * @throws UnsupportedEncodingException
     * @throws JsonProcessingException
     */
    public void doAnimalSearch(int searchterm)
            throws UnsupportedEncodingException, JsonProcessingException {
        /* Request to animal api and receive string output */
        String response = fetch(animalURL);

        /* Clean up response (List of json form) */
        ObjectMapper mapper = new ObjectMapper();
        /* Map response to list of animals */
        List<Animal> animalList = mapper.readValue(response, new TypeReference<List<Animal>>() {});
        /* Select the item of client's choice */
        animal = animalList.get(searchterm);
        /* Retrieve picture url from animal object */
        pictureURL = animal.getPicLink();
    }

    /**
     * Make an HTTP request to a given URL
     * reference: InterestingPicture from Lab2
     * @param urlString The URL of the request
     * @return A string of the response from the HTTP GET.  This is identical
     * to what would be returned from using curl on the command line.
     */
    private String fetch(String urlString) {
        String response = "";
        try {
            URL url = new URL(urlString);
            /*
             * Create an HttpURLConnection.  This is useful for setting headers
             * and for getting the path of the resource that is returned (which
             * may be different than the URL above if redirected).
             * HttpsURLConnection (with an "s") can be used if required by the site.
             */
            HttpURLConnection connection = (HttpURLConnection) url.openConnection();
            // Read all the text returned by the server
            BufferedReader in = new BufferedReader(new InputStreamReader(connection.getInputStream(), "UTF-8"));
            String str;
            // Read each line of "in" until done, adding each to "response"
            while ((str = in.readLine()) != null) {
                // str is one line of text readLine() strips newline characters
                response += str;
            }
            in.close();
        } catch (IOException e) {
            System.out.println("Eeek, an exception");
            // Do something reasonable.  This is left for students to do.
        }
        return response;
    }
}

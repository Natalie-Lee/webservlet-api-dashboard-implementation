/**
 * @author Natalie Lee
 * @andrewID chiaenl
 */
package com.example.webtryout;

import java.io.*;
import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;

import com.mongodb.client.*;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.*;
import jakarta.servlet.annotation.*;
import org.bson.Document;

@WebServlet(name = "animalSearch", urlPatterns = {"/get-animal","/get-dashboard"})
public class AnimalServlet extends HttpServlet {
    private static final String DATABASE_NAME = "animal_db";
    private static final String COLLECTION_NAME = "user_log";
    private AnimalModel am = null;
    private static final String mongoDBURL = "";
    private MongoClient mongoClient;
    private MongoDatabase database;
    private MongoCollection<Document> collection;

    @Override
    public void init() {
        /* Initiate mongoDB and animal object for later use */
        mongoClient = MongoClients.create(mongoDBURL);
        database = mongoClient.getDatabase(DATABASE_NAME);
        collection = database.getCollection(COLLECTION_NAME);
        am = new AnimalModel();
    }
    public static boolean isNumeric(String str) {
        try {
            Double.parseDouble(str);
            return true;
        } catch(NumberFormatException e){
            return false;
        }
    }

    /**
     * Identify the incoming request. There are two possible paths.
     * (1) /get-animal: from Android app, request a search for animal
     * (2) /get-dashboard: from Webpage, request database read
     *
     * @param request
     * @param response
     * @throws IOException
     * @throws ServletException
     */
    @Override
    public void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
        /* If the url pattern is /get-animal: the request is from Android app */
        if (request.getServletPath().equals("/get-animal")) {
            String searchWord = request.getParameter("searchWord");
            int searchNum;
            /* Error handling If user input is null or not numeric -> set as 0*/
            if (searchWord == null || !isNumeric(searchWord)) searchNum = 0;
            else searchNum = Integer.parseInt(searchWord) % 10;
            PrintWriter out = response.getWriter();
            String animalResponse = "";

            /* Call search function and store data in am.animal */
            am.doAnimalSearch(searchNum);
            /* Set the response to string type for user end's ease of use */
            animalResponse = am.animal.toString();

            /* For current search result into Document and insert into MongoDB*/
            Document record = createDocument(request, animalResponse);
            collection.insertOne(record);

            /* Send back response */
            response.setContentType("application/json");
            response.setCharacterEncoding("UTF-8");
            out.println(animalResponse);
            out.flush();
        } else if (request.getServletPath().equals("/get-dashboard")){
            /* If url pattern is /get-dashboard: the request is from web page and is calling for database retrieving */
            /* Read the database into List of Document*/
            List<Document> logs = new ArrayList<>();
            FindIterable<Document> iter = collection.find();
            iter.into(logs);
            /* Add read logs into request*/
            request.setAttribute("records", logs);
            /* Display database result on /index.jsp */
            getServletContext().getRequestDispatcher("/index.jsp").forward(request, response);
        }

    }

    /**
     * Create document to write into MongoDB
     * @param request
     * @param content
     * @return
     */
    private Document createDocument(HttpServletRequest request, String content) {
        // extract client info
        String userAgent = request.getHeader("User-Agent");
        // extract timestamp
        Timestamp timestamp = new Timestamp(System.currentTimeMillis());
        String timestampString = new SimpleDateFormat("mm/dd/yy hh:mm:ss").format(timestamp);
        // add on api result to content
        Document record = new Document();
        record.append("user_agent", userAgent);
        record.append("timestamp", timestampString);
        record.append("content", content);
        return record;
    }
}
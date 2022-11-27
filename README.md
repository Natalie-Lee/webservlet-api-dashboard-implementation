# webservlet-api-dashboard-implementation
Project4Task2Writeup
Name: NatalieLee Andrew id: chiaenl
1. Implement a native Android application
  * Has at least 3 different kinds of views:
    Views used: (1) Button (2) TextView (3) ImageView
  * Requires input from the user
    Ask user to type number into and use it as a selection of picture
  * Makes an HTTP request
    Function search in GetAnimal starts a HttpURLConnection and send request to endpoint
  * Receives and parses an XML or JSON formatted reply from your web service 
    The Json response from /get-animal is as below
    The search in GetAnimal read string response with BufferReader, and turn into Json
  * Displays new information to the user
  * Is repeatable 
    Type in another number and new data will be fetched

2. Implement a web service, deployed to Heroku
  * Implement a simple (can be a single path) API
    Zoo Animal API Link: https://zoo-animal-api.herokuapp.com/ 
  * Receives an HTTP request from the native Android application Use url pattern /get-animal for the request from Android application
  * Executes business logic appropriate to your application.
    doAnimalSearch in AnimalModel receives client’s choice of number and call fetch to contact with Zoo Animal api.
  * Replies to the Android application with an XML or JSON formatted response.
    doGet AnimalServlet , extract response(json) from animal object and turn it into string for ease of use

3. Handle error conditions
  * Invalid mobile app input
  * Handled non-digit, null, or larger than 10 situations

4. Log useful information
  * Stored the user_agent, timestamp, and response data

5. Store the log information in a database
  * If the request url-pattern is /get-animal , the response is stored in MongoDB database/collection

6. Display operations analytics and full logs on a web-based dashboard
7. Deploy the web service to Heroku
  * Please access to dashboard through this link: 
    https://sleepy-shelf-03569.herokuapp.com/get-dashboard 
  * Access to request new animal (two ways):
    1) https://sleepy-shelf-03569.herokuapp.com/get-animal 
    2) Android app enter number and click on submit

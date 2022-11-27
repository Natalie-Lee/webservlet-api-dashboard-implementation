<%@ page import="java.util.ArrayList" %>
<%@ page import="org.bson.Document" %>
<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<!DOCTYPE html>
<html>
<head>
    <title>ANIMAL</title>
</head>
<body>
<br/>

<form method="GET">
    <button type="refresh" >Refresh</button><br/>
</form>

<table style="width: 100%">
    <tr>
        <th>log_num</th>
        <th>animal_content</th>
        <th>user_agent</th>
        <th>timestamp</th>
    </tr>
    <%
        // display response data on the table
        ArrayList<Document> logs = (ArrayList<Document>) request.getAttribute("records");
        if (logs != null ) {
            int log_num = 1;
            for (Document log : logs) {
    %>
    <tr>
        <td><%=log_num%></td>
        <td><%=log.get("content")%></td>
        <td><%=log.get("user_agent")%></td>
        <td><%=log.get("timestamp")%></td>
    </tr>
    <%
                log_num++;
            }
        }
    %>
</table>
</body>

</html>
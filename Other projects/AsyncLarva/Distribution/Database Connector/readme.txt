***Database Connector***

The purpose of this simple application to act as an interface between Larva and a database of events. 
To specify the events of interest you can instantiate the Template class and add filters. 
Each filter includes a field of interest and is useful to constraint the value of the field. 

**EXAMPLE**: the following template specifies the event name "BasicTransaction" from table "transactionTable" with timestamp field "creationTimestamp" (crucial for ordering events). 
The final parameter of the Template constructor specifies the timestamp type, supporting DATETIME or LONG_TIMESTAMP timestamps
The first two filters are specified simply to indicate that the fields concerned are to be passed as event parameters to the monitor. 
The rest of the filters have the purpose of acting as as constraints over the fields indicated: 

  new Template("BasicTransaction", "transactionTable", "database", "creationTimestamp", Template.DATETIME_TIMESTAMP)
   .addFilter(new Filter("transactionId"))
   .addFilter(new Filter("transactionAmount"))
   .addFilter(new Filter("transactiontype",Filter.NOT_EQUALS,"Automatic"))
   .addFilter(new Filter("transactionStatus",Filter.EQUALS,"SUCCESSFUL")));

Alternatively, one can also specify SQL code directly to the template: 

  new Template("event_name", "SELECT * FROM database.login_log", "login_time", Template.LONG_TIMESTAMP);

These templates should be set in the *editMe.EditMe* class. In this class you can also set a number of parameters, which can otherwise be passed as commandline arguments (see below).

  
**RUNNING Database Connector**
            
To call the Database Connector use java -cp ".;aspectjrt.jar;mysql-connector-java.jar" Events.EventGenerator with following options: 
  -s <timestamp>    the timestamp of the first event of interest in milliseconds
  -e <timestamp>    the last timestamp of interest  in milliseconds
  -url <string>     the JDBC url
  -un <string>      the username to access the DB
  -pw <string>      the password to access the DB

*EXAMPLE*

-s 0
-e 9999999999999
-url jdbc:mysql://localhost:3306/audit 
-un "root" 
-pw ""


**OTHER REMARKS** 

For large use cases, it is advisable to use memory options for Java to ensure you don't go out of memory: -Xmx1024M -Xms1024M

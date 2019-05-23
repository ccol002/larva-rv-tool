***Sample Script***

This folder contains contains a number of files which typically would have to be supplied by the user:
 
*audit.sql*          SQL which can used to set up a MYSQL database to be used with the sample
*initialization.txt* A text file which the system uses to record whether the monitor have been initialised or not. 
                       This is not being used by the example but is useful if the monitor is run several times,
                       processing subsequent time portions each time.
*SC.java*            This text file contains any relevant methods which are used by *script.lrv*
*script.lrv*         A sample Larva script based on the table specified in *audit.sql*


Typical usage: 

1>> Typically one would want to have his own database rather than the sample provided here. 
This would require using a custom sql file or a custom database in the first place.

2>> *script.lrv* contains the properties to be monitored. This should be edited to correspond with the properties 
one wants to check over the database being monitored. The script refers to SC.java so make sure to include any
methods called from *script.lrv* into SC.java 
(note: change the name of script.lrv might require some modifications in the imports within Database Connector)

3>> Once the database is in place and *script.lrv* modified, then make sure to modify *editMe.EditMe* within Database 
Connector to provide the visibility required for the events in *script.lrv*. 

4>> Now you are ready to run the tool. First execute *LarvaBIG*, put the generated files in the source folder of 
*Database Connector* and subsequently run *Database Connector*. 

 

 
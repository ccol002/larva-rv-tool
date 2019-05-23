***LarvaBIG***

LarvaBIG is a scalable version of Larva which stores monitors as fields in database tables. Its use is basically identical to standard Larva except that LarvaBIG can only handle basic variable types since these have to be converted into database values. (In the future other technologies might be explored such as storing serialised objects rather than the actual monitor variables.)

Calling LarvaBIG simply requires the specification of a Larva script file: 

   java compiler.Compiler <larvascript directory+file> 

LarvaBIG also supports a number of optional switches: 

  -o   <dir> to specify the output directory
  -r   <dir> to specify the report directory (where the monitor reports problems)
  -g   <dir> to specify the Graphviz directory
  -t   to hide the text on the transitions in the automaton diagrams
  -p   to hide the priority values on the transitions in the automaton diagrams
  -v   to have the automata produce verbose output			
  -url <url> to specify database to store monitors		
  -un  <username> to specify the username to access the database				
  -pw  <password> to specify the password to access the database		
  
*EXAMPLE*

"/Users/christiancolombo/Database Connector/sample script/script.lrv" 
-o "/Users/christiancolombo/Database Connector/src" 
-r "/Users/christiancolombo/Database Connector/src/larvaOutput" 
-url jdbc:mysql://localhost:3306/monitor 
-un "root" 
-pw ""
-v  

*Very importantly* LarvaBIG assumes a MYSQL database setup is available. 
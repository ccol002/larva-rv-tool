To run the Larva compiler the following call should be made: 

      java -cp . compiler.Compiler <script file> -o <output directory>

  or if using the JAR file

      java -cp larva.jar compiler.Compiler <script file> -o <output directory>


Furthermore, there are 4 available batch file for the following 4 operations:

  1>> run compiler:	

	simply runs the compiler to generate the necessary Java files to monitor
	the property described in the specified script file. However, this does 			
        not compile any Java code. For this batch file to run you simply need 				
        the java command to be available in the command line. 
	Two arguments are required: 
		(1) the script file with the LARVA code
		(2) the output directory where the source files will be generated
		    (this should usually be the root directory of the application)

  2>> source weave compiler: 

	this generates the Java files for a specified script file and 					
	compiles the application to be monitored together with the newly 					
	generated files. Then, it runs the monitored system with the 					
	monitoring. For this to run the source code of the 							
	application is required. It is assumed that the commands: java, 					
	javac, ajc and aj5 are available. Three arguments will be 						
	required:
		(1) the script file with the LARVA code
		(2) the root directory of the application source code
		(3) the command line arguments passed to the java program to run 						the application (e.g. main.Main -inp1 -inp2)

  3>> binary weave compiler: 

	this generates the Java files for a specified script file and 					
	compiles the application to be monitored together with the newly 					
	generated files. However there is no need for the source code but 				
	simply the byte code. Then, it runs the monitored system with the 				
	monitoring. It is assumed that the commands: java, javac, ajc and 				
	aj5 are available. Three arguments will be required:
		(1) the script file with the LARVA code
		(2) the root directory of the application byte code
		(3) the command line arguments passed to the java program to run 						the application (e.g. main.Main -inp1 -inp2)

  4>> load-time source weave compiler: 

	this generates the Java files for a specified script file and 					
	compiles the application to be monitored together with the newly 					
	generated files. However, the compilation of the user application is 				
	compiled without the monitoring (before the larva files are generated); 			
	load-time weaving is used such that the classes are weaved as they are 				
	loaded. Then, it runs the monitored system with the monitoring. For this 			
	to run the source code of the application is required. It is assumed 				
	that the commands: java, javac, ajc and aj5 are available. Three 					
	arguments will be required:
		(1) the script file with the LARVA code
		(2) the root directory of the application source code
		(3) the command line arguments passed to the java program to run 						the application (e.g. main.Main -inp1 -inp2)


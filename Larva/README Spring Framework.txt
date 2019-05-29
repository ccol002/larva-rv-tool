1) The location of the monitor has to be inside the SpringBootApplication root, i.e. in a sub-package of the main application - not directly in the src folder. In that case, Spring will ignore the package and compile time weaving fails.

2) I had overlooked that the package and import statements must be adjusted - this can be partially done with corresponding import statements in the import section of the .lrv file. A couple of lines (automatically generated imports and package declarations) have to be deleted manually to avoid warnings.

3) For some reason  I have not been able to write logs to the monitor output file, which is correctly generated upon instantiation. I have been able to create logs when monitoring a "normal" Java program with a Larva generated .aj monitor.
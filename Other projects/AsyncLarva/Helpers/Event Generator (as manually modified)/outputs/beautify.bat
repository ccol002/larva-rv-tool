@ECHO OFF

CD .\

SET /P F=Enter script file: 

java -cp larvaOutput.jar larvaOutput.Parser %F%

%F:~0,-5%\index.html"


pause
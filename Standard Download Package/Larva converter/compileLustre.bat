@echo off

cd .\

SET F=
SET /P F=Enter script file: 

SET O=
SET /P O=Enter destination file:

java -cp . main.Main -LUSTRE %F% -o %O%

pause

@echo off

cd .\

SET F=
SET /P F=Enter script file: 

SET O=
SET /P O=Enter destination file:

java -cp . main.Main -IMPL %F% -o %O%

pause

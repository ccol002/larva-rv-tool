cd .\

SET F=
SET /P F=Enter script file: 

SET O=
SET /P O=Enter destination directory: 

java -cp . compiler.Compiler %F% -o %O%

pause
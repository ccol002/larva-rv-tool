cd .\

SET F=
SET /P F=Enter script file: 

java -cp . compiler.Compiler %F%

pause
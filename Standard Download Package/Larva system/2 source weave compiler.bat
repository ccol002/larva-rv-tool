@echo off 
echo ******************************************************************************
echo *  This will automatically weave and run your application                    *
echo *  make sure that your PATH directory gives direct access to the commands:   *
echo *  javac, ajc, aj5                                                           *
echo *  also ensure that the source code of your application is available         *
echo ******************************************************************************

cd .\

SET F=
SET /P F=Enter script file: 

SET O=
SET /P O=Enter application root directory: 

SET A=
SET /P A=Enter application command line: 


echo generating files...
java -cp . compiler.Compiler %F% -o %O%

echo compiling files...
call ajc -1.5 -cp aspectjrt.jar;%O% -sourceroots %O%

echo compiling aspects...
call ajc -1.5 -cp aspectjrt.jar;%O% -outxmlfile %O%\META-INF\aop.xml %O%\aspects\*.aj

echo running application...
call aj5 -cp %O% %A%

pause
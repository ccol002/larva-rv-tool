set JAVA_HOME=C:\Program Files\Java\jre1.5.0_06

set CLASS_PATH=;.;C:\Program Files\Java\jre1.5.0_06\lib\tools.jar;

set PATH=%PATH%;C:\AppPerfect\DevTest\apdevtools\eclipse\plugins\com.appperfect.teststudio_9.6.0.3086\lib;

"%JAVA_HOME%\bin\java"  -Xbootclasspath/a:"C:\AppPerfect\DevTest\apdevtools\eclipse\plugins\com.appperfect.teststudio_9.6.0.3086\lib\profiler.jar" -Djp.library.path="C:\AppPerfect\DevTest\apdevtools\eclipse\plugins\com.appperfect.teststudio_9.6.0.3086\lib" -agentlib:aptsti32=propertiesfile="C:\Documents and Settings\Colombo.LAPTOP\Desktop\profiler\NewProject\profiler.properties" -classpath "%CLASS_PATH%" compiler.Compiler bank.txt
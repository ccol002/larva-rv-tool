cd C:\Documents and Settings\Colombo.LAPTOP\Desktop\projects\BenchmarkMOP

SET /P F=Enter Test number: 

java -cp bin;aspectjrt.jar benchmark.Tester %F% 
pause

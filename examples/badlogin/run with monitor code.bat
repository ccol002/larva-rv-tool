call ajc -1.8 -cp aspectjrt.jar -sourceroots . -d bin

call java -cp "bin;aspectjrt.jar" main.Main

pause

@echo off
cd /d "%~dp0"
javac -cp . Main.java Input.java Player.java RoundRobin.java Knockout.java
if errorlevel 1 exit /b 1
java -cp . Main

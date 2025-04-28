@echo off
set JAVA_HOME=C:\Program Files\Eclipse Adoptium\jdk-11.0.26.4-hotspot
set PATH=%JAVA_HOME%\bin;%PATH%
start cmd /k "java --version && echo Now using Java 11 && cd /d C:\Users\swapn\Downloads\sonarqube-7.9.6"
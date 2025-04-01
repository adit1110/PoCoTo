::[Bat To Exe Converter]
::
::YAwzoRdxOk+EWAnk
::fBw5plQjdG8=
::YAwzuBVtJxjWCl3EqQJgSA==
::ZR4luwNxJguZRRnk
::Yhs/ulQjdF+5
::cxAkpRVqdFKZSDk=
::cBs/ulQjdF+5
::ZR41oxFsdFKZSDk=
::eBoioBt6dFKZSDk=
::cRo6pxp7LAbNWATEpCI=
::egkzugNsPRvcWATEpCI=
::dAsiuh18IRvcCxnZtBJQ
::cRYluBh/LU+EWAnk
::YxY4rhs+aU+JeA==
::cxY6rQJ7JhzQF1fEqQJQ
::ZQ05rAF9IBncCkqN+0xwdVs0
::ZQ05rAF9IAHYFVzEqQJQ
::eg0/rx1wNQPfEVWB+kM9LVsJDGQ=
::fBEirQZwNQPfEVWB+kM9LVsJDGQ=
::cRolqwZ3JBvQF1fEqQJQ
::dhA7uBVwLU+EWDk=
::YQ03rBFzNR3SWATElA==
::dhAmsQZ3MwfNWATElA==
::ZQ0/vhVqMQ3MEVWAtB9wSA==
::Zg8zqx1/OA3MEVWAtB9wSA==
::dhA7pRFwIByZRRnk
::Zh4grVQjdCyDJGyX8VAjFBpQRRCAAES0A5EO4f7+086CsUYJW/IDKt2KiIaLMO8v7VfrYIZ4hkZql+gDOBQWewquDg==
::YB416Ek+ZG8=
::
::
::978f952a14a936cc963da21a135fa983
@echo off
setlocal

echo Compiling PoCoTo Project...

:: Set JavaFX path
set JAVAFX_LIB=C:\Program Files\Java\javafx-sdk-23.0.2\lib

:: Build full classpath (Gson + JavaFX + src)
set CLASSPATH="lib/gson-2.12.1.jar;%JAVAFX_LIB%\*;src"

:: Compile
javac -cp %CLASSPATH% ^
--module-path "%JAVAFX_LIB%" --add-modules javafx.controls,javafx.fxml ^
-d bin ^
src/app/*.java ^
src/controller/*.java ^
src/model/*.java ^
src/view/*.java ^
src/util/*.java ^
src/service/*.java

IF %ERRORLEVEL% NEQ 0 (
    echo.
    echo Compilation failed!
    pause
    exit /b
)

echo.
echo Compilation successful!
echo Running PoCoToApp...

:: Run the app
java --module-path "%JAVAFX_LIB%" --add-modules javafx.controls,javafx.fxml ^
-cp "bin;lib/gson-2.12.1.jar" app.PoCoToApp

pause
endlocal

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

@REM @echo off
@REM echo Compiling PoCoTo Project...

@REM javac --module-path "C:\Program Files\Java\javafx-sdk-23.0.2\lib" --add-modules javafx.controls,javafx.fxml -d bin src\app\PoCoToApp.java src\controller\*.java src\model\*.java src\view\*.java

@REM if %errorlevel% neq 0 (
@REM     echo Compilation failed!
@REM     pause
@REM     exit /b
@REM )

@REM echo Running PoCoToApp...

@REM java --module-path "C:\Program Files\Java\javafx-sdk-23.0.2\lib" --add-modules javafx.controls,javafx.fxml -cp bin app.PoCoToApp

@REM pause

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
    echo ❌ Compilation failed!
    pause
    exit /b
)

echo.
echo ✅ Compilation successful!
echo Running PoCoToApp...

:: Run the app
java --module-path "%JAVAFX_LIB%" --add-modules javafx.controls,javafx.fxml ^
-cp "bin;lib/gson-2.12.1.jar" app.PoCoToApp

pause
endlocal

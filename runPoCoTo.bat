@echo off
echo Compiling PoCoTo Project...

javac --module-path "C:\Program Files\Java\javafx-sdk-23.0.2\lib" --add-modules javafx.controls,javafx.fxml -d bin src\app\PoCoToApp.java src\controller\*.java src\model\*.java src\view\*.java

if %errorlevel% neq 0 (
    echo Compilation failed!
    pause
    exit /b
)

echo Running PoCoToApp...

java --module-path "C:\Program Files\Java\javafx-sdk-23.0.2\lib" --add-modules javafx.controls,javafx.fxml -cp bin app.PoCoToApp

pause

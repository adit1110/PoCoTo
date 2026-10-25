@echo off
setlocal

REM Run from the repository root regardless of the caller's directory.
cd /d "%~dp0"

set "JAVAFX_LIB=C:\Program Files\Java\javafx-sdk-23.0.2\lib"
set "BUILD_DIR=build\tests"
set "APP_CLASSES=%BUILD_DIR%\classes"
set "TEST_CLASSES=%BUILD_DIR%\test-classes"

if not exist "%JAVAFX_LIB%\javafx.controls.jar" (
    echo ERROR: JavaFX not found at "%JAVAFX_LIB%".
    exit /b 1
)
if not exist "lib\junit-platform-console-standalone-1.10.0.jar" (
    echo ERROR: JUnit console launcher is missing from lib\.
    exit /b 1
)

REM Clean dedicated test output so no stale .class files can mask edits.
if exist "%BUILD_DIR%" rmdir /s /q "%BUILD_DIR%"
mkdir "%APP_CLASSES%" || exit /b 1
mkdir "%TEST_CLASSES%" || exit /b 1

echo Compiling PoCoTo application sources...
javac --module-path "%JAVAFX_LIB%" --add-modules javafx.controls,javafx.fxml ^
  -cp "lib/*" -d "%APP_CLASSES%" ^
  src\app\*.java src\controller\*.java src\model\*.java ^
  src\view\*.java src\util\*.java src\service\*.java
if errorlevel 1 (
    echo ERROR: Application compilation failed.
    exit /b 1
)

echo Compiling PoCoTo regression tests...
javac --module-path "%JAVAFX_LIB%" --add-modules javafx.controls,javafx.fxml ^
  -cp "%APP_CLASSES%;bin;lib/*" -d "%TEST_CLASSES%" ^
  src\test\BearStatLogicTest.java src\test\StatDecayLifecycleTest.java src/test/SaveLoadRegressionTest.java src/test/GameplaySaveLoadTest.java
if errorlevel 1 (
    echo ERROR: Test compilation failed.
    exit /b 1
)

echo Running PoCoTo regression tests...
java --module-path "%JAVAFX_LIB%" --add-modules javafx.controls,javafx.fxml ^
  -cp "%TEST_CLASSES%;%APP_CLASSES%;bin;lib/*" ^
  org.junit.platform.console.ConsoleLauncher execute ^
  --select-class test.BearStatLogicTest ^
  --select-class test.StatDecayLifecycleTest ^
  --select-class test.SaveLoadRegressionTest
if errorlevel 1 (
    echo ERROR: One or more tests failed.
    exit /b 1
)

REM Run UI tests in a separate JVM: JavaFX cannot restart after Platform.exit().
echo Running gameplay Save/Load UI tests...
java --module-path "%JAVAFX_LIB%" --add-modules javafx.controls,javafx.fxml ^
  -cp "%TEST_CLASSES%;%APP_CLASSES%;bin;lib/*" ^
  org.junit.platform.console.ConsoleLauncher execute ^
  --select-class test.GameplaySaveLoadTest
if errorlevel 1 (
    echo ERROR: Gameplay UI tests failed.
    exit /b 1
)

echo All selected regression tests passed.
exit /b 0

@echo off
setlocal
cd /d "%~dp0"
set "JAVAFX_LIB=C:\Program Files\Java\javafx-sdk-23.0.2\lib"
set "APP_CLASSES=build\tests\classes"
set "LEGACY_CLASSES=build\tests\legacy-classes"
if not exist "%APP_CLASSES%\model\Bear.class" (
  echo ERROR: First run runPoCoToTests.bat to compile the application.
  exit /b 1
)
if not exist "%LEGACY_CLASSES%" mkdir "%LEGACY_CLASSES%"
echo Compiling original CS2212 tests...
javac --module-path "%JAVAFX_LIB%" --add-modules javafx.controls,javafx.fxml ^
 -cp "%APP_CLASSES%;lib/*" -d "%LEGACY_CLASSES%" ^
 src\test\PlayerTest.java src\test\ParentalControlTest.java ^
 src\test\GameControllerTest.java src\test\DatabaseHelperTest.java ^
 src\test\NotificationServiceTest.java src\test\PauseMenuTest.java ^
 src\test\AutoDecayTest.java src\test\BearControllerTest.java ^
 src\test\CoTest.java src\test\InventoryTest.java ^
 src\test\PoTest.java src\test\ToTest.java
if errorlevel 1 exit /b 1
echo Running original model and controller tests...
java --module-path "%JAVAFX_LIB%" --add-modules javafx.controls,javafx.fxml ^
 -cp "%LEGACY_CLASSES%;%APP_CLASSES%;bin;lib/*" ^
 org.junit.platform.console.ConsoleLauncher execute ^
 --select-class test.PlayerTest ^
 --select-class test.ParentalControlTest ^
 --select-class test.AutoDecayTest ^
 --select-class test.BearControllerTest ^
 --select-class test.CoTest ^
 --select-class test.InventoryTest ^
 --select-class test.PoTest ^
 --select-class test.ToTest ^
 --select-class test.controller.GameControllerTest ^
 --select-class util.DatabaseHelperTest ^
 --select-class service.NotificationServiceTest
if errorlevel 1 exit /b 1
echo Running Pause menu UI test in separate JVM...
java --module-path "%JAVAFX_LIB%" --add-modules javafx.controls,javafx.fxml ^
 -cp "%LEGACY_CLASSES%;%APP_CLASSES%;bin;lib/*" ^
 org.junit.platform.console.ConsoleLauncher execute --select-class test.PauseMenuTest
if errorlevel 1 exit /b 1
echo All selected original tests passed.

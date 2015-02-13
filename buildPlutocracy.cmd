@echo off

:: Set JAVA_HOME
set JAVA_HOME = -1

:: The following works with most jdk installations.
set KeyName=HKEY_LOCAL_MACHINE\SOFTWARE\JavaSoft\Java Development Kit
set Cmd=reg query "%KeyName%" /s
for /f "tokens=2*" %%i in ('%Cmd% ^| find "JavaHome"') do set JAVA_HOME=%%j

:: If JAVA_HOME was not set above, try looking for a 32-bit JDK on 64-bit windows.
if "%JAVA_HOME%" == -1 goto secondary

:setAnt
:: set ANT_HOME
set ANT_HOME=ant

::set PATH
set PATH=%PATH%;%ANT_HOME%\bin

:: Call ant.
call %ANT_HOME%\bin\ant.bat

pause
exit

:secondary
set KeyName=HKEY_LOCAL_MACHINE\SOFTWARE\WoW6432Node\JavaSoft\Java Development Kit
set Cmd=reg query "%KeyName%" /s
for /f "tokens=2*" %%i in ('%Cmd% ^| find "JavaHome"') do set JAVA_HOME=%%j
goto setAnt
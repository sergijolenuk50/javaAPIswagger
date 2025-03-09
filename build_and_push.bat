@echo off

tasklist /FI "IMAGENAME eq Docker Desktop.exe" | find /I "Docker Desktop.exe" >nul
if errorlevel 1 (
    echo Docker Desktop starting ...
    start "" "C:\Program Files\Docker\Docker\Docker Desktop.exe"
    :waitForDocker
    tasklist /FI "IMAGENAME eq Docker Desktop.exe" | find /I "Docker Desktop.exe" >nul
    if errorlevel 1 (
        timeout /t 10 /nobreak >nul
        goto waitForDocker
    )
    timeout /t 2 /nobreak >nul
    echo Docker Desktop successfuly started ...
) 

echo Changing directory client...
cd "ReactCategoriAPI"

echo Building Docker image client...
docker build -t olenuk_client .

echo Docker login...
docker login

echo Tagging Docker image client...
docker tag olenuk_client:latest sergijolenuk50/olenuk_client:latest

echo Pushing Docker image client to repository...
docker push sergijolenuk50/olenuk_client:latest

echo Done ---client---!

echo Changing directory api...
cd ".."
cd "javaAPIswagger"

echo Building Docker image api...
docker build -t java-olenuk-api . 

echo Tagging Docker image api...
docker tag java-olenuk-api:latest sergijolenuk50/java-olenuk-api:latest

echo Pushing Docker image api to repository...
docker push sergijolenuk50/java-olenuk-api:latest

echo Done ---api---!
pause


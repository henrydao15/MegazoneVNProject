@echo off
title ${project.artifactId}
rem -------------------------------------------------------------------------
rem
rem
rem -------------------------------------------------------------------------

setlocal & pushd

set JAVA_OPTS=-Dspring.profiles.include=core,test
if "${project.artifactId}" == "gateway" (
    set JAVA_OPTS =
)
java -Xms128m -Xmx512m -jar %JAVA_OPTS% ${project.artifactId}-${project.version}.jar

endlocal & popd
pause




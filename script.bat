javac -d Framework/WEB-INF/classes -cp Framework/lib/servlet-api.jar Framework/WEB-INF/classes/*.java
cd Framework/WEB-INF/classes
jar -cf framework.jar ./
xcopy /y framework.jar "..\..\..\framework.jar"
cd ..\..\..\
SET CLASSPATH=Testframework\WEB-INF\lib\framework.jar
javac -parameters -d Testframework/WEB-INF/classes/*.java
cd Testframework
jar -cf TestFramework.war ./
xcopy /y TestFramework.war "..\TestFramework.war"
cd ..\
xcopy /y TestFramework.war "C:\Program Files\Apache Software Foundation\Tomcat 10.0\webapps"
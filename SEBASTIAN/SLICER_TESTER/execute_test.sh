mvn clean install;
cp -r json_test ./target
cd target
java -jar SsoTester-0.0.2-SNAPSHOT.jar


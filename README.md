# Installation guide

### Setup dependencies

Run the `dep_setup.sh` script. Please check that everything has been correctly installed.

Then run the `install_libs.sh` script giving as argument the desired parent folder for the libs repo.

### Install the application

Enter the `<repo-root>/SEBASTIAN` folder and run 
```mvn clean package```

A jar file (`Sebastian-<version>-<release-type>.jar`) will be generated in the `<repo-root>/SEBASTIAN/target` folder.

### Start the application

$ java -jar Sebastian-<version>-<release-type>.jar

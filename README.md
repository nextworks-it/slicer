# Installation guide

### 0.Cloning

The Sebastian repo includes a submodule for the GUI template Gentelella.
Please make sure to clone including submodules
(`git clone --recurse-submodules <repo-url>`) or, if you have already cloned
the repo, to initialize the submodule (`git submodule update --init --recursive`).

### 1.Setup dependencies

Run the `dep_setup.sh` script. Please check that everything has been correctly installed.

Then run the `install_libs.sh` script giving as argument the desired parent folder for the libs repo.

### 2.Build the application

In the `<repo-root>` folder, run 
```mvn clean install```

A jar file (`Sebastian-<version>-<release-type>.jar`) will be generated in the `<repo-root>/SEBASTIAN/target` folder.
This is a portable jar, containing everything needed by the application, and can be used as-is, even on other systems.

### 3.Start the application

After building the application, in the `<repo-root>/SEBASTIAN/target` folder, run

$ java -jar Sebastian-<version>-<release-type>.jar

### 4.Credentials

There is one pre-generated bootstrap user with credentials admin/admin.
Using this user, you can create more (basic-level) users.

### 5.GUI

See SEBASTIAN_WEB_GUI/README.md

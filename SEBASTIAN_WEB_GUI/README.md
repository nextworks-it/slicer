# How to expose the Sebastian web GUI

This readme contains the instruction for exposing the Sebastian web GUI interacting with the Sebastian core.
There are two ways to do that:
1. Manual apache2 daemon configuration
2. Use a pre-configured docker image.

Sections 1. and 2. will deal with the respective methods. The recommended method for the general case is
method 2.

Note: in all cases, for the GUI to be working, it will have to run on the same system as the Sebastian Core.

## 1.Configuring Apache web server daemon for the Sebastian web GUI

### 1.0. NOTE:
in the following, we will give all paths relative to the apache2 configuration root folder,
e.g. in ubuntu it's /etc/apache2. It will also assume all other apache2 configurations are the default
ones.

### 1.1. Main configuration file

In the file `apache2.conf`, locate the list of <Directory/> directives and add
there the stanza

```
<Directory /path/to/folder>
	Options +Indexes +FollowSymLinks +Includes
	AllowOverride None
	Require all granted
</Directory>
```

where `/path/to/folder/` is the path to the parent folder of the sebastian_web_gui and gentelella folder
(e.g. the SEBASTIAN_WEB_GUI folder itself).

### 1.2. Enable apache INCLUDE directive

run the command
```
$ ln -s mods-available/include.load mods-enabled/include.load
```
soft-linking the file include.load in conf-available into the folder conf-enabled

### 1.3. Enable INCLUDE directive in html files

In the folder `conf-available` create a file `html-include.conf` (the name is not actually relevant)
containing the following:

```
AddType text/html .html
AddOutputFilter INCLUDES .html
```

then run the command
```
$ ln -s conf-available/html-include.conf conf-enabled/html-include.conf
```
linking it into the conf-enabled folder.

### 1.4. Configure site

Change the file `sites-available/000-default.conf` to look like this

```
<VirtualHost *:80>
	ServerAdmin <YOUR@EMAIL.HERE>
	DocumentRoot /path/to/parent/folder

	RedirectMatch permanent "^/$" "http://<IP-ADDRESS>/sebastian_web_gui/index.html"

	ErrorLog ${APACHE_LOG_DIR}/error.log
	CustomLog ${APACHE_LOG_DIR}/access.log combined
</VirtualHost>
```

taking care to replace the placeholders with the actual values, and `/path/to/parent/folder`
with the path to the folder used in section 1.

## 2. How to build the pre-made docker image

### 2.1 Basic procedure 

In the root folder of the repo (default: sebastian) run

```
./make-docker-container.sh
```

That's all!

## 2.2 Testing container

If you'd rather have a bigger container including Sebastian core too, run

```
./make-docker-container.sh TEST
```

Please notice that this does not lead to a functioning installation, as Sebastian is run with the "LOGGING" NFVO.
If you have an NFVO running, you can deploy Sebastian in the container by editing the `application.properties` file
in the `docker/test` folder and rebuilding the container:

```
docker image rm -f nextworks/sebastian-tests # if necessary
./make-docker-container.sh TEST 
```

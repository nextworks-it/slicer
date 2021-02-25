Edit configuration: slicer/src/app/environments/environments.ts


curl -o- https://raw.githubusercontent.com/nvm-sh/nvm/v0.37.2/install.sh | bash

source /home/<user>/.bashrc
nvm install 12

nvm use 12

- Now angular cli has to be installed by :
cd slicer 
sudo npm install

sudo npm install -g @angular/cli

ng build --commonChunk=true --namedChunks=true --optimization=true –sourceMap=false


-  For testing purposes: 
ng serve


-  For production use: copy the “dist” folder inside the apache/nginx site folder


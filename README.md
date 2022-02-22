## Installation
First, download this repository and run

`mvn install`

Then make sure you have docker installed. After that go to the root directory of this project and run

`docker volume create --name=parser_db`

Now everytime you want to start this project you will need to run

`docker-compose up`

from the root directory of this project.

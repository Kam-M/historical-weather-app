# HISTORICAL WEATHER DATA APPLICATION

## How to run the app

1. Setup a MySQL database on yor machine - I have used Docker to achieve that.
   ```
   sudo docker pull mysql
   sudo docker run --detach --name=mysql.1 -p 52000:3306  --env="MYSQL_ROOT_PASSWORD=mypassword" mysql
2. Create a database with name:
   ```
   requests
   ```
  I use DBeaver Community Edition - it also allowes me to easily track changes when the app works.
  ```
  https://dbeaver.io/download/
  ```
  
3. Go to 'target' directory and type:
  ```
  java -jar weather-app-0.0.1-SNAPSHOT.jar

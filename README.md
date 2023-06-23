# HISTORICAL WEATHER DATA APPLICATION

## How to run the app

1. Setup a MySQL database on yor machine - I have used Docker to achieve that.
```
   sudo docker pull mysql
   sudo docker run --detach --name=mysql.1 -p 52000:3306  --env="MYSQL_ROOT_PASSWORD=mypassword" mysql
```

2. Create a database with name:
```
   requests
```
   I use DBeaver Community Edition - it also allowes me to easily track changes when the app works.

3. Go to 'target' directory and type:
```
   java -jar weather-app-0.0.1-SNAPSHOT.jar
```

4. Go to browser and type e.g. :
```
   http://localhost:8200/weather/historical?longitude=20.514860&latitude=44.969601
```

5. If everything works you should receive e.g. :
```
[{"date":"2023-06-15","precipitationAvg":0.75,"sunrise":"02:48:00","sunset":"18:27:00"},
{"date":"2023-06-16","precipitationAvg":0.61,"sunrise":"02:48:00","sunset":"18:28:00"},
{"date":"2023-06-17","precipitationAvg":0.1,"sunrise":"02:48:00","sunset":"18:28:00"},
{"date":"2023-06-18","precipitationAvg":0.0,"sunrise":"02:49:00","sunset":"18:28:00"},
{"date":"2023-06-19","precipitationAvg":0.0,"sunrise":"02:49:00","sunset":"18:29:00"},
{"date":"2023-06-20","precipitationAvg":0.0,"sunrise":"02:49:00","sunset":"18:29:00"},
{"date":"2023-06-21","precipitationAvg":0.0,"sunrise":"02:49:00","sunset":"18:29:00"},
{"date":"2023-06-22","precipitationAvg":0.0,"sunrise":"02:49:00","sunset":"18:29:00"}]
```

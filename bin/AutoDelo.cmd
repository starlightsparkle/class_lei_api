cd ../ && mvn clean package && scp ./target/class_lei-0.0.1-SNAPSHOT.jar  root@139.9.89.11:/home/class_lei  && ssh root@139.9.89.11 "sh /home/class_lei/restart.sh " || TIMEOUT /T 10

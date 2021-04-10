# keycloak-kafka-event-listener
Custom event listener to publish all Keycloak events to Kafka 

## How to build
```
./mvnw clean install
```

## How to manually install plugin into Keycloak
```
cp keycloak-kafka-event-listener.jar /keycloak-x.x.x/standalone/deployments
```

## How to test locally
1. Compile solution `./mvnw clean install`
2. Start Kafka and Keycloak
 - It should copy the project jar into deployments folder
 - It should copy the standalone.xml file into configuration folder
3. Enable the plugin in Keycloak
    - Go to http://localhost:8080
    - Log in (credentials in docker-compose.yml)
    - Select the realm and go to Events
    - In config tab, add kafka-event-listener to Event Listeners
    - Save

At this point, Keycloak should publish any event happening internally into Kafka. To verify, you can listen to 
configured topics:

To see standard events
```
kafkacat -b localhost:9094 -t sample-keycloak-event -C -o beginning 
```

To see admin events
```
kafkacat -b localhost:9094 -t sample-keycloak-admin-event -C -o beginning
```
 
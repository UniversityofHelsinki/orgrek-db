
#### Lisää projektiin application-local.properties tiedosto src/main/resources hakemiston alle (tiedostoa ei saa lisätä versionhallintaan)

spring.datasource.url=xxxxxxxxx
spring.datasource.password=xxxxxxxxx
spring.datasource.username=xxxxxxxxx
server.port=xxxx

Katso arvot keepassistä.

Portti, johon haluat db projektin käynnistyvän alustetaan ympäristömuuttujassa nimeltä port

Lisää Intellij Idean Spring Boot konfiguraatioon tieto, että haluat paikallisesti ajaa Spring sovellusta lokaalilla profiililla
Edit Configurations -> Spring Boot Configuration -> VM Options kohtaan -Dspring.profiles.active=local

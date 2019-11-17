## Cashmanager

To compile the server:
	-	cd server/
	-	mvn package

To run it:
	-	mvn spring-boot:run

To watch the H2 database:
	-	http://localhost:8080/h2-console/
	-	In the field URL replace by:	jdbc:h2:mem:testdb

Praktisches Projekt für das Modul Datenbanken und Anwendungsentwicklung
von 

Christopher Kremkow, Friedrich Gabriel Middendorf und Antek Rühmigen

Teil 3:
-
Objekt-relationales Mapping:

- Die zugehörigen Models sind zu finden unter: "src/main/java/models/modelsHibernate"


Applikation
- Die beiden APIs sind unter "src/main/java/api" zu finden, jede Api inklusive Impl besitzt ein eigenes Package
- Die Funktionalitäten der jeweiligen Apis werden mit Hilfe eines jeweiligen Controllers delegiert, 
  welche zu finden sind unter: "src/main/java/anwendung/controller"
- Die StoredProcedure für die Api Methode "getShorthestFriendshipPath" ist
  unter "src/main/SqlSkripte/storedProcedure.sql" zu finden
- Das Konsolenprogramm lässt sich starten über: "src/main/java/anwendung/main.java"


Teil 2:
-
- Die Bearbeitung der Aufgaben a)-c) ist unter "src/main/SqlSkripte/aufgabeZwei.sql" zu finden.



Teil 1:
-
- Die Bearbeitung der Aufgaben a) und b) ist unter 
"social_network/src/main/Teil 1.pdf" zu finden.

- Das SQL-DDL-Skript für die Aufgabe c) ist unter 
"src/main/SqlSkripte/init.sql" zu finden.

- Der Datenimport aus Aufgabe d) geschieht durch Ausführen der 
social_network/src/main/java/datenbankImport.Main.java.
Das Ausführen der datenbankImport.Main.java führt zuerst das init.sql Skript aus
und importiert anschließend alle csv Datein in korrekter Reihenfolge.
Hierbei ist zu beachten, dass in der datenbankImport.Main.java vor dem Ausführen noch die Variable
"csvDir" korrekt gesetzt werden muss. Diese verweist auf den Pfad des Ordners, welcher
die csv Datein enthält(Der Ordner der social_network.zip Datei).



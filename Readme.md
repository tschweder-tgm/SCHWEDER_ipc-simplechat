# Dokumentation für IPC-Simplechat
## Aufgabenstellung
Es soll ein Simplechat (mit einem Server und min. 2 Client Threads) programmiert werden.
<br>
Das Programm ist in Java umzusetzten und die GUI soll von dem QTDesigner übernommen werden.

## Lösung
### UI zu JUI
Zuerst haben wir die UI-Files aus dem QTDesigner mittels QTJambi-Designer-Converter ("juic.bat").
<br>
Weiters haben wir die Java-Libary QTJambi zu unserem Projekt hinzugefügt.

### Server
#### Class Server
##### Konstruktor
##### Broadcast (Synchronised)
##### Remove (Synchronised)
##### Stop*
#### Class ClientThread (extends Thread)
##### Konstruktor
##### Run
##### Close
##### WriteMsg
### Client
#### Class Client
##### Konstruktor
##### Start
##### SendMessage
##### (Disconnect)
#### Class Listner (extends Thread)
Hat nur die Run Methode.
<br>
Diese aktualisiert in einer Dauerschleife den Chat, damit alle neuen Nachrichten auf den Clients angezeigt werden.
### ServerController
#### Class ServerController
Hat nur die Main Methode.
<br>
Welche einen Server, mit dem Port 5050 und der UI von Ui_ChatServer, mittels der Klasse Server erstellt und startet, mithilfe der Klasse ServerRun.
### ClientController
#### Class ClientController
Hat nur die Main Methode.
<br>
Welche einen Client, mit den Verbindungsdaten zu dem Server (port:5050, host:"localhost") und der UI von Ui_ChatClient, mittels der Klasse Client und startet.


## Glossar
< methode >* ... protected
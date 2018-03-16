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
### ServerController
### ClientController

## Glossar
< methode >* ... protected
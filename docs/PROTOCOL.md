# **Tourplanner Protocol**

## **Links**
[GitHub](https://github.com/Teezious/tourplanner)

## **Timetable**


| Date       | Time |
| :--------- | ---: |
| 07.03.2021 | 3h   |
| 03.05.2021 | 3h   |
| 18.05.2021 | 5h   |
| 19.05.2021 | 2h   |
| 23.05.2021 | 4h   |
| 25.05.2021 | 3h   |
| 26.05.2021 | 9h   |
| 27.05.2021 | 6h   |
| 28.05.2021 | 15h  |
| 01.06.2021 | 2h   |
| 02.06.2021 | 2h   |
| 03.06.2021 | 4h   |
| 04.06.2021 | 6h   |
| 05.06.2021 | 5h   |
| 06.05.2021 | 10h  |
| 07.06.2021 | 4h   |
| Total:     | 83h  |

---
## **Technical Steps**


- 07.03.2021
  - Probblems:
    - including JavaFX and getting it to run
  - Tasks: 
    - reading and highlighting specification
    - creating git
    - creating of protocol template
    - Hello World Java FX test

- 03.05.2021
  - Problems: 
    - struggling getting application to compile
  - Tasks:
    - started actual coding based on JavaFX intro

- 18.05.2021
  - Problems:
    - Application could not find resource folder and thus could not open fxml file
    - Bug of items not being displayed in Listview
  - Tasks:
    - fixed resource file problem (define resources in pom)
    - fixed items not being displayed in Listview
    - added window for adding tours
- 19.05.2021
  - Problems:
    - Problem with path to DBConfig file and and DBCreateStatements file
  - Tasks:
    - fixed both path problems
    - added db connection
    - added db config reader
    - added db sql script reader
- 23.05.2021
  - Problems:
    - /
  - Tasks:
    - added tourhandler
    - ability to add, edit and delete tours
    - saving tours in database
- 23.05.2021
  - Problems:
    - /
  - Tasks:
    - edited mainWindow
    - added tourInfos.fxml
    - added LogItem
    - added TourInfosController
    - edited create_tables.sql to add Logs entry
- 25.05.2021
  - Problems:
    - /
  - Tasks:
    - added loghandler
    - added Activities
    - added Weather
    - added likely temporary ControllerViewModel
    - added AddLogController
    - added addLog.fxml
    - added editLog.fxml
    - edited TourInfosController
- 26.05.2021
  - Problems:
    - Nullpointer Exception when receiving LogList
    - Type problems with spinners
  - Tasks:
    - fixed Nullpointerxception when receiving LogList (due to not instantiating loghandler *facepalm*)
    - fixed type problems with spinners
    - correct displaying of Logs
    - correct displaying of tourdetails
    - added EditLogController
    - added Constructors to LogItem
    - added ability do add logs
    - added ability do delete logs
    - added ability do edit logs
- 27.05.2021
  - Problems:
    - Creating img only possible with absolute path right now
  - Tasks:
    - added APIcomm
    - added Maphandler
    - added RoutItem
    - ability to automatically add Images when adding Tour
    - ability to automatically remove associated images when removing Tour
- 27.05.2021
  - Problems:
    - Controller alway null for some reason (took several hours)
    - Problem setting up observers and updating data (took several hours)
  - Tasks:
    - solved problems
    - updated project structure
    - introduced viemodels and split controllers from viewmodels
    - new fxml files (as this was somehow the only way to solve the controller problem) -> still WIP
    - seperated BL and DAL more strict
    - removed unneccessary import
    - removed unneccessary files
- 01.06.2021
  - Problems:
    - Setting up log4j config file
  - Tasks:
    - Setting up log4j config file
    - getting logger to work
    - writing first logs
- 02.06.2021
  - Problems:
    - /
  - Tasks:
    - filling project with logs
    - writing logs for most of the classes
    - small refactoring
    - making DB a singleton
- 03.06.2021
  - Problems:
    - massive bug when trying to get DB connection
    - Nullpointer Exception in Database class due to path failing in static class
  - Tasks:
    - writing last loggers
    - bug fixing
    - new paths.xml file
    - paths are now read from paths.xml file
    - fixing DB bug
- 04.06.2021
  - Problems:
    - getting started with itextpdf and jfreechart library proved to be difficult
  - Tasks:
    - added Buttons to export, import, report and UNIQUE FEATURE (favorite toure)
    - added functionality to file report
    - added Reporthandler
    - added Charthandler
    - Other Minor changes
- 05.06.2021
  - Problems:
    - problems with image path
  - Tasks:
    - added feature to add a favorite tour
    - favorite tour is differently colored in the listview
    - started adding first unit tests
- 06.06.2021
  - Problems:
    - new problems with image path
  - Tasks:
    - added more unit tests
    - slight structural changes
    - when start and endpoint change old map image is now being deleted properlyk
    - changed image handling to absolute pathe (ugly but the only way i got it to work)
    - added export
    - added import
    - added new controller for import export
    - added new functions in filehandler for import export
    - added new fxml for import export
    - simplified fxml -> GUI now adaptive to size change
    - created summary report option
    - simplified main controller
    - path for reports can now be specified
- 07.06.2021
  - Problems:
    - /
  - Tasks:
    - fusioned Edit- and Add- LogController/TourController and corresponding Viewmodel to one
    - wrote protocol
    - added comments
---

## **Design Choices**
---
### **Layers**
 The Program is split into 4 big parts: 
  - Business Layer (BL)
  - Data Layer (DL)
  - models
  - GUI
    - viewmodels 
    - views 
    - (fxml files)
---

### **Business Layer**

Der Business Layer besteht aus 5 Klassen:
  - Tourhandler
  - Charthandler
  - Maphandler
  - Loghandler
  - Reporthandler

Die zwei wichtigsten sind dabei der Tourhandler und der Loghandler. Sie sind dafür zuständige mit dem Data Layer (DL) zu Kommunizieren und zwar immer dann wenn eine der vier grundlegenden Operationen CRUD für die Tours oder Logs benötigt wird. 
Der Maphandler ist dafür zuständing URLs für die HTTP Requests an Mapquest zu kreieren und diese an die APIcomm (DL) weiterzuleiten. Die erhaltenen Bilder werden dabei auch gleich gespeichert. Der Reporthandler ist für das erstellen des Reports und des Summary Reports zuständig. Als PDF Library habe ich mich dabei für die gratis PDF Library IText7 entschieden. 
Der Charthandler kreiert aus den LogDaten zusätzliche Graphiken die dann in die Summary Reports eingebaut werden. Hier habe ich mich für die Library JFreeCharts entschieden.


---

### **Data Layer**
Der Data Layer besteht aus 5 Klassen:
  - APIcomm
  - Database
    - Tourhelper
    - Loghelper
  - Filehandler
  - XMLreader

Die APIcomm ist wie oben schon erwähnt für die Durchführung der Mapquest Request URLs zuständig. Dabei wird die Standard java HTTP Library verwendet. Zusätzlich wird die Jackson Library verwendet um die Antworten im Json Format zu deserialisieren und im model RouteItem zu verwenden.

Die Database Klasse implementiert die grundlegenden Database Funktionen: Initialisierung der Verbindung, Tables kreiieren, Connection Getter und Connection schließen. Dabei wird ein Singleton Pattern angewandt. Die Connection wird immer nur einmal erstellt.

Die Klassen Loghelper und Tourhelper erben von der Database und könne somit leicht auf die einmalig erstellte Connection zugreifen. Sie sind dafür zuständig die jeweiligen Befehle vom Loghandler und Tourhandler zu übernehmen und die geforderten SQL Statements durchzuführen.

Zu guter Letzt gibt es noch den Filehandler und den XMLReader die sich ein wenig überschneiden und womöglich zu einer Klasse fusioniert werden hätten können. Der Filehandler wird aufgerufen wenn eine Tour und somit ein Bild gelöscht werden muss, sowie am Start des Programmes wenn die Database initialisiert wird und die jeweiligen SQL_Files gelesen werden. Zusätzlich können Tours importiert und exportiert werden. Hier wird also wieder die Jackson Library verwendet. 

Der XMLReader ist wie der Name schon sagt dafür zuständig XML Files zu lesen und auf einzelne Element der Files zuzugreifen. Der XMLReader wird daher verwendet um den Datenbank Access zu lesen, den Mapquest Accesss zu lesen und um an die benötigten Paths während des Programmflusses zu kommen.


---


### **Models**

Es gibt 3 models:
  - LogItem
  - TourItem
  - RouteItem

Sie halten jeweils nur die benötigten Daten und haben Lombok Getter und Setter. LogItem und RouteItem werden währen des Ganzen Programmes benötigt, während das TourItem quasi nur bei den MapQuest Requests benötigt wird.


---

### **Views**

Für alle fxml Files:
  - mainWindow.fxml
  - tourInfos.fxml
  - tourOverview.fxml
  - path.fxml
  - log.fxml
  - add.fxml

gibt es je einen Controller. TourInfos.fxml tourOverview.fxml werden in das MainWindow eingebunden. Der MainController hat aber mit der Menubar auch eigene Aufgaben nämlich das Importieren, Exportieren und Report generieren. 
Die TourOverview hält die Listview mit allen Tours und die Buttons zum hinzufügen, editieren, entfernen sowie zu Favoriten hinzufügen (Unique Feature).
Die TourInfos.fxml hält die Searchbar mit Such und Clear Button. Zusätzlich befindet sich hier eine TabPane mit 3 Tabs:
  - Route (Mapquest Bild)
  - Tour Infos (Distanz etc.)
  - Logs (LogTable mit allen Logs sowie CUD Buttons für die Logs)
LogController und TourController sind jeweils Forms und neue Windows die aufgerufen werden wenn eine Tour oder ein Log kreiiert oder editiert werden soll. Editieren und Hinzufügen ha design


---


###  **Viewmodels**

Für jeden Controller gibt es auch ein ViewModel hier werden die Daten für den jeweiligen Controller gehalten. Zusätzlich sitzen hier die Funktionen für Kommunikation mit anderen Layers. Um die Daten der Controller und Viewmodel untereinander zu synchronisieren wurde ein Observerpatten angewandt. Dabei wird zu Programm Start festgelegt wer welche Daten beobachten möchte (etwa TourOverview die Searchbar in TourInfos). Im Programmfluss werden dann alle Beobachter aus der Liste benachrichtigt wenn sich ein Datensatz ändert. Der Datensatz wird dabei auch gleich mitgeschickt.


---

### **Unique Feature**

Als Unique Feature kann der User in der Tourliste mit dem Button (#) eine Tour zu seinen Favoriten hinzufügen. Die Favoriten werden in der Liste anders eingefärbt und stehen immer ganz Oben.


---


### **Testing Decisions**

Beim Testen habe ich micht hauptsächlich, auch weil es am leichtesten zu testen ist, für alle Operationen die mit CRUD von LogItems und TourItems entschieden. Getestet wird also hauptsächlich ob die Einträge korrekt in der Datenbank landen und ob die Einträge dann auch wieder korrekt gelesen werden. Zusätzlich wird geschaut ob alles richtig gelöscht wird (auch die zu den Touren gehörigen Bilder).


---


### **Failures and Lessons learned**

Wie so oft habe ich das Anfangen mit dem Projekt ewig lange aufgeschoben, was darin resultierte, dass ich quasi ein ganzes Projekt in ein bisschen mehr als zwei Wochen schreiben musste. Darunter leide nicht nur ich sondern auch die Qualität des Projekts. Weil einfach weniger Zeit ist sich Gedanken über den Projektaufbau, das Implementieren und das Gelernte zu machen. Da ich nun so wenig Zeit habe, meinte ich es würde sich nicht lohnen einen Plan zu machen wie das Projekt zu strukturieren ist -> **WRONG** lohnt sich immer!. Durch das Fehlen eines Planes musste ich das Projekt einige Male restrukturieren und braucht schlussendlich noch mehr Zeit. 

Abgesehen vom üblichen Prokrastinationsdilemma hab ich in diesem Projekt viel mitgenommen. Zum einem war es cool einen ersten Einblick in die GUI Programmierung zu erhalten und zu sehen wie das fusionieren von der üblichen Programmlogik und der GUI funktioniert. Das lernen vom MVVM Design Patterns war zunächst ein wenig verwirrend, macht nachdem man es implementiert hat durchaus Sinn. Auf jeden Fall ist es gut zu Wissen sich in Zukunft nicht mehr mit reinen Konsolen Applikationen zufrieden geben zu müssen.

Auch die generelle Aufteilung von Projekten in verschiedene Layers ist eine nützliche Information die ich auch in zukünftigen Projekten anwenden kann und werde.
Zusätzlich wusste ich zwar schon immer dass die Verwendung von Loggern gegenüber dem STDOUT zu empfehlen ist, doch hätte ich es sicher noch länger herausgeschoben wenn ich in diesem Projekt nicht zu der Verwendung gezwungen worden wäre.


---



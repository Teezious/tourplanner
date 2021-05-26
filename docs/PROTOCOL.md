# Tourplanner Protocol

### Links
[GitHub](https://github.com/Teezious/tourplanner)

### Timetable


| Date       | Time |
| :--------- | ---: |
| 07.03.2021 | 3h   |
| 03.05.2021 | 3h   |
| 18.05.2021 | 5h   |
| 19.05.2021 | 2h   |
| 23.05.2021 | 4h   |
| 25.05.2021 | 3h   |
| 26.05.2021 | 9h   |


### Technical Steps


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

 
### Design Choices



### Failures and Lessons learned


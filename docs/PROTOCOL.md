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
| 27.05.2021 | 6h   |
| 28.05.2021 | 15h  |
| 01.06.2021 | 2h   |
| 02.06.2021 | 2h   |
| 03.06.2021 | 4h   |
| 04.06.2021 | 6h   |
| 05.06.2021 | 3h   |


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
   



 
### Design Choices



### Failures and Lessons learned


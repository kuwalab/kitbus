appView = new App.AppView(
  el: '#main'
)

timetableListView = new App.TimetableListView(
  el: '#timetable'
  collection: App.timetableList
)

App.rideBusView = new App.RideBusView(
  el: '#rideBus'
)

appView.render()


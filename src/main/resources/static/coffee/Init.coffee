appView = new App.AppView({
  el: '#main'
})

timetableListView = new App.TimetableListView({
  el: '#timetable'
  collection: App.timetableList
})

appView.render()


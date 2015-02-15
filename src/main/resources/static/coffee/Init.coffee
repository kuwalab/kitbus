appView = new App.AppView({
  el: '#main'
})

timetableListView = new App.TimetableListView({
  el: '#timetable'
  collection: App.TimetableList
})

appView.render()


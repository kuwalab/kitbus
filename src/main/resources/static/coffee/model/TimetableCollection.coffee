class TimetableCollection extends Backbone.Collection
  model: App.TimetableModel

App.TimetableCollection = TimetableCollection

App.timetableCollection = new TimetableCollection()
class TimetableModel extends Backbone.Model
  @SHUTTLE_OUTWARD: 'OUTWARD'
  @SHUTTLE_HOMEWARD: 'HOMEWARD'

  default: {
    'building1': ''
    'building74': ''
    'building61': ''
    'building65': ''
  }
  
  @setTime: (shuttle, timeArray) ->
    if shuttle is TimetableModel.SHUTTLE_OUTWARD
      return new TimetableModel({
        building1: timeArray[0]
        building74: timeArray[1]
        building61: timeArray[2]
        building65: timeArray[3]
      })
    else
      return new TimetableModel({
        building1: timeArray[3]
        building74: timeArray[2]
        building61: timeArray[1]
        building65: timeArray[0]
      })

App.TimetableModel = TimetableModel

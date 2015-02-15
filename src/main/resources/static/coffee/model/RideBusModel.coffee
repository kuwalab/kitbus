class RideBusModel extends Backbone.Model
  default: {
    departureTime: ''
    beforeAlert: ''
  }

App.RideBusModel = RideBusModel

App.rideBusModel = new App.RideBusModel()

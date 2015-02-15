class RideBusModel extends Backbone.Model
  defaults: {
    departureTime: ''
    beforeAlert: '10'
  }

App.RideBusModel = RideBusModel

App.rideBusModel = new App.RideBusModel()

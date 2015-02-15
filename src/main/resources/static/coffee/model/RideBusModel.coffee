class RideBusModel extends Backbone.Model
  defaults: {
    departureTime: ''
  }

App.RideBusModel = RideBusModel

App.rideBusModel = new App.RideBusModel()

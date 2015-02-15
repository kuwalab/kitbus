class RideBusView extends Backbone.View
  model: App.rideBusView
  render: ->
    @

App.RideBusView = RideBusView

App.rideBusView = new RideBusView(
  el: '#rideBus'
)
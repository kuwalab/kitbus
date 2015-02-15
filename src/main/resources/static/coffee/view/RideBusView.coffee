class RideBusView extends Backbone.View
  model: App.rideBusModel

  initialize: ->
    RideBusView.departureTimeTmpl = _.template($('#departureTimeTmpl').html())
    _.bindAll @, 'onChange'
    @model.on 'change', @onChange
    @$('#beforeAlert').val(@model.get('beforeAlert'))
    do @render

  render: ->
    @$('#departureTime').html RideBusView.departureTimeTmpl(@model.toJSON())
    @

  onChange: ->
    do @render
    departureTime = @model.get('departureTime')
    return if departureTime is ''
    beforeAlert = parseInt $('#beforeAlert').val(), 10
    return if beforeAlert is NaN
    @model.set('beforeAlert', beforeAlert)

    Notification.requestPermission (selectedPermission) ->
      permission = selectedPermission

    date = new Date()
    nowHour = date.getHours()
    nowMinute = date.getMinutes()
    nowSecond = date.getSeconds()
    targetTime = departureTime.split(':')
    targetSecond = (((targetTime[0] - nowHour) * 60 + targetTime[1] - nowMinute - beforeAlert) * 60 - nowSecond) * 1000
    console.log new Date(), targetSecond
    return if targetSecond <= 0
    setTimeout( ->
      notify = new Notification('バスが来ます', { tag: 'tag', body: '通知の本文', icon: 'icon.png' })
    , targetSecond)

App.RideBusView = RideBusView

class RideBusView extends Backbone.View
  model: App.rideBusModel
  
  events:
    'change #beforeAlert': 'onChangeBeforeAlert'

  initialize: ->
    RideBusView.departureTimeTmpl = _.template($('#departureTimeTmpl').html())
    _.bindAll @, 'onChange'
    @model.on 'change', @onChange
    @$('#beforeAlert').val(@model.get('beforeAlert'))
    do @render

  render: ->
    @$('#departureTime').html RideBusView.departureTimeTmpl(@model.toJSON())
    @

  setTimer: ->
    departureTime = @model.get 'departureTime'
    return if departureTime is ''
    beforeAlert = parseInt $('#beforeAlert').val(), 10
    return if isNaN beforeAlert

    Notification.requestPermission (selectedPermission) ->
      permission = selectedPermission

    date = new Date()
    nowHour = date.getHours()
    nowMinute = date.getMinutes()
    nowSecond = date.getSeconds()
    targetTime = departureTime.split ':'
    for value, i in targetTime
      targetTime[i] = parseInt value, 10
    targetSecond = (((targetTime[0] - nowHour) * 60 + targetTime[1] - nowMinute - beforeAlert) * 60 - nowSecond) * 1000
    return if targetSecond <= 0
    if @timer then clearTimeout @timer
    @timer = setTimeout( ->
      notify = new Notification('バスが来ます', { tag: 'tag', body: '通知の本文', icon: 'icon.png' })
    , targetSecond)


  onChangeBeforeAlert: ->
    do @setTimer

  onChange: ->
    do @render
    do @setTimer

App.RideBusView = RideBusView

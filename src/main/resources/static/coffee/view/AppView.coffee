class AppView extends Backbone.View
  events:
    'change input[name="shuttle"]': 'onChangeShuttle'

  initialize: ->
    if !AppView.initViewTmpl
      AppView.initViewTmpl = _.template($('#initView').html())
    do @initView
    return

  render: ->
    @

  onChangeShuttle: ->
    shuttleChecked = @$('input[name="shuttle"]:checked').val()
    
    do @render

  initView: ->
    $.ajax(
      url: '/api/servicetable'
      cache: false
      dataType: 'json'
    ).then((data) =>
      # 運行の曜日を取得
      @serviceDay = getServiceDay(data)
      $.ajax(
        url: '/api/timetable'
        cache: false
        dataType: 'json'
      )
    ).then((data) =>
      @setTimetable(data)
    )
    return

  setTimetable: (timetableData) ->
    console.log(timetableData)
    for timetable in timetableData.timetableList
      console.log(timetable)
    return

dateFormat = (targetDate) ->
  year = targetDate.getFullYear()
  month = targetDate.getMonth() + 1
  date = targetDate.getDate()

  month = if month < 10 then '0' + month else month
  date = if date < 10 then '0' + date else date

  "#{year}/#{month}/#{date}"

getServiceDay = (servicetable) ->
  date = new Date()
  serviceDay = servicetable.serviceMap[dateFormat(date)]
  return serviceDay if serviceDay
  return 'SUNDAY' if date.getDay() is 0
  return 'SATURDAY' if date.getDay() is 6
  'WEEKDAY'

App.AppView = AppView

class AppView extends Backbone.View
  events:
    'change input[name="shuttle"]': 'onChangeShuttle'

  initialize: ->
    if !AppView.initViewTmpl
      AppView.initViewTmpl = _.template($('#initView').html())
    do @initView

  render: ->
    @

  onChangeShuttle: ->
    console.log('click')

  initView: ->
    $.ajax(
      url: '/api/servicetable'
      cache: false
      dataType: 'json'
    ).then((data) ->
      # 運行の曜日を取得
      @serviceDay = getServiceDay(data)
      console.log(@serviceDay)
      $.ajax(
        url: '/api/timetable'
        cache: false
        dataType: 'json'
      )
    ).then((data) ->
      timetable = data
    )

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

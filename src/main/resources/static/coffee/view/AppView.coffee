class AppView extends Backbone.View
  events:
    'change input[name="shuttle"]': 'onChangeShuttle'
    'click #notifTestButton': 'onClickNotifTest'

  initialize: ->
    Notification.requestPermission (selectedPermission) ->
      permission = selectedPermission
    @$time = @$('#time')
    do @intervalTime
    do @initView
    return

  intervalTime: ->
    do @updateTime
    setInterval =>
      do @updateTime
    , 1000

  updateTime: ->
    date = new Date()
    minute = date.getMinutes()
    dispTime = date.getHours() + ":" + if minute < 10 then '0' + minute else minute
    @$time.text(dispTime)
    return

  render: ->
    @

  onChangeShuttle: ->
    shuttleChecked = @$('input[name="shuttle"]:checked').val()
    # なにかいい方法がないか。。
    App.timetableList.shuttle = shuttleChecked
    App.timetableList.reset @timetableArray[shuttleChecked]
    do @render

  onClickNotifTest: ->
    notify = new Notification('通知のテスト', { tag: 'tag', body: 'これが出ていれば、通知のテストはOKです', icon: 'image/bus.png' })

  initView: ->
    # 日付の表示
    date = new Date()
    $('#date').text("#{date.getMonth() + 1}月#{date.getDate()}日")
    $.ajax(
      url: '/api/servicetable'
      cache: false
      dataType: 'json'
    ).then((data) =>
      # 運行の曜日を取得
      @serviceDay = getServiceDay data
      
      serviceName =
        WEEKDAY: '平日'
        SATURDAY: '土曜'
        SUNDAY: '運行なし'
      @$('#service').text(serviceName[@serviceDay])
      $.ajax(
        url: '/api/timetable'
        cache: false
        dataType: 'json'
      )
    ).then((data) =>
      @setTimetable data
      @$('input[name="shuttle"]:eq(0)').trigger('click');
    )
    return

  setTimetable: (timetableData) ->
    @timetableArray = {
      outward: []
      homeward: []
    }
    for timetable in timetableData.timetableList
      if timetable.serviceDay is @serviceDay
        for timeArray in timetable.busStopTimeList
          @timetableArray[timetable.shuttle.toLowerCase()].push(
            App.TimetableModel.setTime timetable.shuttle, timeArray
          )
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

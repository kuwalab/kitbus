class TimetableListView extends Backbone.View
  events:
    'click tbody td': 'onClickTime'

  initialize: (options) ->
    TimetableListView.timetableTmpl = _.template($('#timetableTmpl').html())
    TimetableListView.timetableHeaderOutwardTmpl = _.template($('#timetableHeaderOutwardTmpl').html())
    TimetableListView.timetableHeaderHomewardTmpl = _.template($('#timetableHeaderHomewardTmpl').html())
    TimetableListView.timetableRowOutwardTmpl = _.template($('#timetableRowOutwardTmpl').html())
    TimetableListView.timetableRowHomewardTmpl = _.template($('#timetableRowHomewardTmpl').html())

    _.bindAll @, 'onReset'
    @collection = options.collection
    @collection.on 'reset', @onReset

  render: ->
    @$el.html(TimetableListView.timetableTmpl())
    $thead = @$ 'thead'
    $tbody = @$ 'tbody'
    if @collection.shuttle is 'outward'
      $thead.html(TimetableListView.timetableHeaderOutwardTmpl())
      @collection.each((timetableModel) ->
        $tbody.append(TimetableListView.timetableRowOutwardTmpl(timetableModel.toJSON()))
      )
    else
      $thead.html(TimetableListView.timetableHeaderHomewardTmpl())
      @collection.each((timetableModel) ->
        $tbody.append(TimetableListView.timetableRowHomewardTmpl(timetableModel.toJSON()))
      )
    @
  
  onReset: ->
    do @render

  onClickTime: (e) ->
    $td = $ e.currentTarget
    index = $td.parent('tr').find('td').index($td)
    return if @collection.shuttle is 'outward' and index >= 1
    return if @collection.shuttle is 'homeward' and index >= 3
    App.rideBusModel.set 'departureTime', $td.text()

App.TimetableListView = TimetableListView

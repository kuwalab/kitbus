class TimetableListView extends Backbone.View
  initialize: (options) ->
    if !TimetableListView.timetableTmpl
      TimetableListView.timetableTmpl = _.template($('#timetableTmpl').html())
    if !TimetableListView.timetableRowOutwardTmpl
      TimetableListView.timetableRowOutwardTmpl = _.template($('#timetableRowOutwardTmpl').html())
    if !TimetableListView.timetableRowHomewardTmpl
      TimetableListView.timetableRowHomewardTmpl = _.template($('#timetableRowHomewardTmpl').html())

    _.bindAll @, 'onReset'
    @collection = options.collection
    @collection.on('reset', @onReset)

  render: ->
    @$el.html(TimetableListView.timetableTmpl())
    $tbody = @$('tbody')
    if @collection.shuttle is 'outward'
      @collection.each((timetableModel) ->
        $tbody.append(TimetableListView.timetableRowOutwardTmpl(timetableModel.toJSON()))
      )
    else
      @collection.each((timetableModel) ->
        $tbody.append(TimetableListView.timetableRowHomewardTmpl(timetableModel.toJSON()))
      )
    @
  
  onReset: ->
    do @render

App.TimetableListView = TimetableListView

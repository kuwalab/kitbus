(function() {
  this.App = {};

}).call(this);

(function() {
  var TimetableCollection,
    __hasProp = {}.hasOwnProperty,
    __extends = function(child, parent) { for (var key in parent) { if (__hasProp.call(parent, key)) child[key] = parent[key]; } function ctor() { this.constructor = child; } ctor.prototype = parent.prototype; child.prototype = new ctor(); child.__super__ = parent.prototype; return child; };

  TimetableCollection = (function(_super) {
    __extends(TimetableCollection, _super);

    function TimetableCollection() {
      return TimetableCollection.__super__.constructor.apply(this, arguments);
    }

    TimetableCollection.prototype.model = App.TimetableModel;

    return TimetableCollection;

  })(Backbone.Collection);

  App.TimetableCollection = TimetableCollection;

}).call(this);

(function() {
  var TimetableModel,
    __hasProp = {}.hasOwnProperty,
    __extends = function(child, parent) { for (var key in parent) { if (__hasProp.call(parent, key)) child[key] = parent[key]; } function ctor() { this.constructor = child; } ctor.prototype = parent.prototype; child.prototype = new ctor(); child.__super__ = parent.prototype; return child; };

  TimetableModel = (function(_super) {
    __extends(TimetableModel, _super);

    function TimetableModel() {
      return TimetableModel.__super__.constructor.apply(this, arguments);
    }

    TimetableModel.prototype["default"] = {
      'building1': '',
      'building74': '',
      'building61': '',
      'building65': ''
    };

    return TimetableModel;

  })(Backbone.Model);

  App.TimetableModel = TimetableModel;

}).call(this);

(function() {
  var AppView, dateFormat, getServiceDay,
    __hasProp = {}.hasOwnProperty,
    __extends = function(child, parent) { for (var key in parent) { if (__hasProp.call(parent, key)) child[key] = parent[key]; } function ctor() { this.constructor = child; } ctor.prototype = parent.prototype; child.prototype = new ctor(); child.__super__ = parent.prototype; return child; };

  AppView = (function(_super) {
    __extends(AppView, _super);

    function AppView() {
      return AppView.__super__.constructor.apply(this, arguments);
    }

    AppView.prototype.events = {
      'change input[name="shuttle"]': 'onChangeShuttle'
    };

    AppView.prototype.initialize = function() {
      if (!AppView.initViewTmpl) {
        AppView.initViewTmpl = _.template($('#initView').html());
      }
      return this.initView();
    };

    AppView.prototype.render = function() {
      return this;
    };

    AppView.prototype.onChangeShuttle = function() {
      var shuttleChecked;
      shuttleChecked = this.$('input[name="shuttle"]:checked').val();
      console.log(shuttleChecked);
      return this.render();
    };

    AppView.prototype.initView = function() {
      return $.ajax({
        url: '/api/servicetable',
        cache: false,
        dataType: 'json'
      }).then(function(data) {
        this.serviceDay = getServiceDay(data);
        return $.ajax({
          url: '/api/timetable',
          cache: false,
          dataType: 'json'
        });
      }).then(function(data) {
        var timetable;
        return timetable = data;
      });
    };

    return AppView;

  })(Backbone.View);

  dateFormat = function(targetDate) {
    var date, month, year;
    year = targetDate.getFullYear();
    month = targetDate.getMonth() + 1;
    date = targetDate.getDate();
    month = month < 10 ? '0' + month : month;
    date = date < 10 ? '0' + date : date;
    return "" + year + "/" + month + "/" + date;
  };

  getServiceDay = function(servicetable) {
    var date, serviceDay;
    date = new Date();
    serviceDay = servicetable.serviceMap[dateFormat(date)];
    if (serviceDay) {
      return serviceDay;
    }
    if (date.getDay() === 0) {
      return 'SUNDAY';
    }
    if (date.getDay() === 6) {
      return 'SATURDAY';
    }
    return 'WEEKDAY';
  };

  App.AppView = AppView;

}).call(this);

(function() {
  var appView;

  appView = new App.AppView({
    el: '#main'
  });

  appView.render();

}).call(this);

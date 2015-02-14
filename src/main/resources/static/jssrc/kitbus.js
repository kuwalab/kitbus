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
  var dateFormat, getServiceDay, serviceDay;

  serviceDay = null;

  $.ajax({
    url: '/api/servicetable',
    cache: false,
    dataType: 'json'
  }).then(function(data) {
    serviceDay = getServiceDay(data);
    console.log(serviceDay);
    return $.ajax({
      url: '/api/timetable',
      cache: false,
      dataType: 'json'
    });
  }).then(function(data) {
    var timetable;
    return timetable = data;
  });

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
    var date;
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

}).call(this);

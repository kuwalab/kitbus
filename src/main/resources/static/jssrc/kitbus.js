(function() {
  this.App = {};

}).call(this);

(function() {
  var RideBusModel,
    __hasProp = {}.hasOwnProperty,
    __extends = function(child, parent) { for (var key in parent) { if (__hasProp.call(parent, key)) child[key] = parent[key]; } function ctor() { this.constructor = child; } ctor.prototype = parent.prototype; child.prototype = new ctor(); child.__super__ = parent.prototype; return child; };

  RideBusModel = (function(_super) {
    __extends(RideBusModel, _super);

    function RideBusModel() {
      return RideBusModel.__super__.constructor.apply(this, arguments);
    }

    RideBusModel.prototype.defaults = {
      departureTime: ''
    };

    return RideBusModel;

  })(Backbone.Model);

  App.RideBusModel = RideBusModel;

  App.rideBusModel = new App.RideBusModel();

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

  App.timetableList = new TimetableCollection();

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

    TimetableModel.SHUTTLE_OUTWARD = 'OUTWARD';

    TimetableModel.SHUTTLE_HOMEWARD = 'HOMEWARD';

    TimetableModel.prototype["default"] = {
      'building1': '',
      'building74': '',
      'building61': '',
      'building65': ''
    };

    TimetableModel.setTime = function(shuttle, timeArray) {
      if (shuttle === TimetableModel.SHUTTLE_OUTWARD) {
        return new TimetableModel({
          building1: timeArray[0],
          building74: timeArray[1],
          building61: timeArray[2],
          building65: timeArray[3]
        });
      } else {
        return new TimetableModel({
          building1: timeArray[3],
          building74: timeArray[2],
          building61: timeArray[1],
          building65: timeArray[0]
        });
      }
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
      'change input[name="shuttle"]': 'onChangeShuttle',
      'click #notifTestButton': 'onClickNotifTest'
    };

    AppView.prototype.initialize = function() {
      Notification.requestPermission(function(selectedPermission) {
        var permission;
        return permission = selectedPermission;
      });
      this.$time = this.$('#time');
      this.intervalTime();
      this.initView();
    };

    AppView.prototype.intervalTime = function() {
      this.updateTime();
      return setInterval((function(_this) {
        return function() {
          return _this.updateTime();
        };
      })(this), 1000);
    };

    AppView.prototype.updateTime = function() {
      var date, dispTime, minute;
      date = new Date();
      minute = date.getMinutes();
      dispTime = date.getHours() + ":" + (minute < 10 ? '0' + minute : minute);
      this.$time.text(dispTime);
    };

    AppView.prototype.render = function() {
      return this;
    };

    AppView.prototype.onChangeShuttle = function() {
      var shuttleChecked;
      shuttleChecked = this.$('input[name="shuttle"]:checked').val();
      App.timetableList.shuttle = shuttleChecked;
      App.timetableList.reset(this.timetableArray[shuttleChecked]);
      return this.render();
    };

    AppView.prototype.onClickNotifTest = function() {
      var notify;
      return notify = new Notification('通知のテスト', {
        tag: 'tag',
        body: 'これが出ていれば、通知のテストはOKです',
        icon: 'image/bus.png'
      });
    };

    AppView.prototype.initView = function() {
      var date;
      date = new Date();
      $('#date').text("" + (date.getMonth() + 1) + "月" + (date.getDate()) + "日");
      $.ajax({
        url: '/api/servicetable',
        cache: false,
        dataType: 'json'
      }).then((function(_this) {
        return function(data) {
          var serviceName;
          _this.serviceDay = getServiceDay(data);
          serviceName = {
            WEEKDAY: '平日',
            SATURDAY: '土曜',
            SUNDAY: '運行なし'
          };
          _this.$('#service').text(serviceName[_this.serviceDay]);
          return $.ajax({
            url: '/api/timetable',
            cache: false,
            dataType: 'json'
          });
        };
      })(this)).then((function(_this) {
        return function(data) {
          _this.setTimetable(data);
          return _this.$('input[name="shuttle"]:eq(0)').trigger('click');
        };
      })(this));
    };

    AppView.prototype.setTimetable = function(timetableData) {
      var timeArray, timetable, _i, _j, _len, _len1, _ref, _ref1;
      this.timetableArray = {
        outward: [],
        homeward: []
      };
      _ref = timetableData.timetableList;
      for (_i = 0, _len = _ref.length; _i < _len; _i++) {
        timetable = _ref[_i];
        if (timetable.serviceDay === this.serviceDay) {
          _ref1 = timetable.busStopTimeList;
          for (_j = 0, _len1 = _ref1.length; _j < _len1; _j++) {
            timeArray = _ref1[_j];
            this.timetableArray[timetable.shuttle.toLowerCase()].push(App.TimetableModel.setTime(timetable.shuttle, timeArray));
          }
        }
      }
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
  var RideBusView,
    __hasProp = {}.hasOwnProperty,
    __extends = function(child, parent) { for (var key in parent) { if (__hasProp.call(parent, key)) child[key] = parent[key]; } function ctor() { this.constructor = child; } ctor.prototype = parent.prototype; child.prototype = new ctor(); child.__super__ = parent.prototype; return child; };

  RideBusView = (function(_super) {
    __extends(RideBusView, _super);

    function RideBusView() {
      return RideBusView.__super__.constructor.apply(this, arguments);
    }

    RideBusView.prototype.model = App.rideBusModel;

    RideBusView.prototype.events = {
      'change #beforeAlert': 'onChangeBeforeAlert'
    };

    RideBusView.prototype.initialize = function() {
      RideBusView.departureTimeTmpl = _.template($('#departureTimeTmpl').html());
      _.bindAll(this, 'onChange');
      this.model.on('change', this.onChange);
      this.$('#beforeAlert').val(this.model.get('beforeAlert'));
      return this.render();
    };

    RideBusView.prototype.render = function() {
      this.$('#departureTime').html(RideBusView.departureTimeTmpl(this.model.toJSON()));
      return this;
    };

    RideBusView.prototype.setTimer = function() {
      var beforeAlert, date, departureTime, i, nowHour, nowMinute, nowSecond, targetSecond, targetTime, value, _i, _len;
      departureTime = this.model.get('departureTime');
      if (departureTime === '') {
        return;
      }
      beforeAlert = parseInt($('#beforeAlert').val(), 10);
      if (isNaN(beforeAlert)) {
        return;
      }
      date = new Date();
      nowHour = date.getHours();
      nowMinute = date.getMinutes();
      nowSecond = date.getSeconds();
      targetTime = departureTime.split(':');
      for (i = _i = 0, _len = targetTime.length; _i < _len; i = ++_i) {
        value = targetTime[i];
        targetTime[i] = parseInt(value, 10);
      }
      targetSecond = (((targetTime[0] - nowHour) * 60 + targetTime[1] - nowMinute - beforeAlert) * 60 - nowSecond) * 1000;
      if (targetSecond <= 0) {
        return;
      }
      if (this.timer) {
        clearTimeout(this.timer);
      }
      return this.timer = setTimeout(function() {
        var notify;
        return notify = new Notification('もうすぐバスが来ます', {
          tag: 'tag',
          body: "" + departureTime + "発のバスがあと" + beforeAlert + "分で発車します",
          icon: 'image/bus.png'
        });
      }, targetSecond);
    };

    RideBusView.prototype.onChangeBeforeAlert = function() {
      return this.setTimer();
    };

    RideBusView.prototype.onChange = function() {
      this.render();
      return this.setTimer();
    };

    return RideBusView;

  })(Backbone.View);

  App.RideBusView = RideBusView;

}).call(this);

(function() {
  var TimetableListView,
    __hasProp = {}.hasOwnProperty,
    __extends = function(child, parent) { for (var key in parent) { if (__hasProp.call(parent, key)) child[key] = parent[key]; } function ctor() { this.constructor = child; } ctor.prototype = parent.prototype; child.prototype = new ctor(); child.__super__ = parent.prototype; return child; };

  TimetableListView = (function(_super) {
    __extends(TimetableListView, _super);

    function TimetableListView() {
      return TimetableListView.__super__.constructor.apply(this, arguments);
    }

    TimetableListView.prototype.events = {
      'click tbody td': 'onClickTime'
    };

    TimetableListView.prototype.initialize = function(options) {
      TimetableListView.timetableTmpl = _.template($('#timetableTmpl').html());
      TimetableListView.timetableHeaderOutwardTmpl = _.template($('#timetableHeaderOutwardTmpl').html());
      TimetableListView.timetableHeaderHomewardTmpl = _.template($('#timetableHeaderHomewardTmpl').html());
      TimetableListView.timetableRowOutwardTmpl = _.template($('#timetableRowOutwardTmpl').html());
      TimetableListView.timetableRowHomewardTmpl = _.template($('#timetableRowHomewardTmpl').html());
      _.bindAll(this, 'onReset');
      this.collection = options.collection;
      return this.collection.on('reset', this.onReset);
    };

    TimetableListView.prototype.render = function() {
      var $tbody, $thead;
      this.$el.html(TimetableListView.timetableTmpl());
      $thead = this.$('thead');
      $tbody = this.$('tbody');
      if (this.collection.shuttle === 'outward') {
        $thead.html(TimetableListView.timetableHeaderOutwardTmpl());
        this.collection.each(function(timetableModel) {
          return $tbody.append(TimetableListView.timetableRowOutwardTmpl(timetableModel.toJSON()));
        });
      } else {
        $thead.html(TimetableListView.timetableHeaderHomewardTmpl());
        this.collection.each(function(timetableModel) {
          return $tbody.append(TimetableListView.timetableRowHomewardTmpl(timetableModel.toJSON()));
        });
      }
      return this;
    };

    TimetableListView.prototype.onReset = function() {
      return this.render();
    };

    TimetableListView.prototype.onClickTime = function(e) {
      var $td, index;
      $td = $(e.currentTarget);
      index = $td.parent('tr').find('td').index($td);
      if (this.collection.shuttle === 'outward' && index >= 1) {
        return;
      }
      if (this.collection.shuttle === 'homeward' && index >= 3) {
        return;
      }
      return App.rideBusModel.set('departureTime', $td.text());
    };

    return TimetableListView;

  })(Backbone.View);

  App.TimetableListView = TimetableListView;

}).call(this);

(function() {
  var appView, timetableListView;

  appView = new App.AppView({
    el: '#main'
  });

  timetableListView = new App.TimetableListView({
    el: '#timetable',
    collection: App.timetableList
  });

  App.rideBusView = new App.RideBusView({
    el: '#rideBus'
  });

}).call(this);

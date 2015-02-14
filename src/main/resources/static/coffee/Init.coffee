initData = []

$.ajax(
  url: 'data/servicetable.csv'
  cache: false
  beforeSend = (xhr) ->
    xhr.overrideMimeType 'text/plain; charset=Shift_JIS'
    return
  dataType: 'text'
).done((data) ->
  serviceArray = data.split(/\r\n|\r|\n/)

  for value, index in serviceArray
    continue if index < 2
    break if value is ''
    serviceData = value.split(/,/)
    initData[index - 2] = new App.ServiceModel(
      data: serviceData[0]
      youbiType: serviceData[1]
    )

  console.log(initData)
  serviceCollection = new App.ServiceCollection(initData)
  console.log(serviceCollection)
)


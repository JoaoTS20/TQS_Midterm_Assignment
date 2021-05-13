# API Documentation



## Problem Domain
### Get Current Air Quality By City

**GET ``` /api/airquality/city/{city}```**\
*Example Value*
```json
{
  "timestamp": 1620861789010, 
  "city_name":"Porto",
  "lat":"42.16737",
  "lon":"-6.89934",
  "data": 
  [{
      "aqi":41.0,
      "o3":88.4235,
      "co":282.049,
      "so2":0.447966,
      "no2":1.18214,
      "pm25":0.933011,
      "pm10":3.62781,
      "pollen_level_tree":1,
      "pollen_level_grass":1,
      "pollen_level_weed":1,
      "mold_level":1
    }]
}
```
- When Not Valid: ``` {city} ```
```json
{
  "timestamp":0,
  "city_name":"error",
  "lat":"",
  "lon":"",
  "data":[]
}
```



### Get Current Air Quality By Coordinates

**GET ``` /api/airquality/coordinates/{latitude},{longitude}```**\
*Example Value*
```json
{
  "timestamp":1620862256638,
  "city_name":"Aveiro",
  "lat":"40.64427",
  "lon":"-8.64554",
  "data": [
    {
      "aqi":45.0,
      "o3":97.096,
      "co":230.0,
      "so2":0.709668,
      "no2":13.5,
      "pm25":1.5,
      "pm10":2.0,
      "pollen_level_tree":1,
      "pollen_level_grass":1,
      "pollen_level_weed":1,
      "mold_level":1
    }]
}
```

- When Not Valid: ``` {latitude},{longitude} ```
```json
{
  "timestamp":0,
  "city_name":"error",
  "lat":"",
  "lon":"",
  "data":[]
}
```
#### Field Descriptions:
- timestamp: Time in milliseconds of the Get Request to the External API.
- city_name: City name.
- lat: Latitude (Degrees).
- lon: Longitude (Degrees).
- data: [
    - aqi: Air Quality Index [US - EPA standard 0 - +500]
    - o3: Concentration of surface O3 (µg/m³)
    - so2: Concentration of surface SO2 (µg/m³)
    - no2: Concentration of surface NO2 (µg/m³)
    - co: Concentration of carbon monoxide (µg/m³)
    - pm25: Concentration of particulate matter < 2.5 microns (µg/m³)
    - pm10: Concentration of particulate matter < 10 microns (µg/m³)
    - pollen_level_tree: Tree pollen level (0 = None, 1 = Low, 2 = Moderate, 3 = High, 4 = Very High)
    - pollen_level_grass:Grass pollen level (0 = None, 1 = Low, 2 = Moderate, 3 = High, 4 = Very High)
    - pollen_level_weed:Weed pollen level (0 = None, 1 = Low, 2 = Moderate, 3 = High, 4 = Very High)
    - mold_level: Mold level (0 = None, 1 = Low, 2 = Moderate, 3 = High, 4 = Very High)\
      ]


## Cache Usage Statistics

### Get Cities and respective Air Quality in Cache

**GET ``` /api/airquality/cached```**\
*Example Value*
```json
{
  "porto": {
  "timestamp":1620862276769,
  "city_name":"Porto",
  "lat":"41.14961",
  "lon":"-8.61099",
  "data":[
    {
      "aqi":29.0,
      "o3":62.0,
      "co":253.0,
      "so2":22.0,
      "no2":22.2333,
      "pm25":2.78014,
      "pm10":23.0,
      "pollen_level_tree":1,
      "pollen_level_grass":1,
      "pollen_level_weed":1,
      "mold_level":1
    }]
  },
  "aveiro": {
    "timestamp":1620862256638,
    "city_name":"Aveiro",
    "lat":"40.64427",
    "lon":"-8.64554",
    "data":[
      {
        "aqi":45.0,
        "o3":97.096,
        "co":230.0,
        "so2":0.709668,
        "no2":13.5,
        "pm25":1.5,
        "pm10":2.0,
        "pollen_level_tree":1,
        "pollen_level_grass":1,
        "pollen_level_weed":1,
        "mold_level":1
      }
    ]}
}
```

### Get Basic Cache Usage Statistics and Cities in Cache

**GET ``` /api/airquality/statistics```**\
*Example Value*
```json
{
  "hit":"2",
  "citiesAirInfoInCache":"[porto, aveiro]",
  "miss":"3"
}
```


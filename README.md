# POI Distance

Simple module for reading a list of POIs and getting the nearest point for given coordinates.

## Sample

The following sample first reads data from a json file and then find points with the closest distance to zero
coordinate.

```kotlin
val service = POIServiceJson(jacksonObjectMapper(), "GIP_PV_STOPS_EU-DEL-V-20220627.json")
service.update()
val pois: Set<Feature> = service.getPOI(Coordinate(BigDecimal.ZERO, BigDecimal.ZERO), Instant.now())
```

The contained test data set contains two points with the same distance so both are returned. See POIServiceJsonTest for
fur the complete sample.

## Data

[Data source](https://data.oebb.at/#default/datasetDetail): [ÖBB-Infrastruktur AG](http://infrastruktur.oebb.at/de/)
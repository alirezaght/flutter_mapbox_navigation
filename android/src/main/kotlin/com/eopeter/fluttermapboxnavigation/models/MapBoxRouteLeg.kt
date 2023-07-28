package com.eopeter.fluttermapboxnavigation.models

import com.google.gson.JsonArray
import com.google.gson.JsonElement
import com.google.gson.JsonObject
import com.mapbox.api.directions.v5.models.DirectionsRoute
import com.mapbox.api.directions.v5.models.RouteLeg
import com.mapbox.navigation.base.trip.model.RouteLegProgress


data class MapBoxRoute(val route: DirectionsRoute) {
    var routeIndex = route.routeIndex()
    var distance = route.distance()
    var duration = route.duration()
    var legs: JsonArray = JsonArray()
    init {
        route.legs()?.forEach { routeLeg ->
            var leg = MapBoxRouteLeg(null)
            leg.distance = routeLeg.distance()
            leg.duration = routeLeg.duration()
            leg.summary = routeLeg.summary()
            legs.add(leg.toJsonObject())
        }
    }

    fun toJsonObject(): JsonObject {
        var obj = JsonObject()
        obj.addProperty("routeIndex", routeIndex)
        obj.addProperty("distance", distance)
        obj.addProperty("duration", duration)
        obj.add("legs", legs)
        return obj
    }

}


data class MapBoxRouteLeg(val leg: RouteLegProgress?) {
    var step: MapBoxRouteStep? = if (leg?.currentStepProgress?.step == null) null else MapBoxRouteStep(leg.currentStepProgress, leg.currentStepProgress?.step);
    var nextStep: MapBoxRouteStep? = if (leg?.upcomingStep == null) null else MapBoxRouteStep(null, leg.upcomingStep);
    var maxSpeed: Int?;
    var maxSpeedUnit: String?;
    var duration = leg?.routeLeg?.duration()
    var distance = leg?.routeLeg?.distance()
    var summary = leg?.routeLeg?.summary()

    fun toJsonObject(): JsonObject {
        var obj = JsonObject()
        obj.add("step", step?.toJsonObject())
        obj.add("nextStep", nextStep?.toJsonObject())
        obj.addProperty("maxSpeed", maxSpeed)
        obj.addProperty("maxSpeedUnit", maxSpeedUnit)
        obj.addProperty("duration", duration)
        obj.addProperty("distance", distance)
        obj.addProperty("summary", summary)
        return obj
    }

    init {
        var geoIndex = leg?.currentStepProgress?.step?.intersections()
            ?.get(leg.currentStepProgress?.intersectionIndex ?: 0)?.geometryIndex() ?: -1

        this.maxSpeed =
            if (geoIndex == -1) null else leg?.routeLeg?.annotation()?.maxspeed()?.get(geoIndex)
                ?.speed()
        this.maxSpeedUnit =
            if (geoIndex == -1) null else leg?.routeLeg?.annotation()?.maxspeed()?.get(geoIndex)
                ?.unit()
    }

}

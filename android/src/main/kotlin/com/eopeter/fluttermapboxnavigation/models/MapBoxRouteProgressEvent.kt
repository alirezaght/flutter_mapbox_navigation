package com.eopeter.fluttermapboxnavigation.models
import com.google.gson.JsonObject
import com.mapbox.navigation.base.trip.model.RouteProgress

data class MapBoxRouteProgressEvent(val progress: RouteProgress) {


    var currentLeg: MapBoxRouteLeg = MapBoxRouteLeg(progress.currentLegProgress);
    var duration: Double = progress.durationRemaining;
    var distance: Float = progress.distanceRemaining;

    fun toJsonObject(): JsonObject {
        var obj = JsonObject()
        obj.add("currentLeg", currentLeg.toJsonObject())
        obj.addProperty("duration", duration)
        obj.addProperty("distance", distance)
        return obj
    }

}

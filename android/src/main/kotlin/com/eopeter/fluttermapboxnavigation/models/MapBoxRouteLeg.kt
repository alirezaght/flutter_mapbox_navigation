package com.eopeter.fluttermapboxnavigation.models

import com.google.gson.JsonArray
import com.google.gson.JsonElement
import com.google.gson.JsonObject
import com.mapbox.navigation.base.trip.model.RouteLegProgress


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

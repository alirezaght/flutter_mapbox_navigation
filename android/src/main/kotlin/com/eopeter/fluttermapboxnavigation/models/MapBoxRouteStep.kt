package com.eopeter.fluttermapboxnavigation.models

import com.google.gson.JsonArray
import com.google.gson.JsonElement
import com.google.gson.JsonObject
import com.mapbox.api.directions.v5.models.LegStep
import com.mapbox.navigation.base.trip.model.RouteLegProgress
import com.mapbox.navigation.base.trip.model.RouteStepProgress

data class Maneuver(var instruction: String?, var type: String?, var modifier: String?) {
    fun toJsonObject(): JsonObject {
        var obj = JsonObject()
        obj.addProperty("instruction", instruction)
        obj.addProperty("type", type)
        obj.addProperty("modifier", modifier)
        return obj
    }
}

data class Instruction(var text: String, var type: String?, var modifier: String?) {

    fun toJsonObject(): JsonObject {
        var obj = JsonObject()
        obj.addProperty("text", text)
        obj.addProperty("type", type)
        obj.addProperty("modifier", modifier)
        return obj
    }
}

data class BannerInstruction(
    var distanceAlongGeometry: Double,
    var primary: Instruction,
    var sub: Instruction?
) {

    fun toJsonObject(): JsonObject {
        var obj = JsonObject()
        obj.addProperty("distanceAlongGeometry", distanceAlongGeometry)
        obj.add("primary", primary.toJsonObject())
        obj.add("sub", sub?.toJsonObject())
        return obj
    }

}


data class MapBoxRouteStep(val stepProgress: RouteStepProgress?, val step: LegStep?) {


    var distance: Double? =
        stepProgress?.distanceRemaining?.toDouble() ?: step?.distance();
    var duration: Double? = stepProgress?.durationRemaining ?: step?.duration();
    var speedLimitUnit: String? = step?.speedLimitUnit();
    var speedLimitSign: String? = step?.speedLimitSign();
    var name: String? = step?.name();
    var mode: String? = step?.mode();
    var maneuver: Maneuver = Maneuver(
        step?.maneuver()?.instruction(),
        step?.maneuver()?.type(),
        step?.maneuver()?.modifier()
    );
    var bannerInstructions = JsonArray();

    fun toJsonObject(): JsonObject {
        var obj = JsonObject()
        obj.addProperty("speedLimitUnit", speedLimitUnit)
        obj.addProperty("speedLimitSign", speedLimitSign)
        obj.addProperty("duration", duration)
        obj.addProperty("distance", distance)
        obj.addProperty("mode", mode)
        obj.add("maneuver", maneuver.toJsonObject())
        obj.add("bannerInstructions", bannerInstructions)
        return obj
    }


    init {
        step?.bannerInstructions()?.forEach {
            bannerInstructions.add(
                BannerInstruction(
                    it.distanceAlongGeometry(),
                    Instruction(it.primary().text(), it.primary().type(), it.primary().modifier()),
                    if (it.sub() == null) null else Instruction(
                        it.sub()!!.text(),
                        it.sub()!!.type(),
                        it.sub()!!.modifier()
                    )
                ).toJsonObject()
            )
        }
    }


}

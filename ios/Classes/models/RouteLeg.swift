import Foundation
import MapboxDirections
import MapboxNavigation
import MapboxCoreNavigation

public class MapBoxRouteLeg : Codable
{
    var maxSpeed: Double?
    var maxSpeedUnit: String?
    var duration: Double?
    var distance: Double?
    var summary: String?
    var step: MapBoxRouteStep?
    var nextStep: MapBoxRouteStep?

    init(leg: RouteLeg, progress: RouteProgress?) {
        self.duration = leg.expectedTravelTime
        self.distance = leg.distance
        self.summary = leg.name
        self.step = progress == nil ? nil : MapBoxRouteStep(step: progress!.currentLegProgress.currentStep, progress: progress!.currentLegProgress.currentStepProgress)
        self.nextStep = progress == nil ? nil : progress!.upcomingStep == nil ? nil : MapBoxRouteStep(step: progress!.upcomingStep!, progress: nil)
        self.maxSpeed = progress == nil ? nil : progress!.currentLegProgress.currentSpeedLimit?.value
        self.maxSpeedUnit = progress == nil ? nil : progress!.currentLegProgress.currentSpeedLimit?.unit.symbol
    }
}

import Foundation
import MapboxDirections
import MapboxNavigation
import MapboxCoreNavigation

public class MapBoxRouteProgressEvent : Codable
{
    let arrived: Bool
    let distance: Double
    let duration: Double
    let distanceTraveled: Double
    let currentLegDistanceTraveled: Double
    let currentLegDistanceRemaining: Double
    let currentStepInstruction: String
    let legIndex: Int
    let stepIndex: Int
    let currentLeg: RouteLeg
    var priorLeg: RouteLeg? = nil
    var remainingLegs: [RouteLeg] = []
    var currentRouteIndex: Int = 0

    init(progress: RouteProgress) {

        arrived = progress.isFinalLeg && progress.currentLegProgress.userHasArrivedAtWaypoint
        distance = progress.distanceRemaining
        distanceTraveled = progress.distanceTraveled
        duration = progress.durationRemaining
        legIndex = progress.legIndex
        stepIndex = progress.currentLegProgress.stepIndex
        
        currentLeg = progress.currentLeg
        
        if(progress.priorLeg != nil)
        {
            priorLeg = progress.priorLeg!
        }

        for leg in progress.remainingLegs
        {
            remainingLegs.append(leg)
        }

        currentLegDistanceTraveled = progress.currentLegProgress.distanceTraveled
        currentLegDistanceRemaining = progress.currentLegProgress.distanceRemaining
        currentStepInstruction = progress.currentLegProgress.currentStep.description
    }


}

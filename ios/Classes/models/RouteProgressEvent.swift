import Foundation
import MapboxDirections
import MapboxNavigation
import MapboxCoreNavigation

public class MapBoxRouteProgressEvent : Codable
{
    let currentLeg: MapBoxRouteLeg
    let distance: Double
    let duration: Double

    init(progress: RouteProgress) {
        
        distance = progress.distanceRemaining
        duration = progress.durationRemaining
        
        currentLeg = MapBoxRouteLeg(leg: progress.currentLeg, progress: progress)
                
    }


}

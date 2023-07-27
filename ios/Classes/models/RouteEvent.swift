import Foundation
import MapboxDirections
import MapboxNavigation
import MapboxCoreNavigation

public class MapBoxRouteEvent : Codable
{
    let eventType: MapBoxEventType
    let data: String

    init(eventType: MapBoxEventType, data: String) {
        self.eventType = eventType
        self.data = data
    }
}


public class MapBoxRoute : Codable {
    let routeIndex: String
    let distance: Double
    let duration: Double
    var legs: [MapBoxRouteLeg] = []
    
    init(route: IndexedRouteResponse) {
        self.routeIndex = String(route.routeIndex)
        self.distance = route.currentRoute!.distance
        self.duration = route.currentRoute!.expectedTravelTime
        self.legs = []
        route.currentRoute?.legs.forEach({ leg in
            self.legs.append(MapBoxRouteLeg(leg: leg, progress: nil))
        })
    }
    
    init(index: Int, route: Route) {
        self.routeIndex = String(index)
        self.distance = route.distance
        self.duration = route.expectedTravelTime
        self.legs = []
        route.legs.forEach({ leg in
            self.legs.append(MapBoxRouteLeg(leg: leg, progress: nil))
        })
    }
}

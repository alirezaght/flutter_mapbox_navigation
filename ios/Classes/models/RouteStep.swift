import Foundation
import MapboxDirections
import MapboxNavigation
import MapboxCoreNavigation

public class Maneuver : Codable {
    let instruction: String?
    let type: String?
    let modifier: String?
    init(instruction: String?, type: String?, modifier: String?) {
        self.instruction = instruction
        self.type = type
        self.modifier = modifier
    }
}

public class Instruction : Codable {
    let text: String?
    let type: String?
    let modifier: String?
    init(text: String?, type: String?, modifier: String?) {
        self.text = text
        self.type = type
        self.modifier = modifier
    }
}

public class BannerInstruction : Codable {
    let distanceAlongGeometry: Double
    let primary: Instruction
    let sub: Instruction?
    init(distanceAlongGeometry: Double, primary: Instruction, sub: Instruction?) {
        self.distanceAlongGeometry = distanceAlongGeometry
        self.primary = primary
        self.sub = sub
    }
}

public class MapBoxRouteStep : Codable
{
    let distance: Double?
    let duration: Double?
    let speedLimitUnit: String?
    let speedLimitSign: String?
    let name: String?
    let mode: String?
    var maneuver: Maneuver
    var bannerInstructions: [BannerInstruction] = []

    init(step: RouteStep, progress: RouteStepProgress?){
        self.distance = progress?.distanceRemaining ?? step.distance
        self.duration = progress?.durationRemaining ?? step.expectedTravelTime
        self.speedLimitSign = step.speedLimitSignStandard?.rawValue
        self.speedLimitUnit = step.speedLimitUnit?.symbol
        self.name = step.description
        self.mode = step.transportType.rawValue
        self.maneuver = Maneuver(instruction: "", type: step.maneuverType.rawValue, modifier: step.maneuverDirection?.rawValue)
        step.instructionsDisplayedAlongStep?.forEach({ instruction in
            self.bannerInstructions.append(BannerInstruction(distanceAlongGeometry: instruction.distanceAlongStep.magnitude, primary: Instruction(text: instruction.primaryInstruction.text, type: instruction.primaryInstruction.maneuverType?.rawValue, modifier: instruction.primaryInstruction.maneuverDirection?.rawValue), sub: instruction.secondaryInstruction == nil ? nil : Instruction(text: instruction.secondaryInstruction!.text, type: instruction.secondaryInstruction!.maneuverType?.rawValue, modifier: instruction.secondaryInstruction!.maneuverDirection?.rawValue)))
        })
    }
}

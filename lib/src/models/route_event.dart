import 'dart:convert';

import 'package:flutter_mapbox_navigation/flutter_mapbox_navigation.dart';

/// Represents an event sent by the navigation service
class RouteEvent {
  /// Constructor
  RouteEvent({
    this.eventType,
    this.data,
  });

  /// Creates [RouteEvent] object from json
  RouteEvent.fromJson(dynamic json) {
    try {
      eventType = MapBoxEvent.values
          .firstWhere((e) =>
      e.name == json['eventType']);
    } catch (e) {
      // TODO handle the error
    }


    data = json['data'];
  }

  /// Route event type
  MapBoxEvent? eventType;

  /// optional data related to route event
  dynamic data;
}

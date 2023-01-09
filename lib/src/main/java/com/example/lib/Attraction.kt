package com.example.lib

import java.util.*

class Attraction (var name: String, var location: String, var latitude: Double, var longitude: Double,
var info: String, var year: Int, var events: MutableList<Event>, private val uuid:String = UUID.randomUUID().toString().replace("-", "")) : Comparable<Attraction> {

    override fun compareTo(other: Attraction): Int {
        return location.compareTo(location)
    }

    override fun toString(): String {
        return "UUID: $uuid Attraction name: $name Location: $location Coordinates ($latitude, $longitude) Info: $info Events: $events \n"
    }
}
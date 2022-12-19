package com.example.lib

import java.util.*

class Attraction (private val name: String, private val location: String, private
val info: String, val year: Int, private var events: MutableList<Event>, private val uuid:String = UUID.randomUUID().toString().replace("-", "")) : Comparable<Attraction> {

    override fun compareTo(other: Attraction): Int {
        return location.compareTo(location)
    }

    override fun toString(): String {
        return "UUID: $uuid Attraction name: $name Location: $location Info: $info Events: $events \n"
    }
}
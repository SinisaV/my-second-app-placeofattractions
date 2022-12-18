package com.example.lib

class Attraction (private val name: String, private val location: String, private
val info: String, private var events: MutableList<Event>) : Comparable<Attraction> {

    override fun compareTo(other: Attraction): Int {
        return location.compareTo(location)
    }

    override fun toString(): String {
        return "Attraction name: $name Location: $location Info: $info Events: $events \n"
    }
}
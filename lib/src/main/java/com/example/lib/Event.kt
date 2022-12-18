package com.example.lib

class Event (private val name: String, private val date: String) : Comparable<Event> {
    override fun compareTo(other: Event): Int {
        return date.compareTo(date)
    }

    override fun toString(): String {
        return "Name: $name Date: $date \n"
    }
}
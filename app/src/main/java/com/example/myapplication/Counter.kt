package com.example.myapplication

class Counter {
    fun countWords(text: String): Int {
        return text.trim().split("\\s+".toRegex()).filter { it.isNotEmpty() }.size
    }

    fun countCharacters(text: String): Int {
        return text.length
    }
} 
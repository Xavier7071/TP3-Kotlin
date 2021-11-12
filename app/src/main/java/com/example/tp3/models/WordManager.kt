package com.example.tp3.models

class WordManager {
    var words: ArrayList<String> = ArrayList()
    var removedWords: ArrayList<String> = ArrayList()
    var correctWord: String = ""
    var scrambledWord: String = ""

    fun chooseRandomWord() {
        var word: String
        do {
            word = words.random()
        } while (removedWords.contains(word))
        if (words.contains(word)) {
            correctWord = word
            removedWords.add(word)
            scrambledWord = scrambleWord(word)
        }
    }

    private fun scrambleWord(word: String): String {
        val chars = word.toCharArray()
        chars.shuffle()
        return chars.concatToString()
    }
}
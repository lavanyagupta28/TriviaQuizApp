package com.example.triviaquizapp

enum class Difficulty(val difficultyLevel: Int, val apiName: String) {
    ANY_DIFFICULTY(0,""),
    EASY(1,"easy"),
    MEDIUM(2,"medium"),
    HARD(3,"hard");

    companion object {
        fun getDifficultyByDifficultyLevel(difficultyLevel: Int): Difficulty? {
            return entries.find { it.difficultyLevel == difficultyLevel}
        }
    }

}
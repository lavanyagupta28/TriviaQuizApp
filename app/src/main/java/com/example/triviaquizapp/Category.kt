package com.example.triviaquizapp

enum class Category(val id:Int, val displayName: String) {
    ANY_CATEGORY(1,"Any Category"),
    GENERAL_KNOWLEDGE(2, "General Knowledge"),
    ENTERTAINMENT_BOOKS(3, "Entertainment: Books"),
    ENTERTAINMENT_FILM(4, "Entertainment: Film"),
    ENTERTAINMENT_MUSIC(5, "Entertainment: Music"),
    ENTERTAINMENT_MUSICALS_THEATRES(6, "Entertainment: Musicals & Theatres"),
    ENTERTAINMENT_TELEVISION(7, "Entertainment: Television"),
    ENTERTAINMENT_VIDEO_GAMES(8, "Entertainment: Video Games"),
    ENTERTAINMENT_BOARD_GAMES(9, "Entertainment: Board Games"),
    SCIENCE_NATURE(10, "Science & Nature"),
    SCIENCE_COMPUTERS(11, "Science: Computers"),
    SCIENCE_MATHEMATICS(12, "Science: Mathematics"),
    MYTHOLOGY(13, "Mythology"),
    SPORTS(14, "Sports"),
    GEOGRAPHY(15, "Geography"),
    HISTORY(16, "History"),
    POLITICS(17, "Politics"),
    ART(18, "Art"),
    CELEBRITIES(19, "Celebrities"),
    ANIMALS(20, "Animals"),
    VEHICLES(21, "Vehicles"),
    ENTERTAINMENT_COMICS(22, "Entertainment: Comics"),
    SCIENCE_GADGETS(23, "Science: Gadgets"),
    ENTERTAINMENT_ANIME_MANGA(24, "Entertainment: Japanese Anime & Manga"),
    ENTERTAINMENT_CARTOON_ANIMATIONS(25, "Entertainment: Cartoon & Animations");


    companion object {
        fun getIdByDisplayName(displayName: String):Int? {
            return entries.find{it.displayName == displayName}?.id
        }
    }
}
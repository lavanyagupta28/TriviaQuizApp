package com.example.triviaquizapp

object HardcodedQuestions {
    val questionInputs = listOf(
        QuestionInput(
            Category.GENERAL_KNOWLEDGE,
            Type.MULTIPLE_CHOICE,
            Difficulty.MEDIUM,
            "What is the average life span of a garden ant?",
            "15 years",listOf("24 hours","1 week","3 years")
        ),
        QuestionInput(
            Category.ENTERTAINMENT_MUSIC,
            Type.MULTIPLE_CHOICE,
            Difficulty.MEDIUM,
            "What was Raekwon the Chefs debut solo album?",
            "Only Built 4 Cuban Linx",listOf("Shaolin vs Wu-Tang","The Wild","The Lex Diamond Story")
        )
    )
    val jsonString = """
        {
            "response_code": 0,
            "results": [
                {
                    "type": "boolean",
                    "difficulty": "easy",
                    "category": "Entertainment: Video Games",
                    "question": "Activision created Battlefield 1.",
                    "correct_answer": "False",
                    "incorrect_answers": [
                        "True"
                    ]
                },
                {
                    "type": "multiple",
                    "difficulty": "easy",
                    "category": "Sports",
                    "question": "Who won the UEFA Champions League in 2016?",
                    "correct_answer": "Real Madrid C.F.",
                    "incorrect_answers": [
                        "FC Bayern Munich",
                        "Atletico Madrid",
                        "Manchester City F.C."
                    ]
                },
                {
                    "type": "boolean",
                    "difficulty": "easy",
                    "category": "History",
                    "question": "In World War ll, Great Britian used inflatable tanks on the ports of Great Britain to divert Hitler away from Normandy/D-day landing.",
                    "correct_answer": "True",
                    "incorrect_answers": [
                        "False"
                    ]
                },
                {
                    "type": "boolean",
                    "difficulty": "easy",
                    "category": "History",
                    "question": "Thomas Crapper was a plumber who invented the flushing toilet.",
                    "correct_answer": "False",
                    "incorrect_answers": [
                        "True"
                    ]
                },
                {
                    "type": "multiple",
                    "difficulty": "easy",
                    "category": "Entertainment: Video Games",
                    "question": "Who is the main antagonist in the Portal franchise?",
                    "correct_answer": "GLaDOS",
                    "incorrect_answers": [
                        "Chell",
                        "Wheatley",
                        "Rick"
                    ]
                },
                {
                    "type": "multiple",
                    "difficulty": "easy",
                    "category": "Sports",
                    "question": "In bowling, what is the term used for getting three consecutive strikes?",
                    "correct_answer": "Turkey",
                    "incorrect_answers": [
                        "Flamingo",
                        "Birdie",
                        "Eagle"
                    ]
                },
                {
                    "type": "multiple",
                    "difficulty": "easy",
                    "category": "Entertainment: Video Games",
                    "question": "Who is the creator of the Super Smash Bros. Series?",
                    "correct_answer": "Masahiro Sakurai",
                    "incorrect_answers": [
                        "Reggie Fils-Aim&eacute;",
                        "Bill Trinen",
                        "Hideo Kojima"
                    ]
                },
                {
                    "type": "multiple",
                    "difficulty": "easy",
                    "category": "Vehicles",
                    "question": "The LS3 engine is how many cubic inches?",
                    "correct_answer": "376",
                    "incorrect_answers": [
                        "346",
                        "364",
                        "427"
                    ]
                },
                {
                    "type": "multiple",
                    "difficulty": "easy",
                    "category": "Entertainment: Video Games",
                    "question": "What is the name of the main character in &quot;Life is Strange&quot;?",
                    "correct_answer": "Maxine Caulfield",
                    "incorrect_answers": [
                        "Victoria Chase",
                        "Stella Hill",
                        "Chloe Price"
                    ]
                },
                {
                    "type": "boolean",
                    "difficulty": "easy",
                    "category": "History",
                    "question": "The United States of America declared their independence from the British Empire on July 4th, 1776.",
                    "correct_answer": "True",
                    "incorrect_answers": [
                        "False"
                    ]
                }
            ]
        }
    """.trimIndent()
}
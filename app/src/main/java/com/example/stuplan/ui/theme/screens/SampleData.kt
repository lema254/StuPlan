package com.example.stuplan.ui.theme.screens

fun getHtmlKeyPoints(id: Int): List<String> {
    return when (id) {
        1 -> listOf(
            "HTML elements are represented by tags (e.g., <p>, <h1>, <div>)",
            "Tags usually come in pairs: an opening tag <tag> and a closing tag </tag>",
            "The basic structure of an HTML document includes <html>, <head>, and <body> tags"
        )
        // Add more cases as needed
        else -> emptyList()
    }
}

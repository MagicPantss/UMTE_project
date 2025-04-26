package com.example.tmdb_project.data

interface MovieItem {
    val id: Int
    val posterPath: String?
    val title: String?
    val name: String?
    val releaseDate: String?
    val overview: String?
}
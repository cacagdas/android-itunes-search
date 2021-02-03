package com.cacagdas.itunessearchapp.api

enum class MediaType (var id: Int, var queryString: String) {
    Movies(1, "movie"),
    Music(2, "music"),
    Apps(3, "software"),
    Books(4, "ebook"),
    All(-1, "all")
}
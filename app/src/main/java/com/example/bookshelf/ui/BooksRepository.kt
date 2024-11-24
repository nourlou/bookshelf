package com.example.bookshelf.ui
class BooksRepository {
    private val api = RetrofitInstance.api

    suspend fun getBooks(query: String): List<BookItem> {
        val response = api.searchBooks(query)
        return response.items ?: emptyList()
    }
}




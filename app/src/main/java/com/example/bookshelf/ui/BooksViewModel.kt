package com.example.bookshelf.ui

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

class BooksViewModel : ViewModel() {
    private val repository = BooksRepository()

    private val _books = MutableStateFlow<List<BookItem>>(emptyList())
    val books: StateFlow<List<BookItem>> get() = _books

    fun searchBooks(query: String) {
        viewModelScope.launch {
            val books = repository.getBooks(query)
            _books.value = books
        }
    }
}


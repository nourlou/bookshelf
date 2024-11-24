package com.example.bookshelf.ui

import android.util.Log
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.bookshelf.R

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun BookshelfScreen(viewModel: BooksViewModel = viewModel()) {
    val books = viewModel.books.collectAsState()

    Column(modifier = Modifier.padding(16.dp)) {
        OutlinedTextField(
            value = "",
            onValueChange = { query -> viewModel.searchBooks(query) },
            label = { Text("Search for books") },
            modifier = Modifier.fillMaxWidth()
        )

        Spacer(modifier = Modifier.height(16.dp))

        LazyVerticalGrid(
            columns = GridCells.Fixed(2),
            modifier = Modifier.fillMaxSize(),
            contentPadding = PaddingValues(8.dp)
        ) {
            items(books.value.size) { index ->
                val book = books.value[index]
                BookCard(book)
            }
        }
    }
}

@Composable
fun BookCard(book: BookItem) {
    Card(
        modifier = Modifier
            .padding(8.dp)
            .fillMaxWidth(),
        elevation = CardDefaults.cardElevation(4.dp)
    ) {
        Column {
            val imageUrl = book.volumeInfo.imageLinks?.thumbnail?.replace("http", "https")
            Log.d("ImageUrl", "Image URL: $imageUrl")  // Log pour vérifier l'URL de l'image

            if (imageUrl != null) {
                AsyncImage(
                    model = imageUrl,
                    contentDescription = book.volumeInfo.title,
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(150.dp),
                    contentScale = ContentScale.Crop,
                    error = painterResource(id = R.drawable.placeholder) // Image de remplacement en cas d'erreur
                )
            } else {
                // Placeholder pour les livres sans image
                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(150.dp),
                    contentAlignment = Alignment.Center
                ) {
                    Text(
                        text = "No Image",
                        style = MaterialTheme.typography.bodyMedium
                    )
                }
            }
            Text(
                text = book.volumeInfo.title,
                modifier = Modifier.padding(8.dp),
                style = MaterialTheme.typography.bodyMedium
            )
        }
    }
}


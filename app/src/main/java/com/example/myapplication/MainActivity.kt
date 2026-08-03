package com.example.grocerylist // <-- use YOUR package name

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            MaterialTheme {
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    GroceryListApp()
                }
            }
        }
    }
}

@Composable
fun GroceryListApp() {
    // ---- 1. STATE ----
    // The current text in the input box
    var newItem by remember { mutableStateOf("") }
    // The list of grocery items (observable, so the UI updates)
    val groceries = remember { mutableStateListOf<String>() }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp)
    ) {
        Text(
            text = "My Grocery List",
            fontSize = 24.sp,
            style = MaterialTheme.typography.headlineMedium
        )

        Spacer(modifier = Modifier.height(16.dp))

        // ---- 2. INPUT + ADD EVENT ----
        Row(
            modifier = Modifier.fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically
        ) {
            OutlinedTextField(
                value = newItem,
                onValueChange = { newItem = it },
                label = { Text("Enter an item") },
                singleLine = true,
                modifier = Modifier.weight(1f)
            )
            Spacer(modifier = Modifier.width(8.dp))
            Button(
                onClick = {
                    if (newItem.isNotBlank()) {
                        groceries.add(newItem.trim()) // add typed text to the list
                        newItem = ""
                    }
                },
                enabled = newItem.isNotBlank()
            ) {
                Text("Add")
            }
        }

        Spacer(modifier = Modifier.height(16.dp))


        Text(
            text = "Total items: ${groceries.size}",
            style = MaterialTheme.typography.titleMedium
        )

        Spacer(modifier = Modifier.height(8.dp))

        HorizontalDivider()

        Spacer(modifier = Modifier.height(8.dp))

        // ---- 3. LIST + DELETE EVENT ----
        LazyColumn(
            modifier = Modifier.weight(1f)
        ) {
            itemsIndexed(
                items = groceries
            ) { index, item ->
                Card(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(vertical = 4.dp)
                ) {
                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(horizontal = 16.dp, vertical = 8.dp),
                        horizontalArrangement = Arrangement.SpaceBetween,
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Text(
                            text = item,
                            fontSize = 18.sp,
                            modifier = Modifier.weight(1f)
                        )
                        IconButton(onClick = { groceries.removeAt(index) }) {
                            Icon(
                                imageVector = Icons.Filled.Delete,
                                contentDescription = "Delete $item"
                            )
                        }
                    }
                }
            }
        }
    }
}
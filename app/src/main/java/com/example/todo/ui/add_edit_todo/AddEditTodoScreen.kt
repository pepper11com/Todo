package com.example.todo.ui.add_edit_todo

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Done
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Snackbar
import androidx.compose.material3.SnackbarDuration
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.example.todo.util.UiEvent

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AddEditTodoScreen(
    onPopBackStack: () -> Unit,
    viewModel: AddEditTodoViewModel = hiltViewModel()
) {
    val scaffoldState =  remember { SnackbarHostState() }

    LaunchedEffect(key1 = true) {
        viewModel.uiEvent.collect { event ->
            when (event) {
                is UiEvent.PopBackStack -> onPopBackStack()
                is UiEvent.ShowSnackBar -> {
                    scaffoldState.showSnackbar(
                        message = event.message,
                        actionLabel = event.action,
                        duration = SnackbarDuration.Short
                    )
                }
                else -> Unit
            }
        }
    }

    Scaffold(
        snackbarHost = {
            SnackbarHost(
                hostState = scaffoldState,
                snackbar = { data ->
                    Snackbar(
                        snackbarData = data,
                        modifier = Modifier.padding(16.dp)
                    )
                }
            )
        },
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        floatingActionButton = {
            FloatingActionButton(
                onClick = {
                    viewModel.onEvent(AddEditTodoEvent.OnSaveTodoClick)
                },
            ) {
                Icon(
                    imageVector = Icons.Default.Done,
                    contentDescription = "Save Todo"
                )
            }
        }
    ) { innerPadding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding)
        ) {
            TextField(
                value = viewModel.title,
                onValueChange = {
                    viewModel.onEvent(AddEditTodoEvent.OnTitleChange(it))
                },
                placeholder = { Text(text = "Title") },
                modifier = Modifier
                    .fillMaxWidth()
            )

            Spacer(modifier = Modifier.padding(8.dp))

            TextField(
                value = viewModel.description,
                onValueChange = {
                    viewModel.onEvent(AddEditTodoEvent.OnDescriptionChange(it))
                },
                placeholder = { Text(text = "Description") },
                modifier = Modifier
                    .fillMaxWidth()
            )
        }
    }
}
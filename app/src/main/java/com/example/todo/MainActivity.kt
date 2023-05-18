package com.example.todo

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.example.todo.ui.add_edit_todo.AddEditTodoScreen
import com.example.todo.ui.theme.TodoTheme
import com.example.todo.ui.todo_list.TodoListScreen
import com.example.todo.util.Routes
import dagger.hilt.android.AndroidEntryPoint
import okhttp3.Route

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            TodoTheme {
                val navController = rememberNavController()
                NavHost(
                    navController = navController,
                    startDestination = Routes.TODO_LIST
                ){
                    composable(route = Routes.TODO_LIST){
                        TodoListScreen(
                            onNavigate = {
                                navController.navigate(it.route)
                            }
                        )
                    }
                    composable(
                        route = Routes.ADD_EDIT_TODO + "?todoId={todoId}",
                        arguments = listOf(
                            navArgument(name = "todoId"){
                                type = NavType.IntType
                                defaultValue = -1
                            }
                        )
                    ){
                        AddEditTodoScreen(
                            onPopBackStack = {
                                navController.popBackStack()
                            }
                        )
                    }
                }
            }
        }
    }
}
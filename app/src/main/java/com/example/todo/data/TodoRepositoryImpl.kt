package com.example.todo.data

import kotlinx.coroutines.flow.Flow

class TodoRepositoryImpl(
    private val dao: TodoDao
): TodoRepository {

    override fun getTodos(): Flow<List<Todo>> {
        return dao.getTodos()
    }

    override suspend fun getTodoById(id: Int): Todo? {
        return dao.getTodoById(id)
    }

    override suspend fun insertTodo(todo: Todo) {
        dao.insertTodo(todo)
    }

    override suspend fun deleteTodo(todo: Todo) {
        dao.deleteTdo(todo)
    }

}
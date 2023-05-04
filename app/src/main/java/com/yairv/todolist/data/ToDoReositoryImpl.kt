package com.yairv.todolist.data

import kotlinx.coroutines.flow.Flow

class ToDoReositoryImpl(
    private val dao: ToDoDao
) : ToDoRepository {

    override suspend fun insertToDo(toDo: ToDo) {
        dao.insertToDo(toDo)
    }

    override suspend fun deleteToDo(toDo: ToDo) {
        dao.deleteToDo(toDo)
    }

    override suspend fun getToDoById(id: Int): ToDo? {
        return dao.getToDoById(id)
    }

    override fun getToDos(): Flow<List<ToDo>> {
        return dao.getToDos()
    }
}

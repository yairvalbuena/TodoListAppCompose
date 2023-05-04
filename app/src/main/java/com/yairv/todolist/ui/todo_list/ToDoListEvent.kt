package com.yairv.todolist.ui.todo_list // ktlint-disable package-name

import com.yairv.todolist.data.ToDo

sealed class ToDoListEvent {
    data class OnDeleteToDoClick(val toDo: ToDo) : ToDoListEvent()
    data class OnDoneChange(val todo: ToDo, val isDone: Boolean) : ToDoListEvent()
    object OnUndoDeleteClick : ToDoListEvent()
    data class OnToDoClick(val todo: ToDo) : ToDoListEvent()
    object OnAddToDoClick : ToDoListEvent()
}

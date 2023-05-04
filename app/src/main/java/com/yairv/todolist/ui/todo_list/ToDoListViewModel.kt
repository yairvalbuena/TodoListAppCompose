package com.yairv.todolist.ui.todo_list // ktlint-disable package-name

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.yairv.todolist.data.ToDo
import com.yairv.todolist.data.ToDoRepository
import com.yairv.todolist.util.Routes
import com.yairv.todolist.util.UiEvent
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ToDoListViewModel @Inject constructor(
    private val repository: ToDoRepository,
) : ViewModel() {

    val todos = repository.getToDos()

    private val _uiEvent = Channel<UiEvent>()
    val uiEvent = _uiEvent.receiveAsFlow()

    private var deletedToDo: ToDo? = null

    fun onEvent(event: ToDoListEvent) {
        when (event) {
            is ToDoListEvent.OnToDoClick -> {
                sendUiEvent(UiEvent.Navigate(Routes.ADD_EDIT_TODO + "?todoId=${event.todo.id}"))
            }
            is ToDoListEvent.OnAddToDoClick -> {
                sendUiEvent(UiEvent.Navigate(Routes.ADD_EDIT_TODO))
            }
            is ToDoListEvent.OnUndoDeleteClick -> {
                deletedToDo?.let { todo ->
                    viewModelScope.launch {
                        repository.insertToDo(todo)
                    }
                }
            }
            is ToDoListEvent.OnDeleteToDoClick -> {
                viewModelScope.launch {
                    deletedToDo = event.toDo
                    repository.deleteToDo(event.toDo)
                    sendUiEvent(
                        UiEvent.ShowSnackBar(
                            message = "ToDo deleted.",
                            action = "Undo",
                        ),
                    )
                }
            }
            is ToDoListEvent.OnDoneChange -> {
                viewModelScope.launch {
                    repository.insertToDo(
                        event.todo.copy(
                            isDone = event.isDone,
                        ),
                    )
                }
            }
        }
    }

    private fun sendUiEvent(event: UiEvent) {
        viewModelScope.launch {
            _uiEvent.send(event)
        }
    }
}

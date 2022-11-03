package com.study.todo.service;

import java.util.List;

import com.study.todo.web.controller.dto.todo.CreateTodoReqDto;
import com.study.todo.web.controller.dto.todo.TodoListRespDto;

public interface TodoService {
	
	public List<TodoListRespDto> getTodoList(String type) throws Exception;
	
	public boolean addTodo(CreateTodoReqDto createTodoReqDto) throws Exception;
 	
}

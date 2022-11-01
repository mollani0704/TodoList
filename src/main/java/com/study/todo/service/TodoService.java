package com.study.todo.service;

import com.study.todo.web.controller.dto.todo.TodoListRespDto;

public interface TodoService {
	
	public TodoListRespDto getTodoList(String type) throws Exception;
	
}

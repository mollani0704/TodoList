package com.study.todo.service;

import java.util.List;

import com.study.todo.web.controller.dto.todo.CreateTodoReqDto;
import com.study.todo.web.controller.dto.todo.TodoListRespDto;

public interface TodoService {
	
	//조회
	public List<TodoListRespDto> getTodoList(String type) throws Exception;
	
	//추가
	public boolean addTodo(CreateTodoReqDto createTodoReqDto) throws Exception;
 	
	//수정
	public boolean updateTodoComplete(int todoCode) throws Exception;
}

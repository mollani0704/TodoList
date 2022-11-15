package com.study.todo.service;

import java.util.List;

import com.study.todo.web.controller.dto.todo.CreateTodoReqDto;
import com.study.todo.web.controller.dto.todo.TodoListRespDto;
import com.study.todo.web.controller.dto.todo.UpdateTodoReqDto;

public interface TodoService {
	
	//조회
	public List<TodoListRespDto> getTodoList(String type, int page, int contentCount) throws Exception;
	
	//추가
	public boolean addTodo(CreateTodoReqDto createTodoReqDto) throws Exception;
 	
	//수정
	public boolean updateTodoComplete(int todoCode) throws Exception;
	public boolean updateTodoImportance(int todoCode) throws Exception;
	public boolean updateTodoContent(UpdateTodoReqDto updateTodoReqDto) throws Exception;
	
	//삭제
	public boolean deleteTodo(int todoCode) throws Exception;
}

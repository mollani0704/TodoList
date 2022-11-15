package com.study.todo.domain;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface TodoRepository {

	public List<Todo> getTodoList(Map<String, Object> map) throws Exception;
	
	public int addTodo(Todo todo) throws Exception; 
	
	public int updateTodoComplete(int todo_code) throws Exception;
	public int updateTodoImportance(int todo_code) throws Exception;
	public int updateTodoContent(Todo todo) throws Exception;
	
	public int deleteTodo(int todo_code) throws Exception;
}

package com.study.todo.domain;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface TodoRepository {

	public List<Todo> getTodoList(String type) throws Exception;
	
	public int addTodo(Todo todo) throws Exception; 
	
	public int updateTodoComplete(int todo_code) throws Exception;
}

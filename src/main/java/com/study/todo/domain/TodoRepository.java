package com.study.todo.domain;

import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface TodoRepository {

	public Todo getTodoList(String type) throws Exception;
	
}

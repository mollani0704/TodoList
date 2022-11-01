package com.study.todo.service;

import org.springframework.stereotype.Service;

import com.study.todo.domain.Todo;
import com.study.todo.domain.TodoRepository;
import com.study.todo.web.controller.dto.todo.TodoListRespDto;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class TodoServiceImpl implements TodoService{
	
	private final TodoRepository todoRepository;
	
	@Override
	public TodoListRespDto getTodoList(String type) throws Exception {
		
		Todo todoList = todoRepository.getTodoList(type);
		
		return todoList.toRespDto();
	}

}

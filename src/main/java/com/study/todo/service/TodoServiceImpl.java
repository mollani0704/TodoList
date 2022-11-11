package com.study.todo.service;

import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Service;

import com.study.todo.domain.Todo;
import com.study.todo.domain.TodoRepository;
import com.study.todo.web.controller.dto.todo.CreateTodoReqDto;
import com.study.todo.web.controller.dto.todo.TodoListRespDto;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class TodoServiceImpl implements TodoService{
	
	private final TodoRepository todoRepository;
	
	@Override
	public List<TodoListRespDto> getTodoList(String type) throws Exception {
		
		List<TodoListRespDto> todoList = new ArrayList<TodoListRespDto>();
		List<Todo> todoContents = todoRepository.getTodoList(type);
		
		todoContents.forEach(data -> {
			todoList.add(data.toRespDto());
		});
		
		
		return todoList;
	}

	@Override
	public boolean addTodo(CreateTodoReqDto createTodoReqDto) throws Exception {
		
		return todoRepository.addTodo(createTodoReqDto.toEntity()) > 0;
	}

	@Override
	public boolean updateTodoComplete(int todoCode) throws Exception {
		
		return todoRepository.updateTodoComplete(todoCode) > 0;
	}

	@Override
	public boolean updateTodoImportance(int todoCode) throws Exception {
		
		return todoRepository.updateTodoImportance(todoCode) > 0;
	}

	@Override
	public boolean deleteTodo(int todoCode) throws Exception {
		
		return todoRepository.deleteTodo(todoCode) > 0; 
	}

}

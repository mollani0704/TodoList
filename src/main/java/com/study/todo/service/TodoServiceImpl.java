package com.study.todo.service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Service;

import com.study.todo.domain.Todo;
import com.study.todo.domain.TodoRepository;
import com.study.todo.web.controller.dto.todo.CreateTodoReqDto;
import com.study.todo.web.controller.dto.todo.TodoListRespDto;
import com.study.todo.web.controller.dto.todo.UpdateTodoReqDto;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class TodoServiceImpl implements TodoService{
	
	private final TodoRepository todoRepository;
	
	@Override
	public List<TodoListRespDto> getTodoList(String type, int page, int contentCount) throws Exception {
		
		Map<String, Object> map = new HashMap<String, Object>();
		
		map.put("type", type);
		map.put("index", (page - 1) * contentCount);
		map.put("count", contentCount);
		
		List<TodoListRespDto> todoList = new ArrayList<TodoListRespDto>();
		List<Todo> todoContents = todoRepository.getTodoList(map);
		
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

	@Override
	public boolean updateTodoContent(UpdateTodoReqDto updateTodoReqDto) throws Exception {
		
		return todoRepository.updateTodoContent(updateTodoReqDto.toEntity()) > 0;
	}

}

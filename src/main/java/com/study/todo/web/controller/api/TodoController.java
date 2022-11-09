package com.study.todo.web.controller.api;

import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.study.todo.service.TodoService;
import com.study.todo.web.controller.dto.CMRespDto;
import com.study.todo.web.controller.dto.todo.CreateTodoReqDto;
import com.study.todo.web.controller.dto.todo.TodoListRespDto;

import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api/v1/todo")
@RequiredArgsConstructor
public class TodoController {
	
	private final TodoService todoService;
	
	@PutMapping("/complete/{todoCode}")
	public ResponseEntity<?> setCompleteTodo(@PathVariable int todoCode) {
		
		boolean status = false;
		
		try {
			status = todoService.updateTodoComplete(todoCode);
		} catch (Exception e) {
			e.printStackTrace();
			return ResponseEntity.internalServerError().body(new CMRespDto<>(-1, "완료된 todoList 변경 안됨", status));
		}
		
		return ResponseEntity.ok().body(new CMRespDto<>(1, "완료된 todoList 변경 완료", status));
	}
	
	@PostMapping("/todoData")
	public ResponseEntity<?> addTodo(@RequestBody CreateTodoReqDto createTodoReqDto) {
		
		boolean status = false;
		
		try {
			status = todoService.addTodo(createTodoReqDto);
		} catch (Exception e) {
			e.printStackTrace();
			return ResponseEntity.internalServerError().body(new CMRespDto<>(-1, "todo 데이터 입력 실패", status));
		}
		
		return ResponseEntity.ok().body(new CMRespDto<>(1, "todo 데이터 입력 성공", status));
	}
	
	@GetMapping("/list/{type}")
	public ResponseEntity<?> getTodoList(@PathVariable String type) {
		
		List<TodoListRespDto> list = null;
		
		try {
			list = todoService.getTodoList(type);
		} catch (Exception e) {
			e.printStackTrace();
			return ResponseEntity.internalServerError().body(new CMRespDto<>(-1, "데이터 불러오기 실패", list));
		}
		
		return ResponseEntity.ok().body(new CMRespDto<>(1, "데이터 불러오기 성공", list));
		
	}
	
}

package com.study.todo.web.controller.api;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.study.todo.service.TodoService;
import com.study.todo.web.controller.dto.CMRespDto;
import com.study.todo.web.controller.dto.todo.TodoListRespDto;

import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api/v1/todo")
@RequiredArgsConstructor
public class TodoController {
	
	private final TodoService todoService;
	
	@GetMapping("/list/{type}")
	public ResponseEntity<?> getTodoList(@PathVariable String type) {
		
		TodoListRespDto list = null;
		
		try {
			list = todoService.getTodoList(type);
		} catch (Exception e) {
			e.printStackTrace();
			return ResponseEntity.internalServerError().body(new CMRespDto<>(-1, "데이터 불러오기 실패", list));
		}
		
		return ResponseEntity.ok().body(new CMRespDto<>(1, "데이터 불러오기 성공", list));
		
	}
	
}

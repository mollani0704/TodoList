package com.study.todo.web.controller.api;

import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
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
import com.study.todo.web.controller.dto.todo.UpdateTodoReqDto;

import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api/v1/todo")
@RequiredArgsConstructor
public class TodoController {
	
	private final TodoService todoService;
	
	@DeleteMapping("/{todoCode}")
	public ResponseEntity<?> deleteTodo(@PathVariable int todoCode) {
		
		boolean status = false;
		
		try {
			status = todoService.deleteTodo(todoCode);
		} catch (Exception e) {
			e.printStackTrace();
			return ResponseEntity.internalServerError().body(new CMRespDto<>(-1, "todolist 삭제 실패", status));
		}
		
		return ResponseEntity.ok().body(new CMRespDto<>(1, "todolist 삭제 성공", status));
	}
	
	@PutMapping("/{todoCode}")
	public ResponseEntity<?> setTodo(@RequestBody UpdateTodoReqDto updateTodoReqDto) {
		
		boolean status = false;
		
		try {
			status = todoService.updateTodoContent(updateTodoReqDto);
		} catch (Exception e) {
			e.printStackTrace();
			return ResponseEntity.internalServerError().body(new CMRespDto<>(-1, "todoContent 내용 변경 실패", status));
		}
		
		return ResponseEntity.ok().body(new CMRespDto<>(1, "todoContent 내용 변경 성공", status));
	}
	
	@PutMapping("/importance/{todoCode}")
	public ResponseEntity<?> setImportanceTodo(@PathVariable int todoCode) {
		
		boolean status = false;
		
		try {
			status = todoService.updateTodoImportance(todoCode);
		} catch (Exception e) {
			e.printStackTrace();
			return ResponseEntity.internalServerError().body(new CMRespDto<>(-1, "importance 변경 안됨", status));
		}
		
		return ResponseEntity.ok().body(new CMRespDto<>(1, "importance 변경 완료", status));
	}
	
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
	public ResponseEntity<?> getTodoList(@PathVariable String type, @RequestParam int page, @RequestParam int contentCount) {
		
		List<TodoListRespDto> list = null;
		
		try {
			list = todoService.getTodoList(type, page, contentCount);
		} catch (Exception e) {
			e.printStackTrace();
			return ResponseEntity.internalServerError().body(new CMRespDto<>(-1, "데이터 불러오기 실패", list));
		}
		
		return ResponseEntity.ok().body(new CMRespDto<>(1, "데이터 불러오기 성공", list));
		
	}
	
}

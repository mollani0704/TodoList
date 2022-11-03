package com.study.todo.web.controller.dto.todo;

import com.study.todo.domain.Todo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class CreateTodoReqDto {
	private String todoValue;
	private boolean importanceValue;
	
	public Todo toEntity() {
		return Todo.builder()
				.todo_content(todoValue)
				.importance_flag(importanceValue == false ? 0 : 1)
				.build();
	}
}

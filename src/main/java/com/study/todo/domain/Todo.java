package com.study.todo.domain;

import java.util.Date;

import com.study.todo.web.controller.dto.todo.TodoListRespDto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class Todo {
	
	private int todo_code;
	private String todo_content;
	private int todo_complete;
	private int importance_flag;
	private Date create_date;
	private Date update_date;
	
	public TodoListRespDto toRespDto() {
		return TodoListRespDto.builder()
					.todoCode(todo_code)
					.todoContent(todo_content)
					.todoComplete(todo_complete)
					.importanceFlag(importance_flag)
					.createDate(create_date)
					.updateDate(update_date)
					.build();
	}
	
}

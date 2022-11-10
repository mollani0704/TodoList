package com.study.todo.domain;

import java.util.Date;

import com.study.todo.web.controller.dto.todo.TodoListRespDto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Todo {
	
	private int todo_code;
	private String todo_content;
	private int todo_complete;
	private int importance_flag;
	private int total_count;
	private int incomplete_count;
	private Date create_date;
	private Date update_date;
	
	public TodoListRespDto toRespDto() {
		return TodoListRespDto.builder()
					.todoCode(todo_code)
					.todoContent(todo_content)
					.todoComplete(todo_complete == 1)
					.importanceFlag(importance_flag == 1)
					.totalCount(total_count)
					.incompleteCount(incomplete_count)
					.createDate(create_date)
					.updateDate(update_date)
					.build();
	}
	
}

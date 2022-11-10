package com.study.todo.web.controller.dto.todo;

import java.util.Date;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class TodoListRespDto {
	private int todoCode;
	private String todoContent;
	private boolean todoComplete;
	private boolean importanceFlag;
	private int totalCount;
	private int incompleteCount;
	private Date createDate;
	private Date updateDate;
}

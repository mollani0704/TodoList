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
	private int todoComplete;
	private int importanceFlag;
	private Date createDate;
	private Date updateDate;
}

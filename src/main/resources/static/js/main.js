const todoContentList = document.querySelector('.todo__content__list');
const todoAddButton = document.querySelector('.todo__add__button');
const modalContainer = document.querySelector('.modal__container');
const inCompleteCountNumber = document.querySelector(".incomplete__count__number");
const selectedTypeButton = document.querySelector(".selected__type__button");
const typeSelectBoxList = document.querySelector(".type__select__boxList");
const typeSelectBoxListText = typeSelectBoxList.querySelectorAll("li")
const selectedType = document.querySelector(".selected__type");
const sectionBody = document.querySelector(".section__body");

let listType = 'all';
let totalPage = 0;
let page = 1;


//=============<<  todolist 추가  >>===============

todoAddButton.onclick = () => {
	modalContainer.classList.toggle('modal-visible');
	todoContentList.style.overflow = 'hidden';
	setModalEvent();
};

function setModalEvent() {
	const modalCloseBtn = document.querySelector('.modal__close__button');
	const modalCommitBtn = document.querySelector('.modal__commit__button');
	const modalTodoInput = document.querySelector('.modal__todo__input');
	const modalImportanceCheck = document.querySelector('#importance__check');

	modalContainer.onclick = (event) => {
		if (event.target == modalContainer) {
			modalCloseBtn.click();
		}
	}

	modalCloseBtn.onclick = () => {
		modalContainer.classList.toggle('modal-visible');
	};

	modalCommitBtn.onclick = () => {
		let todo_value = modalTodoInput.value;
		let importance_value = modalImportanceCheck.checked;
		
		if (todo_value == "") {
			alert("todo 목록을 적어주세요");
		} else {
			
			let data = {
				todoValue: todo_value,
				importanceValue: importance_value,
			};

			addTodo(data);
			uncheckedImportance(modalImportanceCheck);
			clearModalTodoInputValue(modalTodoInput);

			modalContainer.classList.toggle('modal-visible');
		}
	};
}

function uncheckedImportance(modalImportanceCheck) {
	modalImportanceCheck.checked = false;
}

function clearModalTodoInputValue(modalTodoInput) {
	modalTodoInput.value = "";
}

//======<<  todoList 목록에 관한 이벤트들  >>===========

function createList(todoList) {
	for (content of todoList) {
		const listContent = `
			
			<li class="todo__content">
	           <input type="checkbox" id="complete__check-${content.todoCode}" class="complete__check" ${content.todoComplete ? 'checked' : ''} />
	           <label for="complete__check-${content.todoCode}"></label>
	           <div class="todo__content__text">${content.todoContent}</div>
	           <input type="text" class="todo__content__input visible" value="${content.todoContent}" />
	           <input type="checkbox" id="importance__check-${content.todoCode}" class="importance__check" ${content.importanceFlag ? 'checked' : ''} />
	           <label for="importance__check-${content.todoCode}"></label>
	           <div class="trash__button"><i class="fa-solid fa-trash"></i></div>
	         </li>
			`;
		appendList(listContent);
	}

	addEvent();
}

function appendList(listContent) {
	todoContentList.innerHTML += listContent;
}

function addEvent() {

	const todoContents = document.querySelectorAll(".todo__content");

	for (let todoContent of todoContents) {
		const todoCode = getTodoCode(todoContent);

		addCompleteEvent(todoContent, todoCode);
		addImportanceEvent(todoContent, todoCode);
		addDeleteEvent(todoContent, todoCode);
		addContentInputEvent(todoContent, todoCode);
	}

}

function addCompleteEvent(todoContent, todoCode) {
	const completeCheck = todoContent.querySelector(".complete__check");

	completeCheck.onchange = () => {
		let incompleteCount = parseInt(inCompleteCountNumber.textContent);

		if (completeCheck.checked) {
			inCompleteCountNumber.textContent = incompleteCount - 1;
		} else {
			inCompleteCountNumber.textContent = incompleteCount + 1;
		}

		updateCheckStatus("complete", todoContent, todoCode);
	}
}

function addImportanceEvent(todoContent, todoCode) {
	const importanceCheck = todoContent.querySelector(".importance__check");

	importanceCheck.onchange = () => {
		updateCheckStatus("importance", todoContent, todoCode);
	}
}

function addDeleteEvent(todoContent, todoCode) {
	const deleteButton = todoContent.querySelector(".trash__button");

	deleteButton.onclick = () => {
		console.log("test");
		deleteTodo(todoContent, todoCode);
	}
}

function addContentInputEvent(todoContent, todoCode) {
	const todoContentText = todoContent.querySelector(".todo__content__text");
	const todoContentInput = todoContent.querySelector(".todo__content__input");
	
	let todoContentOldValue = null;
	
	let eventFlag = false;
	
	let updateTodo = () => {
		const todoContentNewValue = todoContentInput.value;
		if(getChangeStatusOfValue(todoContentOldValue, todoContentNewValue)) {
			if(updateTodoContent(todoCode, todoContentNewValue)) {
				todoContentText.textContent = todoContentNewValue;
			}
		}
		
		todoContentText.classList.toggle("visible");
		todoContentInput.classList.toggle("visible");
		
	}
	
	todoContentText.onclick = () => {
		todoContentValue = todoContentInput.value;
		todoContentText.classList.toggle("visible");
		todoContentInput.classList.toggle("visible");
		todoContentInput.focus();
		eventFlag = true;
	}
	
	todoContentInput.onblur = () => {
		if(eventFlag) {
			updateTodo();
		}
	}
	
	todoContentInput.onkeyup = () => {
		if(window.event.keyCode == 13) {
			updateTodo();
			eventFlag = false;
		}
	}
	

}

function getChangeStatusOfValue(originValue, newValue) {
	return originValue != newValue
}

function updateCheckStatus(type, todoContent, todoCode) {

	let result = updateStatus(type, todoCode);
	console.log(result);

	if (((type == "complete" && (listType == "complete" || listType == "incomplete"))
		|| (type == "importance" && listType == "importance")) && result) {
		todoContentList.removeChild(todoContent);
	}

}

function getTodoCode(todoContent) {
	const completeCheck = todoContent.querySelector(".complete__check");

	const todoCode = completeCheck.getAttribute("id");
	const tokenindex = todoCode.lastIndexOf("-");

	return todoCode.substring(tokenindex + 1);
}

function setTotalPage(totalCount) {

	console.log(totalCount);
	totalPage = totalCount % 10 == 0 ? totalCount / 10 : Math.floor(totalCount / 10) + 1;
	
}

sectionBody.onscroll = () => {
	
	console.log("clientHeight : " + todoContentList.clientHeight);
	console.log("offsetHeight : " + sectionBody.offsetHeight);
	console.log("sectionBody : " + sectionBody.scrollTop);

	let checkNum = todoContentList.clientHeight - sectionBody.offsetHeight - sectionBody.scrollTop;
	
	if(checkNum < 1 && checkNum > -1 &&  page < totalPage) {
		page++;
		load();
	}
	
}

//========<< complete, incomplete, importance 타입 변경 이벤트들 >>======

selectedTypeButton.onclick = () => {
	typeSelectBoxList.classList.toggle("visible");
}

for (let i = 0; i < typeSelectBoxListText.length; i++) {
	typeSelectBoxListText[i].onclick = () => {

		removeAllClassList(typeSelectBoxListText, "type__selected")
		typeSelectBoxListText[i].classList.add("type__selected");
		setListType(typeSelectBoxListText[i].textContent);

		selectedType.textContent = typeSelectBoxListText[i].textContent;

		clearTodoListContent();

		load();
	}
}

function clearTodoListContent() {
	todoContentList.innerHTML = "";
}

function setListType(typeText) {
	listType = typeText.toLowerCase();
	console.log(listType);
}

function removeAllClassList(elements, className) {
	for (element of elements) {
		element.classList.remove(className);
	}
}

function setIncompleteCount(incompleteCount) {
	inCompleteCountNumber.textContent = incompleteCount;
}

load();

//============요청============//

function addTodo(data) {
	$.ajax({
		type: 'post',
		url: `/api/v1/todo/todoData`,
		contentType: 'application/json',
		data: JSON.stringify(data),
		async: false,
		dataType: 'json',
		success: response => {
			if(response.data) {
				todoContentList.innerHTML = "";
				load();
			}
		},

		error: errorMessage,
	});
}

function updateStatus(type, todoCode) {
	let result = false;

	$.ajax({
		type: "put",
		url: `/api/v1/todo/${type}/${todoCode}`,
		async: false,
		dataType: "json",
		success: (response) => {
			result = response.data
		},

		erorr: errorMessage
	})
	return result;
}

function updateTodoContent(todoCode, todo) {
	let successFlag = false;
	
	$.ajax({
		type: "put",
		url: `/api/v1/todo/${todoCode}`,
		contentType: "application/json",
		data: JSON.stringify({
			"todoCode" : todoCode,
			"todo": todo
		}),
		async: false,
		dataType: "json",
		success: (response) => {
			successFlag = response.data
		},
		error: errorMessage
	})
	
	return successFlag
}

function deleteTodo(todoContent, todoCode) {

	$.ajax({
		type: "delete",
		url: `/api/v1/todo/${todoCode}`,
		async: false,
		dataType: "json",
		success: (response) => {
			console.log(response.data);
			if (response.data) {
				todoContentList.removeChild(todoContent);
			}
		},

		error: errorMessage
	})

}

function load() {
	$.ajax({
		type: 'get',
		url: `/api/v1/todo/list/${listType}`,
		data: {
			"page": page,
			"contentCount": 20
		},	
		dataType: 'json',
		success: response => {
			console.log(response.data);
			
			setTotalPage(response.data[0].totalCount);
			setIncompleteCount(response.data[0].incompleteCount);
			createList(response.data);
			
		},
		error: errorMessage,
	});
}

function errorMessage(request, error) {
	alert('요청 실패');
	console.log(request.status);
	console.log(request.responseText);
	console.log(error);
}

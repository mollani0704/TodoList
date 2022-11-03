const todoContentList = document.querySelector(".todo__content__list");
const todoAddButton = document.querySelector(".todo__add__button");
const modalContainer = document.querySelector(".modal__container");

todoAddButton.onclick = () => {
	modalContainer.classList.toggle("modal-visible");
	todoContentList.style.overflow = "hidden";
	setModalEvent();
}

function setModalEvent() {
	const modalCloseBtn = document.querySelector(".modal__close__button");
	const modalCommitBtn = document.querySelector(".modal__commit__button");
	const modalTodoInput = document.querySelector(".modal__todo__input");
	const modalImportanceCheck = document.querySelector("#importance__check");
	
	modalCloseBtn.onclick = () => {
		modalContainer.classList.toggle("modal-visible");
	}
	
	modalCommitBtn.onclick = () => {
		
		let todo_value = modalTodoInput.value;
		let importance_value = modalImportanceCheck.checked;
		
		let data = {
			todoValue : todo_value,
			importanceValue : importance_value
		}
		
		addTodo(data);
		
	}
}



function createList(todoList) {
	
	for(content of todoList) {
		const listContent = `
			
			<li class="todo__content">
	           <input type="checkbox" id="complete__check-${content.todoCode}" class="complete__check ${content.todo_complete ? 'checked' : ''}" />
	           <label for="complete__check-${content.todoCode}"></label>
	           <div class="todo__content__text">${content.todoContent}</div>
	           <input type="text" class="todo__content__input visible" value="${content.todoContent}" />
	           <input type="checkbox" id="importance__check-1" class="importance__check ${content.importance ? 'checked' : ''}" />
	           <label for="importance__check-${content.todoCode}"></label>
	           <div class="trash__button"><i class="fa-solid fa-trash"></i></div>
	         </li>
			`
			appendList(listContent);
	}
	
}

function appendList(listContent) {
	
	todoContentList.innerHTML += listContent;
	
}


let listType = 'all';

load();

//============요청============// 

function addTodo(data) {
	$.ajax({
		type: 'post',
		url: `/api/v1/todo/todoData`,
		contentType: "application/json",
		data : JSON.stringify(data),
		async: false,
		dataType: "json",
		success: (response) => {
			console.log(response.data);
		},
		
		error: errorMessage
		
	})
}

function load() {
    $.ajax({
        type: 'get',
        url: `/api/v1/todo/list/${listType}`,
        dataType: 'json',
        success: response => {
            console.log(response.data);
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

const todoContentList = document.querySelector('.todo__content__list');
const todoAddButton = document.querySelector('.todo__add__button');
const modalContainer = document.querySelector('.modal__container');
const deleteButton = document.querySelector('.trash__button');
const inCompleteCountNumber = document.querySelector(".incomplete__count__number");
const selectedTypeButton = document.querySelector(".selected__type__button");
const typeSelectBoxList = document.querySelector(".type__select__boxList");
const typeSelectBoxListText = typeSelectBoxList.querySelectorAll("li")
const selectedType = document.querySelector(".selected__type");

let listType = 'all';

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

    modalCloseBtn.onclick = () => {
        modalContainer.classList.toggle('modal-visible');
    };

    modalCommitBtn.onclick = () => {
        let todo_value = modalTodoInput.value;
        let importance_value = modalImportanceCheck.checked;

        let data = {
            todoValue: todo_value,
            importanceValue: importance_value,
        };

        addTodo(data);
    };
}

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
 
function addEvent() {
	
	const todoContents = document.querySelectorAll(".todo__content");
	
	for(let todoContent of todoContents) {
		const todoCode = subStringTodoCode(todoContent);
		
		addCompleteEvent(todoContent, todoCode);
	}
	
}

function addCompleteEvent(todoContent, todoCode) {
	const completeCheck = todoContent.querySelector(".complete__check");
	
	completeCheck.onchange = () => {
		let incompleteCount = parseInt(inCompleteCountNumber.textContent);
		
		if(completeCheck.checked) {
			inCompleteCountNumber.textContent = incompleteCount - 1;
		} else {
			inCompleteCountNumber.textContent = incompleteCount + 1;
		}
		
		updateCheckStatus("complete", todoContent, todoCode);
	}
}

function updateCheckStatus(type, todoContent, todoCode) {
	
	let result = updateStatus(type, todoCode);
	console.log(result);
	
	if (((type == "complete" && (listType == "complete" || listType == "incomplete"))
		|| (type == "importance" && listType == "importance")) && result) {
			todoContentList.removeChild(todoContent);
		}
	
}

function subStringTodoCode(todoContent) {
	const completeCheck = todoContent.querySelector(".complete__check");
	
	const todoCode = completeCheck.getAttribute("id");
	const tokenindex = todoCode.lastIndexOf("-");
	
	return todoCode.substring(tokenindex + 1);
}

function appendList(listContent) {
    todoContentList.innerHTML += listContent;
}

selectedTypeButton.onclick = () => {
	typeSelectBoxList.classList.toggle("visible");
}

for(let i = 0; i < typeSelectBoxListText.length; i++) {
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
	for(element of elements) {
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
            console.log(response.data);
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

function load() {
    $.ajax({
        type: 'get',
        url: `/api/v1/todo/list/${listType}`,
        dataType: 'json',
        success: response => {
            console.log(response.data);
            
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

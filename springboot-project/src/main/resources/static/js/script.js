const password = document.getElementById('password');
const checkbox = document.getElementById('check');
const email = document.getElementById('email');
const nameUser = document.getElementById('name');

let users=[];

// pick all user
fetch('/project/user/')
	.then(response => response.json())
	.then(data => {
		users=data;
		console.log(users)
	})
	.catch(error => console.error('Error:', error));

checkbox.addEventListener('change', function() {
	if (this.checked) {
		password.type = 'text';
	} else {
		password.type = 'password';
	}
});

function thereIsInput(input){
	if(input.value !== null && input.value.trim() !== ''){
		return true;
	}else{
		return false;
	}
}

function saveUser() {
	if(thereIsInput(email) && thereIsInput(nameUser) && thereIsInput(password)){
		fetch('/project/user/', {
			method: 'POST',
			headers: {
				'Content-Type': 'application/json'
			},
			body: JSON.stringify({
				name: nameUser.value,
				email: email.value,
				password: password.value
			})
		});
	}
}

function showAllLeafs(){
	const leafContainer = document.getElementById('leaf-container');
	leafContainer.innerHTML = '';
	
	let topPosition = 100; // first leaf start here
	let topPositionSpan = 250; // first span start here
	
	users.forEach(user => {
		const img = document.createElement('img');
		const span = document.createElement('span');
		
		img.src = 'assets/leaf.png';
		img.alt = `Leaf`;
		img.style.position = 'absolute';
		img.style.top = `${topPosition}px`;
		img.style.right = '350px';
		
		span.textContent = `${user.name}`;
		span.style.position = 'absolute';
		span.style.top = `${topPositionSpan}px`;
		span.style.right = '500px';
		span.style.fontSize= '2rem';
		span.style.background= '#4A8FE7';
		span.style.padding= '1rem';
		span.style.borderRadius= '1rem';
		span.style.pointerEvents = 'none';
		
		leafContainer.appendChild(img);
		leafContainer.appendChild(span);
		topPosition += 400;
		topPositionSpan += 400;
		
		console.log(user);
	})
}
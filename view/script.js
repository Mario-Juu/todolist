const url = "http://localhost:8080/tasks/users/1"

function hideloader(){
    document.getElementById("loading").style.display="none";
}

function show(tasks){
    let tab = `<thead>
        <th scope="col">#</th>
        <th scope="col">Description</th>
        <th scope="col">Username</th>
        <th scope="col">User ID</th>
        </thead>`
    ;
    for(let task of tasks){
        tab += `<tbody>
        <tr>
        <th>${task.id}</th>
        <td>${task.description}</td>
        <td>${task.user ? task.user.username : 'N/A'}</td>
        <td>${task.user ? task.user.id : 'N/A'}</td>
        </tr>`
    }
    document.getElementById("tasks").innerHTML = tab;
}

async function getAPI(url){
    const response = await fetch(url, {method: 'GET'});

    var data = await response.json();
    if(response)
        hideloader();

    show(data);
}

getAPI(url);
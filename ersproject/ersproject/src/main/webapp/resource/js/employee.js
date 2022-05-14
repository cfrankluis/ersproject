"use strict"

let globalVariables = {
    userData : null
};

window.onload = function(){
    document.getElementById("addSubmit").addEventListener("click",addReimbursement);
    initializeData();
}

function addReimbursement(){
    let type = document.getElementById("reimbursementType");
    let amt = document.getElementById("reimbursementAmount");
    let desc = document.getElementById("reimbursementDescription");
    let res = document.getElementById("resolver");

    if(type.checkValidity() && amt.checkValidity() && res.checkValidity()){
        let xhttp = new XMLHttpRequest();  
        xhttp.onreadystatechange = function(){
            if(xhttp.readyState==4 && xhttp.status ==200){
                let responseJson = JSON.parse(xhttp.responseText);
                console.log(responseJson);
                addToTable(responseJson,"alltabData");
                
                var myModalEl = document.getElementById('exampleModal');
                var modal = bootstrap.Modal.getInstance(myModalEl);
                modal.hide();
            }
        }

        let user = globalVariables.userData;
        let dataToSend = {
            "authorId" : user.userId,
            "typeId" : type.value,
            "amount" : amt.value,
            "description" : desc.value,
            "resolverId" : res.value
        };

        xhttp.open('POST', "http://localhost:8080/ersproject/employee/add");
        xhttp.send(JSON.stringify(dataToSend));
    }
}

function initializeData(){
    let xhttp = new XMLHttpRequest();
    xhttp.onreadystatechange = function(){
        if(xhttp.readyState==4 && xhttp.status ==200){
            let responseJson = JSON.parse(xhttp.responseText);
            globalVariables.userData = responseJson[0];
            tabulateData(responseJson[1],"pasttabData");
            tabulateData(responseJson[2],"alltabData");
            getAllResolvers(responseJson[3]);

            let banner = document.getElementById("greetings");
            banner.innerText = "Hello " + responseJson[0].firstName + " " + responseJson[0].lastName;
        }
    }

    xhttp.open('GET', "http://localhost:8080/ersproject/employee/start");
    xhttp.send();
}

function tabulateData(dataArray, tabId){
    
    for(let index in dataArray){
        addToTable(dataArray[index],tabId);
    }
}

function getAllResolvers(financeManagers){
    for(let index in financeManagers){
        getResolverOption(financeManagers[index]);
    }
}

function getResolverOption(x){
    let selectHolder = document.getElementById("resolver");
    let optionHolder = document.createElement("option");
    optionHolder.setAttribute("value",x.userId);
    optionHolder.innerText = x.firstName + " " + x.lastName;

    selectHolder.appendChild(optionHolder);
}

function addToTable(r,tabId)
{
    let tableData = document.getElementById(tabId);
    let row = document.createElement("tr");
    row.setAttribute("scope","row");
    
    let idCell = document.createElement("td");
    let typeCell = document.createElement("td");
    let amountCell = document.createElement("td");
    let submitCell = document.createElement("td");
    let resolveCell = document.createElement("td");
    let statusCell = document.createElement("td");
    let descCell = document.createElement("td");
    
    let idNode = document.createTextNode(r.id);
    //let typeNode = document.createTextNode(r.typeId);
    let amountNode = document.createTextNode((r.amount).toFixed(2));
    let submitNode = document.createTextNode(r.submitted);
    //let resolveNode = document.createTextNode(r.resolved);
    //let statusNode = document.createTextNode(r.statusId);
    let descNode = document.createTextNode(r.description);

    let resolveNode;
    if(r.resolved === null){
        resolveNode = document.createTextNode("UNRESOLVED");
    } else{
        resolveNode = document.createTextNode(r.resolved);
    }

    let typeNode;
    switch(r.typeId){
        case 1:
            typeNode = document.createTextNode("LODGING");
            break;

        case 2:
            typeNode = document.createTextNode("TRAVEL");
            break;

        case 3:
            typeNode = document.createTextNode("FOOD");
            break;

        case 4:
            typeNode = document.createTextNode("OTHER");
            break;
    }

    let statusNode;
    switch(r.statusId){
        case 1:
            statusNode = document.createTextNode("PENDING");
            break;

        case 2:
            statusNode = document.createTextNode("APPROVED");
            break;

        case 3:
            statusNode = document.createTextNode("DENIED");
            break;
    }
    
    idCell.appendChild(idNode);
    typeCell.appendChild(typeNode);
    amountCell.appendChild(amountNode);
    submitCell.appendChild(submitNode);
    resolveCell.appendChild(resolveNode);
    statusCell.appendChild(statusNode);
    descCell.appendChild(descNode);

    row.appendChild(idCell);
    row.appendChild(typeCell);
    row.appendChild(amountCell);
    row.appendChild(submitCell);
    row.appendChild(resolveCell);
    row.appendChild(statusCell);
    row.appendChild(statusCell);
    row.appendChild(descCell);
    
    tableData.appendChild(row);
}
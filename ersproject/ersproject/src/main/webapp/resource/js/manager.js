"use strict"

let globalVariables = {
    userData : null,
    filterStatus : 0
};

window.onload = function(){
    initializedata();
    document.getElementById("details").hidden = true;
    document.getElementById("resolveButton").addEventListener('click',resolve);
    document.getElementById("filterButton").addEventListener('click',filterByStatus);
    document.getElementById("tab_all").checked = true;
}

function resolve(){
    let rowId = document.getElementById("idDet").innerText;
    let resolution;
    document.getElementsByName("resolveOptions").forEach(e =>{
        if(e.checked)
            resolution = e;
        else
            return;
    })

    if(resolution){
        let xhttp = new XMLHttpRequest();
        let dataToSend = {
            id : parseInt(rowId),
            statusId : parseInt(resolution.value)
        }
        xhttp.onreadystatechange = function(){
            if(xhttp.readyState==4 && xhttp.status ==200){
                let statusText;
                switch(dataToSend.statusId){
                    case 2:
                        statusText = "APPROVED";
                        break;
                    
                    case 3:
                        statusText = "DENIED";
                        break; 
                }
                let currentStatus = document.getElementById(rowId+"_stat");
                let resDate = document.getElementById(rowId+"_resDate");
                let resDet = document.getElementById("resDet");
                let statDet = document.getElementById("statDet");

                if(resDate && currentStatus){
                    resDate.innerText = (new Date()).toLocaleDateString();
                    currentStatus.innerText = statusText;
                }
                
                statDet.innerText = statusText;
                resDet.innerText = (new Date()).toLocaleDateString();
            }
        }

        console.log(dataToSend);
        xhttp.open('POST', "http://localhost:8080/ersproject/manager/resolve");
        xhttp.send(JSON.stringify(dataToSend));
    }
}

function filterByStatus(){
    let status;
    document.getElementsByName("filterOptions").forEach(e => {
        if(e.checked)
            status = e.value;
        else
            return;
    });

    if(status !== undefined && globalVariables.filterStatus !== status){
        let xhttp = new XMLHttpRequest();
        xhttp.onreadystatechange = function(){
            if(xhttp.readyState==4 && xhttp.status ==200){
                globalVariables.filterStatus = status;
                let responseJson = JSON.parse(xhttp.responseText);
                tabulateData(responseJson);
            }
        }

        xhttp.open('GET', `http://localhost:8080/ersproject/manager/filter?status=${status}`);
        xhttp.send();
    }
}

function initializedata(){
    let xhttp = new XMLHttpRequest();
    xhttp.onreadystatechange = function(){
        if(xhttp.readyState==4 && xhttp.status ==200){
            let responseJson = JSON.parse(xhttp.responseText);
            globalVariables.userData = responseJson[0];
            globalVariables.filterStatus = 0;
            document.getElementById("banner").innerText = "Hello " + globalVariables.userData.firstName + " " + globalVariables. userData.lastName;
            tabulateData(responseJson[1]);
            //console.log(responseJson[1]);
        }
    }

    xhttp.open('GET', "http://localhost:8080/ersproject/manager/start");
    xhttp.send();
}

function tabulateData(dataArray, tabId){

    let tableData = document.getElementById("reimb");
    tableData.removeChild(tableData.lastChild);
    let tabBody = document.createElement("tbody");
    tabBody.setAttribute("id","reimbAll");
    tableData.appendChild(tabBody);

    for(let index in dataArray){
        addToTable(dataArray[index],"reimbAll");
    }
}

function addToTable(r,tabId)
{
    let tableData = document.getElementById(tabId);
    let row = document.createElement("tr");
    row.setAttribute("scope","row");
    
    let idCell = document.createElement("td");
    idCell.setAttribute("id",r.id+"_id");

    let typeCell = document.createElement("td");
    typeCell.setAttribute("id",r.id+"_type");

    let amountCell = document.createElement("td");
    amountCell.setAttribute("id",r.id+"_amt");

    let authorCell = document.createElement("td");
    authorCell.setAttribute("id",r.id+"_auth");

    let submitCell = document.createElement("td");
    submitCell.setAttribute("id",r.id+"_subDate");

    let resolveCell = document.createElement("td");
    resolveCell.setAttribute("id",r.id + "_resDate");
    
    let statusCell = document.createElement("td");
    statusCell.setAttribute("id", r.id + "_stat");


    let idNode = document.createTextNode(r.id);
    let amountNode = document.createTextNode((r.amount).toFixed(2));
    let authorNode = document.createTextNode(r.authorFirstName + " " + r.authorLastName);
    let submitNode = document.createTextNode((new Date(r.submitted)).toLocaleDateString());
    let typeNode = document.createTextNode(r.type);
    let statusNode = document.createTextNode(r.status);

    let resolveNode;
    if(r.resolved === null){
        resolveNode = document.createTextNode("UNRESOLVED");
    } else{
        resolveNode = document.createTextNode((new Date(r.resolved)).toLocaleDateString());
    }

    idCell.appendChild(idNode);
    typeCell.appendChild(typeNode);
    amountCell.appendChild(amountNode);
    authorCell.appendChild(authorNode);
    submitCell.appendChild(submitNode);
    resolveCell.appendChild(resolveNode);
    statusCell.appendChild(statusNode);

    row.appendChild(idCell);
    row.appendChild(typeCell);
    row.appendChild(amountCell);
    row.appendChild(authorCell);
    row.appendChild(submitCell);
    row.appendChild(resolveCell);
    row.appendChild(statusCell);
    row.appendChild(statusCell);
    
    row.addEventListener('click',function(){
        generateDetails(r);
    });
        
    row.setAttribute("id",r.id);
    tableData.appendChild(row);
}

function generateDetails(obj){
    let idDet = document.getElementById("idDet");
    let typeDet =  document.getElementById("typeDet");
    let amountDet = document.getElementById("amountDet");
    let authDet = document.getElementById("authDet");
    let submitDet = document.getElementById("submitDet");
    let resDet = document.getElementById("resDet");
    let statDet = document.getElementById("statDet");
    let descDet = document.getElementById("descDet");

    idDet.innerText = document.getElementById(obj.id+"_id").innerText;
    typeDet.innerText = document.getElementById(obj.id+"_type").innerText;
    amountDet.innerText = document.getElementById(obj.id+"_amt").innerText;
    authDet.innerText = document.getElementById(obj.id+"_auth").innerText;
    submitDet.innerText = document.getElementById(obj.id+"_subDate").innerText;
    resDet.innerText = document.getElementById(obj.id+"_resDate").innerText;
    statDet.innerText = document.getElementById(obj.id+"_stat").innerText;
    descDet.innerText = obj.description;

    if(statDet.innerText === "APPROVED")
        document.getElementById("form_approve").checked = true;
    else if(statDet.innerText === "DENIED")
        document.getElementById("form_deny").checked = true;
    else{
        document.getElementById("form_deny").checked = false;
        document.getElementById("form_approve").checked = false;
    }

    document.getElementById("details").hidden = false;
}
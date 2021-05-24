function createDivAddCard() {
        if(document.querySelector("#addCard").innerHTML.trim() == ""){
                let div = document.createElement('div');
                div.innerHTML = document.getElementById('addForm').innerHTML;
                document.getElementById('addCard').appendChild(div);
        }
}
function createDivEditCard(clicked_id) {
        if(document.querySelector("#editCard").innerHTML.trim() == ""){
                let div = document.createElement('div');
                div.innerHTML = document.getElementById('editForm').innerHTML;
                document.getElementById('editCard').appendChild(div);
        }
        let valueName = document.getElementById("name"+clicked_id).innerText;
        document.getElementById('idSelectedCard').value = clicked_id;
        document.getElementById("nameSelectedCard").firstChild.nodeValue = "Name: "+valueName;
}
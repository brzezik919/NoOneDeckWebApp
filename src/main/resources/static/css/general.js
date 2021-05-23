function createDivAddCard() {
        if(document.querySelector("#addCard").innerHTML.trim() == ""){
                let div = document.createElement('div');
                div.innerHTML = document.getElementById('addForm').innerHTML;
                document.getElementById('addCard').appendChild(div);
        }
}
function createDivEditCard() {
        if(document.querySelector("#editCard").innerHTML.trim() == ""){
                let div = document.createElement('div');
                div.innerHTML = document.getElementById('editForm').innerHTML;
                document.getElementById('editCard').appendChild(div);
        }
        document.getElementById("editName").innerHTML += 'SIEMA';
}
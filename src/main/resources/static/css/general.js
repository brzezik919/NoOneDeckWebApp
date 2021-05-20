function createDivAddCard() {
        if(document.querySelector("#addCard").innerHTML.trim() == ""){
                let div = document.createElement('div');
                div.innerHTML = document.getElementById('addForm').innerHTML;
                document.getElementById('addCard').appendChild(div);
        }
}n
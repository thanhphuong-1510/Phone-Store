function xoaPhanLoai() {
        var container = document.querySelector('.row-phanloai');
        if (container.children.length <= 1) {
            return;
        }
        var newRow = container.children[0].cloneNode(true);
        var inputs = newRow.getElementsByTagName('input');
        for (var i = 0; i < inputs.length; i++) {
            inputs[i].value = '';
        }
        container.removeChild(container.firstElementChild);
}
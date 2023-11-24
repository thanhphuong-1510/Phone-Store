function themMoiPhanLoai() {
        var container = document.querySelector('.row-phanloai');
        var newRow = container.children[0].cloneNode(true);
        var inputs = newRow.getElementsByTagName('input');
        for (var i = 0; i < inputs.length; i++) {
            inputs[i].value = '';
        }
        container.insertBefore(newRow, container.firstChild);
}
function invert(field) {
    for (i = 0; i < field.length; i++) {
        if (field[i].checked == true) field[i].checked = false;
        else field[i].checked = true;
    }
}

$(document).on('change', 'input[type=checkbox]', function () {
    var $this = $(this), $chks = $(document.getElementsByName(this.name)), $all = $chks.filter(".chk-all");

    if ($this.hasClass('chk-all')) {
        $chks.prop('checked', $this.prop('checked'));
    } else switch ($chks.filter(":checked").length) {
        case +$all.prop('checked'):
            $all.prop('checked', false).prop('indeterminate', false);
            break;
        case $chks.length - !!$this.prop('checked'):
            $all.prop('checked', true).prop('indeterminate', false);
            break;
        default:
            $all.prop('indeterminate', true);
    }
});


// $('.btn-show-item').click(function () {
//     var $modal =$('#modalShowItem').clone();
//
//     var $btn = $(this);
//
//     var id = $btn.attr('data-id');
//     var name = $btn.attr('data-name');
//     var description = $btn.attr('data-description');
//     var userName = $btn.attr('data-userName');
//     var collectionName = $btn.attr('data-collectionName');
//     var url = $btn.attr('data-url');
//     var date = $btn.attr('data-date');
//     var time = $btn.attr('data-time');
//
//     $modal.find('[name="id"]').text(id);
//     $modal.find('[name="name"]').text(name);
//     $modal.find('[name="description"]').text(description);
//     $modal.find('[name="collectionName"]').text(collectionName);
//     $modal.find('[url="image"]').attr("src", url);
//     // $modal.find('[url="url"]').text(url);
//     $modal.find('[name="userName"]').text(userName);
//     $modal.find('[name="date"]').text(date);
//     $modal.find('[name="time"]').text(time);
//
//     $modal.modal('show');
// });

//
// $('.btn-edit-item').click(function () {
//     var $modal =$('#modalEditItem').clone();
//
//     var $btn = $(this);
//
//     var id = $btn.attr('data-id');
//     var name = $btn.attr('data-name');
//     var description = $btn.attr('data-description');
//     var userName = $btn.attr('data-userName');
//     var collectionName = $btn.attr('data-collectionName');
//     var url = $btn.attr('data-url');
//     var date = $btn.attr('data-date');
//     var time = $btn.attr('data-time');
//
//     $modal.find('[val="id"]').val(id);
//     $modal.find('[text="idShow"]').text(id);
//     $modal.find('[val="name"]').val(name);
//     $modal.find('[val="description"]').val(description);
//     $modal.find('[text="collectionName"]').text(collectionName);
//     $modal.find('[url="image"]').attr("src", url);
//     // $modal.find('[name="url"]').value(url);
//     $modal.find('[text="userName"]').text(userName);
//     $modal.find('[text="date"]').text(date);
//     $modal.find('[text="time"]').text(time);
//
//     $modal.modal('show');
// });

// $('.btn-edit-collection').click(function () {
//     var $modal =$('#modalEditCollection').clone();
//     var $btn = $(this);
//
//     var id = $btn.attr('data-id');
//     var name = $btn.attr('data-name');
//     var description = $btn.attr('data-description');
//     var url = $btn.attr('data-url');
//
//     $modal.find('[text="idShow"]').text(id);
//     $modal.find('[val="id"]').val(id);
//     $modal.find('[val="name"]').val(name);
//     $modal.find('[val="description"]').val(description);
//     $modal.find('[url="image"]').attr("src", url);
//
//
//     $modal.modal('show');
// });
//
// $('.btn-show-collection').click(function () {
//     var $modal =$('#modalShowCollection').clone();
//     var $btn = $(this);
//
//     var id = $btn.attr('data-id');
//     var name = $btn.attr('data-name');
//     var description = $btn.attr('data-description');
//     var userName = $btn.attr('data-userName');
//     var url = $btn.attr('data-url');
//
//     $modal.find('[text="idShow"]').text(id);
//     $modal.find('[url="image"]').attr("src", url);
//     $modal.find('[text="name"]').text(name);
//     $modal.find('[text="description"]').text(description);
//     $modal.find('[text="userName"]').text(userName);
//
//
//     $modal.modal('show');
// });
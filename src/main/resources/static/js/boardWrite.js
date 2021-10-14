let uploadFiles = [];
$('#loveBoardBtn').attr("aria-expanded", "true");

// 글자수 체킹 해주는거 및 유효성
$('#contents').on("change keyup paste input", function () {
    if ($(this).val().trim().length > 0) {
        $(this).attr("class", "form-control is-valid");
    } else {
        $(this).nextAll('.invalid-feedback').text('한글자 이상 작성해주세요');
        $(this).attr("class", "form-control is-invalid");
    }
    if ($(this).val().trim().length >= 2000) {
        $(this).nextAll('.invalid-feedback').text('2000 글자 미만으로 작성해주세요');
        $(this).attr("class", "form-control is-invalid");
    }
    $('#contentNum').text($(this).val().trim().length + "/2000");
})
$('#title').on("change keyup paste input", function () {
    if ($(this).val().trim().length > 0) {
        $(this).attr("class", "form-control is-valid");
    } else {
        $(this).nextAll('.invalid-feedback').text('한글자 이상 작성해주세요');
        $(this).attr("class", "form-control is-invalid");
    }
    if ($(this).val().trim().length >= 100) {
        $(this).nextAll('.invalid-feedback').text('100 글자 미만으로 작성해주세요');
        $(this).attr("class", "form-control is-invalid");
    }
    $('#titleNum').text($(this).val().trim().length + "/100");
})

// 드래그 이벤트 주기
$(document).on("dragleave", "#drop", function (e) { //드래그 요소가 나갔을때
    $(this).removeClass('bg-light');
    e.stopPropagation();
    e.preventDefault();
}).on("dragover", "#drop", function (e) { // 드래그 요소가 들어왔을때
    $(this).addClass('bg-light');
    e.stopPropagation();
    e.preventDefault();
}).on('drop', "#drop", function (e) { //드래그한 항목을 떨어뜨렸을때
    e.preventDefault();
    $(this).removeClass('bg-light');
    let fileForm = /(.*?)\.(jpg|jpeg|png|gif|bmp)$/;
    let files = e.originalEvent.dataTransfer.files;
    for (let i = 0; i < files.length; i++) {
        let file = files[i];
        if (file.name.match(fileForm)) {
            $('#imgText').remove();
            let size = uploadFiles.push(file); //업로드 목록에 추가
            preview(file, size - 1); //미리보기 만들기
        } else {
            alert("이미지 파일만 등록해주세요!")
            $('#thumbnails').empty();
            return;
        }
    }
});

// 이미지 띄우기라는데 이해안됨 걍 복붙 ㅅㄱ
function preview(file, idx) {
    let reader = new FileReader();
    reader.onload = (function (f, idx) {
        return function (e) {
            let content = "";
            content += '<div class="d-flex flex-column close" data-idx="' + idx + '" style="cursor: pointer">';
            content += '<div class="thumb">';
            content += '<img style="width: 150px;height: 150px;" class="img-fluid img-count" src="' + e.target.result + '" title="' + escape(f.name) + '"/>';
            content += '</div>';
            content += '<div class="text-center">';
            content += '<i class="bi bi-x-lg fs-3"></i>';
            content += '</div>';
            content += '</div>';
            $("#thumbnails").append(content);
        };
    })(file, idx);
    reader.readAsDataURL(file);
}

// 이미지 삭제
$(document).on("click", ".close", function (e) {
    let idx = $(this).attr('data-idx');
    uploadFiles[idx] = 'disable'; //삭제시 자기 배열에 disable 첨가
    $(this).remove(); //이미지 삭제
});
// 글쓰기
$('#boardWriteBtn').on('click', function () {
    let title = $('#title').val().trim();
    let contents = $('#contents').val().trim();
    if ($('#title').hasClass('is-valid') && $('#contents').hasClass('is-valid')) {
        let formData = new FormData();
        // 파일 넣기
        $.each(uploadFiles, function (i, file) {
            if (file.upload != 'disable') {
                formData.append('files', file, file.name);
            }
        });
        // 파라미터 넣기
        formData.append("title", title);
        formData.append("content", contents);
        $.ajax({
            url: '/loveBoard/boardWrite',
            enctype: 'multipart/form-data',
            data: formData,
            type: 'post',
            contentType: false,
            processData: false,
            success: function (suc) {
                if (suc == 0) {
                    alert("이미지는 16장이내만 등록해주세요!")
                } else {
                    alert("등록 성공!")
                }
            }
        });
    } else {
        if (title == "") {
            $('#title').nextAll('.invalid-feedback').text('한글자 이상 작성해주세요');
            $('#title').attr('class', 'form-control is-invalid');
        }
        if (contents == "") {
            $('#contents').nextAll('.invalid-feedback').text('한글자 이상 작성해주세요');
            $('#contents').attr('class', 'form-control is-invalid');
        }
    }
})
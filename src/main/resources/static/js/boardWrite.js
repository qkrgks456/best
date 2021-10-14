// 글쓰기
$(document).on('click', '#boardWriteBtn', function () {
    let check = 0;
    $.each(uploadFiles, function (i, file) {
        if (file != 'disable') {
            check++;
        }
    });
    if (check > 16) {
        alert('이미지는 16장까지만 등록가능합니다!')
    } else {
        let title = $('#title').val().trim();
        let contents = $('#contents').val().trim();
        if ($('#title').hasClass('is-valid') && $('#contents').hasClass('is-valid')) {
            LoadingWithMask();
            let formData = new FormData();
            // 파일 넣기
            $.each(uploadFiles, function (i, file) {
                if (file != 'disable') {
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
                    closeLoadingWithMask();
                    alert("등록 성공!")
                    location.href = '/loveBoard/boardDetail/' + suc + '/1'
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
    }
})

// 로딩창
function LoadingWithMask() {
    //화면의 높이와 너비를 구합니다.
    let maskHeight = $(document).height();
    let maskWidth = window.document.body.clientWidth;

    //화면에 출력할 마스크를 설정해줍니다.
    let mask = "<div id='mask' style='position:absolute; z-index:9000; display:none; left:0; top:0;'></div>";
    let loadingImg = '';

    loadingImg += "<div id='loadingImg' class='h-100 d-flex justify-content-center align-items-center'>";
    loadingImg += "<img src='/img/loadingImg.gif' style='position: relative; display: block; margin: 0px auto;'/>";
    loadingImg += "</div>";

    //화면에 레이어 추가
    $('body').append(mask)
    $('#mask').append(loadingImg)
    //마스크의 높이와 너비를 화면 것으로 만들어 전체 화면을 채웁니다.
    $('#mask').css({
        'width': maskWidth
        , 'height': maskHeight
        , 'opacity': '0.3'
    });
    //마스크 표시
    $('#mask').show();

    //로딩중 이미지 표시
    $('#loadingImg').show();
}

// 로딩창 멈추기
function closeLoadingWithMask() {
    $('#mask, #loadingImg').hide();
    $('#mask, #loadingImg').remove();
}
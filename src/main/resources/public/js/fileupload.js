'use strict';

$(function() {
    $('#fileupload')
        .fileupload({
            dataType: 'json',
            maxChunkSize: 1000000,
            maxNumberOfFiles: 1,
            progressall: function(e, data) {
                var progress = parseInt(data.loaded / data.total * 100, 10);
                $('#progress .progress-bar').css('width',
                    progress + '%');
            }
        })
        .bind(
            'fileuploadsubmit',
            function(e, data) {
                data.formData = {
                    userId: $('#home').scope().home.id,
                    name: $('#home').scope().home.user,
                    fileId: Math.floor((Math.random() * 1000000000000) + 1)
                };
            })
        .bind(
            'fileuploaddone',
            function(e, data) {            	
                if (data.result.name) {
                	$('#pMessage')
                        .text('O arquivo ' + data.result.name + ' foi enviado com sucesso.');
                }
                $('#refreshFiles').click();
            })
        .bind(
            'fileuploadfail',
            function(e, data) {
            	$('#pMessage')
                    .text('Ocorreu um erro inesperado ao enviar o arquivo.');                    
            });
});
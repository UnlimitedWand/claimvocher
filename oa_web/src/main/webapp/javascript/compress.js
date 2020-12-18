// 图片上传
function uploadImage(event) {
    const file = event.target.files
    createImage(file[0], function(img) {
        createCanvas(img, 1200)
    })
    event.target.value = ''
}

// 生成图片副本
function createImage(file, callback) {
    const reader = new FileReader()
    reader.readAsDataURL(file)
    reader.onload = function () {
        const img = new Image()
        img.src = reader.result
        callback(img)
    }
}

// 生成画布
function createCanvas(img, max) {
    const cvs = document.createElement('canvas')
    const ctx = cvs.getContext('2d')
    let width = img.naturalWidth || 800
    let height = img.naturalHeight || 800
    const ratio = width / height
    if (width > max) {
        width = max
        height = width / ratio
    }
    cvs.width = width
    cvs.height = height
    img.onload = function() {
        const base64 = compressImage(cvs, ctx, img, width, height, max)
        //console.log(base64)
    }
}

// 图片质量压缩
function compressImage(cvs, ctx, img, width, height, max) {
    // 绘制图片
    ctx.drawImage(img, 0, 0, img.naturalWidth, img.naturalHeight, 0, 0, width, height)
    const quality = width > max ? 0.4 : width > 600 ? 0.5 : 1
    let imageData = cvs.toDataURL('image/png', quality)
    let newImageBlob = dataURLToBlob(imageData)
    baiduDecode(newImageBlob)
    //downloadImage(imageData)
    return imageData
}

// 图片下载
function downloadImage(newImageData) {
    const link = document.createElement('a')
    link.href = newImageData
    link.download = 'img.png'
    link.target = '_blank'
    document.body.appendChild(link)
    link.click()
    document.body.removeChild(link)
}

function baiduDecode(imageData) {
    let formData = new FormData();
    formData.append('image', imageData,"file_"+Date.parse(new Date())+".jpg");
    $.ajax(
        {
            url:'/decode',
            type:'post',
            //cache: false,
            dateType:'json',
            contentType: false,
            //headers:{'Content-Type':'multipart/form-data'},
            data:formData,
            processData: false,
            success:function(data) {
                $('.result-qrcode').append(data + '<br/>')
                let data_obj = JSON.parse(data)
                console.log(data_obj["codes_result_num"])
                console.log(data_obj["codes_result"][0]["text"])

                if (data_obj["codes_result_num"] == 0) {
                    alert("识别失败，请重新扫描二维码！")
                }
                else {
                    let protocol = window.location.protocol
                    let host = window.location.host
                    let sub_url = data_obj["codes_result"][0]["text"]
                    let final_url = protocol+host+sub_url
                    if (window.confirm("you will go to:"+final_url+",sure?")) {
                        window.location.href = data_obj["codes_result"][0]["text"]
                    } else {
                    }
                    //alert("即将前往："+data_obj["codes_result"][0]["text"]+"确定吗？");
                }
            },
            error:function(data){
                $('.result-qrcode').append(data + '<br/>')
            }
        }
    );
}

function dataURLToBlob(dataurl){
    let arr = dataurl.split(',');
    let mime = arr[0].match(/:(.*?);/)[1];
    let bstr = atob(arr[1]);
    let n = bstr.length;
    let u8arr = new Uint8Array(n);
    while(n--){
        u8arr[n] = bstr.charCodeAt(n);
    }
    return new Blob([u8arr], {type:mime});
}
/**
 * Created by edeity on 3/18/16.
 */
// 截图插件
$(function () {
    // 分享
    covering().appendShareImg();

    // 以下为存储文件名(从服务器返回)的数组: imgsName, 以及截取时暂存img数据的数据:imgsData
    var imgsName = ['', '', ''];
    var imgsData = ['', '', ''];
    var shouldUploadImgNum = 0;
    var uploadImgNum = 0; // 上传的图片数量

    // 处理多次提交表单
    var isUpdate = false;
    // 处理上传图片的等待行为
    var isUpdataImg = false;

    // 让截图页面的插件(弹出的手势识别的截图框)刚好能够覆盖整个页面
    $('.clip-area').css('height', $(window).height());

    // 注册第一个截图工具
    bindClipPanel('#first-clip-area', '#first-file', '#first-img', '#first-clip-btn', '#first-close', '#first-clip-panel'
        ,function (imgData) {
            imgsData[0] = imgData;
            $('#second-img').fadeIn(200);
        });
    // 注册第二个截图工具
    bindClipPanel('#second-clip-area', '#second-file', '#second-img', '#second-clip-btn', '#seconde-close', '#second-clip-panel'
        ,function (imgData) {
            imgsData[1] = imgData;
            $('#third-img').fadeIn(200);
        });
    // 注册第三个截图工具
    bindClipPanel('#third-clip-area', '#third-file', '#third-img', '#third-clip-btn', '#third-close', '#third-clip-panel'
        ,function (imgData) {
            imgsData[2] = imgData;
        });

    // 触发 <input type='file'> 事件
    $("#first-img").on('click', function () {
        $("#first-file").trigger('click');
    });
    $("#second-img").on('click', function () {
        $("#second-file").trigger('click');
    });
    $("#third-img").on('click', function () {
        $("#third-file").trigger('click');
    });


    /**
     * 一次性上传所有图片, 并将返回的文件名, 放在imgsName[i]中
     */
    $("#upload-img").on('click', function () {
        if(!isUpdataImg) {
            showLoadingUpdateImage();

            // 初始传播数量值
            uploadImgNum = 0;
            shouldUploadImgNum = 0;

            // 启动定时器, 当两分钟无法返还所有信息时,提示服务器繁忙
            var timeOutKey = setTimeout(function() {
                $("#pop").fadeOut(200);//隐藏div
                $('#cover_bg').fadeOut(200);//背景遮罩
                $('#upload-img-num').text(uploadImgNum);
                hideLoadingUpdateImage();
                alert('由于服务器繁忙, 或您当前所处网络状态不稳定, 只能上传部分图片');
            }, 120000);
            // ajax上传文件
            for (var i = 0, len = imgsData.length; i < len; i++) {
                if (imgsData[i] !== '') {
                    // 在此处计算出真正要上传的文件
                    shouldUploadImgNum ++;
                    // 假如该位置拥有图片资料,而不是''
                    uploadImg(i, timeOutKey);
                }
            }
        }
    });


    /**
     * 向服务器上传图片
     * @param index imgsData中图片的位置
     */
    function uploadImg(index, timeOutKey) {
        $.post('./upload', {
            file: imgsData[index].split(',')[1],
            old_file_name: imgsName[index] // 假如存在旧照片
        }, function (data) {
            if (data.status === 0) {
                //console.log(index);
                // 允许更新
                uploadImgNum++;
                if (imgsName[index] === '') {
                    if(uploadImgNum === shouldUploadImgNum ) {
                        hideLoadingUpdateImage();
                        $("#pop").fadeOut(200);//隐藏div
                        $('#cover_bg').fadeOut(200);//背景遮罩
                        $('#upload-img-num').text(uploadImgNum);
                        clearTimeout(timeOutKey);
                    }
                    //console.log('上传成功');
                } else {
                    if(uploadImgNum === shouldUploadImgNum ) {
                        hideLoadingUpdateImage();
                        $("#pop").fadeOut(200);//隐藏div
                        $('#cover_bg').fadeOut(200);//背景遮罩
                        $('#upload-img-num').text(uploadImgNum);
                        clearTimeout(timeOutKey);
                    }
                    //console.log('更新成功');
                }
                imgsName[index] = data.filename;
            } else if (data.status === 1) {
                covering().show({txt: '上传失败, 请稍后再试'})
            }
        }).error(function() {
            //alert('当前网络状态不佳, 请更换较流畅的网络, 如wifi, 或稍后再试.');
            hideLoadingUpdateImage();
        });
    }


    /**
     * 认证并提交表单
     */
    $("#submit").on('click', function () {
        //console.log(imgsName);
        // 开始检查
        var name = $('#name').val(); // 姓名
        var sex = $('input:radio[name=sexuality]:checked').val(); // 性别
        var phone = $('#phone').val(); // 电话
        var address = $('#address').val(); // 地址
        var slogan = $('#enounce').val(); // 宣言
        var result = checkAll(name, sex, phone, address, imgsName[0], slogan);
        if (result.status !== true) {
            covering().show({
                isShowCovring: true,
                txt: result.msg,
                //time: 2000
            })
        } else {
            // 在此时才开始提交表单
            if (!isUpdate) {
                showLoadingUpdateForm();
                 //验证完毕,提交到服务器中
                $.post('./user', {
                    user: {
                        name: name, sex: sex, phone: phone, address: address,
                        first_img_name: imgsName[0], second_img_name: imgsName[1], third_img_name: imgsName[2],
                        slogan: slogan, vote_num: 0
                    }
                }, function (data) {
                    hideLoadingUpdateForm();
                    if (data.status === 0) {
                        covering().show({txt: '提交成功'}, function () {
                            // 跳转到宝贝的详情页
                            ///details.html?userId=2
                            location.href = './details?userId=' + data.id;
                        })
                    } else {
                        covering().show({txt: data.msg})
                    }
                }).error(function() {
                    hideLoadingUpdateForm();
                });
            }
        }
    });

    /**
     * 返回内容
     * status: true 或 false
     * msg: 提示的信息
     */
    function checkAll(name, sex, phone, address, imgsName, slogan) {
        // 默认值
        var status = true;
        var msg = '';
        var reg = /^0?1[3|4|5|8][0-9]\d{8}$/; // 验证手机号码的正则
        var nameReg = /[刷|票|piao]+/; // 刷票正则
        if (name === '') {
            status = false;
            msg = '名字不能为空';
        }
        else if (nameReg.test(name)) {
            status = false;
            msg = '非法输入,请重试';
        }
        else if (sex === '') {
            status = false;
            msg = '性别不能为空';
        }
        else if (phone === '') {
            status = false;
            msg = '电话号码不能为空';
        }
        else if (!reg.test(phone)) {
            status = false;
            msg = "请输入合法电话号码";
        }
        else if (address === '') {
            status = false;
            msg = '地址不能为空';
        }
        else if (address.length >= 20) {
            status = false;
            msg = '地址只需填写省市, 20字以内哦'
        }
        else if (imgsName === '') {
            status = false;
            msg = '至少得有一张宝宝图片';
        }
        else if (slogan === '') {
            status = false;
            msg = '时尚宣言不能为空';
        }
        else if (slogan.length >= 25) {
            status = false;
            msg = '时尚宣言不能超过25字,当前字数: ' + slogan.length;
        }
        return {
            status: status,
            msg: msg
        }
    }

    function showLoadingUpdateImage() {
        // 初始为0
        $('#upload-img').addClass('unclickable');
        isUpdataImg = true;
        $('.sk-double-bounce').fadeIn();
    }
    function hideLoadingUpdateImage() {
        $('#upload-img').removeClass('unclickable');
        isUpdataImg = false;
        $('.sk-double-bounce').fadeOut();
    }
    function showLoadingUpdateForm() {
        isUpdate = true;
        $('.sk-double-bounce').fadeIn();
    }
    function hideLoadingUpdateForm() {
        isUpdate = false;
        $('.sk-double-bounce').fadeOut();
    }
    /**
     * 绑定插件的方法
     */
    function bindClipPanel(clipArea, fileInput, viewDiv, okBtn, closeBtn, clipPanel, fininshCall) {
        $(closeBtn).on('click', function() {
            $(clipPanel).fadeOut();
        })
        jQuery(clipArea).photoClip({
            width: 260,
            height: 350,
            file: fileInput,
            view: viewDiv,
            ok: okBtn,
            loadStart: function () {
            },
            loadComplete: function () {
                $(clipPanel).show();
                $(okBtn).on('click', function () {
                    $(clipPanel).hide();
                })
            },
            clipFinish: function (imgData) {
                fininshCall(imgData);
            }
        });
    }
});

/**
 * 该段页面用于无跳转(ajax)更新页面资料,
 * 包括上一页,下一页,排名方式,搜索(姓名, 电话, 编号)
 * 该代码就是为了构造url
 * 正常显示: ?method=show&page=1
 * 查询显示: ?method=search&page=1&key=phone&value=18764095625
 * 排名显示: ?method=rank&page=1
  */
$(function() {
    // 分享
    covering().appendShareImg();

    var ACCEPT_METHOD = {
        SHOW: 'show',
        SEARCH: 'search',
        RANK: 'rank'
    }
    var SEARCH_KEY = {
        NAME: 'name',
        ID: 'id',
        PHONE: 'phone'
    }
    var VOTE_PAGE_KEY = 'profile'; // 这个和当前页面vote.html相同, 其实是由后台路由决定的

    var currentPage = 1; // 当前页面
    var totalPage = 1; // 包含结果的所有页面
    var currentMethod = ACCEPT_METHOD.SHOW; // 当前调用方法
    var key = 'undefined';
    var value = 'undefined';

    getData(resetPage);

    /**
     * 通过若干参数,请求后台数据
     * @param callback
     */
    function getData(callback) {
        $.get(VOTE_PAGE_KEY, {
            method : currentMethod,
            page : currentPage,
            key : key,
            value : value
        }, function(data) {
            callback(data);
        })
    }

    /**
     * 不用刷新页面刷新页面资料, 应该刷新的数据有
     * 1. 页面model的信息, 包括图片, 编号(id), 名字(name), 投票人数(vote-num), 是否已经投票(is-vote)等
     * 2. 页面其他信息, 包括当前页数, 总页数等
     * @param data
     */
    function resetPage(data) {

        // 页面其他信息, 页数, 总页数
        currentPage = data.page;
        totalPage = data.totalPage;
        $('#page-num').val(currentPage);
        $('#total-page').text(totalPage);

        // 页面model信息
        var users = data.users;
        var voteIDs = data.voteIDs;
        var list = $('#model-list');

        // 清空所有原先的数据
        list.html('');

        // 重新填充数据
        for(var i= 0, len = users.length; i< len; i++) {
            var user = users[i];
            var user_id = user.id;
            // dom操作
            list.append(
                '<li class="model">' +
                    '<a class="model_img" href="/details.html?userId=' + user_id + '">' +
                        '<img src="/uploads/img/'+ user.first_img_name + '">' +
                    '</a>' +
                    '<div>' +
                        '<span class="vote_name">' + user_id + '号 ' + user.name + '</span>' +
                        '<a class="bottom_a vote" id="' + user_id + '">' +
                            '<span class="vote-tips" id="vote_click">点击投票</span>' +
                            '<span class="vote_heart">' +
                                '<img class="imgflag" src="img/heart_w.png">' +
                            '</span>' +
                            '<span class="vote-num">' + user.vote_num + '</span>' +
                        '</a>' +
                    '</div>' +
                '</li>'
            );

            // 根据投票情况获取微信用户是否为相应的model投票
            for(var j= 0, vote_len = voteIDs.length; j<vote_len; j++) {
                if(voteIDs[j] == user_id) {
                    $('#' + user_id).addClass('vote-love');
                }
            }
            refreshLove();
        }
        bindVote();
    }

    // 获取条件查询结果
    $('#search').on('click', function() {
        currentMethod = ACCEPT_METHOD.SEARCH;
        currentPage = 1;
        value = $('#search-content').val().trim();
        if(value === '') {
            covering().show({
                txt: '请输入查询条件',
            });
        }else {
            key =  parseInt(value) ? (value.length < 6 ? SEARCH_KEY.ID : SEARCH_KEY.PHONE) : (SEARCH_KEY.NAME);
            getData(resetPage);
        }
    });

    // 获取排名查询结果
    $('#rank').on('click', function() {
        currentMethod = ACCEPT_METHOD.RANK;
        currentPage = 1;
        getData(resetPage);
    });

    // 点击上一页,执行的操作
    $('#pre-page').on('click', function() {
        var tempPage = currentPage - 1;
        if(tempPage <= 0) {
            covering().show({txt: '已是第一页'});
        } else {
            currentPage = tempPage;
            getData(resetPage)
        }
    });

    // 点击下一页, 执行下一页的操作
    $('#next-page').on('click', function() {
        var tempPage = currentPage + 1;
        if(tempPage > totalPage) {
            covering().show({txt: '已经是最后一页了'});
        } else {
            currentPage = tempPage;
            getData(resetPage)
        }
    });

    $('#page-num').on('blur', function() {
        var toPage = $(this).val();
        if(toPage != currentPage) {
            console.log(toPage);
            if(toPage > totalPage) {
                covering().show({txt: '跳转页面不可大于总页数'});
            }else {
                currentPage = toPage;
                getData(resetPage);
            }
        }
    })
})

/**
 * 投票的js, 该段js(对应后台)需要实现基本功能
 * 1. 获取微信用户的唯一标识符
 * 2. 确保一个id每天只能投三票, 且对应不同的选手
 * 3. 确保每投一票, 会增加一次抽奖的机会
 * 4. 每抽一次奖, 需记录抽奖记录, 以便真正抽到实物奖励时, 能联系客服
  */
function getUrlParam(name)
{
    var reg = new RegExp("(^|&)"+ name +"=([^&]*)(&|$)"); //构造一个含有目标参数的正则表达式对象
    var r = window.location.search.substr(1).match(reg);  //匹配目标参数
    if(r !== null) {
        return r[2];
    }
}

/**
 * 绑定点击投票事件
 */
function bindVote() {
    // 每次获取资料后,重新绑定
    $('.vote').on('click', function() {
        var id = $(this).attr('id');
        // 投票的ajax操作
        $.post('/vote/'+id, function(data) {
            if(data.status === 0) {
                // 提交成功
                covering().show({txt: '投票成功'}, function(){
                    // 没缓存情况下,reload对手机还是服务器消耗过多,故取消;
                    // location.reload();]
                    var that = $('#' + id);
                    var voteNum = that.find('.vote-num');
                    that.addClass('vote-love');
                    console.log(voteNum.text());
                    voteNum.text(parseInt(voteNum.text()) + 1);
                    refreshLove();
                });
            }else if(data.status === 1){
                // 告知投票失败的原因
                covering().show({
                    txt: data.msg,
                });
            }
        })
    })
}

function refreshLove() {
    $('.vote-love span.vote-tips').text('已投票');
    $('.vote-love img.imgflag').attr('src', 'img/heart_r.png')
}
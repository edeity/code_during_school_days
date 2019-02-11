(function (doc, win) {
    // 获取根元素
    var docEl = doc.documentElement,
        // 是否支持orientantionchange事件
        resizeEvt = 'orientationchange' in window ? 'orientationchange' : 'resize',
    // 根据客户端长度更改,该长度应该以320px为基准
        recalc = function () {
            var clientWidth = docEl.clientWidth;
            if (!clientWidth) return;
            docEl.style.fontSize = 10 * (clientWidth / 320) + 'px'
        };

    if (!doc.addEventListener) return;
    // 注册相应的事件
    win.addEventListener(resizeEvt, recalc, false);
    doc.addEventListener('DOMContentLoaded', recalc, false);
})(document, window);

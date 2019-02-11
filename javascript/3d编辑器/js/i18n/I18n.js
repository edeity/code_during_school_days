/**
 * Created by Javer on 2015/7/1.
 */
//语言包(json格式)，ajax加载
var I18n = function(editor) {
};

I18n.prototype.getLanguage = function(language, callback) {
    if(language == 'chn') {

        getLanguageFromServer('./js/i18n/chn.json');

    }else if(language == 'en') {

        getLanguageFromServer('./js/i18n/en.json');

    }

    //就目前而言,利用了Jquery
    //但若在后来编码中没有在其他地方用到jquery,应更改为原生js实现
    //否则,将严重影响网络(Jquery还是太大了)
    function getLanguageFromServer(path) {
        $.get(path, function(result) {
            editor.l = result;
            callback();
        })
    }

};
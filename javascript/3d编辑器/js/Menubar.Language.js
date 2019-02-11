/**
 * Created by Javer on 2015/7/1.
 */

//添加设置编辑器语言的按钮,若要扩展该功能(实现其他国家的语言),
//1.将./i18n的en.json进行复制,对相应的值进行翻译,可参考./i18n的chn.json文件
//2.在该代码最后添加相应的按钮
//3.将文件路径下载路径添加到./i18n/I18n.js中
Menubar.Language = function ( editor ) {
    var l = editor.l.menubar['Language.js'];

    var container = new UI.Panel();
    container.setClass( 'menu' );

    var title = new UI.Panel();
    title.setClass( 'title' );
    title.setTextContent( l['Language'] );
    container.add( title );

    var options = new UI.Panel();
    options.setClass( 'options' );
    container.add( options );

    // 中文简体
    var option = new UI.Panel();
    option.setClass( 'option' );
    option.setTextContent( '中文简体' );
    option.onClick( function () {
        editor.config.setKey('language', 'chn');
        location.reload();
    } );
    options.add( option );
    //英文
    var option = new UI.Panel();
    option.setClass('option');
    option.setTextContent( 'English' );
    option.onClick( function () {
        editor.config.setKey('language', 'en');
        location.reload();
    } );
    options.add( option );
    //其他语言

    return container;

};

/**
 * Created by edeity on 15/7/15.
 * 实现添加文字的面板
 */
Sidebar.Text = function ( editor ) {
    //var l = editor.l.sidebar['Scene.js'];

    var signals = editor.signals;

    var container = new UI.CollapsiblePanel();
    container.setCollapsed( editor.config.getKey( 'ui/sidebar/text/collapsed' ) );
    container.onCollapsedChange( function ( boolean ) {

        editor.config.setKey( 'ui/sidebar/text/collapsed', boolean );

    } );

    container.addStatic( new UI.Text('Text') );
    container.add( new UI.Break() );

    var textRow = new UI.Panel();
    var textInput = new UI.Input('');
    var textBtn = new UI.Button("生成文字");
    textBtn.onClick(function(){
        //生成文字
        var text = textInput.getValue();
        var shape = new THREE.TextGeometry( text, {
            size: 40,
            height: 20,
            curveSegments: 3,
            font: 'helvetiker',
            bevelThickness: 3,
            bevelSize: 3,
            bevelEnabled: true
        });
        var wrapper = new THREE.MeshNormalMaterial({color: 0x00ff00});
        var words = new THREE.Mesh(shape, wrapper);

        editor.addObject( words);
        editor.select(words);
    });

    textRow.add(textInput);
    textRow.add(textBtn);
    container.add( textRow );

    return container;

};

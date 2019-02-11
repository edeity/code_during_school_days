/**
 * @author mrdoob / http://mrdoob.com/
 */

Menubar.Edit = function ( editor ) {
	var l = editor.l.menubar["Edit.js"];

	var container = new UI.Panel();
	container.setClass( 'menu' );

	var title = new UI.Panel();
	title.setClass( 'title' );
	title.setTextContent( l['Edit'] );
	container.add( title );

	var options = new UI.Panel();
	options.setClass( 'options' );
	container.add( options );

	// Clone

	var option = new UI.Panel();
	option.setClass( 'option' );
	option.setTextContent( l['Clone'] );
	option.onClick( function () {

		var object = editor.selected;

		if ( object.parent === undefined ) return; // avoid cloning the camera or scene

		object = object.clone();

		editor.addObject( object );
		editor.select( object );

	} );
	options.add( option );

	// Delete

	var option = new UI.Panel();
	option.setClass( 'option' );
	option.setTextContent( l['Delete'] );
	option.onClick( function () {
	
		var object = editor.selected;

		if ( confirm( 'Delete ' + object.name + '?' ) === false ) return;

		var parent = object.parent;
		editor.removeObject( object );
		editor.select( parent );

	} );
	options.add( option );

	return container;

};

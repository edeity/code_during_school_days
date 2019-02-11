/**
 * @author mrdoob / http://mrdoob.com/
 */

var Menubar = function ( editor ) {

	var container = new UI.Panel();
	container.setId( 'menubar' );

	container.add( new Menubar.File( editor ) );
	container.add( new Menubar.Edit( editor ) );
	container.add( new Menubar.Add( editor ) );;
	container.add( new Menubar.View( editor ) );
	container.add( new Menubar.Status( editor ) );
	//添加翻译功能
	container.add( new Menubar.Language(editor));

	return container;

};

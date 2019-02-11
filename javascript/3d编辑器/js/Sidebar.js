/**
 * @author mrdoob / http://mrdoob.com/
 */

var Sidebar = function ( editor ) {

	var container = new UI.Panel();
	container.setId( 'sidebar' );

	container.add( new Sidebar.Scene( editor ) );
	container.add( new Sidebar.Material( editor ) );
    container.add(new Sidebar.Text(editor));

	return container;

};

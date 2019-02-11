/**
 * @author mrdoob / http://mrdoob.com/
 */

var Toolbar = function ( editor ) {
	var l = editor.l.toolbar["Toolbar.js"];

	var signals = editor.signals;

	var container = new UI.Panel();
	container.setId( 'toolbar' );

	var buttons = new UI.Panel();
	container.add( buttons );

	// translate / rotate / scale

	var translate = new UI.Button( l['translate'] ).onClick( function () {

		signals.transformModeChanged.dispatch( 'translate' );

	} );
	buttons.add( translate );

	var rotate = new UI.Button( l['rotate'] ).onClick( function () {

		signals.transformModeChanged.dispatch( 'rotate' );

	} );
	buttons.add( rotate );

	var scale = new UI.Button( l['scale'] ).onClick( function () {

		signals.transformModeChanged.dispatch( 'scale' );

	} );
	buttons.add( scale );

	// grid

	var grid = new UI.Number( 25 ).onChange( update );
	grid.dom.style.width = '42px';
	buttons.add( new UI.Text( l['Grid: '] ) );
	buttons.add( grid );

	var showGrid = new UI.Checkbox().onChange( update ).setValue( true );
	buttons.add( showGrid );
	buttons.add( new UI.Text( l['show'] ) );

	//锁定大小
	var lockScale = new UI.Checkbox().onChange(function() {
		editor.editorControl.zoomable = !editor.editorControl.zoomable;
	}).setValue(false);
	buttons.add(lockScale);
	buttons.add( new UI.Text('锁定大小'));

	//锁定视角
	var lockAnggle = new UI.Checkbox().onChange(function() {
		editor.editorControl.rotateable = !editor.editorControl.rotateable;
	}).setValue(false);
	buttons.add(lockAnggle);
	buttons.add(new UI.Text('锁定角度'));

	function update() {
		signals.showGridChanged.dispatch( showGrid.getValue() );

	}

	return container;

}

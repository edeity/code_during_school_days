/**
 * @author mrdoob / http://mrdoob.com/
 */

Menubar.Add = function ( editor ) {
	var l = editor.l.menubar["Add.js"];
	var container = new UI.Panel();
	container.setClass( 'menu' );

	var title = new UI.Panel();
	title.setClass( 'title' );
	title.setTextContent( l['Add'] );
	container.add( title );

	var options = new UI.Panel();
	options.setClass( 'options' );
	container.add( options );

	//

	var meshCount = 0;
	var lightCount = 0;
	var cameraCount = 0;

	editor.signals.editorCleared.add( function () {

		meshCount = 0;
		lightCount = 0;
		cameraCount = 0;

	} );

	// Group:分组

	var option = new UI.Panel();
	option.setClass( 'option' );
	option.setTextContent( l['Group'] );
	option.onClick( function () {

		var mesh = new THREE.Group();
		mesh.name = 'Group ' + ( ++ meshCount );

		editor.addObject( mesh );
		editor.select( mesh );

	} );
	options.add( option );

	//

	options.add( new UI.HorizontalRule() );

	// Plane:平面

	var option = new UI.Panel();
	option.setClass( 'option' );
	option.setTextContent( l['Plane'] );
	option.onClick( function () {

		var width = 200;
		var height = 200;

		var widthSegments = 1;
		var heightSegments = 1;

		var geometry = new THREE.PlaneGeometry( width, height, widthSegments, heightSegments );
		var material = new THREE.MeshPhongMaterial();
		var mesh = new THREE.Mesh( geometry, material );
		mesh.name = 'Plane ' + ( ++ meshCount );

		editor.addObject( mesh );
		editor.select( mesh );

	} );
	options.add( option );

	// Box:盒子

	var option = new UI.Panel();
	option.setClass( 'option' );
	option.setTextContent( l['Box'] );
	option.onClick( function () {

		var width = 100;
		var height = 100;
		var depth = 100;

		var widthSegments = 1;
		var heightSegments = 1;
		var depthSegments = 1;

		var geometry = new THREE.BoxGeometry( width, height, depth, widthSegments, heightSegments, depthSegments );
		var mesh = new THREE.Mesh( geometry, new THREE.MeshPhongMaterial() );
		mesh.name = 'Box ' + ( ++ meshCount );

		editor.addObject( mesh );
		editor.select( mesh );

	} );
	options.add( option );

	// Circle:圆

	var option = new UI.Panel();
	option.setClass( 'option' );
	option.setTextContent( l['Circle'] );
	option.onClick( function () {

		var radius = 20;
		var segments = 32;

		var geometry = new THREE.CircleGeometry( radius, segments );
		var mesh = new THREE.Mesh( geometry, new THREE.MeshPhongMaterial() );
		mesh.name = 'Circle ' + ( ++ meshCount );

		editor.addObject( mesh );
		editor.select( mesh );

	} );
	options.add( option );

	// Cylinder:圆柱

	var option = new UI.Panel();
	option.setClass( 'option' );
	option.setTextContent( l['Cylinder'] );
	option.onClick( function () {

		var radiusTop = 20;
		var radiusBottom = 20;
		var height = 100;
		var radiusSegments = 32;
		var heightSegments = 1;
		var openEnded = false;

		var geometry = new THREE.CylinderGeometry( radiusTop, radiusBottom, height, radiusSegments, heightSegments, openEnded );
		var mesh = new THREE.Mesh( geometry, new THREE.MeshPhongMaterial() );
		mesh.name = 'Cylinder ' + ( ++ meshCount );

		editor.addObject( mesh );
		editor.select( mesh );

	} );
	options.add( option );

	// Sphere:球

	var option = new UI.Panel();
	option.setClass( 'option' );
	option.setTextContent( l['Sphere'] );
	option.onClick( function () {

		var radius = 75;
		var widthSegments = 32;
		var heightSegments = 16;
		var phiStart = 0;
		var phiLength = Math.PI * 2;
		var thetaStart = 0;
		var thetaLength = Math.PI;

		var geometry = new THREE.SphereGeometry( radius, widthSegments, heightSegments, phiStart, phiLength, thetaStart, thetaLength );
		var mesh = new THREE.Mesh( geometry, new THREE.MeshPhongMaterial() );
		mesh.name = 'Sphere ' + ( ++ meshCount );

		editor.addObject( mesh );
		editor.select( mesh );

	} );
	options.add( option );

	// Icosahedron:二十面体

	var option = new UI.Panel();
	option.setClass( 'option' );
	option.setTextContent( l['Icosahedron'] );
	option.onClick( function () {

		var radius = 75;
		var detail = 2;

		var geometry = new THREE.IcosahedronGeometry( radius, detail );
		var mesh = new THREE.Mesh( geometry, new THREE.MeshPhongMaterial() );
		mesh.name = 'Icosahedron ' + ( ++ meshCount );

		editor.addObject( mesh );
		editor.select( mesh );

	} );
	options.add( option );

	// Torus:圆环面

	var option = new UI.Panel();
	option.setClass( 'option' );
	option.setTextContent( l['Torus'] );
	option.onClick( function () {

		var radius = 100;
		var tube = 40;
		var radialSegments = 8;
		var tubularSegments = 6;
		var arc = Math.PI * 2;

		var geometry = new THREE.TorusGeometry( radius, tube, radialSegments, tubularSegments, arc );
		var mesh = new THREE.Mesh( geometry, new THREE.MeshPhongMaterial() );
		mesh.name = 'Torus ' + ( ++ meshCount );

		editor.addObject( mesh );
		editor.select( mesh );

	} );
	options.add( option );

	// TorusKnot:换面扭结

	var option = new UI.Panel();
	option.setClass( 'option' );
	option.setTextContent( l['TorusKnot'] );
	option.onClick( function () {

		var radius = 100;
		var tube = 40;
		var radialSegments = 64;
		var tubularSegments = 8;
		var p = 2;
		var q = 3;
		var heightScale = 1;

		var geometry = new THREE.TorusKnotGeometry( radius, tube, radialSegments, tubularSegments, p, q, heightScale );
		var mesh = new THREE.Mesh( geometry, new THREE.MeshPhongMaterial() );
		mesh.name = 'TorusKnot ' + ( ++ meshCount );

		editor.addObject( mesh );
		editor.select( mesh );

	} );
	options.add( option );

	// Sprite

	var option = new UI.Panel();
	option.setClass( 'option' );
	option.setTextContent( l['Sprite'] );
	option.onClick( function () {

		var sprite = new THREE.Sprite( new THREE.SpriteMaterial() );
		sprite.name = 'Sprite ' + ( ++ meshCount );

		editor.addObject( sprite );
		editor.select( sprite );

	} );
	options.add( option );
	return container;

}

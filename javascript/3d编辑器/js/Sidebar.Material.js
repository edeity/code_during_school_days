/**
 * @author mrdoob / http://mrdoob.com/
 */

//在本次需求中,仅仅需要更改物体的颜色和进行简单的全局贴图,故删除了原有的大量功能
Sidebar.Material = function ( editor ) {
	var l = editor.l.sidebar['Material.js'];

	var signals = editor.signals;

	//原本编辑器中,可以选定类型,现仅支持自动判断
	var materialClasses = {
		'LineBasicMaterial': THREE.LineBasicMaterial,
		'LineDashedMaterial': THREE.LineDashedMaterial,
		'MeshBasicMaterial': THREE.MeshBasicMaterial,
		'MeshDepthMaterial': THREE.MeshDepthMaterial,
		'MeshFaceMaterial': THREE.MeshFaceMaterial,
		'MeshLambertMaterial': THREE.MeshLambertMaterial,
		'MeshNormalMaterial': THREE.MeshNormalMaterial,
		'MeshPhongMaterial': THREE.MeshPhongMaterial,
		'PointCloudMaterial': THREE.PointCloudMaterial,
		'ShaderMaterial': THREE.ShaderMaterial,
		'SpriteMaterial': THREE.SpriteMaterial,
		'SpriteCanvasMaterial': THREE.SpriteCanvasMaterial,
		'Material': THREE.Material
	};

	//总得分类
	var container = new UI.CollapsiblePanel();
	container.setCollapsed( editor.config.getKey( 'ui/sidebar/material/collapsed' ) );
	container.onCollapsedChange( function ( boolean ) {

		editor.config.setKey( 'ui/sidebar/material/collapsed', boolean );

	} );
	container.setDisplay( 'none' );
	container.dom.classList.add( 'Material' );

	container.addStatic( new UI.Text().setValue( l['MATERIAL'] ) );
	container.add( new UI.Break() );

	//在原来编辑器重用到,但在本次需求中没有用到的关于3d模型的属性
	var materialUUID = new UI.Input();
	var materialClass = new UI.Select().setOptions( {
		'LineBasicMaterial': 'LineBasicMaterial',
		'LineDashedMaterial': 'LineDashedMaterial',
		'MeshBasicMaterial': 'MeshBasicMaterial',
		'MeshDepthMaterial': 'MeshDepthMaterial',
		'MeshFaceMaterial': 'MeshFaceMaterial',
		'MeshLambertMaterial': 'MeshLambertMaterial',
		'MeshNormalMaterial': 'MeshNormalMaterial',
		'MeshPhongMaterial': 'MeshPhongMaterial',
		'ShaderMaterial': 'ShaderMaterial',
		'SpriteMaterial': 'SpriteMaterial'
	});
	var materialEmissiveRow = new UI.Panel;

	// 颜色选择器
	var materialColorRow = new UI.Panel();
	var materialColor = new UI.Color().onChange( update );
    //因为不用设置自发光,所以将其和颜色同步,为了能在编辑器中表现出来

	materialColorRow.add( new UI.Text( l['Color'] ).setWidth( '90px' ) );
	materialColorRow.add( materialColor );

	container.add( materialColorRow );

	// 贴图,在当前需求中仅仅需要能够显示贴图
	var materialMapRow = new UI.Panel();
	var materialMapEnabled = new UI.Checkbox( false ).onChange( update );
	var materialMap = new UI.Texture().onChange( update );

	materialMapRow.add( new UI.Text( l['Map'] ).setWidth( '90px' ) );
	materialMapRow.add( materialMapEnabled );
	materialMapRow.add( materialMap );

	container.add( materialMapRow );

	//更新值得操作
	function update() {

		var object = editor.selected;

		var geometry = object.geometry;
		var material = object.material;

		var textureWarning = false;
		var objectHasUvs = false;

		if ( object instanceof THREE.Sprite ) objectHasUvs = true;
		if ( geometry instanceof THREE.Geometry && geometry.faceVertexUvs[ 0 ].length > 0 ) objectHasUvs = true;
		if ( geometry instanceof THREE.BufferGeometry && geometry.attributes.uv !== undefined ) objectHasUvs = true;

		if ( material ) {
			if ( material.uuid !== undefined ) {material.uuid = materialUUID.getValue();}
			if ( material instanceof materialClasses[ materialClass.getValue() ] === false ) {
				material = new materialClasses[ materialClass.getValue() ]();
				object.material = material;
			}
			if ( material.color !== undefined ) {
				material.color.setHex( materialColor.getHexValue() );
                material.emissive.setHex(materialColor.getHexValue());
			}

			//edeity :map
			if ( material.map !== undefined ) {
				var mapEnabled = materialMapEnabled.getValue() === true;
				if ( objectHasUvs ) {
					material.map = mapEnabled ? materialMap.getValue() : null;
					material.needsUpdate = true;
				} else {
					if ( mapEnabled ) textureWarning = true;
				}
			}

			updateRows();

			signals.materialChanged.dispatch( material );

		}

		if ( textureWarning ) {console.warn( "Can't set texture, model doesn't have texture coordinates" );}
	};

	function updateRows() {

		var properties = {
			'color': materialColorRow,
			'emissive': materialEmissiveRow,
			'map': materialMapRow
			};

		var material = editor.selected.material;

		for ( var property in properties ) {
			properties[ property ].setDisplay( material[ property ] !== undefined ? '' : 'none' );
		}

	};

	// events

	signals.objectSelected.add( function ( object ) {

		if ( object && object.material ) {

			container.setDisplay( '' );

			var material = object.material;

			materialClass.setValue( material.type );
			if ( material.color !== undefined ) {materialColor.setHexValue( material.color.getHexString() );}
			if ( material.map !== undefined ) {
				materialMapEnabled.setValue( material.map !== null );
				materialMap.setValue( material.map );
			}

			updateRows();

		} else {

			container.setDisplay( 'none' );

		}

	} );

	return container;

}

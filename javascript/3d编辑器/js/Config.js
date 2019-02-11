/**
 * @author mrdoob / http://mrdoob.com/
 */

var Config = function () {

	var name = 'threejs-editor';

	var storage = {
		'autosave': true,
		'theme': 'css/light.css',
		'language': 'chn',
		'project/renderer': 'CanvasRenderer',
		'project/renderer/antialias': true,
		'project/vr': false,

		'texture/camera': true,
		'texture/brash' : 'marker',

		'camera/position': [ 500, 250, 500 ],
		'camera/target': [ 0, 0, 0 ],

		'ui/sidebar/material/collapsed': true,
		'ui/sidebar/scene/collapsed': false,
        'ui/sidebar/text/collapsed' : false
	};

	if ( window.localStorage[ name ] === undefined ) {

		window.localStorage[ name ] = JSON.stringify( storage );

	} else {

		var data = JSON.parse( window.localStorage[ name ] );

		for ( var key in data ) {

			storage[ key ] = data[ key ];

		}

	}

	return {

		getKey: function ( key ) {

			return storage[ key ];

		},

		setKey: function () { // key, value, key, value ...

			for ( var i = 0, l = arguments.length; i < l; i += 2 ) {

				storage[ arguments[ i ] ] = arguments[ i + 1 ];

			}

			window.localStorage[ name ] = JSON.stringify( storage );

			console.log( '[' + /\d\d\:\d\d\:\d\d/.exec( new Date() )[ 0 ] + ']', 'Saved config to LocalStorage.' );

		},

		clear: function () {

			delete window.localStorage[ name ];

		}

	}

};

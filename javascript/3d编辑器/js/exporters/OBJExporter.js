/**
 * @author mrdoob / http://mrdoob.com/
 */

THREE.OBJExporter = function () {};

THREE.OBJExporter.prototype = {

	constructor: THREE.OBJExporter,

	parse: function ( object ) {

		var output = '';

		var indexVertex = 0;
		var indexVertexUvs = 0
		var indexNormals = 0;

		var parseObject = function ( child ) {

			var nbVertex = 0;
			var nbVertexUvs = 0;
			var nbNormals = 0;

			var geometry = child.geometry;

			/*
			* 修复工作 : Start
			* 由于导入obj文件时，数组在内存存在的形式是BufferGeometry（点线面等信息由数组表示）
			* 而在原OBJExporter中，仅支持导出Geometry（点线面信息由Vertox3表示）
			* 故将BufferGeometry转化为Geometry
			*/
			if(geometry instanceof THREE.BufferGeometry) {
				//利用原BufferGeometry数组新建Geometry并赋值给原引用
				geometry = new THREE.Geometry().fromBufferGeometry(geometry);
			}
			/*
			* 在OBJLoader（详情见../examples/loader/OBJLoader）中，由于正则匹配，'o'后必须有文件名（child.name属性）
			* 然而BufferGeometry转化的Geometry没有文件名，因此导致导入不成功，故在此添加文件名
			*/
			child.name = child.name ? child.name : 'undefine';
			/*修复工作 ：End*/

			if ( geometry instanceof THREE.Geometry ) {

				output += 'o ' + child.name + '\n';

				for ( var i = 0, l = geometry.vertices.length; i < l; i ++ ) {

					var vertex = geometry.vertices[ i ].clone();
					vertex.applyMatrix4( child.matrixWorld );

					output += 'v ' + vertex.x + ' ' + vertex.y + ' ' + vertex.z + '\n';

					nbVertex ++;

				}

				// uvs

				for ( var i = 0, l = geometry.faceVertexUvs[ 0 ].length; i < l; i ++ ) {

					var vertexUvs = geometry.faceVertexUvs[ 0 ][ i ];

					for ( var j = 0; j < vertexUvs.length; j ++ ) {

						var uv = vertexUvs[ j ];
						vertex.applyMatrix4( child.matrixWorld );

						output += 'vt ' + uv.x + ' ' + uv.y + '\n';

						nbVertexUvs ++;

					}

				}

				// normals

				for ( var i = 0, l = geometry.faces.length; i < l; i ++ ) {

					var normals = geometry.faces[ i ].vertexNormals;

					for ( var j = 0; j < normals.length; j ++ ) {

						var normal = normals[ j ];
						output += 'vn ' + normal.x + ' ' + normal.y + ' ' + normal.z + '\n';

						nbNormals ++;

					}

				}

				// faces

				for ( var i = 0, j = 1, l = geometry.faces.length; i < l; i ++, j += 3 ) {

					var face = geometry.faces[ i ];

					output += 'f ';
					output += ( indexVertex + face.a + 1 ) + '/' + ( indexVertexUvs + j ) + '/' + ( indexNormals + j ) + ' ';
					output += ( indexVertex + face.b + 1 ) + '/' + ( indexVertexUvs + j + 1 ) + '/' + ( indexNormals + j + 1 ) + ' ';
					output += ( indexVertex + face.c + 1 ) + '/' + ( indexVertexUvs + j + 2 ) + '/' + ( indexNormals + j + 2 ) + '\n';

				}

			}

			// update index
			indexVertex += nbVertex;
			indexVertexUvs += nbVertexUvs;
			indexNormals += nbNormals;

		};

		object.traverse( parseObject );

		return output;

	}

};

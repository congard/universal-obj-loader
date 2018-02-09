// Main file of congard OBJLoader
// Version 1.0.1
// 9 feb 2018 18:58
// dbcongard@gmail.com
// t.me/congard
// congard.pp.ua
// GitHub page: https://github.com/congard/universal-obj-loader

package free.lib.congard.objloader;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStreamReader;

import free.lib.congard.objloader.CgrTools.CgrTimeMeter;

public class Model {
	public float[] vertices, texCoords, normals;
	public VerticesDescriptor[] vd;
	public float minX, maxX, minY, maxY, minZ, maxZ;
	public int POLY_TYPE_TRIANGLES = 0, POLY_TYPE_QUADS = 1, POLY_TYPE_POLYGON = 2;
	public OBJLoader loader;
	private File pathToOBJ;
	
	public Model(File pathToOBJ) {
		this.pathToOBJ = pathToOBJ;
		loader = new OBJLoader(this);
	}
	
	// [RU] Отправляет на парсинг файл
	// [EN] Start parsing
	// ---Начало работы---
	// ---Begin---
	public void load() {
		CgrTimeMeter ctm = new CgrTimeMeter("Model.load");
		ctm.start();
		try {
			loader.load(new BufferedReader(new InputStreamReader(new FileInputStream(pathToOBJ))));
		} catch (FileNotFoundException e) {
			System.out.println("FileNotFoundException >> Error loading model: " + e);
		} catch (IOException e) {
			System.out.println("IOException >> Error loading model: " + e);
		} catch (Exception e) {
			e.printStackTrace();
			System.out.println("Exception >> Error loading model: " + e);
		}
		ctm.end();
	}
	// ---End---
	// ---Конец работы---
	
	// [RU] Очищает массивы чтобы меньше занимали в памяти (можно вызывать после Model.convertToFloatArray)
	// [EN] Clears arrays to consume less memory (can be called after the Model.convertToFloatArray)
	public void cleanup() {
		CgrTimeMeter ctm = new CgrTimeMeter("Model.cleanup");
		ctm.start();
		loader.faces.clear();
		loader.facesnorms.clear();
		loader.facestexs.clear();
		loader.vertexsets.clear();
		loader.vertexsetsnorms.clear();
		loader.vertexsetstexs.clear();
		ctm.end();
	}
	
	// [RU] Создает примитивные массивы для дальнейшей отрисовки
	// [EN] Creates primitive arrays for further rendering
	public void convertToFloatArrays(boolean isMakeNormalsArray, boolean isMakeTexCoordsArray) {
		CgrTimeMeter ctm = new CgrTimeMeter("Model.convertToFloatArrays");
		ctm.start();
		loader.convertToArrays(isMakeNormalsArray, isMakeTexCoordsArray);
		ctm.end();
	}
	
	// >>
	public void enable(int funct) {
		if (funct == LoaderConstants.ONE_MINUS_TEX_COORD) loader.texA = 1;
		else if (funct == LoaderConstants.TEX_VERTEX_2D || funct == LoaderConstants.TEX_VERTEX_3D) loader.TEX_MODE = funct;
	}
	
	public void disable(int funct) {
		if (funct == LoaderConstants.ONE_MINUS_TEX_COORD) loader.texA = 0;
		else System.out.println("Can't disable this function");
	}
	// <<
	
	// [RU] Устанавливает типы полигонов которые используются для отрисовки
	// [RU] Например, чтобы при отрисовке не делать проверки на то, что это за тип, можно предварительно задать
	// [EN] Sets the types of polygons used for rendering
	// [EN] For example, if you do not make a check for what type it is, you can predefine
	// setDefaultPolyTypes(GL2.GL_TRIANGLES, GL2.GL_QUADS, GL2.GL_POLYGON)
	public void setDefaultPolyTypes(int POLY_TYPE_TRIANGLES, int POLY_TYPE_QUADS, int POLY_TYPE_POLYGON) {
		this.POLY_TYPE_TRIANGLES = POLY_TYPE_TRIANGLES;
		this.POLY_TYPE_QUADS = POLY_TYPE_QUADS;
		this.POLY_TYPE_POLYGON = POLY_TYPE_POLYGON;
	}
	
	// [RU] Класс, который хранит данные о вершинах и нужен при отрисовке
	// [EN] A class that stores data about vertices and is needed when rendering
	public static class VerticesDescriptor {
		public static final int UNDEFINED_POLY_TYPE = -1;
		public int POLYTYPE = UNDEFINED_POLY_TYPE, START = 0, END = 0;
	}
}

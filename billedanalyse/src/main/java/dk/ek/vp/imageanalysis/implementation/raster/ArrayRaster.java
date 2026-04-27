package dk.ek.vp.imageanalysis.implementation.raster;

import dk.ek.vp.imageanalysis.interfaces.GridGeometry;
import dk.ek.vp.imageanalysis.interfaces.Raster;

public class ArrayRaster implements Raster {
    private double[][] rasterArray;
    private GridGeometry geometry;

    public ArrayRaster(double[][] array, GridGeometry geometry) {
        this.rasterArray = array;
        this.geometry = geometry;
    }

    @Override
    public double getValue(int x, int y) {
        return rasterArray[y][x];
    }

    @Override
    public GridGeometry getGeometry() {
        return geometry;
    }

    @Override
    public int width() {
        return rasterArray[0].length;
    }

    @Override
    public int height() {
        return rasterArray.length;
    }
}

package dk.ek.vp.imageanalysis.implementation.raster;

import dk.ek.vp.imageanalysis.interfaces.GridGeometry;
import dk.ek.vp.imageanalysis.interfaces.Raster;
/**
 * Raster implementation - fully materialized
 * two-dimensional array in memory.
 *
 * ArrayRaster provides eager access to raster values and
 * serves as the primary concrete raster representation
 * for loaded or materialized datasets.
 *
 * Pixel values are stored in row-major order:
 *
 * rasterArray[y][x]
 *
 * where:
 * - x represents the column index
 * - y represents the row index
 */
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

package dk.ek.vp.imageanalysis.implementation.raster.vrttiles;

import dk.ek.vp.imageanalysis.interfaces.GridGeometry;
import dk.ek.vp.imageanalysis.interfaces.Raster;

import java.util.List;

public class VrtRaster implements Raster {

    private final List<Raster> children;
    private final int width;
    private final int height;

    public VrtRaster(List<Raster> children) {
        this.children = children;

        int maxX = 0;
        int maxY = 0;

        for (Raster r : children) {
            if (r instanceof RasterTile t) {
                maxX = Math.max(maxX, t.width() + t.tile.xOffset());
                maxY = Math.max(maxY, t.height() + t.tile.yOffset());
            }
        }

        this.width = maxX;
        this.height = maxY;
    }

    @Override
    public double getValue(int x, int y) {

        for (Raster r : children) {

            if (r instanceof RasterTile t && t.contains(x, y)) {
                return t.getValue(x, y);
            }
        }

        return Double.NaN;
    }

    @Override
    public int width() {
        return width;
    }

    @Override
    public int height() {
        return height;
    }

    @Override
    public GridGeometry getGeometry() {
        return null; // simplificeret
    }
    public List<Raster> getChildren() {
        return children;
    }
}
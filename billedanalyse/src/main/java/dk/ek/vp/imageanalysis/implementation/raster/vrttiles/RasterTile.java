package dk.ek.vp.imageanalysis.implementation.raster.vrttiles;

import dk.ek.vp.imageanalysis.interfaces.GridGeometry;
import dk.ek.vp.imageanalysis.interfaces.Raster;
import dk.ek.vp.imageanalysis.interfaces.RasterReader;

public class RasterTile implements Raster {
    final VrtTile tile;
    private final RasterReader reader;

    private Raster loaded; // lazy read

    public RasterTile(VrtTile tile, RasterReader reader) {
        this.tile = tile;
        this.reader = reader;
    }

    private Raster getLoaded() {
        if (loaded == null) {
            try {
                System.out.println("Loading tile: " + tile.path());
                loaded = reader.read(tile.path());
            } catch (Exception e) {
                throw new RuntimeException(e);
            }
        }
        return loaded;
    }

    public boolean contains(int x, int y) {
        return x >= tile.xOffset() &&
                y >= tile.yOffset() &&
                x < tile.xOffset() + tile.width() &&
                y < tile.yOffset() + tile.height();
    }

    @Override
    public double getValue(int x, int y) {
        Raster r = getLoaded();

        int localX = x - tile.xOffset();
        int localY = y - tile.yOffset();

        return r.getValue(localX, localY);
    }

    @Override
    public int width() {
        return tile.width();
    }

    @Override
    public int height() {
        return tile.height();
    }

    @Override
    public GridGeometry getGeometry() {
        return null; // simplificeret
    }
}

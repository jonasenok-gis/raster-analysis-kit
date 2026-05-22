package dk.ek.vp.imageanalysis.interfaces;
/**
 * Represents a raster grid containing numeric values.
 *
 * A raster exposes access to cell values through
 * pixel coordinates (x, y), along with spatial metadata
 * describing the grid geometry.
 *
 * Implementations may be eager (fully materialized in memory)
 * or lazy (values computed on demand).
 */
public interface Raster {

    double getValue(int x, int y);
    GridGeometry getGeometry();
    int width();
    int height();
}
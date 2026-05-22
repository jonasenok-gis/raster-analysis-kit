package dk.ek.vp.imageanalysis.interfaces;


/**
 * Describes the spatial geometry of a raster grid.
 *
 * The origin represents the upper-left corner of the raster
 * in world coordinates of the appropriate coordinate system (SRID) .
 *
 * Pixel sizes are expressed in coordinate system units.
 */
public interface GridGeometry {
    double originX();
    double originY();
    double pixelSizeX();
    double pixelSizeY();
    int srid();
}

package dk.ek.vp.imageanalysis.implementation.raster;
/**
 * Implementation of GridGeometry as a record (read GridGeometry interface)
 */
public record GridGeometryRecord(double originX,
                                 double originY,
                                 double pixelSizeX,
                                 double pixelSizeY,
                                 int srid) implements dk.ek.vp.imageanalysis.interfaces.GridGeometry {
}

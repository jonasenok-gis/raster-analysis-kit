package dk.ek.vp.imageanalysis.implementation.raster;

public record GridGeometryRecord(double originX,
                                 double originY,
                                 double pixelSizeX,
                                 double pixelSizeY,
                                 int srid) implements dk.ek.vp.imageanalysis.interfaces.GridGeometry {
}

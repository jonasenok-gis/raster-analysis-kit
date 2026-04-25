package dk.ek.vp.imageanalysis.implementation.raster;

public record GridGeometry(double originX,
                           double originY,
                           double pixelSizeX,
                           double pixelSizeY,
                           int srid) {
}

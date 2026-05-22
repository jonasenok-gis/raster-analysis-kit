package dk.ek.vp.imageanalysis.interfaces;
/**
 * Writes raster data to an external datasource.
 *
 * Implementations are responsible for converting internal
 * Raster representations into file formats such as GeoTIFF.
 */
public interface RasterWriter {
    void write(Raster raster, String path) throws Exception;
}
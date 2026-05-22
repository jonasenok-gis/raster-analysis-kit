package dk.ek.vp.imageanalysis.interfaces;

import org.geotools.api.referencing.FactoryException;

import java.io.IOException;
/**
 * Reads raster data from an external datasource.
 *
 * Implementations are responsible for converting file formats
 * such as GeoTIFF or VRT into the internal Raster abstraction.
 * Can be an adaption of an extension, e.g. GeoTools.
 */
public interface RasterReader {
    Raster read(String Path) throws IOException, FactoryException; // læs TIFF (fx via GeoTools eller GDAL wrapper)
}

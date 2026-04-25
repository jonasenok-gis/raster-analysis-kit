package dk.ek.vp.imageanalysis.interfaces;

import org.geotools.api.data.DataSourceException;
import org.geotools.api.referencing.FactoryException;

import java.io.IOException;

public interface GeoTiffReader {
    Raster read(String Path) throws IOException, FactoryException; // læs TIFF (fx via GeoTools eller GDAL wrapper)
}

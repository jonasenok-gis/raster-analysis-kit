package dk.ek.vp.imageanalysis.demos;

import dk.ek.vp.imageanalysis.implementation.command.FilterCommand;
import dk.ek.vp.imageanalysis.implementation.raster.GeoTiffReaderWithGeoTools;
import dk.ek.vp.imageanalysis.interfaces.Raster;
import dk.ek.vp.imageanalysis.interfaces.RasterCommand;
import org.geotools.api.referencing.FactoryException;

import java.io.IOException;

public class FilterDemo {
static void main() throws FactoryException, IOException {
    GeoTiffReaderWithGeoTools reader = new GeoTiffReaderWithGeoTools();
    Raster raster = reader.read("../DTM_1km_6192_704.tif");

    System.out.println(raster.getValue(10, 10));
    RasterCommand filter = new FilterCommand(10);

    Raster result = filter.execute(raster);

    // test én pixel
    System.out.println(result.getValue(10, 10));
}
}

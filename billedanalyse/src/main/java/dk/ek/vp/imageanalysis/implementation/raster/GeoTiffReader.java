package dk.ek.vp.imageanalysis.implementation.raster;

import dk.ek.vp.imageanalysis.interfaces.GridGeometry;
import dk.ek.vp.imageanalysis.interfaces.Raster;
import dk.ek.vp.imageanalysis.interfaces.RasterUtils;
import org.geotools.api.data.DataSourceException;
import org.geotools.api.referencing.FactoryException;
import org.geotools.api.referencing.crs.CoordinateReferenceSystem;
import org.geotools.coverage.grid.GridCoverage2D;
import org.geotools.geometry.jts.ReferencedEnvelope;
import org.geotools.referencing.CRS;

import java.awt.image.RenderedImage;
import java.io.File;
import java.io.IOException;

public class GeoTiffReader implements dk.ek.vp.imageanalysis.interfaces.GeoTiffReader {
    @Override
    public Raster read(String path) throws IOException, FactoryException {

        File file = new File(path);

        org.geotools.gce.geotiff.GeoTiffReader reader =
                new org.geotools.gce.geotiff.GeoTiffReader(file);

        GridCoverage2D coverage = reader.read(null);

        // Retrieve pixel values into a 2D array
        RenderedImage image = coverage.getRenderedImage();
        java.awt.image.Raster raster = image.getData();

        int width = raster.getWidth();
        int height = raster.getHeight();

        double[][] data = new double[height][width];

        for (int y = 0; y < height; y++) {
            for (int x = 0; x < width; x++) {
                data[y][x] = raster.getSampleDouble(x, y, 0);
            }
        }

        // retrieve the geographic information (pixel sizes, coordinates, spatial coordinate system)
       ReferencedEnvelope env = coverage.getEnvelope2D();

        double pixelSizeX = env.getWidth() / width;
        double pixelSizeY = env.getHeight() / height;

        double originX = env.getMinX();
        double originY = env.getMaxY();

        CoordinateReferenceSystem crs = coverage.getCoordinateReferenceSystem();
        // srid = Spatial Reference ID
        Integer srid = CRS.lookupEpsgCode(crs, true);

        GridGeometry geometry = new GridGeometry(
                originX,
                originY,
                pixelSizeX,
                pixelSizeY,
                srid != null ? srid : -1
        );

        return new ArrayRaster(data, geometry);
    }
}

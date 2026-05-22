package dk.ek.vp.imageanalysis.implementation.raster;

import dk.ek.vp.imageanalysis.interfaces.GridGeometry;
import dk.ek.vp.imageanalysis.interfaces.Raster;
import dk.ek.vp.imageanalysis.interfaces.RasterReader;
import org.geotools.api.referencing.FactoryException;
import org.geotools.api.referencing.crs.CoordinateReferenceSystem;
import org.geotools.coverage.grid.GridCoverage2D;
import org.geotools.geometry.jts.ReferencedEnvelope;
import org.geotools.referencing.CRS;

import java.awt.image.RenderedImage;
import java.io.File;
import java.io.IOException;
/**
 * GeoTIFF raster reader implementation based on the GeoTools library.
 *
 * This reader converts GeoTIFF datasets into the internal Raster
 * abstraction used by the raster processing framework.
 *
 * The implementation performs:
 * - reading of raster cell values into a materialized array
 * - extraction of spatial metadata
 * - conversion of CRS information into an SRID
 *
 * The resulting raster is returned as an ArrayRaster.
 */
public class GeoTiffReaderWithGeoTools implements RasterReader {
    @Override
    public Raster read(String path) throws IOException, FactoryException {

        File file = new File(path);

        org.geotools.gce.geotiff.GeoTiffReader reader =
                new org.geotools.gce.geotiff.GeoTiffReader(file);

        GridCoverage2D coverage = reader.read(null);

        /*
         * Retrieve raster pixel values from the GeoTIFF image
         * and materialize them into a 2D array.
         * Because a tif/other Raster GIS format can contain several bands (be an 3D array)
         * a loop through the data is necessary.
         */
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

        /*
         * Extract spatial metadata from the GeoTIFF coverage,
         * including extent, pixel resolution, and coordinate
         * reference system.
         */
        ReferencedEnvelope env = coverage.getEnvelope2D();

        double pixelSizeX = env.getWidth() / width;
        double pixelSizeY = env.getHeight() / height;

        double originX = env.getMinX();
        double originY = env.getMaxY();

        CoordinateReferenceSystem crs = coverage.getCoordinateReferenceSystem();
        /*
         * SRID = Spatial Reference System Identifier, ie. the file's coordinate system.
         */
        Integer srid = CRS.lookupEpsgCode(crs, true);

        GridGeometry geometry = new GridGeometryRecord(
                originX,
                originY,
                pixelSizeX,
                pixelSizeY,
                srid != null ? srid : -1
        );

        return new ArrayRaster(data, geometry);
    }
}

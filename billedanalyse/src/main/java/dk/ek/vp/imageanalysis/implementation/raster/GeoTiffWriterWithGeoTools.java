package dk.ek.vp.imageanalysis.implementation.raster;
// own packages
import dk.ek.vp.imageanalysis.interfaces.GridGeometry;
import dk.ek.vp.imageanalysis.interfaces.Raster;

//geotools
import dk.ek.vp.imageanalysis.interfaces.RasterWriter;
import org.geotools.api.referencing.crs.CoordinateReferenceSystem;
import org.geotools.coverage.grid.GridCoverage2D;
import org.geotools.coverage.grid.GridCoverageFactory;
import org.geotools.gce.geotiff.GeoTiffWriter;
import org.geotools.referencing.CRS;
import org.geotools.geometry.jts.ReferencedEnvelope;

// java/javax packages for handling files/images
import javax.media.jai.RasterFactory;
import java.awt.image.WritableRaster;
import java.io.File;
/**
 * GeoTiffWriterWithGeoTools
 *
 * Writes an internal Raster implementation to a GeoTIFF file using GeoTools.
 *
 * The process is:
 * 1. Convert the internal Raster representation into a GeoTools WritableRaster.
 * 2. Transfer pixel values into the raster.
 * 3. Build spatial metadata (bounding box + CRS).
 * 4. Wrap everything into a GridCoverage2D.
 * 5. Write the coverage to disk as a GeoTIFF.
 *
 * Assumptions:
 * - Raster uses a single band (grayscale or scalar data).
 * - GridGeometry provides origin (top-left) and pixel size.
 * - SRID follows EPSG codes when available.
 */
public class GeoTiffWriterWithGeoTools implements RasterWriter {

    @Override
    public void write(Raster raster, String path) throws Exception {

        int width = raster.width();
        int height = raster.height();

        /* ---------------------------------------------------------------------
        * 1. Create writable raster (single band, double precision values)
        * ---------------------------------------------------------------------
        */
        WritableRaster wr = RasterFactory.createBandedRaster(

                java.awt.image.DataBuffer.TYPE_DOUBLE,
                width,
                height,
                1,
                null
        );

        // ---------------------------------------------------------------------
        // 2. Copy pixel values from custom Raster into GeoTools raster
        // ---------------------------------------------------------------------
        for (int y = 0; y < height; y++) {
            for (int x = 0; x < width; x++) {
                wr.setSample(x, y, 0, raster.getValue(x, y));
            }
        }

        // ---------------------------------------------------------------------
        // 3. Build spatial extent (geographic envelope)
        // ---------------------------------------------------------------------
        GridGeometry g = raster.getGeometry();

        double minX = g.originX();
        double maxY = g.originY();

        double maxX = minX + g.pixelSizeX() * width;
        double minY = maxY - g.pixelSizeY() * height;
        // ---------------------------------------------------------------------
        // 4. Coordinate Reference System (CRS)
        // If an SRID is provided, decode it into a GeoTools CRS object.
        // Otherwise, CRS remains null (no spatial reference).
        // ---------------------------------------------------------------------
        CoordinateReferenceSystem crs = null;

        if (g.srid() != -1) {
            crs = CRS.decode("EPSG:" + g.srid());
        }
        // ---------------------------------------------------------------------
        // 5. Create georeferenced envelope (bounding box + CRS)
        // ---------------------------------------------------------------------
        ReferencedEnvelope env = new ReferencedEnvelope(
                minX,
                maxX,
                minY,
                maxY,
                crs
        );



        // ---------------------------------------------------------------------
        // 6. Wrap raster data + spatial reference into a GridCoverage2D
        // ---------------------------------------------------------------------
        GridCoverageFactory factory = new GridCoverageFactory();
        GridCoverage2D coverage = factory.create(
                "raster",
                wr,
                env
        );
        // ---------------------------------------------------------------------
        // 7. Write GeoTIFF to disk
        // ---------------------------------------------------------------------
        GeoTiffWriter writer =
                new GeoTiffWriter(new File(path));

        writer.write(coverage, null);
    }
}
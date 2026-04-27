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

public class GeoTiffWriterWithGeoTools implements RasterWriter {

    @Override
    public void write(Raster raster, String path) throws Exception {

        int width = raster.width();
        int height = raster.height();

        // === 1. Lav WritableRaster ===
        WritableRaster wr = RasterFactory.createBandedRaster(
                java.awt.image.DataBuffer.TYPE_DOUBLE,
                width,
                height,
                1,
                null
        );

        // === 2. Fyld data ===
        for (int y = 0; y < height; y++) {
            for (int x = 0; x < width; x++) {
                wr.setSample(x, y, 0, raster.getValue(x, y));
            }
        }

        // === 3. Geometri ===
        GridGeometry g = raster.getGeometry();

        double minX = g.originX();
        double maxY = g.originY();

        double maxX = minX + g.pixelSizeX() * width;
        double minY = maxY - g.pixelSizeY() * height;
        // === 4. CRS ===
        CoordinateReferenceSystem crs = null;

        if (g.srid() != -1) {
            crs = CRS.decode("EPSG:" + g.srid());
        }

        ReferencedEnvelope env = new ReferencedEnvelope(
                minX,
                maxX,
                minY,
                maxY,
                crs
        );



        // === 5. Coverage ===
        GridCoverageFactory factory = new GridCoverageFactory();
        GridCoverage2D coverage = factory.create(
                "raster",
                wr,
                env
        );

        // === 6. Write ===
        GeoTiffWriter writer =
                new GeoTiffWriter(new File(path));

        writer.write(coverage, null);
    }
}
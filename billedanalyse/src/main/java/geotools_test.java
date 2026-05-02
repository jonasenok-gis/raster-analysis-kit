import java.io.File;
import org.geotools.coverage.grid.GridCoverage2D;
import org.geotools.gce.geotiff.GeoTiffReader;

public class geotools_test {
    public static void main(String[] args) throws Exception {

        File file = new File("../DTM_1km_6192_704.tif");

        GeoTiffReader reader = new GeoTiffReader(file);
        GridCoverage2D coverage = reader.read( null);

        System.out.println("Loaded raster: " + coverage.getName());
    }
}
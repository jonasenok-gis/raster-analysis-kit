package dk.ek.vp.imageanalysis.demos;

import dk.ek.vp.imageanalysis.implementation.command.FilterCommand;
import dk.ek.vp.imageanalysis.implementation.raster.GeoTiffReaderWithGeoTools;
import dk.ek.vp.imageanalysis.implementation.raster.GeoTiffWriterWithGeoTools;
import dk.ek.vp.imageanalysis.interfaces.Raster;
import dk.ek.vp.imageanalysis.interfaces.RasterCommand;
import dk.ek.vp.imageanalysis.interfaces.RasterWriter;

public class WriterDemo {
        public static void main(String[] args) throws Exception {

            String inputPath = "../DTM_1km_6192_704.tif";
            String outputPath = "../DTM_1km_6192_704_outputtest.tif";

            // === 1. Read ===
            GeoTiffReaderWithGeoTools reader = new GeoTiffReaderWithGeoTools();
            Raster raster = reader.read(inputPath);

            System.out.println("Loaded raster: " +
                    raster.width() + "x" + raster.height());

            RasterCommand filter = new FilterCommand(30);

            Raster result = filter.execute(raster);

            // test én pixel (sanity check)
            System.out.println("Sample value before: " + raster.getValue(10, 10));
            System.out.println("Sample value after:  " + result.getValue(10, 10));

            // === 3. Write ===
            RasterWriter writer = new GeoTiffWriterWithGeoTools();
            writer.write(result, outputPath);

            System.out.println("Written to: " + outputPath);
        }
}


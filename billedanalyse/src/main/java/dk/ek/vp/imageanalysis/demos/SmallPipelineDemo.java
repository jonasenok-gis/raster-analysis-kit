package dk.ek.vp.imageanalysis.demos;

import dk.ek.vp.imageanalysis.implementation.command.Pipeline;
import dk.ek.vp.imageanalysis.implementation.command.SlopeCommand;
import dk.ek.vp.imageanalysis.implementation.raster.GeoTiffReaderWithGeoTools;
import dk.ek.vp.imageanalysis.implementation.raster.GeoTiffWriterWithGeoTools;
import dk.ek.vp.imageanalysis.interfaces.Raster;
import dk.ek.vp.imageanalysis.interfaces.RasterWriter;

public class SmallPipelineDemo {

    public static void main(String[] args) throws Exception {

        String inputPath = "../DTM_1km_6192_704.tif";
        String outputPath = "../DTM_1km_6192_704_pipelineOuttest.tif";
        //read input
        GeoTiffReaderWithGeoTools reader = new GeoTiffReaderWithGeoTools();
        Raster input = reader.read(inputPath);

        System.out.println("Input read from: " + inputPath);
        System.out.println("Image dimension: " + input.height() + " x "+input.width() );
        // filter command - example with any cellValue > 30 meters => 1, below => 0


        // create pipeline and add filter
        Pipeline pipeline = Pipeline.empty().threshold(30.0).materialize().slope();

        // execute pipeline
        Raster result = pipeline.execute(input);

        // write to file
        RasterWriter writer = new GeoTiffWriterWithGeoTools();
        writer.write(result, outputPath);

        System.out.println("Output written to: " + outputPath);
    }

}
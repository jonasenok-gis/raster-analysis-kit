package dk.ek.vp.imageanalysis.implementation.raster.vrttiles;

import dk.ek.vp.imageanalysis.implementation.raster.GeoTiffReaderWithGeoTools;
import dk.ek.vp.imageanalysis.interfaces.Raster;

import static dk.ek.vp.imageanalysis.implementation.raster.vrttiles.VrtReader.read;

public class vrtDemo {

    static void main() {

        String inputVRT = "../DTM_vrt/DTM_vrt.vrt";
        GeoTiffReaderWithGeoTools reader = new GeoTiffReaderWithGeoTools();
        VrtRaster vrtRaster = (VrtRaster) read(inputVRT, reader);

        for (Raster raster: vrtRaster.getChildren())
        {
            System.out.println(raster.height());
        }
    }
}

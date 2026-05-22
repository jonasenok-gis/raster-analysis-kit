package dk.ek.vp.imageanalysis.implementation.raster.vrttiles;
import dk.ek.vp.imageanalysis.interfaces.GridGeometry;

import java.io.File;

public record VrtMetadata(File file,
                          int width,
                          int height,
                          GridGeometry gridGeometry) {

}
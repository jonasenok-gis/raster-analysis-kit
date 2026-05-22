package dk.ek.vp.imageanalysis.interfaces;
/**
 * Utility methods related to raster processing and conversion.
 *
 * This class contains helper functionality that does not
 * naturally belong to individual raster implementations
 * or commands.
 */
public interface RasterUtils {

    double[][] toArray(Raster input);
}

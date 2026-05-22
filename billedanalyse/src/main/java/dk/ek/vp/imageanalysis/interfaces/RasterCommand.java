package dk.ek.vp.imageanalysis.interfaces;
/**
 * Represents a raster transformation operation.
 *
 * Commands take a raster as input and produce a new raster
 * as output.
 * Can have many different implementations based on the needed analysis
 * (math, boolean logic, window analysis)
 */
public interface RasterCommand {
    Raster execute(Raster input);
}

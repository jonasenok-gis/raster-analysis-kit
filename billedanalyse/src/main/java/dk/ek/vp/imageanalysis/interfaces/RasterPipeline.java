package dk.ek.vp.imageanalysis.interfaces;
/**
 * Represents a composable sequence of raster operations.
 *
 * A pipeline allows multiple RasterCommands to be chained
 * together into a single processing workflow.
 *
 * a pipeline is meant to be lazily implemented - the actual result is first
 * computed when needed (e.g. writing to a file)
 */
public interface RasterPipeline extends RasterCommand{
    RasterPipeline add(RasterCommand command);
}

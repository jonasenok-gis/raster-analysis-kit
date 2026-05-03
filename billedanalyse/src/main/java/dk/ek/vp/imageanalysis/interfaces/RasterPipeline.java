package dk.ek.vp.imageanalysis.interfaces;

public interface RasterPipeline extends RasterCommand{
    RasterPipeline add(RasterCommand command);
}

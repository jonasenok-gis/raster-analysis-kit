package dk.ek.vp.imageanalysis.implementation.command;

import dk.ek.vp.imageanalysis.implementation.raster.AbstractLazyRaster;
import dk.ek.vp.imageanalysis.interfaces.GridGeometry;
import dk.ek.vp.imageanalysis.interfaces.Raster;
import dk.ek.vp.imageanalysis.interfaces.RasterCommand;

public class FilterCommand implements RasterCommand {

    private final double threshold;

    public FilterCommand(double threshold) {
        this.threshold = threshold;
    }

    @Override
    public Raster execute(Raster input) {
        // syntaksen nedenunder svarer til at lave en class der nedarver metoderne fra AbstractLazyRaster
        // men det man returnerer er ikke bare et nyt datasæt/array.
        // det er en custom implementation af Raster-interfacet.
        // Som har en custom getValue til at filtere værdier.
        return new AbstractLazyRaster(input) {
            @Override
            public double getValue(int x, int y) {
                // short syntax: [condition] ? [value-if-true] : [value-if-false];

                return source.getValue(x, y) > threshold ? 1.0 : 0.0;
            }
        };
    }
}
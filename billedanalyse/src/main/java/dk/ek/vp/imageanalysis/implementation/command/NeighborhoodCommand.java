package dk.ek.vp.imageanalysis.implementation.command;

import dk.ek.vp.imageanalysis.implementation.raster.AbstractLazyRaster;
import dk.ek.vp.imageanalysis.interfaces.Raster;
import dk.ek.vp.imageanalysis.interfaces.RasterCommand;

public abstract class NeighborhoodCommand implements RasterCommand {

    protected final int radius;

    public NeighborhoodCommand(int radius) {
        this.radius = radius;
    }

    @Override
    public Raster execute(Raster input) {

        return new AbstractLazyRaster(input) {

            @Override
            public double getValue(int x, int y) {

                // edge handling
                if (x < radius || y < radius ||
                        x >= width() - radius || y >= height() - radius) {
                    return Double.NaN;
                }

                // saml neighborhood
                double[][] window = new double[2 * radius + 1][2 * radius + 1];

                for (int dy = -radius; dy <= radius; dy++) {
                    for (int dx = -radius; dx <= radius; dx++) {
                        window[dy + radius][dx + radius] =
                                source.getValue(x + dx, y + dy);
                    }
                }

                // delegér beregning til subclass
                return apply(source, window, x, y);
            }
        };
    }

    // subclasses implementerer logikken
    protected abstract double apply(Raster source, double[][] window, int x, int y);
}
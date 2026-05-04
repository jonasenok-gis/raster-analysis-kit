package dk.ek.vp.imageanalysis.implementation.command;

import dk.ek.vp.imageanalysis.implementation.raster.AbstractLazyRaster;
import dk.ek.vp.imageanalysis.interfaces.Raster;
import dk.ek.vp.imageanalysis.interfaces.RasterCommand;

import java.util.function.DoubleBinaryOperator;

public class CombineCommand implements RasterCommand {

    private final Raster other;
    private final DoubleBinaryOperator function;

    public CombineCommand(Raster other, DoubleBinaryOperator function) {
        this.other = other;
        this.function = function;
    }

    @Override
    public Raster execute(Raster input) {

        return new AbstractLazyRaster(input) {

            @Override
            public double getValue(int x, int y) {

                double v1 = source.getValue(x, y);
                double v2 = other.getValue(x, y);

                return function.applyAsDouble(v1, v2);
            }
        };
    }
}
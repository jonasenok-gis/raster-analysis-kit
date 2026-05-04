package dk.ek.vp.imageanalysis.implementation.command;
import dk.ek.vp.imageanalysis.implementation.raster.AbstractLazyRaster;
import dk.ek.vp.imageanalysis.interfaces.Raster;
import dk.ek.vp.imageanalysis.interfaces.RasterCommand;

import java.util.function.DoubleUnaryOperator;

public class MapCommand implements RasterCommand {

    private final DoubleUnaryOperator function;

    public MapCommand(DoubleUnaryOperator function) {
        this.function = function;
    }

    @Override
    public Raster execute(Raster input) {

        return new AbstractLazyRaster(input) {

            @Override
            public double getValue(int x, int y) {
                return function.applyAsDouble(source.getValue(x, y));
            }
        };
    }
}
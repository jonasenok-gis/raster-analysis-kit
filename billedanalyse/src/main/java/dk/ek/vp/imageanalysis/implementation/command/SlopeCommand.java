package dk.ek.vp.imageanalysis.implementation.command;

import dk.ek.vp.imageanalysis.implementation.raster.AbstractLazyRaster;
import dk.ek.vp.imageanalysis.interfaces.Raster;
import dk.ek.vp.imageanalysis.interfaces.RasterCommand;

public class SlopeCommand implements RasterCommand {

    @Override
    public Raster execute(Raster input) {
        double cellSizeX = input.getGeometry().pixelSizeX();
        double cellSizeY = input.getGeometry().pixelSizeY();
        return new AbstractLazyRaster(input) {

            @Override
            public double getValue(int x, int y) {

                if (x <= 0 || y <= 0 || x >= width() - 1 || y >= height() - 1) {
                    return Double.NaN;
                }
                // using Horns algorithm to calculate weighted east-west and north-south slope
                double z1 = source.getValue(x - 1, y - 1);
                double z2 = source.getValue(x,     y - 1);
                double z3 = source.getValue(x + 1, y - 1);
                double z4 = source.getValue(x - 1, y);
                double z6 = source.getValue(x + 1, y);
                double z7 = source.getValue(x - 1, y + 1);
                double z8 = source.getValue(x,     y + 1);
                double z9 = source.getValue(x + 1, y + 1);

                double dzdx = ((z3 + 2*z6 + z9) - (z1 + 2*z4 + z7)) / (8 * cellSizeX);
                double dzdy = ((z7 + 2*z8 + z9) - (z1 + 2*z2 + z3)) / (8 * cellSizeY);

                return Math.sqrt(dzdx * dzdx + dzdy * dzdy);
            }
        };
    }
}

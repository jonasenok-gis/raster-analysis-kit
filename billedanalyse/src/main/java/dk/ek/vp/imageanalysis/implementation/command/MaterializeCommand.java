package dk.ek.vp.imageanalysis.implementation.command;

import dk.ek.vp.imageanalysis.implementation.raster.ArrayRaster;
import dk.ek.vp.imageanalysis.interfaces.Raster;
import dk.ek.vp.imageanalysis.interfaces.RasterCommand;

// class to materialize, that is compute every previous step before proceeding to next step/command
// useful before calculating window/kernel based commands such as slope.
public class MaterializeCommand implements RasterCommand {

    @Override
    public Raster execute(Raster input) {

        int width = input.width();
        int height = input.height();

        double[][] data = new double[height][width];

        for (int y = 0; y < height; y++) {
            for (int x = 0; x < width; x++) {
                data[y][x] = input.getValue(x, y);
            }
        }

        // return the newly calculated array
        return new ArrayRaster(data, input.getGeometry());
    }
}
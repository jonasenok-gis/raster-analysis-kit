package dk.ek.vp.imageanalysis.implementation.command;

import dk.ek.vp.imageanalysis.interfaces.Raster;

public class SlopeCommand2 extends NeighborhoodCommand {

    public SlopeCommand2() {
        super(1); // 3x3 kernel
    }

    @Override
    protected double apply(Raster source, double[][] w, int x, int y) {

        double cellSizeX = source.getGeometry().pixelSizeX();
        double cellSizeY = source.getGeometry().pixelSizeY();

        double z1 = w[0][0];
        double z2 = w[0][1];
        double z3 = w[0][2];
        double z4 = w[1][0];
        double z6 = w[1][2];
        double z7 = w[2][0];
        double z8 = w[2][1];
        double z9 = w[2][2];

        double dzdx = ((z3 + 2*z6 + z9) - (z1 + 2*z4 + z7)) / (8 * cellSizeX);
        double dzdy = ((z7 + 2*z8 + z9) - (z1 + 2*z2 + z3)) / (8 * cellSizeY);

        return Math.sqrt(dzdx * dzdx + dzdy * dzdy);
    }
}
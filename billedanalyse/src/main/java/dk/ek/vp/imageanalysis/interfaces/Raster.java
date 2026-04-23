package dk.ek.vp.imageanalysis.interfaces;

public interface Raster {
    // i virkeligheden er jeg ikke sikker paa, hvilke af disse skal bruges
    double getValue(int x, int y);
    double pixelXSize();
    double pixelYsize();
    int width();
    int height();
    int srid();
    double[][] getCoordinates();
}
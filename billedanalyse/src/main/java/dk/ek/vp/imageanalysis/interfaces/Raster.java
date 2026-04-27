package dk.ek.vp.imageanalysis.interfaces;

public interface Raster {
    // i virkeligheden er jeg ikke sikker paa, hvilke af disse skal bruges
    double getValue(int x, int y);
    GridGeometry getGeometry();
    int width();
    int height();
}
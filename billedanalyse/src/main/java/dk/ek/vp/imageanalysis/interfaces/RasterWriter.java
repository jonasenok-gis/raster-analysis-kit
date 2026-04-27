package dk.ek.vp.imageanalysis.interfaces;

public interface RasterWriter {
    void write(Raster raster, String path) throws Exception;
}
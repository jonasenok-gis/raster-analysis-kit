package dk.ek.vp.imageanalysis.implementation.raster.vrttiles;


public record VrtTile(
        String path,
        int xOffset,
        int yOffset,
        int width,
        int height
) {}

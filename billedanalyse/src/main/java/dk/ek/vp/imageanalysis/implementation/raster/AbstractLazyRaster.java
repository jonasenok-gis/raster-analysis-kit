package dk.ek.vp.imageanalysis.implementation.raster;

import dk.ek.vp.imageanalysis.interfaces.GridGeometry;
import dk.ek.vp.imageanalysis.interfaces.Raster;

/**
 * Base class for lazily evaluated raster implementations.
 *
 * AbstractLazyRaster wraps another raster and delegates
 * metadata operations such as dimensions and geometry
 * to the source raster (input-parameter in the constructor).
 *
 * Subclasses are responsible for implementing getValue(x, y)
 * and defining how pixel values are computed on demand.
 *
 * This allows raster operations to be chained together
 * without immediately materializing intermediate results.
 */
public abstract class AbstractLazyRaster implements Raster {
    //The underlying raster used as input for lazy evaluation.
    protected final Raster source;

    public AbstractLazyRaster(Raster source) {
        this.source = source;
    }

    @Override
    public int width() {
        return source.width();
    }

    @Override
    public int height() {
        return source.height();
    }

    @Override
    public GridGeometry getGeometry() {
        return source.getGeometry();
    }
}

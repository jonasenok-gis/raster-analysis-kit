package dk.ek.vp.imageanalysis.implementation.raster;

import dk.ek.vp.imageanalysis.interfaces.GridGeometry;
import dk.ek.vp.imageanalysis.interfaces.Raster;


//en abstract base class der bruges blot som en wrapper rundt om et eksisterende raster-objekt.
// bemærk at abstract betyder man ikke bruger classen direkte (den kan ikke instantieres)
// her bruger man den til at implementere dele af et interface der skal genbruges (width, height og getGeometry)
// derudover bruges den som en wrapper rundt om et faktisk raster.
// man forlænger/extender denne class med en ny getValue metode der afhænger af den command man vil køre
// forlængelsen er dybest set et Decorator Pattern
public abstract class AbstractLazyRaster implements Raster {
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

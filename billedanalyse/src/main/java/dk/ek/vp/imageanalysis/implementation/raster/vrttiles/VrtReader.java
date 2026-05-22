package dk.ek.vp.imageanalysis.implementation.raster.vrttiles;

import dk.ek.vp.imageanalysis.interfaces.Raster;
import dk.ek.vp.imageanalysis.interfaces.RasterReader;
import org.w3c.dom.*;
import org.xml.sax.SAXException;

import javax.xml.parsers.*;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class VrtReader {

    public static Raster read(String vrtPath, RasterReader reader) {

        try {
            List<Raster> rasters = new ArrayList<>();

            File file = new File(vrtPath);

            // xml-parsing builder
            DocumentBuilder builder = DocumentBuilderFactory
                    .newInstance()
                    .newDocumentBuilder();

            Document doc = builder.parse(file);

            Element root = doc.getDocumentElement();

            int totalWidth = Integer.parseInt(root.getAttribute("rasterXSize"));
            int totalHeight = Integer.parseInt(root.getAttribute("rasterYSize"));


            // each file in a .vrt is within a SimpleSource tag
            NodeList simpleSources = doc.getElementsByTagName("SimpleSource");

            for (int i = 0; i < simpleSources.getLength(); i++) {
                Element simpleSource = (Element) simpleSources.item(i);

                String path = simpleSource
                        .getElementsByTagName("SourceFilename")
                        .item(0)
                        .getTextContent();

                Element dstRect = (Element) simpleSource
                        .getElementsByTagName("DstRect")
                        .item(0);

                int xOffset = Integer.parseInt(dstRect.getAttribute("xOff"));
                int yOffset = Integer.parseInt(dstRect.getAttribute("yOff"));
                int width   = Integer.parseInt(dstRect.getAttribute("xSize"));
                int height  = Integer.parseInt(dstRect.getAttribute("ySize"));

                VrtTile metadata = new VrtTile(path, xOffset, yOffset, width, height);
                rasters.add(new RasterTile(metadata, reader));
            }
            return new VrtRaster(rasters);


            } catch (ParserConfigurationException ex) {
            throw new RuntimeException(ex);
        } catch (IOException ex) {
            throw new RuntimeException(ex);
        } catch (SAXException ex) {
            throw new RuntimeException(ex);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}
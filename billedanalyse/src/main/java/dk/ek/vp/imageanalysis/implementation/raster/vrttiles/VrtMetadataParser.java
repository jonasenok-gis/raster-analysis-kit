package dk.ek.vp.imageanalysis.implementation.raster.vrttiles;

import dk.ek.vp.imageanalysis.implementation.raster.GridGeometryRecord;
import dk.ek.vp.imageanalysis.interfaces.GridGeometry;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.xml.sax.SAXException;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import java.io.File;
import java.io.IOException;

public class VrtMetadataParser {

    public static VrtMetadata parse(File file) throws ParserConfigurationException, IOException, SAXException {

        // xml-parsing builder
        DocumentBuilder builder = DocumentBuilderFactory
                .newInstance()
                .newDocumentBuilder();

        Document doc = builder.parse(file);
        Element root = doc.getDocumentElement();

        // --- Raster size ---
        int width = Integer.parseInt(root.getAttribute("rasterXSize"));
        int height = Integer.parseInt(root.getAttribute("rasterYSize"));

        // --- GeoTransform ---
        String geoTransformText = doc
                .getElementsByTagName("GeoTransform")
                .item(0)
                .getTextContent()
                .trim();

        String[] parts = geoTransformText.split(",");

        double originX    = Double.parseDouble(parts[0].trim());
        double pixelSizeX = Double.parseDouble(parts[1].trim());
        double originY    = Double.parseDouble(parts[3].trim());
        double pixelSizeY = Double.parseDouble(parts[5].trim());

        // --- SRID ---
        String srsText = doc
                .getElementsByTagName("SRS")
                .item(0)
                .getTextContent();

        int srid = extractEpsg(srsText);

        // --- GridGeometry ---
        GridGeometry gridGeometry = new GridGeometryRecord(
                originX, originY, pixelSizeX, pixelSizeY, srid
        );

        return new VrtMetadata(file, width, height, gridGeometry);
    }

    private static int extractEpsg(String srsText) {
        int srid = -1;

        int index = srsText.lastIndexOf("EPSG\",\"");
        if (index != -1) {
            String sub = srsText.substring(index + 7);
            srid = Integer.parseInt(sub.split("\"")[0]);
        }

        return srid;
    }
}
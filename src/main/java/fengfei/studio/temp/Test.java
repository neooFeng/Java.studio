package fengfei.studio.temp;

import org.apache.http.Header;
import org.apache.http.HttpHeaders;
import org.apache.http.message.BasicHeader;
import sun.plugin2.message.HeartbeatMessage;

import javax.imageio.ImageIO;
import javax.xml.bind.DatatypeConverter;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.*;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.ArrayList;
import java.util.Base64;
import java.util.List;

public class Test {

    public static void main(String[] args) throws IOException {
        /*String path = "C:\\Users\\fengfei\\Downloads";
        concatImgs(path);*/

        String path = "C:\\Users\\fengfei\\Desktop\\64.txt";

        String thisLine = "";
        try (BufferedReader br = new BufferedReader(new FileReader(path))) {
            while ((thisLine = br.readLine()) != null) {
                byte[] bufferBytes = DatatypeConverter.parseBase64Binary(thisLine);

                File outFile = new File("C:\\Users\\fengfei\\Desktop\\64.png");
                FileOutputStream fo = new FileOutputStream(outFile);
                fo.write(bufferBytes);
                fo.flush();
                fo.close();
            }
        }
    }

    private static void concatImgs(String path) throws IOException {
        int imagesCount = 20;
        BufferedImage[] images = new BufferedImage[imagesCount];
        for(int j = 0; j < images.length; j++) {
            images[j] = ImageIO.read(new File(path, getName(j) + ".jpg"));
        }

        int heightTotal = 0;
        int maxWidth = 0;
        for(int j = 0; j < images.length; j++) {
            heightTotal += images[j].getHeight();
            maxWidth = Math.max(maxWidth, images[j].getWidth());
        }


        int heightCurr = 0;
        BufferedImage concatImage = new BufferedImage(maxWidth, heightTotal, BufferedImage.TYPE_INT_RGB);
        Graphics2D g2d = concatImage.createGraphics();
        for(int j = 0; j < images.length; j++) {
            g2d.drawImage(images[j], 0, heightCurr, null);
            heightCurr += images[j].getHeight();
        }
        g2d.dispose();


        System.out.println(imgToBase64String(concatImage, "PNG"));

        //ImageIO.write(concatImage, "PNG", new File(path, "combined.png"));
    }


    private static String imgToBase64String(final BufferedImage img, final String formatName) {
        final ByteArrayOutputStream os = new ByteArrayOutputStream();
        try {
            ImageIO.write(img, formatName, Base64.getEncoder().wrap(os));
            return os.toString();
        } catch (final IOException ioe) {
            throw new UncheckedIOException(ioe);
        }
    }

    private static BufferedImage base64StringToImg(final String base64String) {
        try {
            return ImageIO.read(new ByteArrayInputStream(Base64.getDecoder().decode(base64String)));
        } catch (final IOException ioe) {
            throw new UncheckedIOException(ioe);
        }
    }

    private static String getName(int j) {
        int t = j+1;

        if (t < 10){
            return "0" + t;
        }else{
            return String.valueOf(t);
        }
    }
}

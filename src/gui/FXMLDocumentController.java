package gui;

import image_processing.Binarization;
import image_processing.DivideImage;
import image_processing.Filters;
import image_processing.GrayScale;
import image_processing.HistogramEqualization;
import image_processing.K3M;
import image_processing.Morphology;
import image_processing.Negative;
import image_processing.SE;
import java.awt.Color;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.embed.swing.SwingFXUtils;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.image.ImageView;
import javax.imageio.ImageIO;
import javax.swing.JFileChooser;

public class FXMLDocumentController implements Initializable {

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
    }

    private BufferedImage bi;
    private BufferedImage loadedbi;

    @FXML
    private Label labelPlik;

    @FXML
    private ImageView imageView;
    
    @FXML
    Button resetImage;
    
    @FXML
    TextField threshold; 
    @FXML
    TextField maskSide; 
    
    private GrayScale gs;
    private Binarization binarization;
    private HistogramEqualization histogramEquation;
    private Morphology morphology;
    private Filters filters;
    private DivideImage di;
    private K3M k3m;

    @FXML
    private void chooseFile(ActionEvent event) {
        JFileChooser fc = new JFileChooser();
        File obraz;
        int returnValue = fc.showDialog(null, "Wybierz plik");
        if (returnValue == JFileChooser.APPROVE_OPTION) {
            obraz = fc.getSelectedFile();
            try {
                labelPlik.setText(obraz.getName());
                bi = ImageIO.read(obraz);
                loadedbi = ImageIO.read(obraz);
                imageView.setImage(SwingFXUtils.toFXImage(bi, null));
                resetImage.setDisable(false);
            } catch (IOException e1) {
                e1.printStackTrace();
            }
        }
    }
    
    @FXML
    private void resetImage(ActionEvent event) {
        for (int i = 0; i < loadedbi.getHeight(); i++) {
            for (int j = 0; j < loadedbi.getWidth(); j++) {
                Color c = new Color(loadedbi.getRGB(j, i));
                
                bi.setRGB(j,i,c.getRGB());
            }
        }
        imageView.setImage(SwingFXUtils.toFXImage(bi, null));
    }
    
    @FXML
    private void grayScaleYUV(ActionEvent event) {
        if(gs == null)
            gs = new GrayScale();
        bi = gs.grayScaleYUV(bi);
        imageView.setImage(SwingFXUtils.toFXImage(bi, null));
    }
    
    @FXML
    private void grayScaleAvg(ActionEvent event) {
        if(gs == null)
            gs = new GrayScale();
        bi = gs.grayScaleAvg(bi);
        imageView.setImage(SwingFXUtils.toFXImage(bi, null));
    }
    
    @FXML
    private void thresholdBinarization(ActionEvent event) {
        if(binarization == null)
            binarization = new Binarization();
        bi = binarization.binarize(bi, Integer.parseInt(threshold.getText()));
        imageView.setImage(SwingFXUtils.toFXImage(bi, null));
    }
    
    @FXML
    private void thresholdBinarization2(ActionEvent event) {
        if(binarization == null)
            binarization = new Binarization();
        bi = binarization.binarize2(bi, Integer.parseInt(threshold.getText()));
        imageView.setImage(SwingFXUtils.toFXImage(bi, null));
    }
    
    @FXML
    private void otsuBinarization(ActionEvent event) {
        if(binarization == null)
            binarization = new Binarization();
        bi = binarization.otsuBinarize(bi);
        imageView.setImage(SwingFXUtils.toFXImage(bi, null));
    }
    
    @FXML
    private void otsuBinarizationLocal(ActionEvent event) {
        if(binarization == null)
            binarization = new Binarization();
        if(di == null)
            di = new DivideImage();
        int side;
        if(bi.getWidth() > bi.getHeight())
            side = bi.getWidth() /4;
        else
            side = bi.getHeight() / 4;
        BufferedImage[][] bis = di.divide(bi, side);
        BufferedImage[][] bis2 = new BufferedImage[bis.length][bis[0].length];
        for(int i = 0; i < bis.length; i++) {
            for(int j = 0; j < bis[i].length; j++) {
                bis2[i][j] = binarization.otsuBinarize(bis[i][j]);
            }
        }
        bi = di.merge(bis2);
        imageView.setImage(SwingFXUtils.toFXImage(bi, null));
    }
    
    @FXML
    private void bhtBinarization(ActionEvent event) {
        if(binarization == null)
            binarization = new Binarization();
        bi = binarization.bhtBinarize(bi);
        imageView.setImage(SwingFXUtils.toFXImage(bi, null));
    }
    @FXML
    private void bhtBinarization2(ActionEvent event) {
        if(binarization == null)
            binarization = new Binarization();
        bi = binarization.bhtBinarize2(bi);
        imageView.setImage(SwingFXUtils.toFXImage(bi, null));
    }
    @FXML
    private void bhtBinarization3(ActionEvent event) {
        if(binarization == null)
            binarization = new Binarization();
        bi = binarization.bhtBinarize3(bi);
        imageView.setImage(SwingFXUtils.toFXImage(bi, null));
    }
    
    @FXML
    private void bernsenBinarization(ActionEvent event) {
        if(binarization == null)
            binarization = new Binarization();
//        bi = binarization.bernsenBinarize(bi, Integer.parseInt(maskSide.getText()));
        bi = binarization.bernsenBinarize2(bi, Integer.parseInt(maskSide.getText()), 100, 30);
        imageView.setImage(SwingFXUtils.toFXImage(bi, null));
    }
    
    @FXML
    private void whiteRohrerBinarization(ActionEvent event) {
        if(binarization == null)
            binarization = new Binarization();
        bi = binarization.whiteRohrerBinarize(bi, 23, 2.0);
        imageView.setImage(SwingFXUtils.toFXImage(bi, null));
    }
    
    @FXML
    private void niblackBinarization(ActionEvent event) {
        if(binarization == null)
            binarization = new Binarization();
        bi = binarization.niblackBinarize(bi, 15, -1.0);
        imageView.setImage(SwingFXUtils.toFXImage(bi, null));
    }
    
    @FXML
    private void sauvolaBinarization(ActionEvent event) {
        if(binarization == null)
            binarization = new Binarization();
        bi = binarization.sauvolaBinarize(bi, 15, 0.3, 128);
        imageView.setImage(SwingFXUtils.toFXImage(bi, null));
    }
    
    @FXML
    private void ridlerCalvardBinarization(ActionEvent event) {
        if(binarization == null)
            binarization = new Binarization();
        bi = binarization.ridlerCalvardBinarize(bi);
        imageView.setImage(SwingFXUtils.toFXImage(bi, null));
    }
    
    @FXML
    private void histogramEquation(ActionEvent event) {
        if(histogramEquation == null)
            histogramEquation = new HistogramEqualization();
        bi = histogramEquation.histogramEquation(bi);
        imageView.setImage(SwingFXUtils.toFXImage(bi, null));
    }
    
    @FXML
    private void negative(ActionEvent event) {
        bi = new Negative().negate(bi);
        imageView.setImage(SwingFXUtils.toFXImage(bi, null));
    }
    
    @FXML
    private void dilation(ActionEvent event) {
        if(morphology == null)
            morphology = new Morphology();
        bi = morphology.dilation(bi, new SE(new int[][] { { 1, 1, 1 }, { 1, 1, 1 }, { 1, 1, 1 } }, 1, 1));
        imageView.setImage(SwingFXUtils.toFXImage(bi, null));
    }
    
    @FXML
    private void erosion(ActionEvent event) {
        if(morphology == null)
            morphology = new Morphology();
        bi = morphology.erosion(bi, new SE(new int[][] { { 1, 1, 1 }, { 1, 1, 1 }, { 1, 1, 1 } }, 1, 1));
        imageView.setImage(SwingFXUtils.toFXImage(bi, null));
    }
    
    @FXML
    private void opening(ActionEvent event) {
        if(morphology == null)
            morphology = new Morphology();
        bi = morphology.erosion(bi, new SE(new int[][] { { 1, 1, 1 }, { 1, 1, 1 }, { 1, 1, 1 } }, 1, 1));
        bi = morphology.dilation(bi, new SE(new int[][] { { 1, 1, 1 }, { 1, 1, 1 }, { 1, 1, 1 } }, 1, 1));
        imageView.setImage(SwingFXUtils.toFXImage(bi, null));
    }
    
    @FXML
    private void closing(ActionEvent event) {
        if(morphology == null)
            morphology = new Morphology();
        bi = morphology.dilation(bi, new SE(new int[][] { { 1, 1, 1 }, { 1, 1, 1 }, { 1, 1, 1 } }, 1, 1));
        bi = morphology.erosion(bi, new SE(new int[][] { { 1, 1, 1 }, { 1, 1, 1 }, { 1, 1, 1 } }, 1, 1));
        imageView.setImage(SwingFXUtils.toFXImage(bi, null));
    }
    
    @FXML
    private void hitOrMiss(ActionEvent event) {
        if(morphology == null)
            morphology = new Morphology();
        //TODO wybor maski w GUI
        bi = morphology.hitOrMiss(bi, new SE(new int[][] { { -1, 0, 0 }, { 1, 1, 0 }, { -1, 1, -1 } }, 1, 1));
        imageView.setImage(SwingFXUtils.toFXImage(bi, null));
    }
    
    @FXML
    private void skeletonization(ActionEvent event) {
        if(morphology == null)
            morphology = new Morphology();
        bi = morphology.skeletonization(bi);
        imageView.setImage(SwingFXUtils.toFXImage(bi, null));
    }
    
    @FXML
    private void skeletonization2(ActionEvent event) {
        if(k3m == null)
            k3m = new K3M();
        bi = k3m.skeletonization2(bi);
        imageView.setImage(SwingFXUtils.toFXImage(bi, null));
    }
    
    @FXML
    private void lowPass1(ActionEvent event) {
        if(filters == null)
            filters = new Filters();
        bi = filters.filter(bi, filters.lowPass);
        imageView.setImage(SwingFXUtils.toFXImage(bi, null));
    }
    
    @FXML
    private void highPass1(ActionEvent event) {
        if(filters == null)
            filters = new Filters();
        bi = filters.filter(bi, filters.highPass);
//        int[] filter = new int[filters.highPass.length];
//        for(int i = 0; i < filters.highPass.length; i++)
//            filter[i] = (int) filters.highPass[i];
//        bi = filters.universalFilter(bi, filter);
        imageView.setImage(SwingFXUtils.toFXImage(bi, null));
    }
    
    @FXML
    private void meanFilter(ActionEvent event) {
        if(filters == null)
            filters = new Filters();
        bi = filters.meanFilter(bi, Integer.parseInt(maskSide.getText()));
//        bi = filters.universalFilter(bi, new int[]{1,1,1,1,1,1,1,1,1});
        imageView.setImage(SwingFXUtils.toFXImage(bi, null));
    }
    
    @FXML
    private void medianFilter(ActionEvent event) {
        if(filters == null)
            filters = new Filters();
        bi = filters.medianFilter(bi, Integer.parseInt(maskSide.getText()));
        imageView.setImage(SwingFXUtils.toFXImage(bi, null));
    }

}

package gui;

import image_processing.Binarization;
import image_processing.Filters;
import image_processing.GrayScale;
import image_processing.HistogramEqualization;
import image_processing.Morphology;
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
        bi = binarization.bernsenBinarize2(bi, Integer.parseInt(maskSide.getText()), 100, 15);
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

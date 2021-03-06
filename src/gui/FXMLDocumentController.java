package gui;

import image_processing.AnisotropicDiffusionFilter;
import image_processing.Binarization;
import image_processing.DivideImage;
import image_processing.Filters;
import image_processing.GaussianFilter;
import image_processing.GrayScale;
import image_processing.HistogramEqualization;
import image_processing.K3M;
import image_processing.KMM;
import image_processing.Morphology;
import image_processing.Negative;
import image_processing.SE;
import image_processing.WienerFilter;
import java.awt.Color;
import java.awt.image.BufferedImage;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.embed.swing.SwingFXUtils;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.image.ImageView;
import javax.imageio.ImageIO;
import javax.swing.JFileChooser;
import utils.CloneImage;
import utils.CompareImages;
import utils.StandardDeviation;

public class FXMLDocumentController implements Initializable {

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        methodCombo.getItems().setAll("Otsu", "Ridler,Calvard", "Pun", "Kapur", "Bernsen", "White,Rohrer", "Niblack", "Sauvola", "Bradley");
        methodCombo.valueProperty().addListener(new ChangeListener<String>() {
            @Override
            public void changed(ObservableValue ov, String _old, String _new) {
                par1.setText("");
                par2.setText("");
                switch(_new) {
                    case "Otsu":
                        maskSide.setVisible(false);
                        par1.setVisible(false);
                        par2.setVisible(false);
                        break;
                    case "Ridler,Calvard":
                        maskSide.setVisible(false);
                        par1.setVisible(false);
                        par2.setVisible(false);
                        break;
                    case "Pun":
                        maskSide.setVisible(false);
                        par1.setVisible(false);
                        par2.setVisible(false);
                        break;
                    case "Kapur":
                        maskSide.setVisible(false);
                        par1.setVisible(false);
                        par2.setVisible(false);
                        break;
                    case "Bernsen":
                        maskSide.setVisible(true);
                        par1.setVisible(true);
                        par2.setVisible(true);
                        par1.setPromptText("tg (domyślnie 127)");
                        par2.setPromptText("eps (domyślnie 50)");
                        break;
                    case "White,Rohrer":
                        maskSide.setVisible(true);
                        par1.setVisible(true);
                        par2.setVisible(false);
                        par1.setPromptText("k (domyślnie 1.0)");
                        break;
                    case "Niblack":
                        maskSide.setVisible(true);
                        par1.setVisible(true);
                        par2.setVisible(false);
                        par1.setPromptText("k (domyślnie -0.5)");
                        break;
                    case "Sauvola":
                        maskSide.setVisible(true);
                        par1.setVisible(true);
                        par2.setVisible(false);
                        par1.setPromptText("k (domyślnie 0.5)");
                        break;
                    case "Bradley":
                        maskSide.setVisible(true);
                        par1.setVisible(true);
                        par2.setVisible(false);
                        par1.setPromptText("t (domyślnie 15)");
                        break;
                }
            }
        });
    }

    private BufferedImage bi;
    private BufferedImage gt;
    private BufferedImage loadedbi;

    @FXML
    private Label labelPlik;
    @FXML
    private Label labelPlik2;

    @FXML
    private ImageView imageView;
    
    @FXML
    Button resetImage;
    @FXML
    Button grayScaleYUV;
    @FXML
    Button grayScaleAvg;
    @FXML
    Button equalizationBtn;
    @FXML
    Button dilationBtn;
    @FXML
    Button erosionBtn;
    @FXML
    Button openingBtn;
    @FXML
    Button closingBtn;
    @FXML
    Button hitOrMissBtn;
    @FXML
    Button skeletonizationBtn;
    @FXML
    Button highPass1;
    @FXML
    Button meanFilter;
    @FXML
    Button medianFilter;
    @FXML
    Button negateBtn;
    @FXML
    Button compare;
    @FXML
    Button saveFile;
    @FXML
    Button binarizeBtn;
    @FXML
    Button gaussBtn;
    @FXML
    Button kuwaharaBtn;
    @FXML
    Button anisotropicBtn;
    
    
    @FXML
    TextField threshold; 
    @FXML
    TextField maskSide;
    @FXML
    TextField maskSide2;
    @FXML
    TextField newFileName;
    @FXML
    TextField par1;
    @FXML
    TextField par2;
    
    @FXML
    ComboBox methodCombo;
    
    private GrayScale gs;
    private Binarization binarization;
    private HistogramEqualization histogramEquation;
    private Morphology morphology;
    private Filters filters;
    private DivideImage di;
    private K3M k3m;
    
    private CompareImages ci;
    
    @FXML
    private void chooseFile(ActionEvent event) {
        JFileChooser fc = new JFileChooser();
        File obraz;
        int returnValue = fc.showDialog(null, "Wybierz plik");
        if (returnValue == JFileChooser.APPROVE_OPTION) {
//            obraz = new File("D:\\Dokumenty\\Studia\\Zajęcia\\Wykłady\\Seminarium\\wybrane rysunki\\original\\a.bmp");
            obraz = fc.getSelectedFile();
            try {
                labelPlik.setText(obraz.getName());
                bi = ImageIO.read(obraz);
                loadedbi = ImageIO.read(obraz);
                imageView.setImage(SwingFXUtils.toFXImage(bi, null));
                resetImage.setDisable(false);
                grayScaleYUV.setDisable(false);
                grayScaleAvg.setDisable(false);
                equalizationBtn.setDisable(false);
                dilationBtn.setDisable(false);
                erosionBtn.setDisable(false);
                openingBtn.setDisable(false);
                closingBtn.setDisable(false);
                hitOrMissBtn.setDisable(false);
                skeletonizationBtn.setDisable(false);
                highPass1.setDisable(false);
                meanFilter.setDisable(false);
                medianFilter.setDisable(false);
                negateBtn.setDisable(false);
                saveFile.setDisable(false);
                binarizeBtn.setDisable(false);
                gaussBtn.setDisable(false);
                kuwaharaBtn.setDisable(false);
                anisotropicBtn.setDisable(false);
            } catch (IOException e1) {
                e1.printStackTrace();
            }
        }
    }
    
    @FXML
    private void chooseGroundTruthFile(ActionEvent event) {
        JFileChooser fc = new JFileChooser();
        File obraz;
        int returnValue = fc.showDialog(null, "Wybierz plik");
        if (returnValue == JFileChooser.APPROVE_OPTION) {
            obraz = fc.getSelectedFile();
            try {
                labelPlik2.setText(obraz.getName());
                gt = ImageIO.read(obraz);
                compare.setDisable(false);
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
    private void binarize(ActionEvent event) {
//        "Otsu", "Ridler,Calvard", "Pun", "Kapur", "Bernsen", "White,Rohrer", "Niblack", "Sauvola", "Bradley"
        if(methodCombo.getSelectionModel().getSelectedItem() == null) {
            Alert alert = new Alert(AlertType.ERROR);
            alert.setTitle("Błąd");
            alert.setHeaderText("Wybierz algorytm");
            alert.showAndWait();
        }
        else {
            String method = methodCombo.getSelectionModel().getSelectedItem().toString();
            try {
                switch(method) {
                    case "Otsu":
                        otsuBinarization(event);
                        break;
                    case "Ridler,Calvard":
                        ridlerCalvardBinarization(event);
                        break;
                    case "Pun":
                        punBinarization(event);
                        break;
                    case "Kapur":
                        kswBinarization(event);
                        break;
                    case "Bernsen":
                        bernsenBinarization(event);
                        break;
                    case "White,Rohrer":
                        whiteRohrerBinarization(event);
                        break;
                    case "Niblack":
                        niblackBinarization(event);
                        break;
                    case "Sauvola":
                        sauvolaBinarization(event);
                        break;
                    case "Bradley":
                        bradleyBinarization(event);
                        break;
                }
            } catch (NumberFormatException e) {
                Alert alert = new Alert(AlertType.ERROR);
                alert.setTitle("Błąd");
                alert.setHeaderText("Błędne dane wejściowe");
                alert.showAndWait();
            } catch (RuntimeException e) {
                Alert alert = new Alert(AlertType.ERROR);
                alert.setTitle("Błąd");
                alert.setHeaderText("Długośc maski musi być nieparzysta");
                alert.showAndWait();
            }
        }
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
    private void optimalDistributionBinarization(ActionEvent event) {
        if(binarization == null)
            binarization = new Binarization();
        bi = binarization.optimalDistributionBinarize(bi);
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
        int s = 3, t = 127, e = 50;
        if(maskSide.getText() != null && !maskSide.getText().equals(""))
            s = Integer.parseInt(maskSide.getText());
        if(par1.getText() != null && !par1.getText().equals(""))
            t = Integer.parseInt(par1.getText());
        if(par2.getText() != null && !par2.getText().equals(""))
            e = Integer.parseInt(par2.getText());
        bi = binarization.bernsenBinarize2(bi, s, t, e);
        imageView.setImage(SwingFXUtils.toFXImage(bi, null));
    }
    
    @FXML
    private void bradleyBinarization(ActionEvent event) {
        if(binarization == null)
            binarization = new Binarization();
        int s = 3, t = 15;
        if(maskSide.getText() != null && !maskSide.getText().equals(""))
            s = Integer.parseInt(maskSide.getText());
        if(par1.getText() != null && !par1.getText().equals(""))
            t = Integer.parseInt(par1.getText());
        bi = binarization.bradleyBinarize(bi, s, t);
        imageView.setImage(SwingFXUtils.toFXImage(bi, null));
    }
    
    @FXML
    private void whiteRohrerBinarization(ActionEvent event) {
        if(binarization == null)
            binarization = new Binarization();
        int s = 3;
        double k = 1.0;
        if(maskSide.getText() != null && !maskSide.getText().equals(""))
            s = Integer.parseInt(maskSide.getText());
        if(par1.getText() != null && !par1.getText().equals(""))
            k = Double.parseDouble(par1.getText());
        bi = binarization.whiteRohrerBinarize(bi, s, k);
        imageView.setImage(SwingFXUtils.toFXImage(bi, null));
    }
    
    @FXML
    private void niblackBinarization(ActionEvent event) {
        if(binarization == null)
            binarization = new Binarization();
        int s = 3;
        double k = -0.5;
        if(maskSide.getText() != null && !maskSide.getText().equals(""))
            s = Integer.parseInt(maskSide.getText());
        if(par1.getText() != null && !par1.getText().equals(""))
            k = Double.parseDouble(par1.getText());
        bi = binarization.niblackBinarize(bi, s, k);
        imageView.setImage(SwingFXUtils.toFXImage(bi, null));
    }
    
    @FXML
    private void sauvolaBinarization(ActionEvent event) {
        if(binarization == null)
            binarization = new Binarization();
        int s = 3;
        double k = 0.5;
        if(maskSide.getText() != null && !maskSide.getText().equals(""))
            s = Integer.parseInt(maskSide.getText());
        if(par1.getText() != null && !par1.getText().equals(""))
            k = Double.parseDouble(par1.getText());
        bi = binarization.sauvolaBinarize(bi, s, k, 128);
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
    private void punBinarization(ActionEvent event) {
        if(binarization == null)
            binarization = new Binarization();
        bi = binarization.punBinarize(bi);
        imageView.setImage(SwingFXUtils.toFXImage(bi, null));
    }
    
    @FXML
    private void kswBinarization(ActionEvent event) {
        if(binarization == null)
            binarization = new Binarization();
        bi = binarization.kswBinarize(bi);
        imageView.setImage(SwingFXUtils.toFXImage(bi, null));
    }
    
    @FXML
    private void localEntropyBinarization(ActionEvent event) {
        if(binarization == null)
            binarization = new Binarization();
        bi = binarization.localEntropyBinarize(bi);
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
        try {
            bi = morphology.dilation(bi, new SE(new int[][] { { 1, 1, 1 }, { 1, 1, 1 }, { 1, 1, 1 } }, 1, 1));
            imageView.setImage(SwingFXUtils.toFXImage(bi, null));
        } catch (RuntimeException e) {
            Alert alert = new Alert(AlertType.ERROR);
            alert.setTitle("Błąd");
            alert.setHeaderText("Obraz nie jest binarny");
            alert.showAndWait();
        }
    }
    
    @FXML
    private void erosion(ActionEvent event) {
        if(morphology == null)
            morphology = new Morphology();
        try {
            bi = morphology.erosion(bi, new SE(new int[][] { { 1, 1, 1 }, { 1, 1, 1 }, { 1, 1, 1 } }, 1, 1));
            imageView.setImage(SwingFXUtils.toFXImage(bi, null));
        } catch (RuntimeException e) {
            Alert alert = new Alert(AlertType.ERROR);
            alert.setTitle("Błąd");
            alert.setHeaderText("Obraz nie jest binarny");
            alert.showAndWait();
        }
    }
    
    @FXML
    private void opening(ActionEvent event) {
        if(morphology == null)
            morphology = new Morphology();
        try {
            bi = morphology.erosion(bi, new SE(new int[][] { { 1, 1, 1 }, { 1, 1, 1 }, { 1, 1, 1 } }, 1, 1));
            bi = morphology.dilation(bi, new SE(new int[][] { { 1, 1, 1 }, { 1, 1, 1 }, { 1, 1, 1 } }, 1, 1));
            imageView.setImage(SwingFXUtils.toFXImage(bi, null));
        } catch (RuntimeException e) {
            Alert alert = new Alert(AlertType.ERROR);
            alert.setTitle("Błąd");
            alert.setHeaderText("Obraz nie jest binarny");
            alert.showAndWait();
        }
    }
    
    @FXML
    private void closing(ActionEvent event) {
        if(morphology == null)
            morphology = new Morphology();
        try {
            bi = morphology.dilation(bi, new SE(new int[][] { { 1, 1, 1 }, { 1, 1, 1 }, { 1, 1, 1 } }, 1, 1));
            bi = morphology.erosion(bi, new SE(new int[][] { { 1, 1, 1 }, { 1, 1, 1 }, { 1, 1, 1 } }, 1, 1));
            imageView.setImage(SwingFXUtils.toFXImage(bi, null));
        } catch (RuntimeException e) {
            Alert alert = new Alert(AlertType.ERROR);
            alert.setTitle("Błąd");
            alert.setHeaderText("Obraz nie jest binarny");
            alert.showAndWait();
        }
    }
    
    @FXML
    private void hitOrMiss(ActionEvent event) {
        if(morphology == null)
            morphology = new Morphology();
        try {
            //TODO wybor maski w GUI
            bi = morphology.hitOrMiss(bi, new SE(new int[][] { { -1, 0, 0 }, { 1, 1, 0 }, { -1, 1, -1 } }, 1, 1));
            imageView.setImage(SwingFXUtils.toFXImage(bi, null));
        } catch (RuntimeException e) {
            Alert alert = new Alert(AlertType.ERROR);
            alert.setTitle("Błąd");
            alert.setHeaderText("Obraz nie jest binarny");
            alert.showAndWait();
        }
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
    private void skeletonization3(ActionEvent event) {
        try {
            bi = KMM.thin(bi);
            imageView.setImage(SwingFXUtils.toFXImage(bi, null));
        } catch (RuntimeException e) {
            Alert alert = new Alert(AlertType.ERROR);
            alert.setTitle("Błąd");
            alert.setHeaderText("Obraz nie jest binarny");
            alert.showAndWait();
        }
    }
    
    
    @FXML
    private void gaussFilter(ActionEvent event) {
        if(filters == null)
            filters = new Filters();
        try {
            int s = Integer.parseInt(maskSide2.getText());
            if(s == 3) {
                bi = filters.filter(bi, filters.gauss1);
                imageView.setImage(SwingFXUtils.toFXImage(bi, null));
            } else if(s == 5) {
                bi = filters.filter(bi, filters.gauss2);
                imageView.setImage(SwingFXUtils.toFXImage(bi, null));
            } else if (s == 7) {
                bi = filters.filter(bi, filters.gauss3);
                imageView.setImage(SwingFXUtils.toFXImage(bi, null));
            }
            else {
                Alert alert = new Alert(AlertType.ERROR);
                alert.setTitle("Błąd");
                alert.setHeaderText("Błędne dane wejściowe");
                alert.setContentText("Wprowadź długość maski: 3, 5 lub 7");
                alert.showAndWait();
            }
        } catch (NumberFormatException e) {
            Alert alert = new Alert(AlertType.ERROR);
            alert.setTitle("Błąd");
            alert.setHeaderText("Błędne dane wejściowe");
            alert.setContentText("Wprowadź długość maski: 3, 5 lub 7");
            alert.showAndWait();
        }
    }    
    
    @FXML
    private void lowPass1(ActionEvent event) {
        if(filters == null)
            filters = new Filters();
        bi = filters.filter(bi, filters.lowPass);
//        bi = filters.filter(bi, filters.gauss1);
//        bi = filters.kuwaharaFilter(bi, Integer.parseInt(maskSide.getText()));
//        bi = new AnisotropicDiffusionFilter().filter(bi, 50, 1, 15, 0.0);
//        bi = new WienerFilter().filter(bi);
//        bi = new GaussianFilter().filter(bi, null);
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
    private void anisotropicDiffusion(ActionEvent event) {
        bi = new AnisotropicDiffusionFilter().filter(bi, 15, 1, 15, 0.0);
        imageView.setImage(SwingFXUtils.toFXImage(bi, null));
    }
    
    @FXML
    private void kuwaharaFilter(ActionEvent event) {
        if(filters == null)
            filters = new Filters();
        try {
            bi = filters.kuwaharaFilter(bi, Integer.parseInt(maskSide2.getText()));
            imageView.setImage(SwingFXUtils.toFXImage(bi, null));
        } catch (NumberFormatException e) {
            Alert alert = new Alert(AlertType.ERROR);
            alert.setTitle("Błąd");
            alert.setHeaderText("Błędne dane wejściowe");
            alert.setContentText("Wprowadź nieparzystą długość maski");
            alert.showAndWait();
        }
    }
    
    @FXML
    private void meanFilter(ActionEvent event) {
        if(filters == null)
            filters = new Filters();
        try {
            bi = filters.meanFilter(bi, Integer.parseInt(maskSide2.getText()));
    //        bi = filters.universalFilter(bi, new int[]{1,1,1,1,1,1,1,1,1});
            imageView.setImage(SwingFXUtils.toFXImage(bi, null));
        } catch (NumberFormatException e) {
            Alert alert = new Alert(AlertType.ERROR);
            alert.setTitle("Błąd");
            alert.setHeaderText("Błędne dane wejściowe");
            alert.setContentText("Wprowadź nieparzystą długość maski");
            alert.showAndWait();
        }
    }
    
    @FXML
    private void medianFilter(ActionEvent event) {
        if(filters == null)
            filters = new Filters();
        try {
            bi = filters.medianFilter(bi, Integer.parseInt(maskSide2.getText()));
            imageView.setImage(SwingFXUtils.toFXImage(bi, null));
        } catch (NumberFormatException e) {
            Alert alert = new Alert(AlertType.ERROR);
            alert.setTitle("Błąd");
            alert.setHeaderText("Błędne dane wejściowe");
            alert.setContentText("Wprowadź nieparzystą długość maski");
            alert.showAndWait();
        }
    }
    
    @FXML
    private void autoCompare(ActionEvent event) {
        automaticComparing2(event);
    }
    
    @FXML
    private void compare(ActionEvent event) {
//        automaticComparing(event);

        if(ci == null)
            ci = new CompareImages();
//        BufferedImage gt_file = new BufferedImage(3, 3, BufferedImage.TYPE_INT_ARGB);
//        BufferedImage bi = new BufferedImage(3, 3, BufferedImage.TYPE_INT_ARGB);
//        for(int i = 0; i < 3; i++) {
//            for(int j = 0; j < 3; j++) {
//                Color c = new Color(255, 255, 255);
//                gt_file.setRGB(i, j, c.getRGB());
//                bi.setRGB(i, j, c.getRGB());
//                if(i == j) {
//                    c = new Color(0, 0, 0);
//                    gt_file.setRGB(i, j, c.getRGB());
//                    bi.setRGB(i, j, c.getRGB());
//                }
//            }
//        }
//        Color c = new Color(0, 0, 0);
//        bi.setRGB(2, 1, c.getRGB());
//        bi.setRGB(1, 2, c.getRGB());
        System.out.println("Misclassification error: " + ci.calculateMissclaficationError(bi, gt));
        System.out.println("Misclassification error 2: " + ci.ME(bi, gt));
        BufferedImage cloned = new CloneImage().deepCopy(loadedbi);
//        cloned = new GrayScale().grayScaleYUV(cloned);
//        cloned.setRGB(0, 0, new Color(30, 30, 30).getRGB());
//        cloned.setRGB(1, 1, new Color(50, 50, 50).getRGB());
//        cloned.setRGB(2, 2, new Color(20, 20, 20).getRGB());
//        for(int i = 0; i < 3; i++) {
//            for(int j = 0; j < 3; j++) {
//                System.out.print(new Color(cloned.getRGB(j, i)).getRed() + " ");
//            }
//            System.out.println();
//        }
        System.out.println("Region nonuniformity: " + ci.calculateRegionNonuniformity(cloned, bi));
        System.out.println("Relative foreground area error: " + ci.calculateRelativeForegroundAreaError(bi, gt));
        System.out.println("Relative foreground area error 2: " + ci.RFAE(bi, gt));
        System.out.println("Accuracy: " + ci.ACCURACY(bi, gt));
        System.out.println("Sensitivity: " + ci.SENSITIVITY(bi, gt));
        System.out.println("Specificity: " + ci.SPECIFICITY(bi, gt));
        System.out.println("Extraction error rate: " + ci.EER(bi, gt));
        System.out.println("F_Measure: " + ci.FMEASURE(bi, gt));
        System.out.println("PSNR: " + ci.PSNR(bi, gt));
        
    }
    
    private String compare() {
        StringBuilder sb = new StringBuilder();
        if(ci == null)
            ci = new CompareImages();
        sb.append("Misclassification error: ").append(ci.calculateMissclaficationError(bi, gt)).append(System.getProperty("line.separator"));
        sb.append("Misclassification error 2: ").append(ci.ME(bi, gt)).append(System.getProperty("line.separator"));
        BufferedImage cloned = new CloneImage().deepCopy(loadedbi);
        sb.append("Region nonuniformity: ").append(ci.calculateRegionNonuniformity(cloned, bi)).append(System.getProperty("line.separator"));
        sb.append("Relative foreground area error: ").append(ci.calculateRelativeForegroundAreaError(bi, gt)).append(System.getProperty("line.separator"));
        sb.append("Relative foreground area error 2: ").append(ci.RFAE(bi, gt)).append(System.getProperty("line.separator"));
        sb.append("Accuracy: ").append(ci.ACCURACY(bi, gt)).append(System.getProperty("line.separator"));
        sb.append("Sensitivity: ").append(ci.SENSITIVITY(bi, gt)).append(System.getProperty("line.separator"));
        sb.append("Specificity: ").append(ci.SPECIFICITY(bi, gt)).append(System.getProperty("line.separator"));
        sb.append("Extraction error rate: ").append(ci.EER(bi, gt)).append(System.getProperty("line.separator"));
        sb.append("F_Measure: ").append(ci.FMEASURE(bi, gt)).append(System.getProperty("line.separator"));
        sb.append("PSNR: ").append(ci.PSNR(bi, gt));
        return sb.toString();
    }
    
    private double compare2() {
        if(ci == null)
            ci = new CompareImages();
        BufferedImage cloned = new CloneImage().deepCopy(loadedbi);
        double me = ci.calculateMissclaficationError(bi, gt);
        double nu = ci.calculateRegionNonuniformity(cloned, bi);
        double rae = ci.calculateRelativeForegroundAreaError(bi, gt);
        double acc = ci.ACCURACY(bi, gt);
        double sens = ci.SENSITIVITY(bi, gt);
        double spec = ci.SPECIFICITY(bi, gt);
//        double eer = ci.EER(bi, gt);
        double fm = ci.FMEASURE(bi, gt);
        double result = ( (1.0 - me) + (1.0 - nu) + (1.0 - rae) + acc + sens + spec + /*(1.0 - eer)*/ + fm ) / 7.0;
        System.out.println(result);
        return result;
    }
    
    private double compare3() {
        if(ci == null)
            ci = new CompareImages();
        BufferedImage cloned = new CloneImage().deepCopy(loadedbi);
        double me = ci.calculateMissclaficationError(bi, gt);
        double nu = ci.calculateRegionNonuniformity(cloned, bi);
        double rae = ci.calculateRelativeForegroundAreaError(bi, gt);
        double acc = ci.ACCURACY(bi, gt);
        double sens = ci.SENSITIVITY(bi, gt);
        double spec = ci.SPECIFICITY(bi, gt);
//        double eer = ci.EER(bi, gt);
        double fm = ci.FMEASURE(bi, gt);
        double[] matrix = new double[]{1.0 - me, 1.0 - nu, 1.0 - rae, acc, sens, spec/*, 1.0 - eer*/, fm};
        StandardDeviation std = new StandardDeviation();
        double result = std.getStd(matrix);
        System.out.println(result);
        return result;
    }
    
    @FXML
    private void saveFile(ActionEvent event) {
        File newFile = new File(newFileName.getText() + ".jpg");
        try {
            ImageIO.write(bi, "JPG", newFile);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    
    @FXML
    private void automaticComparing(ActionEvent event) {
        BufferedWriter bw = null;
        FileWriter fw = null;
        String[] files = new String[]{"a", "b", "c", "d", "e", "f", "g", "h", "i"};
        float[][] gausses = new float[3][];
        gausses[0] = Filters.gauss1;
        gausses[1] = Filters.gauss2;
        gausses[2] = Filters.gauss3;
        int sides[] = new int[]{9, 15, 25};
        try {
            gs = new GrayScale();
            binarization = new Binarization();
            filters = new Filters();
            AnisotropicDiffusionFilter adf = new AnisotropicDiffusionFilter();
            File in, out, gt_file, out_skel;
            String content;
            
            //GLOBALNE BEZ FILTRÓW:
            for(int i = 0; i < files.length; i++) {
                in = new File("D:\\Dokumenty\\Studia\\Zajęcia\\Wykłady\\Seminarium\\wybrane rysunki\\original\\" + files[i] + ".bmp");
                gt_file = new File("D:\\Dokumenty\\Studia\\Zajęcia\\Wykłady\\Seminarium\\wybrane rysunki\\gt\\" + files[i] + "_gt.bmp");
                bi = ImageIO.read(in);
                gt = ImageIO.read(gt_file);
                loadedbi = ImageIO.read(in);
                //OTSU:
                bi = binarization.otsuBinarize(bi);
                content = compare();
                fw = new FileWriter(files[i] + "OtsuNoFilter.txt");
                bw = new BufferedWriter(fw);
                bw.write(content);
                bw.close();
                out = new File(files[i] + "OtsuNoFilter.jpg");
                ImageIO.write(bi, "JPG", out);
                bi = KMM.thin(bi);
                out_skel = new File(files[i] + "OtsuNoFilterSkel.jpg");
                ImageIO.write(bi, "JPG", out_skel);
                resetImage(event);
                //RIDLER-CALVARD:
                bi = binarization.ridlerCalvardBinarize(bi);
                content = compare();
                fw = new FileWriter(files[i] + "RidlerNoFilter.txt");
                bw = new BufferedWriter(fw);
                bw.write(content);
                bw.close();
                out = new File(files[i] + "RidlerNoFilter.jpg");
                ImageIO.write(bi, "JPG", out);
                bi = KMM.thin(bi);
                out_skel = new File(files[i] + "RidlerNoFilterSkel.jpg");
                ImageIO.write(bi, "JPG", out_skel);
                resetImage(event);
                //PUN:
                bi = binarization.punBinarize(bi);
                content = compare();
                fw = new FileWriter(files[i] + "PunNoFilter.txt");
                bw = new BufferedWriter(fw);
                bw.write(content);
                bw.close();
                out = new File(files[i] + "PunNoFilter.jpg");
                ImageIO.write(bi, "JPG", out);
                bi = KMM.thin(bi);
                out_skel = new File(files[i] + "PunNoFilterSkel.jpg");
                ImageIO.write(bi, "JPG", out_skel);
                resetImage(event);
                //KAPUR-SAHOO-WONG:
                bi = binarization.kswBinarize(bi);
                content = compare();
                fw = new FileWriter(files[i] + "KapurNoFilter.txt");
                bw = new BufferedWriter(fw);
                bw.write(content);
                bw.close();
                out = new File(files[i] + "KapurNoFilte.jpg");
                ImageIO.write(bi, "JPG", out);
                bi = KMM.thin(bi);
                out_skel = new File(files[i] + "KapurNoFilterSkel.jpg");
                ImageIO.write(bi, "JPG", out_skel);
                resetImage(event);
            }
            
            
            //GLOBALNE gauss1, gauss2, gauss3:
            for(int i = 0; i < files.length; i++) {
                in = new File("D:\\Dokumenty\\Studia\\Zajęcia\\Wykłady\\Seminarium\\wybrane rysunki\\original\\" + files[i] + ".bmp");
                gt_file = new File("D:\\Dokumenty\\Studia\\Zajęcia\\Wykłady\\Seminarium\\wybrane rysunki\\gt\\" + files[i] + "_gt.bmp");
                bi = ImageIO.read(in);
                gt = ImageIO.read(gt_file);
                loadedbi = ImageIO.read(in);
                for(int g = 0; g < gausses.length; g++) {
                    //OTSU:
                    int gg = g+1;
                    bi = filters.filter(bi, gausses[g]);
                    bi = binarization.otsuBinarize(bi);
                    content = compare();
                    fw = new FileWriter(files[i] + "OtsuGauss" + gg + ".txt");
                    bw = new BufferedWriter(fw);
                    bw.write(content);
                    bw.close();
                    out = new File(files[i] + "OtsuGauss" + gg + ".jpg");
                    ImageIO.write(bi, "JPG", out);
                    bi = KMM.thin(bi);
                    out_skel = new File(files[i] + "OtsuGauss" + gg + "Skel.jpg");
                    ImageIO.write(bi, "JPG", out_skel);
                    resetImage(event);
                    //RIDLER-CALVARD:
                    bi = filters.filter(bi, gausses[g]);
                    bi = binarization.ridlerCalvardBinarize(bi);
                    content = compare();
                    fw = new FileWriter(files[i] + "RidlerGauss" + gg + ".txt");
                    bw = new BufferedWriter(fw);
                    bw.write(content);
                    bw.close();
                    out = new File(files[i] + "RidlerGauss" + gg + ".jpg");
                    ImageIO.write(bi, "JPG", out);
                    bi = KMM.thin(bi);
                    out_skel = new File(files[i] + "RidlerGauss" + gg + "Skel.jpg");
                    ImageIO.write(bi, "JPG", out_skel);
                    resetImage(event);
                    //PUN:
                    bi = filters.filter(bi, gausses[g]);
                    bi = binarization.punBinarize(bi);
                    content = compare();
                    fw = new FileWriter(files[i] + "PunGauss" + gg + ".txt");
                    bw = new BufferedWriter(fw);
                    bw.write(content);
                    bw.close();
                    out = new File(files[i] + "PunGauss" + gg + ".jpg");
                    ImageIO.write(bi, "JPG", out);
                    bi = KMM.thin(bi);
                    out_skel = new File(files[i] + "PunGauss" + gg + "Skel.jpg");
                    ImageIO.write(bi, "JPG", out_skel);
                    resetImage(event);
                    //KAPUR-SAHOO-WONG:
                    bi = filters.filter(bi, gausses[g]);
                    bi = binarization.kswBinarize(bi);
                    content = compare();
                    fw = new FileWriter(files[i] + "KapurGauss" + gg + ".txt");
                    bw = new BufferedWriter(fw);
                    bw.write(content);
                    bw.close();
                    out = new File(files[i] + "KapurGauss" + gg + ".jpg");
                    ImageIO.write(bi, "JPG", out);
                    bi = KMM.thin(bi);
                    out_skel = new File(files[i] + "KapurGauss" + gg + "Skel.jpg");
                    ImageIO.write(bi, "JPG", out_skel);
                    resetImage(event);
                }
            }
            
            
            //GLOBALNE, MEDIANA x1 3x3
            for(int i = 0; i < files.length; i++) {
                in = new File("D:\\Dokumenty\\Studia\\Zajęcia\\Wykłady\\Seminarium\\wybrane rysunki\\original\\" + files[i] + ".bmp");
                gt_file = new File("D:\\Dokumenty\\Studia\\Zajęcia\\Wykłady\\Seminarium\\wybrane rysunki\\gt\\" + files[i] + "_gt.bmp");
                bi = ImageIO.read(in);
                gt = ImageIO.read(gt_file);
                loadedbi = ImageIO.read(in);
                //OTSU:
                bi = filters.medianFilter(bi, 3);
                bi = binarization.otsuBinarize(bi);
                content = compare();
                fw = new FileWriter(files[i] + "OtsuMedian.txt");
                bw = new BufferedWriter(fw);
                bw.write(content);
                bw.close();
                out = new File(files[i] + "OtsuMedian.jpg");
                ImageIO.write(bi, "JPG", out);
                bi = KMM.thin(bi);
                out_skel = new File(files[i] + "OtsuMedianSkel.jpg");
                ImageIO.write(bi, "JPG", out_skel);
                resetImage(event);
                //RIDLER-CALVARD:
                bi = filters.medianFilter(bi, 3);
                bi = binarization.ridlerCalvardBinarize(bi);
                content = compare();
                fw = new FileWriter(files[i] + "RidlerMedian.txt");
                bw = new BufferedWriter(fw);
                bw.write(content);
                bw.close();
                out = new File(files[i] + "RidlerMedian.jpg");
                ImageIO.write(bi, "JPG", out);
                bi = KMM.thin(bi);
                out_skel = new File(files[i] + "RidlerMedianSkel.jpg");
                ImageIO.write(bi, "JPG", out_skel);
                resetImage(event);
                //PUN:
                bi = filters.medianFilter(bi, 3);
                bi = binarization.punBinarize(bi);
                content = compare();
                fw = new FileWriter(files[i] + "PunMedian.txt");
                bw = new BufferedWriter(fw);
                bw.write(content);
                bw.close();
                out = new File(files[i] + "PunMedian.jpg");
                ImageIO.write(bi, "JPG", out);
                bi = KMM.thin(bi);
                out_skel = new File(files[i] + "PunMedianSkel.jpg");
                ImageIO.write(bi, "JPG", out_skel);
                resetImage(event);
                //KAPUR-SAHOO-WONG:
                bi = filters.medianFilter(bi, 3);
                bi = binarization.kswBinarize(bi);
                content = compare();
                fw = new FileWriter(files[i] + "KapurMedian.txt");
                bw = new BufferedWriter(fw);
                bw.write(content);
                bw.close();
                out = new File(files[i] + "KapurMedian.jpg");
                ImageIO.write(bi, "JPG", out);
                bi = KMM.thin(bi);
                out_skel = new File(files[i] + "KapurMedianSkel.jpg");
                ImageIO.write(bi, "JPG", out_skel);
                resetImage(event);
            }
            //GLOBALNE, MEDIANA x1 5x5
            for(int i = 0; i < files.length; i++) {
                in = new File("D:\\Dokumenty\\Studia\\Zajęcia\\Wykłady\\Seminarium\\wybrane rysunki\\original\\" + files[i] + ".bmp");
                gt_file = new File("D:\\Dokumenty\\Studia\\Zajęcia\\Wykłady\\Seminarium\\wybrane rysunki\\gt\\" + files[i] + "_gt.bmp");
                bi = ImageIO.read(in);
                gt = ImageIO.read(gt_file);
                loadedbi = ImageIO.read(in);
                //OTSU:
                bi = filters.medianFilter(bi, 5);
                bi = binarization.otsuBinarize(bi);
                content = compare();
                fw = new FileWriter(files[i] + "OtsuMedian5.txt");
                bw = new BufferedWriter(fw);
                bw.write(content);
                bw.close();
                out = new File(files[i] + "OtsuMedian5.jpg");
                ImageIO.write(bi, "JPG", out);
                bi = KMM.thin(bi);
                out_skel = new File(files[i] + "OtsuMedian5Skel.jpg");
                ImageIO.write(bi, "JPG", out_skel);
                resetImage(event);
                //RIDLER-CALVARD:
                bi = filters.medianFilter(bi, 5);
                bi = binarization.ridlerCalvardBinarize(bi);
                content = compare();
                fw = new FileWriter(files[i] + "RidlerMedian5.txt");
                bw = new BufferedWriter(fw);
                bw.write(content);
                bw.close();
                out = new File(files[i] + "RidlerMedian5.jpg");
                ImageIO.write(bi, "JPG", out);
                bi = KMM.thin(bi);
                out_skel = new File(files[i] + "RidlerMedian5Skel.jpg");
                ImageIO.write(bi, "JPG", out_skel);
                resetImage(event);
                //PUN:
                bi = filters.medianFilter(bi, 5);
                bi = binarization.punBinarize(bi);
                content = compare();
                fw = new FileWriter(files[i] + "PunMedian5.txt");
                bw = new BufferedWriter(fw);
                bw.write(content);
                bw.close();
                out = new File(files[i] + "PunMedian5.jpg");
                ImageIO.write(bi, "JPG", out);
                bi = KMM.thin(bi);
                out_skel = new File(files[i] + "PunMedian5Skel.jpg");
                ImageIO.write(bi, "JPG", out_skel);
                resetImage(event);
                //KAPUR-SAHOO-WONG:
                bi = filters.medianFilter(bi, 5);
                bi = binarization.kswBinarize(bi);
                content = compare();
                fw = new FileWriter(files[i] + "KapurMedian5.txt");
                bw = new BufferedWriter(fw);
                bw.write(content);
                bw.close();
                out = new File(files[i] + "KapurMedian5.jpg");
                ImageIO.write(bi, "JPG", out);
                bi = KMM.thin(bi);
                out_skel = new File(files[i] + "KapurMedian5Skel.jpg");
                ImageIO.write(bi, "JPG", out_skel);
                resetImage(event);
            }
            
            //GLOBALNE, Kuwahar x1 3x3
            for(int i = 0; i < files.length; i++) {
                in = new File("D:\\Dokumenty\\Studia\\Zajęcia\\Wykłady\\Seminarium\\wybrane rysunki\\original\\" + files[i] + ".bmp");
                gt_file = new File("D:\\Dokumenty\\Studia\\Zajęcia\\Wykłady\\Seminarium\\wybrane rysunki\\gt\\" + files[i] + "_gt.bmp");
                bi = ImageIO.read(in);
                gt = ImageIO.read(gt_file);
                loadedbi = ImageIO.read(in);
                //OTSU:
                bi = filters.kuwaharaFilter(bi, 3);
                bi = binarization.otsuBinarize(bi);
                content = compare();
                fw = new FileWriter(files[i] + "OtsuKuwahara.txt");
                bw = new BufferedWriter(fw);
                bw.write(content);
                bw.close();
                out = new File(files[i] + "OtsuKuwahara.jpg");
                ImageIO.write(bi, "JPG", out);
                bi = KMM.thin(bi);
                out_skel = new File(files[i] + "OtsuKuwaharaSkel.jpg");
                ImageIO.write(bi, "JPG", out_skel);
                resetImage(event);
                //RIDLER-CALVARD:
                bi = filters.kuwaharaFilter(bi, 3);
                bi = binarization.ridlerCalvardBinarize(bi);
                content = compare();
                fw = new FileWriter(files[i] + "RidlerKuwahara.txt");
                bw = new BufferedWriter(fw);
                bw.write(content);
                bw.close();
                out = new File(files[i] + "RidlerKuwahara.jpg");
                ImageIO.write(bi, "JPG", out);
                bi = KMM.thin(bi);
                out_skel = new File(files[i] + "RidlerKuwaharaSkel.jpg");
                ImageIO.write(bi, "JPG", out_skel);
                resetImage(event);
                //PUN:
                bi = filters.kuwaharaFilter(bi, 3);
                bi = binarization.punBinarize(bi);
                content = compare();
                fw = new FileWriter(files[i] + "PunKuwahara.txt");
                bw = new BufferedWriter(fw);
                bw.write(content);
                bw.close();
                out = new File(files[i] + "PunKuwahara.jpg");
                ImageIO.write(bi, "JPG", out);
                bi = KMM.thin(bi);
                out_skel = new File(files[i] + "PunKuwaharaSkel.jpg");
                ImageIO.write(bi, "JPG", out_skel);
                resetImage(event);
                //KAPUR-SAHOO-WONG:
                bi = filters.kuwaharaFilter(bi, 3);
                bi = binarization.kswBinarize(bi);
                content = compare();
                fw = new FileWriter(files[i] + "KapurKuwahara.txt");
                bw = new BufferedWriter(fw);
                bw.write(content);
                bw.close();
                out = new File(files[i] + "KapurKuwahara.jpg");
                ImageIO.write(bi, "JPG", out);
                bi = KMM.thin(bi);
                out_skel = new File(files[i] + "KapurKuwaharaSkel.jpg");
                ImageIO.write(bi, "JPG", out_skel);
                resetImage(event);
            }
            //GLOBALNE, Kuwahar x1 5x5
            for(int i = 0; i < files.length; i++) {
                in = new File("D:\\Dokumenty\\Studia\\Zajęcia\\Wykłady\\Seminarium\\wybrane rysunki\\original\\" + files[i] + ".bmp");
                gt_file = new File("D:\\Dokumenty\\Studia\\Zajęcia\\Wykłady\\Seminarium\\wybrane rysunki\\gt\\" + files[i] + "_gt.bmp");
                bi = ImageIO.read(in);
                gt = ImageIO.read(gt_file);
                loadedbi = ImageIO.read(in);
                //OTSU:
                bi = filters.kuwaharaFilter(bi, 5);
                bi = binarization.otsuBinarize(bi);
                content = compare();
                fw = new FileWriter(files[i] + "OtsuKuwahara5.txt");
                bw = new BufferedWriter(fw);
                bw.write(content);
                bw.close();
                out = new File(files[i] + "OtsuKuwahara5.jpg");
                ImageIO.write(bi, "JPG", out);
                bi = KMM.thin(bi);
                out_skel = new File(files[i] + "OtsuKuwahara5Skel.jpg");
                ImageIO.write(bi, "JPG", out_skel);
                resetImage(event);
                //RIDLER-CALVARD:
                bi = filters.kuwaharaFilter(bi, 5);
                bi = binarization.ridlerCalvardBinarize(bi);
                content = compare();
                fw = new FileWriter(files[i] + "RidlerKuwahara5.txt");
                bw = new BufferedWriter(fw);
                bw.write(content);
                bw.close();
                out = new File(files[i] + "RidlerKuwahara5.jpg");
                ImageIO.write(bi, "JPG", out);
                bi = KMM.thin(bi);
                out_skel = new File(files[i] + "RidlerKuwahara5Skel.jpg");
                ImageIO.write(bi, "JPG", out_skel);
                resetImage(event);
                //PUN:
                bi = filters.kuwaharaFilter(bi, 5);
                bi = binarization.punBinarize(bi);
                content = compare();
                fw = new FileWriter(files[i] + "PunKuwahara5.txt");
                bw = new BufferedWriter(fw);
                bw.write(content);
                bw.close();
                out = new File(files[i] + "PunKuwahara5.jpg");
                ImageIO.write(bi, "JPG", out);
                bi = KMM.thin(bi);
                out_skel = new File(files[i] + "PunKuwahara5Skel.jpg");
                ImageIO.write(bi, "JPG", out_skel);
                resetImage(event);
                //KAPUR-SAHOO-WONG:
                bi = filters.kuwaharaFilter(bi, 5);
                bi = binarization.kswBinarize(bi);
                content = compare();
                fw = new FileWriter(files[i] + "KapurKuwahara5.txt");
                bw = new BufferedWriter(fw);
                bw.write(content);
                bw.close();
                out = new File(files[i] + "KapurKuwahara5.jpg");
                ImageIO.write(bi, "JPG", out);
                bi = KMM.thin(bi);
                out_skel = new File(files[i] + "KapurKuwahara5Skel.jpg");
                ImageIO.write(bi, "JPG", out_skel);
                resetImage(event);
            }
            
            
            //GLOBALNE, ANIZOTROPIC DIFF 5 iteracji, threshold 15, sigma 0
            for(int i = 0; i < files.length; i++) {
                in = new File("D:\\Dokumenty\\Studia\\Zajęcia\\Wykłady\\Seminarium\\wybrane rysunki\\original\\" + files[i] + ".bmp");
                gt_file = new File("D:\\Dokumenty\\Studia\\Zajęcia\\Wykłady\\Seminarium\\wybrane rysunki\\gt\\" + files[i] + "_gt.bmp");
                bi = ImageIO.read(in);
                gt = ImageIO.read(gt_file);
                loadedbi = ImageIO.read(in);
                //OTSU:
                bi = adf.filter(bi, 5, 1, 15, 0.0);
                bi = binarization.otsuBinarize(bi);
                content = compare();
                fw = new FileWriter(files[i] + "OtsuAnizotropic5.txt");
                bw = new BufferedWriter(fw);
                bw.write(content);
                bw.close();
                out = new File(files[i] + "OtsuAnizotropic5.jpg");
                ImageIO.write(bi, "JPG", out);
                bi = KMM.thin(bi);
                out_skel = new File(files[i] + "OtsuAnizotropic5Skel.jpg");
                ImageIO.write(bi, "JPG", out_skel);
                resetImage(event);
                //RIDLER-CALVARD:
                bi = adf.filter(bi, 5, 1, 15, 0.0);
                bi = binarization.ridlerCalvardBinarize(bi);
                content = compare();
                fw = new FileWriter(files[i] + "RidlerAnizotropic5.txt");
                bw = new BufferedWriter(fw);
                bw.write(content);
                bw.close();
                out = new File(files[i] + "RidlerAnizotropic5.jpg");
                ImageIO.write(bi, "JPG", out);
                bi = KMM.thin(bi);
                out_skel = new File(files[i] + "RidlerMAnizotropic5Skel.jpg");
                ImageIO.write(bi, "JPG", out_skel);
                resetImage(event);
                //PUN:
                bi = adf.filter(bi, 5, 1, 15, 0.0);
                bi = binarization.punBinarize(bi);
                content = compare();
                fw = new FileWriter(files[i] + "PunAnizotropic5.txt");
                bw = new BufferedWriter(fw);
                bw.write(content);
                bw.close();
                out = new File(files[i] + "PunAnizotropic5.jpg");
                ImageIO.write(bi, "JPG", out);
                bi = KMM.thin(bi);
                out_skel = new File(files[i] + "PunAnizotropic5Skel.jpg");
                ImageIO.write(bi, "JPG", out_skel);
                resetImage(event);
                //KAPUR-SAHOO-WONG:
                bi = adf.filter(bi, 5, 1, 15, 0.0);
                bi = binarization.kswBinarize(bi);
                content = compare();
                fw = new FileWriter(files[i] + "KapurAnizotropic5.txt");
                bw = new BufferedWriter(fw);
                bw.write(content);
                bw.close();
                out = new File(files[i] + "KapurAnizotropic5.jpg");
                ImageIO.write(bi, "JPG", out);
                bi = KMM.thin(bi);
                out_skel = new File(files[i] + "KapurAnizotropic5Skel.jpg");
                ImageIO.write(bi, "JPG", out_skel);
                resetImage(event);
            }
            
            //GLOBALNE, ANIZOTROPIC DIFF 10 iteracji, threshold 15, sigma 0
            for(int i = 0; i < files.length; i++) {
                in = new File("D:\\Dokumenty\\Studia\\Zajęcia\\Wykłady\\Seminarium\\wybrane rysunki\\original\\" + files[i] + ".bmp");
                gt_file = new File("D:\\Dokumenty\\Studia\\Zajęcia\\Wykłady\\Seminarium\\wybrane rysunki\\gt\\" + files[i] + "_gt.bmp");
                bi = ImageIO.read(in);
                gt = ImageIO.read(gt_file);
                loadedbi = ImageIO.read(in);
                //OTSU:
                bi = adf.filter(bi, 10, 1, 15, 0.0);
                bi = binarization.otsuBinarize(bi);
                content = compare();
                fw = new FileWriter(files[i] + "OtsuAnizotropic10.txt");
                bw = new BufferedWriter(fw);
                bw.write(content);
                bw.close();
                out = new File(files[i] + "OtsuAnizotropic10.jpg");
                ImageIO.write(bi, "JPG", out);
                bi = KMM.thin(bi);
                out_skel = new File(files[i] + "OtsuAnizotropic10Skel.jpg");
                ImageIO.write(bi, "JPG", out_skel);
                resetImage(event);
                //RIDLER-CALVARD:
                bi = adf.filter(bi, 10, 1, 15, 0.0);
                bi = binarization.ridlerCalvardBinarize(bi);
                content = compare();
                fw = new FileWriter(files[i] + "RidlerAnizotropic10.txt");
                bw = new BufferedWriter(fw);
                bw.write(content);
                bw.close();
                out = new File(files[i] + "RidlerAnizotropic10.jpg");
                ImageIO.write(bi, "JPG", out);
                bi = KMM.thin(bi);
                out_skel = new File(files[i] + "RidlerAnizotropic10Skel.jpg");
                ImageIO.write(bi, "JPG", out_skel);
                resetImage(event);
                //PUN:
                bi = adf.filter(bi, 10, 1, 15, 0.0);
                bi = binarization.punBinarize(bi);
                content = compare();
                fw = new FileWriter(files[i] + "PunAnizotropic10.txt");
                bw = new BufferedWriter(fw);
                bw.write(content);
                bw.close();
                out = new File(files[i] + "PunAnizotropic10.jpg");
                ImageIO.write(bi, "JPG", out);
                bi = KMM.thin(bi);
                out_skel = new File(files[i] + "PunAnizotropic10Skel.jpg");
                ImageIO.write(bi, "JPG", out_skel);
                resetImage(event);
                //KAPUR-SAHOO-WONG:
                bi = adf.filter(bi, 10, 1, 15, 0.0);
                bi = binarization.kswBinarize(bi);
                content = compare();
                fw = new FileWriter(files[i] + "KapurAnizotropic10.txt");
                bw = new BufferedWriter(fw);
                bw.write(content);
                bw.close();
                out = new File(files[i] + "KapurAnizotropic10.jpg");
                ImageIO.write(bi, "JPG", out);
                bi = KMM.thin(bi);
                out_skel = new File(files[i] + "KapurAnizotropic10Skel.jpg");
                ImageIO.write(bi, "JPG", out_skel);
                resetImage(event);
            }
            
            
            //LOKALNE BEZ FILTRÓW:
            for(int i = 0; i < files.length; i++) {
                in = new File("D:\\Dokumenty\\Studia\\Zajęcia\\Wykłady\\Seminarium\\wybrane rysunki\\original\\" + files[i] + ".bmp");
                gt_file = new File("D:\\Dokumenty\\Studia\\Zajęcia\\Wykłady\\Seminarium\\wybrane rysunki\\gt\\" + files[i] + "_gt.bmp");
                bi = ImageIO.read(in);
                gt = ImageIO.read(gt_file);
                loadedbi = ImageIO.read(in);
                //BERNSEN:
                //9x9, 15x15,25x25
                for(int s = 0; s < sides.length; s++) {
                    int[] epsilons = new int[]{30, 50, 70};
                    //bez epsilonu
                    bi = binarization.bernsenBinarize(bi, sides[s]);
                    content = compare();
                    fw = new FileWriter(files[i] + "Bernsen" + sides[s] + "NoFilter.txt");
                    bw = new BufferedWriter(fw);
                    bw.write(content);
                    bw.close();
                    out = new File(files[i] + "Bernsen" + sides[s] + "NoFilter.jpg");
                    ImageIO.write(bi, "JPG", out);
                    bi = KMM.thin(bi);
                    out_skel = new File(files[i] + "Bernsen" + sides[s] + "NoFilterSkel.jpg");
                    ImageIO.write(bi, "JPG", out_skel);
                    resetImage(event);
                    //epsilons 30, 50, 70:, thresh=127
                    for(int eps = 0; eps < epsilons.length; eps++) {
                        bi = binarization.bernsenBinarize2(bi, sides[s], 127, epsilons[eps]);
                        content = compare();
                        fw = new FileWriter(files[i] + "Bernsen" + sides[s] + "Eps" + epsilons[eps] + "NoFilter.txt");
                        bw = new BufferedWriter(fw);
                        bw.write(content);
                        bw.close();
                        out = new File(files[i] + "Bernsen" + sides[s] + "Eps" + epsilons[eps] + "NoFilter.jpg");
                        ImageIO.write(bi, "JPG", out);
                        bi = KMM.thin(bi);
                        out_skel = new File(files[i] + "Bernsen" + sides[s] + "Eps" + epsilons[eps] + "NoFilterSkel.jpg");
                        ImageIO.write(bi, "JPG", out_skel);
                        resetImage(event);
                    }
                }
                //WHITE-ROHRER:
                //9x9, 15x15,25x25
                for(int s = 0; s < sides.length; s++) {
                    double[] ks = new double[]{1.2, 1.5, 2.0, 2.3};
                    String[] kss = new String[]{"12", "15", "20", "23"};
                    //k=1.2, 1.5, 2.0, 2.3
                    for(int k = 0; k < ks.length; k++) {
                        bi = binarization.whiteRohrerBinarize(bi, sides[s], ks[k]);
                        content = compare();
                        fw = new FileWriter(files[i] + "WhiteRohrer" + sides[s] + "K" + kss[k] + "NoFilter.txt");
                        bw = new BufferedWriter(fw);
                        bw.write(content);
                        bw.close();
                        out = new File(files[i] + "WhiteRohrer" + sides[s] + "K" + kss[k] + "NoFilter.jpg");
                        ImageIO.write(bi, "JPG", out);
                        bi = KMM.thin(bi);
                        out_skel = new File(files[i] + "WhiteRohrer" + sides[s] + "K" + kss[k] + "NoFilterSkel.jpg");
                        ImageIO.write(bi, "JPG", out_skel);
                        resetImage(event);
                    }
                }
                //NIBLACK:
                 //9x9, 15x15,25x25
                for(int s = 0; s < sides.length; s++) {
                    double[] ks = new double[]{-0.5, -1.0, -1.5};
                    String[] kss = new String[]{"-05", "-10", "-15"};
                    //k=-0.5, -1.0, -1.5
                    for(int k = 0; k < ks.length; k++) {
                        bi = binarization.niblackBinarize(bi, sides[s], ks[k]);
                        content = compare();
                        fw = new FileWriter(files[i] + "Niblack" + sides[s] + "K" + kss[k] + "NoFilter.txt");
                        bw = new BufferedWriter(fw);
                        bw.write(content);
                        bw.close();
                        out = new File(files[i] + "Niblack" + sides[s] + "K" + kss[k] + "NoFilter.jpg");
                        ImageIO.write(bi, "JPG", out);
                        bi = KMM.thin(bi);
                        out_skel = new File(files[i] + "Niblack" + sides[s] + "K" + kss[k] + "NoFilterSkel.jpg");
                        ImageIO.write(bi, "JPG", out_skel);
                        resetImage(event);
                    }
                }
                //SAUVOLA:
                //9x9, 15x15,25x25
                for(int s = 0; s < sides.length; s++) {
                    double[] ks = new double[]{0.3, 0.5, 0.7};
                    String[] kss = new String[]{"03", "05", "07"};
                    //k=0.3, 0.5, 0.7
                    for(int k = 0; k < ks.length; k++) {
                        bi = binarization.sauvolaBinarize(bi, sides[s], ks[k], 128);
                        content = compare();
                        fw = new FileWriter(files[i] + "Sauvola" + sides[s] + "K" + kss[k] + "NoFilter.txt");
                        bw = new BufferedWriter(fw);
                        bw.write(content);
                        bw.close();
                        out = new File(files[i] + "Sauvola" + sides[s] + "K" + kss[k] + "NoFilter.jpg");
                        ImageIO.write(bi, "JPG", out);
                        bi = KMM.thin(bi);
                        out_skel = new File(files[i] + "Sauvola" + sides[s] + "K" + kss[k] + "NoFilterSkel.jpg");
                        ImageIO.write(bi, "JPG", out_skel);
                        resetImage(event);
                    }
                }
                //BRADLEY:
                //s=9, 15, 25
                int sides2[] = new int[]{9, 15, 25, 50, 100};
                for(int s = 0; s < sides2.length; s++) {
                    int[] ts = new int[]{10, 15, 20};
                    String[] tss = new String[]{"10", "15", "20"};
                    //t=10, 15, 20
                    for(int t = 0; t < ts.length; t++) {
                        bi = binarization.bradleyBinarize(bi, sides2[s], ts[t]);
                        content = compare();
                        fw = new FileWriter(files[i] + "BradleyS" + sides2[s] + "T" + tss[t] + "NoFilter.txt");
                        bw = new BufferedWriter(fw);
                        bw.write(content);
                        bw.close();
                        out = new File(files[i] + "BradleyS" + sides2[s] + "T" + tss[t] + "NoFilter.jpg");
                        ImageIO.write(bi, "JPG", out);
                        bi = KMM.thin(bi);
                        out_skel = new File(files[i] + "BradleyS" + sides2[s] + "T" + tss[t] + "NoFilterSkel.jpg");
                        ImageIO.write(bi, "JPG", out_skel);
                        resetImage(event);
                    }
                }
            }
            
            //LOKALNE gauss1, gauss2, gauss3:
            for(int i = 0; i < files.length; i++) {
                in = new File("D:\\Dokumenty\\Studia\\Zajęcia\\Wykłady\\Seminarium\\wybrane rysunki\\original\\" + files[i] + ".bmp");
                gt_file = new File("D:\\Dokumenty\\Studia\\Zajęcia\\Wykłady\\Seminarium\\wybrane rysunki\\gt\\" + files[i] + "_gt.bmp");
                bi = ImageIO.read(in);
                gt = ImageIO.read(gt_file);
                loadedbi = ImageIO.read(in);
                
                for(int g = 0; g < gausses.length; g++) {
                    int gg = g+1;
                    //BERNSEN:
                    //9x9, 15x15,25x25
                    for(int s = 0; s < sides.length; s++) {
                        int[] epsilons = new int[]{30, 50, 70};
                        //bez epsilonu
                        bi = filters.filter(bi, gausses[g]);
                        bi = binarization.bernsenBinarize(bi, sides[s]);
                        content = compare();
                        fw = new FileWriter(files[i] + "Bernsen" + sides[s] + "Gauss" + gg + ".txt");
                        bw = new BufferedWriter(fw);
                        bw.write(content);
                        bw.close();
                        out = new File(files[i] + "Bernsen" + sides[s] + "Gauss" + gg + ".jpg");
                        ImageIO.write(bi, "JPG", out);
                        bi = KMM.thin(bi);
                        out_skel = new File(files[i] + "Bernsen" + sides[s] + "Gauss" + gg + "Skel.jpg");
                        ImageIO.write(bi, "JPG", out_skel);
                        resetImage(event);
                        //epsilons 30, 50, 70:, thresh=127
                        for(int eps = 0; eps < epsilons.length; eps++) {
                            bi = filters.filter(bi, gausses[g]);
                            bi = binarization.bernsenBinarize2(bi, sides[s], 127, epsilons[eps]);
                            content = compare();
                            fw = new FileWriter(files[i] + "Bernsen" + sides[s] + "Eps" + epsilons[eps] + "Gauss" + gg + ".txt");
                            bw = new BufferedWriter(fw);
                            bw.write(content);
                            bw.close();
                            out = new File(files[i] + "Bernsen" + sides[s] + "Eps" + epsilons[eps] + "Gauss" + gg + ".jpg");
                            ImageIO.write(bi, "JPG", out);
                            bi = KMM.thin(bi);
                            out_skel = new File(files[i] + "Bernsen" + sides[s] + "Eps" + epsilons[eps] + "Gauss" + gg + "Skel.jpg");
                            ImageIO.write(bi, "JPG", out_skel);
                            resetImage(event);
                        }
                    }
                    //WHITE-ROHRER:
                    //9x9, 15x15,25x25
                    for(int s = 0; s < sides.length; s++) {
                        double[] ks = new double[]{1.2, 1.5, 2.0, 2.3};
                        String[] kss = new String[]{"12", "15", "20", "23"};
                        //k=1.2, 1.5, 2.0, 2.3
                        for(int k = 0; k < ks.length; k++) {
                            bi = filters.filter(bi, gausses[g]);
                            bi = binarization.whiteRohrerBinarize(bi, sides[s], ks[k]);
                            content = compare();
                            fw = new FileWriter(files[i] + "WhiteRohrer" + sides[s] + "K" + kss[k] + "Gauss" + gg + ".txt");
                            bw = new BufferedWriter(fw);
                            bw.write(content);
                            bw.close();
                            out = new File(files[i] + "WhiteRohrer" + sides[s] + "K" + kss[k] + "Gauss" + gg + ".jpg");
                            ImageIO.write(bi, "JPG", out);
                            bi = KMM.thin(bi);
                            out_skel = new File(files[i] + "WhiteRohrer" + sides[s] + "K" + kss[k] + "Gauss" + gg + "Skel.jpg");
                            ImageIO.write(bi, "JPG", out_skel);
                            resetImage(event);
                        }
                    }
                    //NIBLACK:
                     //9x9, 15x15,25x25
                    for(int s = 0; s < sides.length; s++) {
                        double[] ks = new double[]{-0.5, -1.0, -1.5};
                        String[] kss = new String[]{"-05", "-10", "-15"};
                        //k=-0.5, -1.0, -1.5
                        for(int k = 0; k < ks.length; k++) {
                            bi = filters.filter(bi, gausses[g]);
                            bi = binarization.niblackBinarize(bi, sides[s], ks[k]);
                            content = compare();
                            fw = new FileWriter(files[i] + "Niblack" + sides[s] + "K" + kss[k] + "Gauss" + gg + ".txt");
                            bw = new BufferedWriter(fw);
                            bw.write(content);
                            bw.close();
                            out = new File(files[i] + "Niblack" + sides[s] + "K" + kss[k] + "Gauss" + gg + ".jpg");
                            ImageIO.write(bi, "JPG", out);
                            bi = KMM.thin(bi);
                            out_skel = new File(files[i] + "Niblack" + sides[s] + "K" + kss[k] + "Gauss" + gg + "Skel.jpg");
                            ImageIO.write(bi, "JPG", out_skel);
                            resetImage(event);
                        }
                    }
                    //SAUVOLA:
                    //9x9, 15x15,25x25
                    for(int s = 0; s < sides.length; s++) {
                        double[] ks = new double[]{0.3, 0.5, 0.7};
                        String[] kss = new String[]{"03", "05", "07"};
                        //k=0.3, 0.5, 0.7
                        for(int k = 0; k < ks.length; k++) {
                            bi = filters.filter(bi, gausses[g]);
                            bi = binarization.sauvolaBinarize(bi, sides[s], ks[k], 128);
                            content = compare();
                            fw = new FileWriter(files[i] + "Sauvola" + sides[s] + "K" + kss[k] + "Gauss" + gg + ".txt");
                            bw = new BufferedWriter(fw);
                            bw.write(content);
                            bw.close();
                            out = new File(files[i] + "Sauvola" + sides[s] + "K" + kss[k] + "Gauss" + gg + ".jpg");
                            ImageIO.write(bi, "JPG", out);
                            bi = KMM.thin(bi);
                            out_skel = new File(files[i] + "Sauvola" + sides[s] + "K" + kss[k] + "Gauss" + gg + "Skel.jpg");
                            ImageIO.write(bi, "JPG", out_skel);
                            resetImage(event);
                        }
                    }
                    //BRADLEY:
                    //s=9, 15, 25
                    int sides2[] = new int[]{9, 15, 25, 50, 100};
                    for(int s = 0; s < sides2.length; s++) {
                        int[] ts = new int[]{10, 15, 20};
                        String[] tss = new String[]{"10", "15", "20"};
                        //t=10, 15, 20
                        for(int t = 0; t < ts.length; t++) {
                            bi = filters.filter(bi, gausses[g]);
                            bi = binarization.bradleyBinarize(bi, sides2[s], ts[t]);
                            content = compare();
                            fw = new FileWriter(files[i] + "BradleyS" + sides2[s] + "T" + tss[t] + "Gauss" + gg + ".txt");
                            bw = new BufferedWriter(fw);
                            bw.write(content);
                            bw.close();
                            out = new File(files[i] + "BradleyS" + sides2[s] + "T" + tss[t] + "Gauss" + gg + ".jpg");
                            ImageIO.write(bi, "JPG", out);
                            bi = KMM.thin(bi);
                            out_skel = new File(files[i] + "BradleyS" + sides2[s] + "T" + tss[t] + "Gauss" + gg + "Skel.jpg");
                            ImageIO.write(bi, "JPG", out_skel);
                            resetImage(event);
                        }
                    }
                }
            }
            
            
            //LOKALNE MEDIANAA 3X3:
            for(int i = 0; i < files.length; i++) {
                in = new File("D:\\Dokumenty\\Studia\\Zajęcia\\Wykłady\\Seminarium\\wybrane rysunki\\original\\" + files[i] + ".bmp");
                gt_file = new File("D:\\Dokumenty\\Studia\\Zajęcia\\Wykłady\\Seminarium\\wybrane rysunki\\gt\\" + files[i] + "_gt.bmp");
                bi = ImageIO.read(in);
                gt = ImageIO.read(gt_file);
                loadedbi = ImageIO.read(in);
                //BERNSEN:
                //9x9, 15x15,25x25
                for(int s = 0; s < sides.length; s++) {
                    int[] epsilons = new int[]{30, 50, 70};
                    //bez epsilonu
                    bi = filters.medianFilter(bi, 3);
                    bi = binarization.bernsenBinarize(bi, sides[s]);
                    content = compare();
                    fw = new FileWriter(files[i] + "Bernsen" + sides[s] + "Median.txt");
                    bw = new BufferedWriter(fw);
                    bw.write(content);
                    bw.close();
                    out = new File(files[i] + "Bernsen" + sides[s] + "Median.jpg");
                    ImageIO.write(bi, "JPG", out);
                    bi = KMM.thin(bi);
                    out_skel = new File(files[i] + "Bernsen" + sides[s] + "MedianSkel.jpg");
                    ImageIO.write(bi, "JPG", out_skel);
                    resetImage(event);
                    //epsilons 30, 50, 70:, thresh=127
                    for(int eps = 0; eps < epsilons.length; eps++) {
                        bi = filters.medianFilter(bi, 3);
                        bi = binarization.bernsenBinarize2(bi, sides[s], 127, epsilons[eps]);
                        content = compare();
                        fw = new FileWriter(files[i] + "Bernsen" + sides[s] + "Eps" + epsilons[eps] + "Median.txt");
                        bw = new BufferedWriter(fw);
                        bw.write(content);
                        bw.close();
                        out = new File(files[i] + "Bernsen" + sides[s] + "Eps" + epsilons[eps] + "Median.jpg");
                        ImageIO.write(bi, "JPG", out);
                        bi = KMM.thin(bi);
                        out_skel = new File(files[i] + "Bernsen" + sides[s] + "Eps" + epsilons[eps] + "MedianSkel.jpg");
                        ImageIO.write(bi, "JPG", out_skel);
                        resetImage(event);
                    }
                }
                //WHITE-ROHRER:
                //9x9, 15x15,25x25
                for(int s = 0; s < sides.length; s++) {
                    double[] ks = new double[]{1.2, 1.5, 2.0, 2.3};
                    String[] kss = new String[]{"12", "15", "20", "23"};
                    //k=1.2, 1.5, 2.0, 2.3
                    for(int k = 0; k < ks.length; k++) {
                        bi = filters.medianFilter(bi, 3);
                        bi = binarization.whiteRohrerBinarize(bi, sides[s], ks[k]);
                        content = compare();
                        fw = new FileWriter(files[i] + "WhiteRohrer" + sides[s] + "K" + kss[k] + "Median.txt");
                        bw = new BufferedWriter(fw);
                        bw.write(content);
                        bw.close();
                        out = new File(files[i] + "WhiteRohrer" + sides[s] + "K" + kss[k] + "Median.jpg");
                        ImageIO.write(bi, "JPG", out);
                        bi = KMM.thin(bi);
                        out_skel = new File(files[i] + "WhiteRohrer" + sides[s] + "K" + kss[k] + "MedianSkel.jpg");
                        ImageIO.write(bi, "JPG", out_skel);
                        resetImage(event);
                    }
                }
                //NIBLACK:
                 //9x9, 15x15,25x25
                for(int s = 0; s < sides.length; s++) {
                    double[] ks = new double[]{-0.5, -1.0, -1.5};
                    String[] kss = new String[]{"-05", "-10", "-15"};
                    //k=-0.5, -1.0, -1.5
                    for(int k = 0; k < ks.length; k++) {
                        bi = filters.medianFilter(bi, 3);
                        bi = binarization.niblackBinarize(bi, sides[s], ks[k]);
                        content = compare();
                        fw = new FileWriter(files[i] + "Niblack" + sides[s] + "K" + kss[k] + "Median.txt");
                        bw = new BufferedWriter(fw);
                        bw.write(content);
                        bw.close();
                        out = new File(files[i] + "Niblack" + sides[s] + "K" + kss[k] + "Median.jpg");
                        ImageIO.write(bi, "JPG", out);
                        bi = KMM.thin(bi);
                        out_skel = new File(files[i] + "Niblack" + sides[s] + "K" + kss[k] + "MedianSkel.jpg");
                        ImageIO.write(bi, "JPG", out_skel);
                        resetImage(event);
                    }
                }
                //SAUVOLA:
                //9x9, 15x15,25x25
                for(int s = 0; s < sides.length; s++) {
                    double[] ks = new double[]{0.3, 0.5, 0.7};
                    String[] kss = new String[]{"03", "05", "07"};
                    //k=0.3, 0.5, 0.7
                    for(int k = 0; k < ks.length; k++) {
                        bi = filters.medianFilter(bi, 3);
                        bi = binarization.sauvolaBinarize(bi, sides[s], ks[k], 128);
                        content = compare();
                        fw = new FileWriter(files[i] + "Sauvola" + sides[s] + "K" + kss[k] + "Median.txt");
                        bw = new BufferedWriter(fw);
                        bw.write(content);
                        bw.close();
                        out = new File(files[i] + "Sauvola" + sides[s] + "K" + kss[k] + "Median.jpg");
                        ImageIO.write(bi, "JPG", out);
                        bi = KMM.thin(bi);
                        out_skel = new File(files[i] + "Sauvola" + sides[s] + "K" + kss[k] + "MedianSkel.jpg");
                        ImageIO.write(bi, "JPG", out_skel);
                        resetImage(event);
                    }
                }
                //BRADLEY:
                //s=9, 15, 25
                int sides2[] = new int[]{9, 15, 25, 50, 100};
                for(int s = 0; s < sides2.length; s++) {
                    int[] ts = new int[]{10, 15, 20};
                    String[] tss = new String[]{"10", "15", "20"};
                    //t=10, 15, 20
                    for(int t = 0; t < ts.length; t++) {
                        bi = filters.medianFilter(bi, 3);
                        bi = binarization.bradleyBinarize(bi, sides2[s], ts[t]);
                        content = compare();
                        fw = new FileWriter(files[i] + "BradleyS" + sides2[s] + "T" + tss[t] + "Median.txt");
                        bw = new BufferedWriter(fw);
                        bw.write(content);
                        bw.close();
                        out = new File(files[i] + "BradleyS" + sides2[s] + "T" + tss[t] + "Median.jpg");
                        ImageIO.write(bi, "JPG", out);
                        bi = KMM.thin(bi);
                        out_skel = new File(files[i] + "BradleyS" + sides2[s] + "T" + tss[t] + "MedianSkel.jpg");
                        ImageIO.write(bi, "JPG", out_skel);
                        resetImage(event);
                    }
                }
            }
            
            
            
            
            //LOKALNE MEDIANAA 5X5:
            for(int i = 0; i < files.length; i++) {
                in = new File("D:\\Dokumenty\\Studia\\Zajęcia\\Wykłady\\Seminarium\\wybrane rysunki\\original\\" + files[i] + ".bmp");
                gt_file = new File("D:\\Dokumenty\\Studia\\Zajęcia\\Wykłady\\Seminarium\\wybrane rysunki\\gt\\" + files[i] + "_gt.bmp");
                bi = ImageIO.read(in);
                gt = ImageIO.read(gt_file);
                loadedbi = ImageIO.read(in);
                //BERNSEN:
                //9x9, 15x15,25x25
                for(int s = 0; s < sides.length; s++) {
                    int[] epsilons = new int[]{30, 50, 70};
                    //bez epsilonu
                    bi = filters.medianFilter(bi, 5);
                    bi = binarization.bernsenBinarize(bi, sides[s]);
                    content = compare();
                    fw = new FileWriter(files[i] + "Bernsen" + sides[s] + "Median5.txt");
                    bw = new BufferedWriter(fw);
                    bw.write(content);
                    bw.close();
                    out = new File(files[i] + "Bernsen" + sides[s] + "Median5.jpg");
                    ImageIO.write(bi, "JPG", out);
                    bi = KMM.thin(bi);
                    out_skel = new File(files[i] + "Bernsen" + sides[s] + "Median5Skel.jpg");
                    ImageIO.write(bi, "JPG", out_skel);
                    resetImage(event);
                    //epsilons 30, 50, 70:, thresh=127
                    for(int eps = 0; eps < epsilons.length; eps++) {
                        bi = filters.medianFilter(bi, 5);
                        bi = binarization.bernsenBinarize2(bi, sides[s], 127, epsilons[eps]);
                        content = compare();
                        fw = new FileWriter(files[i] + "Bernsen" + sides[s] + "Eps" + epsilons[eps] + "Median5.txt");
                        bw = new BufferedWriter(fw);
                        bw.write(content);
                        bw.close();
                        out = new File(files[i] + "Bernsen" + sides[s] + "Eps" + epsilons[eps] + "Median5.jpg");
                        ImageIO.write(bi, "JPG", out);
                        bi = KMM.thin(bi);
                        out_skel = new File(files[i] + "Bernsen" + sides[s] + "Eps" + epsilons[eps] + "Median5Skel.jpg");
                        ImageIO.write(bi, "JPG", out_skel);
                        resetImage(event);
                    }
                }
                //WHITE-ROHRER:
                //9x9, 15x15,25x25
                for(int s = 0; s < sides.length; s++) {
                    double[] ks = new double[]{1.2, 1.5, 2.0, 2.3};
                    String[] kss = new String[]{"12", "15", "20", "23"};
                    //k=1.2, 1.5, 2.0, 2.3
                    for(int k = 0; k < ks.length; k++) {
                        bi = filters.medianFilter(bi, 5);
                        bi = binarization.whiteRohrerBinarize(bi, sides[s], ks[k]);
                        content = compare();
                        fw = new FileWriter(files[i] + "WhiteRohrer" + sides[s] + "K" + kss[k] + "Median5.txt");
                        bw = new BufferedWriter(fw);
                        bw.write(content);
                        bw.close();
                        out = new File(files[i] + "WhiteRohrer" + sides[s] + "K" + kss[k] + "Median5.jpg");
                        ImageIO.write(bi, "JPG", out);
                        bi = KMM.thin(bi);
                        out_skel = new File(files[i] + "WhiteRohrer" + sides[s] + "K" + kss[k] + "Median5Skel.jpg");
                        ImageIO.write(bi, "JPG", out_skel);
                        resetImage(event);
                    }
                }
                //NIBLACK:
                 //9x9, 15x15,25x25
                for(int s = 0; s < sides.length; s++) {
                    double[] ks = new double[]{-0.5, -1.0, -1.5};
                    String[] kss = new String[]{"-05", "-10", "-15"};
                    //k=-0.5, -1.0, -1.5
                    for(int k = 0; k < ks.length; k++) {
                        bi = filters.medianFilter(bi, 5);
                        bi = binarization.niblackBinarize(bi, sides[s], ks[k]);
                        content = compare();
                        fw = new FileWriter(files[i] + "Niblack" + sides[s] + "K" + kss[k] + "Median5.txt");
                        bw = new BufferedWriter(fw);
                        bw.write(content);
                        bw.close();
                        out = new File(files[i] + "Niblack" + sides[s] + "K" + kss[k] + "Median5.jpg");
                        ImageIO.write(bi, "JPG", out);
                        bi = KMM.thin(bi);
                        out_skel = new File(files[i] + "Niblack" + sides[s] + "K" + kss[k] + "Median5Skel.jpg");
                        ImageIO.write(bi, "JPG", out_skel);
                        resetImage(event);
                    }
                }
                //SAUVOLA:
                //9x9, 15x15,25x25
                for(int s = 0; s < sides.length; s++) {
                    double[] ks = new double[]{0.3, 0.5, 0.7};
                    String[] kss = new String[]{"03", "05", "07"};
                    //k=0.3, 0.5, 0.7
                    for(int k = 0; k < ks.length; k++) {
                        bi = filters.medianFilter(bi, 5);
                        bi = binarization.sauvolaBinarize(bi, sides[s], ks[k], 128);
                        content = compare();
                        fw = new FileWriter(files[i] + "Sauvola" + sides[s] + "K" + kss[k] + "Median5.txt");
                        bw = new BufferedWriter(fw);
                        bw.write(content);
                        bw.close();
                        out = new File(files[i] + "Sauvola" + sides[s] + "K" + kss[k] + "Median5.jpg");
                        ImageIO.write(bi, "JPG", out);
                        bi = KMM.thin(bi);
                        out_skel = new File(files[i] + "Sauvola" + sides[s] + "K" + kss[k] + "Median5Skel.jpg");
                        ImageIO.write(bi, "JPG", out_skel);
                        resetImage(event);
                    }
                }
                //BRADLEY:
                //s=9, 15, 25
                int sides2[] = new int[]{9, 15, 25, 50, 100};
                for(int s = 0; s < sides2.length; s++) {
                    int[] ts = new int[]{10, 15, 20};
                    String[] tss = new String[]{"10", "15", "20"};
                    //t=10, 15, 20
                    for(int t = 0; t < ts.length; t++) {
                        bi = filters.medianFilter(bi, 5);
                        bi = binarization.bradleyBinarize(bi, sides2[s], ts[t]);
                        content = compare();
                        fw = new FileWriter(files[i] + "BradleyS" + sides2[s] + "T" + tss[t] + "Median5.txt");
                        bw = new BufferedWriter(fw);
                        bw.write(content);
                        bw.close();
                        out = new File(files[i] + "BradleyS" + sides2[s] + "T" + tss[t] + "Median5.jpg");
                        ImageIO.write(bi, "JPG", out);
                        bi = KMM.thin(bi);
                        out_skel = new File(files[i] + "BradleyS" + sides2[s] + "T" + tss[t] + "Median5Skel.jpg");
                        ImageIO.write(bi, "JPG", out_skel);
                        resetImage(event);
                    }
                }
            }
            
            
            //LOKALNE, ANIZOTROPIC DIFF 5 iteracji, threshold 15, sigma 0
            for(int i = 0; i < files.length; i++) {
                in = new File("D:\\Dokumenty\\Studia\\Zajęcia\\Wykłady\\Seminarium\\wybrane rysunki\\original\\" + files[i] + ".bmp");
                gt_file = new File("D:\\Dokumenty\\Studia\\Zajęcia\\Wykłady\\Seminarium\\wybrane rysunki\\gt\\" + files[i] + "_gt.bmp");
                bi = ImageIO.read(in);
                gt = ImageIO.read(gt_file);
                loadedbi = ImageIO.read(in);
                //BERNSEN:
                //9x9, 15x15,25x25
                for(int s = 0; s < sides.length; s++) {
                    int[] epsilons = new int[]{30, 50, 70};
                    //bez epsilonu
                    bi = adf.filter(bi, 5, 1, 15, 0.0);
                    bi = binarization.bernsenBinarize(bi, sides[s]);
                    content = compare();
                    fw = new FileWriter(files[i] + "Bernsen" + sides[s] + "Anizotropic5.txt");
                    bw = new BufferedWriter(fw);
                    bw.write(content);
                    bw.close();
                    out = new File(files[i] + "Bernsen" + sides[s] + "Anizotropic5.jpg");
                    ImageIO.write(bi, "JPG", out);
                    bi = KMM.thin(bi);
                    out_skel = new File(files[i] + "Bernsen" + sides[s] + "Anizotropic5Skel.jpg");
                    ImageIO.write(bi, "JPG", out_skel);
                    resetImage(event);
                    //epsilons 30, 50, 70:, thresh=127
                    for(int eps = 0; eps < epsilons.length; eps++) {
                        bi = adf.filter(bi, 5, 1, 15, 0.0);
                        bi = binarization.bernsenBinarize2(bi, sides[s], 127, epsilons[eps]);
                        content = compare();
                        fw = new FileWriter(files[i] + "Bernsen" + sides[s] + "Eps" + epsilons[eps] + "Anizotropic5.txt");
                        bw = new BufferedWriter(fw);
                        bw.write(content);
                        bw.close();
                        out = new File(files[i] + "Bernsen" + sides[s] + "Eps" + epsilons[eps] + "Anizotropic5.jpg");
                        ImageIO.write(bi, "JPG", out);
                        bi = KMM.thin(bi);
                        out_skel = new File(files[i] + "Bernsen" + sides[s] + "Eps" + epsilons[eps] + "Anizotropic5Skel.jpg");
                        ImageIO.write(bi, "JPG", out_skel);
                        resetImage(event);
                    }
                }
                //WHITE-ROHRER:
                //9x9, 15x15,25x25
                for(int s = 0; s < sides.length; s++) {
                    double[] ks = new double[]{1.2, 1.5, 2.0, 2.3};
                    String[] kss = new String[]{"12", "15", "20", "23"};
                    //k=1.2, 1.5, 2.0, 2.3
                    for(int k = 0; k < ks.length; k++) {
                        bi = adf.filter(bi, 5, 1, 15, 0.0);
                        bi = binarization.whiteRohrerBinarize(bi, sides[s], ks[k]);
                        content = compare();
                        fw = new FileWriter(files[i] + "WhiteRohrer" + sides[s] + "K" + kss[k] + "Anizotropic5.txt");
                        bw = new BufferedWriter(fw);
                        bw.write(content);
                        bw.close();
                        out = new File(files[i] + "WhiteRohrer" + sides[s] + "K" + kss[k] + "Anizotropic5.jpg");
                        ImageIO.write(bi, "JPG", out);
                        bi = KMM.thin(bi);
                        out_skel = new File(files[i] + "WhiteRohrer" + sides[s] + "K" + kss[k] + "Anizotropic5Skel.jpg");
                        ImageIO.write(bi, "JPG", out_skel);
                        resetImage(event);
                    }
                }
                //NIBLACK:
                 //9x9, 15x15,25x25
                for(int s = 0; s < sides.length; s++) {
                    double[] ks = new double[]{-0.5, -1.0, -1.5};
                    String[] kss = new String[]{"-05", "-10", "-15"};
                    //k=-0.5, -1.0, -1.5
                    for(int k = 0; k < ks.length; k++) {
                        bi = adf.filter(bi, 5, 1, 15, 0.0);
                        bi = binarization.niblackBinarize(bi, sides[s], ks[k]);
                        content = compare();
                        fw = new FileWriter(files[i] + "Niblack" + sides[s] + "K" + kss[k] + "Anizotropic5.txt");
                        bw = new BufferedWriter(fw);
                        bw.write(content);
                        bw.close();
                        out = new File(files[i] + "Niblack" + sides[s] + "K" + kss[k] + "Anizotropic5.jpg");
                        ImageIO.write(bi, "JPG", out);
                        bi = KMM.thin(bi);
                        out_skel = new File(files[i] + "Niblack" + sides[s] + "K" + kss[k] + "Anizotropic5Skel.jpg");
                        ImageIO.write(bi, "JPG", out_skel);
                        resetImage(event);
                    }
                }
                //SAUVOLA:
                //9x9, 15x15,25x25
                for(int s = 0; s < sides.length; s++) {
                    double[] ks = new double[]{0.3, 0.5, 0.7};
                    String[] kss = new String[]{"03", "05", "07"};
                    //k=0.3, 0.5, 0.7
                    for(int k = 0; k < ks.length; k++) {
                        bi = adf.filter(bi, 5, 1, 15, 0.0);
                        bi = binarization.sauvolaBinarize(bi, sides[s], ks[k], 128);
                        content = compare();
                        fw = new FileWriter(files[i] + "Sauvola" + sides[s] + "K" + kss[k] + "Anizotropic5.txt");
                        bw = new BufferedWriter(fw);
                        bw.write(content);
                        bw.close();
                        out = new File(files[i] + "Sauvola" + sides[s] + "K" + kss[k] + "Anizotropic5.jpg");
                        ImageIO.write(bi, "JPG", out);
                        bi = KMM.thin(bi);
                        out_skel = new File(files[i] + "Sauvola" + sides[s] + "K" + kss[k] + "Anizotropic5Skel.jpg");
                        ImageIO.write(bi, "JPG", out_skel);
                        resetImage(event);
                    }
                }
                //BRADLEY:
                //s=9, 15, 25
                int sides2[] = new int[]{9, 15, 25, 50, 100};
                for(int s = 0; s < sides2.length; s++) {
                    int[] ts = new int[]{10, 15, 20};
                    String[] tss = new String[]{"10", "15", "20"};
                    //t=10, 15, 20
                    for(int t = 0; t < ts.length; t++) {
                        bi = adf.filter(bi, 5, 1, 15, 0.0);
                        bi = binarization.bradleyBinarize(bi, sides2[s], ts[t]);
                        content = compare();
                        fw = new FileWriter(files[i] + "BradleyS" + sides2[s] + "T" + tss[t] + "Anizotropic5.txt");
                        bw = new BufferedWriter(fw);
                        bw.write(content);
                        bw.close();
                        out = new File(files[i] + "BradleyS" + sides2[s] + "T" + tss[t] + "Anizotropic5.jpg");
                        ImageIO.write(bi, "JPG", out);
                        bi = KMM.thin(bi);
                        out_skel = new File(files[i] + "BradleyS" + sides2[s] + "T" + tss[t] + "Anizotropic5Skel.jpg");
                        ImageIO.write(bi, "JPG", out_skel);
                        resetImage(event);
                    }
                }
            }
            
            
            //LOKALNE, ANIZOTROPIC DIFF 10 iteracji, threshold 15, sigma 0
            for(int i = 0; i < files.length; i++) {
                in = new File("D:\\Dokumenty\\Studia\\Zajęcia\\Wykłady\\Seminarium\\wybrane rysunki\\original\\" + files[i] + ".bmp");
                gt_file = new File("D:\\Dokumenty\\Studia\\Zajęcia\\Wykłady\\Seminarium\\wybrane rysunki\\gt\\" + files[i] + "_gt.bmp");
                bi = ImageIO.read(in);
                gt = ImageIO.read(gt_file);
                loadedbi = ImageIO.read(in);
                //BERNSEN:
                //9x9, 15x15,25x25
                for(int s = 0; s < sides.length; s++) {
                    int[] epsilons = new int[]{30, 50, 70};
                    //bez epsilonu
                    bi = adf.filter(bi, 10, 1, 15, 0.0);
                    bi = binarization.bernsenBinarize(bi, sides[s]);
                    content = compare();
                    fw = new FileWriter(files[i] + "Bernsen" + sides[s] + "Anizotropic10.txt");
                    bw = new BufferedWriter(fw);
                    bw.write(content);
                    bw.close();
                    out = new File(files[i] + "Bernsen" + sides[s] + "Anizotropic10.jpg");
                    ImageIO.write(bi, "JPG", out);
                    bi = KMM.thin(bi);
                    out_skel = new File(files[i] + "Bernsen" + sides[s] + "Anizotropic10Skel.jpg");
                    ImageIO.write(bi, "JPG", out_skel);
                    resetImage(event);
                    //epsilons 30, 50, 70:, thresh=127
                    for(int eps = 0; eps < epsilons.length; eps++) {
                        bi = adf.filter(bi, 10, 1, 15, 0.0);
                        bi = binarization.bernsenBinarize2(bi, sides[s], 127, epsilons[eps]);
                        content = compare();
                        fw = new FileWriter(files[i] + "Bernsen" + sides[s] + "Eps" + epsilons[eps] + "Anizotropic10.txt");
                        bw = new BufferedWriter(fw);
                        bw.write(content);
                        bw.close();
                        out = new File(files[i] + "Bernsen" + sides[s] + "Eps" + epsilons[eps] + "Anizotropic10.jpg");
                        ImageIO.write(bi, "JPG", out);
                        bi = KMM.thin(bi);
                        out_skel = new File(files[i] + "Bernsen" + sides[s] + "Eps" + epsilons[eps] + "Anizotropic10Skel.jpg");
                        ImageIO.write(bi, "JPG", out_skel);
                        resetImage(event);
                    }
                }
                //WHITE-ROHRER:
                //9x9, 15x15,25x25
                for(int s = 0; s < sides.length; s++) {
                    double[] ks = new double[]{1.2, 1.5, 2.0, 2.3};
                    String[] kss = new String[]{"12", "15", "20", "23"};
                    //k=1.2, 1.5, 2.0, 2.3
                    for(int k = 0; k < ks.length; k++) {
                        bi = adf.filter(bi, 10, 1, 15, 0.0);
                        bi = binarization.whiteRohrerBinarize(bi, sides[s], ks[k]);
                        content = compare();
                        fw = new FileWriter(files[i] + "WhiteRohrer" + sides[s] + "K" + kss[k] + "Anizotropic10.txt");
                        bw = new BufferedWriter(fw);
                        bw.write(content);
                        bw.close();
                        out = new File(files[i] + "WhiteRohrer" + sides[s] + "K" + kss[k] + "Anizotropic10.jpg");
                        ImageIO.write(bi, "JPG", out);
                        bi = KMM.thin(bi);
                        out_skel = new File(files[i] + "WhiteRohrer" + sides[s] + "K" + kss[k] + "Anizotropic10Skel.jpg");
                        ImageIO.write(bi, "JPG", out_skel);
                        resetImage(event);
                    }
                }
                //NIBLACK:
                 //9x9, 15x15,25x25
                for(int s = 0; s < sides.length; s++) {
                    double[] ks = new double[]{-0.5, -1.0, -1.5};
                    String[] kss = new String[]{"-05", "-10", "-15"};
                    //k=-0.5, -1.0, -1.5
                    for(int k = 0; k < ks.length; k++) {
                        bi = adf.filter(bi, 10, 1, 15, 0.0);
                        bi = binarization.niblackBinarize(bi, sides[s], ks[k]);
                        content = compare();
                        fw = new FileWriter(files[i] + "Niblack" + sides[s] + "K" + kss[k] + "Anizotropic10.txt");
                        bw = new BufferedWriter(fw);
                        bw.write(content);
                        bw.close();
                        out = new File(files[i] + "Niblack" + sides[s] + "K" + kss[k] + "Anizotropic10.jpg");
                        ImageIO.write(bi, "JPG", out);
                        bi = KMM.thin(bi);
                        out_skel = new File(files[i] + "Niblack" + sides[s] + "K" + kss[k] + "Anizotropic10Skel.jpg");
                        ImageIO.write(bi, "JPG", out_skel);
                        resetImage(event);
                    }
                }
                //SAUVOLA:
                //9x9, 15x15,25x25
                for(int s = 0; s < sides.length; s++) {
                    double[] ks = new double[]{0.3, 0.5, 0.7};
                    String[] kss = new String[]{"03", "05", "07"};
                    //k=0.3, 0.5, 0.7
                    for(int k = 0; k < ks.length; k++) {
                        bi = adf.filter(bi, 10, 1, 15, 0.0);
                        bi = binarization.sauvolaBinarize(bi, sides[s], ks[k], 128);
                        content = compare();
                        fw = new FileWriter(files[i] + "Sauvola" + sides[s] + "K" + kss[k] + "Anizotropic10.txt");
                        bw = new BufferedWriter(fw);
                        bw.write(content);
                        bw.close();
                        out = new File(files[i] + "Sauvola" + sides[s] + "K" + kss[k] + "Anizotropic10.jpg");
                        ImageIO.write(bi, "JPG", out);
                        bi = KMM.thin(bi);
                        out_skel = new File(files[i] + "Sauvola" + sides[s] + "K" + kss[k] + "Anizotropic10Skel.jpg");
                        ImageIO.write(bi, "JPG", out_skel);
                        resetImage(event);
                    }
                }
                //BRADLEY:
                //s=9, 15, 25
                int sides2[] = new int[]{9, 15, 25, 50, 100};
                for(int s = 0; s < sides2.length; s++) {
                    int[] ts = new int[]{10, 15, 20};
                    String[] tss = new String[]{"10", "15", "20"};
                    //t=10, 15, 20
                    for(int t = 0; t < ts.length; t++) {
                        bi = adf.filter(bi, 10, 1, 15, 0.0);
                        bi = binarization.bradleyBinarize(bi, sides2[s], ts[t]);
                        content = compare();
                        fw = new FileWriter(files[i] + "BradleyS" + sides2[s] + "T" + tss[t] + "Anizotropic10.txt");
                        bw = new BufferedWriter(fw);
                        bw.write(content);
                        bw.close();
                        out = new File(files[i] + "BradleyS" + sides2[s] + "T" + tss[t] + "Anizotropic10.jpg");
                        ImageIO.write(bi, "JPG", out);
                        bi = KMM.thin(bi);
                        out_skel = new File(files[i] + "BradleyS" + sides2[s] + "T" + tss[t] + "Anizotropic10Skel.jpg");
                        ImageIO.write(bi, "JPG", out_skel);
                        resetImage(event);
                    }
                }
            }
            
            
            //LOKALNE KUWAHARA 1x 3X3:
            for(int i = 0; i < files.length; i++) {
                in = new File("D:\\Dokumenty\\Studia\\Zajęcia\\Wykłady\\Seminarium\\wybrane rysunki\\original\\" + files[i] + ".bmp");
                gt_file = new File("D:\\Dokumenty\\Studia\\Zajęcia\\Wykłady\\Seminarium\\wybrane rysunki\\gt\\" + files[i] + "_gt.bmp");
                bi = ImageIO.read(in);
                gt = ImageIO.read(gt_file);
                loadedbi = ImageIO.read(in);
                //BERNSEN:
                //9x9, 15x15,25x25
                for(int s = 0; s < sides.length; s++) {
                    int[] epsilons = new int[]{30, 50, 70};
                    //bez epsilonu
                    bi = filters.kuwaharaFilter(bi, 3);
                    bi = binarization.bernsenBinarize(bi, sides[s]);
                    content = compare();
                    fw = new FileWriter(files[i] + "Bernsen" + sides[s] + "Kuwahara.txt");
                    bw = new BufferedWriter(fw);
                    bw.write(content);
                    bw.close();
                    out = new File(files[i] + "Bernsen" + sides[s] + "Kuwahara.jpg");
                    ImageIO.write(bi, "JPG", out);
                    bi = KMM.thin(bi);
                    out_skel = new File(files[i] + "Bernsen" + sides[s] + "KuwaharaSkel.jpg");
                    ImageIO.write(bi, "JPG", out_skel);
                    resetImage(event);
                    //epsilons 30, 50, 70:, thresh=127
                    for(int eps = 0; eps < epsilons.length; eps++) {
                        bi = filters.kuwaharaFilter(bi, 3);
                        bi = binarization.bernsenBinarize2(bi, sides[s], 127, epsilons[eps]);
                        content = compare();
                        fw = new FileWriter(files[i] + "Bernsen" + sides[s] + "Eps" + epsilons[eps] + "Kuwahara.txt");
                        bw = new BufferedWriter(fw);
                        bw.write(content);
                        bw.close();
                        out = new File(files[i] + "Bernsen" + sides[s] + "Eps" + epsilons[eps] + "Kuwahara.jpg");
                        ImageIO.write(bi, "JPG", out);
                        bi = KMM.thin(bi);
                        out_skel = new File(files[i] + "Bernsen" + sides[s] + "Eps" + epsilons[eps] + "KuwaharaSkel.jpg");
                        ImageIO.write(bi, "JPG", out_skel);
                        resetImage(event);
                    }
                }
                //WHITE-ROHRER:
                //9x9, 15x15,25x25
                for(int s = 0; s < sides.length; s++) {
                    double[] ks = new double[]{1.2, 1.5, 2.0, 2.3};
                    String[] kss = new String[]{"12", "15", "20", "23"};
                    //k=1.2, 1.5, 2.0, 2.3
                    for(int k = 0; k < ks.length; k++) {
                        bi = filters.kuwaharaFilter(bi, 3);
                        bi = binarization.whiteRohrerBinarize(bi, sides[s], ks[k]);
                        content = compare();
                        fw = new FileWriter(files[i] + "WhiteRohrer" + sides[s] + "K" + kss[k] + "Kuwahara.txt");
                        bw = new BufferedWriter(fw);
                        bw.write(content);
                        bw.close();
                        out = new File(files[i] + "WhiteRohrer" + sides[s] + "K" + kss[k] + "Kuwahara.jpg");
                        ImageIO.write(bi, "JPG", out);
                        bi = KMM.thin(bi);
                        out_skel = new File(files[i] + "WhiteRohrer" + sides[s] + "K" + kss[k] + "KuwaharaSkel.jpg");
                        ImageIO.write(bi, "JPG", out_skel);
                        resetImage(event);
                    }
                }
                //NIBLACK:
                 //9x9, 15x15,25x25
                for(int s = 0; s < sides.length; s++) {
                    double[] ks = new double[]{-0.5, -1.0, -1.5};
                    String[] kss = new String[]{"-05", "-10", "-15"};
                    //k=-0.5, -1.0, -1.5
                    for(int k = 0; k < ks.length; k++) {
                        bi = filters.kuwaharaFilter(bi, 3);
                        bi = binarization.niblackBinarize(bi, sides[s], ks[k]);
                        content = compare();
                        fw = new FileWriter(files[i] + "Niblack" + sides[s] + "K" + kss[k] + "Kuwahara.txt");
                        bw = new BufferedWriter(fw);
                        bw.write(content);
                        bw.close();
                        out = new File(files[i] + "Niblack" + sides[s] + "K" + kss[k] + "Kuwahara.jpg");
                        ImageIO.write(bi, "JPG", out);
                        bi = KMM.thin(bi);
                        out_skel = new File(files[i] + "Niblack" + sides[s] + "K" + kss[k] + "KuwaharaSkel.jpg");
                        ImageIO.write(bi, "JPG", out_skel);
                        resetImage(event);
                    }
                }
                //SAUVOLA:
                //9x9, 15x15,25x25
                for(int s = 0; s < sides.length; s++) {
                    double[] ks = new double[]{0.3, 0.5, 0.7};
                    String[] kss = new String[]{"03", "05", "07"};
                    //k=0.3, 0.5, 0.7
                    for(int k = 0; k < ks.length; k++) {
                        bi = filters.kuwaharaFilter(bi, 3);
                        bi = binarization.sauvolaBinarize(bi, sides[s], ks[k], 128);
                        content = compare();
                        fw = new FileWriter(files[i] + "Sauvola" + sides[s] + "K" + kss[k] + "Kuwahara.txt");
                        bw = new BufferedWriter(fw);
                        bw.write(content);
                        bw.close();
                        out = new File(files[i] + "Sauvola" + sides[s] + "K" + kss[k] + "Kuwahara.jpg");
                        ImageIO.write(bi, "JPG", out);
                        bi = KMM.thin(bi);
                        out_skel = new File(files[i] + "Sauvola" + sides[s] + "K" + kss[k] + "KuwaharaSkel.jpg");
                        ImageIO.write(bi, "JPG", out_skel);
                        resetImage(event);
                    }
                }
                //BRADLEY:
                //s=9, 15, 25
                int sides2[] = new int[]{9, 15, 25, 50, 100};
                for(int s = 0; s < sides2.length; s++) {
                    int[] ts = new int[]{10, 15, 20};
                    String[] tss = new String[]{"10", "15", "20"};
                    //t=10, 15, 20
                    for(int t = 0; t < ts.length; t++) {
                        bi = filters.kuwaharaFilter(bi, 3);
                        bi = binarization.bradleyBinarize(bi, sides2[s], ts[t]);
                        content = compare();
                        fw = new FileWriter(files[i] + "BradleyS" + sides2[s] + "T" + tss[t] + "Kuwahara.txt");
                        bw = new BufferedWriter(fw);
                        bw.write(content);
                        bw.close();
                        out = new File(files[i] + "BradleyS" + sides2[s] + "T" + tss[t] + "Kuwahara.jpg");
                        ImageIO.write(bi, "JPG", out);
                        bi = KMM.thin(bi);
                        out_skel = new File(files[i] + "BradleyS" + sides2[s] + "T" + tss[t] + "KuwaharaSkel.jpg");
                        ImageIO.write(bi, "JPG", out_skel);
                        resetImage(event);
                    }
                }
            }
            //LOKALNE KUWAHARA 1x 5X5:
            for(int i = 0; i < files.length; i++) {
                in = new File("D:\\Dokumenty\\Studia\\Zajęcia\\Wykłady\\Seminarium\\wybrane rysunki\\original\\" + files[i] + ".bmp");
                gt_file = new File("D:\\Dokumenty\\Studia\\Zajęcia\\Wykłady\\Seminarium\\wybrane rysunki\\gt\\" + files[i] + "_gt.bmp");
                bi = ImageIO.read(in);
                gt = ImageIO.read(gt_file);
                loadedbi = ImageIO.read(in);
                //BERNSEN:
                //9x9, 15x15,25x25
                for(int s = 0; s < sides.length; s++) {
                    int[] epsilons = new int[]{30, 50, 70};
                    //bez epsilonu
                    bi = filters.kuwaharaFilter(bi, 5);
                    bi = binarization.bernsenBinarize(bi, sides[s]);
                    content = compare();
                    fw = new FileWriter(files[i] + "Bernsen" + sides[s] + "Kuwahara5.txt");
                    bw = new BufferedWriter(fw);
                    bw.write(content);
                    bw.close();
                    out = new File(files[i] + "Bernsen" + sides[s] + "Kuwahara5.jpg");
                    ImageIO.write(bi, "JPG", out);
                    bi = KMM.thin(bi);
                    out_skel = new File(files[i] + "Bernsen" + sides[s] + "Kuwahara5Skel.jpg");
                    ImageIO.write(bi, "JPG", out_skel);
                    resetImage(event);
                    //epsilons 30, 50, 70:, thresh=127
                    for(int eps = 0; eps < epsilons.length; eps++) {
                        bi = filters.kuwaharaFilter(bi, 5);
                        bi = binarization.bernsenBinarize2(bi, sides[s], 127, epsilons[eps]);
                        content = compare();
                        fw = new FileWriter(files[i] + "Bernsen" + sides[s] + "Eps" + epsilons[eps] + "Kuwahara5.txt");
                        bw = new BufferedWriter(fw);
                        bw.write(content);
                        bw.close();
                        out = new File(files[i] + "Bernsen" + sides[s] + "Eps" + epsilons[eps] + "Kuwahara5.jpg");
                        ImageIO.write(bi, "JPG", out);
                        bi = KMM.thin(bi);
                        out_skel = new File(files[i] + "Bernsen" + sides[s] + "Eps" + epsilons[eps] + "Kuwahara5Skel.jpg");
                        ImageIO.write(bi, "JPG", out_skel);
                        resetImage(event);
                    }
                }
                //WHITE-ROHRER:
                //9x9, 15x15,25x25
                for(int s = 0; s < sides.length; s++) {
                    double[] ks = new double[]{1.2, 1.5, 2.0, 2.3};
                    String[] kss = new String[]{"12", "15", "20", "23"};
                    //k=1.2, 1.5, 2.0, 2.3
                    for(int k = 0; k < ks.length; k++) {
                        bi = filters.kuwaharaFilter(bi, 5);
                        bi = binarization.whiteRohrerBinarize(bi, sides[s], ks[k]);
                        content = compare();
                        fw = new FileWriter(files[i] + "WhiteRohrer" + sides[s] + "K" + kss[k] + "Kuwahara5.txt");
                        bw = new BufferedWriter(fw);
                        bw.write(content);
                        bw.close();
                        out = new File(files[i] + "WhiteRohrer" + sides[s] + "K" + kss[k] + "Kuwahara5.jpg");
                        ImageIO.write(bi, "JPG", out);
                        bi = KMM.thin(bi);
                        out_skel = new File(files[i] + "WhiteRohrer" + sides[s] + "K" + kss[k] + "Kuwahara5Skel.jpg");
                        ImageIO.write(bi, "JPG", out_skel);
                        resetImage(event);
                    }
                }
                //NIBLACK:
                 //9x9, 15x15,25x25
                for(int s = 0; s < sides.length; s++) {
                    double[] ks = new double[]{-0.5, -1.0, -1.5};
                    String[] kss = new String[]{"-05", "-10", "-15"};
                    //k=-0.5, -1.0, -1.5
                    for(int k = 0; k < ks.length; k++) {
                        bi = filters.kuwaharaFilter(bi, 5);
                        bi = binarization.niblackBinarize(bi, sides[s], ks[k]);
                        content = compare();
                        fw = new FileWriter(files[i] + "Niblack" + sides[s] + "K" + kss[k] + "Kuwahara5.txt");
                        bw = new BufferedWriter(fw);
                        bw.write(content);
                        bw.close();
                        out = new File(files[i] + "Niblack" + sides[s] + "K" + kss[k] + "Kuwahara5.jpg");
                        ImageIO.write(bi, "JPG", out);
                        bi = KMM.thin(bi);
                        out_skel = new File(files[i] + "Niblack" + sides[s] + "K" + kss[k] + "Kuwahara5Skel.jpg");
                        ImageIO.write(bi, "JPG", out_skel);
                        resetImage(event);
                    }
                }
                //SAUVOLA:
                //9x9, 15x15,25x25
                for(int s = 0; s < sides.length; s++) {
                    double[] ks = new double[]{0.3, 0.5, 0.7};
                    String[] kss = new String[]{"03", "05", "07"};
                    //k=0.3, 0.5, 0.7
                    for(int k = 0; k < ks.length; k++) {
                        bi = filters.kuwaharaFilter(bi, 5);
                        bi = binarization.sauvolaBinarize(bi, sides[s], ks[k], 128);
                        content = compare();
                        fw = new FileWriter(files[i] + "Sauvola" + sides[s] + "K" + kss[k] + "Kuwahara5.txt");
                        bw = new BufferedWriter(fw);
                        bw.write(content);
                        bw.close();
                        out = new File(files[i] + "Sauvola" + sides[s] + "K" + kss[k] + "Kuwahara5.jpg");
                        ImageIO.write(bi, "JPG", out);
                        bi = KMM.thin(bi);
                        out_skel = new File(files[i] + "Sauvola" + sides[s] + "K" + kss[k] + "Kuwahara5Skel.jpg");
                        ImageIO.write(bi, "JPG", out_skel);
                        resetImage(event);
                    }
                }
                //BRADLEY:
                //s=9, 15, 25
                int sides2[] = new int[]{9, 15, 25, 50, 100};
                for(int s = 0; s < sides2.length; s++) {
                    int[] ts = new int[]{10, 15, 20};
                    String[] tss = new String[]{"10", "15", "20"};
                    //t=10, 15, 20
                    for(int t = 0; t < ts.length; t++) {
                        bi = filters.kuwaharaFilter(bi, 5);
                        bi = binarization.bradleyBinarize(bi, sides2[s], ts[t]);
                        content = compare();
                        fw = new FileWriter(files[i] + "BradleyS" + sides2[s] + "T" + tss[t] + "Kuwahara5.txt");
                        bw = new BufferedWriter(fw);
                        bw.write(content);
                        bw.close();
                        out = new File(files[i] + "BradleyS" + sides2[s] + "T" + tss[t] + "Kuwahara5.jpg");
                        ImageIO.write(bi, "JPG", out);
                        bi = KMM.thin(bi);
                        out_skel = new File(files[i] + "BradleyS" + sides2[s] + "T" + tss[t] + "Kuwahara5Skel.jpg");
                        ImageIO.write(bi, "JPG", out_skel);
                        resetImage(event);
                    }
                }
            }
            
            
        } catch (Exception ex) {
            ex.printStackTrace();
            Logger.getLogger(FXMLDocumentController.class.getName()).log(Level.SEVERE, null, ex);
        } finally {
            try {
                if (bw != null)
                    bw.close();
                if (fw != null)
                    fw.close();
            } catch (IOException ex) {
                ex.printStackTrace();
            }
        }
    }

    @FXML
    private void automaticComparing2(ActionEvent event) {
        BufferedWriter bw = null;
        FileWriter fw = null;
        String[] files = new String[]{"a", "b", "c", "d", "e", "f", "g", "h", "i"};
        float[][] gausses = new float[3][];
        gausses[0] = Filters.gauss1;
        gausses[1] = Filters.gauss2;
        gausses[2] = Filters.gauss3;
        int sides[] = new int[]{9, 15, 25};
        try {
            gs = new GrayScale();
            binarization = new Binarization();
            filters = new Filters();
            AnisotropicDiffusionFilter adf = new AnisotropicDiffusionFilter();
            
            File in, gt_file;
            String content;
            double result;
            
            
//            //GLOBALNE BEZ FILTRÓW:
//            for(int i = 0; i < files.length; i++) {
//                in = new File("D:\\Dokumenty\\Studia\\Zajęcia\\Wykłady\\Seminarium\\wybrane rysunki\\original\\" + files[i] + ".bmp");
//                gt_file = new File("D:\\Dokumenty\\Studia\\Zajęcia\\Wykłady\\Seminarium\\wybrane rysunki\\gt\\" + files[i] + "_gt.bmp");
//                bi = ImageIO.read(in);
//                gt = ImageIO.read(gt_file);
//                loadedbi = ImageIO.read(in);
//                //OTSU:
//                bi = binarization.otsuBinarize(bi);
//                result = compare2();
//                content = Double.toString(result);
//                fw = new FileWriter(files[i] + "OtsuNoFilter.txt");
//                bw = new BufferedWriter(fw);
//                bw.write(content);
//                bw.close();
//                resetImage(event);
//                //RIDLER-CALVARD:
//                bi = binarization.ridlerCalvardBinarize(bi);
//                result = compare2();
//                content = Double.toString(result);
//                fw = new FileWriter(files[i] + "RidlerNoFilter.txt");
//                bw = new BufferedWriter(fw);
//                bw.write(content);
//                bw.close();
//                resetImage(event);
//                //PUN:
//                bi = binarization.punBinarize(bi);
//                result = compare2();
//                content = Double.toString(result);
//                fw = new FileWriter(files[i] + "PunNoFilter.txt");
//                bw = new BufferedWriter(fw);
//                bw.write(content);
//                bw.close();
//                resetImage(event);
//                //KAPUR-SAHOO-WONG:
//                bi = binarization.kswBinarize(bi);
//                result = compare2();
//                content = Double.toString(result);
//                fw = new FileWriter(files[i] + "KapurNoFilter.txt");
//                bw = new BufferedWriter(fw);
//                bw.write(content);
//                bw.close();
//                resetImage(event);
//            }
//            
//            
//            //GLOBALNE gauss1, gauss2, gauss3:
//            for(int i = 0; i < files.length; i++) {
//                in = new File("D:\\Dokumenty\\Studia\\Zajęcia\\Wykłady\\Seminarium\\wybrane rysunki\\original\\" + files[i] + ".bmp");
//                gt_file = new File("D:\\Dokumenty\\Studia\\Zajęcia\\Wykłady\\Seminarium\\wybrane rysunki\\gt\\" + files[i] + "_gt.bmp");
//                bi = ImageIO.read(in);
//                gt = ImageIO.read(gt_file);
//                loadedbi = ImageIO.read(in);
//                for(int g = 0; g < gausses.length; g++) {
//                    //OTSU:
//                    int gg = g+1;
//                    bi = filters.filter(bi, gausses[g]);
//                    bi = binarization.otsuBinarize(bi);
//                    result = compare2();
//                    content = Double.toString(result);
//                    fw = new FileWriter(files[i] + "OtsuGauss" + gg + ".txt");
//                    bw = new BufferedWriter(fw);
//                    bw.write(content);
//                    bw.close();
//                    resetImage(event);
//                    //RIDLER-CALVARD:
//                    bi = filters.filter(bi, gausses[g]);
//                    bi = binarization.ridlerCalvardBinarize(bi);
//                    result = compare2();
//                    content = Double.toString(result);
//                    fw = new FileWriter(files[i] + "RidlerGauss" + gg + ".txt");
//                    bw = new BufferedWriter(fw);
//                    bw.write(content);
//                    bw.close();
//                    resetImage(event);
//                    //PUN:
//                    bi = filters.filter(bi, gausses[g]);
//                    bi = binarization.punBinarize(bi);
//                    result = compare2();
//                    content = Double.toString(result);
//                    fw = new FileWriter(files[i] + "PunGauss" + gg + ".txt");
//                    bw = new BufferedWriter(fw);
//                    bw.write(content);
//                    bw.close();
//                    resetImage(event);
//                    //KAPUR-SAHOO-WONG:
//                    bi = filters.filter(bi, gausses[g]);
//                    bi = binarization.kswBinarize(bi);
//                    result = compare2();
//                    content = Double.toString(result);
//                    fw = new FileWriter(files[i] + "KapurGauss" + gg + ".txt");
//                    bw = new BufferedWriter(fw);
//                    bw.write(content);
//                    bw.close();
//                    resetImage(event);
//                }
//            }
//            
//            
//            //GLOBALNE, MEDIANA x1 3x3
//            for(int i = 0; i < files.length; i++) {
//                in = new File("D:\\Dokumenty\\Studia\\Zajęcia\\Wykłady\\Seminarium\\wybrane rysunki\\original\\" + files[i] + ".bmp");
//                gt_file = new File("D:\\Dokumenty\\Studia\\Zajęcia\\Wykłady\\Seminarium\\wybrane rysunki\\gt\\" + files[i] + "_gt.bmp");
//                bi = ImageIO.read(in);
//                gt = ImageIO.read(gt_file);
//                loadedbi = ImageIO.read(in);
//                //OTSU:
//                bi = filters.medianFilter(bi, 3);
//                bi = binarization.otsuBinarize(bi);
//                result = compare2();
//                content = Double.toString(result);
//                fw = new FileWriter(files[i] + "OtsuMedian.txt");
//                bw = new BufferedWriter(fw);
//                bw.write(content);
//                bw.close();
//                resetImage(event);
//                //RIDLER-CALVARD:
//                bi = filters.medianFilter(bi, 3);
//                bi = binarization.ridlerCalvardBinarize(bi);
//                result = compare2();
//                content = Double.toString(result);
//                fw = new FileWriter(files[i] + "RidlerMedian.txt");
//                bw = new BufferedWriter(fw);
//                bw.write(content);
//                bw.close();
//                resetImage(event);
//                //PUN:
//                bi = filters.medianFilter(bi, 3);
//                bi = binarization.punBinarize(bi);
//                result = compare2();
//                content = Double.toString(result);
//                fw = new FileWriter(files[i] + "PunMedian.txt");
//                bw = new BufferedWriter(fw);
//                bw.write(content);
//                bw.close();
//                resetImage(event);
//                //KAPUR-SAHOO-WONG:
//                bi = filters.medianFilter(bi, 3);
//                bi = binarization.kswBinarize(bi);
//                result = compare2();
//                content = Double.toString(result);
//                fw = new FileWriter(files[i] + "KapurMedian.txt");
//                bw = new BufferedWriter(fw);
//                bw.write(content);
//                bw.close();
//                resetImage(event);
//            }
//            
//            //GLOBALNE, MEDIANA x1 5x5
//            for(int i = 0; i < files.length; i++) {
//                in = new File("D:\\Dokumenty\\Studia\\Zajęcia\\Wykłady\\Seminarium\\wybrane rysunki\\original\\" + files[i] + ".bmp");
//                gt_file = new File("D:\\Dokumenty\\Studia\\Zajęcia\\Wykłady\\Seminarium\\wybrane rysunki\\gt\\" + files[i] + "_gt.bmp");
//                bi = ImageIO.read(in);
//                gt = ImageIO.read(gt_file);
//                loadedbi = ImageIO.read(in);
//                //OTSU:
//                bi = filters.medianFilter(bi, 5);
//                bi = binarization.otsuBinarize(bi);
//                result = compare2();
//                content = Double.toString(result);
//                fw = new FileWriter(files[i] + "OtsuMedian5.txt");
//                bw = new BufferedWriter(fw);
//                bw.write(content);
//                bw.close();
//                resetImage(event);
//                //RIDLER-CALVARD:
//                bi = filters.medianFilter(bi, 5);
//                bi = binarization.ridlerCalvardBinarize(bi);
//                result = compare2();
//                content = Double.toString(result);
//                fw = new FileWriter(files[i] + "RidlerMedian5.txt");
//                bw = new BufferedWriter(fw);
//                bw.write(content);
//                bw.close();
//                resetImage(event);
//                //PUN:
//                bi = filters.medianFilter(bi, 5);
//                bi = binarization.punBinarize(bi);
//                result = compare2();
//                content = Double.toString(result);
//                fw = new FileWriter(files[i] + "PunMedian5.txt");
//                bw = new BufferedWriter(fw);
//                bw.write(content);
//                bw.close();
//                resetImage(event);
//                //KAPUR-SAHOO-WONG:
//                bi = filters.medianFilter(bi, 5);
//                bi = binarization.kswBinarize(bi);
//                result = compare2();
//                content = Double.toString(result);
//                fw = new FileWriter(files[i] + "KapurMedian5.txt");
//                bw = new BufferedWriter(fw);
//                bw.write(content);
//                bw.close();
//                resetImage(event);
//            }
//            
//            
//            //GLOBALNE, ANIZOTROPIC DIFF 5 iteracji, threshold 15, sigma 0
//            for(int i = 0; i < files.length; i++) {
//                in = new File("D:\\Dokumenty\\Studia\\Zajęcia\\Wykłady\\Seminarium\\wybrane rysunki\\original\\" + files[i] + ".bmp");
//                gt_file = new File("D:\\Dokumenty\\Studia\\Zajęcia\\Wykłady\\Seminarium\\wybrane rysunki\\gt\\" + files[i] + "_gt.bmp");
//                bi = ImageIO.read(in);
//                gt = ImageIO.read(gt_file);
//                loadedbi = ImageIO.read(in);
//                //OTSU:
//                bi = adf.filter(bi, 5, 1, 15, 0.0);
//                bi = binarization.otsuBinarize(bi);
//                result = compare2();
//                content = Double.toString(result);
//                fw = new FileWriter(files[i] + "OtsuAnizotropic5.txt");
//                bw = new BufferedWriter(fw);
//                bw.write(content);
//                bw.close();
//                resetImage(event);
//                //RIDLER-CALVARD:
//                bi = adf.filter(bi, 5, 1, 15, 0.0);
//                bi = binarization.ridlerCalvardBinarize(bi);
//                result = compare2();
//                content = Double.toString(result);
//                fw = new FileWriter(files[i] + "RidlerAnizotropic5.txt");
//                bw = new BufferedWriter(fw);
//                bw.write(content);
//                bw.close();
//                resetImage(event);
//                //PUN:
//                bi = adf.filter(bi, 5, 1, 15, 0.0);
//                bi = binarization.punBinarize(bi);
//                result = compare2();
//                content = Double.toString(result);
//                fw = new FileWriter(files[i] + "PunAnizotropic5.txt");
//                bw = new BufferedWriter(fw);
//                bw.write(content);
//                bw.close();
//                resetImage(event);
//                //KAPUR-SAHOO-WONG:
//                bi = adf.filter(bi, 5, 1, 15, 0.0);
//                bi = binarization.kswBinarize(bi);
//                result = compare2();
//                content = Double.toString(result);
//                fw = new FileWriter(files[i] + "KapurAnizotropic5.txt");
//                bw = new BufferedWriter(fw);
//                bw.write(content);
//                bw.close();
//                resetImage(event);
//            }
//            
//            
//            //GLOBALNE, ANIZOTROPIC DIFF 10 iteracji, threshold 15, sigma 0
//            for(int i = 0; i < files.length; i++) {
//                in = new File("D:\\Dokumenty\\Studia\\Zajęcia\\Wykłady\\Seminarium\\wybrane rysunki\\original\\" + files[i] + ".bmp");
//                gt_file = new File("D:\\Dokumenty\\Studia\\Zajęcia\\Wykłady\\Seminarium\\wybrane rysunki\\gt\\" + files[i] + "_gt.bmp");
//                bi = ImageIO.read(in);
//                gt = ImageIO.read(gt_file);
//                loadedbi = ImageIO.read(in);
//                //OTSU:
//                bi = adf.filter(bi, 10, 1, 15, 0.0);
//                bi = binarization.otsuBinarize(bi);
//                result = compare2();
//                content = Double.toString(result);
//                fw = new FileWriter(files[i] + "OtsuAnizotropic10.txt");
//                bw = new BufferedWriter(fw);
//                bw.write(content);
//                bw.close();
//                resetImage(event);
//                //RIDLER-CALVARD:
//                bi = adf.filter(bi, 10, 1, 15, 0.0);
//                bi = binarization.ridlerCalvardBinarize(bi);
//                result = compare2();
//                content = Double.toString(result);
//                fw = new FileWriter(files[i] + "RidlerAnizotropic10.txt");
//                bw = new BufferedWriter(fw);
//                bw.write(content);
//                bw.close();
//                resetImage(event);
//                //PUN:
//                bi = adf.filter(bi, 10, 1, 15, 0.0);
//                bi = binarization.punBinarize(bi);
//                result = compare2();
//                content = Double.toString(result);
//                fw = new FileWriter(files[i] + "PunAnizotropic10.txt");
//                bw = new BufferedWriter(fw);
//                bw.write(content);
//                bw.close();
//                resetImage(event);
//                //KAPUR-SAHOO-WONG:
//                bi = adf.filter(bi, 10, 1, 15, 0.0);
//                bi = binarization.kswBinarize(bi);
//                result = compare2();
//                content = Double.toString(result);
//                fw = new FileWriter(files[i] + "KapurAnizotropic10.txt");
//                bw = new BufferedWriter(fw);
//                bw.write(content);
//                bw.close();
//                resetImage(event);
//            }
//            
//            
            //GLOBALNE, KUWAHAR x1 3x3
            for(int i = 0; i < files.length; i++) {
                in = new File("D:\\Dokumenty\\Studia\\Zajęcia\\Wykłady\\Seminarium\\wybrane rysunki\\original\\" + files[i] + ".bmp");
                gt_file = new File("D:\\Dokumenty\\Studia\\Zajęcia\\Wykłady\\Seminarium\\wybrane rysunki\\gt\\" + files[i] + "_gt.bmp");
                bi = ImageIO.read(in);
                gt = ImageIO.read(gt_file);
                loadedbi = ImageIO.read(in);
                //OTSU:
                bi = filters.kuwaharaFilter(bi, 3);
                bi = binarization.otsuBinarize(bi);
                result = compare2();
                content = Double.toString(result);
                fw = new FileWriter(files[i] + "OtsuKuwahara.txt");
                bw = new BufferedWriter(fw);
                bw.write(content);
                bw.close();
                resetImage(event);
                //RIDLER-CALVARD:
                bi = filters.kuwaharaFilter(bi, 3);
                bi = binarization.ridlerCalvardBinarize(bi);
                result = compare2();
                content = Double.toString(result);
                fw = new FileWriter(files[i] + "RidlerKuwahara.txt");
                bw = new BufferedWriter(fw);
                bw.write(content);
                bw.close();
                resetImage(event);
                //PUN:
                bi = filters.kuwaharaFilter(bi, 3);
                bi = binarization.punBinarize(bi);
                result = compare2();
                content = Double.toString(result);
                fw = new FileWriter(files[i] + "PunKuwahara.txt");
                bw = new BufferedWriter(fw);
                bw.write(content);
                bw.close();
                resetImage(event);
                //KAPUR-SAHOO-WONG:
                bi = filters.kuwaharaFilter(bi, 3);
                bi = binarization.kswBinarize(bi);
                result = compare2();
                content = Double.toString(result);
                fw = new FileWriter(files[i] + "KapurKuwahara.txt");
                bw = new BufferedWriter(fw);
                bw.write(content);
                bw.close();
                resetImage(event);
            }
            //GLOBALNE, KUWAHAR x1 5x5
            for(int i = 0; i < files.length; i++) {
                in = new File("D:\\Dokumenty\\Studia\\Zajęcia\\Wykłady\\Seminarium\\wybrane rysunki\\original\\" + files[i] + ".bmp");
                gt_file = new File("D:\\Dokumenty\\Studia\\Zajęcia\\Wykłady\\Seminarium\\wybrane rysunki\\gt\\" + files[i] + "_gt.bmp");
                bi = ImageIO.read(in);
                gt = ImageIO.read(gt_file);
                loadedbi = ImageIO.read(in);
                //OTSU:
                bi = filters.kuwaharaFilter(bi, 5);
                bi = binarization.otsuBinarize(bi);
                result = compare2();
                content = Double.toString(result);
                fw = new FileWriter(files[i] + "OtsuKuwahara5.txt");
                bw = new BufferedWriter(fw);
                bw.write(content);
                bw.close();
                resetImage(event);
                //RIDLER-CALVARD:
                bi = filters.kuwaharaFilter(bi, 5);
                bi = binarization.ridlerCalvardBinarize(bi);
                result = compare2();
                content = Double.toString(result);
                fw = new FileWriter(files[i] + "RidlerKuwahara5.txt");
                bw = new BufferedWriter(fw);
                bw.write(content);
                bw.close();
                resetImage(event);
                //PUN:
                bi = filters.kuwaharaFilter(bi, 5);
                bi = binarization.punBinarize(bi);
                result = compare2();
                content = Double.toString(result);
                fw = new FileWriter(files[i] + "PunKuwahara5.txt");
                bw = new BufferedWriter(fw);
                bw.write(content);
                bw.close();
                resetImage(event);
                //KAPUR-SAHOO-WONG:
                bi = filters.kuwaharaFilter(bi, 5);
                bi = binarization.kswBinarize(bi);
                result = compare2();
                content = Double.toString(result);
                fw = new FileWriter(files[i] + "KapurKuwahara5.txt");
                bw = new BufferedWriter(fw);
                bw.write(content);
                bw.close();
                resetImage(event);
            }
            
            
            //LOKALNE BEZ FILTRÓW:
            for(int i = 0; i < files.length; i++) {
                double maxBernsenNoFilter = Double.MAX_VALUE, maxWhiteRohrerNoFilter = Double.MAX_VALUE, maxNiblackNoFilter = Double.MAX_VALUE, maxSauvolaNoFilter = Double.MAX_VALUE, maxBradleyNoFilter = Double.MAX_VALUE;
                String titleMaxBernsenNoFilter = null, titleMaxWhiteRohrerNoFilter = null, titleMaxNiblackNoFilter = null, titleMaxSauvolaNoFilter = null, titleMaxBradleyNoFilter = null;
                in = new File("D:\\Dokumenty\\Studia\\Zajęcia\\Wykłady\\Seminarium\\wybrane rysunki\\original\\" + files[i] + ".bmp");
                gt_file = new File("D:\\Dokumenty\\Studia\\Zajęcia\\Wykłady\\Seminarium\\wybrane rysunki\\gt\\" + files[i] + "_gt.bmp");
                bi = ImageIO.read(in);
                gt = ImageIO.read(gt_file);
                loadedbi = ImageIO.read(in);
//                //BERNSEN:
//                //9x9, 15x15,25x25
//                for(int s = 0; s < sides.length; s++) {
//                    int[] epsilons = new int[]{30, 50, 70};
//                    //bez epsilonu
//                    bi = binarization.bernsenBinarize(bi, sides[s]);
//                    result = compare2();
//                    if(result > maxBernsenNoFilter) {
//                        maxBernsenNoFilter = result;
//                        titleMaxBernsenNoFilter = files[i] + "Bernsen" + sides[s] + "NoFilter.txt";
//                    }
//                    resetImage(event);
//                    //epsilons 30, 50, 70:, thresh=127
//                    for(int eps = 0; eps < epsilons.length; eps++) {
//                        bi = binarization.bernsenBinarize2(bi, sides[s], 127, epsilons[eps]);
//                        result = compare2();
//                        if(result > maxBernsenNoFilter) {
//                            maxBernsenNoFilter = result;
//                            titleMaxBernsenNoFilter = files[i] + "Bernsen" + sides[s] + "NoFilter.txt";
//                        }
//                        resetImage(event);
//                    }
//                }
//                fw = new FileWriter(titleMaxBernsenNoFilter);
//                bw = new BufferedWriter(fw);
//                bw.write(Double.toString(maxBernsenNoFilter));
//                bw.close();
//                //WHITE-ROHRER:
//                //9x9, 15x15,25x25
//                for(int s = 0; s < sides.length; s++) {
//                    double[] ks = new double[]{1.2, 1.5, 2.0, 2.3};
//                    String[] kss = new String[]{"12", "15", "20", "23"};
//                    //k=1.2, 1.5, 2.0, 2.3
//                    for(int k = 0; k < ks.length; k++) {
//                        bi = binarization.whiteRohrerBinarize(bi, sides[s], ks[k]);
//                        result = compare2();
//                        if(result > maxWhiteRohrerNoFilter) {
//                            maxWhiteRohrerNoFilter = result;
//                            titleMaxWhiteRohrerNoFilter = files[i] + "WhiteRohrer" + sides[s] + "K" + kss[k] + "NoFilter.txt";
//                        }
//                        resetImage(event);
//                    }
//                }
//                fw = new FileWriter(titleMaxWhiteRohrerNoFilter);
//                bw = new BufferedWriter(fw);
//                bw.write(Double.toString(maxWhiteRohrerNoFilter));
//                bw.close();
//                //NIBLACK:
//                 //9x9, 15x15,25x25
//                for(int s = 0; s < sides.length; s++) {
//                    double[] ks = new double[]{-0.5, -1.0, -1.5};
//                    String[] kss = new String[]{"-05", "-10", "-15"};
//                    //k=-0.5, -1.0, -1.5
//                    for(int k = 0; k < ks.length; k++) {
//                        bi = binarization.niblackBinarize(bi, sides[s], ks[k]);
//                        result = compare2();
//                        if(result > maxNiblackNoFilter) {
//                            maxNiblackNoFilter = result;
//                            titleMaxNiblackNoFilter = files[i] + "Niblack" + sides[s] + "K" + kss[k] + "NoFilter.txt";
//                        }
//                        resetImage(event);
//                    }
//                }
//                fw = new FileWriter(titleMaxNiblackNoFilter);
//                bw = new BufferedWriter(fw);
//                bw.write(Double.toString(maxNiblackNoFilter));
//                bw.close();
//                //SAUVOLA:
//                //9x9, 15x15,25x25
//                for(int s = 0; s < sides.length; s++) {
//                    double[] ks = new double[]{0.3, 0.5, 0.7};
//                    String[] kss = new String[]{"03", "05", "07"};
//                    //k=0.3, 0.5, 0.7
//                    for(int k = 0; k < ks.length; k++) {
//                        bi = binarization.sauvolaBinarize(bi, sides[s], ks[k], 128);
//                        result = compare2();
//                        if(result > maxSauvolaNoFilter) {
//                            maxSauvolaNoFilter = result;
//                            titleMaxSauvolaNoFilter = files[i] + "Sauvola" + sides[s] + "K" + kss[k] + "NoFilter.txt";
//                        }
//                        resetImage(event);
//                    }
//                }
//                fw = new FileWriter(titleMaxSauvolaNoFilter);
//                bw = new BufferedWriter(fw);
//                bw.write(Double.toString(maxSauvolaNoFilter));
//                bw.close();
                //BRADLEY:
                //s=9, 15, 25
                int sides2[] = new int[]{9, 15, 25, 50, 100};
                for(int s = 0; s < sides2.length; s++) {
                    int[] ts = new int[]{10, 15, 20};
                    String[] tss = new String[]{"10", "15", "20"};
                    //t=10, 15, 20
                    for(int t = 0; t < ts.length; t++) {
                        bi = binarization.bradleyBinarize(bi, sides2[s], ts[t]);
                        result = compare2();
                        if(result > maxBradleyNoFilter) {
                            maxBradleyNoFilter = result;
                            titleMaxBradleyNoFilter = files[i] + "BradleyS" + sides2[s] + "T" + tss[t] + "NoFilter.txt";
                        }
                        resetImage(event);
                    }
                }
                fw = new FileWriter(titleMaxBradleyNoFilter);
                bw = new BufferedWriter(fw);
                bw.write(Double.toString(maxBradleyNoFilter));
                bw.close();
            }
//            
            //LOKALNE gauss1, gauss2, gauss3:
            for(int i = 0; i < files.length; i++) {
                double[] maxBernsenGauss = new double[]{Double.MAX_VALUE,Double.MAX_VALUE,Double.MAX_VALUE}, maxWhiteRohrerGauss = new double[]{Double.MAX_VALUE,Double.MAX_VALUE,Double.MAX_VALUE}, maxNiblackGauss = new double[]{Double.MAX_VALUE,Double.MAX_VALUE,Double.MAX_VALUE}, mxaSauvolaGauss = new double[]{Double.MAX_VALUE,Double.MAX_VALUE,Double.MAX_VALUE}, maxBradleyGauss = new double[]{Double.MAX_VALUE,Double.MAX_VALUE,Double.MAX_VALUE};
                String[] titleMaxBernsenGauss = new String[]{null,null,null}, titleMaxWhiteRohrerGauss = new String[]{null,null,null}, titleMaxNiblackGauss = new String[]{null,null,null}, titleMaxSauvolaGauss = new String[]{null,null,null}, titleMaxBradleyGauss = new String[]{null,null,null};
                in = new File("D:\\Dokumenty\\Studia\\Zajęcia\\Wykłady\\Seminarium\\wybrane rysunki\\original\\" + files[i] + ".bmp");
                gt_file = new File("D:\\Dokumenty\\Studia\\Zajęcia\\Wykłady\\Seminarium\\wybrane rysunki\\gt\\" + files[i] + "_gt.bmp");
                bi = ImageIO.read(in);
                gt = ImageIO.read(gt_file);
                loadedbi = ImageIO.read(in);
                
                for(int g = 0; g < gausses.length; g++) {
                    int gg = g+1;
//                    //BERNSEN:
//                    //9x9, 15x15,25x25
//                    for(int s = 0; s < sides.length; s++) {
//                        int[] epsilons = new int[]{30, 50, 70};
//                        //bez epsilonu
//                        bi = filters.filter(bi, gausses[g]);
//                        bi = binarization.bernsenBinarize(bi, sides[s]);
//                        result = compare2();
//                        if(result > maxBernsenGauss[g]) {
//                            maxBernsenGauss[g] = result;
//                            titleMaxBernsenGauss[g] = files[i] + "Bernsen" + sides[s] + "Gauss" + gg + ".txt";
//                        }
//                        resetImage(event);
//                        //epsilons 30, 50, 70:, thresh=127
//                        for(int eps = 0; eps < epsilons.length; eps++) {
//                            bi = filters.filter(bi, gausses[g]);
//                            bi = binarization.bernsenBinarize2(bi, sides[s], 127, epsilons[eps]);
//                            result = compare2();
//                            if(result > maxBernsenGauss[g]) {
//                                maxBernsenGauss[g] = result;
//                                titleMaxBernsenGauss[g] = files[i] + "Bernsen" + sides[s] + "Gauss" + gg + ".txt";
//                            }
//                            resetImage(event);
//                        }
//                    }
//                    fw = new FileWriter(titleMaxBernsenGauss[g]);
//                    bw = new BufferedWriter(fw);
//                    bw.write(Double.toString(maxBernsenGauss[g]));
//                    bw.close();
//                    //WHITE-ROHRER:
//                    //9x9, 15x15,25x25
//                    for(int s = 0; s < sides.length; s++) {
//                        double[] ks = new double[]{1.2, 1.5, 2.0, 2.3};
//                        String[] kss = new String[]{"12", "15", "20", "23"};
//                        //k=1.2, 1.5, 2.0, 2.3
//                        for(int k = 0; k < ks.length; k++) {
//                            bi = filters.filter(bi, gausses[g]);
//                            bi = binarization.whiteRohrerBinarize(bi, sides[s], ks[k]);
//                            result = compare2();
//                            if(result > maxWhiteRohrerGauss[g]) {
//                                maxWhiteRohrerGauss[g] = result;
//                                titleMaxWhiteRohrerGauss[g] = files[i] + "WhiteRohrer" + sides[s] + "K" + kss[k] + "Gauss" + gg + ".txt";
//                            }
//                            resetImage(event);
//                        }
//                    }
//                    fw = new FileWriter(titleMaxWhiteRohrerGauss[g]);
//                    bw = new BufferedWriter(fw);
//                    bw.write(Double.toString(maxWhiteRohrerGauss[g]));
//                    bw.close();
//                    //NIBLACK:
//                     //9x9, 15x15,25x25
//                    for(int s = 0; s < sides.length; s++) {
//                        double[] ks = new double[]{-0.5, -1.0, -1.5};
//                        String[] kss = new String[]{"-05", "-10", "-15"};
//                        //k=-0.5, -1.0, -1.5
//                        for(int k = 0; k < ks.length; k++) {
//                            bi = filters.filter(bi, gausses[g]);
//                            bi = binarization.niblackBinarize(bi, sides[s], ks[k]);
//                            result = compare2();
//                            if(result > maxNiblackGauss[g]) {
//                                maxNiblackGauss[g] = result;
//                                titleMaxNiblackGauss[g] = files[i] + "Niblack" + sides[s] + "K" + kss[k] + "Gauss" + gg + ".txt";
//                            }
//                            resetImage(event);
//                        }
//                    }
//                    fw = new FileWriter(titleMaxNiblackGauss[g]);
//                    bw = new BufferedWriter(fw);
//                    bw.write(Double.toString(maxNiblackGauss[g]));
//                    bw.close();
//                    //SAUVOLA:
//                    //9x9, 15x15,25x25
//                    for(int s = 0; s < sides.length; s++) {
//                        double[] ks = new double[]{0.3, 0.5, 0.7};
//                        String[] kss = new String[]{"03", "05", "07"};
//                        //k=0.3, 0.5, 0.7
//                        for(int k = 0; k < ks.length; k++) {
//                            bi = filters.filter(bi, gausses[g]);
//                            bi = binarization.sauvolaBinarize(bi, sides[s], ks[k], 128);
//                            result = compare2();
//                            if(result > mxaSauvolaGauss[g]) {
//                                mxaSauvolaGauss[g] = result;
//                                titleMaxSauvolaGauss[g] = files[i] + "Sauvola" + sides[s] + "K" + kss[k] + "Gauss" + gg + ".txt";
//                            }
//                            resetImage(event);
//                        }
//                    }
//                    fw = new FileWriter(titleMaxSauvolaGauss[g]);
//                    bw = new BufferedWriter(fw);
//                    bw.write(Double.toString(mxaSauvolaGauss[g]));
//                    bw.close();
                    //BRADLEY:
                    //s=9, 15, 25
                    int sides2[] = new int[]{9, 15, 25, 50, 100};
                    for(int s = 0; s < sides2.length; s++) {
                        int[] ts = new int[]{10, 15, 20};
                        String[] tss = new String[]{"10", "15", "20"};
                        //t=10, 15, 20
                        for(int t = 0; t < ts.length; t++) {
                            bi = filters.filter(bi, gausses[g]);
                            bi = binarization.bradleyBinarize(bi, sides2[s], ts[t]);
                            result = compare2();
                            if(result > maxBradleyGauss[g]) {
                                maxBradleyGauss[g] = result;
                                titleMaxBradleyGauss[g] = files[i] + "BradleyS" + sides2[s] + "T" + tss[t] + "Gauss" + gg + ".txt";
                            }
                            resetImage(event);
                        }
                    }
                    fw = new FileWriter(titleMaxBradleyGauss[g]);
                    bw = new BufferedWriter(fw);
                    bw.write(Double.toString(maxBradleyGauss[g]));
                    bw.close();
                }
            }
            
            //LOKALNE MEDIANAA 3X3:
            for(int i = 0; i < files.length; i++) {
                double maxBernsenMedian = 0, maxWhiteRohrerMedian = 0, maxNiblackMedian = 0, maxSauvolaMedian = 0, maxBradleyMedian = 0;
                String titleMaxBernsenMedian = null, titleMaxWhiteRohrerMedian = null, titleMaxNiblackMedian = null, titleMaxSauvolaMedian = null, titleMaxBradleyMedian = null;
                in = new File("D:\\Dokumenty\\Studia\\Zajęcia\\Wykłady\\Seminarium\\wybrane rysunki\\original\\" + files[i] + ".bmp");
                gt_file = new File("D:\\Dokumenty\\Studia\\Zajęcia\\Wykłady\\Seminarium\\wybrane rysunki\\gt\\" + files[i] + "_gt.bmp");
                bi = ImageIO.read(in);
                gt = ImageIO.read(gt_file);
                loadedbi = ImageIO.read(in);
                //BERNSEN:
                //9x9, 15x15,25x25
                for(int s = 0; s < sides.length; s++) {
                    int[] epsilons = new int[]{30, 50, 70};
                    //bez epsilonu
                    bi = filters.medianFilter(bi, 3);
                    bi = binarization.bernsenBinarize(bi, sides[s]);
                    result = compare2();
                    if(result > maxBernsenMedian) {
                        maxBernsenMedian = result;
                        titleMaxBernsenMedian = files[i] + "Bernsen" + sides[s] + "Median.txt";
                    }
                    resetImage(event);
                    //epsilons 30, 50, 70:, thresh=127
                    for(int eps = 0; eps < epsilons.length; eps++) {
                        bi = filters.medianFilter(bi, 3);
                        bi = binarization.bernsenBinarize2(bi, sides[s], 127, epsilons[eps]);
                        result = compare2();
                        if(result > maxBernsenMedian) {
                            maxBernsenMedian = result;
                            titleMaxBernsenMedian = files[i] + "Bernsen" + sides[s] + "Median.txt";
                        }
                        resetImage(event);
                    }
                }
                fw = new FileWriter(titleMaxBernsenMedian);
                bw = new BufferedWriter(fw);
                bw.write(Double.toString(maxBernsenMedian));
                bw.close();
                //WHITE-ROHRER:
                //9x9, 15x15,25x25
                for(int s = 0; s < sides.length; s++) {
                    double[] ks = new double[]{1.2, 1.5, 2.0, 2.3};
                    String[] kss = new String[]{"12", "15", "20", "23"};
                    //k=1.2, 1.5, 2.0, 2.3
                    for(int k = 0; k < ks.length; k++) {
                        bi = filters.medianFilter(bi, 3);
                        bi = binarization.whiteRohrerBinarize(bi, sides[s], ks[k]);
                        result = compare2();
                        if(result > maxWhiteRohrerMedian) {
                            maxWhiteRohrerMedian = result;
                            titleMaxWhiteRohrerMedian = files[i] + "WhiteRohrer" + sides[s] + "K" + kss[k] + "Median.txt";
                        }
                        resetImage(event);
                    }
                }
                fw = new FileWriter(titleMaxWhiteRohrerMedian);
                bw = new BufferedWriter(fw);
                bw.write(Double.toString(maxWhiteRohrerMedian));
                bw.close();
                //NIBLACK:
                 //9x9, 15x15,25x25
                for(int s = 0; s < sides.length; s++) {
                    double[] ks = new double[]{-0.5, -1.0, -1.5};
                    String[] kss = new String[]{"-05", "-10", "-15"};
                    //k=-0.5, -1.0, -1.5
                    for(int k = 0; k < ks.length; k++) {
                        bi = filters.medianFilter(bi, 3);
                        bi = binarization.niblackBinarize(bi, sides[s], ks[k]);
                        result = compare2();
                        if(result > maxNiblackMedian) {
                            maxNiblackMedian = result;
                            titleMaxNiblackMedian = files[i] + "Niblack" + sides[s] + "K" + kss[k] + "Median.txt";
                        }
                        resetImage(event);
                    }
                }
                fw = new FileWriter(titleMaxNiblackMedian);
                bw = new BufferedWriter(fw);
                bw.write(Double.toString(maxNiblackMedian));
                bw.close();
                //SAUVOLA:
                //9x9, 15x15,25x25
                for(int s = 0; s < sides.length; s++) {
                    double[] ks = new double[]{0.3, 0.5, 0.7};
                    String[] kss = new String[]{"03", "05", "07"};
                    //k=0.3, 0.5, 0.7
                    for(int k = 0; k < ks.length; k++) {
                        bi = filters.medianFilter(bi, 3);
                        bi = binarization.sauvolaBinarize(bi, sides[s], ks[k], 128);
                        result = compare2();
                        if(result > maxSauvolaMedian) {
                            maxSauvolaMedian = result;
                            titleMaxSauvolaMedian = files[i] + "Sauvola" + sides[s] + "K" + kss[k] + "Median.txt";
                        }
                        resetImage(event);
                    }
                }
                fw = new FileWriter(titleMaxSauvolaMedian);
                bw = new BufferedWriter(fw);
                bw.write(Double.toString(maxSauvolaMedian));
                bw.close();
                //BRADLEY:
                //s=9, 15, 25
                int sides2[] = new int[]{9, 15, 25, 50, 100};
                for(int s = 0; s < sides2.length; s++) {
                    int[] ts = new int[]{10, 15, 20};
                    String[] tss = new String[]{"10", "15", "20"};
                    //t=10, 15, 20
                    for(int t = 0; t < ts.length; t++) {
                        bi = filters.medianFilter(bi, 3);
                        bi = binarization.bradleyBinarize(bi, sides2[s], ts[t]);
                        result = compare2();
                        if(result > maxBradleyMedian) {
                            maxBradleyMedian = result;
                            titleMaxBradleyMedian = files[i] + "BradleyS" + sides2[s] + "T" + tss[t] + "Median.txt";
                        }
                        resetImage(event);
                    }
                }
                fw = new FileWriter(titleMaxBradleyMedian);
                bw = new BufferedWriter(fw);
                bw.write(Double.toString(maxBradleyMedian));
                bw.close();
            }
//            
            //LOKALNE MEDIANAA 5X5:
            for(int i = 0; i < files.length; i++) {
                double maxBernsenMedian = 0, maxWhiteRohrerMedian = 0, maxNiblackMedian = 0, maxSauvolaMedian = 0, maxBradleyMedian = 0;
                String titleMaxBernsenMedian = null, titleMaxWhiteRohrerMedian = null, titleMaxNiblackMedian = null, titleMaxSauvolaMedian = null, titleMaxBradleyMedian = null;
                in = new File("D:\\Dokumenty\\Studia\\Zajęcia\\Wykłady\\Seminarium\\wybrane rysunki\\original\\" + files[i] + ".bmp");
                gt_file = new File("D:\\Dokumenty\\Studia\\Zajęcia\\Wykłady\\Seminarium\\wybrane rysunki\\gt\\" + files[i] + "_gt.bmp");
                bi = ImageIO.read(in);
                gt = ImageIO.read(gt_file);
                loadedbi = ImageIO.read(in);
                //BERNSEN:
                //9x9, 15x15,25x25
                for(int s = 0; s < sides.length; s++) {
                    int[] epsilons = new int[]{30, 50, 70};
                    //bez epsilonu
                    bi = filters.medianFilter(bi, 5);
                    bi = binarization.bernsenBinarize(bi, sides[s]);
                    result = compare2();
                    if(result > maxBernsenMedian) {
                        maxBernsenMedian = result;
                        titleMaxBernsenMedian = files[i] + "Bernsen" + sides[s] + "Median5.txt";
                    }
                    resetImage(event);
                    //epsilons 30, 50, 70:, thresh=127
                    for(int eps = 0; eps < epsilons.length; eps++) {
                        bi = filters.medianFilter(bi, 5);
                        bi = binarization.bernsenBinarize2(bi, sides[s], 127, epsilons[eps]);
                        result = compare2();
                        if(result > maxBernsenMedian) {
                            maxBernsenMedian = result;
                            titleMaxBernsenMedian = files[i] + "Bernsen" + sides[s] + "Median5.txt";
                        }
                        resetImage(event);
                    }
                }
                fw = new FileWriter(titleMaxBernsenMedian);
                bw = new BufferedWriter(fw);
                bw.write(Double.toString(maxBernsenMedian));
                bw.close();
                //WHITE-ROHRER:
                //9x9, 15x15,25x25
                for(int s = 0; s < sides.length; s++) {
                    double[] ks = new double[]{1.2, 1.5, 2.0, 2.3};
                    String[] kss = new String[]{"12", "15", "20", "23"};
                    //k=1.2, 1.5, 2.0, 2.3
                    for(int k = 0; k < ks.length; k++) {
                        bi = filters.medianFilter(bi, 5);
                        bi = binarization.whiteRohrerBinarize(bi, sides[s], ks[k]);
                        result = compare2();
                        if(result > maxWhiteRohrerMedian) {
                            maxWhiteRohrerMedian = result;
                            titleMaxWhiteRohrerMedian = files[i] + "WhiteRohrer" + sides[s] + "K" + kss[k] + "Median5.txt";
                        }
                        resetImage(event);
                    }
                }
                fw = new FileWriter(titleMaxWhiteRohrerMedian);
                bw = new BufferedWriter(fw);
                bw.write(Double.toString(maxWhiteRohrerMedian));
                bw.close();
                //NIBLACK:
                 //9x9, 15x15,25x25
                for(int s = 0; s < sides.length; s++) {
                    double[] ks = new double[]{-0.5, -1.0, -1.5};
                    String[] kss = new String[]{"-05", "-10", "-15"};
                    //k=-0.5, -1.0, -1.5
                    for(int k = 0; k < ks.length; k++) {
                        bi = filters.medianFilter(bi, 5);
                        bi = binarization.niblackBinarize(bi, sides[s], ks[k]);
                        result = compare2();
                        if(result > maxNiblackMedian) {
                            maxNiblackMedian = result;
                            titleMaxNiblackMedian = files[i] + "Niblack" + sides[s] + "K" + kss[k] + "Median5.txt";
                        }
                        resetImage(event);
                    }
                }
                fw = new FileWriter(titleMaxNiblackMedian);
                bw = new BufferedWriter(fw);
                bw.write(Double.toString(maxNiblackMedian));
                bw.close();
                //SAUVOLA:
                //9x9, 15x15,25x25
                for(int s = 0; s < sides.length; s++) {
                    double[] ks = new double[]{0.3, 0.5, 0.7};
                    String[] kss = new String[]{"03", "05", "07"};
                    //k=0.3, 0.5, 0.7
                    for(int k = 0; k < ks.length; k++) {
                        bi = filters.medianFilter(bi, 5);
                        bi = binarization.sauvolaBinarize(bi, sides[s], ks[k], 128);
                        result = compare2();
                        if(result > maxSauvolaMedian) {
                            maxSauvolaMedian = result;
                            titleMaxSauvolaMedian = files[i] + "Sauvola" + sides[s] + "K" + kss[k] + "Median5.txt";
                        }
                        resetImage(event);
                    }
                }
                fw = new FileWriter(titleMaxSauvolaMedian);
                bw = new BufferedWriter(fw);
                bw.write(Double.toString(maxSauvolaMedian));
                bw.close();
                //BRADLEY:
                //s=9, 15, 25
                int sides2[] = new int[]{9, 15, 25, 50, 100};
                for(int s = 0; s < sides2.length; s++) {
                    int[] ts = new int[]{10, 15, 20};
                    String[] tss = new String[]{"10", "15", "20"};
                    //t=10, 15, 20
                    for(int t = 0; t < ts.length; t++) {
                        bi = filters.medianFilter(bi, 5);
                        bi = binarization.bradleyBinarize(bi, sides2[s], ts[t]);
                        result = compare2();
                        if(result > maxBradleyMedian) {
                            maxBradleyMedian = result;
                            titleMaxBradleyMedian = files[i] + "BradleyS" + sides2[s] + "T" + tss[t] + "Median5.txt";
                        }
                        resetImage(event);
                    }
                }
                fw = new FileWriter(titleMaxBradleyMedian);
                bw = new BufferedWriter(fw);
                bw.write(Double.toString(maxBradleyMedian));
                bw.close();
            }
            
            //LOKALNE, ANIZOTROPIC DIFF 5 iteracji, threshold 15, sigma 0
            for(int i = 0; i < files.length; i++) {
                double maxBernsenMedian = 0, maxWhiteRohrerMedian = 0, maxNiblackMedian = 0, maxSauvolaMedian = 0, maxBradleyMedian = 0;
                String titleMaxBernsenMedian = null, titleMaxWhiteRohrerMedian = null, titleMaxNiblackMedian = null, titleMaxSauvolaMedian = null, titleMaxBradleyMedian = null;
                in = new File("D:\\Dokumenty\\Studia\\Zajęcia\\Wykłady\\Seminarium\\wybrane rysunki\\original\\" + files[i] + ".bmp");
                gt_file = new File("D:\\Dokumenty\\Studia\\Zajęcia\\Wykłady\\Seminarium\\wybrane rysunki\\gt\\" + files[i] + "_gt.bmp");
                bi = ImageIO.read(in);
                gt = ImageIO.read(gt_file);
                loadedbi = ImageIO.read(in);
//                //BERNSEN:
//                //9x9, 15x15,25x25
//                for(int s = 0; s < sides.length; s++) {
//                    int[] epsilons = new int[]{30, 50, 70};
//                    //bez epsilonu
//                    bi = adf.filter(bi, 5, 1, 15, 0.0);
//                    bi = binarization.bernsenBinarize(bi, sides[s]);
//                    result = compare2();
//                    if(result > maxBernsenMedian) {
//                        maxBernsenMedian = result;
//                        titleMaxBernsenMedian = files[i] + "Bernsen" + sides[s] + "Anizotropic5.txt";
//                    }
//                    resetImage(event);
//                    //epsilons 30, 50, 70:, thresh=127
//                    for(int eps = 0; eps < epsilons.length; eps++) {
//                        bi = adf.filter(bi, 5, 1, 15, 0.0);
//                        bi = binarization.bernsenBinarize2(bi, sides[s], 127, epsilons[eps]);
//                        result = compare2();
//                        if(result > maxBernsenMedian) {
//                            maxBernsenMedian = result;
//                            titleMaxBernsenMedian = files[i] + "Bernsen" + sides[s] + "Anizotropic5.txt";
//                        }
//                        resetImage(event);
//                    }
//                }
//                fw = new FileWriter(titleMaxBernsenMedian);
//                bw = new BufferedWriter(fw);
//                bw.write(Double.toString(maxBernsenMedian));
//                bw.close();
//                //WHITE-ROHRER:
//                //9x9, 15x15,25x25
//                for(int s = 0; s < sides.length; s++) {
//                    double[] ks = new double[]{1.2, 1.5, 2.0, 2.3};
//                    String[] kss = new String[]{"12", "15", "20", "23"};
//                    //k=1.2, 1.5, 2.0, 2.3
//                    for(int k = 0; k < ks.length; k++) {
//                        bi = adf.filter(bi, 5, 1, 15, 0.0);
//                        bi = binarization.whiteRohrerBinarize(bi, sides[s], ks[k]);
//                        result = compare2();
//                        if(result > maxWhiteRohrerMedian) {
//                            maxWhiteRohrerMedian = result;
//                            titleMaxWhiteRohrerMedian = files[i] + "WhiteRohrer" + sides[s] + "K" + kss[k] + "Anizotropic5.txt";
//                        }
//                        resetImage(event);
//                    }
//                }
//                fw = new FileWriter(titleMaxWhiteRohrerMedian);
//                bw = new BufferedWriter(fw);
//                bw.write(Double.toString(maxWhiteRohrerMedian));
//                bw.close();
//                //NIBLACK:
//                 //9x9, 15x15,25x25
//                for(int s = 0; s < sides.length; s++) {
//                    double[] ks = new double[]{-0.5, -1.0, -1.5};
//                    String[] kss = new String[]{"-05", "-10", "-15"};
//                    //k=-0.5, -1.0, -1.5
//                    for(int k = 0; k < ks.length; k++) {
//                        bi = adf.filter(bi, 5, 1, 15, 0.0);
//                        bi = binarization.niblackBinarize(bi, sides[s], ks[k]);
//                        result = compare2();
//                        if(result > maxNiblackMedian) {
//                            maxNiblackMedian = result;
//                            titleMaxNiblackMedian = files[i] + "Niblack" + sides[s] + "K" + kss[k] + "Anizotropic5.txt";
//                        }
//                        resetImage(event);
//                    }
//                }
//                fw = new FileWriter(titleMaxNiblackMedian);
//                bw = new BufferedWriter(fw);
//                bw.write(Double.toString(maxNiblackMedian));
//                bw.close();
//                //SAUVOLA:
//                //9x9, 15x15,25x25
//                for(int s = 0; s < sides.length; s++) {
//                    double[] ks = new double[]{0.3, 0.5, 0.7};
//                    String[] kss = new String[]{"03", "05", "07"};
//                    //k=0.3, 0.5, 0.7
//                    for(int k = 0; k < ks.length; k++) {
//                        bi = adf.filter(bi, 5, 1, 15, 0.0);
//                        bi = binarization.sauvolaBinarize(bi, sides[s], ks[k], 128);
//                        result = compare2();
//                        if(result > maxSauvolaMedian) {
//                            maxSauvolaMedian = result;
//                            titleMaxSauvolaMedian = files[i] + "Sauvola" + sides[s] + "K" + kss[k] + "Anizotropic5.txt";
//                        }
//                        resetImage(event);
//                    }
//                }
//                fw = new FileWriter(titleMaxSauvolaMedian);
//                bw = new BufferedWriter(fw);
//                bw.write(Double.toString(maxSauvolaMedian));
//                bw.close();
                //BRADLEY:
                //s=9, 15, 25
                int sides2[] = new int[]{9, 15, 25, 50, 100};
                for(int s = 0; s < sides2.length; s++) {
                    int[] ts = new int[]{10, 15, 20};
                    String[] tss = new String[]{"10", "15", "20"};
                    //t=10, 15, 20
                    for(int t = 0; t < ts.length; t++) {
                        bi = adf.filter(bi, 5, 1, 15, 0.0);
                        bi = binarization.bradleyBinarize(bi, sides2[s], ts[t]);
                        result = compare2();
                        if(result > maxBradleyMedian) {
                            maxBradleyMedian = result;
                            titleMaxBradleyMedian = files[i] + "BradleyS" + sides2[s] + "T" + tss[t] + "Anizotropic5.txt";
                        }
                        resetImage(event);
                    }
                }
                fw = new FileWriter(titleMaxBradleyMedian);
                bw = new BufferedWriter(fw);
                bw.write(Double.toString(maxBradleyMedian));
                bw.close();
            }
            
            
            //LOKALNE, ANIZOTROPIC DIFF 10 iteracji, threshold 15, sigma 0
            for(int i = 0; i < files.length; i++) {
                double maxBernsenMedian = 0, maxWhiteRohrerMedian = 0, maxNiblackMedian = 0, maxSauvolaMedian = 0, maxBradleyMedian = 0;
                String titleMaxBernsenMedian = null, titleMaxWhiteRohrerMedian = null, titleMaxNiblackMedian = null, titleMaxSauvolaMedian = null, titleMaxBradleyMedian = null;
                in = new File("D:\\Dokumenty\\Studia\\Zajęcia\\Wykłady\\Seminarium\\wybrane rysunki\\original\\" + files[i] + ".bmp");
                gt_file = new File("D:\\Dokumenty\\Studia\\Zajęcia\\Wykłady\\Seminarium\\wybrane rysunki\\gt\\" + files[i] + "_gt.bmp");
                bi = ImageIO.read(in);
                gt = ImageIO.read(gt_file);
                loadedbi = ImageIO.read(in);
//                //BERNSEN:
//                //9x9, 15x15,25x25
//                for(int s = 0; s < sides.length; s++) {
//                    int[] epsilons = new int[]{30, 50, 70};
//                    //bez epsilonu
//                    bi = adf.filter(bi, 10, 1, 15, 0.0);
//                    bi = binarization.bernsenBinarize(bi, sides[s]);
//                    result = compare2();
//                    if(result > maxBernsenMedian) {
//                        maxBernsenMedian = result;
//                        titleMaxBernsenMedian = files[i] + "Bernsen" + sides[s] + "Anizotropic10.txt";
//                    }
//                    resetImage(event);
//                    //epsilons 30, 50, 70:, thresh=127
//                    for(int eps = 0; eps < epsilons.length; eps++) {
//                        bi = adf.filter(bi, 10, 1, 15, 0.0);
//                        bi = binarization.bernsenBinarize2(bi, sides[s], 127, epsilons[eps]);
//                        result = compare2();
//                        if(result > maxBernsenMedian) {
//                            maxBernsenMedian = result;
//                            titleMaxBernsenMedian = files[i] + "Bernsen" + sides[s] + "Anizotropic10.txt";
//                        }
//                        resetImage(event);
//                    }
//                }
//                fw = new FileWriter(titleMaxBernsenMedian);
//                bw = new BufferedWriter(fw);
//                bw.write(Double.toString(maxBernsenMedian));
//                bw.close();
//                //WHITE-ROHRER:
//                //9x9, 15x15,25x25
//                for(int s = 0; s < sides.length; s++) {
//                    double[] ks = new double[]{1.2, 1.5, 2.0, 2.3};
//                    String[] kss = new String[]{"12", "15", "20", "23"};
//                    //k=1.2, 1.5, 2.0, 2.3
//                    for(int k = 0; k < ks.length; k++) {
//                        bi = adf.filter(bi, 10, 1, 15, 0.0);
//                        bi = binarization.whiteRohrerBinarize(bi, sides[s], ks[k]);
//                        result = compare2();
//                        if(result > maxWhiteRohrerMedian) {
//                            maxWhiteRohrerMedian = result;
//                            titleMaxWhiteRohrerMedian = files[i] + "WhiteRohrer" + sides[s] + "K" + kss[k] + "Anizotropic10.txt";
//                        }
//                        resetImage(event);
//                    }
//                }
//                fw = new FileWriter(titleMaxWhiteRohrerMedian);
//                bw = new BufferedWriter(fw);
//                bw.write(Double.toString(maxWhiteRohrerMedian));
//                bw.close();
//                //NIBLACK:
//                 //9x9, 15x15,25x25
//                for(int s = 0; s < sides.length; s++) {
//                    double[] ks = new double[]{-0.5, -1.0, -1.5};
//                    String[] kss = new String[]{"-05", "-10", "-15"};
//                    //k=-0.5, -1.0, -1.5
//                    for(int k = 0; k < ks.length; k++) {
//                        bi = adf.filter(bi, 10, 1, 15, 0.0);
//                        bi = binarization.niblackBinarize(bi, sides[s], ks[k]);
//                        result = compare2();
//                        if(result > maxNiblackMedian) {
//                            maxNiblackMedian = result;
//                            titleMaxNiblackMedian = files[i] + "Niblack" + sides[s] + "K" + kss[k] + "Anizotropic10.txt";
//                        }
//                        resetImage(event);
//                    }
//                }
//                fw = new FileWriter(titleMaxNiblackMedian);
//                bw = new BufferedWriter(fw);
//                bw.write(Double.toString(maxNiblackMedian));
//                bw.close();
//                //SAUVOLA:
//                //9x9, 15x15,25x25
//                for(int s = 0; s < sides.length; s++) {
//                    double[] ks = new double[]{0.3, 0.5, 0.7};
//                    String[] kss = new String[]{"03", "05", "07"};
//                    //k=0.3, 0.5, 0.7
//                    for(int k = 0; k < ks.length; k++) {
//                        bi = adf.filter(bi, 10, 1, 15, 0.0);
//                        bi = binarization.sauvolaBinarize(bi, sides[s], ks[k], 128);
//                        result = compare2();
//                        if(result > maxSauvolaMedian) {
//                            maxSauvolaMedian = result;
//                            titleMaxSauvolaMedian = files[i] + "Sauvola" + sides[s] + "K" + kss[k] + "Anizotropic10.txt";
//                        }
//                        resetImage(event);
//                    }
//                }
//                fw = new FileWriter(titleMaxSauvolaMedian);
//                bw = new BufferedWriter(fw);
//                bw.write(Double.toString(maxSauvolaMedian));
//                bw.close();
                //BRADLEY:
                //s=9, 15, 25
                int sides2[] = new int[]{9, 15, 25, 50, 100};
                for(int s = 0; s < sides2.length; s++) {
                    int[] ts = new int[]{10, 15, 20};
                    String[] tss = new String[]{"10", "15", "20"};
                    //t=10, 15, 20
                    for(int t = 0; t < ts.length; t++) {
                        bi = adf.filter(bi, 10, 1, 15, 0.0);
                        bi = binarization.bradleyBinarize(bi, sides2[s], ts[t]);
                        result = compare2();
                        if(result > maxBradleyMedian) {
                            maxBradleyMedian = result;
                            titleMaxBradleyMedian = files[i] + "BradleyS" + sides2[s] + "T" + tss[t] + "Anizotropic10.txt";
                        }
                        resetImage(event);
                    }
                }
                fw = new FileWriter(titleMaxBradleyMedian);
                bw = new BufferedWriter(fw);
                bw.write(Double.toString(maxBradleyMedian));
                bw.close();
            }
//            //LOKALNE KUWAHARA 3X3:
            for(int i = 0; i < files.length; i++) {
                double maxBernsenKuwahar = Double.MAX_VALUE, maxWhiteRohrerKuwahar = Double.MAX_VALUE, maxNiblackKuwahar = Double.MAX_VALUE, maxSauvolaKuwahar = Double.MAX_VALUE, maxBradleyKuwahar = Double.MAX_VALUE;
                String titleMaxBernsenKuwahar = null, titleMaxWhiteRohrerKuwahar = null, titleMaxNiblackKuwahar = null, titleMaxSauvolaKuwahar = null, titleMaxBradleyKuwahar = null;
                in = new File("D:\\Dokumenty\\Studia\\Zajęcia\\Wykłady\\Seminarium\\wybrane rysunki\\original\\" + files[i] + ".bmp");
                gt_file = new File("D:\\Dokumenty\\Studia\\Zajęcia\\Wykłady\\Seminarium\\wybrane rysunki\\gt\\" + files[i] + "_gt.bmp");
                bi = ImageIO.read(in);
                gt = ImageIO.read(gt_file);
                loadedbi = ImageIO.read(in);
                //BERNSEN:
                //9x9, 15x15,25x25
                for(int s = 0; s < sides.length; s++) {
                    int[] epsilons = new int[]{30, 50, 70};
                    //bez epsilonu
                    bi = filters.kuwaharaFilter(bi, 3);
                    bi = binarization.bernsenBinarize(bi, sides[s]);
                    result = compare2();
                    if(result > maxBernsenKuwahar) {
                        maxBernsenKuwahar = result;
                        titleMaxBernsenKuwahar = files[i] + "Bernsen" + sides[s] + "Kuwahara.txt";
                    }
                    resetImage(event);
                    //epsilons 30, 50, 70:, thresh=127
                    for(int eps = 0; eps < epsilons.length; eps++) {
                        bi = filters.kuwaharaFilter(bi, 3);
                        bi = binarization.bernsenBinarize2(bi, sides[s], 127, epsilons[eps]);
                        result = compare2();
                        if(result > maxBernsenKuwahar) {
                            maxBernsenKuwahar = result;
                            titleMaxBernsenKuwahar = files[i] + "Bernsen" + sides[s] + "Kuwahara.txt";
                        }
                        resetImage(event);
                    }
                }
                fw = new FileWriter(titleMaxBernsenKuwahar);
                bw = new BufferedWriter(fw);
                bw.write(Double.toString(maxBernsenKuwahar));
                bw.close();
                //WHITE-ROHRER:
                //9x9, 15x15,25x25
                for(int s = 0; s < sides.length; s++) {
                    double[] ks = new double[]{1.2, 1.5, 2.0, 2.3};
                    String[] kss = new String[]{"12", "15", "20", "23"};
                    //k=1.2, 1.5, 2.0, 2.3
                    for(int k = 0; k < ks.length; k++) {
                        bi = filters.kuwaharaFilter(bi, 3);
                        bi = binarization.whiteRohrerBinarize(bi, sides[s], ks[k]);
                        result = compare2();
                        if(result > maxWhiteRohrerKuwahar) {
                            maxWhiteRohrerKuwahar = result;
                            titleMaxWhiteRohrerKuwahar = files[i] + "WhiteRohrer" + sides[s] + "K" + kss[k] + "Kuwahara.txt";
                        }
                        resetImage(event);
                    }
                }
                fw = new FileWriter(titleMaxWhiteRohrerKuwahar);
                bw = new BufferedWriter(fw);
                bw.write(Double.toString(maxWhiteRohrerKuwahar));
                bw.close();
                //NIBLACK:
                 //9x9, 15x15,25x25
                for(int s = 0; s < sides.length; s++) {
                    double[] ks = new double[]{-0.5, -1.0, -1.5};
                    String[] kss = new String[]{"-05", "-10", "-15"};
                    //k=-0.5, -1.0, -1.5
                    for(int k = 0; k < ks.length; k++) {
                        bi = filters.kuwaharaFilter(bi, 3);
                        bi = binarization.niblackBinarize(bi, sides[s], ks[k]);
                        result = compare2();
                        if(result > maxNiblackKuwahar) {
                            maxNiblackKuwahar = result;
                            titleMaxNiblackKuwahar = files[i] + "Niblack" + sides[s] + "K" + kss[k] + "Kuwahara.txt";
                        }
                        resetImage(event);
                    }
                }
                fw = new FileWriter(titleMaxNiblackKuwahar);
                bw = new BufferedWriter(fw);
                bw.write(Double.toString(maxNiblackKuwahar));
                bw.close();
                //SAUVOLA:
                //9x9, 15x15,25x25
                for(int s = 0; s < sides.length; s++) {
                    double[] ks = new double[]{0.3, 0.5, 0.7};
                    String[] kss = new String[]{"03", "05", "07"};
                    //k=0.3, 0.5, 0.7
                    for(int k = 0; k < ks.length; k++) {
                        bi = filters.kuwaharaFilter(bi, 3);
                        bi = binarization.sauvolaBinarize(bi, sides[s], ks[k], 128);
                        result = compare2();
                        if(result > maxSauvolaKuwahar) {
                            maxSauvolaKuwahar = result;
                            titleMaxSauvolaKuwahar = files[i] + "Sauvola" + sides[s] + "K" + kss[k] + "Kuwahara.txt";
                        }
                        resetImage(event);
                    }
                }
                fw = new FileWriter(titleMaxSauvolaKuwahar);
                bw = new BufferedWriter(fw);
                bw.write(Double.toString(maxSauvolaKuwahar));
                bw.close();
                //BRADLEY:
                //s=9, 15, 25
                int sides2[] = new int[]{9, 15, 25, 50, 100};
                for(int s = 0; s < sides2.length; s++) {
                    int[] ts = new int[]{10, 15, 20};
                    String[] tss = new String[]{"10", "15", "20"};
                    //t=10, 15, 20
                    for(int t = 0; t < ts.length; t++) {
                        bi = filters.kuwaharaFilter(bi, 3);
                        bi = binarization.bradleyBinarize(bi, sides2[s], ts[t]);
                        result = compare2();
                        if(result > maxBradleyKuwahar) {
                            maxBradleyKuwahar = result;
                            titleMaxBradleyKuwahar = files[i] + "BradleyS" + sides2[s] + "T" + tss[t] + "Kuwahara.txt";
                        }
                        resetImage(event);
                    }
                }
                fw = new FileWriter(titleMaxBradleyKuwahar);
                bw = new BufferedWriter(fw);
                bw.write(Double.toString(maxBradleyKuwahar));
                bw.close();
            }
            
            //LOKALNE KUWAHAR 5X5:
            for(int i = 0; i < files.length; i++) {
                double maxBernsenKuwahara = 0, maxWhiteRohrerKuwahara = 0, maxNiblackKuwahara = 0, maxSauvolaKuwahara = 0, maxBradleyKuwahara = 0;
                String titleMaxBernsenKuwahara = null, titleMaxWhiteRohrerKuwahara = null, titleMaxNiblackKuwahara = null, titleMaxSauvolaKuwahara = null, titleMaxBradleyKuwahara = null;
                in = new File("D:\\Dokumenty\\Studia\\Zajęcia\\Wykłady\\Seminarium\\wybrane rysunki\\original\\" + files[i] + ".bmp");
                gt_file = new File("D:\\Dokumenty\\Studia\\Zajęcia\\Wykłady\\Seminarium\\wybrane rysunki\\gt\\" + files[i] + "_gt.bmp");
                bi = ImageIO.read(in);
                gt = ImageIO.read(gt_file);
                loadedbi = ImageIO.read(in);
                //BERNSEN:
                //9x9, 15x15,25x25
                for(int s = 0; s < sides.length; s++) {
                    int[] epsilons = new int[]{30, 50, 70};
                    //bez epsilonu
                    bi = filters.kuwaharaFilter(bi, 5);
                    bi = binarization.bernsenBinarize(bi, sides[s]);
                    result = compare2();
                    if(result > maxBernsenKuwahara) {
                        maxBernsenKuwahara = result;
                        titleMaxBernsenKuwahara = files[i] + "Bernsen" + sides[s] + "Kuwahara5.txt";
                    }
                    resetImage(event);
                    //epsilons 30, 50, 70:, thresh=127
                    for(int eps = 0; eps < epsilons.length; eps++) {
                        bi = filters.kuwaharaFilter(bi, 5);
                        bi = binarization.bernsenBinarize2(bi, sides[s], 127, epsilons[eps]);
                        result = compare2();
                        if(result > maxBernsenKuwahara) {
                            maxBernsenKuwahara = result;
                            titleMaxBernsenKuwahara = files[i] + "Bernsen" + sides[s] + "Kuwahara5.txt";
                        }
                        resetImage(event);
                    }
                }
                fw = new FileWriter(titleMaxBernsenKuwahara);
                bw = new BufferedWriter(fw);
                bw.write(Double.toString(maxBernsenKuwahara));
                bw.close();
                //WHITE-ROHRER:
                //9x9, 15x15,25x25
                for(int s = 0; s < sides.length; s++) {
                    double[] ks = new double[]{1.2, 1.5, 2.0, 2.3};
                    String[] kss = new String[]{"12", "15", "20", "23"};
                    //k=1.2, 1.5, 2.0, 2.3
                    for(int k = 0; k < ks.length; k++) {
                        bi = filters.kuwaharaFilter(bi, 5);
                        bi = binarization.whiteRohrerBinarize(bi, sides[s], ks[k]);
                        result = compare2();
                        if(result > maxWhiteRohrerKuwahara) {
                            maxWhiteRohrerKuwahara = result;
                            titleMaxWhiteRohrerKuwahara = files[i] + "WhiteRohrer" + sides[s] + "K" + kss[k] + "Kuwahara5.txt";
                        }
                        resetImage(event);
                    }
                }
                fw = new FileWriter(titleMaxWhiteRohrerKuwahara);
                bw = new BufferedWriter(fw);
                bw.write(Double.toString(maxWhiteRohrerKuwahara));
                bw.close();
                //NIBLACK:
                 //9x9, 15x15,25x25
                for(int s = 0; s < sides.length; s++) {
                    double[] ks = new double[]{-0.5, -1.0, -1.5};
                    String[] kss = new String[]{"-05", "-10", "-15"};
                    //k=-0.5, -1.0, -1.5
                    for(int k = 0; k < ks.length; k++) {
                        bi = filters.kuwaharaFilter(bi, 5);
                        bi = binarization.niblackBinarize(bi, sides[s], ks[k]);
                        result = compare2();
                        if(result > maxNiblackKuwahara) {
                            maxNiblackKuwahara = result;
                            titleMaxNiblackKuwahara = files[i] + "Niblack" + sides[s] + "K" + kss[k] + "Kuwahara5.txt";
                        }
                        resetImage(event);
                    }
                }
                fw = new FileWriter(titleMaxNiblackKuwahara);
                bw = new BufferedWriter(fw);
                bw.write(Double.toString(maxNiblackKuwahara));
                bw.close();
                //SAUVOLA:
                //9x9, 15x15,25x25
                for(int s = 0; s < sides.length; s++) {
                    double[] ks = new double[]{0.3, 0.5, 0.7};
                    String[] kss = new String[]{"03", "05", "07"};
                    //k=0.3, 0.5, 0.7
                    for(int k = 0; k < ks.length; k++) {
                        bi = filters.kuwaharaFilter(bi, 5);
                        bi = binarization.sauvolaBinarize(bi, sides[s], ks[k], 128);
                        result = compare2();
                        if(result > maxSauvolaKuwahara) {
                            maxSauvolaKuwahara = result;
                            titleMaxSauvolaKuwahara = files[i] + "Sauvola" + sides[s] + "K" + kss[k] + "Kuwahara5.txt";
                        }
                        resetImage(event);
                    }
                }
                fw = new FileWriter(titleMaxSauvolaKuwahara);
                bw = new BufferedWriter(fw);
                bw.write(Double.toString(maxSauvolaKuwahara));
                bw.close();
                //BRADLEY:
                //s=9, 15, 25
                int sides2[] = new int[]{9, 15, 25, 50, 100};
                for(int s = 0; s < sides2.length; s++) {
                    int[] ts = new int[]{10, 15, 20};
                    String[] tss = new String[]{"10", "15", "20"};
                    //t=10, 15, 20
                    for(int t = 0; t < ts.length; t++) {
                        bi = filters.kuwaharaFilter(bi, 5);
                        bi = binarization.bradleyBinarize(bi, sides2[s], ts[t]);
                        result = compare2();
                        if(result > maxBradleyKuwahara) {
                            maxBradleyKuwahara = result;
                            titleMaxBradleyKuwahara = files[i] + "BradleyS" + sides2[s] + "T" + tss[t] + "Kuwahara5.txt";
                        }
                        resetImage(event);
                    }
                }
                fw = new FileWriter(titleMaxBradleyKuwahara);
                bw = new BufferedWriter(fw);
                bw.write(Double.toString(maxBradleyKuwahara));
                bw.close();
            }
            
            
        } catch (Exception ex) {
            ex.printStackTrace();
            Logger.getLogger(FXMLDocumentController.class.getName()).log(Level.SEVERE, null, ex);
        } finally {
            try {
                if (bw != null)
                    bw.close();
                if (fw != null)
                    fw.close();
            } catch (IOException ex) {
                ex.printStackTrace();
            }
        }
    }
    
    @FXML
    private void automaticComparing3(ActionEvent event) {
        BufferedWriter bw = null;
        FileWriter fw = null;
        String[] files = new String[]{"a", "b", "c", "d", "e", "f", "g", "h", "i"};
        float[][] gausses = new float[3][];
        gausses[0] = Filters.gauss1;
        gausses[1] = Filters.gauss2;
        gausses[2] = Filters.gauss3;
        int sides[] = new int[]{9, 15, 25};
        try {
            gs = new GrayScale();
            binarization = new Binarization();
            filters = new Filters();
            AnisotropicDiffusionFilter adf = new AnisotropicDiffusionFilter();
            File in, gt_file;
            String content;
            double result;
            
            
//            //GLOBALNE BEZ FILTRÓW:
//            for(int i = 0; i < files.length; i++) {
//                in = new File("D:\\Dokumenty\\Studia\\Zajęcia\\Wykłady\\Seminarium\\wybrane rysunki\\original\\" + files[i] + ".bmp");
//                gt_file = new File("D:\\Dokumenty\\Studia\\Zajęcia\\Wykłady\\Seminarium\\wybrane rysunki\\gt\\" + files[i] + "_gt.bmp");
//                bi = ImageIO.read(in);
//                gt = ImageIO.read(gt_file);
//                loadedbi = ImageIO.read(in);
//                //OTSU:
//                bi = binarization.otsuBinarize(bi);
//                result = compare3();
//                content = Double.toString(result);
//                fw = new FileWriter(files[i] + "OtsuNoFilter.txt");
//                bw = new BufferedWriter(fw);
//                bw.write(content);
//                bw.close();
//                resetImage(event);
//                //RIDLER-CALVARD:
//                bi = binarization.ridlerCalvardBinarize(bi);
//                result = compare3();
//                content = Double.toString(result);
//                fw = new FileWriter(files[i] + "RidlerNoFilter.txt");
//                bw = new BufferedWriter(fw);
//                bw.write(content);
//                bw.close();
//                resetImage(event);
//                //PUN:
//                bi = binarization.punBinarize(bi);
//                result = compare3();
//                content = Double.toString(result);
//                fw = new FileWriter(files[i] + "PunNoFilter.txt");
//                bw = new BufferedWriter(fw);
//                bw.write(content);
//                bw.close();
//                resetImage(event);
//                //KAPUR-SAHOO-WONG:
//                bi = binarization.kswBinarize(bi);
//                result = compare3();
//                content = Double.toString(result);
//                fw = new FileWriter(files[i] + "KapurNoFilter.txt");
//                bw = new BufferedWriter(fw);
//                bw.write(content);
//                bw.close();
//                resetImage(event);
//            }
//            
//            
//            //GLOBALNE gauss1, gauss2, gauss3:
//            for(int i = 0; i < files.length; i++) {
//                in = new File("D:\\Dokumenty\\Studia\\Zajęcia\\Wykłady\\Seminarium\\wybrane rysunki\\original\\" + files[i] + ".bmp");
//                gt_file = new File("D:\\Dokumenty\\Studia\\Zajęcia\\Wykłady\\Seminarium\\wybrane rysunki\\gt\\" + files[i] + "_gt.bmp");
//                bi = ImageIO.read(in);
//                gt = ImageIO.read(gt_file);
//                loadedbi = ImageIO.read(in);
//                for(int g = 0; g < gausses.length; g++) {
//                    //OTSU:
//                    int gg = g+1;
//                    bi = filters.filter(bi, gausses[g]);
//                    bi = binarization.otsuBinarize(bi);
//                    result = compare3();
//                    content = Double.toString(result);
//                    fw = new FileWriter(files[i] + "OtsuGauss" + gg + ".txt");
//                    bw = new BufferedWriter(fw);
//                    bw.write(content);
//                    bw.close();
//                    resetImage(event);
//                    //RIDLER-CALVARD:
//                    bi = filters.filter(bi, gausses[g]);
//                    bi = binarization.ridlerCalvardBinarize(bi);
//                    result = compare3();
//                    content = Double.toString(result);
//                    fw = new FileWriter(files[i] + "RidlerGauss" + gg + ".txt");
//                    bw = new BufferedWriter(fw);
//                    bw.write(content);
//                    bw.close();
//                    resetImage(event);
//                    //PUN:
//                    bi = filters.filter(bi, gausses[g]);
//                    bi = binarization.punBinarize(bi);
//                    result = compare3();
//                    content = Double.toString(result);
//                    fw = new FileWriter(files[i] + "PunGauss" + gg + ".txt");
//                    bw = new BufferedWriter(fw);
//                    bw.write(content);
//                    bw.close();
//                    resetImage(event);
//                    //KAPUR-SAHOO-WONG:
//                    bi = filters.filter(bi, gausses[g]);
//                    bi = binarization.kswBinarize(bi);
//                    result = compare3();
//                    content = Double.toString(result);
//                    fw = new FileWriter(files[i] + "KapurGauss" + gg + ".txt");
//                    bw = new BufferedWriter(fw);
//                    bw.write(content);
//                    bw.close();
//                    resetImage(event);
//                }
//            }
//            
//            
//            //GLOBALNE, MEDIANA x1 3x3
//            for(int i = 0; i < files.length; i++) {
//                in = new File("D:\\Dokumenty\\Studia\\Zajęcia\\Wykłady\\Seminarium\\wybrane rysunki\\original\\" + files[i] + ".bmp");
//                gt_file = new File("D:\\Dokumenty\\Studia\\Zajęcia\\Wykłady\\Seminarium\\wybrane rysunki\\gt\\" + files[i] + "_gt.bmp");
//                bi = ImageIO.read(in);
//                gt = ImageIO.read(gt_file);
//                loadedbi = ImageIO.read(in);
//                //OTSU:
//                bi = filters.medianFilter(bi, 3);
//                bi = binarization.otsuBinarize(bi);
//                result = compare3();
//                content = Double.toString(result);
//                fw = new FileWriter(files[i] + "OtsuMedian.txt");
//                bw = new BufferedWriter(fw);
//                bw.write(content);
//                bw.close();
//                resetImage(event);
//                //RIDLER-CALVARD:
//                bi = filters.medianFilter(bi, 3);
//                bi = binarization.ridlerCalvardBinarize(bi);
//                result = compare3();
//                content = Double.toString(result);
//                fw = new FileWriter(files[i] + "RidlerMedian.txt");
//                bw = new BufferedWriter(fw);
//                bw.write(content);
//                bw.close();
//                resetImage(event);
//                //PUN:
//                bi = filters.medianFilter(bi, 3);
//                bi = binarization.punBinarize(bi);
//                result = compare3();
//                content = Double.toString(result);
//                fw = new FileWriter(files[i] + "PunMedian.txt");
//                bw = new BufferedWriter(fw);
//                bw.write(content);
//                bw.close();
//                resetImage(event);
//                //KAPUR-SAHOO-WONG:
//                bi = filters.medianFilter(bi, 3);
//                bi = binarization.kswBinarize(bi);
//                result = compare3();
//                content = Double.toString(result);
//                fw = new FileWriter(files[i] + "KapurMedian.txt");
//                bw = new BufferedWriter(fw);
//                bw.write(content);
//                bw.close();
//                resetImage(event);
//            }
//            
//            //GLOBALNE, MEDIANA x1 5x5
//            for(int i = 0; i < files.length; i++) {
//                in = new File("D:\\Dokumenty\\Studia\\Zajęcia\\Wykłady\\Seminarium\\wybrane rysunki\\original\\" + files[i] + ".bmp");
//                gt_file = new File("D:\\Dokumenty\\Studia\\Zajęcia\\Wykłady\\Seminarium\\wybrane rysunki\\gt\\" + files[i] + "_gt.bmp");
//                bi = ImageIO.read(in);
//                gt = ImageIO.read(gt_file);
//                loadedbi = ImageIO.read(in);
//                //OTSU:
//                bi = filters.medianFilter(bi, 5);
//                bi = binarization.otsuBinarize(bi);
//                result = compare3();
//                content = Double.toString(result);
//                fw = new FileWriter(files[i] + "OtsuMedian5.txt");
//                bw = new BufferedWriter(fw);
//                bw.write(content);
//                bw.close();
//                resetImage(event);
//                //RIDLER-CALVARD:
//                bi = filters.medianFilter(bi, 5);
//                bi = binarization.ridlerCalvardBinarize(bi);
//                result = compare3();
//                content = Double.toString(result);
//                fw = new FileWriter(files[i] + "RidlerMedian5.txt");
//                bw = new BufferedWriter(fw);
//                bw.write(content);
//                bw.close();
//                resetImage(event);
//                //PUN:
//                bi = filters.medianFilter(bi, 5);
//                bi = binarization.punBinarize(bi);
//                result = compare3();
//                content = Double.toString(result);
//                fw = new FileWriter(files[i] + "PunMedian5.txt");
//                bw = new BufferedWriter(fw);
//                bw.write(content);
//                bw.close();
//                resetImage(event);
//                //KAPUR-SAHOO-WONG:
//                bi = filters.medianFilter(bi, 5);
//                bi = binarization.kswBinarize(bi);
//                result = compare3();
//                content = Double.toString(result);
//                fw = new FileWriter(files[i] + "KapurMedian5.txt");
//                bw = new BufferedWriter(fw);
//                bw.write(content);
//                bw.close();
//                resetImage(event);
//            }
//            
//            
//            //GLOBALNE, ANIZOTROPIC DIFF 5 iteracji, threshold 15, sigma 0
//            for(int i = 0; i < files.length; i++) {
//                in = new File("D:\\Dokumenty\\Studia\\Zajęcia\\Wykłady\\Seminarium\\wybrane rysunki\\original\\" + files[i] + ".bmp");
//                gt_file = new File("D:\\Dokumenty\\Studia\\Zajęcia\\Wykłady\\Seminarium\\wybrane rysunki\\gt\\" + files[i] + "_gt.bmp");
//                bi = ImageIO.read(in);
//                gt = ImageIO.read(gt_file);
//                loadedbi = ImageIO.read(in);
//                //OTSU:
//                bi = adf.filter(bi, 5, 1, 15, 0.0);
//                bi = binarization.otsuBinarize(bi);
//                result = compare3();
//                content = Double.toString(result);
//                fw = new FileWriter(files[i] + "OtsuAnizotropic5.txt");
//                bw = new BufferedWriter(fw);
//                bw.write(content);
//                bw.close();
//                resetImage(event);
//                //RIDLER-CALVARD:
//                bi = adf.filter(bi, 5, 1, 15, 0.0);
//                bi = binarization.ridlerCalvardBinarize(bi);
//                result = compare3();
//                content = Double.toString(result);
//                fw = new FileWriter(files[i] + "RidlerAnizotropic5.txt");
//                bw = new BufferedWriter(fw);
//                bw.write(content);
//                bw.close();
//                resetImage(event);
//                //PUN:
//                bi = adf.filter(bi, 5, 1, 15, 0.0);
//                bi = binarization.punBinarize(bi);
//                result = compare3();
//                content = Double.toString(result);
//                fw = new FileWriter(files[i] + "PunAnizotropic5.txt");
//                bw = new BufferedWriter(fw);
//                bw.write(content);
//                bw.close();
//                resetImage(event);
//                //KAPUR-SAHOO-WONG:
//                bi = adf.filter(bi, 5, 1, 15, 0.0);
//                bi = binarization.kswBinarize(bi);
//                result = compare3();
//                content = Double.toString(result);
//                fw = new FileWriter(files[i] + "KapurAnizotropic5.txt");
//                bw = new BufferedWriter(fw);
//                bw.write(content);
//                bw.close();
//                resetImage(event);
//            }
//            
//            
//            //GLOBALNE, ANIZOTROPIC DIFF 10 iteracji, threshold 15, sigma 0
//            for(int i = 0; i < files.length; i++) {
//                in = new File("D:\\Dokumenty\\Studia\\Zajęcia\\Wykłady\\Seminarium\\wybrane rysunki\\original\\" + files[i] + ".bmp");
//                gt_file = new File("D:\\Dokumenty\\Studia\\Zajęcia\\Wykłady\\Seminarium\\wybrane rysunki\\gt\\" + files[i] + "_gt.bmp");
//                bi = ImageIO.read(in);
//                gt = ImageIO.read(gt_file);
//                loadedbi = ImageIO.read(in);
//                //OTSU:
//                bi = adf.filter(bi, 10, 1, 15, 0.0);
//                bi = binarization.otsuBinarize(bi);
//                result = compare3();
//                content = Double.toString(result);
//                fw = new FileWriter(files[i] + "OtsuAnizotropic10.txt");
//                bw = new BufferedWriter(fw);
//                bw.write(content);
//                bw.close();
//                resetImage(event);
//                //RIDLER-CALVARD:
//                bi = adf.filter(bi, 10, 1, 15, 0.0);
//                bi = binarization.ridlerCalvardBinarize(bi);
//                result = compare3();
//                content = Double.toString(result);
//                fw = new FileWriter(files[i] + "RidlerAnizotropic10.txt");
//                bw = new BufferedWriter(fw);
//                bw.write(content);
//                bw.close();
//                resetImage(event);
//                //PUN:
//                bi = adf.filter(bi, 10, 1, 15, 0.0);
//                bi = binarization.punBinarize(bi);
//                result = compare3();
//                content = Double.toString(result);
//                fw = new FileWriter(files[i] + "PunAnizotropic10.txt");
//                bw = new BufferedWriter(fw);
//                bw.write(content);
//                bw.close();
//                resetImage(event);
//                //KAPUR-SAHOO-WONG:
//                bi = adf.filter(bi, 10, 1, 15, 0.0);
//                bi = binarization.kswBinarize(bi);
//                result = compare3();
//                content = Double.toString(result);
//                fw = new FileWriter(files[i] + "KapurAnizotropic10.txt");
//                bw = new BufferedWriter(fw);
//                bw.write(content);
//                bw.close();
//                resetImage(event);
//            }
//            
//            
            //GLOBALNE, KUWAHARA x1 3X3
            for(int i = 0; i < files.length; i++) {
                in = new File("D:\\Dokumenty\\Studia\\Zajęcia\\Wykłady\\Seminarium\\wybrane rysunki\\original\\" + files[i] + ".bmp");
                gt_file = new File("D:\\Dokumenty\\Studia\\Zajęcia\\Wykłady\\Seminarium\\wybrane rysunki\\gt\\" + files[i] + "_gt.bmp");
                bi = ImageIO.read(in);
                gt = ImageIO.read(gt_file);
                loadedbi = ImageIO.read(in);
                //OTSU:
                bi = filters.kuwaharaFilter(bi, 3);
                bi = binarization.otsuBinarize(bi);
                result = compare3();
                content = Double.toString(result);
                fw = new FileWriter(files[i] + "OtsuKuwahara.txt");
                bw = new BufferedWriter(fw);
                bw.write(content);
                bw.close();
                resetImage(event);
                //RIDLER-CALVARD:
                bi = filters.kuwaharaFilter(bi, 3);
                bi = binarization.ridlerCalvardBinarize(bi);
                result = compare3();
                content = Double.toString(result);
                fw = new FileWriter(files[i] + "RidlerKuwahara.txt");
                bw = new BufferedWriter(fw);
                bw.write(content);
                bw.close();
                resetImage(event);
                //PUN:
                bi = filters.kuwaharaFilter(bi, 3);
                bi = binarization.punBinarize(bi);
                result = compare3();
                content = Double.toString(result);
                fw = new FileWriter(files[i] + "PunKuwahara.txt");
                bw = new BufferedWriter(fw);
                bw.write(content);
                bw.close();
                resetImage(event);
                //KAPUR-SAHOO-WONG:
                bi = filters.kuwaharaFilter(bi, 3);
                bi = binarization.kswBinarize(bi);
                result = compare3();
                content = Double.toString(result);
                fw = new FileWriter(files[i] + "KapurKuwahara.txt");
                bw = new BufferedWriter(fw);
                bw.write(content);
                bw.close();
                resetImage(event);
            }
            //GLOBALNE, KUWAHARA x1 5X5
            for(int i = 0; i < files.length; i++) {
                in = new File("D:\\Dokumenty\\Studia\\Zajęcia\\Wykłady\\Seminarium\\wybrane rysunki\\original\\" + files[i] + ".bmp");
                gt_file = new File("D:\\Dokumenty\\Studia\\Zajęcia\\Wykłady\\Seminarium\\wybrane rysunki\\gt\\" + files[i] + "_gt.bmp");
                bi = ImageIO.read(in);
                gt = ImageIO.read(gt_file);
                loadedbi = ImageIO.read(in);
                //OTSU:
                bi = filters.kuwaharaFilter(bi, 5);
                bi = binarization.otsuBinarize(bi);
                result = compare3();
                content = Double.toString(result);
                fw = new FileWriter(files[i] + "OtsuKuwahara5.txt");
                bw = new BufferedWriter(fw);
                bw.write(content);
                bw.close();
                resetImage(event);
                //RIDLER-CALVARD:
                bi = filters.kuwaharaFilter(bi, 5);
                bi = binarization.ridlerCalvardBinarize(bi);
                result = compare3();
                content = Double.toString(result);
                fw = new FileWriter(files[i] + "RidlerKuwahara5.txt");
                bw = new BufferedWriter(fw);
                bw.write(content);
                bw.close();
                resetImage(event);
                //PUN:
                bi = filters.kuwaharaFilter(bi, 5);
                bi = binarization.punBinarize(bi);
                result = compare3();
                content = Double.toString(result);
                fw = new FileWriter(files[i] + "PunKuwahara5.txt");
                bw = new BufferedWriter(fw);
                bw.write(content);
                bw.close();
                resetImage(event);
                //KAPUR-SAHOO-WONG:
                bi = filters.kuwaharaFilter(bi, 5);
                bi = binarization.kswBinarize(bi);
                result = compare3();
                content = Double.toString(result);
                fw = new FileWriter(files[i] + "KapurKuwahara5.txt");
                bw = new BufferedWriter(fw);
                bw.write(content);
                bw.close();
                resetImage(event);
            }

            
            
            //LOKALNE BEZ FILTRÓW:
            for(int i = 0; i < files.length; i++) {
                double minBernsenNoFilter = Double.MAX_VALUE, minWhiteRohrerNoFilter = Double.MAX_VALUE, minNiblackNoFilter = Double.MAX_VALUE, minSauvolaNoFilter = Double.MAX_VALUE, minBradleyNoFilter = Double.MAX_VALUE;
                String titleMinBernsenNoFilter = null, titleMinWhiteRohrerNoFilter = null, titleMinNiblackNoFilter = null, titleMinSauvolaNoFilter = null, titleMinBradleyNoFilter = null;
                in = new File("D:\\Dokumenty\\Studia\\Zajęcia\\Wykłady\\Seminarium\\wybrane rysunki\\original\\" + files[i] + ".bmp");
                gt_file = new File("D:\\Dokumenty\\Studia\\Zajęcia\\Wykłady\\Seminarium\\wybrane rysunki\\gt\\" + files[i] + "_gt.bmp");
                bi = ImageIO.read(in);
                gt = ImageIO.read(gt_file);
                loadedbi = ImageIO.read(in);
//                //BERNSEN:
//                //9x9, 15x15,25x25
//                for(int s = 0; s < sides.length; s++) {
//                    int[] epsilons = new int[]{30, 50, 70};
//                    //bez epsilonu
//                    bi = binarization.bernsenBinarize(bi, sides[s]);
//                    result = compare3();
//                    if(result < minBernsenNoFilter) {
//                        minBernsenNoFilter = result;
//                        titleMinBernsenNoFilter = files[i] + "Bernsen" + sides[s] + "NoFilter.txt";
//                    }
//                    resetImage(event);
//                    //epsilons 30, 50, 70:, thresh=127
//                    for(int eps = 0; eps < epsilons.length; eps++) {
//                        bi = binarization.bernsenBinarize2(bi, sides[s], 127, epsilons[eps]);
//                        result = compare3();
//                        if(result < minBernsenNoFilter) {
//                            minBernsenNoFilter = result;
//                            titleMinBernsenNoFilter = files[i] + "Bernsen" + sides[s] + "NoFilter.txt";
//                        }
//                        resetImage(event);
//                    }
//                }
//                fw = new FileWriter(titleMinBernsenNoFilter);
//                bw = new BufferedWriter(fw);
//                bw.write(Double.toString(minBernsenNoFilter));
//                bw.close();
//                //WHITE-ROHRER:
//                //9x9, 15x15,25x25
//                for(int s = 0; s < sides.length; s++) {
//                    double[] ks = new double[]{1.2, 1.5, 2.0, 2.3};
//                    String[] kss = new String[]{"12", "15", "20", "23"};
//                    //k=1.2, 1.5, 2.0, 2.3
//                    for(int k = 0; k < ks.length; k++) {
//                        bi = binarization.whiteRohrerBinarize(bi, sides[s], ks[k]);
//                        result = compare3();
//                        if(result < minWhiteRohrerNoFilter) {
//                            minWhiteRohrerNoFilter = result;
//                            titleMinWhiteRohrerNoFilter = files[i] + "WhiteRohrer" + sides[s] + "K" + kss[k] + "NoFilter.txt";
//                        }
//                        resetImage(event);
//                    }
//                }
//                fw = new FileWriter(titleMinWhiteRohrerNoFilter);
//                bw = new BufferedWriter(fw);
//                bw.write(Double.toString(minWhiteRohrerNoFilter));
//                bw.close();
//                //NIBLACK:
//                 //9x9, 15x15,25x25
//                for(int s = 0; s < sides.length; s++) {
//                    double[] ks = new double[]{-0.5, -1.0, -1.5};
//                    String[] kss = new String[]{"-05", "-10", "-15"};
//                    //k=-0.5, -1.0, -1.5
//                    for(int k = 0; k < ks.length; k++) {
//                        bi = binarization.niblackBinarize(bi, sides[s], ks[k]);
//                        result = compare3();
//                        if(result < minNiblackNoFilter) {
//                            minNiblackNoFilter = result;
//                            titleMinNiblackNoFilter = files[i] + "Niblack" + sides[s] + "K" + kss[k] + "NoFilter.txt";
//                        }
//                        resetImage(event);
//                    }
//                }
//                fw = new FileWriter(titleMinNiblackNoFilter);
//                bw = new BufferedWriter(fw);
//                bw.write(Double.toString(minNiblackNoFilter));
//                bw.close();
//                //SAUVOLA:
//                //9x9, 15x15,25x25
//                for(int s = 0; s < sides.length; s++) {
//                    double[] ks = new double[]{0.3, 0.5, 0.7};
//                    String[] kss = new String[]{"03", "05", "07"};
//                    //k=0.3, 0.5, 0.7
//                    for(int k = 0; k < ks.length; k++) {
//                        bi = binarization.sauvolaBinarize(bi, sides[s], ks[k], 128);
//                        result = compare3();
//                        if(result < minSauvolaNoFilter) {
//                            minSauvolaNoFilter = result;
//                            titleMinSauvolaNoFilter = files[i] + "Sauvola" + sides[s] + "K" + kss[k] + "NoFilter.txt";
//                        }
//                        resetImage(event);
//                    }
//                }
//                fw = new FileWriter(titleMinSauvolaNoFilter);
//                bw = new BufferedWriter(fw);
//                bw.write(Double.toString(minSauvolaNoFilter));
//                bw.close();
                //BRADLEY:
                //s=9, 15, 25
                int sides2[] = new int[]{9, 15, 25, 50, 100};
                for(int s = 0; s < sides2.length; s++) {
                    int[] ts = new int[]{10, 15, 20};
                    String[] tss = new String[]{"10", "15", "20"};
                    //t=10, 15, 20
                    for(int t = 0; t < ts.length; t++) {
                        bi = binarization.bradleyBinarize(bi, sides2[s], ts[t]);
                        result = compare3();
                        if(result < minBradleyNoFilter) {
                            minBradleyNoFilter = result;
                            titleMinBradleyNoFilter = files[i] + "BradleyS" + sides2[s] + "T" + tss[t] + "NoFilter.txt";
                        }
                        resetImage(event);
                    }
                }
                fw = new FileWriter(titleMinBradleyNoFilter);
                bw = new BufferedWriter(fw);
                bw.write(Double.toString(minBradleyNoFilter));
                bw.close();
            }
//            
            //LOKALNE gauss1, gauss2, gauss3:
            for(int i = 0; i < files.length; i++) {
                double[] minBernsenGauss = new double[]{Double.MAX_VALUE,Double.MAX_VALUE,Double.MAX_VALUE}, minWhiteRohrerGauss = new double[]{Double.MAX_VALUE,Double.MAX_VALUE,Double.MAX_VALUE}, minNiblackGauss = new double[]{Double.MAX_VALUE,Double.MAX_VALUE,Double.MAX_VALUE}, minSauvolaGauss = new double[]{Double.MAX_VALUE,Double.MAX_VALUE,Double.MAX_VALUE}, minBradleyGauss = new double[]{Double.MAX_VALUE,Double.MAX_VALUE,Double.MAX_VALUE};
                String[] titleMinBernsenGauss = new String[]{null,null,null}, titleMinWhiteRohrerGauss = new String[]{null,null,null}, titleMinNiblackGauss = new String[]{null,null,null}, titleMinSauvolaGauss = new String[]{null,null,null}, titleMinBradleyGauss = new String[]{null,null,null};
                in = new File("D:\\Dokumenty\\Studia\\Zajęcia\\Wykłady\\Seminarium\\wybrane rysunki\\original\\" + files[i] + ".bmp");
                gt_file = new File("D:\\Dokumenty\\Studia\\Zajęcia\\Wykłady\\Seminarium\\wybrane rysunki\\gt\\" + files[i] + "_gt.bmp");
                bi = ImageIO.read(in);
                gt = ImageIO.read(gt_file);
                loadedbi = ImageIO.read(in);
                
                for(int g = 0; g < gausses.length; g++) {
                    int gg = g+1;
//                    //BERNSEN:
//                    //9x9, 15x15,25x25
//                    for(int s = 0; s < sides.length; s++) {
//                        int[] epsilons = new int[]{30, 50, 70};
//                        //bez epsilonu
//                        bi = filters.filter(bi, gausses[g]);
//                        bi = binarization.bernsenBinarize(bi, sides[s]);
//                        result = compare3();
//                        if(result < minBernsenGauss[g]) {
//                            minBernsenGauss[g] = result;
//                            titleMinBernsenGauss[g] = files[i] + "Bernsen" + sides[s] + "Gauss" + gg + ".txt";
//                        }
//                        resetImage(event);
//                        //epsilons 30, 50, 70:, thresh=127
//                        for(int eps = 0; eps < epsilons.length; eps++) {
//                            bi = filters.filter(bi, gausses[g]);
//                            bi = binarization.bernsenBinarize2(bi, sides[s], 127, epsilons[eps]);
//                            result = compare3();
//                            if(result < minBernsenGauss[g]) {
//                                minBernsenGauss[g] = result;
//                                titleMinBernsenGauss[g] = files[i] + "Bernsen" + sides[s] + "Gauss" + gg + ".txt";
//                            }
//                            resetImage(event);
//                        }
//                    }
//                    fw = new FileWriter(titleMinBernsenGauss[g]);
//                    bw = new BufferedWriter(fw);
//                    bw.write(Double.toString(minBernsenGauss[g]));
//                    bw.close();
//                    //WHITE-ROHRER:
//                    //9x9, 15x15,25x25
//                    for(int s = 0; s < sides.length; s++) {
//                        double[] ks = new double[]{1.2, 1.5, 2.0, 2.3};
//                        String[] kss = new String[]{"12", "15", "20", "23"};
//                        //k=1.2, 1.5, 2.0, 2.3
//                        for(int k = 0; k < ks.length; k++) {
//                            bi = filters.filter(bi, gausses[g]);
//                            bi = binarization.whiteRohrerBinarize(bi, sides[s], ks[k]);
//                            result = compare3();
//                            if(result < minWhiteRohrerGauss[g]) {
//                                minWhiteRohrerGauss[g] = result;
//                                titleMinWhiteRohrerGauss[g] = files[i] + "WhiteRohrer" + sides[s] + "K" + kss[k] + "Gauss" + gg + ".txt";
//                            }
//                            resetImage(event);
//                        }
//                    }
//                    fw = new FileWriter(titleMinWhiteRohrerGauss[g]);
//                    bw = new BufferedWriter(fw);
//                    bw.write(Double.toString(minWhiteRohrerGauss[g]));
//                    bw.close();
//                    //NIBLACK:
//                     //9x9, 15x15,25x25
//                    for(int s = 0; s < sides.length; s++) {
//                        double[] ks = new double[]{-0.5, -1.0, -1.5};
//                        String[] kss = new String[]{"-05", "-10", "-15"};
//                        //k=-0.5, -1.0, -1.5
//                        for(int k = 0; k < ks.length; k++) {
//                            bi = filters.filter(bi, gausses[g]);
//                            bi = binarization.niblackBinarize(bi, sides[s], ks[k]);
//                            result = compare3();
//                            if(result < minNiblackGauss[g]) {
//                                minNiblackGauss[g] = result;
//                                titleMinNiblackGauss[g] = files[i] + "Niblack" + sides[s] + "K" + kss[k] + "Gauss" + gg + ".txt";
//                            }
//                            resetImage(event);
//                        }
//                    }
//                    fw = new FileWriter(titleMinNiblackGauss[g]);
//                    bw = new BufferedWriter(fw);
//                    bw.write(Double.toString(minNiblackGauss[g]));
//                    bw.close();
//                    //SAUVOLA:
//                    //9x9, 15x15,25x25
//                    for(int s = 0; s < sides.length; s++) {
//                        double[] ks = new double[]{0.3, 0.5, 0.7};
//                        String[] kss = new String[]{"03", "05", "07"};
//                        //k=0.3, 0.5, 0.7
//                        for(int k = 0; k < ks.length; k++) {
//                            bi = filters.filter(bi, gausses[g]);
//                            bi = binarization.sauvolaBinarize(bi, sides[s], ks[k], 128);
//                            result = compare3();
//                            if(result < minSauvolaGauss[g]) {
//                                minSauvolaGauss[g] = result;
//                                titleMinSauvolaGauss[g] = files[i] + "Sauvola" + sides[s] + "K" + kss[k] + "Gauss" + gg + ".txt";
//                            }
//                            resetImage(event);
//                        }
//                    }
//                    fw = new FileWriter(titleMinSauvolaGauss[g]);
//                    bw = new BufferedWriter(fw);
//                    bw.write(Double.toString(minSauvolaGauss[g]));
//                    bw.close();
                    //BRADLEY:
                    //s=9, 15, 25
                    int sides2[] = new int[]{9, 15, 25, 50, 100};
                    for(int s = 0; s < sides2.length; s++) {
                        int[] ts = new int[]{10, 15, 20};
                        String[] tss = new String[]{"10", "15", "20"};
                        //t=10, 15, 20
                        for(int t = 0; t < ts.length; t++) {
                            bi = filters.filter(bi, gausses[g]);
                            bi = binarization.bradleyBinarize(bi, sides2[s], ts[t]);
                            result = compare3();
                            if(result < minBradleyGauss[g]) {
                                minBradleyGauss[g] = result;
                                titleMinBradleyGauss[g] = files[i] + "BradleyS" + sides2[s] + "T" + tss[t] + "Gauss" + gg + ".txt";
                            }
                            resetImage(event);
                        }
                    }
                    fw = new FileWriter(titleMinBradleyGauss[g]);
                    bw = new BufferedWriter(fw);
                    bw.write(Double.toString(minBradleyGauss[g]));
                    bw.close();
                }
            }
//            
            //LOKALNE MEDIANAA 3X3:
            for(int i = 0; i < files.length; i++) {
                double minBernsenMedian = Double.MAX_VALUE, minWhiteRohrerMedian = Double.MAX_VALUE, minNiblackMedian = Double.MAX_VALUE, minSauvolaMedian = Double.MAX_VALUE, minBradleyMedian = Double.MAX_VALUE;
                String titleMinBernsenMedian = null, titleMinWhiteRohrerMedian = null, titleMinNiblackMedian = null, titleMinSauvolaMedian = null, titleMinBradleyMedian = null;
                in = new File("D:\\Dokumenty\\Studia\\Zajęcia\\Wykłady\\Seminarium\\wybrane rysunki\\original\\" + files[i] + ".bmp");
                gt_file = new File("D:\\Dokumenty\\Studia\\Zajęcia\\Wykłady\\Seminarium\\wybrane rysunki\\gt\\" + files[i] + "_gt.bmp");
                bi = ImageIO.read(in);
                gt = ImageIO.read(gt_file);
                loadedbi = ImageIO.read(in);
//                //BERNSEN:
//                //9x9, 15x15,25x25
//                for(int s = 0; s < sides.length; s++) {
//                    int[] epsilons = new int[]{30, 50, 70};
//                    //bez epsilonu
//                    bi = filters.medianFilter(bi, 3);
//                    bi = binarization.bernsenBinarize(bi, sides[s]);
//                    result = compare3();
//                    if(result < minBernsenMedian) {
//                        minBernsenMedian = result;
//                        titleMinBernsenMedian = files[i] + "Bernsen" + sides[s] + "Median.txt";
//                    }
//                    resetImage(event);
//                    //epsilons 30, 50, 70:, thresh=127
//                    for(int eps = 0; eps < epsilons.length; eps++) {
//                        bi = filters.medianFilter(bi, 3);
//                        bi = binarization.bernsenBinarize2(bi, sides[s], 127, epsilons[eps]);
//                        result = compare3();
//                        if(result < minBernsenMedian) {
//                            minBernsenMedian = result;
//                            titleMinBernsenMedian = files[i] + "Bernsen" + sides[s] + "Median.txt";
//                        }
//                        resetImage(event);
//                    }
//                }
//                fw = new FileWriter(titleMinBernsenMedian);
//                bw = new BufferedWriter(fw);
//                bw.write(Double.toString(minBernsenMedian));
//                bw.close();
//                //WHITE-ROHRER:
//                //9x9, 15x15,25x25
//                for(int s = 0; s < sides.length; s++) {
//                    double[] ks = new double[]{1.2, 1.5, 2.0, 2.3};
//                    String[] kss = new String[]{"12", "15", "20", "23"};
//                    //k=1.2, 1.5, 2.0, 2.3
//                    for(int k = 0; k < ks.length; k++) {
//                        bi = filters.medianFilter(bi, 3);
//                        bi = binarization.whiteRohrerBinarize(bi, sides[s], ks[k]);
//                        result = compare3();
//                        if(result < minWhiteRohrerMedian) {
//                            minWhiteRohrerMedian = result;
//                            titleMinWhiteRohrerMedian = files[i] + "WhiteRohrer" + sides[s] + "K" + kss[k] + "Median.txt";
//                        }
//                        resetImage(event);
//                    }
//                }
//                fw = new FileWriter(titleMinWhiteRohrerMedian);
//                bw = new BufferedWriter(fw);
//                bw.write(Double.toString(minWhiteRohrerMedian));
//                bw.close();
//                //NIBLACK:
//                 //9x9, 15x15,25x25
//                for(int s = 0; s < sides.length; s++) {
//                    double[] ks = new double[]{-0.5, -1.0, -1.5};
//                    String[] kss = new String[]{"-05", "-10", "-15"};
//                    //k=-0.5, -1.0, -1.5
//                    for(int k = 0; k < ks.length; k++) {
//                        bi = filters.medianFilter(bi, 3);
//                        bi = binarization.niblackBinarize(bi, sides[s], ks[k]);
//                        result = compare3();
//                        if(result < minNiblackMedian) {
//                            minNiblackMedian = result;
//                            titleMinNiblackMedian = files[i] + "Niblack" + sides[s] + "K" + kss[k] + "Median.txt";
//                        }
//                        resetImage(event);
//                    }
//                }
//                fw = new FileWriter(titleMinNiblackMedian);
//                bw = new BufferedWriter(fw);
//                bw.write(Double.toString(minNiblackMedian));
//                bw.close();
//                //SAUVOLA:
//                //9x9, 15x15,25x25
//                for(int s = 0; s < sides.length; s++) {
//                    double[] ks = new double[]{0.3, 0.5, 0.7};
//                    String[] kss = new String[]{"03", "05", "07"};
//                    //k=0.3, 0.5, 0.7
//                    for(int k = 0; k < ks.length; k++) {
//                        bi = filters.medianFilter(bi, 3);
//                        bi = binarization.sauvolaBinarize(bi, sides[s], ks[k], 128);
//                        result = compare3();
//                        if(result < minSauvolaMedian) {
//                            minSauvolaMedian = result;
//                            titleMinSauvolaMedian = files[i] + "Sauvola" + sides[s] + "K" + kss[k] + "Median.txt";
//                        }
//                        resetImage(event);
//                    }
//                }
//                fw = new FileWriter(titleMinSauvolaMedian);
//                bw = new BufferedWriter(fw);
//                bw.write(Double.toString(minSauvolaMedian));
//                bw.close();
                //BRADLEY:
                //s=9, 15, 25
                int sides2[] = new int[]{9, 15, 25, 50, 100};
                for(int s = 0; s < sides2.length; s++) {
                    int[] ts = new int[]{10, 15, 20};
                    String[] tss = new String[]{"10", "15", "20"};
                    //t=10, 15, 20
                    for(int t = 0; t < ts.length; t++) {
                        bi = filters.medianFilter(bi, 3);
                        bi = binarization.bradleyBinarize(bi, sides2[s], ts[t]);
                        result = compare3();
                        if(result < minBradleyMedian) {
                            minBradleyMedian = result;
                            titleMinBradleyMedian = files[i] + "BradleyS" + sides2[s] + "T" + tss[t] + "Median.txt";
                        }
                        resetImage(event);
                    }
                }
                fw = new FileWriter(titleMinBradleyMedian);
                bw = new BufferedWriter(fw);
                bw.write(Double.toString(minBradleyMedian));
                bw.close();
            }
            
            
            //LOKALNE MEDIANAA 5X5:
            for(int i = 0; i < files.length; i++) {
                double minBernsenMedian = Double.MAX_VALUE, minWhiteRohrerMedian = Double.MAX_VALUE, minNiblackMedian = Double.MAX_VALUE, minSauvolaMedian = Double.MAX_VALUE, minBradleyMedian = Double.MAX_VALUE;
                String titleMinBernsenMedian = null, titleMinWhiteRohrerMedian = null, titleMinNiblackMedian = null, titleMinSauvolaMedian = null, titleMinBradleyMedian = null;
                in = new File("D:\\Dokumenty\\Studia\\Zajęcia\\Wykłady\\Seminarium\\wybrane rysunki\\original\\" + files[i] + ".bmp");
                gt_file = new File("D:\\Dokumenty\\Studia\\Zajęcia\\Wykłady\\Seminarium\\wybrane rysunki\\gt\\" + files[i] + "_gt.bmp");
                bi = ImageIO.read(in);
                gt = ImageIO.read(gt_file);
                loadedbi = ImageIO.read(in);
//                //BERNSEN:
//                //9x9, 15x15,25x25
//                for(int s = 0; s < sides.length; s++) {
//                    int[] epsilons = new int[]{30, 50, 70};
//                    //bez epsilonu
//                    bi = filters.medianFilter(bi, 5);
//                    bi = binarization.bernsenBinarize(bi, sides[s]);
//                    result = compare3();
//                    if(result < minBernsenMedian) {
//                        minBernsenMedian = result;
//                        titleMinBernsenMedian = files[i] + "Bernsen" + sides[s] + "Median5.txt";
//                    }
//                    resetImage(event);
//                    //epsilons 30, 50, 70:, thresh=127
//                    for(int eps = 0; eps < epsilons.length; eps++) {
//                        bi = filters.medianFilter(bi, 5);
//                        bi = binarization.bernsenBinarize2(bi, sides[s], 127, epsilons[eps]);
//                        result = compare3();
//                        if(result < minBernsenMedian) {
//                            minBernsenMedian = result;
//                            titleMinBernsenMedian = files[i] + "Bernsen" + sides[s] + "Median5.txt";
//                        }
//                        resetImage(event);
//                    }
//                }
//                fw = new FileWriter(titleMinBernsenMedian);
//                bw = new BufferedWriter(fw);
//                bw.write(Double.toString(minBernsenMedian));
//                bw.close();
//                //WHITE-ROHRER:
//                //9x9, 15x15,25x25
//                for(int s = 0; s < sides.length; s++) {
//                    double[] ks = new double[]{1.2, 1.5, 2.0, 2.3};
//                    String[] kss = new String[]{"12", "15", "20", "23"};
//                    //k=1.2, 1.5, 2.0, 2.3
//                    for(int k = 0; k < ks.length; k++) {
//                        bi = filters.medianFilter(bi, 5);
//                        bi = binarization.whiteRohrerBinarize(bi, sides[s], ks[k]);
//                        result = compare3();
//                        if(result < minWhiteRohrerMedian) {
//                            minWhiteRohrerMedian = result;
//                            titleMinWhiteRohrerMedian = files[i] + "WhiteRohrer" + sides[s] + "K" + kss[k] + "Median5.txt";
//                        }
//                        resetImage(event);
//                    }
//                }
//                fw = new FileWriter(titleMinWhiteRohrerMedian);
//                bw = new BufferedWriter(fw);
//                bw.write(Double.toString(minWhiteRohrerMedian));
//                bw.close();
//                //NIBLACK:
//                 //9x9, 15x15,25x25
//                for(int s = 0; s < sides.length; s++) {
//                    double[] ks = new double[]{-0.5, -1.0, -1.5};
//                    String[] kss = new String[]{"-05", "-10", "-15"};
//                    //k=-0.5, -1.0, -1.5
//                    for(int k = 0; k < ks.length; k++) {
//                        bi = filters.medianFilter(bi, 5);
//                        bi = binarization.niblackBinarize(bi, sides[s], ks[k]);
//                        result = compare3();
//                        if(result < minNiblackMedian) {
//                            minNiblackMedian = result;
//                            titleMinNiblackMedian = files[i] + "Niblack" + sides[s] + "K" + kss[k] + "Median5.txt";
//                        }
//                        resetImage(event);
//                    }
//                }
//                fw = new FileWriter(titleMinNiblackMedian);
//                bw = new BufferedWriter(fw);
//                bw.write(Double.toString(minNiblackMedian));
//                bw.close();
//                //SAUVOLA:
//                //9x9, 15x15,25x25
//                for(int s = 0; s < sides.length; s++) {
//                    double[] ks = new double[]{0.3, 0.5, 0.7};
//                    String[] kss = new String[]{"03", "05", "07"};
//                    //k=0.3, 0.5, 0.7
//                    for(int k = 0; k < ks.length; k++) {
//                        bi = filters.medianFilter(bi, 5);
//                        bi = binarization.sauvolaBinarize(bi, sides[s], ks[k], 128);
//                        result = compare3();
//                        if(result < minSauvolaMedian) {
//                            minSauvolaMedian = result;
//                            titleMinSauvolaMedian = files[i] + "Sauvola" + sides[s] + "K" + kss[k] + "Median5.txt";
//                        }
//                        resetImage(event);
//                    }
//                }
//                fw = new FileWriter(titleMinSauvolaMedian);
//                bw = new BufferedWriter(fw);
//                bw.write(Double.toString(minSauvolaMedian));
//                bw.close();
                //BRADLEY:
                //s=9, 15, 25
                int sides2[] = new int[]{9, 15, 25, 50, 100};
                for(int s = 0; s < sides2.length; s++) {
                    int[] ts = new int[]{10, 15, 20};
                    String[] tss = new String[]{"10", "15", "20"};
                    //t=10, 15, 20
                    for(int t = 0; t < ts.length; t++) {
                        bi = filters.medianFilter(bi, 5);
                        bi = binarization.bradleyBinarize(bi, sides2[s], ts[t]);
                        result = compare3();
                        if(result < minBradleyMedian) {
                            minBradleyMedian = result;
                            titleMinBradleyMedian = files[i] + "BradleyS" + sides2[s] + "T" + tss[t] + "Median5.txt";
                        }
                        resetImage(event);
                    }
                }
                fw = new FileWriter(titleMinBradleyMedian);
                bw = new BufferedWriter(fw);
                bw.write(Double.toString(minBradleyMedian));
                bw.close();
            }
            
            
            //GLOBALNE, ANIZOTROPIC DIFF 5 iteracji, threshold 15, sigma 0
            for(int i = 0; i < files.length; i++) {
                double minBernsenMedian = Double.MAX_VALUE, minWhiteRohrerMedian = Double.MAX_VALUE, minNiblackMedian = Double.MAX_VALUE, minSauvolaMedian = Double.MAX_VALUE, minBradleyMedian = Double.MAX_VALUE;
                String titleMinBernsenMedian = null, titleMinWhiteRohrerMedian = null, titleMinNiblackMedian = null, titleMinSauvolaMedian = null, titleMinBradleyMedian = null;
                in = new File("D:\\Dokumenty\\Studia\\Zajęcia\\Wykłady\\Seminarium\\wybrane rysunki\\original\\" + files[i] + ".bmp");
                gt_file = new File("D:\\Dokumenty\\Studia\\Zajęcia\\Wykłady\\Seminarium\\wybrane rysunki\\gt\\" + files[i] + "_gt.bmp");
                bi = ImageIO.read(in);
                gt = ImageIO.read(gt_file);
                loadedbi = ImageIO.read(in);
                //BERNSEN:
                //9x9, 15x15,25x25
//                for(int s = 0; s < sides.length; s++) {
//                    int[] epsilons = new int[]{30, 50, 70};
//                    //bez epsilonu
//                    bi = adf.filter(bi, 5, 1, 15, 0.0);
//                    bi = binarization.bernsenBinarize(bi, sides[s]);
//                    result = compare3();
//                    if(result < minBernsenMedian) {
//                        minBernsenMedian = result;
//                        titleMinBernsenMedian = files[i] + "Bernsen" + sides[s] + "Anizotropic5.txt";
//                    }
//                    resetImage(event);
//                    //epsilons 30, 50, 70:, thresh=127
//                    for(int eps = 0; eps < epsilons.length; eps++) {
//                        bi = adf.filter(bi, 5, 1, 15, 0.0);
//                        bi = binarization.bernsenBinarize2(bi, sides[s], 127, epsilons[eps]);
//                        result = compare3();
//                        if(result < minBernsenMedian) {
//                            minBernsenMedian = result;
//                            titleMinBernsenMedian = files[i] + "Bernsen" + sides[s] + "Anizotropic5.txt";
//                        }
//                        resetImage(event);
//                    }
//                }
//                fw = new FileWriter(titleMinBernsenMedian);
//                bw = new BufferedWriter(fw);
//                bw.write(Double.toString(minBernsenMedian));
//                bw.close();
//                //WHITE-ROHRER:
//                //9x9, 15x15,25x25
//                for(int s = 0; s < sides.length; s++) {
//                    double[] ks = new double[]{1.2, 1.5, 2.0, 2.3};
//                    String[] kss = new String[]{"12", "15", "20", "23"};
//                    //k=1.2, 1.5, 2.0, 2.3
//                    for(int k = 0; k < ks.length; k++) {
//                        bi = adf.filter(bi, 5, 1, 15, 0.0);
//                        bi = binarization.whiteRohrerBinarize(bi, sides[s], ks[k]);
//                        result = compare3();
//                        if(result < minWhiteRohrerMedian) {
//                            minWhiteRohrerMedian = result;
//                            titleMinWhiteRohrerMedian = files[i] + "WhiteRohrer" + sides[s] + "K" + kss[k] + "Anizotropic5.txt";
//                        }
//                        resetImage(event);
//                    }
//                }
//                fw = new FileWriter(titleMinWhiteRohrerMedian);
//                bw = new BufferedWriter(fw);
//                bw.write(Double.toString(minWhiteRohrerMedian));
//                bw.close();
//                //NIBLACK:
//                 //9x9, 15x15,25x25
//                for(int s = 0; s < sides.length; s++) {
//                    double[] ks = new double[]{-0.5, -1.0, -1.5};
//                    String[] kss = new String[]{"-05", "-10", "-15"};
//                    //k=-0.5, -1.0, -1.5
//                    for(int k = 0; k < ks.length; k++) {
//                        bi = adf.filter(bi, 5, 1, 15, 0.0);
//                        bi = binarization.niblackBinarize(bi, sides[s], ks[k]);
//                        result = compare3();
//                        if(result < minNiblackMedian) {
//                            minNiblackMedian = result;
//                            titleMinNiblackMedian = files[i] + "Niblack" + sides[s] + "K" + kss[k] + "Anizotropic5.txt";
//                        }
//                        resetImage(event);
//                    }
//                }
//                fw = new FileWriter(titleMinNiblackMedian);
//                bw = new BufferedWriter(fw);
//                bw.write(Double.toString(minNiblackMedian));
//                bw.close();
//                //SAUVOLA:
//                //9x9, 15x15,25x25
//                for(int s = 0; s < sides.length; s++) {
//                    double[] ks = new double[]{0.3, 0.5, 0.7};
//                    String[] kss = new String[]{"03", "05", "07"};
//                    //k=0.3, 0.5, 0.7
//                    for(int k = 0; k < ks.length; k++) {
//                        bi = adf.filter(bi, 5, 1, 15, 0.0);
//                        bi = binarization.sauvolaBinarize(bi, sides[s], ks[k], 128);
//                        result = compare3();
//                        if(result < minSauvolaMedian) {
//                            minSauvolaMedian = result;
//                            titleMinSauvolaMedian = files[i] + "Sauvola" + sides[s] + "K" + kss[k] + "Anizotropic5.txt";
//                        }
//                        resetImage(event);
//                    }
//                }
//                fw = new FileWriter(titleMinSauvolaMedian);
//                bw = new BufferedWriter(fw);
//                bw.write(Double.toString(minSauvolaMedian));
//                bw.close();
                //BRADLEY:
                //s=9, 15, 25
                int sides2[] = new int[]{9, 15, 25, 50, 100};
                for(int s = 0; s < sides2.length; s++) {
                    int[] ts = new int[]{10, 15, 20};
                    String[] tss = new String[]{"10", "15", "20"};
                    //t=10, 15, 20
                    for(int t = 0; t < ts.length; t++) {
                        bi = adf.filter(bi, 5, 1, 15, 0.0);
                        bi = binarization.bradleyBinarize(bi, sides2[s], ts[t]);
                        result = compare3();
                        if(result < minBradleyMedian) {
                            minBradleyMedian = result;
                            titleMinBradleyMedian = files[i] + "BradleyS" + sides2[s] + "T" + tss[t] + "Anizotropic5.txt";
                        }
                        resetImage(event);
                    }
                }
                fw = new FileWriter(titleMinBradleyMedian);
                bw = new BufferedWriter(fw);
                bw.write(Double.toString(minBradleyMedian));
                bw.close();
            }
            
            
            //GLOBALNE, ANIZOTROPIC DIFF 10 iteracji, threshold 15, sigma 0
            for(int i = 0; i < files.length; i++) {
                double minBernsenMedian = Double.MAX_VALUE, minWhiteRohrerMedian = Double.MAX_VALUE, minNiblackMedian = Double.MAX_VALUE, minSauvolaMedian = Double.MAX_VALUE, minBradleyMedian = Double.MAX_VALUE;
                String titleMinBernsenMedian = null, titleMinWhiteRohrerMedian = null, titleMinNiblackMedian = null, titleMinSauvolaMedian = null, titleMinBradleyMedian = null;
                in = new File("D:\\Dokumenty\\Studia\\Zajęcia\\Wykłady\\Seminarium\\wybrane rysunki\\original\\" + files[i] + ".bmp");
                gt_file = new File("D:\\Dokumenty\\Studia\\Zajęcia\\Wykłady\\Seminarium\\wybrane rysunki\\gt\\" + files[i] + "_gt.bmp");
                bi = ImageIO.read(in);
                gt = ImageIO.read(gt_file);
                loadedbi = ImageIO.read(in);
//                //BERNSEN:
//                //9x9, 15x15,25x25
//                for(int s = 0; s < sides.length; s++) {
//                    int[] epsilons = new int[]{30, 50, 70};
//                    //bez epsilonu
//                    bi = adf.filter(bi, 10, 1, 15, 0.0);
//                    bi = binarization.bernsenBinarize(bi, sides[s]);
//                    result = compare3();
//                    if(result < minBernsenMedian) {
//                        minBernsenMedian = result;
//                        titleMinBernsenMedian = files[i] + "Bernsen" + sides[s] + "Anizotropic10.txt";
//                    }
//                    resetImage(event);
//                    //epsilons 30, 50, 70:, thresh=127
//                    for(int eps = 0; eps < epsilons.length; eps++) {
//                        bi = adf.filter(bi, 10, 1, 15, 0.0);
//                        bi = binarization.bernsenBinarize2(bi, sides[s], 127, epsilons[eps]);
//                        result = compare3();
//                        if(result < minBernsenMedian) {
//                            minBernsenMedian = result;
//                            titleMinBernsenMedian = files[i] + "Bernsen" + sides[s] + "Anizotropic10.txt";
//                        }
//                        resetImage(event);
//                    }
//                }
//                fw = new FileWriter(titleMinBernsenMedian);
//                bw = new BufferedWriter(fw);
//                bw.write(Double.toString(minBernsenMedian));
//                bw.close();
//                //WHITE-ROHRER:
//                //9x9, 15x15,25x25
//                for(int s = 0; s < sides.length; s++) {
//                    double[] ks = new double[]{1.2, 1.5, 2.0, 2.3};
//                    String[] kss = new String[]{"12", "15", "20", "23"};
//                    //k=1.2, 1.5, 2.0, 2.3
//                    for(int k = 0; k < ks.length; k++) {
//                        bi = adf.filter(bi, 10, 1, 15, 0.0);
//                        bi = binarization.whiteRohrerBinarize(bi, sides[s], ks[k]);
//                        result = compare3();
//                        if(result < minWhiteRohrerMedian) {
//                            minWhiteRohrerMedian = result;
//                            titleMinWhiteRohrerMedian = files[i] + "WhiteRohrer" + sides[s] + "K" + kss[k] + "Anizotropic10.txt";
//                        }
//                        resetImage(event);
//                    }
//                }
//                fw = new FileWriter(titleMinWhiteRohrerMedian);
//                bw = new BufferedWriter(fw);
//                bw.write(Double.toString(minWhiteRohrerMedian));
//                bw.close();
//                //NIBLACK:
//                 //9x9, 15x15,25x25
//                for(int s = 0; s < sides.length; s++) {
//                    double[] ks = new double[]{-0.5, -1.0, -1.5};
//                    String[] kss = new String[]{"-05", "-10", "-15"};
//                    //k=-0.5, -1.0, -1.5
//                    for(int k = 0; k < ks.length; k++) {
//                        bi = adf.filter(bi, 10, 1, 15, 0.0);
//                        bi = binarization.niblackBinarize(bi, sides[s], ks[k]);
//                        result = compare3();
//                        if(result < minNiblackMedian) {
//                            minNiblackMedian = result;
//                            titleMinNiblackMedian = files[i] + "Niblack" + sides[s] + "K" + kss[k] + "Anizotropic10.txt";
//                        }
//                        resetImage(event);
//                    }
//                }
//                fw = new FileWriter(titleMinNiblackMedian);
//                bw = new BufferedWriter(fw);
//                bw.write(Double.toString(minNiblackMedian));
//                bw.close();
//                //SAUVOLA:
//                //9x9, 15x15,25x25
//                for(int s = 0; s < sides.length; s++) {
//                    double[] ks = new double[]{0.3, 0.5, 0.7};
//                    String[] kss = new String[]{"03", "05", "07"};
//                    //k=0.3, 0.5, 0.7
//                    for(int k = 0; k < ks.length; k++) {
//                        bi = adf.filter(bi, 10, 1, 15, 0.0);
//                        bi = binarization.sauvolaBinarize(bi, sides[s], ks[k], 128);
//                        result = compare3();
//                        if(result < minSauvolaMedian) {
//                            minSauvolaMedian = result;
//                            titleMinSauvolaMedian = files[i] + "Sauvola" + sides[s] + "K" + kss[k] + "Anizotropic10.txt";
//                        }
//                        resetImage(event);
//                    }
//                }
//                fw = new FileWriter(titleMinSauvolaMedian);
//                bw = new BufferedWriter(fw);
//                bw.write(Double.toString(minSauvolaMedian));
//                bw.close();
                //BRADLEY:
                //s=9, 15, 25
                int sides2[] = new int[]{9, 15, 25, 50, 100};
                for(int s = 0; s < sides2.length; s++) {
                    int[] ts = new int[]{10, 15, 20};
                    String[] tss = new String[]{"10", "15", "20"};
                    //t=10, 15, 20
                    for(int t = 0; t < ts.length; t++) {
                        bi = adf.filter(bi, 10, 1, 15, 0.0);
                        bi = binarization.bradleyBinarize(bi, sides2[s], ts[t]);
                        result = compare3();
                        if(result < minBradleyMedian) {
                            minBradleyMedian = result;
                            titleMinBradleyMedian = files[i] + "BradleyS" + sides2[s] + "T" + tss[t] + "Anizotropic10.txt";
                        }
                        resetImage(event);
                    }
                }
                fw = new FileWriter(titleMinBradleyMedian);
                bw = new BufferedWriter(fw);
                bw.write(Double.toString(minBradleyMedian));
                bw.close();
            }
            
            
            
            //LOKALNE KUWAHARA 3X3:
            for(int i = 0; i < files.length; i++) {
                double minBernsenKuwahar = Double.MAX_VALUE, minWhiteRohrerKuwahar = Double.MAX_VALUE, minNiblackKuwahar = Double.MAX_VALUE, minSauvolaKuwahar = Double.MAX_VALUE, minBradleyKuwahar = Double.MAX_VALUE;
                String titleMinBernsenKuwahar = null, titleMinWhiteRohrerKuwahar = null, titleMinNiblackKuwahar = null, titleMinSauvolaKuwahar = null, titleMinBradleyKuwahar = null;
                in = new File("D:\\Dokumenty\\Studia\\Zajęcia\\Wykłady\\Seminarium\\wybrane rysunki\\original\\" + files[i] + ".bmp");
                gt_file = new File("D:\\Dokumenty\\Studia\\Zajęcia\\Wykłady\\Seminarium\\wybrane rysunki\\gt\\" + files[i] + "_gt.bmp");
                bi = ImageIO.read(in);
                gt = ImageIO.read(gt_file);
                loadedbi = ImageIO.read(in);
                //BERNSEN:
                //9x9, 15x15,25x25
                for(int s = 0; s < sides.length; s++) {
                    int[] epsilons = new int[]{30, 50, 70};
                    //bez epsilonu
                    bi = filters.kuwaharaFilter(bi, 3);
                    bi = binarization.bernsenBinarize(bi, sides[s]);
                    result = compare3();
                    if(result < minBernsenKuwahar) {
                        minBernsenKuwahar = result;
                        titleMinBernsenKuwahar = files[i] + "Bernsen" + sides[s] + "Kuwahara.txt";
                    }
                    resetImage(event);
                    //epsilons 30, 50, 70:, thresh=127
                    for(int eps = 0; eps < epsilons.length; eps++) {
                        bi = filters.kuwaharaFilter(bi, 3);
                        bi = binarization.bernsenBinarize2(bi, sides[s], 127, epsilons[eps]);
                        result = compare3();
                        if(result < minBernsenKuwahar) {
                            minBernsenKuwahar = result;
                            titleMinBernsenKuwahar = files[i] + "Bernsen" + sides[s] + "Kuwahara.txt";
                        }
                        resetImage(event);
                    }
                }
                fw = new FileWriter(titleMinBernsenKuwahar);
                bw = new BufferedWriter(fw);
                bw.write(Double.toString(minBernsenKuwahar));
                bw.close();
                //WHITE-ROHRER:
                //9x9, 15x15,25x25
                for(int s = 0; s < sides.length; s++) {
                    double[] ks = new double[]{1.2, 1.5, 2.0, 2.3};
                    String[] kss = new String[]{"12", "15", "20", "23"};
                    //k=1.2, 1.5, 2.0, 2.3
                    for(int k = 0; k < ks.length; k++) {
                        bi = filters.kuwaharaFilter(bi, 3);
                        bi = binarization.whiteRohrerBinarize(bi, sides[s], ks[k]);
                        result = compare3();
                        if(result < minWhiteRohrerKuwahar) {
                            minWhiteRohrerKuwahar = result;
                            titleMinWhiteRohrerKuwahar = files[i] + "WhiteRohrer" + sides[s] + "K" + kss[k] + "Kuwahara.txt";
                        }
                        resetImage(event);
                    }
                }
                fw = new FileWriter(titleMinWhiteRohrerKuwahar);
                bw = new BufferedWriter(fw);
                bw.write(Double.toString(minWhiteRohrerKuwahar));
                bw.close();
                //NIBLACK:
                 //9x9, 15x15,25x25
                for(int s = 0; s < sides.length; s++) {
                    double[] ks = new double[]{-0.5, -1.0, -1.5};
                    String[] kss = new String[]{"-05", "-10", "-15"};
                    //k=-0.5, -1.0, -1.5
                    for(int k = 0; k < ks.length; k++) {
                        bi = filters.kuwaharaFilter(bi, 3);
                        bi = binarization.niblackBinarize(bi, sides[s], ks[k]);
                        result = compare3();
                        if(result < minNiblackKuwahar) {
                            minNiblackKuwahar = result;
                            titleMinNiblackKuwahar = files[i] + "Niblack" + sides[s] + "K" + kss[k] + "Kuwahara.txt";
                        }
                        resetImage(event);
                    }
                }
                fw = new FileWriter(titleMinNiblackKuwahar);
                bw = new BufferedWriter(fw);
                bw.write(Double.toString(minNiblackKuwahar));
                bw.close();
                //SAUVOLA:
                //9x9, 15x15,25x25
                for(int s = 0; s < sides.length; s++) {
                    double[] ks = new double[]{0.3, 0.5, 0.7};
                    String[] kss = new String[]{"03", "05", "07"};
                    //k=0.3, 0.5, 0.7
                    for(int k = 0; k < ks.length; k++) {
                        bi = filters.kuwaharaFilter(bi, 3);
                        bi = binarization.sauvolaBinarize(bi, sides[s], ks[k], 128);
                        result = compare3();
                        if(result < minSauvolaKuwahar) {
                            minSauvolaKuwahar = result;
                            titleMinSauvolaKuwahar = files[i] + "Sauvola" + sides[s] + "K" + kss[k] + "Kuwahara.txt";
                        }
                        resetImage(event);
                    }
                }
                fw = new FileWriter(titleMinSauvolaKuwahar);
                bw = new BufferedWriter(fw);
                bw.write(Double.toString(minSauvolaKuwahar));
                bw.close();
                //BRADLEY:
                //s=9, 15, 25
                int sides2[] = new int[]{9, 15, 25, 50, 100};
                for(int s = 0; s < sides2.length; s++) {
                    int[] ts = new int[]{10, 15, 20};
                    String[] tss = new String[]{"10", "15", "20"};
                    //t=10, 15, 20
                    for(int t = 0; t < ts.length; t++) {
                        bi = filters.kuwaharaFilter(bi, 3);
                        bi = binarization.bradleyBinarize(bi, sides2[s], ts[t]);
                        result = compare3();
                        if(result < minBradleyKuwahar) {
                            minBradleyKuwahar = result;
                            titleMinBradleyKuwahar = files[i] + "BradleyS" + sides2[s] + "T" + tss[t] + "Kuwahara.txt";
                        }
                        resetImage(event);
                    }
                }
                fw = new FileWriter(titleMinBradleyKuwahar);
                bw = new BufferedWriter(fw);
                bw.write(Double.toString(minBradleyKuwahar));
                bw.close();
            }
            //LOKALNE KUWAHARA 5X5:
            for(int i = 0; i < files.length; i++) {
                double minBernsenKuwahar = Double.MAX_VALUE, minWhiteRohrerKuwahar = Double.MAX_VALUE, minNiblackKuwahar = Double.MAX_VALUE, minSauvolaKuwahar = Double.MAX_VALUE, minBradleyKuwahar = Double.MAX_VALUE;
                String titleMinBernsenKuwahar = null, titleMinWhiteRohrerKuwahar = null, titleMinNiblackKuwahar = null, titleMinSauvolaKuwahar = null, titleMinBradleyKuwahar = null;
                in = new File("D:\\Dokumenty\\Studia\\Zajęcia\\Wykłady\\Seminarium\\wybrane rysunki\\original\\" + files[i] + ".bmp");
                gt_file = new File("D:\\Dokumenty\\Studia\\Zajęcia\\Wykłady\\Seminarium\\wybrane rysunki\\gt\\" + files[i] + "_gt.bmp");
                bi = ImageIO.read(in);
                gt = ImageIO.read(gt_file);
                loadedbi = ImageIO.read(in);
                //BERNSEN:
                //9x9, 15x15,25x25
                for(int s = 0; s < sides.length; s++) {
                    int[] epsilons = new int[]{30, 50, 70};
                    //bez epsilonu
                    bi = filters.kuwaharaFilter(bi, 5);
                    bi = binarization.bernsenBinarize(bi, sides[s]);
                    result = compare3();
                    if(result < minBernsenKuwahar) {
                        minBernsenKuwahar = result;
                        titleMinBernsenKuwahar = files[i] + "Bernsen" + sides[s] + "Kuwahara5.txt";
                    }
                    resetImage(event);
                    //epsilons 30, 50, 70:, thresh=127
                    for(int eps = 0; eps < epsilons.length; eps++) {
                        bi = filters.kuwaharaFilter(bi, 5);
                        bi = binarization.bernsenBinarize2(bi, sides[s], 127, epsilons[eps]);
                        result = compare3();
                        if(result < minBernsenKuwahar) {
                            minBernsenKuwahar = result;
                            titleMinBernsenKuwahar = files[i] + "Bernsen" + sides[s] + "Kuwahara5.txt";
                        }
                        resetImage(event);
                    }
                }
                fw = new FileWriter(titleMinBernsenKuwahar);
                bw = new BufferedWriter(fw);
                bw.write(Double.toString(minBernsenKuwahar));
                bw.close();
                //WHITE-ROHRER:
                //9x9, 15x15,25x25
                for(int s = 0; s < sides.length; s++) {
                    double[] ks = new double[]{1.2, 1.5, 2.0, 2.3};
                    String[] kss = new String[]{"12", "15", "20", "23"};
                    //k=1.2, 1.5, 2.0, 2.3
                    for(int k = 0; k < ks.length; k++) {
                        bi = filters.kuwaharaFilter(bi, 5);
                        bi = binarization.whiteRohrerBinarize(bi, sides[s], ks[k]);
                        result = compare3();
                        if(result < minWhiteRohrerKuwahar) {
                            minWhiteRohrerKuwahar = result;
                            titleMinWhiteRohrerKuwahar = files[i] + "WhiteRohrer" + sides[s] + "K" + kss[k] + "Kuwahara5.txt";
                        }
                        resetImage(event);
                    }
                }
                fw = new FileWriter(titleMinWhiteRohrerKuwahar);
                bw = new BufferedWriter(fw);
                bw.write(Double.toString(minWhiteRohrerKuwahar));
                bw.close();
                //NIBLACK:
                 //9x9, 15x15,25x25
                for(int s = 0; s < sides.length; s++) {
                    double[] ks = new double[]{-0.5, -1.0, -1.5};
                    String[] kss = new String[]{"-05", "-10", "-15"};
                    //k=-0.5, -1.0, -1.5
                    for(int k = 0; k < ks.length; k++) {
                        bi = filters.kuwaharaFilter(bi, 5);
                        bi = binarization.niblackBinarize(bi, sides[s], ks[k]);
                        result = compare3();
                        if(result < minNiblackKuwahar) {
                            minNiblackKuwahar = result;
                            titleMinNiblackKuwahar = files[i] + "Niblack" + sides[s] + "K" + kss[k] + "Kuwahara5.txt";
                        }
                        resetImage(event);
                    }
                }
                fw = new FileWriter(titleMinNiblackKuwahar);
                bw = new BufferedWriter(fw);
                bw.write(Double.toString(minNiblackKuwahar));
                bw.close();
                //SAUVOLA:
                //9x9, 15x15,25x25
                for(int s = 0; s < sides.length; s++) {
                    double[] ks = new double[]{0.3, 0.5, 0.7};
                    String[] kss = new String[]{"03", "05", "07"};
                    //k=0.3, 0.5, 0.7
                    for(int k = 0; k < ks.length; k++) {
                        bi = filters.kuwaharaFilter(bi, 5);
                        bi = binarization.sauvolaBinarize(bi, sides[s], ks[k], 128);
                        result = compare3();
                        if(result < minSauvolaKuwahar) {
                            minSauvolaKuwahar = result;
                            titleMinSauvolaKuwahar = files[i] + "Sauvola" + sides[s] + "K" + kss[k] + "Kuwahara5.txt";
                        }
                        resetImage(event);
                    }
                }
                fw = new FileWriter(titleMinSauvolaKuwahar);
                bw = new BufferedWriter(fw);
                bw.write(Double.toString(minSauvolaKuwahar));
                bw.close();
                //BRADLEY:
                //s=9, 15, 25
                int sides2[] = new int[]{9, 15, 25, 50, 100};
                for(int s = 0; s < sides2.length; s++) {
                    int[] ts = new int[]{10, 15, 20};
                    String[] tss = new String[]{"10", "15", "20"};
                    //t=10, 15, 20
                    for(int t = 0; t < ts.length; t++) {
                        bi = filters.kuwaharaFilter(bi, 5);
                        bi = binarization.bradleyBinarize(bi, sides2[s], ts[t]);
                        result = compare3();
                        if(result < minBradleyKuwahar) {
                            minBradleyKuwahar = result;
                            titleMinBradleyKuwahar = files[i] + "BradleyS" + sides2[s] + "T" + tss[t] + "Kuwahara5.txt";
                        }
                        resetImage(event);
                    }
                }
                fw = new FileWriter(titleMinBradleyKuwahar);
                bw = new BufferedWriter(fw);
                bw.write(Double.toString(minBradleyKuwahar));
                bw.close();
            }
            
            
        } catch (Exception ex) {
            ex.printStackTrace();
            Logger.getLogger(FXMLDocumentController.class.getName()).log(Level.SEVERE, null, ex);
        } finally {
            try {
                if (bw != null)
                    bw.close();
                if (fw != null)
                    fw.close();
            } catch (IOException ex) {
                ex.printStackTrace();
            }
        }
    }
    
}

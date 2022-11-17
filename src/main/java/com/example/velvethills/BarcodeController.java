package com.example.velvethills;

import com.example.velvethills.Models.Order;
import com.itextpdf.text.BadElementException;
import com.itextpdf.text.Document;
import com.itextpdf.text.DocumentException;
import com.itextpdf.text.Image;
import com.itextpdf.text.pdf.PdfWriter;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.fxml.FXML;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.control.Button;
import javafx.scene.image.WritableImage;
import javafx.scene.paint.Color;
import javafx.stage.FileChooser;
import javafx.stage.Window;
import javafx.util.Duration;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.RenderedImage;
import java.io.*;
import java.net.URISyntaxException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.text.SimpleDateFormat;
import java.util.Calendar;

import javafx.stage.Stage;
import javafx.stage.Window;
//import javafx.embed.swing.SwingFXUtils;
import com.example.velvethills.CustomLibs.SwingFXUtils;

public class BarcodeController {
    @FXML
    private Canvas barcode;

    @FXML
    private Button downloadBarcode;

    @FXML
    private Button exitOBtn;

    private static final int CANVAS_SIZE = 150;

    private Window primaryStage;

    @FXML
    public void initialize() {
        exitOBtn.setOnMouseClicked(mouseEvent -> {
            Stage stage = (Stage) exitOBtn.getScene().getWindow();
            stage.close();
        });

        downloadBarcode.setOnMouseClicked(mouseEvent -> {
            FileChooser savefile = new FileChooser();
            savefile.setTitle("Save File");
            savefile.setInitialFileName("barcode.pdf");
            savefile.getExtensionFilters().add((new FileChooser.ExtensionFilter("PDF files (*.pdf)", "*.pdf")));

            File file = new File("src/main/shtrih-code.jpeg");

            if (file != null) {
                try {
                    WritableImage writableImage = new WritableImage(CANVAS_SIZE, CANVAS_SIZE);
                    barcode.snapshot(null, writableImage);

                    RenderedImage renderedImage = SwingFXUtils.fromFXImage(writableImage, null);
                    ImageIO.write(renderedImage, "png", file);
                } catch (IOException ex) {
                    ex.printStackTrace();
                    System.out.println("Error!");
                }
            }
            Path path1 = null;
            path1 = Paths.get("src/main/shtrih-code.jpeg");

            Window primaryStage = null;
            File file1 = savefile.showSaveDialog(primaryStage);
            Document document1 = new Document();
            try {
                PdfWriter.getInstance(document1, new FileOutputStream(String.valueOf(file1)));
            } catch (DocumentException | FileNotFoundException e) {
                throw new RuntimeException(e);
            }
            document1.open();

            Image image = null;
            try {
                image = Image.getInstance(path1.toAbsolutePath().toString());
            } catch (BadElementException e) {
                throw new RuntimeException(e);
            } catch (IOException e) {
                throw new RuntimeException(e);
            }

            try {
                document1.add(image);
            } catch (DocumentException e) {
                throw new RuntimeException(e);
            }

            document1.close();
        });
    }

    public void getData(Order order) {
        GraphicsContext gc = barcode.getGraphicsContext2D();
        gc.clearRect(0, 0, barcode.getWidth(), barcode.getHeight());
        double mm = (double) Toolkit.getDefaultToolkit().getScreenResolution() / 25.4D;
        System.out.println(mm);
        int[] palks = new int[]{5, 1, 4, 0, 9, 2, 0, 2, 0, 1, 2, 3, 4, 5, 6};
        gc.setFill(Color.BLACK);
        int x0 = 20;
        int y0 = 10;
        double heightPalks = 22.85D * mm;
        double weightOgrPalks = 0.5D * mm;
        double rasstoyanieMegdyPalk = 0.5D * mm;

        gc.fillRect((double) x0, (double) y0, weightOgrPalks, heightPalks + 1.65D * mm);
        int otcydaPalks = (int) ((double) x0 + weightOgrPalks + rasstoyanieMegdyPalk);
        int otcydaZifra = otcydaPalks;
        gc.fillRect((double) otcydaPalks, (double) y0, weightOgrPalks, heightPalks + 1.65D * mm);
        otcydaPalks = (int) ((double) otcydaPalks + weightOgrPalks + rasstoyanieMegdyPalk);
        boolean printSrednyaPalks = false;

        for (int numberPalks = 0; numberPalks < palks.length; ++numberPalks) {
            double shirinaPalks = (double) palks[numberPalks] * 0.15D * mm;
            if (numberPalks == palks.length / 2 && !printSrednyaPalks) {
                gc.setFill(Color.BLACK);
                gc.fillRect((double) otcydaPalks, (double) y0, weightOgrPalks, heightPalks + 1.65D * mm);
                otcydaPalks = (int) ((double) otcydaPalks + weightOgrPalks + rasstoyanieMegdyPalk);

                gc.fillRect((double) otcydaPalks, (double) y0, weightOgrPalks, heightPalks + 1.65D * mm);
                otcydaPalks = (int) ((double) otcydaPalks + weightOgrPalks + rasstoyanieMegdyPalk);
                --numberPalks;
                printSrednyaPalks = true;

            } else {
                if (shirinaPalks == 0.0D) {
                    shirinaPalks = 1.35D * mm;
                    gc.setFill(Color.WHITE);
                } else {
                    gc.setFill(Color.BLACK);
                }

                gc.fillRect((double) otcydaPalks, (double) y0, shirinaPalks, heightPalks);

                otcydaPalks = (int) ((double) otcydaPalks + shirinaPalks + rasstoyanieMegdyPalk);
            }
        }

        gc.fillRect((double) otcydaPalks, (double) y0, weightOgrPalks, heightPalks + 1.65D * mm);
        otcydaPalks = (int) ((double) otcydaPalks + weightOgrPalks + rasstoyanieMegdyPalk);
        gc.fillRect((double) otcydaPalks, (double) y0, weightOgrPalks, heightPalks + 1.65D * mm);
        String timeStamp = new SimpleDateFormat("ddMMyyyyHHmm").format(Calendar.getInstance().getTime());
        String codes = order.getId() + timeStamp + order.getTimeProcat();
        for (int i = 0; i < 6; i++) {
            int a = (int) (Math.random() * 10);
            codes += a;
        }
        gc.fillText(codes, x0, y0 + heightPalks + 1.65D * mm + 10, 80);
    }
}

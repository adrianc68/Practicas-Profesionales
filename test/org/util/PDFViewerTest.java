package org.util;

import org.junit.Test;
import javax.swing.JPanel;
import static org.junit.jupiter.api.Assertions.assertNotNull;

public class PDFViewerTest {

    @Test
    public void getJPanelPDF() {
        PDFViewer pdfViewer = new PDFViewer("testPDF.pdf");
        JPanel jPanel = pdfViewer.getJPanelPDF();
        assertNotNull(jPanel);
    }

    @Test(timeout=15000)
    public void getJPanelByNoExistingPDF() {
        PDFViewer pdfViewer = new PDFViewer("f");
        JPanel jPanel = pdfViewer.getJPanelPDF();
        assertNotNull(jPanel);
    }


}
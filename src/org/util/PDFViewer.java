package org.util;

import org.icepdf.ri.common.SwingController;
import org.icepdf.ri.common.SwingViewBuilder;
import javax.swing.JFrame;
import javax.swing.JPanel;

public class PDFViewer {
    private String path;

    /***
     * PDFViewer constructor
     * It receives a path and initialize it
     */
    public PDFViewer(String path) {
        this.path = path;
    }

    /***
     * Get a JPanel with PDF content
     * <p>
     * This method returns a JPanel with Swing components. It can be stick it
     * in another component
     * </p>
     * @return
     */
    public JPanel getJPanelPDF() {
        SwingController swingController = new SwingController();
        swingController.setIsEmbeddedComponent(true);
        SwingViewBuilder swingViewBuilder = new SwingViewBuilder(swingController);
        JPanel panel = swingViewBuilder.buildViewerPanel();
        swingController.getDocumentViewController().setAnnotationCallback( new org.icepdf.ri.common.MyAnnotationCallback( swingController.getDocumentViewController() ) );
        swingController.openDocument(path);
        return panel;
    }

    /***
     * Get a JFrame with the PDF content and swing components
     * <p>
     * It should be used when you need to display a pdf content in a
     * independient window
     * </p>
     */
    public void viewPDFByAIndependentWindowInSwing() {
        SwingController controller = new SwingController();
        SwingViewBuilder factory = new SwingViewBuilder(controller);
        JPanel viewerComponentPanel = factory.buildViewerPanel();
        controller.getDocumentViewController().setAnnotationCallback( new org.icepdf.ri.common.MyAnnotationCallback( controller.getDocumentViewController() ) );
        controller.openDocument(path);
        JFrame applicationFrame = new JFrame();
        applicationFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        applicationFrame.add(viewerComponentPanel);
        applicationFrame.pack();
        applicationFrame.setVisible(true);
    }

}

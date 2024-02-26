package com.gdx.game.course.introduction;

import com.badlogic.gdx.ApplicationListener;
import com.badlogic.gdx.backends.lwjgl.LwjglAWTCanvas;
import com.gdx.game.course.introduction.common.SampleFactory;
import com.gdx.game.course.introduction.common.SampleInfos;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.util.Objects;

public class GdxSamplerLauncher extends JFrame { //JFrame - java desktop top level window

    private static final int WIDTH = 1080;
    private static final int HEIGHT = 720;
    private static final int LIST_WIDTH = 200;
    private static final int CANVAS_WIDTH = WIDTH - LIST_WIDTH;

    // AWT = Abstract Window Toolkit
    //enables to embed application
    private LwjglAWTCanvas lwjglAWTCanvas;
    private JList sampleList;
    private JPanel controlPanel;

    public GdxSamplerLauncher() throws HeadlessException {
        init();
    }

    private void init() {
        createControlPanel();

        Container container = getContentPane();
        container.add(controlPanel, BorderLayout.WEST);
        addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent e) {
                if(Objects.isNull(lwjglAWTCanvas)) {
                    //will cal dispose and stop application in background
                    lwjglAWTCanvas.stop();
                }
            }
        });

        setTitle(GdxSamplerLauncher.class.getSimpleName());
        setMinimumSize(new Dimension(WIDTH, HEIGHT));
        setSize(WIDTH, HEIGHT);
        setResizable(false);
        setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE); //to close the application after pressing X

        // tell window (jframe) to resize and layout our components
        pack();
        setVisible(true);

    }

    private void createControlPanel() {
        controlPanel = new JPanel(new GridBagLayout()); //GridBag bahves like an excel grid
        GridBagConstraints constraints = new GridBagConstraints();

        //constrains for scroll pane
        constraints.gridx = 0; // column
        constraints.gridy = 0; // raw
        constraints.fill = GridBagConstraints.VERTICAL; // to fill vertically
        constraints.weighty = 1; //weight used when fill empty space

        sampleList = new JList(SampleInfos.getSampleNames().toArray());
        sampleList.setFixedCellWidth(LIST_WIDTH);
        sampleList.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        sampleList.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                if (e.getClickCount() == 2) { //check if it's double click
                    launchSelectedSample();
                }
            }
        });

        JScrollPane scrollPane = new JScrollPane(sampleList);
        controlPanel.add(scrollPane, constraints);

        //Constrains for button
        constraints.gridx = 0;
        constraints.gridy = 1;
        constraints.fill = GridBagConstraints.HORIZONTAL;
        constraints.weighty = 0;
        JButton launchButton = new JButton("Launch sample");
        launchButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                launchSelectedSample();
            }
        });

        controlPanel.add(launchButton, constraints);
    }

    private void launchSelectedSample() {
        String name = (String) sampleList.getSelectedValue();

        if(Objects.isNull(name) || name.isEmpty()) {
            System.out.println("No sample to run");
            return;
        }
        launchSample(name);
    }

    private void launchSample(String name) {
        System.out.println("launching sample name= " + name);

        Container container = getContentPane();

        if(Objects.nonNull(lwjglAWTCanvas)) {
            lwjglAWTCanvas.stop();
            container.remove(lwjglAWTCanvas.getCanvas()); //because we need to make new instance of canvas
        }

        ApplicationListener sample = SampleFactory.newSample(name);

        lwjglAWTCanvas = new LwjglAWTCanvas(sample);
        lwjglAWTCanvas.getCanvas().setSize(CANVAS_WIDTH, HEIGHT);
        container.add(lwjglAWTCanvas.getCanvas(), BorderLayout.CENTER);

        pack();
    }

    // == main ==
    public static void main(String[] args) {
        // must be used to run our jframe properly
        SwingUtilities.invokeLater(GdxSamplerLauncher::new);
    }
}

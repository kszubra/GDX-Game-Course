package com.gdx.game.course;

import com.badlogic.gdx.ApplicationListener;
import com.badlogic.gdx.backends.lwjgl.LwjglAWTCanvas;
import com.badlogic.gdx.utils.reflect.ClassReflection;

import javax.swing.*;
import java.awt.*;
import java.util.Objects;

public class GdxSamplerLauncher extends JFrame { //JFrame - java desktop top level window

    private static final int WIDTH = 1080;
    private static final int HEIGHT = 720;

    // AWT = Abstract Window Toolkit
    private LwjglAWTCanvas lwjglAWTCanvas;

    public GdxSamplerLauncher() throws HeadlessException {
        setTitle(GdxSamplerLauncher.class.getSimpleName());
        setMinimumSize(new Dimension(WIDTH, HEIGHT));
        setSize(WIDTH, HEIGHT);
        setResizable(false);
        setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE); //to close the application after pressing X

        // tell window (jframe) to resize and layout our components
        pack();
        setVisible(true);

        launchSample("com.gdx.game.course.InputListeningSample");
    }

    private void launchSample(String name) {
        System.out.println("launching sample name= " + name);

        Container container = getContentPane();

        if(Objects.nonNull(lwjglAWTCanvas)) {
            lwjglAWTCanvas.stop();
            container.remove(lwjglAWTCanvas.getCanvas()); //because we need to make new instance of canvas
        }

        ApplicationListener sample;

        try {
            // get class object by name
            Class<ApplicationListener> clazz = ClassReflection.forName(name);

            // create new instance
            sample = ClassReflection.newInstance(clazz);
        } catch (Exception e) {
            throw new RuntimeException("Cannot create sample with name= " + name, e);
        }

        lwjglAWTCanvas = new LwjglAWTCanvas(sample);
        lwjglAWTCanvas.getCanvas().setSize(WIDTH, HEIGHT);
        container.add(lwjglAWTCanvas.getCanvas(), BorderLayout.CENTER);

        pack();
    }

    // == main ==
    public static void main(String[] args) {
        // must be used to run our jframe properly
        SwingUtilities.invokeLater(GdxSamplerLauncher::new);
    }
}

package me.wbars.life.gui;

import javax.swing.*;
import java.awt.*;
import java.io.IOException;
import java.nio.charset.Charset;
import java.nio.file.Files;

public class Main {
    private static final MyPanel contentPane = new MyPanel();

    public static void main(String[] args) {
        SwingUtilities.invokeLater(Main::createAndShowGui);
    }

    private static void createAndShowGui() {
        JFrame frame = new JFrame("Frame");
        frame.setContentPane(contentPane);
        frame.setSize(Toolkit.getDefaultToolkit().getScreenSize());
        frame.setJMenuBar(createMenu());
        frame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        frame.setVisible(true);
    }

    private static JMenuBar createMenu() {
        JMenuBar menuBar = new JMenuBar();

        JMenu file = new JMenu("File");
        JMenuItem open = new JMenuItem("Open");
        open.addActionListener(e -> {
            JFileChooser fc = new JFileChooser();
            int result = fc.showOpenDialog(contentPane);
            if (result != JFileChooser.APPROVE_OPTION) return;

            try {
                java.util.List<String> strings = Files.readAllLines(fc.getSelectedFile().toPath(), Charset.defaultCharset());
                contentPane.init(strings);
                contentPane.reload(contentPane.getRate());
            } catch (IOException e1) {
                e1.printStackTrace();
            }
        });
        file.add(open);

        JMenu run = new JMenu("Run");
        JMenuItem faster = new JMenuItem("Faster");
        JMenuItem slower = new JMenuItem("Slower");
        faster.addActionListener(e -> contentPane.reload(contentPane.getRate() / 2));
        slower.addActionListener(e -> contentPane.reload(contentPane.getRate() * 2));

        JMenuItem zoomIn = new JMenuItem("Zoom in");
        JMenuItem zoomOut = new JMenuItem("Zoom out");

        zoomIn.addActionListener(e -> contentPane.largerScale());
        zoomOut.addActionListener(e -> contentPane.smallerScale());

        JMenuItem pause = new JMenuItem("Pause");
        JMenuItem start = new JMenuItem("Start");

        pause.addActionListener(e -> contentPane.setPaused(true));
        start.addActionListener(e -> contentPane.setPaused(false));

        run.add(faster);
        run.add(slower);
        run.add(zoomIn);
        run.add(zoomOut);
        run.add(pause);
        run.add(start);

        menuBar.add(file);
        menuBar.add(run);
        return menuBar;
    }


}

package me.wbars.life.gui;

import me.wbars.life.RleService;
import me.wbars.life.core.Cell;
import me.wbars.life.core.Game;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseMotionAdapter;
import java.util.HashSet;
import java.util.List;
import java.util.concurrent.ScheduledFuture;
import java.util.concurrent.ScheduledThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

public class MyPanel extends JPanel {
    private static final int CELL_SIZE = 10;
    private static final double GRID_THRESHOLD = 0.7;
    private static final double SCALE_STEP = 0.1;
    private double multiplier = 1.0;
    private Game game = new Game(new HashSet<>());
    private int xOffset = 0;
    private int yOffset = 0;
    private final ScheduledThreadPoolExecutor executor = new ScheduledThreadPoolExecutor(1);
    private ScheduledFuture<?> task;
    private int rate = 600;
    private Point mousePt;
    private volatile boolean paused = true;
    private int hoverRow;
    private int hoverCol;
    private boolean hovered = false;

    MyPanel() {
        addMouseListener(new MouseAdapter() {
            @Override
            public void mouseEntered(MouseEvent e) {
                hovered = true;
                repaint();
            }

            @Override
            public void mouseExited(MouseEvent e) {
                hovered = false;
                repaint();
            }

            @Override
            public void mousePressed(MouseEvent e) {
                mousePt = e.getPoint();
            }

            @Override
            public void mouseClicked(MouseEvent e) {
                game.addCell(hoverRow, hoverCol);
                repaint();
            }
        });
        addMouseMotionListener(new MouseMotionAdapter() {
            @Override
            public void mouseMoved(MouseEvent e) {
                hoverRow = (e.getY() - yOffset) / cellSize();
                hoverCol = (e.getX() - xOffset) / cellSize();
                repaint();
            }

            @Override
            public void mouseDragged(MouseEvent e) {
                int dx = e.getX() - mousePt.x;
                int dy = e.getY() - mousePt.y;
                changeOffset(dx, dy);
                mousePt = e.getPoint();
            }
        });
        reload(rate);
    }

    @Override
    public Color getBackground() {
        return Color.white;
    }

    @Override
    public LayoutManager getLayout() {
        return new CardLayout();
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        Graphics2D g2d = (Graphics2D) g.create();
        g2d.setColor(Color.black);
        if (game != null) {
            for (Cell cell : game.living()) {
                fillCell(g2d, cell.col(), cell.row());
            }
        }
        if (hovered) {
            g2d.setColor(Color.GRAY);
            fillCell(g2d, hoverCol, hoverRow);
        }

        if (multiplier > GRID_THRESHOLD) drawGrid(g2d);
        g2d.dispose();
    }

    private int offset(int offset) {
        return offset % cellSize() - cellSize();
    }

    private void drawGrid(Graphics2D g2d) {
        g2d.setColor(Color.lightGray);
        int top = offset(yOffset);
        int left = offset(xOffset);
        int bottom = getHeight() + top + cellSize();
        int right = getWidth() + left + cellSize();

        for (int i = left; i <= right; i += cellSize()) {
            g2d.drawLine(i, top, i, bottom);
        }
        for (int i = top; i <= bottom; i += cellSize()) {
            g2d.drawLine(left, i, right, i);
        }
    }

    private void fillCell(Graphics2D g2d, int col, int row) {
        g2d.fillRect(xOffset + col * cellSize(), yOffset + row * cellSize(), cellSize(), cellSize());
    }

    private int cellSize() {
        return (int) (multiplier * CELL_SIZE);
    }

    private void next() {
        if (game != null) game.nextGeneration();
        repaint();
    }

    void smallerScale() {
        multiplier = Math.max(SCALE_STEP, multiplier - SCALE_STEP);
        repaint();
    }

    void largerScale() {
        multiplier += SCALE_STEP;
        repaint();
    }

    private void changeOffset(int dx, int dy) {
        xOffset += dx;
        yOffset += dy;

        repaint();
    }

    void init(List<String> strings) {
        game = RleService.toGame(strings);
    }

    void reload(int rate) {
        if (task != null) task.cancel(true);

        this.rate = rate;
        task = executor.scheduleAtFixedRate(() -> {
            if (!paused) next();
        }, 0, rate, TimeUnit.MILLISECONDS);
    }

    void setPaused(boolean paused) {
        this.paused = paused;
    }

    int getRate() {
        return rate;
    }
}

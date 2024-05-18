import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.List;

public class LorenzAttractor extends JFrame {
    private LorenzPanel panel;
    private Timer timer;

    public LorenzAttractor() {
        setTitle("Lorenz Attractor");
        setSize(800, 600);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);

        panel = new LorenzPanel();
        add(panel, BorderLayout.CENTER);

        JButton startButton = new JButton("Start");
        startButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                startSimulation();
            }
        });

        JButton stopButton = new JButton("Stop");
        stopButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                stopSimulation();
            }
        });

        JPanel controlPanel = new JPanel();
        controlPanel.add(startButton);
        controlPanel.add(stopButton);

        add(controlPanel, BorderLayout.SOUTH);
    }

    private void startSimulation() {
        if (timer == null) {
            timer = new Timer(50, new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    panel.updateSimulation();
                }
            });
        }
        timer.start();
    }

    private void stopSimulation() {
        if (timer != null) {
            timer.stop();
        }
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {
                new LorenzAttractor().setVisible(true);
            }
        });
    }

    private class LorenzPanel extends JPanel {
        private static final double SIGMA = 10.0;
        private static final double RHO = 28.0;
        private static final double BETA = 8.0 / 3.0;
        private static final double DT = 0.01;

        private double x = 0.1;
        private double y = 0;
        private double z = 0;

        private List<Point3D> points = new ArrayList<>();

        public LorenzPanel() {
            setBackground(Color.BLACK);
        }

        public void updateSimulation() {
            double dx = SIGMA * (y - x);
            double dy = x * (RHO - z) - y;
            double dz = x * y - BETA * z;

            x += dx * DT;
            y += dy * DT;
            z += dz * DT;

            points.add(new Point3D(x, y, z));
            repaint();
        }

        @Override
        protected void paintComponent(Graphics g) {
            super.paintComponent(g);
            Graphics2D g2d = (Graphics2D) g;
            g2d.setColor(Color.WHITE);

            for (Point3D point : points) {
                int px = (int) (getWidth() / 2 + point.x * 10);
                int py = (int) (getHeight() / 2 + point.y * 10);
                g2d.drawRect(px, py, 1, 1);
            }
        }

        private class Point3D {
            double x, y, z;

            Point3D(double x, double y, double z) {
                this.x = x;
                this.y = y;
                this.z = z;
            }
        }
    }
}

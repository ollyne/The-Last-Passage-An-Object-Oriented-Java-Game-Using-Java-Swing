package panel;

import javax.swing.*;
import java.awt.*;

import logic.GameState;

public class MenuPanel extends JPanel {

    // COLOR THEME 
    private static final Color BG_TOP     = new Color(170, 150, 120);
    private static final Color BG_BOTTOM  = new Color(130, 110, 85);
    private static final Color CARD_COLOR = new Color(235, 225, 210);
    private static final Color TEXT_COLOR = new Color(60, 45, 30);

    // BUTTON SIZE 
    private static final Dimension BUTTON_SIZE =
            new Dimension(220, 40);

    public MenuPanel(GameFrame frame) {

        setPreferredSize(new Dimension(600, 500));
        setLayout(new GridBagLayout());

        // CARD 
        JPanel card = new JPanel();
        card.setBackground(CARD_COLOR);
        card.setLayout(new BoxLayout(card, BoxLayout.Y_AXIS));
        card.setBorder(
            BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(TEXT_COLOR, 2, true),
                BorderFactory.createEmptyBorder(25, 40, 25, 40)
            )
        );

        // TITLE
        JLabel title = new JLabel("THE LAST PASSAGE");
        title.setFont(new Font("Serif", Font.BOLD, 26));
        title.setForeground(TEXT_COLOR);
        title.setAlignmentX(Component.CENTER_ALIGNMENT);

        // SUBTITLE
        JLabel subtitle = new JLabel("Enter your name to begin");
        subtitle.setFont(new Font("SansSerif", Font.PLAIN, 13));
        subtitle.setForeground(TEXT_COLOR);
        subtitle.setBorder(BorderFactory.createEmptyBorder(5, 0, 15, 0));
        subtitle.setAlignmentX(Component.CENTER_ALIGNMENT);

        // NAME FIELD
        JTextField nameField = new JTextField();
        nameField.setMaximumSize(new Dimension(220, 30));
        nameField.setFont(new Font("SansSerif", Font.PLAIN, 14));
        nameField.setBorder(
            BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(TEXT_COLOR, 1, true),
                BorderFactory.createEmptyBorder(5, 8, 5, 8)
            )
        );

        // BUTTONS
        JButton startButton   = createMenuButton("START GAME", 2);
        JButton recordButton  = createMenuButton("VIEW RECORDS", 2);
        JButton exitButton    = createMenuButton("EXIT", 2);

        startButton.addActionListener(e -> {
            String name = nameField.getText().trim();
            if (name.isEmpty()) name = "Player";
            frame.startGame(name);
        });

        recordButton.addActionListener(e ->
            frame.getSceneManager().switchScene(GameState.RECORDS)
        );

        exitButton.addActionListener(e -> {
            int confirm = JOptionPane.showConfirmDialog(
                this,
                "Are you sure you want to exit?",
                "Exit Game",
                JOptionPane.YES_NO_OPTION
            );
            if (confirm == JOptionPane.YES_OPTION) {
                System.exit(0);
            }
        });

        // ADD TO CARD 
        card.add(title);
        card.add(subtitle);
        card.add(nameField);
        card.add(Box.createVerticalStrut(18));
        card.add(startButton);
        card.add(Box.createVerticalStrut(10));
        card.add(recordButton);
        card.add(Box.createVerticalStrut(10));
        card.add(exitButton);

        add(card);
    }

    // BUTTON FACTORY
    private JButton createMenuButton(String text, int borderThickness) {
        JButton button = new JButton(text);
        button.setFocusPainted(false);
        button.setFont(new Font("SansSerif", Font.BOLD, 13));
        button.setBackground(CARD_COLOR);
        button.setForeground(TEXT_COLOR);

        button.setPreferredSize(BUTTON_SIZE);
        button.setMaximumSize(BUTTON_SIZE);
        button.setMinimumSize(BUTTON_SIZE);

        button.setAlignmentX(Component.CENTER_ALIGNMENT);

        button.setBorder(
            BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(TEXT_COLOR, borderThickness, true),
                BorderFactory.createEmptyBorder(8, 20, 8, 20)
            )
        );

        return button;
    }

    // BACKGROUND
    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        Graphics2D g2 = (Graphics2D) g;

        GradientPaint gp = new GradientPaint(
            0, 0, BG_TOP,
            0, getHeight(), BG_BOTTOM
        );
        g2.setPaint(gp);
        g2.fillRect(0, 0, getWidth(), getHeight());
    }
}


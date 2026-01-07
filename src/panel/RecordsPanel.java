package panel;

import javax.swing.*;
import java.awt.*;
import java.util.List;

import data.GameDataManager;
import data.Record;
import logic.GameState;

public class RecordsPanel extends JPanel {

    // COLOR THEME 
    private static final Color BG_COLOR     = new Color(150, 130, 105);
    private static final Color CARD_COLOR   = new Color(235, 225, 210);
    private static final Color BORDER_COLOR = new Color(120, 95, 70);
    private static final Color TEXT_COLOR   = new Color(50, 40, 30);
    private static final Color HIGHLIGHT_BG = new Color(210, 190, 160);

    public RecordsPanel(GameFrame frame) {

        setPreferredSize(new Dimension(600, 520));
        setLayout(new GridBagLayout()); 

        // WRAPPER UTAMA
        JPanel wrapper = new JPanel();
        wrapper.setOpaque(false);
        wrapper.setLayout(new BoxLayout(wrapper, BoxLayout.Y_AXIS));

        // TITLE
        JLabel title = new JLabel("LATEST RECORDS");
        title.setFont(new Font("Serif", Font.BOLD, 22));
        title.setForeground(TEXT_COLOR);
        title.setAlignmentX(Component.CENTER_ALIGNMENT);
        title.setBorder(BorderFactory.createEmptyBorder(0, 0, 12, 0));
        wrapper.add(title);

        // RECORDS BOX 
        JPanel card = new JPanel();
        card.setBackground(CARD_COLOR);
        card.setLayout(new BoxLayout(card, BoxLayout.Y_AXIS));
        card.setAlignmentX(Component.CENTER_ALIGNMENT);
        card.setBorder(
            BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(BORDER_COLOR, 2, true),
                BorderFactory.createEmptyBorder(20, 30, 20, 30)
            )
        );

        List<Record> records = GameDataManager.loadLatestRecords(5);

        if (records.isEmpty()) {

            JLabel empty = new JLabel("NO RECORDS YET");
            empty.setFont(new Font("Arial", Font.ITALIC, 14));
            empty.setForeground(TEXT_COLOR);
            empty.setAlignmentX(Component.CENTER_ALIGNMENT);
            card.add(empty);

        } else {

            // BEST RECORD
            Record best = records.stream()
                    .min((a, b) -> Integer.compare(a.getTime(), b.getTime()))
                    .orElse(null);

            if (best != null) {
                JLabel bestLabel = new JLabel(
                    "BEST RECORD:  " + best.getName() + " — " + best.getTime() + " sec"
                );
                bestLabel.setFont(new Font("Arial", Font.BOLD, 14));
                bestLabel.setForeground(TEXT_COLOR);
                bestLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
                bestLabel.setBorder(BorderFactory.createEmptyBorder(0, 0, 10, 0));
                card.add(bestLabel);

                JSeparator sep = new JSeparator();
                sep.setMaximumSize(new Dimension(280, 1));
                sep.setForeground(BORDER_COLOR);
                card.add(sep);

                card.add(Box.createVerticalStrut(12));
            }

            // RECORD LIST
            int rank = 1;
            for (int i = 0; i < records.size(); i++) {
                Record r = records.get(i);
                boolean isNewest = (i == 0);

                JLabel row = new JLabel(
                    rank + ".  " + r.getName() + " — " + r.getTime() + " sec"
                );
                row.setFont(new Font(
                    "Arial",
                    isNewest ? Font.BOLD : Font.PLAIN,
                    14
                ));
                row.setForeground(TEXT_COLOR);
                row.setAlignmentX(Component.CENTER_ALIGNMENT);

                if (isNewest) {
                    row.setOpaque(true);
                    row.setBackground(HIGHLIGHT_BG);
                    row.setBorder(
                        BorderFactory.createCompoundBorder(
                            BorderFactory.createLineBorder(BORDER_COLOR),
                            BorderFactory.createEmptyBorder(6, 14, 6, 14)
                        )
                    );

                    JLabel badge = new JLabel("YOUR NEW RECORD!");
                    badge.setFont(new Font("Arial", Font.BOLD, 11));
                    badge.setForeground(new Color(120, 80, 40));
                    badge.setAlignmentX(Component.CENTER_ALIGNMENT);

                    card.add(row);
                    card.add(Box.createVerticalStrut(4));
                    card.add(badge);
                    card.add(Box.createVerticalStrut(10));
                } else {
                    row.setBorder(BorderFactory.createEmptyBorder(4, 0, 4, 0));
                    card.add(row);
                }

                rank++;
            }
        }

        wrapper.add(card);
        wrapper.add(Box.createVerticalStrut(12));

        // BACK BUTTON (TEPAT DI BAWAH BOX)
        JButton back = new JButton("BACK TO MENU");
        back.setFont(new Font("Arial", Font.BOLD, 12));
        back.setFocusPainted(false);
        back.setBackground(CARD_COLOR);
        back.setForeground(TEXT_COLOR);
        back.setAlignmentX(Component.CENTER_ALIGNMENT);
        back.setBorder(
            BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(BORDER_COLOR, 2),
                BorderFactory.createEmptyBorder(8, 26, 8, 26)
            )
        );

        back.addActionListener(e ->
            frame.getSceneManager().switchScene(GameState.MENU)
        );

        wrapper.add(back);
        add(wrapper, new GridBagConstraints());
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        g.setColor(BG_COLOR);
        g.fillRect(0, 0, getWidth(), getHeight());
    }
}

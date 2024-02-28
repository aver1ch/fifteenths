import javax.swing.*;
import javax.swing.border.TitledBorder;
import java.awt.*;
import java.awt.event.*;
import java.util.Random;

public class Fifteenths {
    public static final JFrame window = new JFrame("Пятнашки");
    private JButton[] buttons = new JButton[16];
    private static final Random RANDOM = new Random();
    private JPanel gamePanel = new JPanel(new GridLayout(4, 4, 0, 0));
    private JPanel scoreBoardPanel = new JPanel();

    int indexOfEmptyButton = 0;

    public Fifteenths() {
        //window
        window.setSize(500, 500);
        window.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        window.setLayout(new BorderLayout());

        //buttons
        drawButtons();
        //scoreBoard
        drawScoreTable();

        window.setVisible(true);
    }

    private void drawScoreTable() {
        TitledBorder border = BorderFactory.createTitledBorder("Info");
        scoreBoardPanel.setBorder(border);
        scoreBoardPanel.setLayout(new GridLayout(5, 1));

        // Создаем кнопку новой игры
        JButton newGameButton = new JButton("New Game");
        newGameButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                drawButtons();
            }
        });
        scoreBoardPanel.add(newGameButton);

        window.add(scoreBoardPanel, BorderLayout.NORTH); // Добавляем панель на окно
        window.pack();
    }

    private void drawButtons()
    {
        gamePanel.removeAll();
        for (int i = 0; i < buttons.length; ++i) {
            if (i == buttons.length - 1) {
                buttons[i] = new JButton("");
                indexOfEmptyButton = i;
            } else {
                buttons[i] = new JButton("" + (i + 1));
            }
            buttons[i].setPreferredSize(new Dimension(100, 100));

            buttons[i].addActionListener(new ActionListener() {
                public void actionPerformed(ActionEvent e) {
                    JButton clickedButton = (JButton) e.getSource();

                    int clickedIndex = 0;
                    for (int i = 0; i < buttons.length; i++) {
                        if (buttons[i] == clickedButton) {
                            clickedIndex = i;
                            break;
                        }
                    }

                    if (isNeighbor(indexOfEmptyButton, clickedIndex)) {
                        JButton temp = buttons[clickedIndex];
                        buttons[clickedIndex] = buttons[indexOfEmptyButton];
                        buttons[indexOfEmptyButton] = temp;

                        indexOfEmptyButton = clickedIndex;

                        gamePanel.removeAll();
                        for (JButton button : buttons) {
                            gamePanel.add(button);
                        }
                        gamePanel.revalidate();
                        gamePanel.repaint();
                    }
                }
            });

            gamePanel.add(buttons[i]);
        }

        shuffle();

        for (int i = 0; i < buttons.length; ++i) {
            if (buttons[i].getText().equals("")) {
                indexOfEmptyButton = i;
            }
            gamePanel.add(buttons[i]);
        }

        window.add(gamePanel, BorderLayout.CENTER);
        window.repaint();
        window.pack();
    }

    boolean isWin() {
        boolean win = false;
        for (int i = 0; i < buttons.length - 1; ++i) {
            if (Integer.valueOf(buttons[i].getText()) != i) {
                win = false;
            } else {
                win = true;
            }
        }
        return win;
    }

    private boolean isNeighbor(int indexEmpty, int indexPlate) {
        return ((indexEmpty + 1 == indexPlate)
                || (indexEmpty - 1 == indexPlate)
                || (indexEmpty + 4 == indexPlate)
                || (indexEmpty - 4 == indexPlate));
    }

    private void shuffle() {
        int n = buttons.length;

        while (n > 1) {
            int r = RANDOM.nextInt(n--);
            JButton tmp = buttons[r];
            buttons[r] = buttons[n];
            buttons[n] = tmp;
        }
    }

}

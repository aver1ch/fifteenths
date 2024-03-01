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
    private JButton newGameButton = new JButton("Новая игра");

    private int indexOfEmptyButton = 0;
    private int steps = 0;

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
        scoreBoardPanel.removeAll();
        TitledBorder border = BorderFactory.createTitledBorder("Счёт");
        scoreBoardPanel.setBorder(border);
        scoreBoardPanel.setLayout(new GridLayout(5, 1));
        JLabel score = new JLabel("Шаги:" + steps);
        JLabel correctPositions = new JLabel("Правильные позиции:" + countOfGoodDestination(buttons));

        newGameButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                steps = 0;
                drawScoreTable();
                drawButtons();
            }
        });

        scoreBoardPanel.add(newGameButton);
        scoreBoardPanel.add(score);
        scoreBoardPanel.add(correctPositions);
        window.add(scoreBoardPanel, BorderLayout.NORTH);
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
                        scoreBoardPanel.removeAll();
                        ++steps;
                        int heuristicValue = heuristicFunction(buttons);
                        JLabel score = new JLabel("Шаги:" + steps + "    " + "Шагов до победы:" + heuristicValue + "       " + "Правильные позиции:" + countOfGoodDestination(buttons));
                        scoreBoardPanel.add(newGameButton);
                        scoreBoardPanel.add(score);

                        JButton temp = buttons[clickedIndex];
                        buttons[clickedIndex] = buttons[indexOfEmptyButton];
                        buttons[indexOfEmptyButton] = temp;

                        indexOfEmptyButton = clickedIndex;

                        gamePanel.removeAll();
                        for (JButton button : buttons) {
                            gamePanel.add(button);
                        }

                        JButton autoPlayButton = new JButton("Автоигра");
                        autoPlayButton.setPreferredSize(new Dimension(100, 20));
                        autoPlayButton.addActionListener(new ActionListener() {
                            public void actionPerformed(ActionEvent e)
                            {
                                autoPlay();
                            }
                        });
                        scoreBoardPanel.add(autoPlayButton);


                        gamePanel.revalidate();
                        gamePanel.repaint();
                        scoreBoardPanel.revalidate();
                        scoreBoardPanel.repaint();

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

    int countOfGoodDestination(JButton[] buttons) {
        int counterOfGoodDestination = 0;
        for (int i = 0; i < buttons.length; ++i) {
            String buttonText = buttons[i].getText();
            if (!buttonText.isEmpty() && Integer.valueOf(buttonText) == i + 1) {
                ++counterOfGoodDestination;
            }
        }
        return counterOfGoodDestination;
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

    private void autoPlay() {
        int heuristicValue = heuristicFunction(buttons);
    
        while (heuristicValue > 0) {
            int minHeuristicValue = Integer.MAX_VALUE;
            int nextIndex = -1;
            int indexOfEmptyButton = -1;
    
            // Найдем индекс пустой кнопки
            for (int i = 0; i < buttons.length; i++) {
                if (buttons[i].getText().isEmpty()) {
                    indexOfEmptyButton = i;
                    break;
                }
            }
    
            for (int i = 0; i < buttons.length; i++) {
                if (isNeighbor(indexOfEmptyButton, i)) {
                    JButton temp = buttons[i];
                    buttons[i] = buttons[indexOfEmptyButton];
                    buttons[indexOfEmptyButton] = temp;
    
                    int newHeuristicValue = heuristicFunction(buttons);
                    if (newHeuristicValue < minHeuristicValue) {
                        minHeuristicValue = newHeuristicValue;
                        nextIndex = i;
                    }
    
                    // Вернем обратно на место
                    temp = buttons[i];
                    buttons[i] = buttons[indexOfEmptyButton];
                    buttons[indexOfEmptyButton] = temp;

                    gamePanel.removeAll();
                    for (JButton button : buttons) {
                        gamePanel.add(button);
                    }
                    gamePanel.revalidate();
                    gamePanel.repaint();
                    }
            }
    
            if (nextIndex != -1) {
                JButton temp = buttons[nextIndex];
                buttons[nextIndex] = buttons[indexOfEmptyButton];
                buttons[indexOfEmptyButton] = temp;
    
                // Обновляем значение эвристической функции
                heuristicValue = minHeuristicValue;
    
                // Обновляем интерфейс
                gamePanel.removeAll();
                for (JButton button : buttons) {
                    gamePanel.add(button);
                }
                
                scoreBoardPanel.repaint();
                gamePanel.revalidate();
                gamePanel.repaint();
            } else {
                // Если не нашли более хорошего хода, прерываем цикл
                break;
            }
        }
    }
    
    
        
    private int heuristicFunction(JButton[] buttons) {
        int heuristicValue = 0;

        // Считаем количество ходов, необходимых для перемещения каждой плитки на ее правильное место
        for (int i = 0; i < buttons.length; ++i) {
            String buttonText = buttons[i].getText();
            if (!buttonText.isEmpty()) {
                int destinationIndex = Integer.valueOf(buttonText) - 1;
                int currentDistance = Math.abs(i - destinationIndex);
                int horizontalDistance = currentDistance % 4;
                int verticalDistance = currentDistance / 4;

                // Считаем количество ходов, необходимых для перемещения плитки по горизонтали
                if (horizontalDistance > 0) {
                    heuristicValue += horizontalDistance;
                }

                // Считаем количество ходов, необходимых для перемещения плитки по вертикали
                if (verticalDistance > 0) {
                    heuristicValue += verticalDistance;
                }
            }
        }

        return heuristicValue;
    }

}
    

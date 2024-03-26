import javax.swing.*;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.List;

public class UI extends JFrame {

    private FifteenPuzzle puzzle;
    private JPanel puzzlePanel;
    private JButton[][] buttons;
    private JPanel controlPanel;
    private JButton userSolutionButton;
    private JButton autoSolutionButton;
    private JButton newGameButton;
    private volatile boolean stopDemonstration = false;
    private int steps;
    private JPanel scoreBoardPanel;


    public UI() {
        setTitle("Fifteen Puzzle");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(500, 500);
    
        puzzle = new FifteenPuzzle();
        puzzle.shuffle(70);
    
        puzzlePanel = new JPanel(new GridLayout(FifteenPuzzle.DIMS, FifteenPuzzle.DIMS));
        buttons = new JButton[FifteenPuzzle.DIMS][FifteenPuzzle.DIMS];
        initButtons();
    
        scoreBoardPanel = new JPanel(); // Инициализируем scoreBoardPanel
    
        controlPanel = new JPanel();
        JLabel autoGameText = new JLabel("Автоигра: ");
        userSolutionButton = new JButton("Выкл");
        autoSolutionButton = new JButton("Вкл");
        newGameButton = new JButton("Новая игра");
    
        userSolutionButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                solveByUser();
            }
        });
    
        autoSolutionButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                solveAndShowSolution();
            }
        });
    
        newGameButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                startNewGame();
            }
        });
        
        controlPanel.add(autoGameText);
        controlPanel.add(userSolutionButton);
        controlPanel.add(autoSolutionButton);
        controlPanel.add(newGameButton);
    
        add(controlPanel, BorderLayout.NORTH);
        add(puzzlePanel, BorderLayout.CENTER);
        add(scoreBoardPanel, BorderLayout.SOUTH);
    
        setVisible(true);
    }

    private void initButtons() {
        for (int i = 0; i < FifteenPuzzle.DIMS; i++) {
            for (int j = 0; j < FifteenPuzzle.DIMS; j++) {
                buttons[i][j] = new JButton();
                buttons[i][j].setPreferredSize(new Dimension(100, 100));
                buttons[i][j].setBorder(BorderFactory.createLineBorder(Color.BLACK));
                buttons[i][j].addActionListener(new ActionListener() {
                    public void actionPerformed(ActionEvent e) {
                        JButton clickedButton = (JButton) e.getSource();
                        moveButton(clickedButton);
                    }
                });
                puzzlePanel.add(buttons[i][j]);
            }
        }
        updatePuzzlePanel();
    }

    private void updatePuzzlePanel() {
        int[][] tiles = puzzle.getTiles();
        for (int i = 0; i < FifteenPuzzle.DIMS; i++) {
            for (int j = 0; j < FifteenPuzzle.DIMS; j++) {
                buttons[i][j].setText(Integer.toString(tiles[i][j]));
                if (tiles[i][j] == 0) {
                    buttons[i][j].setOpaque(true);
                    buttons[i][j].setForeground(Color.RED);
                    buttons[i][j].setBackground(Color.RED);
                } else {
                    buttons[i][j].setOpaque(false);
                    buttons[i][j].setForeground(Color.BLACK);
                }
            }
        }
        revalidate();
        repaint();
    }    
    
    private int countOfGoodDestination(JButton[][] buttons) {
        int counterOfGoodDestination = 0;
        for (int i = 0; i < FifteenPuzzle.DIMS; i++) {
            for (int j = 0; j < FifteenPuzzle.DIMS; j++) {
                String buttonText = buttons[i][j].getText();
                if (!buttonText.isEmpty() && Integer.valueOf(buttonText) == i * FifteenPuzzle.DIMS + j + 1) {
                    counterOfGoodDestination++;
                }
            }
        }
        return counterOfGoodDestination;
    }    

    private void checkCompletion() {
        if (puzzle.isSolved()) {
            JOptionPane.showMessageDialog(this, "Поздравляем! Вы решили головоломку за " + steps + " шагов!");
        }
    }

    private void solveAndShowSolution() {
        new Thread(() -> {
            List<FifteenPuzzle> solution = puzzle.aStarSolve();
            if (solution != null) {
                for (FifteenPuzzle sp : solution) {
                    if (stopDemonstration) {
                        stopDemonstration = false;
                        return;
                    }
                    puzzle = sp;
                    updatePuzzlePanel();
                    try {
                        Thread.sleep(1000);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
            } else {
                System.out.println("Не удалось найти решение.");
            }
        }).start();
    }

    private void solveByUser() {
        stopDemonstration = true;
    }

    private void startNewGame() {
        stopDemonstration = true; // Остановка автоигры при новой игре
        puzzle.shuffle(70);
        steps = 0;
        updatePuzzlePanel();
        updateScorePanel();
        stopDemonstration = false;
    }

    private void updateScorePanel() {
        scoreBoardPanel.removeAll();
        int goodDestinationCount = countOfGoodDestination(buttons);
        JLabel correctPositions = new JLabel("Фишек на своих местах: " + goodDestinationCount);
        JLabel stepsLabel = new JLabel("Количество ходов: " + steps);
        JLabel manhattanLabel = new JLabel("Манхэттенское расстояние: " + puzzle.manhattanDistance());
        scoreBoardPanel.add(correctPositions);
        scoreBoardPanel.add(stepsLabel);
        scoreBoardPanel.add(manhattanLabel);
        pack();
        scoreBoardPanel.revalidate();
        scoreBoardPanel.repaint();
    }
    

    private void moveButton(JButton clickedButton) {
        for (int i = 0; i < FifteenPuzzle.DIMS; i++) {
            for (int j = 0; j < FifteenPuzzle.DIMS; j++) {
                if (buttons[i][j] == clickedButton) {
                    FifteenPuzzle.TilePos clickedPos = puzzle.new TilePos(i, j);
                    if (puzzle.isValidMove(clickedPos)) {
                        puzzle.move(clickedPos);
                        steps++;
                        updatePuzzlePanel();
                        updateScorePanel();
                        checkCompletion();
                    }
                    return;
                }
            }
        }
    }
    
    public static void main(String[] args) {
        SwingUtilities.invokeLater(UI::new);
    }
}

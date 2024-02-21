import javax.swing.*;

import java.awt.*;
import java.util.List;
import java.util.ArrayList;
import java.util.Collections;

// первая, которая считает, сколько ячеек находится не на своём месте
// вторая, которая считает количество ходов до правильной комбинации
// эвристика
// uml-модели

final public class Fifteenths
{
    private final JFrame frame = new JFrame("Пятнашки");
    private List<JButton> buttons = new ArrayList<>();

    final public void window()
    {
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setLayout(new FlowLayout());
        frame.setSize(700, 700);
        frame.setVisible(true);
        
        createPlaystation();
        createScoreTable();
    }

    public void createPlaystation()
    {
        JPanel playStation = new JPanel(new GridLayout(4, 4, 5, 5));
        playStation.setBorder(BorderFactory.createTitledBorder("Game"));
        createButtons(playStation);
        frame.getContentPane().add(playStation);
        frame.pack();
        frame.setVisible(true);
    }

    public void createButtons(JPanel playStation)
    {
        for (int i = 1; i <= 15; ++i)
        {
            JButton plate = new JButton(" " + i + " ");
            plate.setPreferredSize(new Dimension(100, 100));
            buttons.add(i - 1, plate);
        }
        shuffle();
        for (int i = 1; i <= 15; ++i)
        {
            playStation.add(buttons.get(i - 1));
        }
    }

    final public void createScoreTable()
    {
        JPanel scoreTable = new JPanel(new GridLayout(2, 2, 700, 350));
        scoreTable.setBorder(BorderFactory.createTitledBorder("Score"));

        JLabel score = new JLabel("Steps: ");
        scoreTable.add(score, BorderLayout.NORTH);

        JButton newGameButton = new JButton("New game");
        newGameButton.setPreferredSize(new Dimension(100, 20));
        scoreTable.add(newGameButton, BorderLayout.SOUTH);

        frame.getContentPane().add(scoreTable);
        frame.pack();
        frame.setVisible(true);
    }

    public int firstHeuristicProblem()
    {
        return 0;
    }

    public int secondHeuristicProblem()
    {
        return 0;
    }

    public void shuffle()
    {
        Collections.shuffle(buttons);
    }    
}

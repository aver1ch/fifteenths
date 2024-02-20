import javax.swing.*;

import java.awt.*;

// первая, которая считает, сколько ячеек находится не на своём месте
// вторая, которая считает количество ходов до правильной комбинации
// сделать игру и сделать, чтобы играла сама игра)
// эвристика
// uml-модели

public class Fifteenths
{
    private final JFrame frame = new JFrame("Пятнашки");

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

        frame.getContentPane().add(playStation);
        frame.pack();
        frame.setVisible(true);
    }

    public void createButtons(JPanel playStation, Container buttons)
    {
        for (int i = 1; i <= 15; ++i)
        {
            JButton plate = new JButton(" " + i + " ");
            plate.setPreferredSize(new Dimension(100, 100));
            buttons.add(plate);
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
}

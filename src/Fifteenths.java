import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class Fifteenths
{
    private final JFrame frame = new JFrame("Пятнашки");
    private JButton emptyPlate = new JButton(" ");
    private List<JButton> buttons = new ArrayList<>();
    private boolean autoPlay = false;
    private int stepsCount = 0;

    public void window() // cоздаётся окно
    {
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setLayout(new BorderLayout());
        frame.setSize(700, 700);

        createPlaystation();
        createScoreTable();
        createModeSelection();

        frame.setVisible(true);
    }

    public void createPlaystation() // создаётся игровая площадка
    {
        JPanel playStation = new JPanel(new GridLayout(4, 4, 5, 5));
        playStation.setBorder(BorderFactory.createTitledBorder("Game"));
        createButtons(playStation);
        frame.getContentPane().add(playStation, BorderLayout.CENTER);
    }

    public void createButtons(JPanel playStation) // создаются кнопки
    {
        for (int i = 1; i <= 15; ++i)
        {
            JButton plate = new JButton(" " + i + " ");
            plate.setPreferredSize(new Dimension(100, 100));
            buttons.add(i - 1, plate);
        }

        emptyPlate.setPreferredSize(new Dimension(100, 100));
        buttons.add(emptyPlate);
        shuffle();
        for (int i = 0; i <= 15; ++i)
        {
            playStation.add(buttons.get(i));
        }
        playStation.add(emptyPlate);
    }

    public void createScoreTable() // создаётся окно счёта
    {
        JPanel scoreTable = new JPanel(new GridLayout(2, 1));
        scoreTable.setBorder(BorderFactory.createTitledBorder("Score"));

        JLabel stepsLabel = new JLabel("Steps: ");
        scoreTable.add(stepsLabel);

        frame.getContentPane().add(scoreTable, BorderLayout.EAST);
    }

    public void swap()
    {
        for(int i = 0; i <= 15; ++i)
        {
            if(buttons.get(i).getText() != " ")
            {
                buttons.get(i).addActionListener(new ActionListener()
                {
                    public void actionPerformed(ActionEvent e)
                    {
                        
                    }    
                });
            }
        }
    }

    public void createModeSelection() // окно выбора режима игры
    {
        JPanel modePanel = new JPanel(new FlowLayout());
        JLabel modeLabel = new JLabel("Choose Mode: ");
        JButton manualModeButton = new JButton("Manual");
        JButton autoModeButton = new JButton("Auto");

        manualModeButton.addActionListener(new ActionListener()
        {
            @Override
            public void actionPerformed(ActionEvent e)
            {
                autoPlay = false; // Устанавливаем режим игры вручную
            }
        });

        /*autoModeButton.addActionListener(new ActionListener()
        {
            @Override
            public void actionPerformed(ActionEvent e)
            {
                autoPlay = true; // Устанавливаем режим игры автоматически
                autoPlay(); // Запускаем автоматическую игру
            }
        });*/

        modePanel.add(modeLabel);
        modePanel.add(manualModeButton);
        modePanel.add(autoModeButton);

        frame.getContentPane().add(modePanel, BorderLayout.NORTH);
    }

    public void shuffle()
    {
        Collections.shuffle(buttons);
    }
}

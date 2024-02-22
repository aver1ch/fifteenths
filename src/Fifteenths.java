import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

// счётчик шагов
// решение двух задачек
// разные режимы

public class Fifteenths
{
    private  JFrame frame = new JFrame("Пятнашки");
    private List<JButton> buttons = new ArrayList<>();
    JButton emptyPlate = new JButton();
    private boolean autoPlay = false;
    private static int stepsCount = 0;
    private boolean isSolved = true;
    public void window() // cоздаётся окно
    {
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setLayout(new BorderLayout());
        frame.setSize(700, 700);

        createPlaystation();
        createScoreTable();
        createModeSelection();
        while(!isSolved)
        {
            
        }

        frame.setVisible(true);
    }

    public void createPlaystation() // создаётся игровая площадка
    {
        JPanel playStation = new JPanel(new GridLayout(4, 4, 5, 5));
        playStation.setBorder(BorderFactory.createTitledBorder("Game"));
        createButtons(playStation);
        frame.getContentPane().add(playStation, BorderLayout.CENTER);
    }

    public void createButtons(JPanel playStation)
    {
        for (int i = 0; i < 16; ++i)
        {
            JButton plate = new JButton(" " + i + " ");
            plate.setPreferredSize(new Dimension(100, 100));
            plate.addActionListener(new ActionListener()
            {
                @Override
                public void actionPerformed(ActionEvent e)
                {
                    int emptyPlateIndex = buttons.indexOf(emptyPlate);
                    int plateIndex = buttons.indexOf(plate);
                    if (isNeighbor(emptyPlateIndex, plateIndex))
                    {
                        JButton tempButton = emptyPlate;
                        String tempText = tempButton.getText();
        
                        emptyPlate.setText(plate.getText());
                        plate.setText(tempText);
                        emptyPlate = plate;
                        frame.repaint();
                    }
                }
            });
            buttons.add(plate);
            
        }
        emptyPlate.setPreferredSize(new Dimension(100, 100));
        emptyPlate.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                emptyPlate.setText(""); // Обновляем текст emptyPlate на пустую строку
                emptyPlate.revalidate(); // Перерисовываем кнопку
                frame.repaint(); // Перерисовываем интерфейс
            }
        });        

        buttons.add(emptyPlate);
        shuffle();

        for(int i = 0; i < 16; ++i)
        {
            playStation.add(buttons.get(i));
        }
    }
    
    private boolean isNeighbor(int indexEmpty, int indexPlate)
    {
        return ((indexEmpty + 1 == indexPlate) 
             || (indexEmpty - 1 == indexPlate) 
             || (indexEmpty + 4 == indexPlate) 
             || (indexEmpty - 4 == indexPlate));
    }
    
    public void createScoreTable() // создаётся окно счёта
    {
        JPanel scoreTable = new JPanel(new GridLayout(2, 1));
        scoreTable.setBorder(BorderFactory.createTitledBorder("Score"));

        JLabel stepsLabel = new JLabel("Steps: " + stepsCount);
        scoreTable.add(stepsLabel);

        frame.getContentPane().add(scoreTable, BorderLayout.EAST);
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

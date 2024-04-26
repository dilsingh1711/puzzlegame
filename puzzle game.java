import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.Collections;

public class PuzzleGame extends JFrame implements ActionListener {

    private JButton[][] buttons;
    private JButton shuffleButton;
    private final int rows = 3;
    private final int cols = 3;

    public PuzzleGame() {
        setTitle("Puzzle Game");
        setSize(300, 300);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(new BorderLayout());

        JPanel puzzlePanel = new JPanel();
        puzzlePanel.setLayout(new GridLayout(rows, cols));
        buttons = new JButton[rows][cols];

        ArrayList<Integer> numbers = new ArrayList<>();
        for (int i = 0; i < rows * cols; i++) {
            numbers.add(i);
        }
        Collections.shuffle(numbers);

        for (int i = 0; i < rows; i++) {
            for (int j = 0; j < cols; j++) {
                int number = numbers.get(i * cols + j);
                buttons[i][j] = new JButton(Integer.toString(number + 1));
                buttons[i][j].addActionListener(this);
                puzzlePanel.add(buttons[i][j]);
            }
        }

        shuffleButton = new JButton("Shuffle");
        shuffleButton.addActionListener(this);

        add(puzzlePanel, BorderLayout.CENTER);
        add(shuffleButton, BorderLayout.SOUTH);

        setVisible(true);
    }

    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == shuffleButton) {
            shuffleTiles();
        } else {
            JButton button = (JButton) e.getSource();
            int row = -1, col = -1;
            for (int i = 0; i < rows; i++) {
                for (int j = 0; j < cols; j++) {
                    if (buttons[i][j] == button) {
                        row = i;
                        col = j;
                        break;
                    }
                }
            }
            moveTile(row, col);
            if (isSolved()) {
                JOptionPane.showMessageDialog(this, "Congratulations! You solved the puzzle!");
            }
        }
    }

    private void shuffleTiles() {
        ArrayList<Integer> numbers = new ArrayList<>();
        for (int i = 0; i < rows * cols; i++) {
            numbers.add(i);
        }
        Collections.shuffle(numbers);

        for (int i = 0; i < rows; i++) {
            for (int j = 0; j < cols; j++) {
                int number = numbers.get(i * cols + j);
                buttons[i][j].setText(Integer.toString(number + 1));
            }
        }
    }

    private void moveTile(int row, int col) {
        if (row > 0 && buttons[row - 1][col].getText().isEmpty()) {
            swapTiles(row, col, row - 1, col);
        } else if (row < rows - 1 && buttons[row + 1][col].getText().isEmpty()) {
            swapTiles(row, col, row + 1, col);
        } else if (col > 0 && buttons[row][col - 1].getText().isEmpty()) {
            swapTiles(row, col, row, col - 1);
        } else if (col < cols - 1 && buttons[row][col + 1].getText().isEmpty()) {
            swapTiles(row, col, row, col + 1);
        }
    }

    private void swapTiles(int row1, int col1, int row2, int col2) {
        String temp = buttons[row1][col1].getText();
        buttons[row1][col1].setText(buttons[row2][col2].getText());
        buttons[row2][col2].setText(temp);
    }

    private boolean isSolved() {
        for (int i = 0; i < rows; i++) {
            for (int j = 0; j < cols; j++) {
                int expectedValue = i * cols + j + 1;
                if (!buttons[i][j].getText().equals(Integer.toString(expectedValue))) {
                    return false;
                }
            }
        }
        return true;
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(new Runnable() {
            public void run() {
                new PuzzleGame();
            }
        });
    }
}

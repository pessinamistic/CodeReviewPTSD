package snakeandladder.src.visualization;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.io.IOException;
import java.nio.file.*;
import java.util.List;
import snakeandladder.src.model.Cell;
import snakeandladder.src.model.Player;

public class SnlBoardVisualizer {
  private final JFrame frame;
  private final JPanel boardPanel;
  private final List<Cell> cells;
  private final List<Player> players;

  public SnlBoardVisualizer(List<Cell> cells, List<Player> players) {
    this.cells = cells;
    this.players = players;

    frame = new JFrame("Snake and Ladder Board");
    frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    frame.setSize(800, 800);

    boardPanel = new JPanel(new GridLayout(10, 10));
    createBoard();

    frame.add(boardPanel, BorderLayout.CENTER);

    // Add controls at the bottom
    JPanel controlPanel = new JPanel();
    JButton exportButton = new JButton("Export Board to HTML");
    exportButton.addActionListener(e -> exportBoardToHtml());
    controlPanel.add(exportButton);

    frame.add(controlPanel, BorderLayout.SOUTH);
  }

  private void createBoard() {
    boardPanel.removeAll();

    // Creating the board in snake pattern (1-100)
    for (int row = 9; row >= 0; row--) {
      for (int col = 0; col < 10; col++) {
        int cellIndex;

        // For even rows (0-indexed), go right to left
        if (row % 2 == 0) {
          cellIndex = row * 10 + 10 - col;
        } else {
          // For odd rows (0-indexed), go left to right
          cellIndex = row * 10 + col + 1;
        }

        JPanel cellPanel = createCellPanel(cellIndex);
        boardPanel.add(cellPanel);
      }
    }

    boardPanel.revalidate();
    boardPanel.repaint();
  }

  private JPanel createCellPanel(int cellIndex) {
    JPanel panel = new JPanel(new BorderLayout());
    panel.setBorder(BorderFactory.createLineBorder(Color.BLACK));

    // Get cell data
    Cell cell = cells.get(cellIndex);

    // Set background color based on cell type
    if (cell.isSnake()) {
      panel.setBackground(new Color(255, 200, 200)); // Light red for snakes
    } else if (cell.isLadder()) {
      panel.setBackground(new Color(200, 255, 200)); // Light green for ladders
    } else {
      panel.setBackground(new Color(255, 255, 200)); // Light yellow for normal cells
    }

    // Add cell number
    JLabel numberLabel = new JLabel(String.valueOf(cellIndex));
    numberLabel.setHorizontalAlignment(SwingConstants.CENTER);
    panel.add(numberLabel, BorderLayout.NORTH);

    // Add move-to info for snakes and ladders
    if (cell.isSnake() || cell.isLadder()) {
      JLabel moveLabel = new JLabel("→ " + cell.getMoveTo());
      moveLabel.setHorizontalAlignment(SwingConstants.CENTER);
      moveLabel.setForeground(cell.isSnake() ? Color.RED : Color.GREEN);
      panel.add(moveLabel, BorderLayout.CENTER);
    }

    // Check for players on this cell
    JPanel playersPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT, 2, 0));
    playersPanel.setOpaque(false);

    for (Player player : players) {
      if (player.getPosition().getCellNumber() == cellIndex) {
        JLabel playerDot = new JLabel("•");
        playerDot.setFont(new Font("Arial", Font.BOLD, 16));
        playerDot.setForeground(getPlayerColor(player));
        playerDot.setToolTipText(player.getName());
        playersPanel.add(playerDot);
      }
    }

    panel.add(playersPanel, BorderLayout.SOUTH);

    return panel;
  }

  private Color getPlayerColor(Player player) {
    // Assign different colors based on player index
    int playerIndex = players.indexOf(player);
    switch (playerIndex % 3) {
      case 0: return Color.BLUE;
      case 1: return new Color(128, 0, 128); // Purple
      case 2: return Color.ORANGE;
      default: return Color.BLACK;
    }
  }

  public void updateBoard() {
    createBoard();
  }

  public void show() {
    frame.setVisible(true);
  }

  private void exportBoardToHtml() {
    StringBuilder html = new StringBuilder();
    html.append("<!DOCTYPE html>\n");
    html.append("<html lang=\"en\">\n");
    html.append("<head>\n");
    html.append("  <meta charset=\"UTF-8\">\n");
    html.append("  <title>Snake and Ladder Board</title>\n");
    html.append("  <style>\n");
    html.append("    body { font-family: Arial, sans-serif; }\n");
    html.append("    .board { display: flex; flex-direction: column; width: 800px; border: 2px solid #333; }\n");
    html.append("    .row { display: flex; }\n");
    html.append("    .cell { width: 80px; height: 80px; box-sizing: border-box; border: 1px solid #666; display: flex; flex-direction: column; justify-content: space-between; padding: 5px; position: relative; }\n");
    html.append("    .normal { background-color: #ffffcc; }\n");
    html.append("    .snake { background-color: #ffcccc; color: #990000; }\n");
    html.append("    .ladder { background-color: #ccffcc; color: #006600; }\n");
    html.append("    .cell-number { font-weight: bold; }\n");
    html.append("    .move-to { font-size: 0.8em; }\n");
    html.append("    .player-dot { position: absolute; top: 5px; right: 5px; font-size: 16px; font-weight: bold; }\n");
    html.append("  </style>\n");
    html.append("</head>\n");
    html.append("<body>\n");
    html.append("  <h1>Snake and Ladder Board</h1>\n");
    html.append("  <div class=\"board\">\n");

    // Creating the board in snake pattern (1-100)
    for (int row = 9; row >= 0; row--) {
      html.append("    <div class=\"row\">\n");

      for (int col = 0; col < 10; col++) {
        int cellIndex;

        // For even rows (0-indexed), go right to left
        if (row % 2 == 0) {
          cellIndex = row * 10 + 10 - col;
        } else {
          // For odd rows (0-indexed), go left to right
          cellIndex = row * 10 + col + 1;
        }

        // Get cell data
        Cell cell = cells.get(cellIndex);

        String cellClass = cell.isSnake() ? "snake" : (cell.isLadder() ? "ladder" : "normal");

        html.append("      <div class=\"cell ").append(cellClass).append("\">\n");
        html.append("        <div class=\"cell-number\">").append(cellIndex).append("</div>\n");

        // Add move-to info for snakes and ladders
        if (cell.isSnake() || cell.isLadder()) {
          html.append("        <div class=\"move-to\">→ ").append(cell.getMoveTo()).append("</div>\n");
        }

        // Check for players on this cell
        for (Player player : players) {
          if (player.getPosition().getCellNumber() == cellIndex) {
            String color;
            int playerIndex = players.indexOf(player);
            switch (playerIndex % 3) {
              case 0: color = "blue"; break;
              case 1: color = "purple"; break;
              case 2: color = "orange"; break;
              default: color = "black"; break;
            }

            html.append("        <div class=\"player-dot\" style=\"color: ").append(color).append(";\" title=\"")
                    .append(player.getName()).append("\">•</div>\n");
          }
        }

        html.append("      </div>\n");
      }

      html.append("    </div>\n");
    }

    html.append("  </div>\n");

    // Add legend
    html.append("  <div style=\"margin-top: 20px;\">\n");
    html.append("    <h3>Legend:</h3>\n");
    html.append("    <div><span style=\"display: inline-block; width: 20px; height: 20px; background-color: #ffffcc; border: 1px solid #666;\"></span> Normal Cell</div>\n");
    html.append("    <div><span style=\"display: inline-block; width: 20px; height: 20px; background-color: #ffcccc; border: 1px solid #666;\"></span> Snake</div>\n");
    html.append("    <div><span style=\"display: inline-block; width: 20px; height: 20px; background-color: #ccffcc; border: 1px solid #666;\"></span> Ladder</div>\n");
    html.append("  </div>\n");

    // Add snakes and ladders list
    html.append("  <div style=\"margin-top: 20px; display: flex;\">\n");
    html.append("    <div style=\"margin-right: 40px;\">\n");
    html.append("      <h3 style=\"color: #990000;\">Snakes:</h3>\n");
    html.append("      <ul>\n");

    for (Cell cell : cells) {
      if (cell.isSnake()) {
        html.append("        <li>From ").append(cell.getCellNumber()).append(" to ").append(cell.getMoveTo()).append("</li>\n");
      }
    }

    html.append("      </ul>\n");
    html.append("    </div>\n");
    html.append("    <div>\n");
    html.append("      <h3 style=\"color: #006600;\">Ladders:</h3>\n");
    html.append("      <ul>\n");

    for (Cell cell : cells) {
      if (cell.isLadder()) {
        html.append("        <li>From ").append(cell.getCellNumber()).append(" to ").append(cell.getMoveTo()).append("</li>\n");
      }
    }

    html.append("      </ul>\n");
    html.append("    </div>\n");
    html.append("  </div>\n");

    // Add player positions
    html.append("  <div style=\"margin-top: 20px;\">\n");
    html.append("    <h3>Player Positions:</h3>\n");
    html.append("    <ul>\n");

    for (Player player : players) {
      html.append("      <li style=\"color: ");
      int playerIndex = players.indexOf(player);
      switch (playerIndex % 3) {
        case 0: html.append("blue"); break;
        case 1: html.append("purple"); break;
        case 2: html.append("orange"); break;
        default: html.append("black"); break;
      }
      html.append(";\">").append(player.getName()).append(": Position ").append(player.getPosition().getCellNumber()).append("</li>\n");
    }

    html.append("    </ul>\n");
    html.append("  </div>\n");

    html.append("</body>\n");
    html.append("</html>");

    // Save HTML file
    try {
      Files.write(Paths.get("snake_and_ladder_board.html"), html.toString().getBytes());
      JOptionPane.showMessageDialog(frame, "Board exported to snake_and_ladder_board.html", "Export Successful", JOptionPane.INFORMATION_MESSAGE);

      // Try to open the file in the default browser
      try {
        Desktop.getDesktop().browse(Paths.get("snake_and_ladder_board.html").toUri());
      } catch (Exception ex) {
        // Silently ignore if we can't open the browser
      }
    } catch (IOException e) {
      JOptionPane.showMessageDialog(frame, "Failed to export board: " + e.getMessage(), "Export Failed", JOptionPane.ERROR_MESSAGE);
    }
  }
}
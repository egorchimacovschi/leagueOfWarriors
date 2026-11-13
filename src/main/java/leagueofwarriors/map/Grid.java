package leagueofwarriors.map;

import java.util.ArrayList;

public class Grid extends ArrayList<ArrayList<Cell>> {

    private int playerX;
    private int playerY;

    private Grid() {
        super();
    }

    public static Grid generateRandom() {
        int width = (int) (Math.random() * 8) + 3;
        int height = (int) (Math.random() * 8) + 3;

        Grid grid = new Grid();

        for (int row = 0; row < height; row++) {
            ArrayList<Cell> rowCells = new ArrayList<>();
            for (int col = 0; col < width; col++) {
                rowCells.add(new Cell(col, row, CellEntityType.VOID));
            }
            grid.add(rowCells);
        }

        int startX = (int) (Math.random() * width);
        int startY = (int) (Math.random() * height);
        grid.get(startY).get(startX).setType(CellEntityType.PLAYER);
        grid.playerX = startX;
        grid.playerY = startY;

        grid.placeRandomly(CellEntityType.PORTAL, 1, width, height);
        grid.placeRandomly(CellEntityType.SANCTUARY, 2, width, height);
        grid.placeRandomly(CellEntityType.ENEMY, 4, width, height);

        return grid;
    }

    private void placeRandomly(CellEntityType type, int count, int width, int height) {
        int placed = 0;
        while (placed < count) {
            int x = (int) (Math.random() * width);
            int y = (int) (Math.random() * height);
            Cell cell = get(y).get(x);
            if (cell.getType() == CellEntityType.VOID) {
                cell.setType(type);
                placed++;
            }
        }
    }

    public CellEntityType goNorth() throws ImpossibleMove {
        return move(playerX, playerY - 1);
    }

    public CellEntityType goSouth() throws ImpossibleMove {
        return move(playerX, playerY + 1);
    }

    public CellEntityType goWest() throws ImpossibleMove {
        return move(playerX - 1, playerY);
    }

    public CellEntityType goEast() throws ImpossibleMove {
        return move(playerX + 1, playerY);
    }

    private CellEntityType move(int newX, int newY) throws ImpossibleMove {
        if (newY < 0 || newY >= size() || newX < 0 || newX >= get(newY).size()) {
            throw new ImpossibleMove("Can't move off the map");
        }
        CellEntityType previousType = get(newY).get(newX).getType();
        get(playerY).get(playerX).setType(CellEntityType.VOID);
        get(newY).get(newX).setType(CellEntityType.PLAYER);
        get(newY).get(newX).markVisited();
        playerX = newX;
        playerY = newY;
        return previousType;
    }

    public void display() {
        for (ArrayList<Cell> row : this) {
            StringBuilder line = new StringBuilder();
            for (Cell cell : row) {
                line.append(switch (cell.getType()) {
                    case PLAYER -> "P";
                    case VOID -> ".";
                    case ENEMY -> "E";
                    case SANCTUARY -> "S";
                    case PORTAL -> "O";
                }).append(" ");
            }
            System.out.println(line);
        }
    }
}

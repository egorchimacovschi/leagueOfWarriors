package leagueofwarriors.map;

public class Cell {
    private final int x;
    private final int y;
    private CellEntityType type;
    private boolean visited;

    public Cell(int x, int y, CellEntityType type) {
        this.x = x;
        this.y = y;
        this.type = type;
        visited = false;
    }

    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }

    public CellEntityType getType() {
        return type;
    }

    public void setType(CellEntityType type) {
        this.type = type;
    }

    public boolean isVisited() {
        return visited;
    }

    public void markVisited() {
        this.visited = true;
    }
}

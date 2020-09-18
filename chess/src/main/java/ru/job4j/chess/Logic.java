package ru.job4j.chess;

import ru.job4j.chess.firuges.Cell;
import ru.job4j.chess.firuges.Figure;
import java.util.Arrays;

public final class Logic {
    private final Figure[] figures = new Figure[32];
    private int index = 0;

    public void add(Figure figure) {
        figures[index++] = figure;
    }

    public boolean move(Cell source, Cell dest)
            throws FigureNotFoundException, ImpossibleMoveException, OccupiedCellException {
        boolean rst = false;
        int index = findBy(source);
        if (index == -1) {
            throw new FigureNotFoundException("Figure not found");
        }
        Cell[] steps = figures[index].way(dest);
        if (steps.length == 0) {
            throw new ImpossibleMoveException("Impossible move");
        }
        if (free(steps)) {
            rst = true;
            figures[index] = figures[index].copy(dest);
        }
        return rst;
    }

    private boolean free(Cell[] steps) throws OccupiedCellException {
        for (Cell step : steps) {
            if (findBy(step) != -1) {
                throw new OccupiedCellException("Cell is occupied");
            }
        }
        return true;
    }

    public void clean() {
        Arrays.fill(figures, null);
        index = 0;
    }

    private int findBy(Cell cell) {
        int rsl = -1;
        for (int index = 0; index != figures.length; index++) {
            Figure figure = figures[index];
            if (figure != null && figure.position().equals(cell)) {
                rsl = index;
                break;
            }
        }
        return rsl;
    }
}

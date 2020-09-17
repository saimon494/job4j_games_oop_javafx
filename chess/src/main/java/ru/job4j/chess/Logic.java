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

    public void move(Cell source, Cell dest)
            throws FigureNotFoundException, ImpossibleMoveException, OccupiedCellException {
        int index = findBy(source);
        if (index == -1) {
            throw new FigureNotFoundException("Figure not found");
        }
        Cell[] steps = figures[index].way(dest);
        if (steps.length == 0) {
            throw new ImpossibleMoveException("Impossible move");
        }
        free(steps);
        figures[index] = figures[index].copy(dest);
    }

    private boolean free(Cell[] steps) throws OccupiedCellException, FigureNotFoundException {
        for (int i = 0; i < figures.length; i++) {
            if (findBy(steps[i]) != -1) {
                throw new OccupiedCellException("Cell is occupied");
            }
        }
        return true;
    }

    public void clean() {
        Arrays.fill(figures, null);
        index = 0;
    }

    private int findBy(Cell cell) throws FigureNotFoundException {
        int rsl = -1;
        for (int index = 0; index != figures.length; index++) {
            Figure figure = figures[index];
            if (figure != null && figure.position().equals(cell)) {
                rsl = index;
            } else {
                throw new FigureNotFoundException("Figure not found");
            }
        }
        return rsl;
    }
}

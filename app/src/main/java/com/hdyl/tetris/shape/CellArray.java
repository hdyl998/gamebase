package com.hdyl.tetris.shape;

import com.hdyl.tetris.PositionCell;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Administrator on 2017/5/7.
 */

public class CellArray {
    public PositionCell[] cells;


    public CellArray(String cellsString) {
        this.cells = createFromString(cellsString);
    }


    private PositionCell[] createFromString(String cellsString) {
        List<PositionCell> list = new ArrayList<>(4);
        String[] arr = cellsString.split(",");
        int yCount = 0;
        for (String string : arr) {
            int xCount = 0;
            for (char ch : string.toCharArray()) {
                if (ch == '1')
                    list.add(new PositionCell(yCount, xCount).setFull(true));
                xCount++;
            }
            yCount++;
        }
        PositionCell cells[] = new PositionCell[list.size()];
        return list.toArray(cells);
    }


    public void setCellsResIndex(int res) {
        for (PositionCell cell : cells) {
            cell.setResIndex(res);
        }
    }

    public PositionCell[] getCells() {
        return cells;
    }

    public int getMaxY() {
        int maxY = 0;
        for (PositionCell cell : getCells()) {
            maxY = Math.max(maxY, cell.getY());
        }
        return maxY;
    }
}

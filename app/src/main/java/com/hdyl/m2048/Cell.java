package com.hdyl.m2048;

public class Cell implements Cloneable {
    private int value;//值
    private int color;//颜色

    public int x,y;

    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }

    public void setX(int x) {
        this.x = x;
    }

    public void setY(int y) {
        this.y = y;
    }

    public Cell() {
    }
    public Cell(int value) {
        setValue(value);
    }


    public void exChangeValue(Cell cell) {
        int i = this.getValue();
        this.setValue(cell.getValue());
        cell.setValue(i);
    }

    /***
     * 返回当前得分
     *
     * @return
     */
    public int addValue() {
        this.setValue(this.getValue() * 2);
        return this.value;
    }

    public void setValue(int value) {
        this.value = value;
        this.color = GameColor.getValueColor(this.value);
    }

    public int getValue() {
        return value;
    }

    public boolean isBigValue() {
        return value > 9999;
    }


    public boolean isValueZero() {
        return 0 == value;
    }

    public int getColor() {
        return color;
    }

    public boolean equals(Cell obj) {
        if (this.getValue() != 0 && this.getValue() == obj.getValue())
            return true;
        return false;
    }

    @Override
    public String toString() {
        return "MineCell{" +
                "value=" + value +
                ", color=" + color +
                ", x=" + x +
                ", y=" + y +
                '}';
    }
}

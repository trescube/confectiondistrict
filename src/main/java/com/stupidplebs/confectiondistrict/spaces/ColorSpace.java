package com.stupidplebs.confectiondistrict.spaces;

import java.util.Objects;

import com.stupidplebs.confectiondistrict.Color;

public class ColorSpace implements Space {
    private final Color color;
    private final Boolean sticky;
    private final Integer jumpAheadCount;
    private final Boolean loseATurn;

    private ColorSpace(final Color color, final Boolean sticky, final Integer jumpAheadCount, final Boolean loseATurn) {
        if (null == color) {
            throw new NullPointerException("color cannot be null");
        }
        if (null == sticky) {
            throw new NullPointerException("sticky cannot be null");
        }
        if (null == jumpAheadCount) {
            throw new NullPointerException("jumpAheadCount cannot be null");
        }
        if (jumpAheadCount < 0) {
            throw new IllegalArgumentException("jumpAheadCount cannot be negative");
        }

        this.color = color;
        this.sticky = sticky;
        this.jumpAheadCount = jumpAheadCount;
        this.loseATurn = loseATurn;

    }

    public ColorSpace(final Color color, final Boolean sticky) {
        this(color, sticky, 0, false);
    }

    public ColorSpace(final Color color, final Integer trailheadSpaceCount) {
        this(color, false, trailheadSpaceCount, false);
    }

    public ColorSpace(final Color color) {
        this(color, false, 0, false);
    }

    public static Space loseATurn(final Color color) {
        return new ColorSpace(color, false, 0, true);
    }
    
    public Color getColor() {
        return color;
    }

    public Boolean isSticky() {
        return sticky;
    }

    public Boolean isTrailhead() {
        return jumpAheadCount > 0;
    }

    public Integer getTrailheadSpaceCount() {
        return jumpAheadCount;
    }

    @Override
    public boolean equals(final Object obj) {
        if (null == obj) {
            return false;
        }
        if (this == obj) {
            return true;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }

        final ColorSpace other = (ColorSpace) obj;

        return color == other.color && 
                sticky == other.sticky && 
                jumpAheadCount == other.jumpAheadCount &&
                loseATurn == other.loseATurn;

    }

    @Override
    public int hashCode() {
        return Objects.hash(color, sticky, jumpAheadCount, loseATurn);
    }

    @Override
    public String toString() {
        return "ColorSpace{" + "color=" + color + ", " + "sticky=" + sticky + ", " + "jumpAheadCount=" + jumpAheadCount
                + ", loseATurn=" + loseATurn + "}";
    }

}

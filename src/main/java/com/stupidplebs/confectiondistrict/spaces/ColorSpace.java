package com.stupidplebs.confectiondistrict.spaces;

import java.util.Objects;

import com.stupidplebs.confectiondistrict.Color;

public class ColorSpace implements Space {
    private final Color color;
    private final Boolean sticky;
    private final Integer jumpAheadCount;
    private final Boolean loseATurn;

    private ColorSpace(final Color color, final Boolean sticky, final Integer jumpAheadCount, final Boolean loseATurn) {
        this.color = color;
        this.sticky = sticky;
        this.jumpAheadCount = jumpAheadCount;
        this.loseATurn = loseATurn;
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

    public Boolean isLoseATurn() {
        return loseATurn;
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
        return new StringBuilder("ColorSpace{").
                append("color=").append(color).append(", ").
                append("sticky=").append(sticky).append(", ").
                append("jumpAheadCount=").append(jumpAheadCount).append(", ").
                append("loseATurn=").append(loseATurn).
                append("}").toString();
    }

    public static class Builder {
        private final Color color;
        private Boolean sticky = false;
        private Integer jumpAheadCount = 0;
        private Boolean loseATurn = false;
        
        public Builder(final Color color) {
            if (null == color) {
                throw new NullPointerException("color cannot be null");
            }
            
            this.color = color;
        }
        
        public Builder sticky() {
            this.sticky = true;
            return this;
        }
        
        public Builder loseATurn() {
            this.loseATurn = true;
            return this;
        }
        
        public Builder trailhead(final Integer jumpAheadCount) {
            if (null == jumpAheadCount) {
                throw new NullPointerException("jumpAheadCount cannot be null");
            }
            if (jumpAheadCount <= 0) {
                throw new IllegalArgumentException("jumpAheadCount must be positive");
            }
            
            this.jumpAheadCount = jumpAheadCount;
            return this;
        }
        
        public ColorSpace build() {
            return new ColorSpace(color, sticky, jumpAheadCount, loseATurn);
        }
        
    }
    
}

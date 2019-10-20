package HexTest;

import java.awt.*;
import javax.swing.*;

public class hmech
{
    private static int BORDERS=50;	//default number of pixels for the border.

    private static int s=0;	// length of one side
    private static int t=0;	// short side of 30o triangle outside of each hex
    private static int r=0;	// radius of inscribed circle (centre to middle of each side). r= h/2
    private static int h=0;	// height. Distance between centres of two adjacent hexes. Distance between two opposite sides in a hex.

    public static void setBorders(int b){
        BORDERS=b;
    }

    public static void setSide(int side) {
        s=side;
        t =  (int) (s / 2);
        r =  (int) (s/2 * Math.sqrt(3));
        h = 2*r;
    }

    public static Polygon hex (int x0, int y0) {

        if (s == 0) {
            System.out.println("ERROR: size of hex has not been set");
            return new Polygon();
        }

        int[] cx,cy;
        cx = new int[] {x0, x0, x0 +r, x0 +h, x0 +h, x0 +r};
        cy = new int[] {y0, y0 +s, y0 +s+t, y0 +s, y0, y0 -t};

        return new Polygon(cx,cy,6);
    }

    public static void drawHex(int i, int j, Graphics2D g2) {
        int x = i*h + (j%2) * r;
        int y = j*(s+t);
        Polygon poly = hex(x,y);
        g2.setColor(hgame.COLOURCELL);
        g2.fillPolygon(poly);
        g2.setColor(hgame.COLOURGRID);
        g2.drawPolygon(poly);
    }

    public static void fillHex(int i, int j, int n, Graphics2D g2) {
        char c='o';
        int x = i*h + (j%2) * r;
        int y = j*(s+t);
        if (n < 0) {
            g2.setColor(hgame.COLOURONE);
            g2.fillPolygon(hex(x,y));
            g2.setColor(hgame.COLOURONETXT);
            c = (char)(-n);
            g2.drawString(""+c, x+r, y+r+4);
        }
        if (n > 0) {
            g2.setColor(hgame.COLOURTWO);
            g2.fillPolygon(hex(x,y));
            g2.setColor(hgame.COLOURTWOTXT);
            c = (char)n;
            g2.drawString(""+c, x+r, y+r+4);
        }
    }

    //This function changes pixel location from a mouse click to a hex grid location
    /*****************************************************************************
     * Name: pxtoHex (pixel to hex)
     * Parameters: mx, my. These are the co-ordinates of mouse click.
     * Returns: point. A point containing the coordinates of the hex that is clicked in.
     If the point clicked is not a valid hex (ie. on the borders of the board, (-1,-1) is returned.
     * Function: This only works for hexes in the FLAT orientation. The POINTY orientation would require
     a whole other function (different math).
     It takes into account the size of borders.
     It also works with XYVertex being True or False.
     *****************************************************************************/
    public static Point pxtoHex(int mx, int my) {
        Point p = new Point(-1,-1);

        //correction for BORDERS and XYVertex
        mx -= BORDERS;
        my -= BORDERS;

        int x = (int) (mx / (s+t)); //this gives a quick value for x. It works only on odd cols and doesn't handle the triangle sections. It assumes that the hexagon is a rectangle with width s+t (=1.5*s).
        int y = (int) ((my - (x%2)*r)/h); //this gives the row easily. It needs to be offset by h/2 (=r)if it is in an even column

        /******FIX for clicking in the triangle spaces (on the left side only)*******/
        //dx,dy are the number of pixels from the hex boundary. (ie. relative to the hex clicked in)
        int dx = mx - x*(s+t);
        int dy = my - y*h;

        if (my - (x%2)*r < 0) return p; // prevent clicking in the open halfhexes at the top of the screen

        //System.out.println("dx=" + dx + " dy=" + dy + "  > " + dx*r/t + " <");

        //even columns
        if (x%2==0) {
            if (dy > r) {	//bottom half of hexes
                if (dx * r /t < dy - r) {
                    x--;
                }
            }
            if (dy < r) {	//top half of hexes
                if ((t - dx)*r/t > dy ) {
                    x--;
                    y--;
                }
            }
        } else {  // odd columns
            if (dy > h) {	//bottom half of hexes
                if (dx * r/t < dy - h) {
                    x--;
                    y++;
                }
            }
            if (dy < h) {	//top half of hexes
                //System.out.println("" + (t- dx)*r/t +  " " + (dy - r));
                if ((t - dx)*r/t > dy - r) {
                    x--;
                }
            }
        }
        p.x=x;
        p.y=y;
        return p;
    }
}
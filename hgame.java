package HexTest;

import java.awt.*;
import javax.swing.*;
import java.awt.event.*;

public class hgame
{
    private hgame() {
        initGame();
        createAndShowGUI();
    }

    public static void main(String[] args)
    {
        SwingUtilities.invokeLater(new Runnable() {
            public void run() {
                new hgame();
            }
        });
    }

    //constants and global variables
    final static Color COLOURBACK =  new Color(134, 61, 1,200);
    final static Color COLOURCELL =  new Color(27, 113, 58,180);
    final static Color COLOURGRID =  Color.BLACK;
    final static Color COLOURONE = new Color(151, 78, 8,200);
    final static Color COLOURONETXT = Color.BLUE;
    final static Color COLOURTWO = new Color(174, 247, 249,200);
    final static Color COLOURTWOTXT = new Color(255,100,255);
    final static int EMPTY = 0;
    final static int BHSIZE = 32; //how many hexes horizontaly
    final static int BVSIZE = 25; //how many hexes verticaly
    final static int HEXSIZE = 25;	//hex size in pixels = 25.
    final static int SCRNWIDHT = (int) (((HEXSIZE / 2) * Math.sqrt(3) * 2) * (BHSIZE));
    final static int SCRNHEIGHT = (int) (BVSIZE+1) * (HEXSIZE * 3/2) + 10;
    final static int BORDERS = 15;

    int[][] board = new int[BHSIZE][BVSIZE];

    void initGame(){

        hmech.setSide(HEXSIZE);
        hmech.setBorders(BORDERS);

        for (int i=0;i<BHSIZE;i++) {
            for (int j=0;j<BVSIZE;j++) {
                board[i][j]=EMPTY;
            }
        }

        //set up board here
        board[0][0] = (int)'A';
        board[0][1] = (int)'A';
        board[1][1] = 1;
        board[1][0] = (int)'A';
        board[10][10] = (int)'A';
        board[0][BVSIZE-1] = (int)'B';
        board[4][4] = -(int)'C';


        board[0][BVSIZE-1] = 'C';
        board[0][BVSIZE-2] = 'c';
        board[1][BVSIZE-1] = 'c';
        board[BHSIZE-1][BVSIZE-1] = 'D';
        board[BHSIZE-2][BVSIZE-1] = 'd';
        board[BHSIZE-1][BVSIZE-2] = 'd';
        for(int i = 0; i < BHSIZE; i++){
            board[i][0] = (int)'A';
        }
    }

    private void createAndShowGUI()
    {
        DrawingPanel panel = new DrawingPanel();

        JFrame frame = new JFrame("Shit Crusaders");
        frame.setDefaultCloseOperation( JFrame.EXIT_ON_CLOSE );
        Container content = frame.getContentPane();
        content.add(panel);
        frame.setSize( SCRNWIDHT, SCRNHEIGHT);
        frame.setResizable(false);
        frame.setLocationRelativeTo( null );
        frame.setVisible(true);
    }


    class DrawingPanel extends JPanel
    {
        //mouse variables here
        //Point mPt = new Point(0,0);

        public DrawingPanel()
        {
            setBackground(COLOURBACK);

            MyMouseListener ml = new MyMouseListener();
            addMouseListener(ml);
        }

        public void paintComponent(Graphics g)
        {
            Graphics2D g2 = (Graphics2D)g;
            g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
            g.setFont(new Font("TimesRoman", Font.PLAIN, 20));
            super.paintComponent(g2);
            //draw grid
            for (int i=0;i<BHSIZE;i++) {
                for (int j=0;j<BVSIZE;j++) {
                    hmech.drawHex(i,j,g2);
                }
            }
            //fill in hexes
            for (int i=0;i<BHSIZE;i++) {
                for (int j=0;j<BVSIZE;j++) {
                    hmech.fillHex(i,j,board[i][j],g2);
                }
            }
        }

        class MyMouseListener extends MouseAdapter	{	//inner class inside DrawingPanel
            public void mouseClicked(MouseEvent e) {
                int x = e.getX();
                int y = e.getY();

                Point p = new Point( hmech.pxtoHex(e.getX(),e.getY()) );
                if (p.x < 0 || p.y < 0 || p.x >= BHSIZE || p.y >= BVSIZE) return;

                //What do you want to do when a hexagon is clicked?
                board[p.x][p.y] = (int)'X';
                repaint();
            }
        } //end of MyMouseListener class
    } // end of DrawingPanel class
}
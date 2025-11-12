import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.util.ArrayList;

public class SpaceInv extends JPanel {

    // Déjà refacto, voir dossier Game et Config
    private int vX=250,vY=450; // position vaisseau
    private ArrayList<Point> t=new ArrayList<Point>(); // tirs
    private ArrayList<Point> e=new ArrayList<Point>(); // ennemis
    private int score =0; // score
    private int tmr=0; // timer
    private Timer timer; // timer global

    public SpaceInv(){
        setFocusable(true);
        for(int i=0;i<5;i++) for(int j=0;j<10;j++) e.add(new Point(50+j*40,50+i*30));

        // Déjà refacto, voir dossier input
        addKeyListener(new KeyAdapter(){
            public void keyPressed(KeyEvent k){
                switch(k.getKeyCode()){
                    case KeyEvent.VK_LEFT: if(vX>0)vX-=10; break;
                    case KeyEvent.VK_RIGHT: if(vX<480)vX+=10; break;
                    case KeyEvent.VK_SPACE: t.add(new Point(vX+15,vY-10)); break;
                }
                repaint();
            }
        });

        // A faire
        timer = new Timer(50,new ActionListener(){
            public void actionPerformed(ActionEvent a){
                tmr++;
                ArrayList<Point> remT=new ArrayList<Point>();
                ArrayList<Point> remE=new ArrayList<Point>();

                for(Point p:t){ p.y-=10; if(p.y<0) remT.add(p); }

                for(Point pT:t){
                    for(Point pE:e){
                        if(pT.x>=pE.x && pT.x<=pE.x+30 && pT.y>=pE.y && pT.y<=pE.y+20){
                            remT.add(pT); remE.add(pE); score +=10;
                        }
                    }
                }

                t.removeAll(remT); e.removeAll(remE);

                if(tmr%20==0){ for(Point p:e) p.y+=10; } // descente ennemis

                // Vérifie fin de partie
                for(Point p:e){
                    if(p.y>=vY){
                        timer.stop();
                        break;
                    }
                }

                repaint();
            }
        });
        timer.start();
    }

    // Déjà refacto, voir dossier UI
    public void paintComponent(Graphics g){
        super.paintComponent(g);
        g.setColor(Color.BLACK);
        g.fillRect(0,0,getWidth(),getHeight());

        g.setColor(Color.GREEN);
        g.fillRect(vX,vY,30,15); // vaisseau

        g.setColor(Color.RED);
        for(Point p:t) g.fillRect(p.x,p.y,5,10); // tirs

        g.setColor(Color.WHITE);
        for(Point p:e) g.fillRect(p.x,p.y,30,20); // ennemis

        g.setColor(Color.YELLOW);
        g.drawString("Score: "+ score,10,20);

        for(Point p:e){
            if(p.y>=vY){
                g.setColor(Color.RED);
                g.drawString("GAME OVER",200,250);
                break;
            }
        }
    }

    public static void main(String[] args){
        // Déjà refacto, voir dossier UI
        JFrame f=new JFrame("Space Invaders");
        f.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        f.setSize(100000,100000);
        f.add(new SpaceInv());
        f.setVisible(true);
    }
}

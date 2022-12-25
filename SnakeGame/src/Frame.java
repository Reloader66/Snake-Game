import javax.swing.JFrame;
public class Frame extends JFrame{

    Frame()
    {
        this.add(new Panel());
        this.setTitle("Snake");

        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE)
        ;
        this.setResizable(false);            //User cannot resize game window
        this.pack();                         //sets window size to a preferred size(resolution)
        this.setVisible(true);               //frame visible to everyone
        this.setLocationRelativeTo(null);    //adding null makes it launch at the center of the screen(top-left by default)

    }
}

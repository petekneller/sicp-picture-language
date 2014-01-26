import java.awt.{Graphics, Dimension, Color}
import javax.swing.{BorderFactory, JPanel, JFrame, SwingUtilities}

object Main {
  def main(args: Array[String]) = {
    SwingUtilities.invokeLater(new Runnable() {
      def run() {
        createAndShowGUI();
      }
    })
  }

  private def createAndShowGUI() = {
    println("Created GUI on EDT? " + SwingUtilities.isEventDispatchThread())
    val f: JFrame = new JFrame("Swing Paint Demo")
    f.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE)
    f.add(new MyPanel())
    f.pack()
    f.setVisible(true)
  }

  class MyPanel extends JPanel {

    setBorder(BorderFactory.createLineBorder(Color.black))

    override def getPreferredSize(): Dimension = new Dimension(250,200)

    override def paintComponent(g: Graphics) {
      super.paintComponent(g)

      // Draw Text
      g.drawString("This is my custom Panel!",10,20)
    }

  }
}


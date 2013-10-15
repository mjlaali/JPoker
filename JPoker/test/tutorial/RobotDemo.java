package tutorial;

import java.awt.AWTException;
import java.awt.BorderLayout;
import java.awt.Font;
import java.awt.Graphics2D;
import java.awt.HeadlessException;
import java.awt.Image;
import java.awt.RenderingHints;
import java.awt.Robot;
import java.awt.Toolkit;
import java.awt.datatransfer.Clipboard;
import java.awt.datatransfer.DataFlavor;
import java.awt.datatransfer.Transferable;
import java.awt.datatransfer.UnsupportedFlavorException;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.InputEvent;
import java.awt.event.KeyEvent;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JTextArea;

public class RobotDemo extends JFrame implements ActionListener
{
	//Store Keystrokes in an array
                   static int keyInput[] = {KeyEvent.VK_0,KeyEvent.VK_A,};
                      //KeyEvent.VK_UP,KeyEvent.VK_UP,KeyEvent.VK_UP};

	static JTextArea ta = new JTextArea();
	
	static JButton bold = new JButton("Bold");

	public RobotDemo()
	{
		getContentPane().add(ta,BorderLayout.CENTER);
		 
		JPanel p = new JPanel();
		bold.addActionListener(this);
		p.add(bold);
		getContentPane().add(p,BorderLayout.SOUTH);
	}
	
	public static String getStrClip() throws HeadlessException, UnsupportedFlavorException, IOException{
		String data = (String) Toolkit.getDefaultToolkit()
                .getSystemClipboard().getData(DataFlavor.stringFlavor);
		return data;
	}

	public static String getClipboardContents() {
	    String result = "";
	    Clipboard clipboard = Toolkit.getDefaultToolkit().getSystemClipboard();
	    //odd: the Object param of getContents is not currently used
	    Transferable contents = clipboard.getContents(null);
	    boolean hasTransferableText =
	      (contents != null) &&
	      contents.isDataFlavorSupported(DataFlavor.stringFlavor)
	    ;
	    if (hasTransferableText) {
	      try {
	        result = (String)contents.getTransferData(DataFlavor.stringFlavor);
	      }
	      catch (UnsupportedFlavorException | IOException ex){
	        System.out.println(ex);
	        ex.printStackTrace();
	      }
	    }
	    return result;
	  }
	
	public static BufferedImage getBufferedImage(Image img) {
        if (img == null) return null;
        int w = img.getWidth(null);
        int h = img.getHeight(null);
        // draw original image to thumbnail image object and
        // scale it to the new size on-the-fly
        BufferedImage bufimg = new BufferedImage(w, h, BufferedImage.TYPE_INT_RGB);
        Graphics2D g2 = bufimg.createGraphics();
        g2.setRenderingHint(RenderingHints.KEY_INTERPOLATION, RenderingHints.VALUE_INTERPOLATION_BILINEAR);
        g2.drawImage(img, 0, 0, w, h, null);
        g2.dispose();
        return bufimg;
    }
	
	public static void main(String[] args) throws AWTException, IOException, UnsupportedFlavorException
	{
		RobotDemo rd = new RobotDemo();

		rd.setLocation(100,100);
		rd.setTitle("Robot Demo");
		rd.setSize(200,200);
		rd.show();

		String old = "";
		Robot robot = new Robot();
		for (int i = 0; i < 1000; ++i){
			System.out.println("****************************************");
			String strClip = getStrClip();
			if (!old.equals(strClip)){
				System.out.println("Clipboard contains: a new hand :D");
				old = strClip;
			}

			robot.delay(5000);
			robot.mousePress(InputEvent.BUTTON1_MASK);
			robot.mouseRelease(InputEvent.BUTTON1_MASK);
			robot.delay(200);
			robot.keyPress(KeyEvent.VK_META);
			robot.keyPress(KeyEvent.VK_A);
			robot.keyRelease(KeyEvent.VK_META);
			robot.keyRelease(KeyEvent.VK_A);
			
			robot.delay(200);
			robot.keyPress(KeyEvent.VK_META);
			robot.keyPress(KeyEvent.VK_C);
			robot.keyRelease(KeyEvent.VK_C);
			robot.keyRelease(KeyEvent.VK_META);
			
		}
		//This types the word 'robot' in the Textarea		

		robot.keyPress(KeyEvent.VK_META);
		robot.keyPress(KeyEvent.VK_SHIFT);
		robot.keyPress(KeyEvent.VK_CONTROL);
		robot.keyPress(KeyEvent.VK_3);

		robot.keyRelease(KeyEvent.VK_META);
		robot.keyRelease(KeyEvent.VK_SHIFT);
		robot.keyRelease(KeyEvent.VK_CONTROL);
		robot.keyRelease(KeyEvent.VK_3);

		System.out.println("RobotDemo.main()");
		robot.delay(2000);
		Clipboard clip = Toolkit.getDefaultToolkit().getSystemClipboard();

		ImageIcon IMG = new ImageIcon((BufferedImage)clip.getData(DataFlavor.imageFlavor));
		BufferedImage bImage = getBufferedImage(IMG.getImage());
		ImageIO.write(bImage, "png", new File("/tmp/test.png"));//		{
//			if(i > 0)
//		  	{
//		             robot.keyRelease(KeyEvent.VK_SHIFT);
//			}
//	
//	        	robot.keyPress(keyInput[i]);
//	    		robot.keyPress(KeyEvent.VK_WINDOWS);
//	    		robot.keyPress(KeyEvent.VK_SPACE);
//	    		
//			robot.delay(500);
//
//		}

		//The following clicks the button 'Bold' to get the text bolder
//		robot.mouseMove(180,280);

//		robot.delay(2000);

//		robot.mousePress(InputEvent.BUTTON1_MASK);
	
		//This delay keeps the button pressed for 2 seconds
//		robot.delay(2000);
	
//		robot.mouseRelease(InputEvent.BUTTON1_MASK);
	}

	public void actionPerformed(ActionEvent ae)
	{
		Font f = new Font("Times New Roman", Font.BOLD, 20);
		ta.setFont(f);
	}
}

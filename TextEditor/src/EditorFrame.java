import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JTextArea;
import javax.swing.event.DocumentEvent;
import javax.swing.filechooser.FileNameExtensionFilter;
import javax.swing.JScrollPane;
import javax.swing.JPopupMenu;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JFileChooser;
import javax.swing.JColorChooser;

import javax.swing.ImageIcon;
import javax.swing.text.BadLocationException;
import javax.swing.text.SimpleAttributeSet;
import javax.swing.text.StyleConstants;
import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics2D;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import javax.swing.JMenu;
import javax.swing.JMenuBar;

public class EditorFrame extends JFrame {
    private JTextArea textArea;
    private JFileChooser fileChooser;
    private JLabel statusLabel;
    private int wordCount = 0;

    public EditorFrame() {
        setTitle("Simple Text Editor");
        setSize(600, 400);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        // create text area with line wrapping and popup menu
        textArea = new JTextArea();
        textArea.setLineWrap(true); // enable line wrapping
        textArea.setFont(new Font(Font.SANS_SERIF, Font.PLAIN, 20));
    


        textArea.addMouseListener(new MouseAdapter() {
            public void mousePressed(MouseEvent e) {
                if (e.isPopupTrigger()) {
                    showPopupMenu(e.getX(), e.getY());
                }
            }
            public void mouseReleased(MouseEvent e) {
                if (e.isPopupTrigger()) {
                    showPopupMenu(e.getX(), e.getY());
                }
            }
        });
        JScrollPane scrollPane = new JScrollPane(textArea);

        // create file chooser
        fileChooser = new JFileChooser();
        
     // create status bar label
        statusLabel = new JLabel("Words: 0");

        // create menu bar and menus
        JMenuBar menuBar = new JMenuBar();
        JMenu fileMenu = new JMenu("File");
        JMenu editMenu = new JMenu("Edit");
        JMenu additionalMenu = new JMenu("Additional");

        // create menu items for File menu
        JMenuItem openItem = new JMenuItem("Open");
        JMenuItem saveItem = new JMenuItem("Save");
        JMenuItem exitItem = new JMenuItem("Exit");

        // add action listeners to menu items
        openItem.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                int result = fileChooser.showOpenDialog(EditorFrame.this);
                if (result == JFileChooser.APPROVE_OPTION) {
                    File file = fileChooser.getSelectedFile();
                    try {
                        FileReader reader = new FileReader(file);
                        textArea.read(reader, null);
                        reader.close();
                    } catch (IOException ex) {
                        ex.printStackTrace();
                    }
                }
            }
        });

        saveItem.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                int result = fileChooser.showSaveDialog(EditorFrame.this);
                if (result == JFileChooser.APPROVE_OPTION) {
                    File file = fileChooser.getSelectedFile();
                    try {
                        FileWriter writer = new FileWriter(file);
                        textArea.write(writer);
                        writer.close();
                    } catch (IOException ex) {
                        ex.printStackTrace();
                    }
                }
            }
        });

        exitItem.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                dispose();
            }
        });
        /*
        private void updateWordCount() {
            String text = textArea.getText();
            String[] words = text.split("\\s+");
            int wordCount = words.length;
            wordCountLabel.setText("Word Count: " + wordCount);
        }

        private void updateCharacterCount() {
            String text = textArea.getText();
            int characterCount = text.length();
            characterCountLabel.setText("Character Count: " + characterCount);
        }
  */
        // create menu items for Edit menu
        JMenuItem fontItem = new JMenuItem("Font...");
        JMenuItem colorItem = new JMenuItem("Color...");
        JMenuItem imageItem = new JMenuItem("Insert Image...");
        JMenuItem bgColorItem = new JMenuItem("Background Color...");

     // add action listeners to menu items
        fontItem.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                Font font = JFontChooser.showDialog(EditorFrame.this, "Select Font", textArea.getFont());
                if (font != null) {
                    textArea.setFont(font);
                }
            }
        });

        colorItem.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                Color color = JColorChooser.showDialog(EditorFrame.this, "Select Color", textArea.getForeground());
                if (color != null) {
                    textArea.setForeground(color);
                }
            }
        });
        bgColorItem.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                Color color = JColorChooser.showDialog(EditorFrame.this, "Select Background Color", textArea.getBackground());
                if (color != null) {
                    textArea.setBackground(color);
                }
            }
        });

        
        imageItem.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                JFileChooser imageChooser = new JFileChooser();
                FileNameExtensionFilter filter = new FileNameExtensionFilter("Image files", "jpg", "jpeg", "png", "gif");
                imageChooser.setFileFilter(filter);
                int result = imageChooser.showOpenDialog(EditorFrame.this);
                if (result == JFileChooser.APPROVE_OPTION) {
                    File file = imageChooser.getSelectedFile();
                    try {
                        BufferedImage originalImage = ImageIO.read(file);
                        int originalWidth = originalImage.getWidth();
                        int originalHeight = originalImage.getHeight();
                        int newWidth = 200;
                        int newHeight = 200;
                        BufferedImage resizedImage = resizeImage(originalImage, newWidth, newHeight);
                        ImageIcon icon = new ImageIcon(resizedImage);
                        SimpleAttributeSet attributeSet = new SimpleAttributeSet();
                        StyleConstants.setIcon(attributeSet, icon);
                        int pos = textArea.getCaretPosition();
                        textArea.getDocument().insertString(pos, " ", attributeSet);
                    } catch (IOException ex) {
                        ex.printStackTrace();
                    } catch (BadLocationException ex) {
                        ex.printStackTrace();
                    }
                }
                
            }
            private BufferedImage resizeImage(BufferedImage image, int width, int height) {
                BufferedImage resizedImage = new BufferedImage(width, height, BufferedImage.TYPE_INT_ARGB);
                Graphics2D g = resizedImage.createGraphics();
                g.drawImage(image, 0, 0, width, height, null);
                g.dispose();
                return resizedImage;
            }

        });

        
        

            
           
        

        
        

        
        // add menu items to Edit menu
        fileMenu.add(openItem);
        fileMenu.add(saveItem);
        fileMenu.addSeparator();
        fileMenu.add(exitItem);

        editMenu.add(fontItem);
        editMenu.add(colorItem);
        editMenu.add(imageItem);
        editMenu.add(bgColorItem);

        // add Edit menu to menu bar
        menuBar.add(fileMenu);
        menuBar.add(editMenu);
        menuBar.add(additionalMenu);

       
        // set menu bar to frame
        setJMenuBar(menuBar);

        


        // create menu items for Additional menu
        JMenuItem aboutItem = new JMenuItem("About");

        colorItem.addActionListener(new ActionListener() {
        	public void actionPerformed(ActionEvent e) {
        	Color color = JColorChooser.showDialog(EditorFrame.this, "Select Color", textArea.getForeground());
        	if (color != null) {
        	textArea.setForeground(color);
        	}
        	}
        	});
        imageItem.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                JFileChooser imageChooser = new JFileChooser();
                int result = imageChooser.showOpenDialog(EditorFrame.this);
                if (result == JFileChooser.APPROVE_OPTION) {
                    File file = imageChooser.getSelectedFile();
                    try {
                        BufferedImage image = ImageIO.read(file);
                        ImageIcon icon = new ImageIcon(image);
                        SimpleAttributeSet attributeSet = new SimpleAttributeSet();
                        StyleConstants.setIcon(attributeSet, icon);
                        int pos = textArea.getCaretPosition();
                        textArea.getDocument().insertString(pos, " ", attributeSet);
                    } catch (IOException ex) {
                        ex.printStackTrace();
                    } catch (BadLocationException ex) {
                        ex.printStackTrace();
                    }
                }
            }
        });

       

        // add action listener to About menu item
        aboutItem.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                // display message dialog with information about the program
                String message = "Simple Text Editor v1.0\n\n" +
                                 "A simple text editor created using Java Swing.\n\n" +
                                 "© 2023 Your Company Name. All rights reserved.";
                JOptionPane.showMessageDialog(EditorFrame.this, message, "About Simple Text Editor", JOptionPane.INFORMATION_MESSAGE);
            }
        });

        // add menu items to menus
        fileMenu.add(openItem);
        fileMenu.add(saveItem);
        fileMenu.addSeparator();
        fileMenu.add(exitItem);
        editMenu.add(fontItem);
        editMenu.add(colorItem);
        editMenu.addSeparator();
        editMenu.add(imageItem);
        additionalMenu.add(aboutItem);

        // add menus to menu bar
        menuBar.add(fileMenu);
        menuBar.add(editMenu);
        menuBar.add(additionalMenu);

        // set menu bar for frame
        setJMenuBar(menuBar);

        // add scroll pane to frame
        add(scrollPane);
    }

    private void showPopupMenu(int x, int y) {
        // create popup menu and menu items
        JPopupMenu popupMenu = new JPopupMenu();
        JMenuItem openItem = new JMenuItem("Open");
        JMenuItem saveItem = new JMenuItem("Save");
        JMenuItem fontItem = new JMenuItem("Font...");
        JMenuItem colorItem = new JMenuItem("Color...");
        JMenuItem imageItem = new JMenuItem("Insert Image...");

        // add action listeners to menu items
        openItem.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                int result = fileChooser.showOpenDialog(EditorFrame.this);
                if (result == JFileChooser.APPROVE_OPTION) {
                    File file = fileChooser.getSelectedFile();
                    try {
                        FileReader reader = new FileReader(file);
                        textArea.read(reader, null);
                        reader.close();
                    } catch (IOException ex) {
                        ex.printStackTrace();
                    }
                }
            }
        });

        saveItem.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                int result = fileChooser.showSaveDialog(EditorFrame.this);
                if (result == JFileChooser.APPROVE_OPTION) {
                    File file = fileChooser.getSelectedFile();
                    try {
                        FileWriter writer = new FileWriter(file);
                        textArea.write(writer);
                        writer.close();
                    } catch (IOException ex) {
                        ex.printStackTrace();
                    }
                }
            }
        });

        fontItem.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
            	Font font = JFontChooser.showDialog(EditorFrame.this, "Select Font", textArea.getFont());
            	if (font != null) {
            	textArea.setFont(font);
            	}
            	}
            	});
        colorItem.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                Color color = JColorChooser.showDialog(EditorFrame.this, "Select Color", textArea.getForeground());
                if (color != null) {
                	textArea.setForeground(color);
                }
            }
        });
        

        imageItem.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                JFileChooser imageChooser = new JFileChooser();
                int result = imageChooser.showOpenDialog(EditorFrame.this);
                if (result == JFileChooser.APPROVE_OPTION) {
                    File file = imageChooser.getSelectedFile();
                    try {
                        BufferedImage image = ImageIO.read(file);
                        ImageIcon icon = new ImageIcon(image);
                        SimpleAttributeSet attributeSet = new SimpleAttributeSet();
                        StyleConstants.setIcon(attributeSet, icon);
                        int pos = textArea.getCaretPosition();
                        textArea.getDocument().insertString(pos, " ", attributeSet);
                    } catch (IOException ex) {
                        ex.printStackTrace();
                    } catch (BadLocationException ex) {
                        ex.printStackTrace();
                    }
                }
            }
        });

        // add menu items to popup menu
        popupMenu.add(openItem);
        popupMenu.add(saveItem);
        popupMenu.addSeparator();
        popupMenu.add(fontItem);
        popupMenu.add(colorItem);
        popupMenu.addSeparator();
        popupMenu.add(imageItem);

        // show popup menu at mouse position
        popupMenu.show(textArea, x, y);
    }



//create additional popup menu for Additional menu
private void showAdditionalPopupMenu(int x, int y) {
//create popup menu and menu items
JPopupMenu popupMenu = new JPopupMenu();
JMenuItem copyItem = new JMenuItem("Copy");
JMenuItem cutItem = new JMenuItem("Cut");
JMenuItem pasteItem = new JMenuItem("Paste");

//add action listeners to menu items
copyItem.addActionListener(new ActionListener() {
 public void actionPerformed(ActionEvent e) {
     textArea.copy();
 }
});

cutItem.addActionListener(new ActionListener() {
 public void actionPerformed(ActionEvent e) {
     textArea.cut();
 }
});

pasteItem.addActionListener(new ActionListener() {
 public void actionPerformed(ActionEvent e) {
     textArea.paste();
 }
});

//add menu items to popup menu
popupMenu.add(copyItem);
popupMenu.add(cutItem);
popupMenu.add(pasteItem);

//show popup menu at mouse position
popupMenu.show(textArea, x, y);
}




public static void main(String[] args) {
EditorFrame frame = new EditorFrame();
frame.setVisible(true);

}







}


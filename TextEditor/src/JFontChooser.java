import javax.swing.JDialog;
import javax.swing.JPanel;
import javax.swing.JButton;
import javax.swing.JList;
import javax.swing.JComboBox;
import javax.swing.JScrollPane;
import javax.swing.ListSelectionModel;
import javax.swing.JLabel;
import javax.swing.SwingConstants;
import javax.swing.border.EmptyBorder;
import java.awt.Font;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import java.awt.GraphicsEnvironment;

public class JFontChooser extends JDialog {
    private Font selectedFont;
    private JList<String> fontList;
    private JComboBox<Integer> sizeComboBox;

    public JFontChooser(Font initialFont) {
        setTitle("Select Font");
        setSize(400, 300);
        setModal(true);

        // create panel for font list, size combo box, and buttons
        JPanel panel = new JPanel();
        panel.setBorder(new EmptyBorder(10, 10, 10, 10));
        panel.setLayout(null);

        // create font list with sizes
        GraphicsEnvironment env = GraphicsEnvironment.getLocalGraphicsEnvironment();
        Font[] fonts = env.getAllFonts();
        String[] fontNames = new String[fonts.length];
        for (int i = 0; i < fonts.length; i++) {
            fontNames[i] = fonts[i].getName() + " (" + fonts[i].getSize() + ")";
        }
        fontList = new JList<>(fontNames);
        fontList.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        JScrollPane scrollPane = new JScrollPane(fontList);
        scrollPane.setBounds(10, 10, 200, 200);
        panel.add(scrollPane);

        // create size combo box
        Integer[] sizes = { 8, 9, 10, 11, 12, 14, 16, 18, 20, 22, 24, 26, 28, 36, 48, 72 };
        sizeComboBox = new JComboBox<>(sizes);
        sizeComboBox.setBounds(220, 10, 80, 30);
        panel.add(sizeComboBox);

        // create OK and Cancel buttons
        JButton okButton = new JButton("OK");
        okButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                int index = fontList.getSelectedIndex();
                int size = (int) sizeComboBox.getSelectedItem();
                selectedFont = fonts[index].deriveFont((float) size);
                dispose();
            }
        });
        okButton.setBounds(220, 50, 80, 30);
        panel.add(okButton);

        JButton cancelButton = new JButton("Cancel");
        cancelButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                selectedFont = null;
                dispose();
            }
        });
        cancelButton.setBounds(220, 90, 80, 30);
        panel.add(cancelButton);

        // create sample text label
        JLabel sampleLabel = new JLabel("Sample Text");
        sampleLabel.setHorizontalAlignment(SwingConstants.CENTER);
        sampleLabel.setBounds(10, 220, 290, 30);
        panel.add(sampleLabel);

        // set initial font and selected index
        for (int i = 0; i < fonts.length; i++) {
            if (fonts[i].getName().equals(initialFont.getName()) && fonts[i].getSize() == initialFont.getSize()) {
                fontList.setSelectedIndex(i);
                break;
            }
        }
        sizeComboBox.setSelectedItem(initialFont.getSize());
        sampleLabel.setFont(initialFont);

        // add panel to dialog
        add(panel);
    }

    public static Font showDialog(EditorFrame editorFrame, String title, Font initialFont) {
    	JFontChooser fontChooser = new JFontChooser(initialFont);
    	fontChooser.setTitle(title);
    	fontChooser.setLocationRelativeTo(editorFrame);
    	fontChooser.setVisible(true);
    	return fontChooser.selectedFont;
    	}
    	}

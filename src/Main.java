import java.awt.*;
import javax.swing.*;
import javax.swing.undo.UndoManager;
import java.awt.event.*;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;

public class Main {
    private JFrame frmTextEditor;
    private String [] fontTypes= GraphicsEnvironment.getLocalGraphicsEnvironment().getAvailableFontFamilyNames();
    private String[] fontStyles={"Bold","italic"};
    private Integer[] fontSizes={9,10,11,12,13,14,15,16};
    private String currentFont="Arial";
    private String currentStyle;
    private int currentSize;
    public static void main(String[] args) {
        EventQueue.invokeLater(new Runnable() {
            public void run() {
                try {
                    Main window = new Main();
                    window.frmTextEditor.setVisible(true);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });
    }

    public Main() {
        initialize();
    }
    private void initialize() {
        //JFrame,ve TextArea oluşturulur.Daha sonrasında konumu belirlenir ve Menü tanıtılıp eklenir.
        frmTextEditor = new JFrame();
        frmTextEditor.setTitle("Text Editor");
        frmTextEditor.setBounds(100, 100, 505, 490);
        frmTextEditor.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frmTextEditor.getContentPane().setLayout(new BorderLayout(0, 0));
        JTextArea textRegion = new JTextArea();
        frmTextEditor.getContentPane().add(textRegion, BorderLayout.CENTER);
        JMenuBar menuBar = new JMenuBar();
        frmTextEditor.setJMenuBar(menuBar);
        UndoManager undo = new UndoManager();
        textRegion.getDocument().addUndoableEditListener(undo);
        JMenu mnFile = new JMenu("File");
        menuBar.add(mnFile);
        JMenuItem mntmNew = new JMenuItem("New");
        //Daha sonra basıldığında yani aktifleştirildiğinde yapılacak işlemler tanıtılır.

        mntmNew.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent arg0) {
                textRegion.setText("");
            }
        });
        mnFile.add(mntmNew);
        JMenuItem mntmSave = new JMenuItem("Save");
        mntmSave.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                JFileChooser filechooser = new JFileChooser("f:");
                int temp = filechooser.showSaveDialog(null);

                if (temp == JFileChooser.APPROVE_OPTION) {
                    File file = new File(filechooser.getSelectedFile().getAbsolutePath());

                    try {
                        FileWriter filewriter = new FileWriter(file, false);
                        BufferedWriter bufferwr = new BufferedWriter(filewriter);//Try catch ile Exception kontrolü yapılır.
                        bufferwr.write(textRegion.getText());
                        bufferwr.flush();
                        bufferwr.close();
                    }
                    catch (Exception ex) {
                        JOptionPane.showMessageDialog(frmTextEditor, ex.getMessage());
                    }}
            }
        });
        mnFile.add(mntmSave);

        JMenuItem mntmOpen = new JMenuItem("Open");
        mntmOpen.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                JFileChooser filechooser = new JFileChooser("f:");
                int temp = filechooser.showOpenDialog(null);

                if (temp == JFileChooser.APPROVE_OPTION) {
                    File file = new File(filechooser.getSelectedFile().getAbsolutePath());

                    try {
                        String str="",str1="";
                        FileReader fileread = new FileReader(file);
                        BufferedReader bufferrd = new BufferedReader(fileread);
                        str1=bufferrd.readLine();
                        while((str=bufferrd.readLine())!=null) {
                            str1=str1+"\n"+str;
                        }
                        textRegion.setText(str1);
                    }
                    catch(Exception ex) {
                        JOptionPane.showMessageDialog(frmTextEditor, ex.getMessage());
                    }
                }
            }

        });
        mnFile.add(mntmOpen);
        JMenuItem mntmExit = new JMenuItem("Exit");
        mntmExit.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                frmTextEditor.dispatchEvent(new WindowEvent(frmTextEditor, WindowEvent.WINDOW_CLOSING));
            }
        });
        mnFile.add(mntmExit);

        JMenu mnEdit = new JMenu("Edit");
        menuBar.add(mnEdit);

        JMenuItem mntmCut = new JMenuItem("Cut");
        mntmCut.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                textRegion.cut();
            }
        });
        mnEdit.add(mntmCut);

        JMenuItem mntmCopy = new JMenuItem("Copy");
        mntmCopy.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                textRegion.copy();
            }
        });
        mnEdit.add(mntmCopy);

        JMenuItem mntmExi = new JMenuItem("Paste");
        mntmExi.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                textRegion.paste();
            }
        });
        mnEdit.add(mntmExi);
        textRegion.addKeyListener(new KeyListener() {
            @Override
            public void keyTyped(KeyEvent e) {

            }

            @Override
            public void keyPressed(KeyEvent e) {
                if (e.getKeyChar()==26)
                {
                    undo.undo();
                }
                if (e.getKeyChar()==25)
                {
                    undo.redo();
                }
            }

            @Override
            public void keyReleased(KeyEvent e) {

            }
        });
        JMenuItem mntmUndo = new JMenuItem("Undo");
        mntmUndo.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {

                undo.undo();
            }

        });
        mnEdit.add(mntmUndo);

        JMenuItem mntmRedo = new JMenuItem("Redo");
        mntmRedo.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                undo.redo();
            }
        });
        mnEdit.add(mntmRedo);


        JMenuItem mntmBold;
        JMenuItem mntmItalic;
        JMenuItem size;
        JMenu font=new JMenu("Font");
        menuBar.add(font);
        mntmBold =new JMenuItem("Bold");
        mntmBold.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {

                textRegion.setFont(new Font("Arial",Font.BOLD,15));
            }
        });
        font.add(mntmBold);
        mntmItalic =new JMenuItem("Italic");
        mntmItalic.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                textRegion.setFont(new Font("Arial",Font.ITALIC,15));
            }
        });
        font.add(mntmItalic);
        size =new JMenuItem("Size(25)");
        size.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                textRegion.setFont(new Font("Arial",Font.BOLD,80));
            }
        });
        font.add(size);
        JScrollPane scrollabletextRegion = new JScrollPane(textRegion);
        scrollabletextRegion.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_ALWAYS);
        scrollabletextRegion.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);
        frmTextEditor.getContentPane().add(scrollabletextRegion);
    }
}
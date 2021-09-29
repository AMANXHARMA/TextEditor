package com.company;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;
import java.io.*;
import java.nio.charset.StandardCharsets;

class Notepad implements ActionListener, WindowListener
{
    JFrame jf,jf_font;
    JMenuItem neww, open, save,saveAs,cut,copy,paste,font,font_color,bg_color;
    JTextArea jt;
    JButton ok;
    JComboBox font_family,font_size,font_style;
    File f;

    void body() {
        try {
            UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
        } catch (Exception e) {
            System.out.println(e);
        }
        jf = new JFrame("Sasta Notepad");
        jf.setSize(700, 600);
        jf.setDefaultCloseOperation(3);
        jf.setLocationRelativeTo(null);
        jf.addWindowListener(this);

        JMenuBar jm = new JMenuBar();
        jf.setJMenuBar(jm);

        JMenu file = new JMenu("File");
        jm.add(file);

        neww = new JMenuItem("New");
        neww.addActionListener(this);
        file.add(neww);

        open = new JMenuItem("Open");
        open.addActionListener(this);
        file.add(open);

        save = new JMenuItem("Save As");
        save.addActionListener(this);
        file.add(save);

        saveAs=new JMenuItem("Save");
        saveAs.addActionListener(this);
        file.add(saveAs);


        JMenu edit = new JMenu("Edit");
        jm.add(edit);

        cut=new JMenuItem("Cut");
        cut.addActionListener(this);
        edit.add(cut);

        copy=new JMenuItem("Copy");
        copy.addActionListener(this);
        edit.add(copy);

        paste=new JMenuItem("Paste");
        paste.addActionListener(this) ;
        edit.add(paste);


        JMenu format = new JMenu("Format");
        jm.add(format);

        font=new JMenuItem("Font");
        font.addActionListener(this);
        format.add(font);

        font_color =new JMenuItem("Font Color");
        font_color.addActionListener(this);
        format.add(font_color );

        bg_color =new JMenuItem("Background Color");
        bg_color.addActionListener(this);
        format.add(bg_color);

        JMenu view = new JMenu("View");
        jm.add(view);

        jt = new JTextArea();

        JScrollPane jScrollPane = new JScrollPane(jt);
        jf.add(jScrollPane);

        jScrollPane.setHorizontalScrollBarPolicy(jScrollPane.HORIZONTAL_SCROLLBAR_ALWAYS);
        jScrollPane.setVerticalScrollBarPolicy(jScrollPane.VERTICAL_SCROLLBAR_ALWAYS);

        jf.setVisible(true);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == neww)
        {
         String text=jt.getText();
            if(!text.equals(""))
            {
                int i=JOptionPane.showConfirmDialog(jf,"Do you want to save the file");
                if(i==0)
                {
                    save();
                    jt.setText("");
                    jf.setTitle("Sasta Notepad");
                }
                else if(i==1)
                {
                    jt.setText("");
                }
            }
        }
        if(e.getSource()==open)
        {
            JFileChooser filechooser = new JFileChooser();
            int result = filechooser.showOpenDialog(jf);
            if (result == 0)
            {
                jt.setText("");
                f = filechooser.getSelectedFile();
                jf.setTitle(f.getName());
                try (FileInputStream file = new FileInputStream(f)) {
                    int i;
                    while ((i = file.read()) != -1) {
                        jt.append(String.valueOf((char) i));
                    }
                } catch (IOException ee) {
                    ee.printStackTrace();
                }
            }
        }
        if(e.getSource()==save)
        {
            save();

        }
         if(e.getSource()==saveAs)
         {
             String title=jf.getTitle();
             if(title.equals("Sasta Notepad"))
             {
                 save();
             }
             else
             {
                 String text=jt.getText();
                 try(FileOutputStream fos=new FileOutputStream(f))
                 {
                    byte[] b=text.getBytes();
                    fos.write(b);
                 }
                 catch(IOException ee)
                 {
                     ee.getStackTrace();
                 }
             }
         }
         if(e.getSource()==cut)
         {
             jt.cut();
         }
         if(e.getSource()==copy)
         {
             jt.copy();
         }
         if(e.getSource()==paste)
         {
             jt.paste();
         }
         if(e.getSource()==font)
         {
             font_frame();
         }
         if(e.getSource()==ok)
         {
            String fontfamily=(String)font_family.getSelectedItem();
            String fontsize=(String)font_size.getSelectedItem();
            String fontstyle=(String)font_style.getSelectedItem();

            int style=0;
            if(fontstyle.equals("Plain"))
            {
                style=0;
            }
            else if(fontstyle.equals("Italic"))
            {
                style=2;
            }
            else if(fontstyle.equals("Bold"))
            {
                style=1;
            }
            Font fontt=new Font(fontfamily,style,Integer.parseInt(fontsize) );
            jt.setFont(fontt);

            jf_font.setVisible(false);

         }
        if(e.getSource()==font_color)
        {
            Color c=JColorChooser.showDialog(jf,"Choose Color",Color.BLACK);
            jt.setForeground(c);
        }
        if(e.getSource()==bg_color)
        {
            Color c1=JColorChooser.showDialog(jf,"Choose Background Color",Color.white  );
            jt.setBackground(c1);
        }




    }
    void font_frame()
    {
        jf_font=new JFrame("Change Font ");
        jf_font.setSize(500,500);
        jf_font.setLocationRelativeTo(jf) ;
        jf_font.setLayout(null);

        String[] fonts= GraphicsEnvironment.getLocalGraphicsEnvironment().getAvailableFontFamilyNames();
        font_family=new JComboBox(fonts);
        font_family.setBounds(50,100,100,30);
        jf_font.add(font_family);

        String[] size={"10","12","14","16","18","20","22","24"};
        font_size=new JComboBox(size);
        font_size.setBounds(200,100,100,30);
        jf_font.add(font_size);

        String[] style={"Italic","Bold","Plain"};
        font_style=new JComboBox(style);
        font_style.setBounds(350,100,100,30);
        jf_font.add(font_style);

        ok=new JButton("OK");
        ok.setBounds(200,250,50,50);
        ok.addActionListener(this);
        jf_font.add(ok);



        jf_font.setVisible(true);

    }
    void save()
    {
        JFileChooser filechooser1=new JFileChooser();
        int result=filechooser1.showSaveDialog(jf);
        if(result==0)
        {
            String text=jt.getText();
            f=filechooser1.getSelectedFile();
            jf.setTitle(f.getName());
            try(FileOutputStream fos=new FileOutputStream(f))
            {
                byte[] b=text.getBytes();
                fos.write(b);
            }
            catch(IOException e1)
            {
                e1.printStackTrace();
            }
        }
    }

    @Override
    public void windowOpened(WindowEvent e) {

    }

    @Override
    public void windowClosing(WindowEvent e) {
        if (!jt.getText().equals("")) {
            int i = JOptionPane.showConfirmDialog(jf, "Do you want to save the file");
            if (i == 0) {
                save();
                jt.setText("");
                jf.setTitle("Sasta Notepad");
            } else if (i == 1) {
                jt.setText("");
            }
        }

    }

    @Override
    public void windowClosed(WindowEvent e) {

    }

    @Override
    public void windowIconified(WindowEvent e) {

    }

    @Override
    public void windowDeiconified(WindowEvent e) {

    }

    @Override
    public void windowActivated(WindowEvent e) {

    }

    @Override
    public void windowDeactivated(WindowEvent e) {

    }
}
public class Main {

    public static void main(String[] args) {
	// write your code here
        Notepad np=new Notepad();
        np.body();
    }
}

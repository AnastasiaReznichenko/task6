import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.util.Scanner;

public class GUI extends JFrame{
    private JPanel MAIN_PANEL;
    private JTable OUTPUT_TABLE;
    private JButton READ_FILE_BUTTON;
    private JButton READ_TEXT_BUTTON;
    private JTextArea INPUT_AREA;
    private JPanel TABLE_MODEL;

    GUI() {
        this.setContentPane(MAIN_PANEL);
        this.setTitle("Task 6");
        this.setDefaultCloseOperation(EXIT_ON_CLOSE);
        Toolkit tkt = Toolkit.getDefaultToolkit();
        Dimension dim = tkt.getScreenSize();
        this.setBounds(0, 0, dim.width, dim.height);


        DefaultTableModel outputTableModel = new DefaultTableModel();
        OUTPUT_TABLE.setModel(outputTableModel);
        outputTableModel.setDataVector(new Object[10][2], getHeaders(2));

        READ_FILE_BUTTON.addActionListener(actionEvent -> {
            JFileChooser jfc = new JFileChooser("examples");
            int ret = jfc.showDialog(null, "Choose Text");
            File file = null;
            if (ret == JFileChooser.APPROVE_OPTION)
                file = jfc.getSelectedFile();
            StringBuilder text = new StringBuilder();
            try {
                FileReader fr = new FileReader(file);
                Scanner scn = new Scanner(fr);
                while (scn.hasNextLine())
                    text.append(scn.nextLine());
                fr.close();
            } catch (Exception e) {
                e.printStackTrace();
            }
            fillTable(text.toString(), outputTableModel);
        });

        READ_TEXT_BUTTON.addActionListener(actionEvent -> {
            fillTable(INPUT_AREA.getText(), outputTableModel);
        });
    }


    private void fillTable(String text, DefaultTableModel model) {
        Collocation[] collocations = (Collocation[]) new Solution().findCollocations(text,
                MapUtils::findCollocation,
                MapUtils::choose);

        model.setDataVector(collocationToObject(collocations), getHeaders(2));
    }

    private Object[][] collocationToObject(Collocation[] collocations) {
        String[][] arr = new String[collocations.length][2];
        reverse(collocations);

        for (int i = 0; i < collocations.length; i++) {
            arr[i][0] = String.valueOf(collocations[i].getFrequency());
            arr[i][1] = collocations[i].getCollocation();
        }
        return arr;
    }

    private Object[] reverse(Object[] arr) {
        Object temp;

        for (int i = 0; i < arr.length/2; i++) {
            temp = arr[arr.length-i-1];
            arr[arr.length-i-1] = arr[i];
            arr[i] = temp;
        }
        return arr;
    }

    private Integer[] getHeaders(int length) {
        Integer[] headers = new Integer[length];
        for (int i = 0; i < length; i++) {
            headers[i] = i+1;
        }
        return headers;
    }

}

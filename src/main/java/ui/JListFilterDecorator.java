package ui;

import javax.swing.*;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import java.awt.*;
import java.util.ArrayList;
import java.util.List;
import java.util.function.BiPredicate;

public class JListFilterDecorator {
    public static JPanel decorate(JList jList, final BiPredicate<String, String> userFilter) {
        if (!(jList.getModel() instanceof DefaultListModel)) {
            throw new IllegalArgumentException("List model must be an instance of DefaultListModel");
        }
        final DefaultListModel<String> model = (DefaultListModel<String>) jList.getModel();
        final List<String> items = getItems(model);
      //  final JTextField textField = new JTextField();
      //  textField.getDocument().addDocumentListener(new DocumentListener() {
//            @Override
//            public void insertUpdate(DocumentEvent e) {
//                filter();
//            }
//
//            @Override
//            public void removeUpdate(DocumentEvent e) {
//                filter();
//            }
//
//            @Override
//            public void changedUpdate(DocumentEvent e) {
//                filter();
//            }
//
//            private void filter() {
//                model.clear();
//                String s = textField.getText();
//                for (String item : items) {
//                    if(userFilter.test(item, s)){
//                        model.addElement(item);
//                    }
//                }
//            }
//        });
//
        JPanel panel = new JPanel(new BorderLayout());
 //       panel.add(textField, BorderLayout.NORTH);
        JScrollPane pane = new JScrollPane(jList);
        panel.add(pane);
        return panel;
    }

    private static <String> List<String> getItems(DefaultListModel<String> model) {
        List<String> list = new ArrayList<>();
        for (int i = 0; i < model.size(); i++) {
            list.add(model.elementAt(i));
        }
        return list;
    }


}
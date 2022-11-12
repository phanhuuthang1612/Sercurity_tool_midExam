package ui;
import javax.swing.*;
import java.awt.event.ActionEvent;
import javax.swing.JPopupMenu;
import javax.swing.undo.*;


public class JTextFieldRegularPopupMenu {
    public static void addTo(JTextArea txtField) 
    {
        JPopupMenu popup = new JPopupMenu();
        UndoManager undoManager = new UndoManager();
        txtField.getDocument().addUndoableEditListener(undoManager);

        Action undoAction = new AbstractAction("Undo") {
            @Override
            public void actionPerformed(ActionEvent ae) {
                if (undoManager.canUndo()) {
                    undoManager.undo();
                }
                else {
                    JOptionPane.showMessageDialog(null,
                            "Undoable: " + undoManager.canUndo() ,
                            "Undo Status", 
                            JOptionPane.INFORMATION_MESSAGE);
                }
            }
        };

       Action copyAction = new AbstractAction("Copy") {
            @Override
            public void actionPerformed(ActionEvent ae) {
                txtField.copy();
            }
        };

        Action cutAction = new AbstractAction("Cut") {
            @Override
            public void actionPerformed(ActionEvent ae) {
                txtField.cut();
            }
        };

        Action pasteAction = new AbstractAction("Paste") {
            @Override
            public void actionPerformed(ActionEvent ae) {
                txtField.paste();
            }
        };

        Action selectAllAction = new AbstractAction("Select All") {
            @Override
            public void actionPerformed(ActionEvent ae) {
                txtField.selectAll();
            }
        };

        popup.add (undoAction);
        popup.addSeparator();
        popup.add (cutAction);
        popup.add (copyAction);
        popup.add (pasteAction);
        popup.addSeparator();
        popup.add (selectAllAction);

       txtField.setComponentPopupMenu(popup);
    }
}
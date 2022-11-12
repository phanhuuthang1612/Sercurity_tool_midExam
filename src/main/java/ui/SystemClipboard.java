package ui;
import java.awt.*;
import java.awt.datatransfer.Clipboard;
import java.awt.datatransfer.DataFlavor;
import java.awt.datatransfer.StringSelection;

import static java.awt.event.KeyEvent.*;
import static org.apache.commons.lang3.SystemUtils.IS_OS_MAC;

public class SystemClipboard
{
    public static void copy(String text)
    {
        Clipboard clipboard = getSystemClipboard();
        clipboard.setContents(new StringSelection(text), null);
    }

    public static void paste() throws AWTException
    {
        Robot robot = new Robot();

        int controlKey = IS_OS_MAC ? VK_META : VK_CONTROL;
        robot.keyPress(controlKey);
        robot.keyPress(VK_V);
        robot.keyRelease(controlKey);
        robot.keyRelease(VK_V);
    }

    public static String get() throws Exception
    {
        Clipboard systemClipboard = getSystemClipboard();
        DataFlavor dataFlavor = DataFlavor.stringFlavor;

        if (systemClipboard.isDataFlavorAvailable(dataFlavor))
        {
            Object text = systemClipboard.getData(dataFlavor);
            return (String) text;
        }

        return null;
    }

    private static Clipboard getSystemClipboard()
    {
        Toolkit defaultToolkit = Toolkit.getDefaultToolkit();
        return defaultToolkit.getSystemClipboard();
    }
}
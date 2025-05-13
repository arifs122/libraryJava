package util;

import javax.swing.*;
import java.net.URL;

public class IconHelper {

        public static void setAppIcon(JFrame frame) {
            try {
                URL iconUrl = IconHelper.class.getResource("/resources/libraryIcon.png");

                if (iconUrl == null) {
                    System.err.println("Icon dosyası bulunamadı!");
                    return;
                }

                frame.setIconImage(new ImageIcon(iconUrl).getImage());
            } catch (Exception e) {
                System.err.println("Icon yüklenirken hata: " + e.getMessage());
            }
        }
}

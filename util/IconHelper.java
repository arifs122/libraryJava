package util;

import javax.swing.*;
import java.net.URL;

public class IconHelper {

        public static void setAppIcon(JFrame frame) {
            try {
                // 1. Resource'dan URL al
                URL iconUrl = IconHelper.class.getResource("/resources/libraryIcon.png"); // veya "/resources/icon.png"

                // 2. Null kontrolü
                if (iconUrl == null) {
                    System.err.println("Icon dosyası bulunamadı!");
                    return;
                }

                // 3. ImageIcon oluştur ve frame'e set et
                frame.setIconImage(new ImageIcon(iconUrl).getImage());
            } catch (Exception e) {
                System.err.println("Icon yüklenirken hata: " + e.getMessage());
            }
        }
}

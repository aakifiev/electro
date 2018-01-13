package elctro.bot.util;

import org.telegram.telegrambots.api.methods.send.SendPhoto;
import org.telegram.telegrambots.api.objects.PhotoSize;

import java.util.Comparator;
import java.util.List;

public class PhotoUtil {
    public static SendPhoto getSendPhoto(List<PhotoSize> photos, Long chatId){
        String f_id = photos.stream()
                .sorted(Comparator.comparing(PhotoSize::getFileSize).reversed())
                .findFirst()
                .orElse(null).getFileId();
        // Know photo width
        int f_width = photos.stream()
                .sorted(Comparator.comparing(PhotoSize::getFileSize).reversed())
                .findFirst()
                .orElse(null).getWidth();
        // Know photo height
        int f_height = photos.stream()
                .sorted(Comparator.comparing(PhotoSize::getFileSize).reversed())
                .findFirst()
                .orElse(null).getHeight();
        // Set photo caption
        String caption = "file_id: " + f_id + "\nwidth: " + Integer.toString(f_width) + "\nheight: " + Integer.toString(f_height);
        return new SendPhoto()
                .setChatId(chatId)
                .setPhoto(f_id);
                //.setCaption(caption);
    }
}

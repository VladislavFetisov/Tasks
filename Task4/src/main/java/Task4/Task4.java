package Task4;

import au.com.bytecode.opencsv.CSVWriter;

import java.io.*;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

import static java.nio.charset.StandardCharsets.UTF_8;

public class Task4 {
    static private final SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSS'Z'");
    static private final int DATA_FORMAT_SIZE = 24;

    private void parseLog(String inputName, Date date1, Date date2) {
        int capacity = 0, currentVolume = 0, initialVolume = 0, attemptOfPour = 0, attemptOfPourMistakes = 0, volumeOfPured = 0, volumeOfUnpured = 0;
        int attemptOfScoop = 0, attemptOfScoopMistakes = 0, volumeOfScooped = 0, volumeOfUnscooped = 0;
        String line, s;
        int index, num;
        Date currentDate;
        try (BufferedReader reader = new BufferedReader(
                new InputStreamReader(new FileInputStream(inputName), UTF_8))) {
            line = reader.readLine();
            int i = 0;
            while (line != null) {
                if (i == 0) capacity = Integer.parseInt(line);
                else if (i == 1) currentVolume = initialVolume = Integer.parseInt(line);
                else {
                    currentDate = sdf.parse(line.substring(0, DATA_FORMAT_SIZE));

                    if (!currentDate.before(date1) && !currentDate.after(date2)) {
                        if (line.contains("top up")) {
                            attemptOfPour++;
                            index = line.indexOf("top up");
                            s = line.substring(index);
                            num = Integer.parseInt(s.replaceAll("[^\\d+]", ""));
                            if (currentVolume + num < capacity) {
                                currentVolume += num;
                                volumeOfPured += num;
                            } else {
                                attemptOfPourMistakes++;
                                volumeOfUnpured += num;
                            }
                        } else {
                            attemptOfScoop++;
                            index = line.indexOf("scoop");
                            s = line.substring(index);
                            num = Integer.parseInt(s.replaceAll("[^\\d+]", ""));
                            if (currentVolume - num >= 0) {
                                currentVolume -= num;
                                volumeOfScooped += num;
                            } else {
                                attemptOfScoopMistakes++;
                                volumeOfUnscooped += num;
                            }
                        }
                    }
                    if (currentDate.after(date2)) break;
                }
                line = reader.readLine();
                i++;
            }
            try (CSVWriter writer = new CSVWriter(new FileWriter("Task4/src/main/java/Task4/LogDescription/" + "Description.scv"))) {
                writer.writeNext(new String[]{"Кол-во попыток налить воду:", String.valueOf(attemptOfPour)});
                writer.writeNext(new String[]{"Процент ошибок при наливании воды:", String.valueOf((attemptOfPourMistakes * 100) / attemptOfPour)});
                writer.writeNext(new String[]{"Обьем налитой воды:", String.valueOf(volumeOfPured)});
                writer.writeNext(new String[]{"Обьем неналитой воды:", String.valueOf(volumeOfUnpured)});
                writer.writeNext(new String[]{"Кол-во попыток забора воду:", String.valueOf(attemptOfScoop)});
                writer.writeNext(new String[]{"Процент ошибок при заборе воды:", String.valueOf((attemptOfScoopMistakes * 100) / attemptOfScoop)});
                writer.writeNext(new String[]{"Обьем забронной воды:", String.valueOf(volumeOfScooped)});
                writer.writeNext(new String[]{"Обьем незабронной воды:", String.valueOf(volumeOfUnscooped)});
                writer.writeNext(new String[]{"Начальной обьем воды:", String.valueOf(initialVolume)});
                writer.writeNext(new String[]{"Конечный обьем воды:", String.valueOf(currentVolume)});
            }
        } catch (IOException | ParseException e) {
            System.err.println("Что-то не так с файлом!");
        }
    }

    public static void main(String[] args) {
        if (args.length == 3) {
            Task4 task4 = new Task4();
            try {
                Date date1 = sdf.parse(args[1]);
                Date date2 = sdf.parse(args[2]);
                if (!date1.before(date2)) System.err.println("InputFilePath date1(ISO 8601) date2(ISO 8601)");
                else task4.parseLog(args[0], date1, date2);
            } catch (ParseException e) {
                System.err.println("Формат даты неправильный!");
            }
        } else System.err.println("InputFilePath date1(ISO 8601) date2(ISO 8601)");
    }

}

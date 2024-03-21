import java.util.ArrayList;
import java.util.Scanner;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.io.FileOutputStream;

public class Main {

    /*
    Takes commands from the user and executes them. Possible commands are
    'read' - reads a file of weather data into the system
    'write' - writes weather data to a file -- overwrites the file if it exists
    'sort' - sorts weather data by the hottest to coldest average temperature
    'append' - writes weather data to a file -- appends data to the file if it exists
    'quit' - ends the program
     */
    public static void main(String[] args)
    {
        ArrayList<WeatherData> weatherData = null;
        while (true)
        {
            System.out.print("Enter a command: ");
            Scanner sc = new Scanner(System.in);

            String command = sc.next().toLowerCase();
            switch (command)
            {
                case "read":
                {
                    System.out.print("Enter the path to the file: ");
                    String path = sc.next();

                    weatherData = ReadFile(path);
                    PrintWeatherData(weatherData);
                    break;
                }
                case "sort":
                {
                    if (weatherData == null)
                    {
                        System.out.println("Please call 'read' first, before calling 'append'.");
                        break;
                    }

                    SortWeatherData(weatherData);
                    break;
                }
                case "write":
                {
                    if (weatherData == null)
                    {
                        System.out.println("Please call 'read' first, before calling 'write'.");
                        break;
                    }

                    System.out.print("Enter the path to the file: ");
                    String path = sc.next();

                    WriteFile(path, false, weatherData);
                    break;
                }
                case "append":
                {
                    if (weatherData == null)
                    {
                        System.out.println("Please call 'read' first, before calling 'append'.");
                        break;
                    }

                    System.out.print("Enter the path to the file: ");
                    String path = sc.next();

                    WriteFile(path, true, weatherData);
                    break;
                }
                case "quit":
                {
                    return;
                }
                default:
                {
                    System.out.println("Unrecognized command. Possible commands are 'read', 'write', 'append', 'sort', and 'quit'");
                    break;
                }
            }
        }
    }

    /*
    Reads a file from the given path and puts the information into an ArrayList.
    If the file does not exist, the function catches the exception, prints a message
    to the console, and return an empty (not null) array.
     */
    public static ArrayList<WeatherData> ReadFile(String path){
        ArrayList<WeatherData> weatherData = new ArrayList<>();
        try {
            File file = new File(path);
            Scanner scanner = new Scanner(file);
            while (scanner.hasNextLine()) {
                String line = scanner.nextLine();
                String[] parts = line.split(",");
                String city = parts[0].trim();
                double averageTemp = Double.parseDouble(parts[1].trim());
                double averageHumidity = Double.parseDouble(parts[2].trim());
                weatherData.add(new WeatherData(city, averageTemp, averageHumidity));
            }
            scanner.close();
        } catch (FileNotFoundException e) {
            System.out.println("File not found. Returning empty weather data list.");
        } catch (NumberFormatException e) {
            System.out.println("Invalid format in the file. Returning empty weather data list.");
        }
        return weatherData;
    }


    /*
    Prints the weather data ArrayList to the console. Each weather data item should
    go on a new line:

    [City1], [Average Temperature], [Average Humidity]
    [City2], [Average Temperature], [Average Humidity]
    ...
     */
    public static void PrintWeatherData(ArrayList<WeatherData> weatherData)
    {
        for (WeatherData data : weatherData) {
            System.out.println(data);
        }
    }

    /*
    Sorts the given ArrayList from hottest average temperature to coldest average temperature
     */
    public static void SortWeatherData(ArrayList<WeatherData> weatherData)
    {
        weatherData.sort((a, b) -> Double.compare(b.getAverageTemp(), a.getAverageTemp()));
    }


    /*
    Writes the weather data information into the file with the given path.
    If shouldAppend is false, the function replaces the existing contents of the file
    (if it exists) with the new weatherData. If shouldAppend is true, the function
    adds the weather data to the end of the file.
    If the file cannot be created, the function catches the exception, prints a message
    to the console, and does not try to write to the file.
     */
    public static void WriteFile(String path, boolean shouldAppend, ArrayList<WeatherData> weatherData) {
        try {
            PrintWriter writer = new PrintWriter(new FileOutputStream(path, shouldAppend));
            for (WeatherData data : weatherData) {
                writer.println(data);
            }
            writer.close();
        } catch (FileNotFoundException e) {
            System.out.println("File cannot be created. Unable to write weather data.");
        }
    }
}

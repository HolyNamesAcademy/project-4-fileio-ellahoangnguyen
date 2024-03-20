import java.util.ArrayList;
import java.util.Scanner;

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
        ArrayList<WeatherData> weatherDataList = new ArrayList<>();
        Scanner scanner = new Scanner(System.in);

        System.out.println("Simulating reading data from file: " + path);
        System.out.println("Please input weather data in the format: City, Average Temperature, Average Humidity");
        System.out.println("Type 'done' when finished.");

        while (true) {
            String line = scanner.nextLine().trim();
            if (line.equalsIgnoreCase("done")) {
                break;
            }

            String[] parts = line.split(", ");
            if (parts.length == 3) {
                String city = parts[0];
                double avgTemperature = Double.parseDouble(parts[1]);
                double avgHumidity = Double.parseDouble(parts[2]);

                WeatherData weatherData = new WeatherData(city, avgTemperature, avgHumidity);
                weatherDataList.add(weatherData);
            } else {
                System.out.println("Invalid input format. Please input data in the format: City, Average Temperature, Average Humidity");
            }
        }

        return weatherDataList;
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
        int n = weatherData.size();
        for (int i = 0; i < n - 1; i++) {
            for (int j = 0; j < n - i - 1; j++) {
                if (weatherData.get(j).compareTo(weatherData.get(j + 1)) < 0) {
                    // Swap weatherData[j] with weatherData[j+1]
                    WeatherData temp = weatherData.get(j);
                    weatherData.set(j, weatherData.get(j + 1));
                    weatherData.set(j + 1, temp);
                }
            }
        }
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
        System.out.println("Simulating writing data to file: " + path);
        if (shouldAppend) {
            System.out.println("Appending data to the file...");
        } else {
            System.out.println("Overwriting data in the file...");
        }

        for (WeatherData data : weatherData) {
            System.out.println(data);
        }

        System.out.println("Data written to file successfully.");
    }
}

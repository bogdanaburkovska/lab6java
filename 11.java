import java.util.Arrays;

public class Main {

    public static void main(String[] args) {
        // Масив для зберігання інформації про результати роботи підприємства
        ProductionRecord[] records = new ProductionRecord[12];

        // Автоматичне заповнення даними
        populateRecords(records);

        // Сортування записів за відсотком виконання плану
        Arrays.sort(records);

        // Вивід відсортованих записів
        System.out.println("\n**Результати роботи підприємства:**");
        System.out.println("Місяць       | План    | Факт    | Відсоток виконання");
        System.out.println("-------------+---------+---------+------------------");
        for (ProductionRecord record : records) {
            System.out.printf("%-12s | %-7d | %-7d | %.2f%%\n",
                    record.getMonth(), record.getPlannedOutput(),
                    record.getActualOutput(), record.getPerformancePercentage());
        }

        // Визначення місяців з найбільшим та найменшим відсотком виконання плану
        ProductionRecord minRecord = records[0];
        ProductionRecord maxRecord = records[records.length - 1];

        System.out.println("\nМісяць з найменшим відсотком виконання плану: " + minRecord.getMonth() +
                " (" + minRecord.getPerformancePercentage() + "%)");

        System.out.println("Місяць з найбільшим відсотком виконання плану: " + maxRecord.getMonth() +
                " (" + maxRecord.getPerformancePercentage() + "%)");
    }

    private static void populateRecords(ProductionRecord[] records) {
        String[] months = {"Січень", "Лютий", "Березень", "Квітень", "Травень", "Червень", 
                           "Липень", "Серпень", "Вересень", "Жовтень", "Листопад", "Грудень"};

        int[] plannedOutputs = {10000, 12000, 15000, 13000, 11000, 14000, 
                                16000, 17000, 18000, 19000, 20000, 21000};

        int[] actualOutputs = {9500, 11000, 14800, 12500, 10500, 13500, 
                               15500, 16500, 17500, 18500, 19500, 20500};

        for (int i = 0; i < records.length; i++) {
            records[i] = new ProductionRecord(months[i], plannedOutputs[i], actualOutputs[i]);
        }
    }
}

class ProductionRecord implements Comparable<ProductionRecord> {
    private String month;
    private int plannedOutput;
    private int actualOutput;

    public ProductionRecord(String month, int plannedOutput, int actualOutput) {
        this.month = month;
        this.plannedOutput = plannedOutput;
        this.actualOutput = actualOutput;
    }

    public String getMonth() {
        return month;
    }

    public int getPlannedOutput() {
        return plannedOutput;
    }

    public int getActualOutput() {
        return actualOutput;
    }

    public double getPerformancePercentage() {
        return (double) actualOutput / plannedOutput * 100;
    }

    @Override
    public int compareTo(ProductionRecord other) {
        return Double.compare(this.getPerformancePercentage(), other.getPerformancePercentage());
    }
}

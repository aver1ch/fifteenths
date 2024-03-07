
public class App
{
    public static void main(String[] args)
    {
        int[] array = {0, 1, 2, 3, 4 ,5 ,6 ,7 ,8 ,9 ,10, 11, 12, 13, 14, 15};
        NormalFifteenths fifteen = new NormalFifteenths();
        int[] shuffled = fifteen.shuffle(array);
        System.out.println("Перемешанное состояние:");
        fifteen.print(shuffled);
        System.out.println("Решение:");
        fifteen.solveTheGame(shuffled);
    }
}

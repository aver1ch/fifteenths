import java.util.*;

public class NormalFifteenths
{
    //vars
    //int[][] arrayOfFifteenths = {{1, 2, 3, 4}, {5, 6, 7, 8}, {9, 10, 11, 12}, {13, 14, 15, 0}};

    //methods
    public NormalFifteenths()
    {
        
    }

    private static class Node implements Comparable<Node> {
        int[] state;
        int cost;
        int heuristic;
        Node parent;

        Node(int[] state, int cost, int heuristic, Node parent) {
            this.state = state;
            this.cost = cost;
            this.heuristic = heuristic;
            this.parent = parent;
        }

        @Override
        public int compareTo(Node other) {
            return Integer.compare(cost + heuristic, other.cost + other.heuristic);
        }
    }

    // Переменная для хранения финального состояния
    private static final int[] GOAL_STATE = {1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11, 12, 13, 14, 15, 0};

    // Метод для решения головоломки
    public void solveTheGame(int[] arrayOfFifteenths) {
        PriorityQueue<Node> openSet = new PriorityQueue<>();
        openSet.offer(new Node(arrayOfFifteenths, 0, calculateHeuristic(arrayOfFifteenths), null));

        Set<String> visited = new HashSet<>();
        visited.add(Arrays.toString(arrayOfFifteenths));

        while (!openSet.isEmpty()) {
            Node current = openSet.poll();

            if (Arrays.equals(current.state, GOAL_STATE)) {
                printSolution(current);
                return;
            }

            int zeroIndex = findZeroIndex(current.state);

            for (int neighborIndex : getNeighborIndices(zeroIndex)) {
                int[] nextState = Arrays.copyOf(current.state, current.state.length);
                swap(nextState, zeroIndex, neighborIndex);

                if (!visited.contains(Arrays.toString(nextState))) {
                    visited.add(Arrays.toString(nextState));
                    openSet.offer(new Node(nextState, current.cost + 1, calculateHeuristic(nextState), current));
                }
            }
        }

        System.out.println("Головоломка не может быть решена");
    }

    // Метод для расчета эвристической оценки (Манхэттенское расстояние)
    private int calculateHeuristic(int[] state) {
        int distance = 0;
        for (int i = 0; i < state.length; i++) {
            if (state[i] != 0) {
                int row = i / 4;
                int col = i % 4;
                int goalIndex = state[i] - 1;
                int goalRow = goalIndex / 4;
                int goalCol = goalIndex % 4;
                distance += Math.abs(row - goalRow) + Math.abs(col - goalCol);
            }
        }
        return distance;
    }

    // Метод для нахождения индекса нулевой плитки
    private int findZeroIndex(int[] state) {
        for (int i = 0; i < state.length; i++) {
            if (state[i] == 0) {
                return i;
            }
        }
        return -1;
    }

    // Метод для получения индексов соседних плиток
    private List<Integer> getNeighborIndices(int zeroIndex) {
        List<Integer> neighbors = new ArrayList<>();
        if (zeroIndex % 4 > 0) neighbors.add(zeroIndex - 1); // Левая соседняя плитка
        if (zeroIndex % 4 < 3) neighbors.add(zeroIndex + 1); // Правая соседняя плитка
        if (zeroIndex >= 4) neighbors.add(zeroIndex - 4); // Верхняя соседняя плитка
        if (zeroIndex < 12) neighbors.add(zeroIndex + 4); // Нижняя соседняя плитка
        return neighbors;
    }

    // Метод для обмена значений между плитками
    private void swap(int[] state, int i, int j) {
        int temp = state[i];
        state[i] = state[j];
        state[j] = temp;
    }

    // Метод для печати пути решения
    private void printSolution(Node node) {
        List<Node> path = new ArrayList<>();
        while (node != null) {
            path.add(node);
            node = node.parent;
        }
        Collections.reverse(path);

        for (int i = 0; i < path.size(); i++) {
            System.out.println("Шаг " + i + ":");
            print(path.get(i).state);
        }
    }
  
    public boolean isSolved(int[] arrayOfFifteenths)
    {
        for(int i = 0; i < 16; ++i)
        {
            if (arrayOfFifteenths[i] != i)
            {
                return false;
            }
        }

        return true;
    }
    public int[] shuffle(int[] arrayOfFifteenths)
    {
        Random rand = new Random();

        for(int i = arrayOfFifteenths.length - 1; i > 0; --i)
        {
            int index = rand.nextInt(i + 1);

            int a = arrayOfFifteenths[index];
            arrayOfFifteenths[index] = arrayOfFifteenths[i];
            arrayOfFifteenths[i] = a;
        }

        return arrayOfFifteenths;
    }

    public void print(int[] arrayOfFifteenths)
    {
        for(int i = 0; i < 16; ++i)
        {
            if (i % 4 == 0)
            {
                System.out.print('\n');
            }
            System.out.print(arrayOfFifteenths[i] + "   ");
        }
    }

}
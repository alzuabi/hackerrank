package components_in_graph;

import java.io.*;
import java.util.*;
import java.util.stream.*;

import static java.util.stream.Collectors.joining;
import static java.util.stream.Collectors.toList;

import java.util.concurrent.atomic.AtomicInteger;


class Result {

    public static class Node {

        public Node(Integer value, List<Node> next) {
            this.value = value;
            this.next = next;
        }

        Integer value;
        List<Node> next;
    }


    public static List<Integer> componentsInGraph(List<List<Integer>> gb) {
        Map<Integer, Node> map = new HashMap<>();
        gb.forEach(integers -> {
            Node node1;
            Node node2;
            if (map.containsKey(integers.get(0)))
                node1 = map.get(integers.get(0));
            else {
                node1 = new Node(integers.get(0), new ArrayList<>());
                map.put(node1.value, node1);
            }

            if (map.containsKey(integers.get(1)))
                node2 = map.get(integers.get(1));
            else {
                node2 = new Node(integers.get(1), new ArrayList<>());
                map.put(node2.value, node2);
            }

            node1.next.add(node2);
            node2.next.add(node1);
        });
        Set<Integer> visited = new HashSet<>();
        AtomicInteger max = new AtomicInteger(Integer.MIN_VALUE);
        AtomicInteger min = new AtomicInteger(Integer.MAX_VALUE);
        map.forEach((key, value) -> {
            if (!visited.contains(key)) {
                int size = getSize(map, key, visited);
                max.set(Math.max(max.get(), size));
                min.set(Math.min(min.get(), size));
            }
        });
        List<Integer> results = new ArrayList<>();
        results.add(min.get());
        results.add(max.get());
        return results;
    }

    private static int getSize(Map<Integer, Node> map, Integer key, Set<Integer> visited) {
        AtomicInteger size = new AtomicInteger(1);
        Queue<Node> queue = new ArrayDeque<>();
        queue.add(map.get(key));
        while (!queue.isEmpty()) {
            Node node = queue.poll();
            visited.add(node.value);
            node.next.forEach(nod -> {
                if (!visited.contains(nod.value)) {
                    visited.add(nod.value);
                    queue.add(nod);
                    size.getAndIncrement();
                }
            });
        }
        return size.get();
    }


}

class Solution {
    public static void main(String[] args) throws IOException {
        BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(System.in));
        BufferedWriter bufferedWriter = new BufferedWriter(new FileWriter("OUTPUT_PATH"));

        int n = Integer.parseInt(bufferedReader.readLine().trim());

        List<List<Integer>> gb = new ArrayList<>();

        IntStream.range(0, n).forEach(i -> {
            try {
                gb.add(
                        Stream.of(bufferedReader.readLine().replaceAll("\\s+$", "").split(" "))
                                .map(Integer::parseInt)
                                .collect(toList())
                );
            } catch (IOException ex) {
                throw new RuntimeException(ex);
            }
        });

        List<Integer> result = Result.componentsInGraph(gb);

        bufferedWriter.write(
                result.stream()
                        .map(Object::toString)
                        .collect(joining(" "))
                        + "\n"
        );

        bufferedReader.close();
        bufferedWriter.close();
    }
}

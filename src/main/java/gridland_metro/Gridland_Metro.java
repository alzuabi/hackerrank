package gridland_metro;

import java.io.*;
import java.util.*;
import java.util.concurrent.atomic.AtomicLong;
import java.util.stream.IntStream;
import java.util.stream.Stream;

import static java.util.stream.Collectors.toList;

class Result {

    static class Range { // represent range from start to end
        long start;
        long end;

        public Range(long start, long end) {
            this.start = start;
            this.end = end;
        }
    }

    public static long gridlandMetro(int n, int m, int k, List<List<Integer>> track) {
        Map<Long, List<Range>> map = new HashMap<>();
        track.forEach(integers -> {
            long row = integers.get(0);
            long start = integers.get(1);
            long end = integers.get(2);
            Range newRange = new Range(start, end);

            if (map.containsKey(row))
                checkRanges(map.get(row), newRange);
            else{
                ArrayList<Range> arrayList = new ArrayList<>();
                arrayList.add(newRange);
                map.put(row, arrayList);

            }
        });
        final AtomicLong sum = new AtomicLong(0);
        map.values()
                .forEach(list -> list.forEach(range ->
                        sum.set(sum.get() + range.end + 1 - range.start)));

        return ((long) n * m) - sum.get();
    }

    private static void checkRanges(List<Range> ranges, Range newRange) {
        Optional<Range> p = ranges.stream().filter(range ->
                //check overlapped pairs
                (range.start >= newRange.start && range.end >= newRange.end && range.start <= newRange.end) ||
                        (range.start >= newRange.start && range.end <= newRange.end) ||
                        (range.start <= newRange.start && newRange.start <= range.end && range.end <= newRange.end) ||
                        (range.start <= newRange.start && range.end >= newRange.end)).findFirst();
        if (p.isPresent()) { // merge new range to existed one
            p.get().start = Math.min(newRange.start, p.get().start);
            p.get().end = Math.max(newRange.end, p.get().end);
        } else
            ranges.add(newRange);
    }
}


class Solution {
    public static void main(String[] args) throws IOException {
        BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(System.in));
        BufferedWriter bufferedWriter = new BufferedWriter(new FileWriter("OUTPUT_PATH"));

        String[] firstMultipleInput = bufferedReader.readLine().replaceAll("\\s+$", "").split(" ");

        int n = Integer.parseInt(firstMultipleInput[0]);

        int m = Integer.parseInt(firstMultipleInput[1]);

        int k = Integer.parseInt(firstMultipleInput[2]);

        List<List<Integer>> track = new ArrayList<>();

        IntStream.range(0, k).forEach(i -> {
            try {
                track.add(
                        Stream.of(bufferedReader.readLine().replaceAll("\\s+$", "").split(" "))
                                .map(Integer::parseInt)
                                .collect(toList())
                );
            } catch (IOException ex) {
                throw new RuntimeException(ex);
            }
        });

        long result = Result.gridlandMetro(n, m, k, track);

        bufferedWriter.write(String.valueOf(result));
        bufferedWriter.newLine();

        bufferedReader.close();
        bufferedWriter.close();
    }
}
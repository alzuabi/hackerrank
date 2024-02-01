package simple_text_editor;

import java.io.*;
import java.util.*;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Stack;
import java.util.function.Consumer;
import java.util.stream.IntStream;

class Solution {

    public static void main(String[] args) throws IOException{
        StringBuffer sb = new StringBuffer();

        Stack<Consumer<StringBuffer>> operations = new Stack<>();
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));

        int q = Integer.parseInt(br.readLine().trim());

        IntStream.range(0, q).forEach(i -> {
            try {
                String[] line = br.readLine().split(" ");
                if (Integer.parseInt(line[0]) == 1)
                    append(sb, operations, line[1]);
                else if (Integer.parseInt(line[0]) == 2)
                    delete(sb, operations, Integer.parseInt(line[1]));
                else if (Integer.parseInt(line[0]) == 3) {
                    print(sb, Integer.parseInt(line[1]));
                } else undo(sb, operations);
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        });
    }

    private static void print(StringBuffer sb, int i) {
        System.out.println(sb.charAt(i - 1));
    }

    private static void undo(StringBuffer sb, Stack<Consumer<StringBuffer>> operations) {
        Consumer<StringBuffer> stringBufferConsumer = operations.pop();
        stringBufferConsumer.accept(sb);
    }


    private static void delete(StringBuffer sb, Stack<Consumer<StringBuffer>> operations, int k) {
        String toAppend = sb.substring(sb.length() - k, sb.length());
        operations.push(stringBuffer -> stringBuffer.append(toAppend));
        sb.delete(sb.length() - k, sb.length());
    }

    private static void append(StringBuffer sb, Stack<Consumer<StringBuffer>> ops, String ap) {
        sb.append(ap);
        ops.push(stringBuffer -> stringBuffer.delete(sb.length() - ap.length(), sb.length()));
    }
}

package hw8;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class NameNumberConverter {
    public static void main(String[] args) {
        String inputFile = "Вася:5 Петя:3 Аня:5";
        Map<String, List<String>> collect = Arrays.stream(inputFile.split(" "))
                .parallel()
                .map(String::trim)
                .map(x -> x.substring(0, 1).toUpperCase() + x.substring(1).toLowerCase())
                .collect(Collectors.toMap(s -> s.split(":")[1],
                        s -> {
                            List<String> value = new ArrayList<>();
                            String[] split = s.split(":");
                            value.add(split[0]);
                            return value;
                        }, (l1, l2) -> {
                            l1.addAll(l2);
                            return l1;
                        }));
        System.out.println(collect);
    }
}

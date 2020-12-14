package ua.edu.ucu.stream;

import ua.edu.ucu.function.*;

import java.util.ArrayList;
import java.util.Arrays;

public class AsIntStream implements IntStream {
    private final ArrayList<Integer> streamList;
    private int count = 0;
    private int sum = 0;

    private AsIntStream(int[] values) {
        streamList = new ArrayList<>();
        for (int val: values) {
            streamList.add(val);
            count++;
            sum += val;
        }
    }

    private AsIntStream(ArrayList<Integer> values) {
        streamList = new ArrayList<>();
        for (int val: values) {
            streamList.add(val);
            count++;
            sum += val;
        }
    }

    public static IntStream of(int... values) {
        return new AsIntStream(values);
    }

    @Override
    public Double average() {
        return (double) (sum / count);
    }

    @Override
    public Integer max() {
        return (int) streamList.stream().sorted().toArray()[count-1];
    }

    @Override
    public Integer min() {
        return (int) streamList.stream().sorted().toArray()[0];
    }

    @Override
    public long count() {
        return count;
    }

    @Override
    public Integer sum() {
        return sum;
    }

    @Override
    public IntStream filter(IntPredicate predicate) {
        ArrayList<Integer> tempStream = new ArrayList<>();
        for (int i = 0; i < count; i++) {
            int val = streamList.get(i);
            if (predicate.test(val)) {
                tempStream.add(val);
            }
        }
        return new AsIntStream(new ArrayList<>(tempStream));
    }

    @Override
    public void forEach(IntConsumer action) {
        for (int val: streamList) {
            action.accept(val);
        }
    }

    @Override
    public IntStream map(IntUnaryOperator mapper) {
        for (int i = 0; i < count; i++) {
            streamList.set(i, mapper.apply(streamList.get(i)));
        }

        return new AsIntStream(new ArrayList<>(streamList));
    }

    @Override
    public IntStream flatMap(IntToIntStreamFunction func) {
        ArrayList<Integer> newIntStream = new ArrayList<>();
        for (int i = 0; i < count; i++) {
            int[] temp = func.applyAsIntStream(streamList.get(i)).toArray();
            for (int val: temp) {
                newIntStream.add(val);
            }
        }

        return new AsIntStream(new ArrayList<>(newIntStream));
    }

    @Override
    public int reduce(int identity, IntBinaryOperator op) {
        int reduced = identity;
        for (int val: streamList) {
            reduced = op.apply(reduced, val);
        }

        return reduced;
    }

    @Override
    public int[] toArray() {
        return Arrays.stream(streamList.toArray()).mapToInt(x -> (Integer) x).toArray();
    }

}

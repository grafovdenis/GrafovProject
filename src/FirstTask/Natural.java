package FirstTask;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;

public final class Natural {

    private int[] array;

    //конструктор (строковый)
    public Natural(String str) {
        if (str.length() == 0) throw new NumberFormatException("Zero-length argument");
        if (str.matches("[0-9]+"))
            this.array = str.chars().map(c -> c -= '0').toArray();
        else throw new NumberFormatException("Not a number");
    }

    //конструктор (из числа)
    public Natural(int num) {
        this(Integer.toString(num));
    }

    //конструктор (из массива)
    private Natural(int[] array) {
        this.array = array;
    }

    private Natural(ArrayList list) {
        this.array = list.stream().mapToInt(i -> (int) i).toArray();
    }

    //equals
    @Override
    public boolean equals(Object obj) {
        if (this == obj) return true;
        if (obj == null || getClass() != obj.getClass()) return false;
        Natural other = (Natural) obj;
        return Arrays.equals(this.array, other.array);
    }

    //less
    public boolean less(Natural other) {
        if (array.length >= other.array.length) {
            return false;
        } else if (array.length < other.array.length) {
            return true;
        }
        int i = 0;
        while (this.array[i] == other.array[i]) i++;
        return (this.array[i] < other.array[i]);
    }

    //more
    public boolean more(Natural other) {
        return (!equals(other) && !less(other));
    }

    //plus
    public Natural plus(Natural other) {
        ArrayList<Integer> result = new ArrayList<>();
        int maxL = Math.max(array.length, other.array.length);
        int minL = Math.min(array.length, other.array.length);
        int delta = maxL - minL;
        ArrayList<Integer> a = new ArrayList<>();
        ArrayList<Integer> b = new ArrayList<>();
        if (minL == array.length) {
            for (int el : other.array) {
                a.add(el);
            }
            for (int i = 0; i < delta; i++) {
                b.add(0);
            }
            for (int el : array) {
                b.add(el);
            }
        } else if (maxL == array.length) {
            for (int el : array) {
                a.add(el);
            }
            for (int i = 0; i < delta; i++) {
                b.add(0);
            }
            for (int el : other.array) {
                b.add(el);
            }
        }
        for (int i = 0; i < maxL; i++) {
            int temp = a.get(maxL - 1 - i) + b.get(maxL - i - 1);
            result.add(i, temp);
        }
        for (int i = 0; i < result.size() - 1; i++) {
            if (result.get(i) >= 10) {
                int temp = result.get(i) % 10;
                result.remove(i);
                result.add(i, temp);
                temp = result.get(i + 1) + 1;
                result.remove(i + 1);
                result.add(i + 1, temp);
            }
        }
        Collections.reverse(result);
        System.out.println(a);
        System.out.println(b);
        System.out.println(result);
        return new Natural(result);
    }

    //deleteZeros

    //minus
    public Natural minus(Natural other) {
        ArrayList<Integer> a = new ArrayList<>();
        ArrayList<Integer> b = new ArrayList<>();
        ArrayList<Integer> result = new ArrayList<>();
        for (int el : array) {
            a.add(el);
        }
        for (int i = 0; i < array.length - other.array.length; i++) {
            b.add(0);
        }
        for (int el : other.array) {
            b.add(el);
        }
        for (int i = 0; i < a.size(); i++) {
            int temp = a.get(a.size() - 1 - i) - b.get(a.size() - i - 1);
            result.add(i, temp);
        }
        for (int i = 0; i < result.size() - 1; i++) {
            if (result.get(i) < 0) {
                int temp = result.get(i) + 10;
                result.remove(i);
                result.add(i, temp);
                temp = result.get(i + 1) - 1;
                result.remove(i + 1);
                result.add(i + 1, temp);
            }
        }
        Collections.reverse(result);
        System.out.println(a);
        System.out.println(b);
        System.out.println(result);
        return new Natural(result);
    }
    //multiply

    //divide

    //mod

    @Override
    public int hashCode() {
        return Arrays.hashCode(array);
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        for (int i : array) {
            sb.append(i);
        }
        return sb.toString();
    }

    public static void main(String[] args) {
        Natural b1 = new Natural("132");
        Natural b2 = new Natural("132");
        System.out.println(b1);
        System.out.println(b2);
        System.out.println(b1.minus(b2));
        System.out.println(b1.more(b2));
        System.out.println(b1.less(b2));
    }
}
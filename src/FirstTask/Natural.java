package FirstTask;

import jdk.nashorn.internal.runtime.regexp.joni.Regex;

import java.util.ArrayList;
import java.util.Arrays;

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
        this.array = Integer.toString(num).chars().map(c -> c -= '0').toArray();
    }

    //конструктор (из массива)
    private Natural(int[] array) {
        this.array = array;
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
    public boolean less(Object obj) {
        if (obj instanceof Natural) {
            Natural other = (Natural) obj;
            return Arrays.hashCode(array) < Arrays.hashCode(other.array);
        }
        return false;
    }

    //more
    public boolean more(Object obj) {
        if (obj instanceof Natural) {
            Natural other = (Natural) obj;
            return Arrays.hashCode(array) > Arrays.hashCode(other.array);
        }
        return false;
    }

    //plus
    Natural plus(Natural other) {
        ArrayList<Integer> result = new ArrayList();
        ArrayList a = new ArrayList();
        ArrayList b = new ArrayList();
        if (this.array.length > other.array.length) {
            for (int el : this.array) a.add(el);
            for (int el : other.array) b.add(el);
        } else {
            for (int el : this.array) b.add(el);
            for (int el : other.array) a.add(el);
        }
        int aLen = a.size();
        int bLen = b.size();
        System.out.println(aLen + " " + bLen);
        int iterator = 1;
        while (b.size() != 0) {
            int temp = Integer.parseInt(a.get(aLen - iterator).toString()) +
                    Integer.parseInt(b.get(bLen - iterator).toString());
            result.add(bLen - iterator, temp);
            b.remove(bLen - iterator);
            System.out.println(iterator);
        }
        String res = result.toString().replace("[", "").replace("]", "");
        return new Natural(res);
    }

    //minus

    //multiply

    //divide

    //mod

    @Override
    public int hashCode() {
        return Arrays.hashCode(array) * 2;
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
        Natural b1 = new Natural("121");
        Natural b2 = new Natural("56");
        System.out.println(b1);
        System.out.println(b2);
        System.out.println(b1.equals(b2));
        System.out.println(b1.plus(b2));
    }
}

package FirstTask;

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
        this(Integer.toString(num));
    }

    //конструктор (из массива)
    private Natural(int[] array) {
        this.array = array;
    }

    private Natural(ArrayList list) {
        //
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
        return Arrays.hashCode(array) < Arrays.hashCode(other.array);
    }

    //more
    public boolean more(Natural other) {
        return Arrays.hashCode(array) > Arrays.hashCode(other.array);
    }

    //plus
    public Natural plus(Natural other) {
        ArrayList<Integer> result = new ArrayList<>();
        return new Natural(result);
    }

    //minus

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
        Natural b1 = new Natural("1234");
        Natural b2 = new Natural("5");
        System.out.println(b1);
        System.out.println(b2);
        System.out.println(b1.plus(b2));
    }
}

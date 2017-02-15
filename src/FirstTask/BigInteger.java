package FirstTask;

import java.util.ArrayList;

public final class BigInteger {

    private int[] array;

    //конструктор (строковый)
    public BigInteger(String str) {
        this.array = str.chars().map(c -> c -= '0').toArray();
    }
    //конструктор (из числа)
    public BigInteger(int num) {
        this.array = Integer.toString(num).chars().map(c -> c -= '0').toArray();
    }

    //equals
    @Override
    public boolean equals(Object obj) {
        if (this == obj) return true;
        if (obj instanceof BigInteger) {
            BigInteger other = (BigInteger) obj;
            return array == other.array;
        }
        return false;
    }
    //less

    //more

    //plus

    //minus

    //multiply

    //divide

    //mod

}

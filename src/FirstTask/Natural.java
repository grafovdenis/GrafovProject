package FirstTask;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;

public final class Natural {

    private int[] array;

    private int pow(int x, int y) {
        int result = x;
        if (y != 0) {
            for (int i = 2; i < y; i++) {
                result *= x;
            }
        } else result = 1;
        return result;
    }

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
        int zeroCount = 0;
        boolean zero = true;
        for(int digit : array) {
                        if(zero) {
                if (digit != 0) {
                    zero = false;
                }
                else {
                    zeroCount ++;
                }
            }
        }
        this.array = Arrays.copyOfRange(array, zeroCount, array.length);
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

    //more
    public boolean more(Natural other) {
        if (array.length < other.array.length) {
            return false;
        } else if (array.length > other.array.length) {
            return true;
        }
        boolean result = false;
        int i = 0;
        while (this.array[i] == other.array[i]) i++;
        return (this.array[i] > other.array[i]);
    }

    //less
    public boolean less(Natural other) {
        return (!more(other) && !equals(other));
    }

    //deleteZeros
    private Natural deleteZeros() {
        if (array[0] == 0 && array.length > 1) {
            return new Natural(0);
        } else return new Natural(array);
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
        if (result.get(0) >= 10) {
            int temp = result.get(0);
            result.remove(0);
            result.add(0, temp % 10);
            result.add(0, temp / 10);
        }
        return new Natural(result);
    }

    //minus
    public Natural minus(Natural other) {
        ArrayList<Integer> a = new ArrayList<>();
        ArrayList<Integer> b = new ArrayList<>();
        ArrayList<Integer> result = new ArrayList<>();
        if (Arrays.equals(array,other.array)) return new Natural(0);
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
        return new Natural(result).deleteZeros();
    }

    //multiply
    public Natural multiply(Natural other) {
        int[] a;
        int[] b;
        if (array.length > other.array.length) {
            a = array;
            b = other.array;
        } else {
            b = array;
            a = other.array;
        }
        int[] result = new int[a.length + b.length];
        int len = result.length;
        int k = 0;
        for (int i = b.length - 1; i >= 0; i--) {
            for (int j = a.length - 1; j >= 0; j--) {
                ++k;
                int temp = a[j] * b[i];
                result[len - k] += temp;
            }
            len--;
            k = 0;
        }
        for (int i = result.length - 1; i > 0; i--) {
            if (result[i] >= 10) {
                result[i - 1] += result[i] / 10;
                result[i] %= 10;
            }
        }
        if (result[0] == 0) {
            int[] res = new int[result.length - 1];
            System.arraycopy(result, 1, res, 0, result.length - 1);
            return new Natural(res);
        }
        return new Natural(result);
    }

    //div

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
}
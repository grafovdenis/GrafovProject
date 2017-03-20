package FirstTask;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;

public final class Natural {

    private int[] array;

    private int[] ZERO = {0};

    //конструктор (строковый)
    public Natural(String str) {
        if (str.equals("0")) this.array = ZERO;
        else if (str.length() == 0) throw new NumberFormatException("Zero-length argument");
        else if (str.matches("[0-9]+")) {
            int zeroCount = 0;
            boolean zero = true;
            for (char ch : str.toCharArray()) {
                if (zero) {
                    if (ch != '0') {
                        zero = false;
                    } else {
                        zeroCount++;
                    }
                }
            }
            this.array = str.substring(zeroCount, str.length()).chars().map(c -> c -= '0').toArray();
        } else throw new NumberFormatException("Not a number");
    }

    //конструктор (из числа)
    public Natural(int num) {
        this(Integer.toString(num));
    }

    //конструктор (из массива)
    private Natural(int[] array) {
        if (Arrays.equals(array, ZERO)) this.array = ZERO;
        else {
            int zeroCount = 0;
            boolean zero = true;
            for (int digit : array) {
                if (zero) {
                    if (digit != 0) {
                        zero = false;
                    } else {
                        zeroCount++;
                    }
                }
            }
            this.array = Arrays.copyOfRange(array, zeroCount, array.length);
        }
    }

    private Natural(ArrayList list) {
        this(list.stream().mapToInt(i -> (int) i).toArray());
    }

    //equals
    @Override
    public boolean equals(Object obj) {
        if (this == obj) return true;
        if (obj == null || getClass() != obj.getClass()) return false;
        Natural other = (Natural) obj;
        return Arrays.equals(this.array, other.array);
    }

    //compareTo
    public int compareTo(Natural other) {
        if (this.equals(other)) return 0;
        if (array.length > other.array.length) {
            return 1;
        } else if (array.length < other.array.length) {
            return -1;
        } else {
            int i = 0;
            while (this.array[i] == other.array[i]) i++;
            if (this.array[i] > other.array[i]) return 1;
            else return -1;
        }
    }

    //plus
    public Natural plus(Natural other) {
        if (array == ZERO) return other;
        if (other.array == ZERO) return this;
        int[] a;
        int[] b;
        if (array.length >= other.array.length) {
            a = array;
            b = other.array;
        } else {
            a = other.array;
            b = array;
        }
        ArrayList<Integer> res = new ArrayList<>();
        int delta = a.length - b.length;
        int carry = 0;
        for (int i = a.length - 1; i > -1; i--) {
            if (i - delta < 0) {
                int temp = a[i] + carry;
                if (temp >= 10) {
                    carry = temp / 10;
                    temp %= 10;
                } else carry = 0;
                res.add(temp);
            } else {
                int temp = a[i] + b[i - delta] + carry;
                if (temp >= 10) {
                    carry = temp / 10;
                    temp %= 10;
                } else carry = 0;
                res.add(temp);
            }
        }
        if (carry != 0) res.add(carry);
        Collections.reverse(res);
        return new Natural(res);
    }

    //minus
    public Natural minus(Natural other) {
        if (other.array.length > array.length) throw new IllegalArgumentException("Sorry :C");
        if (other.array == ZERO) return this;
        if (this.equals(other)) return new Natural(0);
        ArrayList<Integer> res = new ArrayList<>();
        int delta = array.length - other.array.length;
        int carry = 0;
        for (int i = array.length - 1; i > -1; i--) {
            if (i - delta < 0) {
                int temp = array[i] - carry;
                if (temp == 0) {
                    res.add(0);
                    continue;
                }
                if (temp < 0) {
                    temp += 10;
                    carry = 1;
                } else carry = 0;
                res.add(temp);
            } else {
                int temp = array[i] - other.array[i - delta] - carry;
                if (temp == 0) {
                    res.add(0);
                    continue;
                }
                if (temp < 0) {
                    temp += 10;
                    carry = 1;
                } else carry = 0;
                res.add(temp);
            }
        }
        Collections.reverse(res);
        return new Natural(res);
    }

    //multiply
    public Natural multiply(Natural other) {
        if (Arrays.equals(this.array, ZERO) || Arrays.equals(other.array, ZERO)) {
            return new Natural(ZERO);
        }
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

    private Natural multiplyToOneDigit(int digit) {
        ArrayList<Integer> result = new ArrayList<>();
        int carry = 0;
        for (int i = array.length - 1; i >= 0; i--) {
            int temp = array[i] * digit + carry;
            if (temp >= 0) {
                carry = temp / 10;
                temp %= 10;
            } else carry = 0;
            System.out.println(temp);
            result.add(temp);
        }
        Collections.reverse(result);
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
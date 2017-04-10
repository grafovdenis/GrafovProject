package FirstTask;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;

public final class Natural implements Comparable<Natural> {

    private int[] array;

    private static int[] ZERO = {0};

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

    public Natural(int num) {
        this(Integer.toString(num));
    }

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
            int temp;
            if (i - delta < 0)
                temp = a[i] + carry;
            else
                temp = a[i] + b[i - delta] + carry;
            if (temp >= 10) {
                carry = temp / 10;
                temp %= 10;
            } else carry = 0;
            res.add(temp);
        }
        if (carry != 0) res.add(carry);
        Collections.reverse(res);
        return new Natural(res);
    }

    public Natural minus(Natural other) {
        if (other.compareTo(this) == 1) throw new IllegalArgumentException("Sorry :C");
        if (other.array == ZERO) return this;
        if (this.equals(other)) return new Natural(0);
        ArrayList<Integer> res = new ArrayList<>();
        int delta = array.length - other.array.length;
        int carry = 0;
        for (int i = array.length - 1; i > -1; i--) {
            int temp;
            if (i - delta < 0)
                temp = array[i] - carry;
            else
                temp = array[i] - other.array[i - delta] - carry;
            if (temp < 0) {
                temp += 10;
                carry = 1;
            } else carry = 0;
            res.add(temp);
        }
        Collections.reverse(res);
        return new Natural(res);
    }

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
        return new Natural(result);
    }

    /*
    1) Выбираем из A слева столько цифр, сколько их в B. Получаем число A1.
    2) Если А1 < B, то прибавляем в него еще одну цифру из А.
    3) Перебором всех цифр С находим самую большую, при которой "элементарное произведение" C*B <= A1.
    4) Записываем цифру С в результат.
    5) Вычитаем СЛЕВА из A "элементарное произведение" C*B.
    6) Если A >= B Повторяем (1), иначе деление закончено и A - остаток от деления.
     */
    private Natural divMod(Natural other, boolean callDiv) {
        if (other.equals(new Natural(0))) throw new IllegalArgumentException();
        else if (this.equals(new Natural(0))) return new Natural("0");
        else {
            ArrayList<Integer> result = new ArrayList<>();
            ArrayList<Integer> a = new ArrayList<>();
            for (int el : this.array) {
                a.add(el);
            }
            Natural b = other;
            Natural rest = new Natural(0);
            while (rest.compareTo(new Natural(0)) >= 0) {
                int i = 0;
                ArrayList<Integer> a1 = new ArrayList<>();
                if (rest.compareTo(new Natural(0)) != 0) {
                    a1.add(Integer.parseInt(rest.toString()));
                }
                while (new Natural(a1).compareTo(b) < 0) {
                    if (a.size() == 0) break;
                    a1.add(a.get(0));
                    a.remove(0);
                    i++;
                    if (i > 1) result.add(0);
                }
                Natural A1 = new Natural(a1);
                int C = 9;
                Natural composition = b.multiply(new Natural(C));
                while (composition.compareTo(A1) == 1 && C > 0) {
                    C--;
                    composition = b.multiply(new Natural(C));
                }
                if (C >= 10) {
                    result.add(C / 10);
                    result.add(C % 10);
                } else result.add(C);
                if (composition.compareTo(new Natural(0)) == 0) {
                    rest = A1;
                } else
                    rest = A1.minus(composition);
                if (a.size() == 0) break;
            }
            if (callDiv) return new Natural(result);
            else return rest;
        }
    }

    public Natural div(Natural other) {
        return this.divMod(other, true);
    }

    public Natural mod(Natural other) {
        return this.divMod(other, false);
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) return true;
        if (obj == null || getClass() != obj.getClass()) return false;
        Natural other = (Natural) obj;
        return Arrays.equals(this.array, other.array);
    }

    public int compareTo(Natural other) {
        if (array.length > other.array.length) {
            return 1;
        } else if (array.length < other.array.length) {
            return -1;
        } else {
            int i = 0;
            while (this.array[i] == other.array[i]) {
                i++;
                if (i == array.length) {
                    return 0;
                }
            }
            if (this.array[i] > other.array[i]) return 1;
            else return -1;
        }
    }

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
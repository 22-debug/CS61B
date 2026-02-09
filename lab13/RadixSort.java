/**
 * Class for doing Radix sort
 *
 * @author Akhil Batra, Alexander Hwang
 *
 */
public class RadixSort {
    /**
     * Does LSD radix sort on the passed in array with the following restrictions:
     * The array can only have ASCII Strings (sequence of 1 byte characters)
     * The sorting is stable and non-destructive
     * The Strings can be variable length (all Strings are not constrained to 1 length)
     *
     * @param string String[] that needs to be sorted
     *
     * @return String[] the sorted array
     */
    public static String[] sort(String[] string) {
        int maxDigits = 0;
        for (String str : string) {
            if (str.length() > maxDigits) {
                maxDigits = str.length();
            }
        }

        String[] sorted = new String[string.length];
        System.arraycopy(string, 0, sorted, 0, string.length);
        for (int digit = maxDigits - 1; digit >= 0; digit--) {
            sortHelperLSD(sorted, digit);
        }
        return sorted;
    }

    /**
     * LSD helper method that performs a destructive counting sort the array of
     * Strings based off characters at a specific index.
     * @param asciis Input array of Strings
     * @param index The position to sort the Strings on.
     */
    private static void sortHelperLSD(String[] asciis, int index) {
        // Optional LSD helper method for required LSD radix sort
        final int R = 256; // ASCII字符数
        final int TOTAL_BUCKETS = R + 1; // 加上空字符桶

        int[] count = new int[TOTAL_BUCKETS];

        for (String str : asciis) {
            int charIndex;
            if (index >= str.length()) {
                //空字符在第一个桶
                charIndex = 0;
            } else {
                charIndex = (int) str.charAt(index) + 1;
            }
            count[charIndex]++;
        }

        for (int i = 1; i < TOTAL_BUCKETS; i++) {
            count[i] += count[i - 1];
        }

        String[] aux = new String[asciis.length];
        for (int i = asciis.length - 1; i >= 0; i--) {
            String str = asciis[i];
            int charIndex;
            if (index >= str.length()) {
                charIndex = 0;
            } else {
                charIndex = (int) str.charAt(index) + 1;
            }
            aux[--count[charIndex]] = str;
        }

        System.arraycopy(aux, 0, asciis, 0, asciis.length);
        return;
    }

    /**
     * MSD radix sort helper function that recursively calls itself to achieve the sorted array.
     * Destructive method that changes the passed in array, asciis.
     *
     * @param asciis String[] to be sorted
     * @param start int for where to start sorting in this method (includes String at start)
     * @param end int for where to end sorting in this method (does not include String at end)
     * @param index the index of the character the method is currently sorting on
     *
     **/
    private static void sortHelperMSD(String[] asciis, int start, int end, int index) {
        // Optional MSD helper method for optional MSD radix sort
        if (end - start <= 1) {
            return;
        }

        final int R = 256;
        final int TOTAL_BUCKETS = R + 1; //索引0表示空字符，1-256表示ASCII字符

        //统计频率
        int[] count = new int[TOTAL_BUCKETS];
        for (int i = start; i < end; i++) {
            String str = asciis[i];
            int charIndex = (index >= str.length()) ? 0 : (str.charAt(index) + 1);
            count[charIndex]++;
        }

        //转换为累积频率（count[i]表示字符值<=i的字符串数量）
        int[] countCopy = count.clone(); // 保存频率的副本
        for (int i = 1; i < TOTAL_BUCKETS; i++) {
            count[i] += count[i - 1];
        }

        //排序到辅助数组（从后往前保持稳定）
        String[] aux = new String[end - start];
        for (int i = end - 1; i >= start; i--) {
            String str = asciis[i];
            int charIndex = (index >= str.length()) ? 0 : (str.charAt(index) + 1);
            aux[--count[charIndex]] = str;
        }

        //复制回原数组
        System.arraycopy(aux, 0, asciis, start, end - start);

        //计算每个桶的边界并递归排序
        //使用原始的频率countCopy来计算边界
        int cumulative = 0;
        for (int i = 0; i < TOTAL_BUCKETS; i++) {
            int bucketStart = start + cumulative;
            int bucketEnd = start + cumulative + countCopy[i];
            cumulative += countCopy[i];

            if (bucketEnd - bucketStart > 1) {
                sortHelperMSD(asciis, bucketStart, bucketEnd, index + 1);
            }
        }
    }
}

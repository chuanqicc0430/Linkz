package cn.net.cvtt.lian.common.util;

import java.util.Comparator;

public class NumericalVersionComparator implements Comparator<String> {

    @Override
    public int compare(String v1, String v2) throws NumberFormatException {
        if ((v1 == null || v1.trim().isEmpty()) && (v2 != null && !v2.trim().isEmpty())) {
            return -1;
        }
        if ((v2 == null || v2.trim().isEmpty()) && (v1 != null && !v1.trim().isEmpty())) {
            return 1;
        }
        if ((v1 == null || v1.trim().isEmpty()) && (v2 == null || v2.trim().isEmpty())) {
            return 0;
        }

        int v1Temp = 0;
        int v2Temp = 0;
        String[] v1Arr = v1.trim().split("\\.");
        String[] v2Arr = v2.trim().split("\\.");
        int max = Math.max(v1Arr.length, v2Arr.length);

        for (int i = 0; i < max; i++) {
            v1Temp = Integer.parseInt(i != v1Arr.length ? v1Arr[i] : "0");
            v2Temp = Integer.parseInt(i != v2Arr.length ? v2Arr[i] : "0");
            if (v1Temp != v2Temp) {
                break;
            }
        }

        return Integer.signum(v1Temp - v2Temp);
    }
    
    public static void main(String[] args) {
    	NumericalVersionComparator com = new NumericalVersionComparator();
		System.out.println(com.compare("6.0.0.6.5", "6.5"));
	}

}